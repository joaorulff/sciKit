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
	

}
