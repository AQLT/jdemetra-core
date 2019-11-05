/*
 * Copyright 2019 National Bank of Belgium.
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *      https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdplus.dstats;

import jdplus.data.DataBlock;
import demetra.design.Development;
import demetra.design.Immutable;
import demetra.math.Constants;
import jdplus.maths.matrices.LowerTriangularMatrix;
import jdplus.maths.matrices.SymmetricMatrix;
import lombok.NonNull;
import jdplus.random.RandomNumberGenerator;
import demetra.data.DoubleSeq;
import jdplus.maths.matrices.FastMatrix;

/**
 *
 * @author Jean Palate
 */
@Development(status = Development.Status.Release)
@Immutable(lazy = true)
public final class MultivariateNormal {
    
    private static final Normal N=new Normal(0,1);

    private final DataBlock mean;
    private final FastMatrix cov;
    private volatile FastMatrix lchol;

    public MultivariateNormal(DoubleSeq mean, FastMatrix cov) {
        this.mean = DataBlock.of(mean);
        this.cov = cov.deepClone();
    }

    /**
     * @return the mean
     */
    public DataBlock getMean() {
        return mean;
    }

    /**
     * @return the cov
     */
    public FastMatrix getCovariance() {
        return cov;
    }

    public int getDimension() {
        return mean.length();
    }

    /**
     * Fills the given datablock by randoms generated by the random generator
     * @param rng
     * @param rnd The buffer that will contain the generated random numbers
     */
    public void random(@NonNull RandomNumberGenerator rng, DataBlock rnd) {
        rnd.set(()->N.random(rng));
        FastMatrix lm=l();
        LowerTriangularMatrix.lmul(lm, rnd);
        rnd.add(mean);
    }
    
    private FastMatrix l(){
        FastMatrix tmp = this.lchol;
        if (tmp == null) {
            synchronized (this) {
                tmp = this.lchol;
                if (tmp == null) {
                    tmp=cov.deepClone();
                    SymmetricMatrix.lcholesky(tmp, Constants.getEpsilon());
                    this.lchol = tmp;
                }
            }
        }
        return lchol;
    }

}
