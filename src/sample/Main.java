package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Creating ArrayList and Loading images from "images" directory
        ArrayList<Image>  imagesList = new ArrayList<>();
        for(int i = 1; i <= 8; i ++ ) {
            String imageName = "file:images/"+i+".jpg";
            imagesList.add(new Image(imageName));
        }
        BorderPane borderPane = new BorderPane(new ImageView(imagesList.get(3)));


        Scene scene = new Scene(borderPane);

        //Setting primaryStage
        primaryStage.setTitle("Memory Game ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
