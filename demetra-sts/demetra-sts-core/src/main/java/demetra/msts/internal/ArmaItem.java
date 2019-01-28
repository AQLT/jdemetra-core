/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.msts.internal;

import demetra.arima.ArimaModel;
import demetra.arima.ssf.SsfArima;
import demetra.maths.linearfilters.BackFilter;
import demetra.maths.polynomials.Polynomial;
import demetra.msts.IMstsParametersBlock;
import demetra.msts.ModelItem;
import demetra.msts.MstsMapping;
import demetra.msts.StablePolynomial;
import demetra.msts.VarianceParameter;
import demetra.ssf.StateComponent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author palatej
 */
public class ArmaItem extends AbstractModelItem{

    private final StablePolynomial par, pma;
    private final VarianceParameter v;

    public ArmaItem(final String name, double[] ar, double[] ma, double var, boolean fixed) {
        super(name);
        int nar = ar == null ? 0 : ar.length, nma = ma == null ? 0 : ma.length;
        if (nar > 0) {
            par = new StablePolynomial(name + ".ar", ar, fixed);
        } else {
            par = null;
        }
        if (nma > 0) {
            pma = new StablePolynomial(name + ".ma", ma, fixed);
        } else {
            pma = null;
        }
        v = new VarianceParameter(name + ".var", var, true, true);
    }

    @Override
    public void addTo(MstsMapping mapping) {
        if (par != null) {
            mapping.add(par);
        }
        if (pma != null) {
            mapping.add(pma);
        }
        mapping.add(v);
        mapping.add((p, builder) -> {
            BackFilter bar = BackFilter.ONE, bma = BackFilter.ONE;
            int pos = 0;
            if (par != null) {
                int nar=par.getDomain().getDim();
                Polynomial ar = Polynomial.valueOf(1, p.extract(0, nar).toArray());
                bar = new BackFilter(ar);
                pos += nar;
            }
            if (pma != null) {
                int nma=pma.getDomain().getDim();
                Polynomial ma = Polynomial.valueOf(1, p.extract(0, nma).toArray());
                bma = new BackFilter(ma);
                pos += nma;
            }
            double n = p.get(pos++);
            ArimaModel arima = new ArimaModel(bar, BackFilter.ONE, bma, n);
            StateComponent cmp = SsfArima.componentOf(arima);
            builder.add(name, cmp, null);
            return pos;
        });
    }

    @Override
    public List<IMstsParametersBlock> parameters() {
        return Arrays.asList(par, pma, v);
    }
}