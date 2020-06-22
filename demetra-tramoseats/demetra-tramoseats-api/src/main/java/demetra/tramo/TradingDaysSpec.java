/*
 * Copyright 2019 National Bank of Belgium
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
package demetra.tramo;

import demetra.design.Development;
import demetra.design.LombokWorkaround;
import demetra.timeseries.regression.RegressionTestType;
import demetra.timeseries.regression.TradingDaysType;
import demetra.util.Validatable;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 *
 * @author Jean Palate
 */
@Development(status = Development.Status.Beta)
@lombok.Value
public final class TradingDaysSpec implements Validatable<TradingDaysSpec> {

    private static final TradingDaysSpec NONE = new TradingDaysSpec(null, null, TradingDaysType.None,
            false, RegressionTestType.None, 0, AutoMethod.Unused, 0);

    public static TradingDaysSpec stockTradingDays(int w, RegressionTestType type) {
        return new TradingDaysSpec(null, null, TradingDaysType.TradingDays,
                false, type, w, AutoMethod.Unused, 0);
    }

    public static TradingDaysSpec none() {
        return NONE;
    }

    public static TradingDaysSpec userDefined(@NonNull String[] vars, RegressionTestType type) {
        return new TradingDaysSpec(null, vars, TradingDaysType.None,
                false, type, 0, AutoMethod.Unused, 0);
    }

    public static TradingDaysSpec automaticHolidays(String holidays, AutoMethod automaticMethod, double probabilityForFTest) {
        if (automaticMethod == AutoMethod.Unused) {
            throw new IllegalArgumentException();
        }
        return new TradingDaysSpec(holidays, null, TradingDaysType.TradingDays,
                true, RegressionTestType.None, 0, automaticMethod, probabilityForFTest);
    }

    public static TradingDaysSpec automatic(AutoMethod automaticMethod, double probabilityForFTest) {
        if (automaticMethod == AutoMethod.Unused) {
            throw new IllegalArgumentException();
        }
        return new TradingDaysSpec(null, null, TradingDaysType.TradingDays,
                true, RegressionTestType.None, 0, automaticMethod, probabilityForFTest);
    }

    public static TradingDaysSpec holidays(String holidays, TradingDaysType type, boolean leapyear, RegressionTestType regtype) {
        if (type == TradingDaysType.None) {
            throw new IllegalArgumentException();
        }
        return new TradingDaysSpec(holidays, null, type,
                leapyear, regtype, 0, AutoMethod.Unused, 0);
    }

    public static TradingDaysSpec td(TradingDaysType type, boolean leapyear, RegressionTestType regtype) {
        if (type == TradingDaysType.None) {
            throw new IllegalArgumentException();
        }
        return new TradingDaysSpec(null, null, type,
                leapyear, regtype, 0, AutoMethod.Unused, 0);
    }
    public static enum AutoMethod {
        Unused,
        FTest,
        WaldTest
    }

    public static final double DEF_PFTD = .01;

    private String holidays;
    private String[] userVariables;
    private TradingDaysType tradingDaysType;
    private boolean leapYear;
    private RegressionTestType regressionTestType;
    private int stockTradingDays;
    private AutoMethod automaticMethod;
    private double probabilityForFTest;


    @Override
    public TradingDaysSpec validate() throws IllegalArgumentException {
        if (probabilityForFTest <= 0 || probabilityForFTest > .1) {
            throw new IllegalArgumentException("Probability for FTest must be > 0 and < 0.1");
        }
        return this;
    }

    public boolean isUsed() {
        return isAutomatic() || tradingDaysType != TradingDaysType.None || userVariables != null || stockTradingDays != 0;
    }

    public boolean isDefined() {
        return userVariables != null || (stockTradingDays != 0 && regressionTestType == RegressionTestType.None)
                || ((leapYear || tradingDaysType != TradingDaysType.None)
                && (regressionTestType == RegressionTestType.None && automaticMethod == AutoMethod.Unused));
    }

    public boolean isAutomatic() {
        return automaticMethod != AutoMethod.Unused;
    }

    public boolean isStockTradingDays() {
        return stockTradingDays != 0;
    }

    public boolean isValid() {
        if (isStockTradingDays() || isAutomatic()) {
            return true;
        }
        if (regressionTestType.isUsed()) {
            return tradingDaysType != TradingDaysType.None && leapYear;
        }
        if (tradingDaysType == TradingDaysType.None) {
            return !leapYear;
        }
        return true;
    }

    public boolean isTest() {
        return regressionTestType.isUsed();
    }

    public boolean isDefault() {
        return this.equals(NONE);
    }

}