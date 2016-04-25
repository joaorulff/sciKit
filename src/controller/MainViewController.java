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
import model.Degree;
import model.NormalSegment;
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
	
	@FXML
	private Label qLabelID;
	
	@FXML
	private Label pLabelID;
	
	@FXML
	private Label respLabelID;
	
	
	private TableView<NormalSegment> tableview;
	
	private TableView<ZScores> tableviewDouble;
	

	
	
	public void openFileSingle(ActionEvent event) throws FileNotFoundException{
		
		
		
		File input = new File(filePathID.getText());
		ArrayList<Double> list = Util.readValuesFromFile(input);
		list = Util.toZScores(list);
		
		
		
		
		int numberOfSegments = Util.segments(list.size());
		ArrayList<NormalSegment> segments = Util.segmentsLength(numberOfSegments);
		
		for (double zScore : list) {
			for (NormalSegment normalSegment : segments) {
				if(normalSegment.isInside(zScore)){
					normalSegment.incrementCounter();
				}
			}
		}
		
		
		
		double q = 0;
		for (NormalSegment normalSegment : segments) {
			q += normalSegment.calculateQvalue(list.size()/numberOfSegments);
		}
		
		
		double chitest = 1.0 - (1.0/(double)numberOfSegments*0.001);
		float probability = 1.0f - (float)chitest;
		
		String resp = null;
		if(probability > 0.002){
			resp = "Likely Normal Distribution";
		}else{
			resp = "Not Likely Normal Distribution";
		}
		/*
		System.out.println(q);
		System.out.println("CHITEST: " + chitest);
		System.out.println(probability);
		System.out.println(resp);
		*/
		
		fillMean(Util.calculateMean(list));
		fillStandardDeviation(Util.calculateStandardDeviation(list));
		fillVariance(Util.calculateVariance(list));
		fillQ(q);
		fillP(probability);
		fillAnswer(resp);
		fillTable(segments);
		
		
		
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
	
	public void fillQ(double q){
		this.qLabelID.setText(q+"");
	}
	
	public void fillP(double p){
		this.pLabelID.setText(p+"");
	}
	
	public void fillAnswer(String r){
		this.respLabelID.setText(r);
	}
	
	public void fillTable(ArrayList<NormalSegment> list){
		
		TableColumn	<NormalSegment, Double> lowerBound = new TableColumn<>("Lower Bound");
		lowerBound.setMinWidth(100);
		lowerBound.setCellValueFactory(new PropertyValueFactory<>("lowerBound"));
		
		
		TableColumn<NormalSegment, Double> upperBound = new TableColumn<>("Upper Bound");
		upperBound.setMinWidth(100);
		upperBound.setCellValueFactory(new PropertyValueFactory<>("upperBound"));
		
		TableColumn<NormalSegment, Integer> counter = new TableColumn<>("Actual Data Items");
		counter.setMinWidth(100);
		counter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		
		
		
		
		this.tableview = new TableView<>();
		
		
		this.tableview.getColumns().add(lowerBound);
		this.tableview.getColumns().add(upperBound);
		this.tableview.getColumns().add(counter);

		
		ObservableList<NormalSegment> listValues = FXCollections.observableArrayList();
		
		for (NormalSegment x : list) {
			
			NormalSegment temp = new NormalSegment(x.getLowerBound(), x.getUpperBound());
			temp.setCounter(x.getCounter());
			temp.setqValue(x.getqValueCol());
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
		
		
		this.tableviewDouble = new TableView<>();
		
		
		this.tableviewDouble.getColumns().add(xColumn);
		this.tableviewDouble.getColumns().add(yColumn);
		this.tableviewDouble.getColumns().add(integralColumn);
		
		ObservableList<ZScores> listValues = FXCollections.observableArrayList();
		
		for (ZScores x : list) {
			
			
			listValues.add(x);
			
		}
		
		
		this.tableviewDouble.setItems(listValues);
		this.tableHBox.getChildren().addAll(this.tableviewDouble);
		
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
