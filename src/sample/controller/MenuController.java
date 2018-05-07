package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button lab1;


    @FXML
    private Button lab2;

    @FXML
    private AnchorPane menu;


    @FXML
    void initialize() {
        lab1.setOnAction(event -> {
            lab1.getScene().getWindow().hide();
            FXMLLoader Lab1 = new FXMLLoader();
            Lab1.setLocation(getClass().getResource("/sample/view/lab1.fxml"));

            try {
                Lab1.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = Lab1.getRoot();
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Лабораторная работа№1");
            stage.show();
        });

        lab2.setOnAction(event -> {
            lab1.getScene().getWindow().hide();
            FXMLLoader Lab2 = new FXMLLoader();
            Lab2.setLocation(getClass().getResource("/sample/view/lab2.fxml"));

            try {
                Lab2.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = Lab2.getRoot();
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Лабораторная работа№2");
            stage.show();
        });


    }

}
