/*
 * Copyright 2017 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package demetra.maths.matrices.spi;

import demetra.design.Algorithm;
import demetra.design.Development;
import demetra.design.ServiceDefinition;
import demetra.leastsquares.internal.QRSolverImpl;
import demetra.maths.matrices.Matrix;
import demetra.maths.matrices.internal.Householder;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import demetra.maths.matrices.spi.LeastSquaresSolver;

/**
 *
 * @author Jean Palate
 */
@Algorithm
@ServiceDefinition
@Development(status = Development.Status.Alpha)
public interface QRSolver extends LeastSquaresSolver {

    /**
     * Gets the R matrix (upper triangular matrix) of the QR decomposition.
     *    
     * @return The R matrix. Might be singular. 
     */
    Matrix R();
}

