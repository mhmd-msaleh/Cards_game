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

import java.util.Random;

public class Main extends Application {
    Button[][] buttons = new Button[4][4];      //Will be used be handler lambda classes

    @Override
    public void start(Stage primaryStage) throws Exception{


        //Creating new Image for the cards cover
        Image coverImage = new Image("file:images/cover.jpg");

        //Creating Grid Pane for Cards
        GridPane gridPane = new GridPane();

        for (int rows = 0; rows < buttons.length; rows++){
            for (int columns = 0; columns < buttons[rows].length; columns++){
                buttons[rows][columns] = new Button();                          //Instantiate new buttons
                buttons[rows][columns].setGraphic(new ImageView(coverImage));   //Set Cover Image for each button
                gridPane.add(buttons[rows][columns], rows, columns);            //Add button to Grid pane
            }
        }

        //Creating BorderPane to organize all components
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);


        //Setting Scene and primaryStage
        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Memory Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /* *** This method will generate and assign random images to buttons ***
    * First images are imported and stored in [8] array
    * Then 2-D array must be created with size 4x4 to store the randomly
      generated images
    * Each image must be assigned to 2 buttons
    * Linked images and button must be referred to by the same index
    *
    * 1) The process of this method is: to load images from directory,
      then generate a random number to associate every image to 2 indexes
      of the buttons array
     * The method should check if the random number is used before or not
     * Because 2-D array is used, the random number must be generated twice
       [row][coulomn]

     * 2) Another 2-D array of type ImageView  must be created to store buttons' images
     * 3) Finally, the method will return the 2-D array of ImageView
    */
    public static ImageView[][] generateImages() {
        //Creating ArrayList and Loading images from "images" directory
        Image[] images = new Image[8];
        for (int i = 1; i <= 8; i++) {
            String imageName = "file:images/" + i + ".jpg";     //image path => images/num.jpg
            images[i] = new Image(imageName);                   //add image to imagersLIst
        }

        //Random number generator
        Random random = new Random();
        double randomIndex = (random.nextDouble() * 3) + 1;


    }
}
