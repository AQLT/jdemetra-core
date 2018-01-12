/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.maths;


/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
public interface ComplexType {

    double getRe();

    double getIm();

    double arg();

    double abs();

    public static ComplexType of(double re, double im) {
        return new LightComplex(re, im);
    }

    @lombok.Value
    @lombok.AllArgsConstructor(access=lombok.AccessLevel.PACKAGE)
    static class LightComplex implements ComplexType {

        private double re, im;

        @Override
        public double arg() {
            return Math.atan2(im, re);
        }

        @Override
        public double abs() {
            double xa = Math.abs(re), xb = Math.abs(im);
            double w, z;
            if (xa > xb) {
                w = xa;
                z = xb;
            } else {
                w = xb;
                z = xa;
            }
            if (z == 0.0) {
                return w;
            } else {
                double zw = z / w;
                return w * Math.sqrt(1.0 + zw * zw);
            }
        }

    }
}