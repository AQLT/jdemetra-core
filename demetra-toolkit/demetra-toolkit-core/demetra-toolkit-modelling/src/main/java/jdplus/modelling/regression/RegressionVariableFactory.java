/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdplus.modelling.regression;

import demetra.timeseries.regression.ITsVariable;
import demetra.timeseries.TimeSeriesDomain;
import demetra.timeseries.TsPeriod;
import jdplus.math.matrices.Matrix;

/**
 *
 * @author palatej
 * @param <X>
 */
public interface RegressionVariableFactory <X extends ITsVariable> {
    boolean fill(X var, TsPeriod start, Matrix buffer);
    
    <D extends TimeSeriesDomain> boolean fill(X var, D domain, Matrix buffer);
}