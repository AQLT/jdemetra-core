/*
 * Copyright 2015 National Bank of Belgium
 *  
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *  
 * http://ec.europa.eu/idabc/eupl
 *  
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
 /*
 */
package demetra.ssf.univariate;

import demetra.data.DataBlock;
import demetra.maths.matrices.Matrix;
import demetra.ssf.IStateResults;
import demetra.data.DoubleSequence;

/**
 *
 * @author PCUser
 */
public interface ISmoothingResults extends IStateResults {

    DataBlock a(int pos);

    Matrix P(int pos);

    void rescaleVariances(double factor);

    DoubleSequence getComponent(int pos);

    DoubleSequence getComponentVariance(int pos);
    
    boolean hasVariances();

}
