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
package demetra.regarima;

import demetra.data.Parameter;
import demetra.design.Development;
import demetra.design.LombokWorkaround;
import demetra.timeseries.regression.IOutlier;
import demetra.timeseries.regression.InterventionVariable;
import demetra.timeseries.regression.Ramp;
import demetra.timeseries.regression.TsContextVariable;
import demetra.timeseries.regression.UserVariable;
import java.util.*;
import demetra.util.Validatable;

/**
 *
 * @author Jean Palate, Mats Maggi
 */
@Development(status = Development.Status.Beta)
@lombok.Value
@lombok.Builder(toBuilder = true, builderClassName = "Builder", buildMethodName = "buildWithoutValidation")
public final class RegressionSpec implements Validatable<RegressionSpec> {

    public static final double DEF_AICCDIFF = 0;

    private static final RegressionSpec DEFAULT = RegressionSpec.builder().build();

    private double aicDiff;

    private boolean mean;
    @lombok.NonNull
    private TradingDaysSpec tradingDays;
    @lombok.NonNull
    private EasterSpec easter;
    @lombok.Singular
    private List<IOutlier> outliers;
    @lombok.Singular
    private List<TsContextVariable> userDefinedVariables;
    @lombok.Singular
    private List<InterventionVariable> interventionVariables;
    @lombok.Singular
    private List<Ramp> ramps;

    @lombok.Singular
    private Map<String, Parameter[]> coefficients;

    @LombokWorkaround
    public static Builder builder() {
        return new Builder()
                .aicDiff(DEF_AICCDIFF)
                .easter(EasterSpec.builder().build())
                .coefficients(new LinkedHashMap<>())
                .tradingDays(TradingDaysSpec.none());
    }

    public boolean isUsed() {
        return tradingDays.isUsed() || easter.isUsed()
                || !outliers.isEmpty() || !userDefinedVariables.isEmpty()
                || !ramps.isEmpty() || !interventionVariables.isEmpty();
    }

    public int getOutliersCount() {
        return outliers.size();
    }

    public int getRampsCount() {
        return ramps.size();
    }

    public int getInterventionVariablesCount() {
        return interventionVariables.size();
    }

    public int getUserDefinedVariablesCount() {
        return userDefinedVariables.size();
    }

    public boolean isDefault() {
        return this.equals(DEFAULT);
    }

    @Override
    public RegressionSpec validate() throws IllegalArgumentException {
        tradingDays.validate();
        return this;
    }

    public static class Builder implements Validatable.Builder<RegressionSpec> {

    }

}
