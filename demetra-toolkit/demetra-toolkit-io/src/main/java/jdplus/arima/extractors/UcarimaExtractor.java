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
package jdplus.arima.extractors;

import demetra.design.Development;
import demetra.information.InformationMapping;
import jdplus.modelling.ApiUtility;
import jdplus.ucarima.UcarimaModel;

/**
 *
 * @author Jean Palate
 */
@Development(status = Development.Status.Release)
@lombok.experimental.UtilityClass
public class UcarimaExtractor {

    final static String COMPONENT="component", MODEL="model", REDUCEDMODEL="reducedmodel", // Component
            SUM="sum",  // Reduced model
            SIZE="size";  // Number of components

    static final InformationMapping<UcarimaModel> MAPPING = new InformationMapping<>(UcarimaModel.class);

    static {
        MAPPING.set(SIZE, Integer.class, source->source.getComponentsCount());
        MAPPING.set(REDUCEDMODEL, demetra.arima.ArimaModel.class, source->ApiUtility.toApi(source.getModel(), "reducedmodel"));
        MAPPING.delegate(SUM, ArimaExtractor.getMapping(), source->source.getModel());
        MAPPING.delegateArray(COMPONENT, 1, 10, ArimaExtractor.getMapping(), (source, i)
                -> i>source.getComponentsCount()? null : source.getComponent(i-1));
        MAPPING.setArray(MODEL, 1, 10, demetra.arima.ArimaModel.class, (source, i)
                -> i>source.getComponentsCount() ? null : ApiUtility.toApi(source.getComponent(i-1),"cmp"+(i+1)));
    }

    public InformationMapping<UcarimaModel> getMapping() {
        return MAPPING;
    }
    
}
