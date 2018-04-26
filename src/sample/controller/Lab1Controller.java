package sample.controller;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;

import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;


public class Lab1Controller {

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
    private Button gray;
    @FXML
    private Button point;
    private Boolean Point = false;
    private double x1,y1,x2,y2;

    private Image img;
    private File file;
    private BufferedImage buffer = null;

    double distance(double x1, double y1, double x2, double y2){
        double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return distance;
    }

    @FXML
    void initialize() {
        point.setOnAction(event->{
            Point = true;
        });

        image.setOnMouseEntered(event->{
            if(Point == true) {
                x1 = (int) event.getX();
                y1 = (int) event.getY();
                System.out.println(x1+" "+y1);
            }
        });
        image.setOnMouseMoved(event ->{

        });
        image.setOnMouseExited(event ->{
            if(Point == true){
                x2 = (int) event.getX();
                y2 = (int) event.getY();
                System.out.println(x2+" "+y2);
            }
        });
        button.setOnAction(event -> {
            System.out.println("hello");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            Stage stage = new Stage();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg")
            );

            File iii = fileChooser.showSaveDialog(stage);
            if (iii != null) {
                try {
                    ImageIO.write(buffer, "JPEG", iii);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
            System.out.println(image.getFitHeight() + " " + image.getFitWidth() + "  " + image.getRotate()+" ");

            BufferedImage newImage = new BufferedImage(buffer.getHeight(), buffer.getWidth(), buffer.getType());


            for( int i=0; i < buffer.getWidth(); i++ )
                for( int j=0 ; j < buffer.getHeight(); j++ )
                    newImage.setRGB( buffer.getHeight()-1-j, i, buffer.getRGB(i,j) );
            buffer = newImage;
            img = SwingFXUtils.toFXImage(newImage, null);
            image.setImage(img);
        });

        gray.setOnAction(event -> {
            System.out.println(img.getPixelReader().toString());

            Color color;
            int gray;
            for (int i = 0; i < buffer.getWidth(); i++)
                for (int j = 0; j < buffer.getHeight(); j++) {
                    color = new Color(buffer.getRGB(i, j));
                    gray = (color.getBlue() + color.getGreen() + color.getRed()) / 3;
                    color = new Color(gray, gray, gray);
                    buffer.setRGB(i, j, color.getRGB());
                }


            System.out.println(file.getName().toString());

            img = SwingFXUtils.toFXImage(buffer, null);
            image.setImage(img);
        });
    }


    private void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();


        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            System.out.println(file.toString());
            img = new Image(file.toURI().toString());
            image.setLayoutX(10);
            image.setLayoutY(15);
            image.setImage(img);
            try {
                buffer = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
