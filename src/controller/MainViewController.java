package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
	private Label varianceLabelID;
	
	private TableView<ZScores> tableview;
	
	
	
	public void openFile(ActionEvent event) throws FileNotFoundException{
		
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
		
		fillMean(Util.calculateMean(simpleList));
		fillStandardDeviation(Util.calculateStandardDeviation(simpleList));
		
		
	}
	
	
	public void fillMean(double mean){
			
		this.meanLabelID.setText(Double.toString(mean));
		
	}
	
	public void fillVariance(double variation){
		this.varianceLabelID.setText(Double.toString(variation));
	}
	
	
	public void fillStandardDeviation(double standardDeviation){
		
		this.stdDeviationLabelID.setText(Double.toString(standardDeviation).substring(0, 4));
		
		
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
	
	public void fillTableDouble(ArrayList<Double> list){
		
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
	

}