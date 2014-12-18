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

package ec.tstoolkit.timeseries.regression;

import ec.tstoolkit.timeseries.simplets.TsPeriod;

/**
 *
 * @author Jean Palate
 */
public class MissingValueEstimation implements Comparable<MissingValueEstimation> {

    public MissingValueEstimation(TsPeriod pos, double val, double se) {
        position_ = pos;
        value_ = val;
        ser_ = se;
    }

    public double getValue() {
        return value_;
    }

    public double getStdev() {
        return ser_;
    }

    public TsPeriod getPosition() {
        return position_.clone();
    }
    private final double value_;
    private final double ser_;
    private final TsPeriod position_;

    @Override
    public int compareTo(MissingValueEstimation o) {
        return position_.compareTo(o.position_);
    }

}
