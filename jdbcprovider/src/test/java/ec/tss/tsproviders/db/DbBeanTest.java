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

package ec.tss.tsproviders.db;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 *
 * @author Philippe Charles
 */
public class DbBeanTest {

    @Test
    public void testGetDimArray() {

        assertArrayEquals(new String[]{"Col1", "Col2", "Col3"}, DbBean.getDimArray("Col1, Col2, Col3"));
        assertArrayEquals(new String[]{"Col1", "Col3"}, DbBean.getDimArray("Col1, , Col3"));

    }
}
