/*
****** Memory Game *****
 */

package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    //This method will generate random
    @Override
    public void start(Stage primaryStage) throws Exception{

        //Creating ArrayList and Loading images from "images" directory
        Image[]  images = new Image[8];
        for(int i = 0; i < 8; i ++ ) {
            String imageName = "file:images/"+(i+1)+".jpg";     //image path => images/num.jpg
            images[i] = new Image(imageName);                   //add image to images array
        }

        //Creating new Image for the cards cover
        Image coverImage = new Image("file:images/cover.jpg");
        ImageView coverImageView = new ImageView(coverImage);


        //Creating Grid Pane for Cards
        GridPane gridPane = new GridPane();

        //Creating Buttons
        Button[][] buttons = new Button[4][4];
        for (int rows = 0; rows < buttons.length; rows++){
            for (int columns = 0; columns < buttons[rows].length; columns++){
                buttons[rows][columns] = new Button();                          //Instantiate new buttons
                buttons[rows][columns].setGraphic(new ImageView(coverImage));   //Set Cover Image for each button
                gridPane.add(buttons[rows][columns], rows, columns);            //Add button to Grid pane
            }
        }


        //Creating BorderPane to organize all components
        BorderPane borderPane = new BorderPane();


        /*int k = 0;
        for (int i = 0; i<4; i++) {
            for (int j = 0; j<4; j++)
                cardsPane.add(buttons[k], i, j);
        }
        cardsPane.setVgap(5);
        cardsPane.setHgap(5);
        cardsPane.setAlignment(Pos.CENTER);
        */

        //Setting Scene and primaryStage
        Scene scene = new Scene(gridPane, 250, 300);
        primaryStage.setTitle("Memory Game ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
