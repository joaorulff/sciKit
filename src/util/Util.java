/*
+----------------------------------------------------------------------------+
| Monmouth University Spring 2016 SE 403-01 |
+----------------------------------------------------------------------------+
| Program Name: Assignment 5 												|
| Author: João Lucas Rulff														|
| Version: 1.0 																|
| Date: 04/07/2016															|
| Synopsis: 																|
| This program will calculate the mean, standard deviation, correlation and
  variance of a set of values provide to the program by a file.				|
| References: Assingments 1-4												|
+----------------------------------------------------------------------------+
*/

package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import model.ZScores;

public class Util {
	
	
	public static ArrayList<Double> readValuesFromFile(File file) throws FileNotFoundException{
		
		
		Scanner reader = new Scanner(file);
		ArrayList<Double> listOfValues = new ArrayList<>();
		
		while(reader.hasNext())
		
		{
		
			listOfValues.add(Double.parseDouble(reader.next()));
			
		}
		
		reader.close();
		
		return listOfValues;
		
	}
	
	public static ArrayList<ZScores> readValuesFromFileDouble(File file) throws FileNotFoundException{
		
		
		Scanner reader = new Scanner(file);
		ArrayList<ZScores> listOfValues = new ArrayList<>();
		
		while(reader.hasNext())
		
		{
		
			listOfValues.add(new ZScores(Float.parseFloat(reader.next()), Float.parseFloat(reader.next())));
			
		}
		
		reader.close();
		
		return listOfValues;
	}
	
	
	public static double calculateMean(ArrayList<Double> list){
		
		double acumulator = 0;
		for (Double element : list) {
			acumulator += element;
		}
		
		return acumulator/list.size();
	}
	
	
	public static double calculateStandardDeviation(ArrayList<Double> list){
		
		
		double acumulator = 0;
		double mean = calculateMean(list);
		
		for (Double element : list) {
			acumulator += Math.pow(element - mean, 2);
			
		}
		
		return Math.sqrt(acumulator/(list.size()-1));
	}
	
	
	public static double calculateVariance(ArrayList<Double> list){
		
		double mean = calculateMean(list);
		double acumulator = 0;
		for (Double number : list) {
			acumulator += Math.pow((number-mean), 2);
		}
		
		return acumulator/(list.size()-1);
	}
	
	public static double calculateCorrelation(ArrayList<ZScores> list){
		
		double sumOfx = 0, sumOfy = 0, sumOfxTimesY = 0, sumOfxSquared = 0, sumOfySquared = 0;
		
		for (ZScores x : list) {
			sumOfxTimesY += x.getX()*x.getY();
			sumOfx += x.getX();
			sumOfy += x.getY();
			sumOfxSquared += Math.pow(x.getX(), 2);
			sumOfySquared += Math.pow(x.getY(), 2);
		}
		
		
		double numerator = list.size()*sumOfxTimesY - sumOfx*sumOfy;
		double denominator = Math.sqrt(  (list.size()*sumOfxSquared - Math.pow(sumOfx, 2)) * (list.size()*sumOfySquared - Math.pow(sumOfy, 2))  );
		
		return numerator/denominator;
	}
	
	public static double calculateBeta1(ArrayList<ZScores> list){
		
		double sumXandY = 0, sumXSquared = 0, sumX = 0, sumY = 0;
		
		for (ZScores x : list) {
			
			sumXandY += x.getXTimesY();
			sumXSquared += x.getXSquared();
			sumX += x.getX();
			sumY += x.getY();
			
		}
		
		double xAvg = sumX/list.size();
		double yAvg = sumY/list.size();
		
		double numerator = sumXandY - list.size()*xAvg*yAvg;
		double denominator = sumXSquared - list.size()*Math.pow(xAvg, 2);
		
		return numerator/denominator;
		
	}
	
	public static double calculateBeta0(ArrayList<ZScores> list){
		
		double sumX = 0, sumY = 0;
		
		for (ZScores x : list) {
			
			sumY += x.getY();
			sumX += x.getX();
		}
		
		return sumY/list.size() - calculateBeta1(list)*(sumX/list.size());
		
	}
	
	

}
