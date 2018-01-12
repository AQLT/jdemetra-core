/*
 * Copyright 2017 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved 
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

import static demetra.timeseries.RegularDomain.of;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import static demetra.timeseries.TsUnit.HOUR;

/**
 *
 * @author Philippe Charles
 */
public class RegularDomainTest {

    @Test
    public void testFactories() {
        assertThat(of(feb2010, 2).getStartPeriod()).isEqualTo(feb2010);
        assertThat(of(feb2010, 2).getLength()).isEqualTo(2);
    }

    @Test
    public void testSplit() {
        assertThat(RegularDomain.splitOf(TsPeriod.yearly(2000), TsUnit.MONTH, true).getLength()).isEqualTo(12);
        assertThat(RegularDomain.splitOf(TsPeriod.yearly(2000), TsUnit.DAY, true).getLength()).isEqualTo(366);
        assertThatThrownBy(() -> RegularDomain.splitOf(TsPeriod.yearly(2000), TsUnit.WEEK, true)).isInstanceOf(TsException.class);
    }

    @Test
    public void testStart() {
        LocalDateTime x = feb2010.start();

        assertThat(of(feb2010, 0).start()).isEqualTo(x);
        assertThat(of(feb2010, 1).start()).isEqualTo(x);
        assertThat(of(feb2010, 2).start()).isEqualTo(x);
    }

    @Test
    public void testEnd() {
        LocalDateTime x = feb2010.start();

        assertThatThrownBy(() -> of(feb2010, 0).end()).isInstanceOf(IllegalStateException.class);
        assertThat(of(feb2010, 1).end()).isEqualTo(x.plusMonths(1));
        assertThat(of(feb2010, 2).end()).isEqualTo(x.plusMonths(2));
    }

    @Test
    public void testGetStartPeriod() {
        TsPeriod x = feb2010;

        assertThat(of(feb2010, 0).getStartPeriod()).isEqualTo(x);
        assertThat(of(feb2010, 1).getStartPeriod()).isEqualTo(x);
        assertThat(of(feb2010, 2).getStartPeriod()).isEqualTo(x);
    }

    @Test
    public void testGetEndPeriod() {
        TsPeriod x = feb2010;

        assertThatThrownBy(() -> of(feb2010, 0).getEndPeriod()).isInstanceOf(IllegalStateException.class);
        assertThat(of(feb2010, 1).getEndPeriod()).isEqualTo(x.next());
        assertThat(of(feb2010, 2).getEndPeriod()).isEqualTo(x.plus(2));
    }

    @Test
    public void testGetLastPeriod() {
        TsPeriod x = feb2010;

        assertThatThrownBy(() -> of(feb2010, 0).getLastPeriod()).isInstanceOf(IllegalStateException.class);
        assertThat(of(feb2010, 1).getLastPeriod()).isEqualTo(x);
        assertThat(of(feb2010, 2).getLastPeriod()).isEqualTo(x.plus(1));
    }

    @Test
    public void testContainsDateTime() {
        LocalDateTime x = feb2010.start();

        assertThat(of(feb2010, 0).contains(x)).isFalse();
        assertThat(of(feb2010, 1).contains(x)).isTrue();
        assertThat(of(feb2010, 1).contains(x.plusMinutes(10))).isTrue();
        assertThat(of(feb2010, 1).contains(x.minusMonths(1))).isFalse();
        assertThat(of(feb2010, 1).contains(x.plusMonths(-1))).isFalse();
        assertThat(of(feb2010, 2).contains(x.plusMonths(1))).isTrue();
    }

    @Test
    public void testContainsPeriod() {
        TsPeriod x = feb2010;

        assertThat(of(feb2010, 0).contains(x)).isFalse();
        assertThat(of(feb2010, 1).contains(x)).isTrue();
        assertThat(of(feb2010, 1).contains(x.plus(1))).isFalse();
        assertThat(of(feb2010, 1).contains(x.plus(-1))).isFalse();
        assertThat(of(feb2010, 2).contains(x.plus(1))).isTrue();

        assertThatThrownBy(() -> of(feb2010, 1).contains(x.withUnit(HOUR)))
                .isInstanceOf(TsException.class)
                .hasMessage(TsException.INCOMPATIBLE_FREQ);

        assertThat(of(feb2010, 1).contains(x.withReference(TsPeriod.EPOCH.plusMonths(1)))).isTrue();
    }

    @Test
    public void testIndexOfDateTime() {
        LocalDateTime x = feb2010.start();

        assertThat(of(feb2010, 0).indexOf(x)).isEqualTo(-1);
        assertThat(of(feb2010, 1).indexOf(x)).isEqualTo(0);
        assertThat(of(feb2010, 1).indexOf(x.plusMinutes(10))).isEqualTo(0);
        assertThat(of(feb2010, 1).indexOf(x.plusMonths(1))).isEqualTo(-1);
        assertThat(of(feb2010, 1).indexOf(x.plusMonths(-1))).isEqualTo(-1);
        assertThat(of(feb2010, 2).indexOf(x.plusMonths(1))).isEqualTo(1);
    }

    @Test
    public void testIndexOfPeriod() {
        TsPeriod x = feb2010;

        assertThat(of(feb2010, 0).indexOf(x)).isEqualTo(-1);
        assertThat(of(feb2010, 1).indexOf(x)).isEqualTo(0);
        assertThat(of(feb2010, 1).indexOf(x.plus(1))).isEqualTo(-1);
        assertThat(of(feb2010, 1).indexOf(x.plus(-1))).isEqualTo(-1);
        assertThat(of(feb2010, 2).indexOf(x.plus(1))).isEqualTo(1);

        assertThatThrownBy(() -> of(feb2010, 1).indexOf(x.withUnit(HOUR)))
                .isInstanceOf(TsException.class)
                .hasMessage(TsException.INCOMPATIBLE_FREQ);

        assertThat(of(feb2010, 1).indexOf(x.withReference(TsPeriod.EPOCH.plusMonths(1)))).isEqualTo(0);
    }

