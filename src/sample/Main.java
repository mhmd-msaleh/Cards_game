package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        ArrayList<Button> buttonsList = new ArrayList<>();
        //Creating ArrayList and Loading images from "images" directory
        ArrayList<Image>  imagesList = new ArrayList<>();
        for(int i = 1; i <= 8; i ++ ) {
            String imageName = "file:images/"+i+".jpg";     //image path => images/num.jpg
            imagesList.add(new Image(imageName));           //add image to imagersLIst

            buttonsList.add(new Button());
        }
        BorderPane borderPane = new BorderPane();


        Scene scene = new Scene(borderPane);

        //Setting primaryStage
        primaryStage.setTitle("Memory Game ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
