package model;

public class Degree {
	
	public double function (double dof, double x) {    
        double numerator = gamma((dof + 1)/2);
        double denominator = gamma(dof/2) * Math.pow((dof * Math.PI), .5); 
        double fraction = numerator/denominator;
        double result = fraction * Math.pow((1 + (Math.pow(x, 2)/dof)), -((dof+1)/2));
        return result;   
    }

    //integrate from zero to x
    private double integrateT (double dof, double x){
        int n = 4;
        double threshold = 0.00001;
        double newEstimate = 0;
        double oldEstimate;
        double deltaX;

        deltaX = Math.abs(x) / n;

        //for loop to find first estimate
        for(int k = 1; k < n ; k++){
            double i = (((2 * k - 1) / 2) * deltaX);
            double j = function(dof, i);
            newEstimate += (j * deltaX);
        }      
        //do while loop until estimates are equal
        do {
            oldEstimate = newEstimate;
            newEstimate = 0;
            n = n * 2;
            deltaX = Math.abs(x) / n;

            for(int k = 1; k < n ; k++){
                double i = (((2 * k - 1) / 2) * deltaX);
                double j = function(dof, i);
                newEstimate += j * deltaX;
            }
        } while(Math.abs(oldEstimate - newEstimate) >= threshold); 
        //we have p testAnswer     
        return newEstimate;        
    }

    //power degrees of freedom test x value beginning at 1
    public double tdist (double p, double dof) {
        double x = 1;
        double d = 0.5;
        boolean errorPositive = true;
        double testP = integrateT(dof, x);

        if (Math.abs(testP - p) <= 0.001) {
            System.out.println(testP);
            return x;
        } else if (testP < p) {
            x += d;
            errorPositive = false;
        } else if (testP > p) {
            x -= d;
            errorPositive = true;
        }

        testP = integrateT(dof, x);

        while(Math.abs(testP - p) > 0.001){
            if (testP < p) {
                if(errorPositive == true){
                    d = d/2;
                    errorPositive = false;
                }
                x += d;
            } else if (testP > p) {
                if(errorPositive == false){
                    d = d/2;
                    errorPositive = true;
                }
                x -= d;
            }
            testP = integrateT(dof, x);
        }
        return x;
    }

    //recursive gamma function
    
    private double gamma (double x) { 
        
    	if (x==1) {
            return 1;
        } else if (x ==0.5) {
            return Math.sqrt(Math.PI);
        } else {
        	//System.out.println(x);
            return (x-1)*gamma(x-1);
        }
    } 
}