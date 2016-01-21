/*
 * Copyright (c) 2016 Edilbert Fernando. All rights reserved.
 * Copyright (c) 2012, 2013 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This file is licensed under the 3-Clause BSD/New BSD license.
 *
 * The terms of the license can be found in the "License.txt" file provided.
 */

package converter;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Converter extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private final ObservableList<Unit> metricDistances;
    private final ObservableList<Unit> usaDistances;
    private final DoubleProperty meters = new SimpleDoubleProperty(1);
    
    public Converter() {
        //Create Unit objects for metric distances, and then
        //instantiate a ConversionPanel with these Units.
        metricDistances = FXCollections.observableArrayList(
                new Unit("Centimeters", 0.01),
                new Unit("Meters", 1.0),
                new Unit("Kilometers", 1000.0));
        
        //Create Unit objects for U.S. distances, and then
        //instantiate a ConversionPanel with these Units.
        usaDistances = FXCollections.observableArrayList(
                new Unit("Inches", 0.0254),
                new Unit("Feet", 0.305),
                new Unit("Yards", 0.914),
                new Unit("Miles", 1613.0));
    }
    
    @Override
    public void start(Stage stage) throws Exception {      
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("ConversionPanel.fxml"));        
        Parent root = loader.load();        
        Scene scene = new Scene(root);
        stage.setTitle("Converter");        
        stage.setScene(scene);        
        ConversionPanel controller = 
                loader.<ConversionPanel>getController();
        controller.addConversionPanel(
                                "Metric System", metricDistances, meters);
        controller.addConversionPanel(
                                "U.S. System", usaDistances, meters);
        stage.show();
    }
}