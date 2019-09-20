/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.maths;

import demetra.design.Algorithm;
import nbbrd.service.ServiceDefinition;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
@Algorithm
@ServiceDefinition
public interface RealPolynomialRootsSolver {
    Complex[] roots(@NonNull RealPolynomial polynomial);
    
}
