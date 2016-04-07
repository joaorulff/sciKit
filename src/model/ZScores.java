package model;



public class ZScores {
	
	
	private Float x;
	private Float y;
	private Float integralValue;
	
	
	public ZScores(float zScore, float y){
		
		
		this.x = zScore;
		this.setY(y);
		this.calculateIntegral();
		
	}
	
	public ZScores(float zScore){
		
		this.x = zScore;
		this.setY(0.0f);
		this.calculateIntegral();
		
	}
	
	
	
	
	private void calculateIntegral(){
		
		double area = 0;
		int n = 40;
		
		double deltaX = this.x/n;
		
		for (int k = 1; k <= n; k++) {
			
			area += f((2*k-1)*deltaX/2) * deltaX;
			
		}
		
		
		this.integralValue = (float)(0.5 + area);
	}


	
	private double f(double x){
		
		
		double constant = (1/Math.sqrt(2*Math.PI));
		double exponent = -Math.pow(x, 2)/2;
		
		
		return constant*Math.pow(Math.E, exponent);
		
		

	}
	
	public float getIntegralValue (){
		
		return this.integralValue;
	}
	
	

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}
	

}