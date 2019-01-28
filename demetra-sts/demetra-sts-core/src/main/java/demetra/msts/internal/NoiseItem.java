/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.msts.internal;

import demetra.msts.IMstsParametersBlock;
import demetra.msts.ModelItem;
import demetra.msts.MstsMapping;
import demetra.msts.VarianceParameter;
import demetra.ssf.SsfComponent;
import demetra.sts.Noise;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author palatej
 */
public class NoiseItem extends AbstractModelItem {

    private final VarianceParameter v;

    public NoiseItem(String name, double var, boolean fixed) {
        super(name);
        this.v = new VarianceParameter(name + ".var", var, fixed, true);
    }

    @Override
    public void addTo(MstsMapping mapping) {
        mapping.add(v);
        mapping.add((p, builder) -> {
            double e = p.get(0);
            SsfComponent cmp = Noise.of(e);
            builder.add(name, cmp);
            return 1;
        });
    }

    @Override
    public List<IMstsParametersBlock> parameters() {
        return Collections.singletonList(v);
    }

}