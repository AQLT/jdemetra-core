/*
* Copyright 2013 National Bank of Belgium
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
package demetra.data.analysis;

import demetra.maths.Constants;

/**
 * TUKEY-HANNING Taper.
 * The series is modified at the beginning at the end by means of a cosine transformation.
 * The part which is tapered can be specified by the user
 *
 * @author Jean Palate
 */
public class TukeyHanningTaper implements Taper {

    private final double r;

    /**
     *
     * @param r The proportion of the data that are modified by the processing.
     * r/2 percent of the data at the beginning and at the end of the sample are
     * modified
     */
    public TukeyHanningTaper(double r) {
        if (r < 0 || r > 1) {
            throw new IllegalArgumentException();
        }
        this.r = r;
    }

    @Override
    public void process(double[] x) {
        int l = x.length;
        int len = (int) (l * r * .5);
        for (int i = 0; i < len; i++) {
            double xtap = (i + .5) / l;
            double xpi = Constants.TWOPI * xtap / r;
            x[i] *= (1 - Math.cos(xpi)) / 2.0;
        }
        for (int i = x.length - len; i < x.length; i++) {
            double xtap = (i + .5) / l;
            double xpi = Constants.TWOPI * (1 - xtap) / r;
            x[i] *= (1 - Math.cos(xpi)) / 2.0;
        }
    }
}
