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
package ec.tss.disaggregation.documents;

import ec.benchmarking.simplets.TsDisaggregation;
import ec.tstoolkit.Parameter;
import ec.tstoolkit.ParameterType;
import ec.tstoolkit.algorithm.IProcResults;
import ec.tstoolkit.algorithm.ProcessingInformation;
import ec.tstoolkit.arima.estimation.LikelihoodStatistics;
import ec.tstoolkit.data.DataBlock;
import ec.tstoolkit.data.IReadDataBlock;
import ec.tstoolkit.eco.DiffuseConcentratedLikelihood;
import ec.tstoolkit.information.InformationMapper;
import ec.tstoolkit.maths.matrices.Matrix;
import ec.tstoolkit.maths.realfunctions.IParametricMapping;
import ec.tstoolkit.ssf.ISsf;
import ec.tstoolkit.timeseries.simplets.TsData;
import ec.tstoolkit.timeseries.simplets.TsDomain;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jean Palate
 */
public class DisaggregationResults implements IProcResults {

    public static final String DISAGGREGATION = "disaggregation",
            LDISAGGREGATION = "lower bound", UDISAGGREGATION = "upper bound",
            EDISAGGREGATION = "disggregationError", RESIDUALS = "residuals",
            REGEFFECT = "regression effect", SMOOTHING = "smoothing effect";
    private final TsDisaggregation<? extends ISsf> result;
    private final int nindicators;

    public DisaggregationResults(TsDisaggregation<? extends ISsf> disaggregation, int nindics) {
        result = disaggregation;
        this.nindicators = nindics;
    }

    public DiffuseConcentratedLikelihood getLikelihood() {
        return result.getLikelihood();
    }

    public LikelihoodStatistics getLikelihoodStatistics() {
        DiffuseConcentratedLikelihood ll = result.getLikelihood();
        IParametricMapping<? extends ISsf> mapping = result.getMapping();
        return LikelihoodStatistics.create(ll, ll.getN(), mapping == null ? 0 : mapping.getDim(), 0);
    }

    public Parameter getEstimatedParameter() {
        Matrix i = result.getObservedInformation();
        if (i == null) {
            return null;
        }
        IParametricMapping<ISsf> mapping = (IParametricMapping<ISsf>) result.getMapping();
        IReadDataBlock p = mapping.map(result.getEstimatedSsf());
        Parameter x = new Parameter(p.get(0), ParameterType.Estimated);
        x.setStde(Math.sqrt(1 / i.get(0, 0)));
        return x;
    }

    public ISsf getEstimatedSsf() {
        return result.getEstimatedSsf();
    }

    @Override
    public boolean contains(String id) {
        synchronized (mapper) {
            return mapper.contains(id);
        }
    }

    @Override
    public Map<String, Class> getDictionary() {
        // TODO
        LinkedHashMap<String, Class> map = new LinkedHashMap<>();
        mapper.fillDictionary(null, map);
        return map;
    }

    @Override
    public <T> T getData(String id, Class<T> tclass) {
        synchronized (mapper) {
            if (mapper.contains(id)) {
                return mapper.getData(this, id, tclass);
            } else {
                return null;
            }
        }
    }
    
    @Override
    public List<ProcessingInformation> getProcessingInformation() {
        return Collections.EMPTY_LIST;
    }

    public static void fillDictionary(String prefix, Map<String, Class> map){
        mapper.fillDictionary(prefix, map);
    } 

    // MAPPERS
    public static <T> void addMapping(String name, InformationMapper.Mapper<DisaggregationResults, T> mapping) {
        synchronized (mapper) {
            mapper.add(name, mapping);
        }
    }
    private static final InformationMapper<DisaggregationResults> mapper = new InformationMapper<>();

    static {
        mapper.add(DISAGGREGATION, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {

                return source.result.getSmoothedSeries();
            }
        });

        mapper.add(EDISAGGREGATION, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {
                return source.result.getSmoothedSeriesVariance().sqrt();
            }
        });

        mapper.add(LDISAGGREGATION, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {
                TsData s = source.result.getSmoothedSeries();
                TsData e = source.result.getSmoothedSeriesVariance().sqrt();
                e.getValues().mul(-2);
                return TsData.add(s, e);
            }
        });

        mapper.add(UDISAGGREGATION, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {
                TsData s = source.result.getSmoothedSeries();
                TsData e = source.result.getSmoothedSeriesVariance().sqrt();
                e.getValues().mul(2);
                return TsData.add(s, e);
            }
        });

        mapper.add(RESIDUALS, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {
                return source.result.getFullResiduals();
            }
        });

        mapper.add(SMOOTHING, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {
                if (source.nindicators == 0) {
                    return null;
                }
                TsData y = source.result.getSmoothedSeries();
                TsData regs = source.getData(REGEFFECT, TsData.class);
                return TsData.subtract(y, regs);
            }
        });

        mapper.add(REGEFFECT, new InformationMapper.Mapper<DisaggregationResults, TsData>(TsData.class) {
            @Override
            public TsData retrieve(DisaggregationResults source) {
                if (source.nindicators == 0) {
                    return null;
                }
                TsDomain dom = source.result.getData().hEDom;
                DataBlock d = new DataBlock(dom.getLength());
                double[] b = source.getLikelihood().getB();
                Matrix matrix = source.result.getModel().getX().all().matrix(dom);
                for (int i = b.length - source.nindicators, j = matrix.getColumnsCount() - source.nindicators;
                        i < b.length; ++i, ++j) {
                    d.addAY(b[i], matrix.column(j));
                }
                return new TsData(dom.getStart(), d);
            }
        });
    }
}
