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

import model.NormalSegment;
import model.ZScores;

public class Util {
	
	
	public static ArrayList<Double> toZScores(ArrayList<Double> list){
		
		double mean = calculateMean(list);
		double stdDev = calculateStandardDeviation(list);
		
		ArrayList<Double> newList = new ArrayList<>();
		
		for (Double element : list) {
			newList.add((element - mean)/stdDev);
		}
		
		for (Double x : newList) {
			System.out.println(x);
		}
		return newList; 
	}
	
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
	
	public static int segments(int fileLength){
		int maxCount = 0;
		int segements = 0;
		double difference = 99;
		
		
		for(int s = 3; s <= fileLength; s++){
			int count = 0;
			float intTest = ((fileLength/(float)s));
			if ((intTest == Math.floor(intTest)) && !Double.isInfinite(intTest)) {
				count++;
			}
				
			if (fileLength/s >= 5){
				count++;
			}
			
			if(s*s >= fileLength){
				count++;
			}
			
			if(s*s == fileLength){
				count++;
			}
			
			if(count >= maxCount){
				double temp = Math.abs((fileLength - (s * s)));
				if(temp - fileLength  <= difference && temp >= 0){
					difference = temp;
					maxCount = count;
					segements = s;
				}
			}
		
		}
		
		return segements;
		
		}
	
		public static ArrayList<NormalSegment> segmentsLength(int numberOfsegments){
		
		double percentile = 1.0/(double)numberOfsegments;
		ArrayList<Double> upperBounds = new ArrayList<>();
		ZScores helper = new ZScores(0);
		
		for (double i = -3; i < 0; i = i + .001) {
			
			double temp = helper.calculateIntegral(i);
			//Thread.sleep(100);
			//System.out.println("CURRENT I: " + i);
			//System.out.println("CURRENT PERCENTILE: " + percentile);
			//System.out.println("INTEGRAL:  "+ calculateIntegral(i));
			
			if (Math.abs(temp - percentile) < 0.001){
				
				percentile += 1.0/(double)numberOfsegments;
				upperBounds.add(i);
			}
			
		}
		
		for (int i = upperBounds.size()-2; i >= 0; i--) {
			upperBounds.add(upperBounds.get(i)*(-1));
		}
		
		
		ArrayList<NormalSegment> normalSegments = new ArrayList<>();
		for (int i = 0; i < upperBounds.size(); i++) {
			
			if(i == 0){
				NormalSegment temp = new NormalSegment(-1000, upperBounds.get(i));
				normalSegments.add(temp);
			}else if(i != upperBounds.size()-1){
				NormalSegment temp = new NormalSegment(upperBounds.get(i-1), upperBounds.get(i));
				normalSegments.add(temp);
			}
			else{
				NormalSegment temp = new NormalSegment(upperBounds.get(i-1), upperBounds.get(i));
				normalSegments.add(temp);
				NormalSegment temp1 = new NormalSegment(upperBounds.get(i), 1000);
				normalSegments.add(temp1);
			}
			
		}
		return normalSegments;
	}
	

}
