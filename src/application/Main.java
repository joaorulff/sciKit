package application;


/*
+----------------------------------------------------------------------------+
| Monmouth University Spring 2016 SE 403-01 |
+----------------------------------------------------------------------------+
| Program Name: Assignment 5 												|
| Author: Phil DiMarco														|
| Version: 1.0 																|
| Date: 04/07/2016															|
| Synopsis: 																|
| This program will calculate the mean, standard deviation, correlation and
  variance of a set of values provide to the program by a file.				|
| References: Assingments 1-4												|
+----------------------------------------------------------------------------+
*/



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
			Scene scene = new Scene(root,1200,600);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
