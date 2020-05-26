/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.timeseries.calendars;

import demetra.design.Development;
import java.util.function.IntToDoubleFunction;

/**
 *
 * @author palatej
 */
@Development(status = Development.Status.Alpha)
@lombok.Value
public class HolidayPattern {

    public static enum Shape {
        /**
         * 0 during all the period
         */
        Zero,
        /**
         * 1 during all the period
         */
        Constant,
        /**
         * 0 for the first point before the period, 1 for the last point
         */
        LinearUp,
        /**
         * 1 for the first point, 0 for the first point after the period
         */
        LinearDown;

        /**
         *
         * @param length Number of elements
         * @param buffer
         * @param pos
         */
        public void fill(final int length, final double[] buffer, final int pos) {
            switch (this) {
                case Constant:
                    for (int j = pos; j < pos + length; ++j) {
                        buffer[j] = 1;
                    }
                    break;
                case LinearDown:
                    double dstep = 1.0 / length;
                     for (int j = pos, k=0; j < pos + length; ++j, ++k) {
                        buffer[j] = 1-k*dstep;
                    }
                    break;
                case LinearUp:
                    double step = 1.0 / length;
                    for (int j = pos, k=length-1; j < pos+length; ++j, --k) {
                        buffer[j] = 1-k*step;
                    }
                    break;
                case Zero:
                    // nothing to do
                    
            }
        }
    }

    // Factories
    public static HolidayPattern of(int start, Shape shape, int length) {
        double[] w = new double[length];
        shape.fill(length, w, 0);
        return new HolidayPattern(start, w);
    }

    public static HolidayPattern of(int start, Shape shape0, int length0, Shape shape1, int length1) {
        double[] w = new double[length0 + length1];
        shape0.fill(length0, w, 0);
        shape1.fill(length1, w, length0);
        return new HolidayPattern(start, w);
    }

    public static HolidayPattern of(int start, Shape shape0, int length0, Shape shape1, int length1, Shape shape2, int length2) {
        double[] w = new double[length0 + length1 + length2];
        shape0.fill(length0, w, 0);
        shape1.fill(length1, w, length0);
        shape2.fill(length2, w, length0 + length1);
        return new HolidayPattern(start, w);
    }

    /**
     * Starting day (offset in comparison with the reference day).
     * (weight != 0)
     */
    int start;

    /**
     * Weights of the different days.
     */
    double[] weights;

    public double sum() {
        double s = 0;
        for (int i = 0; i < weights.length; ++i) {
            s += weights[i];
        }
        return s;
    }

    public HolidayPattern normalize() {
        double s = sum();
        if (s == 1) {
            return this;
        }
        double[] w = weights.clone();
        for (int i = 0; i < w.length; ++i) {
            w[i] /= s;
        }
        return new HolidayPattern(start, w);
    }
}