    @Test
    public void testMove() {
        assertThat(of(feb2010, 2).move(0)).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).move(1)).isEqualTo(of(feb2010.plus(1), 2));
        assertThat(of(feb2010, 2).move(-1)).isEqualTo(of(feb2010.plus(-1), 2));
    }

    @Test
    public void testRange() {
        assertThat(of(feb2010, 2).range(0, 0)).isEqualTo(of(feb2010, 0));
        assertThat(of(feb2010, 2).range(0, 1)).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).range(0, 2)).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).range(0, 3)).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).range(1, 3)).isEqualTo(of(feb2010.plus(1), 1));
        assertThat(of(feb2010, 2).range(2, 3)).isEqualTo(of(feb2010.plus(2), 0));

        assertThatThrownBy(() -> of(feb2010, 2).range(1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> of(feb2010, 2).range(-1, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> of(feb2010, 2).range(-2, -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testIntersection() {
        RegularDomain x = of(feb2010, 2);

        assertThat(of(feb2010, 2).intersection(x)).isEqualTo(x);
        assertThat(of(feb2010, 2).intersection(x.move(1))).isEqualTo(of(feb2010.plus(1), 1));
        assertThat(of(feb2010, 2).intersection(x.move(2))).isEqualTo(of(feb2010.plus(2), 0));
        assertThat(of(feb2010, 2).intersection(x.move(-1))).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).intersection(x.move(-2))).isEqualTo(of(feb2010, 0));

        assertThatThrownBy(() -> of(feb2010, 2).intersection(of(feb2010.withUnit(HOUR), 2)))
                .isInstanceOf(TsException.class)
                .hasMessage(TsException.INCOMPATIBLE_FREQ);

        assertThat(of(feb2010, 2).intersection(of(feb2010.withReference(TsPeriod.EPOCH.plusMonths(1)), 2))).isEqualTo(x);
    }

    @Test
    public void testUnion() {
        RegularDomain x = of(feb2010, 2);

        assertThat(of(feb2010, 2).union(x)).isEqualTo(x);
        assertThat(of(feb2010, 2).union(x.move(1))).isEqualTo(of(feb2010, 3));
        assertThat(of(feb2010, 2).union(x.move(2))).isEqualTo(of(feb2010, 4));
        assertThat(of(feb2010, 2).union(x.move(-1))).isEqualTo(of(feb2010.plus(-1), 3));
        assertThat(of(feb2010, 2).union(x.move(-2))).isEqualTo(of(feb2010.plus(-2), 4));

        assertThatThrownBy(() -> of(feb2010, 2).union(of(feb2010.withUnit(HOUR), 2)))
                .isInstanceOf(TsException.class)
                .hasMessage(TsException.INCOMPATIBLE_FREQ);

        assertThat(of(feb2010, 2).union(of(feb2010.withReference(TsPeriod.EPOCH.plusMonths(1)), 2))).isEqualTo(x);
    }

    @Test
    public void testSelect() {
        assertThat(of(feb2010, 2).select(TsPeriodSelector.all())).isEqualTo(of(feb2010, 2));

        assertThat(of(feb2010, 2).select(TsPeriodSelector.none())).isEqualTo(of(feb2010, 0));

        assertThat(of(feb2010, 2).select(TsPeriodSelector.first(0))).isEqualTo(of(feb2010, 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.first(1))).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.first(2))).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.first(3))).isEqualTo(of(feb2010, 2));

        assertThat(of(feb2010, 2).select(TsPeriodSelector.last(0))).isEqualTo(of(feb2010.plus(2), 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.last(1))).isEqualTo(of(feb2010.plus(1), 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.last(2))).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.last(3))).isEqualTo(of(feb2010, 2));

        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(0, 0))).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(1, 0))).isEqualTo(of(feb2010.plus(1), 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(0, 1))).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(1, 1))).isEqualTo(of(feb2010.plus(1), 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(0, 2))).isEqualTo(of(feb2010, 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(2, 0))).isEqualTo(of(feb2010.plus(2), 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.excluding(2, 2))).isEqualTo(of(feb2010, 0));

        LocalDateTime x = feb2010.start();

        assertThat(of(feb2010, 2).select(TsPeriodSelector.from(x))).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.from(x.plusMonths(1)))).isEqualTo(of(feb2010.plus(1), 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.from(x.plusMonths(2)))).isEqualTo(of(feb2010.plus(2), 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.from(x.plusMonths(-1)))).isEqualTo(of(feb2010, 2));

        assertThat(of(feb2010, 2).select(TsPeriodSelector.to(x))).isEqualTo(of(feb2010, 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.to(x.plusMonths(1)))).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.to(x.plusMonths(2)))).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.to(x.plusMonths(-1)))).isEqualTo(of(feb2010, 0));

        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x, x))).isEqualTo(of(feb2010, 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x, x.plusMonths(1)))).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x, x.plusMonths(2)))).isEqualTo(of(feb2010, 2));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x.plusMonths(1), x.plusMonths(2)))).isEqualTo(of(feb2010.plus(1), 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x.plusMonths(2), x.plusMonths(3)))).isEqualTo(of(feb2010.plus(2), 0));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x.plusMonths(-1), x.plusMonths(1)))).isEqualTo(of(feb2010, 1));
        assertThat(of(feb2010, 2).select(TsPeriodSelector.between(x.plusMonths(-2), x))).isEqualTo(of(feb2010, 0));
    }

    private final TsPeriod feb2010 = TsPeriod.monthly(2010, 2);
}