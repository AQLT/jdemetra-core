/*
 * Copyright 2016 National Bank of Belgium
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
package demetra.stats.tests;

import demetra.data.DataBlock;
import demetra.data.DataBlockIterator;
import demetra.design.BuilderPattern;
import demetra.maths.matrices.FastMatrix;
import demetra.maths.matrices.internal.Householder;
import demetra.data.DoubleSeq;

/**
 * Augmented Dickey-Fuller test
 *
 * @author Jean Palate
 */
public class AugmentedDickeyFuller {

    public static Builder builder() {
        return new Builder();
    }

    @BuilderPattern(AugmentedDickeyFuller.class)
    public static class Builder {

        private int k = 1; // number of lags. 
        private boolean cnt, trend;
        private DoubleSeq y;

        public Builder data(DoubleSeq y) {
            this.y = y;
            return this;
        }

        public Builder numberOfLags(int nlags) {
            if (k < 1) {
                throw new java.lang.IllegalArgumentException("k should be greater or equal to 1");
            }
            this.k = nlags;
            return this;
        }

        public Builder constant(boolean cnt) {
            this.cnt = cnt;
            return this;
        }

        public Builder linearTrend(boolean trend) {
            this.trend = trend;
            return this;
        }

        public AugmentedDickeyFuller build() {
            //
            int ndata = y.length();
            int ncols = k;
            if (cnt) {
                ++ncols;
            }
            if (trend) {
                ++ncols;
            }
            FastMatrix x = FastMatrix.make(ndata - k, ncols);

            DataBlockIterator columns = x.reverseColumnsIterator();
            columns.next().copy(y.extract(k - 1, ndata - k));
            DataBlock del = DataBlock.of(y);
            del.autoApply(-1, (a, b) -> a - b);
            for (int i = 1; i < k; ++i) {
                columns.next().copy(del.extract(k - i, ndata - k));
            }
            if (cnt) {
                columns.next().set(1);
            }
            if (trend) {
                columns.next().set(idx -> idx);
            }
            return new AugmentedDickeyFuller(del.drop(k, 0), x, cnt, trend);
        }

    }

    private static final double[] TNC_01 = new double[]{-2.56574, -2.2358, -3.627};
    private static final double[] TNC_05 = new double[]{-1.94100, -0.2686, -3.365, 31.223};
    private static final double[] TNC_10 = new double[]{-1.61682, 0.2656, -2.714, 25.364};
    private static final double[] TC_01 = new double[]{-3.43035, -6.5393, -16.786, -79.433};
    private static final double[] TC_05 = new double[]{-2.86154, -2.8903, -4.234, -40.040};
    private static final double[] TC_10 = new double[]{-2.56677, -1.5384, -2.809};
    private static final double[] LT_01 = new double[]{-3.95877, -9.0531, -28.428, -134.155};
    private static final double[] LT_05 = new double[]{-3.41049, -4.3904, -9.036, -45.374};
    private static final double[] LT_10 = new double[]{-3.12705, -2.5856, -3.925, -22.380};

    private final boolean cnt, trend;
    private final FastMatrix x;
    private final DoubleSeq y;
    private final DataBlock b, e;
    private final double t;

    private AugmentedDickeyFuller(DoubleSeq y, FastMatrix x, boolean cnt, boolean trend) {
        this.x = x;
        this.y = y;
        this.cnt = cnt;
        this.trend = trend;
        Householder qr = new Householder(true);
        qr.decompose(x);
        b = DataBlock.make(x.getColumnsCount());
        e = DataBlock.make(x.getRowsCount() - x.getColumnsCount());
        qr.leastSquares(y, b, e);
        int nlast = b.length() - 1;
        double ssq = e.ssq();
        double val = b.get(nlast);
        double std = Math.abs(Math.sqrt(ssq / e.length()) / qr.rdiagonal(false).get(nlast));
        t = val / std;
    }

    public boolean isSignificant(double eps) {
        if (!cnt && !trend) {
            return sign00(eps);
        } else if (!trend) {
            return sign10(eps);
        } else {
            return sign11(eps);
        }
    }

    /**
     * @return the t
     */
    public double getT() {
        return t;
    }

    /**
     * @return the cnt
     */
    public boolean isConstant() {
        return cnt;
    }

    /**
     * @return the trend
     */
    public boolean isTrend() {
        return trend;
    }

    static double thresholdnc(double eps, int n) {
        double[] w = null;
        if (eps == 0.01) {
            w = TNC_01;
        } else if (eps == 0.05) {
            w = TNC_05;
        } else if (eps == 0.1) {
            w = TNC_10;
        }
        if (w == null) {
            return Double.NaN;
        }
        double q = n;
        double s = w[0] + w[1] / q;
        for (int i = 2; i < w.length; ++i) {
            q *= n;
            s += w[i] / q;
        }
        return s;
    }

    static double thresholdc(double eps, int n) {
        double[] w = null;
        if (eps == 0.01) {
            w = TC_01;
        } else if (eps == 0.05) {
            w = TC_05;
        } else if (eps == 0.1) {
            w = TC_10;
        }
        if (w == null) {
            return Double.NaN;
        }
        double q = n;
        double s = w[0] + w[1] / q;
        for (int i = 2; i < w.length; ++i) {
            q *= n;
            s += w[i] / q;
        }
        return s;
    }

    static double thresholdt(double eps, int n) {
        double[] w = null;
        if (eps == 0.01) {
            w = LT_01;
        } else if (eps == 0.05) {
            w = LT_05;
        } else if (eps == 0.1) {
            w = LT_10;
        }
        if (w == null) {
            return Double.NaN;
        }
        double q = n;
        double s = w[0] + w[1] / q;
        for (int i = 2; i < w.length; ++i) {
            q *= n;
            s += w[i] / q;
        }
        return s;
    }

    private boolean sign10(double eps) {
        double th = thresholdc(eps, e.length());
        return t <= th;
    }

    private boolean sign00(double eps) {
        double th = thresholdnc(eps, e.length());
        return t <= th;
    }

    private boolean sign11(double eps) {
        double th = thresholdt(eps, e.length());
        return t <= th;
    }
}
