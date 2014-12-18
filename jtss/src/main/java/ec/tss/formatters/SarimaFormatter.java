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

package ec.tss.formatters;

import ec.tstoolkit.sarima.SarimaModel;

/**
 *
 * @author Jean Palate
 */
public class SarimaFormatter implements IStringFormatter {

    @Override
    public String format(Object obj, int item) {

        SarimaModel model = (SarimaModel)obj;
        switch (item) {
            case 0:
                return model.getSpecification().toString();
            case 1:
                return Integer.toString(model.getSpecification().getP());
            case 2:
                return Integer.toString(model.getSpecification().getD());
            case 3:
                return Integer.toString(model.getSpecification().getQ());
            case 4:
                return Integer.toString(model.getSpecification().getBP());
            case 5:
                return Integer.toString(model.getSpecification().getBD());
            case 6:
                return Integer.toString(model.getSpecification().getBQ());
            default:
                return "";
        }
    }
}
