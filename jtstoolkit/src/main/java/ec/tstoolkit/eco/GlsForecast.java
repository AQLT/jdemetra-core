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
package ec.tstoolkit.eco;

/**
 *
 * @author Jean Palate
 */
public class GlsForecast
{
    /**
         *
         */
    /**
         *
         */
    protected double[] m_ef, m_f;

    /**
         *
         */
    protected int m_nf = 0;

    /**
     * 
     */
    public GlsForecast()
    {
    }

    /**
     * 
     * @param idx
     * @return
     */
    public double forecast(final int idx) {
	return m_f[idx];
    }

    /**
     * 
     * @param idx
     * @return
     */
    public double forecastStdev(final int idx) {
	return m_ef[idx];
    }

    /**
     * 
     * @return
     */
    public int getForecastsCount() {
	return m_nf;
    }

}
