/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdplus.maths.functions.gsl.roots;

import org.junit.Test;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
public class SecantSolverTest {

    @Test
    public void test() {
        SolverTestUtils.testAll("Secant", SecantSolver::new);
    }
}
