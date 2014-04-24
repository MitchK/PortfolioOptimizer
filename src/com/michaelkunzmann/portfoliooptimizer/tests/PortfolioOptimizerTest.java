package com.michaelkunzmann.portfoliooptimizer.tests;

import java.util.Arrays;

import org.junit.Test;

import com.michaelkunzmann.portfoliooptimizer.PortfolioOptimizer;

public class PortfolioOptimizerTest {

	@Test
	public void test() {
		PortfolioOptimizer opt = new PortfolioOptimizer();
		
		final double[] stddevs = {0.2, 0.3};
		final double[][] corrCoeffs = {
				{1.0, 0.0},
				{0.0, 1.0}
		};
		
		System.out.println("Stdevs input: " + Arrays.toString(stddevs));
		System.out.println("CorrCoeffs input: " + Arrays.toString(corrCoeffs));
		
		System.out.println("Risk before: " + opt.getVariance(new double[] {0.5, 0.5}, stddevs, corrCoeffs));
		
		double[] riskMinimalWeights = opt.mvp(stddevs, corrCoeffs);
		
		System.out.println("New optimum weights" + Arrays.toString(riskMinimalWeights));
		
		System.out.println("Risk after: " + opt.getVariance(riskMinimalWeights, stddevs, corrCoeffs));
		
		
	}

}
