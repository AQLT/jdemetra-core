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
package demetra.sarima.estimation;

import demetra.arima.regarima.internals.RegArmaModel;
import demetra.data.DataBlock;
import demetra.data.DoubleSequence;
import demetra.data.Doubles;
import demetra.design.Development;
import demetra.linearmodel.LeastSquaresResults;
import demetra.linearmodel.LinearModel;
import demetra.linearmodel.Ols;
import demetra.sarima.SarimaModel;
import demetra.sarima.SarimaSpecification;
import demetra.sarima.SarmaSpecification;

/**
 *
 * @author Jean Palate
 */
@Development(status = Development.Status.Alpha)
public class HannanRissanenInitializer implements IarmaInitializer {

    private static double EPS = 1e-9;

    private final boolean usedefault, stabilize, failifunstable;
    private DoubleSequence dy_;

    public boolean isStabilizing() {
        return stabilize;
    }

    public boolean isUsingDefaultIfFailed() {
        return usedefault;
    }

    public HannanRissanenInitializer(boolean stabilize, boolean usedefault, boolean failifunstable) {
        this.stabilize = stabilize;
        this.usedefault = usedefault;
        this.failifunstable = failifunstable;
    }

    /**
     * Initialize the parameters of a given RegArima model. The initialization
     * procedure is the following. If the regression model contains variables,
     * an initial set of residuals is computed by ols. If the ols routine fails,
     * null is returned.
     *
     * @param regs The initial model
     * @return The seasonal stationary arma model that contains the initial
     * parameters
     */
    @Override
    public SarimaModel initialize(RegArmaModel<SarimaModel> regs) {
        SarimaModel sarima = regs.getArma();
        SarimaSpecification spec = sarima.specification();
        SarmaSpecification dspec = spec.doStationary();
        try {
            if (spec.getParametersCount() == 0) {
                return SarimaModel.builder(dspec).build();
            }
            dy_ = null;
            LinearModel lm = regs.asLineaModel();
            HannanRissanen hr = new HannanRissanen();
            if (lm.getVariablesCount()>0){
                Ols ols = new Ols();
                LeastSquaresResults lsr = ols.compute(lm);
                lm.calcResiduals(lsr.getCoefficients());
            }else {
                dy_ = lm.getY();
            }
            if (Math.sqrt(Doubles.ssq(dy_) / dy_.length()) < EPS) {
                return SarimaModel.builder(spec).setDefault(0, 0).build();
            }

            if (!hr.process(dy_, dspec)) {
                if (usedefault) {
                    return SarimaModel.builder(spec).setDefault().build();
                } else {
                    return null;
                }
            }
            SarimaModel m = hr.getModel();
            if (!stabilize) {
                return m;
            }
            SarimaModel nm = SarimaMapping.stabilize(m);
            if (nm != m && failifunstable) {
                return null;
            }
            return nm;

        } catch (Exception ex) {
            if (usedefault) {
                return SarimaModel.builder(spec).setDefault().build();
            } else {
                return null;
            }
        }
    }
}
