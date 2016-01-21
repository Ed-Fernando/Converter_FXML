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

import java.text.NumberFormat;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 */
public class ConversionPanel {
        
    @FXML private ComboBox<Unit> metricComboBox;    
    @FXML private Slider metricSlider;
    @FXML private TextField metricTextField;
    @FXML private ComboBox<Unit> usaComboBox;
    @FXML private Slider usaSlider;
    @FXML private TextField usaTextField;
    @FXML private RadioButton toggleCaspian;
    @FXML private RadioButton toggleModena;
        
    public void addConversionPanel(String title, ObservableList<Unit> units, DoubleProperty meters) {                
        switch(title) {
            
            case "Metric System" :                                    
                                        new GenericConversionPanel(title, units, meters, metricComboBox, metricSlider, metricTextField);
                                        break;
                
            case "U.S. System"  :
                                        new GenericConversionPanel(title, units, meters, usaComboBox, usaSlider, usaTextField);
                                        break;
                
            default :
                        break;
        }
    }
    
    @FXML
    private void toggleCaspianTheme() {
        if(toggleModena.isSelected()) {
            toggleModena.setSelected(false);
            Converter.setUserAgentStylesheet(Converter.STYLESHEET_CASPIAN);
        }
        else
            toggleCaspian.setSelected(true);
    }
    
    @FXML
    private void toggleModenaTheme() {
        if(toggleCaspian.isSelected()) {
            toggleCaspian.setSelected(false);
            Converter.setUserAgentStylesheet(Converter.STYLESHEET_MODENA);
        }
        else
            toggleModena.setSelected(true);
    }
    
    private final class GenericConversionPanel {
        
        final static int MAX = 10000;
        
        private DoubleProperty meters;
        private NumberFormat numberFormat;        
        private ComboBox<Unit> genericComboBox;
        private Slider genericSlider;
        private TextField genericTextField;
        
        {
        //Create the text field format, and then the text field.
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        }
        
        GenericConversionPanel (String title, ObservableList<Unit> units, DoubleProperty meters, 
                ComboBox<Unit> comboBox, Slider slider, TextField textField) {
            
            genericComboBox = comboBox;
            genericSlider = slider;
            genericTextField = textField;
            
            genericSlider.setMax(MAX);
            genericComboBox.setItems(units);            
            genericComboBox.setConverter(new StringConverter<Unit>() {

                            @Override
                            public String toString(Unit t) {
                                return t.description;
                            }

                            @Override
                            public Unit fromString(String string) {
                                throw new UnsupportedOperationException("Not supported yet.");
                            }
                        });
            this.meters = meters;
            genericComboBox.getSelectionModel().select(0);
            meters.addListener(fromMeters);
            genericComboBox.valueProperty().addListener(fromMeters);
            genericTextField.textProperty().addListener(toMeters);
            fromMeters.invalidated(null);        
            genericSlider.valueProperty().bindBidirectional(meters);
        }
        
        private InvalidationListener fromMeters = (Observable arg0) -> {
            if (!genericTextField.isFocused()) {
                genericTextField.setText(numberFormat.format(meters.get() / getMultiplier()));
            }
        };

        private InvalidationListener toMeters = (Observable arg0) -> {
            if (!genericTextField.isFocused()) {
                return;
            }
            try {
                meters.set(numberFormat.parse(genericTextField.getText()).doubleValue() * getMultiplier());
            } catch (Exception ignored) {
                System.err.println(ignored.toString());
            }
        };
        
        /**
        * Returns the multiplier (units/meter) for the currently
        * selected unit of measurement.
        */
        private double getMultiplier() {
            return genericComboBox.getValue().multiplier;
        }
    }    
}
