import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int[][] gridStatus;
	private int[][] gridId;
	private int N; 
	private int count;
	private int topSite;
	private int bottomSite;
	private WeightedQuickUnionUF weightedQUF;
	
	
	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n){
		if (n <= 0) {
			   throw new IllegalArgumentException("n must be at least 1.");
		}
		
		N = n;
		count = 0;
		weightedQUF = new WeightedQuickUnionUF(N*N + 2);
		gridId = new int[N+1][N+1];
		gridStatus = new int[N+1][N+1];
		
		for(int i = 1; i <= N; i++) {
			
			for(int j = 1; j <= N; j++){
				gridId[i][j] = (i-1) * N + j-1;
				gridStatus[i][j] = 0; 
			}
		}
		topSite = N*N;
		bottomSite = N*N + 1;
	}
	
    // opens the site (row, col) if it is not open already
	public void open(int row, int col){
		if (row <= 0 || col <= 0 || row > N || col > N) {
			throw new IllegalArgumentException("Rows and columns must be bigger than 0 or smaller than N.");
		}
			
		if(isOpen(row, col)) {
			
		}
		else{
			gridStatus[row][col] = 1;
			if(row != 1 && isOpen(row - 1, col)){
				if(!weightedQUF.connected(gridId[row][col], gridId[row - 1][col])){
					weightedQUF.union(gridId[row][col], gridId[row - 1][col]);
				}	
			}else if (row == 1){
				if(!weightedQUF.connected(gridId[row][col], topSite)){
					weightedQUF.union(gridId[row][col], topSite);
				}	
			}
			if(row != N && isOpen(row + 1, col)){	
				if(!weightedQUF.connected(gridId[row][col], gridId[row + 1][col])){
					weightedQUF.union(gridId[row][col], gridId[row + 1][col]);
				}
			}else if(row == N){
				if(!weightedQUF.connected(gridId[row][col], bottomSite)){
					weightedQUF.union(gridId[row][col], bottomSite);
				}
			}
			if(col != 1 && isOpen(row, col - 1)){
				
				if(!weightedQUF.connected(gridId[row][col], gridId[row][col - 1])){
					weightedQUF.union(gridId[row][col], gridId[row][col - 1]);
				}
			}
			if(col != N && isOpen(row, col + 1)){
				if(!weightedQUF.connected(gridId[row][col], gridId[row][col + 1])){
					weightedQUF.union(gridId[row][col], gridId[row][col + 1]);
				}
			}
			count++;
		}
	}
	
	
	// is the site (row, col) open?
	public boolean isOpen(int row, int col){ 
		if (row <= 0 || col <= 0 || row > N || col > N) {
			throw new IllegalArgumentException("Rows and columns must be bigger than 0 or smaller than N.");
		}
		return gridStatus[row][col] == 1;
	}
	
	
	// is the site (row, col) full?
    public boolean isFull(int row, int col){ 
    	if (row <= 0 || col <= 0 || row > N || col > N) {
			throw new IllegalArgumentException("Rows and columns must be bigger than 0 or smaller than N.");
		}
    	return weightedQUF.connected(gridId[row][col], topSite);
    }
    
    
    // returns the number of open sites
    public int numberOfOpenSites(){
		return count;
    }
    
    // does the system percolate?
	public boolean percolates(){ 
		return weightedQUF.connected(topSite, bottomSite);
	}
}
