/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.maths.matrices.decomposition;

import demetra.data.DoubleSequence;
import demetra.maths.matrices.Matrix;
import demetra.maths.MatrixException;

/**
 * A = U*S*V'
 * @author Jean Palate <jean.palate@nbb.be>
 */
public interface ISingularValueDecomposition {
    void decompose(Matrix A)throws MatrixException;
    
    Matrix U();
    
    DoubleSequence S();
    
    Matrix V();
    
    boolean isFullRank();
    
    int rank();
}