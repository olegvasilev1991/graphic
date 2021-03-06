package sample.controller;


import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.image.Image;


import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;



public class Lab1Controller {

    @FXML
    private Button imagelast;
    @FXML
    private Button curtimage;
    @FXML
    private ImageView cut;
    @FXML
    private Button gist;
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

    int brightness(int r,int g, int b){
        int brig = (int)(0.3*r + 0.59*g + 0.11*b);
        return brig;
    }


    double distance(double x1, double y1, double x2, double y2){
        double m = img.getHeight()/image.getFitHeight();
        double n = img.getWidth()/image.getFitWidth();
        double distance = Math.sqrt(Math.pow((x1 - x2)*n, 2) + Math.pow((y1 - y2)*m, 2));
        return distance;
    }


    @FXML
    void initialize() {
        imagelast.setOnAction(actionEvent -> {
            img = SwingFXUtils.toFXImage(buffer, null);
            image.setImage(img);

        });

        gist.setOnAction(event -> {
            int gistogramma[] = new int[256];
            int max_gistogramma=0;
            for(int i=0; i< buffer.getHeight(); i++)
                for(int j=0; j< buffer.getWidth(); j++){
                    int red = (buffer.getRGB(j,i) >> 16) & 0xff;
                    int green = (buffer.getRGB(j,i) >> 8) & 0xff;
                    int blue = buffer.getRGB(j,i) & 0xff;
                    gistogramma[brightness(red,green,blue)]++;

                }
            for(int i=0; i<255; i++){
                if (gistogramma[i] > max_gistogramma)
                    max_gistogramma = gistogramma[i];

            }
            BufferedImage gist = new BufferedImage(255, 100, BufferedImage.TYPE_INT_RGB);

            for(int i=0;i<255;i++) {

                double a = ((double) gistogramma[i] / (double) max_gistogramma * 100);

                for (int j = 0; j < 100-(int)a; j++) {
                    gist.setRGB(i, j, 0xffffff);

                }
            }

            img = SwingFXUtils.toFXImage(gist, null);
            image.setImage(img);



        });
        point.setOnAction(event->{
            if(Point == false)
                Point = true;
            else Point = false;
        });
        image.setOnMouseClicked(event -> {
            System.out.println("Clicked!");
        });
        image.setOnMousePressed(event->{
            if(Point == true) {
                x1 = (int) event.getX();
                y1 = (int) event.getY();
                System.out.println(x1+" "+y1);
            }
            x1 = (int) event.getX();
            y1 = (int) event.getY();



            cut.setX(x1);
            cut.setY(y1);
            cut.setFitWidth(1);
            cut.setFitHeight(1);
            BufferedImage cutt = new BufferedImage(1,1,BufferedImage.TYPE_BYTE_GRAY);
            cutt.setRGB(0,0,127);
            Image cute = SwingFXUtils.toFXImage(cutt, null);

            cut.setImage(cute);


        });
        image.setOnMouseDragged(event -> {
                cut.setFitWidth(event.getX() - x1);
                cut.setFitHeight(event.getY() - y1);
        });



        image.setOnMouseReleased(event ->{
            if(Point == true){
                x2 = (int) event.getX();
                y2 = (int) event.getY();

                double m = img.getHeight()/image.getFitHeight();
                double n = img.getWidth()/image.getFitWidth();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeight(400);
                alert.setTitle("Растояние");
                alert.setHeaderText("Результат:");
                alert.setContentText("Точка 1: Х="+x1*n+" Y="+y1*m+"\n"+
                "Точка 2: Х="+x2*n+" Y="+y2*m+
                        "\nРастояние между точками "+distance(x1,y1,x2,y2)+" пикселей"
                );

                alert.showAndWait();
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
            BufferedImage grayimage = new BufferedImage(buffer.getWidth(),buffer.getHeight(),buffer.getType());
            Color color;
            int gray;
            for (int i = 0; i < grayimage.getWidth(); i++)
                for (int j = 0; j < grayimage.getHeight(); j++) {
                    color = new Color(buffer.getRGB(i, j));
                    gray = (color.getBlue() + color.getGreen() + color.getRed()) / 3;
                    color = new Color(gray, gray, gray);
                    grayimage.setRGB(i, j, color.getRGB());
                }


            System.out.println(file.getName().toString());

            img = SwingFXUtils.toFXImage(grayimage, null);
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
