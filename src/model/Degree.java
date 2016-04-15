/*
+----------------------------------------------------------------------------+
| Monmouth University Spring 2016 SE 403-01 |
+----------------------------------------------------------------------------+
| Program Name: Assignment 5 												|
| Author: Phil DiMarco and Joao Rulff									|
| Version: 1.0 																|
| Date: 04/07/2016															|
| Synopsis: 																|
| This program will calculate the mean, standard deviation, correlation and
  variance of a set of values provide to the program by a file.				|
| References: Assingments 1-4												|
+----------------------------------------------------------------------------+
*/


package model;

public class Degree {
	
	public double function (double dof, double x) {    
        double numerator = gammaFunction((dof + 1)/2);
        double denominator = gammaFunction(dof/2) * Math.pow((dof * Math.PI), .5); 
        double fraction = numerator/denominator;
        double result = fraction * Math.pow((1 + (Math.pow(x, 2)/dof)), -((dof+1)/2));
        return result;   
    }

    //Integrate the T Distribution function starting in x
    private double integrationOfTDist (double dof, double x){
        int n = 4;
        double threshold = 0.001;
        double newEstimate = 0;
        double oldEstimate;
        double deltaX;

        deltaX = Math.abs(x) / n;


        for(int k = 1; k < n ; k++){
            double i = (((2 * k - 1) / 2) * deltaX);
            double j = function(dof, i);
            newEstimate += (j * deltaX);
        }      
    
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
     
        return newEstimate;        
    }

    //T distribution function calculation
    public double tDistribution (double p, double dof) {
        double x = 1;
        double d = 0.5;
        boolean errorPositive = true;
        double testP = integrationOfTDist(dof, x);

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

        testP = integrationOfTDist(dof, x);

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
            testP = integrationOfTDist(dof, x);
        }
        return x;
    }

    //This method calculates the gamma function for the T Distribution
    
    private double gammaFunction (double g) { 
        
    	if (g==1) {
            return 1;
        } else if (g ==0.5) {
            return Math.sqrt(Math.PI);
        } else {
        	//System.out.println(x);
            return (g-1)*gammaFunction(g-1);
        }
    } 
}