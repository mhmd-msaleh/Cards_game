/*
****** Memory Game *****
 */

package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main extends Application {
    private Button[][] buttons = new Button[4][4];              //Will be used be handler lambda classes
    private ImageView[][] btImages = new ImageView[4][4];       //will be used to change the image of the button
    private StopWatch yourScore = new StopWatch();              //will be used to save the player score.
    private Image coverImage = new Image("file:images/cover.jpg");   //the cover image for the button.
    Timeline animation = new Timeline();        //the timeline for the score of the player.
    Scene playField;        //the scene where the player play the game.
    Scene scoreBored;      //the scene where the score show.
    @Override
    public void start(Stage primaryStage) throws Exception {


        //Creating Grid Pane for Cards
        GridPane gridPane = new GridPane();

        for (int rows = 0; rows < buttons.length; rows++) {
            for (int columns = 0; columns < buttons[rows].length; columns++) {

                //Creating new ImageView for the cards cover
                btImages[rows][columns] = new ImageView();
                btImages[rows][columns] = new ImageView(coverImage);

                buttons[rows][columns] = new Button();                          //Instantiate new buttons
                buttons[rows][columns].setGraphic(btImages[rows][columns]);   //Set Cover Image for each button
                gridPane.add(buttons[rows][columns], rows, columns);            //Add button to Grid pane
            }
        }

        ImageView[][] images = generateImages(); //assigning the images with random order.
        ArrayList<Integer> firstAndSecondPicks = new ArrayList<Integer>();            //This array's job is to detect whether the player selects two images or not.

        ArrayList<StopWatch> scores = new ArrayList<>();    //keep track of the scores.

        //for loop to make even handler for each button.
        for (int rows = 0; rows < buttons.length; rows++) {
            for (int columns = 0; columns < buttons[rows].length; columns++) {

                int finalColumns = columns;     //we need it to use it in the event-handler.
                int finalRows = rows;           //we need it to use it in the event-handler.
                buttons[rows][columns].setOnAction(e -> {

                    buttons[finalRows][finalColumns].setDisable(true);          //disabling the button prevents the player to select it while it is being selected.
                    btImages[finalRows][finalColumns].setImage(images[finalRows][finalColumns].getImage());       //set the button to the selected image.



                    if(isFinished(btImages)){   //if the the game finished then stop the stopwatch(score) and add it to the array list.
                        animation.stop();
                        scores.add(yourScore);
                    }
                    else if (firstAndSecondPicks.size()==4) {        //if the player selects two images then ... and clear the array.

                       //if the two images dose not match then set the buttons back to its normal state.
                        if(!images[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].getImage().equals(images[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].getImage())) {

                            btImages[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].setImage(coverImage);
                            buttons[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].setDisable(false);
                            btImages[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].setImage(coverImage);
                            buttons[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].setDisable(false);
                        }
                        firstAndSecondPicks.clear();        //clearing the array to use it again.
                    }

                    firstAndSecondPicks.add(finalRows);             //add the player selection to be detected.
                    firstAndSecondPicks.add(finalColumns);          //add the player selection to be detected.



                });
            }
        }
        Text watchStop = new Text();
        EventHandler<ActionEvent> eventHandler = e -> {
            yourScore.perSec();
            watchStop.setText("Score : "+yourScore.toString());
        };
        animation =new Timeline(
                new KeyFrame(Duration.millis(1000), eventHandler)
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

        Button btScores = new Button("Go to scores");
        Button btPlay = new Button("Play");

        btScores.setOnAction(e -> {
            Collections.sort(scores);
            primaryStage.setScene(scoreBored);
        });
        btPlay.setOnAction(e -> primaryStage.setScene(playField));
        Text score =new Text();

        VBox vboxForScores = new VBox();
        vboxForScores.setSpacing(10);
        vboxForScores.setAlignment(Pos.CENTER);
        vboxForScores.getChildren().addAll(new Text("Scores:-"),new Text(scores+""),btPlay);

        watchStop.setFont(new Font(16));
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(watchStop,btScores);
        vbox.setPadding(new Insets(10,20,10,10));

        //Creating BorderPane to organize all components
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setRight(vbox);


        //Setting Scene and primaryStage
        playField = new Scene(borderPane,900,760);
        scoreBored = new Scene(vboxForScores,500,500);
        primaryStage.setTitle("Memory Game");
        primaryStage.setScene(playField);
        primaryStage.show();
    }
    //this method returns true if the player complete the game.
    private boolean isFinished(ImageView[][] btImages){
        for (int rows = 0; rows < btImages.length; rows++) {
            for (int columns = 0; columns < btImages[rows].length; columns++) {
            if(btImages[rows][columns].getImage().equals(coverImage))
                return false;
            }
        }
        return true;
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
        for (int i = 0; i < 8; i++) {
            String imageName = "file:images/" + (i+1) + ".jpg";     //image path => images/num.jpg
            images[i] = new Image(imageName);                   //add image to imagersLIst
        }

        //Random number generator
        Random random = new Random();
        int randomIndexRow  ;                    //Random index for the row of the 2D array.
        int randomIndexColumn ;                  //Random index for the Column of the 2D array.
        ImageView[][] imageViews = new ImageView[4][4];                  //2D array that stores random images throughout out unique random indexes.
        for(int k=0;k<16;k++){
            randomIndexRow = (int)(random.nextDouble() * 4);             //random integer number from 0 to 3.
            randomIndexColumn = (int)(random.nextDouble() * 4);          //random integer number from 0 to 3.
            if (imageViews[randomIndexRow][randomIndexColumn]==null)     //if the place is not used then sign it in.
                imageViews[randomIndexRow][randomIndexColumn]=new ImageView(images[k<8 ? k:k-8]);
            else            //if the place is used then keep the loop running(stuck) until finding unused place.
                k--;
        }
        return imageViews;
    }
}

class StopWatch implements Comparable<StopWatch>{
    private int second=0;
    private int minute=0;
    private int hour=0;

    public void perSec(){
        second++;
        if(second==60){
            second=0;
            minute++;
        }
        if(minute==60){
            minute=0;
            hour++;
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("%2d:%2d:%2d",hour,minute,second);
    }

    @Override
    public int compareTo(StopWatch o) {
        if(hour>o.getHour()||hour==o.getHour()&&minute>o.getMinute()||hour==o.getHour()&&minute==o.getMinute()&&second>o.getSecond())
            return -1;
        else if(hour==o.getHour()&&minute==o.getMinute()&&second==o.getSecond())
            return 0;
        else
            return 1;
    }
}
