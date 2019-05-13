/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.maths.matrices.decomposition;

import demetra.maths.matrices.CanonicalMatrix;
import demetra.maths.matrices.MatrixException;
import demetra.data.DoubleSeq;
import demetra.maths.matrices.FastMatrix;

/**
 * A = U*S*V'
 * @author Jean Palate <jean.palate@nbb.be>
 */
public interface ISingularValueDecomposition {
    void decompose(FastMatrix A)throws MatrixException;
    
    CanonicalMatrix U();
    
    DoubleSeq S();
    
    CanonicalMatrix V();
    
    boolean isFullRank();
    
    int rank();
}
