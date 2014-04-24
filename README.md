PortfolioOptimizer
==================

This library solves financial optimization problems.

Minimum Variance Portfolio
--------------------------

Determines the risk-minimal investment weights of N assets, such as shares.

Let's say you have share A and share B  with the following standard deviations:

```
Share A: 0.2
Share B: 0.3
```
And the following correlation coefficient matrix:

```
    A    B
A [1.0, 0.0]
B [0.0, 1.0]
```

Then, the risk-minimal investment weights are:

```
Share A: ~69% 
Share B: ~31%
```

Usage:
```
final double[] stddevs = {0.2, 0.3};
final double[][] corrCoeffs = {
		{1.0, 0.0},
		{0.0, 1.0}
};
double[] riskMinimalWeights = opt.mvp(stddevs, corrCoeffs);
```


Other stuff to follow...
------------------

...
