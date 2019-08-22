import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
	
	private double[] gridFraction;
	private int T;
	private double numberOfMean;
	private double numberOfStddev;
	
 
    // perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials){   
		
		if (n <= 0 || trials<= 0) {
			throw new java.lang.IllegalArgumentException("n must be greater than 0 and trials must be greater than 0");
		}
		
   	 	T = trials;
   	 	int row, col;
   	 	gridFraction = new double[trials];
   	 	
   	 	for(int i = 0; i < trials; i++){
   	 		Percolation percolation = new Percolation(n);
   	 		
   	 		while(!percolation.percolates()){
   	 			row = StdRandom.uniform(n) + 1;
   	 			col = StdRandom.uniform(n) + 1;
   	 			percolation.open(row, col);
   	 		}
   	 		
   	 		gridFraction[i] = percolation.numberOfOpenSites()*1.0/(n*n);
   	 	}
	}
	

	// sample mean of percolation threshold
	public double mean(){
		numberOfMean = StdStats.mean(gridFraction);
		return numberOfMean;
	}

	// sample standard deviation of percolation threshold
	public double stddev(){
		numberOfStddev = StdStats.stddev(gridFraction);
		return numberOfStddev;  
	} 
 
	// low endpoint of 95% confidence interval
	public double confidenceLo(){
		return numberOfMean - confidenceFormula();
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi(){
		return numberOfMean + confidenceFormula();
	}
	
	private double confidenceFormula() {
		return 1.96*Math.sqrt(numberOfStddev)/Math.sqrt(T);
	}
 
	public static void main(String[] args) {
		int n = StdIn.readInt();
		int T = StdIn.readInt();
		PercolationStats perStats = new PercolationStats(n, T);
		StdOut.printf("mean                    = %.8f\n", perStats.mean());
		StdOut.printf("stddev                  = %.8f\n", perStats.stddev());
		StdOut.printf("95%% confidence interval = [%.16f, %.16f]", perStats.confidenceLo(),perStats.confidenceHi());
	}
}
