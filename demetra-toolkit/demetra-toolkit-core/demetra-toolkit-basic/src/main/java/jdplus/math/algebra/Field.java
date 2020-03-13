/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdplus.math.algebra;

import demetra.design.Development;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 * @param <T>
 */
@Development(status = Development.Status.Exploratory)
public interface Field<T> {
    T zero();
    
    T plus(T a, T b);
    
    /**
     * y = -x <-> y + x = 0
     * @param x
     * @return 
     */
    T opposite(T x);
    
    /**
     * x = a - b <-> x = a + (- b)
     * @param a
     * @param b
     * @return 
     */
    T minus(T a, T b);

    T one();
    
    T times(T a, T b);
    /**
     * y = 1/x <-> y * x = 1
     * @param x
     * @return 
     */
    T inv(T x);
    
    /**
     * x = a / b <-> x = a * (1 / b)
     * @param a
     * @param b
     * @return 
     */
    T div(T a, T b);
}