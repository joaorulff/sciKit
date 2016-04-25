package model;

public class NormalSegment {
	
	
	
	double lowerBound;
	double upperBound;
	int counter;
	double qValueCol;
	
	public NormalSegment(double lowerBound, double upperBound){
		
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		
	}
	
	
	public double getLowerBound() {
		return lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}
	
	public int getCounter(){
		return this.counter;
	}
	
	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}


	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}


	public void setqValue(double qValue) {
		this.qValueCol = qValue;
	}


	public boolean isInside(double x){
		
		if(x <= upperBound && x >= lowerBound){
			return true;
		}
		return false;
	}
	
	public void incrementCounter(){
		
		this.counter++;
	}
	
	public double calculateQvalue(int expected){
		
		this.qValueCol = Math.pow(expected - this.counter, 2)/expected;
		return Math.pow(expected - this.counter, 2)/expected;
		
	}


	public double getqValueCol() {
		return qValueCol;
	}


	public void setqValueCol(double qValueCol) {
		this.qValueCol = qValueCol;
	}

	
	
	

}
