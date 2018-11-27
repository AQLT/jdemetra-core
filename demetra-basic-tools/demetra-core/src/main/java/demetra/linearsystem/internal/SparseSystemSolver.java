/*
* Copyright 2013 National Bank ofInternal Belgium
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
* by the European Commission - subsequent versions ofInternal the EUPL (the "Licence");
* You may not use this work except in compliance with the Licence.
* You may obtain a copy ofInternal the Licence at:
*
* http://ec.europa.eu/idabc/eupl
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the Licence is distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and 
* limitations under the Licence.
*/

package demetra.linearsystem.internal;

import demetra.design.Development;
import java.util.Arrays;
import demetra.data.DataBlock;
import demetra.design.AlgorithmImplementation;
import demetra.maths.Constants;
import demetra.maths.matrices.Matrix;
import demetra.maths.matrices.MatrixException;
import demetra.linearsystem.LinearSystemSolver;

/**
 * This class is a translation ofInternal the code used in Seats (routine MLTSOL)
 * 
 * @author Jean Palate
 */
@Development(status = Development.Status.Release)
@AlgorithmImplementation(algorithm = LinearSystemSolver.class)
public final class SparseSystemSolver implements LinearSystemSolver {

    
    /**
     * Compute X such that M * X = B (or X = M^-1 * B)
     * 
     * @param M
     * @param B
     * @return
     */
    private static boolean solve(double[] A, Matrix B) {
        int n=B.getRowsCount(), nl=n+B.getColumnsCount();
	int[] m = new int[nl];
        Arrays.fill(m, -1);

	for (int i = 0; i < n; ++i) {
	    double u = Double.MIN_VALUE;
	    int icur = -1;

	    for (int k = 0; k < n; ++k)
		if (m[k] == -1) // unused
		{
		    double a = A[i+k*n];
		    double absa = Math.abs(a);
		    if (absa > u) {
			icur = k;
			u = absa;
		    }
		}
	    if (icur < 0)
		return false;
	    m[icur] = i;
	    double pivot = 1 / A[i+icur*n];
	    if (Math.abs(pivot) <= 1e-15)
		pivot = 0;
	    for (int j = 0; j < n; ++j) {
		if (j != i) {
		    double a = A[j+icur*n];
		    if (Math.abs(a) >= 1e-13) {
			double fac = pivot * a;
			for (int k = 0; k < nl; ++k) {
			    double aik = A[i+k*n];
			    if (Math.abs(aik) <= Constants.getEpsilon()) {
				A[i+k*n]=0;
				aik = 0;
			    }
			    if (m[k] == -1)
				A[j+k*n]+= -fac * aik;
			}
		    }
		}
	    }
	    for (int k = 0; k < nl; ++k)
		if (m[k] == -1)
		    A[i+k*n]*=pivot;
	}
	for (int k = n, l=0; k < nl; ++k, ++l) {
	    for (int i = 0; i < n; ++i)
		if (m[i] != -1)
		    B.set(i, l, A[m[i]+k*n]);
	}
	return true;
    }

    @Override
    public void solve(Matrix A, DataBlock b) throws MatrixException {
	int n = A.getRowsCount();
        if (b.length() != n)
            throw new MatrixException(MatrixException.DIM);
        double[] X=new double[n*(n+1)];
        A.copyTo(X, 0);
        b.copyTo(X, n*n);
        if (! solve(X, Matrix.columnOf(b)))
            throw new MatrixException(MatrixException.SINGULAR);
    }

    @Override
    public void solve(Matrix A, Matrix B) throws MatrixException {
	int n = A.getRowsCount(), l = B.getColumnsCount(), nl = l + n;
        if (B.getRowsCount() != n)
            throw new MatrixException(MatrixException.DIM);
        double[] X=new double[n*nl];
        A.copyTo(X, 0);
        B.copyTo(X, n*n);
        if (! solve(X, B))
            throw new MatrixException(MatrixException.SINGULAR);
    }
}
