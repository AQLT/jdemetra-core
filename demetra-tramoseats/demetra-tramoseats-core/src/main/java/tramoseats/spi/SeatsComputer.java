/*
 * Copyright 2020 National Bank of Belgium
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
package tramoseats.spi;

import demetra.seats.SeatsProcessor;
import demetra.seats.SeatsResults;
import demetra.seats.SeatsSpec;
import demetra.timeseries.TsData;
import java.util.List;
import nbbrd.service.ServiceProvider;

/**
 *
 * @author palatej
 */
@ServiceProvider(SeatsProcessor.Computer.class)
public class SeatsComputer implements SeatsProcessor.Computer{

    @Override
    public SeatsResults compute(TsData series, SeatsSpec spec, List<String> addtionalItems) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
