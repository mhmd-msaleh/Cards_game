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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main extends Application {
    private final ArrayList<Button> buttons = new ArrayList<>();         //Will be used be handler lambda classes
    private ImageView[] buttonImages = new ImageView[16];       //will be used to change the image of the button.
    private ArrayList<StopWatch> scores = new ArrayList<>();      //keep track of the scores.

    private final Image coverImage = new Image("file:images/cover.jpg");

    private final StopWatch yourScore = new StopWatch();       //will be used to save the player score
    private Scene playField;        //the scene where the player play the game.
    private Scene scoreBored;      //the scene where the score show.

    private final GridPane gridPane = new GridPane();
    private Timeline timer;

    /** This method will set all buttons to coverImage
     *
     * Conditions:
     * If buttons ArrayList is empty, create new buttons
     * If not empty, set existing image for existing buttons
     */
    public void setClosedButtons() {
        if(buttons.isEmpty()) {
            for (int i = 0; i < 16; i++) {
                ImageView imageView = new ImageView(coverImage);
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
                buttons.add(new Button());                          //Instantiate new buttons
                buttons.get(i).setGraphic(imageView);   //Set Cover Image for each button
            }
        }
        else {
            for (Button btn: buttons) {
                ImageView imageView = new ImageView(coverImage);
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
                btn.setGraphic(imageView);
            }
        }
    }

    /** This method returns true if the player complete the game.
     *
     * @param btImages
     * Conditions: If all cover images are hidden
     * @return True
     */
    private boolean isFinished(ImageView[] btImages) {
        for (int rows = 0; rows < btImages.length; rows++) {
            if (btImages[rows].getImage().equals(coverImage))
                return false;
        }
        return true;
    }

    /**
     *
     * @return ImageView[16]
     */
    public ImageView[] generateImages() {
        //Creating ArrayList and Loading images from "images" directory
        Image[] images = new Image[8];
        for (int i = 0; i < 8; i++) {
            String imageName = "file:images/" + (i + 1) + ".jpg";     //image path => images/num.jpg
            images[i] = new Image(imageName);                   //add image to imagersLIst
        }

        //Random number generator
        Random random = new Random();
        int randomIndex = 0;                    //Random index for the row of the 2D array.
        //Random index for the Column of the 2D array.
        ImageView[] imageViews = new ImageView[16];                  //2D array that stores random images throughout out unique random indexes.
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i= 0; i <16; i++){
            randomIndex = (int)(random.nextDouble() * 16);
            if (imageViews[randomIndex] == null){
                 //indexes.add(randomIndex);
                 imageViews[randomIndex] = new ImageView(images[i < 8 ? i : i - 8]);
                 imageViews[randomIndex].setFitHeight(150);
                 imageViews[randomIndex].setFitWidth(150);
            }
            else
                i--;
        }



        /*for (int k = 0; k < 16; k++) {
            randomIndex = (int) (random.nextDouble() * 4);             //random integer number from 0 to 3.
            if (imageViews[randomIndex] == null) {     //if the place is not used then sign it in.
                imageViews[randomIndex]= new ImageView(images[k < 8 ? k : k - 8]);
                imageViews[randomIndex].setFitHeight(150);
                imageViews[randomIndex].setFitHeight(150);
            }
            else            //if the place is used then keep the loop running(stuck) until finding unused place.
                k--;
        }*/
        return imageViews;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MediaPlayer correct = new MediaPlayer(new Media(new File("sounds/Right.mp3").toURI().toString()));  //a sound for matching correctly.
        MediaPlayer wrong = new MediaPlayer(new Media(new File("sounds/Wrong.mp3").toURI().toString()));    //a sound for matching wrong.
        MediaPlayer youWin = new MediaPlayer(new Media(new File("sounds/youWin.mp3").toURI().toString()));
        MediaPlayer letsAGo = new MediaPlayer(new Media(new File("sounds/lets-a-go.mp3").toURI().toString()));

        Button btPlay = new Button("Play");
        Button btScores = new Button("Go to scores");

        Text score = new Text();
        VBox vboxForScores = new VBox();
        vboxForScores.setSpacing(10);
        vboxForScores.setAlignment(Pos.CENTER);
        vboxForScores.getChildren().addAll(new Text("Scores:-"), score, btPlay);

        setClosedButtons();

        gridPane.setVgap(7);
        gridPane.setHgap(7);
        gridPane.setAlignment(Pos.CENTER);
        int indexForButtons = 0;
        for (int i = 0; i < 4; i++){
            for (int j =0; j < 4; j++){
                gridPane.add(buttons.get(indexForButtons), i, j);
                indexForButtons++;
            }
        }

        Text watchStop = new Text();
        // Event Handler To keep track of time
        EventHandler<ActionEvent> eventHandler = e -> {
            yourScore.perSec();
            watchStop.setText("Score : " + yourScore.toString());
        };
        timer = new Timeline(   //the timeline for the score of the player.
                new KeyFrame(Duration.millis(1000), eventHandler)
        );

        // Action for play button:
        // * Set Scene to playFiled
        // * start Timer
        // * play sound
        btPlay.setOnAction(e -> {
            primaryStage.setScene(playField);
           // timer.jumpTo(Duration.ZERO);
            //timeReset.fire();
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

            letsAGo.seek(Duration.ZERO);
            letsAGo.play();
        });

        // Action for score Button:
        // * Retrun to scoreBoard
        // * get Score
        // * Stop and reset timer
        btScores.setOnAction(e -> {
            Collections.sort(scores);
            primaryStage.setScene(scoreBored);
            String allScore = "";
            for (int k = 0; k < scores.size(); k++) {
                allScore += scores.get(0);
                allScore += "\n";
            }
            score.setText(allScore);
            timer.stop();
            yourScore.reset();

        });

        watchStop.setFont(new Font(16));
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(watchStop, btScores);
        vbox.setPadding(new Insets(10, 20, 10, 10));

        //Creating BorderPane to organize all components
        BorderPane playFieldPane = new BorderPane();
        playFieldPane.setCenter(gridPane);
        playFieldPane.setRight(vbox);

        ArrayList<Integer> firstAndSecondPicks = new ArrayList<>();            //This array's job is to detect whether the player selects two images or not.
        buttonImages = generateImages();

        //for loop to make event handler for all buttons.
        for (int i = 0; i < buttons.size(); i++) {
                int finalIndex = i;     //we need it to use it in the event-handler.

                // Set Action for buttons
                buttons.get(i).setOnAction(e -> {
                    buttons.get(finalIndex).setDisable(true);          //disabling the button prevents the player to select it while it is being selected.
                    buttons.get(finalIndex).setGraphic(buttonImages[finalIndex]);       //set the button to the selected image.

                    if (firstAndSecondPicks.size() == 2) {        //if the player selects two images then ... and clear the array.
                        //if the two images dose not match then set the buttons back to its normal state.
                        if (!buttonImages[firstAndSecondPicks.get(0)].getImage().equals(buttonImages[firstAndSecondPicks.get(1)].getImage())) {
                            buttonImages[firstAndSecondPicks.get(0)].setImage(coverImage);
                            buttons.get(firstAndSecondPicks.get(0)).setDisable(false);
                            buttonImages[firstAndSecondPicks.get(1)].setImage(coverImage);
                            buttons.get(firstAndSecondPicks.get(1)).setDisable(false);
                        }
                        firstAndSecondPicks.clear();        //clearing the array to use it again.
                    }

                    firstAndSecondPicks.add(finalIndex);             //add the player selection to be detected.

                    if (isFinished(buttonImages)) {   //if the the game finished then stop the stopwatch(score) and add it to the array list.
                        timer.stop();
                        scores.add(yourScore);
                        youWin.seek(Duration.ZERO);
                        youWin.play();
                        btScores.fire();

                    } else if (firstAndSecondPicks.size() == 2) {
                        if (!buttonImages[firstAndSecondPicks.get(0)].getImage().equals(buttonImages[firstAndSecondPicks.get(1)].getImage())) {
                            wrong.seek(Duration.ZERO);
                            wrong.play();
                        } else {
                            correct.seek(Duration.ZERO);
                            correct.play();
                        }
                    }
                });

        }

        //Setting Scene and primaryStage
        playField = new Scene(playFieldPane, 900, 760);
        scoreBored = new Scene(vboxForScores, 500, 500);
        primaryStage.setTitle("Memory Game");
        primaryStage.setScene(scoreBored);
        primaryStage.show();
    }

}


