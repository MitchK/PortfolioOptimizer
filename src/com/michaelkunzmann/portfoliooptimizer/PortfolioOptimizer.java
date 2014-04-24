package com.michaelkunzmann.portfoliooptimizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Optimizes your asset portfolio by using a maximum saving algorithm
 * 
 * @author Michael Kunzmann
 *
 */
public class PortfolioOptimizer {
	

	public static final double DEFAULT_STEP = 0.00001;
	public static final double DEFAULT_MIN_SAVINGS_DELTA = 0.0000000000001;
	
	
	/**
	 * Calculate the minimum-variance portfolio for the given standard deviations and correlation coefficients
	 * 
	 * @param standardDeviations Standard deviations of your assets
	 * @param corrCoeffs Correlation coefficient matrix
	 * @return
	 */
	public double[] mvp(final double[] standardDeviations, final double[][] corrCoeffs) {
		return mvp(standardDeviations, corrCoeffs, DEFAULT_STEP, DEFAULT_MIN_SAVINGS_DELTA);
	}
	
	/**
	 * Calculate the minimum-variance portfolio for the given standard deviations and correlation coefficients
	 * 
	 * @param standardDeviations Standard deviations of your assets
	 * @param corrCoeffs Correlation coefficient matrix
	 * @param step Optimization step per iteration
	 * @param minSavingsDelta Minimum saving per iteration
	 * @return
	 */
	public double[] mvp(final double[] standardDeviations, final double[][] corrCoeffs, final double step, final double minSavingsDelta) {
		
		// Set start weights
		double[] bestWeights = new double[standardDeviations.length];
		double startWeight = 1.0 / ((double) bestWeights.length);
		
		for (int i = 0; i < bestWeights.length; i++) {
			bestWeights[i] = startWeight;
		}
		
		double[] bestWeightsBefore = null;
		
		// Optimize
		while (weightsEqualOne(bestWeights) && weightsGreaterZero(bestWeights)) {
			
			double varianceOld = getVariance(bestWeights, standardDeviations, corrCoeffs);
			Double maxSavings = null;
			
			// Choose the asset to increase the % for
			for (int i = 0; i < bestWeights.length; i++) {
				
				double[] newWeights = bestWeights.clone();
				
				for (int z = 0; z < bestWeights.length; z++) {
					if (z == i) {
						newWeights[z] += step;
					}
					else {
						newWeights[z] -= (step / ((double) bestWeights.length - 1));
					}
					
				}
				
				double varianceNew = getVariance(newWeights, standardDeviations, corrCoeffs);
				
				
				double savings = varianceOld - varianceNew;
				
				if (savings >= minSavingsDelta) {
					if (maxSavings == null) {
						maxSavings = savings;
						bestWeightsBefore = bestWeights.clone();
						bestWeights = newWeights.clone();
					}
					else if (savings >= maxSavings) {
						bestWeightsBefore = bestWeights.clone();
						bestWeights = newWeights.clone();
					}
				}
			}
			
			if (maxSavings == null) {
				break;
			}
		}
		
		
		return bestWeightsBefore == null ? bestWeights : bestWeightsBefore;
	}
	
	/**
	 * Get standard deviation for a given portfolio
	 * 
	 * @param w
	 * @param s
	 * @param k
	 * @return
	 */
	public double getStdDev(double[] w, double[] s, double[][] k) {
		return Math.sqrt(getVariance(w, s, k));
	}

	/**
	 * Get variance for a given portfolio
	 * 
	 * @param w
	 * @param s
	 * @param k
	 * @return
	 */
	public double getVariance(double[] w, double[] s, double[][] k) {

		double sum = 0.0;
		
		for (int i = 0; i < s.length; i++) {
			sum += Math.pow(w[i], 2.0) * Math.pow(s[i], 2.0);
		}
		
		final List<String > combinationsAlreadyDid = new ArrayList<>();
		
		for (int i = 0; i < k.length; i++) {
			for (int j = 0; j < k[i].length; j++) {
				
				char[] chars = (i + "" + j).toCharArray();
				Arrays.sort(chars);
				
				boolean alreadyDid = combinationsAlreadyDid.contains(new String(chars));
				
				if (i != j && !alreadyDid) {
					sum += 2 * w[i] * w[j] * s[i] * s[j] * k[i][j];
					
					combinationsAlreadyDid.add(new String(chars));
				}
			}
		}
		return sum;
	}
	
	private boolean weightsEqualOne(double[] w) {
		double sum = 0.0;
		
		for (double ww : w) {
			sum += ww;
		}
		
		
		return sum >= 0.99 && sum <= 1.01;
	}
	
	private boolean weightsGreaterZero(double[] w) {
		for (double ww : w) {
			if (ww < 0.0) {
				return false;
			}
		}
		
		return true;
	}
}
