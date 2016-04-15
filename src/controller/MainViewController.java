/*
+----------------------------------------------------------------------------+
| Monmouth University Spring 2016 SE 403-01 |
+----------------------------------------------------------------------------+
| Program Name: Assignment 5 												|
| Author: Kris Horton														|
| Version: 1.0 																|
| Date: 04/07/2016															|
| Synopsis: 																|
| This program will calculate the mean, standard deviation, correlation and
  variance of a set of values provide to the program by a file.				|
| References: Assingments 1-4												|
+----------------------------------------------------------------------------+
*/



package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.ZScores;
import util.Util;




public class MainViewController{
	
	@FXML
	private HBox tableHBox;
	
	@FXML
	private Label meanLabelID;
	
	@FXML
	private Label stdDeviationLabelID;
	
	@FXML
	private Label correlationLabelID;	
	
	@FXML
	private TextField filePathID;
	
	@FXML
	private TextField filePathDoubleID;
	
	@FXML
	private TextField tDistributionPath;
	
	@FXML
	private Label varianceLabelID;
	
	@FXML
	private Label regressionLabelID;
	
	@FXML
	private LineChart<Number, Number> graphID;
	
	
	private TableView<ZScores> tableview;
	

	
	
	public void openFileSingle(ActionEvent event) throws FileNotFoundException{
		
		
		
		File input = new File(filePathID.getText());
		ArrayList<Double> list = Util.readValuesFromFile(input);
		
		fillMean(Util.calculateMean(list));
		fillStandardDeviation(Util.calculateStandardDeviation(list));
		fillVariance(Util.calculateVariance(list));
		fillTable(list);
		
		
	}
	
	
	
	
	public void openFileDouble(ActionEvent event) throws FileNotFoundException {
		
		File input = new File(filePathDoubleID.getText());
		ArrayList<ZScores> list = Util.readValuesFromFileDouble(input);
		
		ArrayList<Double> simpleList = new ArrayList<>();
		
		for (ZScores row : list) {
			simpleList.add(row.getX().doubleValue());
		}
		
		this.fillMean(Util.calculateMean(simpleList));
		this.fillStandardDeviation(Util.calculateStandardDeviation(simpleList));
		this.fillVariance(Util.calculateVariance(simpleList));
		this.fillCorrelation(Util.calculateCorrelation(list));
		this.fillRegression(list);
		this.constructGraph(list);
		this.fillTableDouble(list);
		
		
	}
	
	public void fillRegression(ArrayList<ZScores> list){
		
		
		String beta0 = String.valueOf(Util.calculateBeta0(list));
		String beta1 = String.valueOf(Util.calculateBeta1(list));
		this.regressionLabelID.setText("Beta0: " + beta0.substring(0, 5) + " Beta1: " + beta1.substring(0, 5));
		
	}
	
	
	public void fillMean(double mean){
			
		this.meanLabelID.setText(Double.toString(mean));
		
	}
	
	public void fillVariance(double variation){
		this.varianceLabelID.setText(Double.toString(variation));
	}
	
	
	public void fillStandardDeviation(double standardDeviation){
		
		this.stdDeviationLabelID.setText(Double.toString(standardDeviation));
		
		
	}
	
	public void fillCorrelation(double correlation){
		this.correlationLabelID.setText(Double.toString(correlation));
	}
	
	public void fillTable(ArrayList<Double> list){
		
		TableColumn	<ZScores, Float> xColumn = new TableColumn<>("zScore");
		xColumn.setMinWidth(100);
		xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		
		
		TableColumn<ZScores, Float> integralColumn = new TableColumn<>("integralValue");
		integralColumn.setMinWidth(100);
		integralColumn.setCellValueFactory(new PropertyValueFactory<>("integralValue"));
		
		
		this.tableview = new TableView<>();
		
		
		this.tableview.getColumns().add(xColumn);
		this.tableview.getColumns().add(integralColumn);
		
		ObservableList<ZScores> listValues = FXCollections.observableArrayList();
		
		for (Double x : list) {
			
			ZScores temp = new ZScores(x.floatValue());
			listValues.add(temp);
			
		}
		
		
		this.tableview.setItems(listValues);
		this.tableHBox.getChildren().addAll(this.tableview);
		
	}
	
	public void fillTableDouble(ArrayList<ZScores> list){
		
		TableColumn	<ZScores, Float> xColumn = new TableColumn<>("X");
		xColumn.setMinWidth(100);
		xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		
		TableColumn	<ZScores, Float> yColumn = new TableColumn<>("dof");
		yColumn.setMinWidth(100);
		yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
		
		
		TableColumn<ZScores, Float> integralColumn = new TableColumn<>("integralValue");
		integralColumn.setMinWidth(100);
		integralColumn.setCellValueFactory(new PropertyValueFactory<>("integralValue"));
		
		
		this.tableview = new TableView<>();
		
		
		this.tableview.getColumns().add(xColumn);
		this.tableview.getColumns().add(yColumn);
		this.tableview.getColumns().add(integralColumn);
		
		ObservableList<ZScores> listValues = FXCollections.observableArrayList();
		
		for (ZScores x : list) {
			
			
			listValues.add(x);
			
		}
		
		
		this.tableview.setItems(listValues);
		this.tableHBox.getChildren().addAll(this.tableview);
		
	}
	
	
	public void constructGraph(ArrayList<ZScores> list){
		
		XYChart.Series<Number,Number> series1 = new XYChart.Series<>();
		XYChart.Series<Number,Number> series2 = new XYChart.Series<>();
        
        double beta0 = Util.calculateBeta0(list);
        double beta1 = Util.calculateBeta1(list);
        
        this.graphID.getStylesheets().add("application/application.css");
        
        

        for (int i = 0; i < 2000; i = i+100) {
            series1.getData().add(new XYChart.Data<>(/*String.valueOf(i)*/i, (beta0 + beta1*i)));
        }
        
        for (ZScores element : list) {
        	series2.getData().add(new XYChart.Data<>(element.getX(), element.getY()));
		}
        
        this.graphID.getData().add(series2);
        this.graphID.getData().add(series1); 
        
        
	}
	
	public void tDistributionCalc(ActionEvent event) throws FileNotFoundException{
		
		File input = new File(tDistributionPath.getText());
		ArrayList<ZScores> list = Util.readValuesFromFileDouble(input);
		
		ArrayList<Double> simpleList = new ArrayList<>();
		
		for (ZScores row : list) {
			simpleList.add(row.getX().doubleValue());
		}
		
		for (ZScores row : list) {
			if(row.getX() > .5){
				row.setIntegral(-1);
			}else{
				row.integrationWithT();
			}
			
		}
		
		this.fillMean(Util.calculateMean(simpleList));
		this.fillStandardDeviation(Util.calculateStandardDeviation(simpleList));
		this.fillVariance(Util.calculateVariance(simpleList));
		this.fillCorrelation(Util.calculateCorrelation(list));
		this.fillRegression(list);
		//this.constructGraph(list);
		this.fillTableDouble(list);
		
	}
	

}
