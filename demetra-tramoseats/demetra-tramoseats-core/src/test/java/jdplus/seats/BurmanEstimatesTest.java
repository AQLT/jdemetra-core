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
package jdplus.seats;

import demetra.arima.SarimaSpecification;
import demetra.data.Data;
import jdplus.sarima.SarimaModel;
import jdplus.ucarima.ModelDecomposer;
import jdplus.ucarima.SeasonalSelector;
import jdplus.ucarima.TrendCycleSelector;
import jdplus.ucarima.UcarimaModel;
import org.junit.Test;

/**
 *
 * @author Jean Palate
 */
public class BurmanEstimatesTest {
    
    public BurmanEstimatesTest() {
    }

    @Test
    public void testAirline() {
        UcarimaModel ucm = ucmAirline(-.6, -.8);
        ucm = ucm.simplify();
        BurmanEstimates burman=new BurmanEstimates();
        burman.setData(Data.TS_PROD.getValues());
        burman.setUcarimaModel(ucm);
        double[] estimates = burman.estimates(0, true);
//        System.out.println(DataBlock.ofInternal(estimates));
        estimates = burman.estimates(1, true);
//        System.out.println(DataBlock.ofInternal(estimates));
        estimates = burman.estimates(2, true);
//        System.out.println(DataBlock.ofInternal(estimates));
    }
    
    public static UcarimaModel ucmAirline(double th, double bth) {
        SarimaSpecification spec = SarimaSpecification.airline(true);
        SarimaModel sarima = SarimaModel.builder(spec)
                .theta(1, th)
                .btheta(1, bth)
                .build();

        TrendCycleSelector tsel = new TrendCycleSelector();
        SeasonalSelector ssel = new SeasonalSelector(12);

        ModelDecomposer decomposer = new ModelDecomposer();
        decomposer.add(tsel);
        decomposer.add(ssel);

        UcarimaModel ucm = decomposer.decompose(sarima);
        ucm = ucm.setVarianceMax(-1, false);
        return ucm;
    }
}
