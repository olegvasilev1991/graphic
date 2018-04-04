package sample.controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;




public class Lab1Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ImageView image;
    @FXML
    private Button button;
    @FXML
    private Button menu;
    @FXML
    private Button loadimage;
    @FXML
    private Button rotate;

    @FXML
    void initialize() {
        button.setOnAction(event -> {
            System.out.println("hello");
        });
        menu.setOnAction(event -> {
            menu.getScene().getWindow().hide();
            FXMLLoader menu = new FXMLLoader();
            menu.setLocation(getClass().getResource("/sample/view/menu.fxml"));

            try {
                menu.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = menu.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        });
        loadimage.setOnAction(this::handle);

        rotate.setOnAction(event -> {
            System.out.println(image.getFitHeight()+" "+image.getFitWidth()+"  "+image.getRotate());

            image.setRotate(image.getRotate()+90);

        });
    }


    private void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();


        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            System.out.println(file.toString());
            Image img = new Image(file.toURI().toString());
            image.setLayoutX(10);
            image.setLayoutY(15);
            image.setImage(img);
            /*image.setFitHeight(350);
            image.setFitWidth(350);*/
        }
    }
}
