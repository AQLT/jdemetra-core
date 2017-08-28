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
package demetra.timeseries;

import demetra.design.Development;
import java.time.LocalDateTime;

/**
 *
 * @author Jean Palate
 */
@Development(status = Development.Status.Preliminary)
public class ValidityPeriod {

    private final LocalDateTime beg, end;

    public ValidityPeriod(LocalDateTime beg, LocalDateTime end) {
        this.beg=beg;
        this.end=end;        
    }

    public LocalDateTime getStart() {
        return beg;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isStartSpecified() {
        return beg != null;
    }

    public boolean isEndSpecified() {
        return end != null;
    }
}
