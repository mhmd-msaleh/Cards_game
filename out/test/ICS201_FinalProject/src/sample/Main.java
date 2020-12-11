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
import javafx.scene.control.TextArea;
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

public class Main extends Application {
    private final Button[][] buttons = new Button[4][4];              //Will be used be handler lambda classes
    private final ImageView[][] btImages = new ImageView[4][4];       //will be used to change the image of the button.
    private final Image coverImage = new Image("file:images/cover.jpg");   //the cover image for the button.

    private StopWatch yourScore = new StopWatch();       //will be used to save the player score
    private Timeline animation = new Timeline();        //the timeline for the score of the player.
    private Scene playField;        //the scene where the player play the game.
    private Scene scoreBored;      //the scene where the score show.
    private ArrayList<StopWatch> scores = new ArrayList<>();    //keep track of the scores.

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = setPlayField();

        MediaPlayer correct = new MediaPlayer(new Media(new File("sounds/Right.mp3").toURI().toString()));  //a sound for matching correctly.
        MediaPlayer wrong = new MediaPlayer(new Media(new File("sounds/Wrong.mp3").toURI().toString()));    //a sound for matching wrong.
        MediaPlayer youWin = new MediaPlayer(new Media(new File("sounds/youWin.mp3").toURI().toString()));
        MediaPlayer letsAGo = new MediaPlayer(new Media(new File("sounds/lets-a-go.mp3").toURI().toString()));

        ImageView[][] images = Picture.generateImages(); //assigning the images with random order.
        ArrayList<Integer> firstAndSecondPicks = new ArrayList<>();            //This array's job is to detect whether the player selects two images or not.


        letsAGo.play();
        //for loop to make event handler for all buttons.
        for (int rows = 0; rows < buttons.length; rows++) {
            for (int columns = 0; columns < buttons[rows].length; columns++) {
                int finalColumns = columns;     //we need it to use it in the event-handler.
                int finalRows = rows;           //we need it to use it in the event-handler.

                // Set Action for buttons
                buttons[rows][columns].setOnAction(e -> {
                    buttons[finalRows][finalColumns].setDisable(true);          //disabling the button prevents the player to select it while it is being selected.
                    btImages[finalRows][finalColumns].setImage(images[finalRows][finalColumns].getImage());       //set the button to the selected image.

                    if (firstAndSecondPicks.size() == 4) {        //if the player selects two images then ... and clear the array.
                        //if the two images dose not match then set the buttons back to its normal state.
                        if (!images[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].getImage().equals(images[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].getImage())) {
                            btImages[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].setImage(coverImage);
                            buttons[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].setDisable(false);
                            btImages[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].setImage(coverImage);
                            buttons[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].setDisable(false);
                        }
                        firstAndSecondPicks.clear();        //clearing the array to use it again.
                    }

                    firstAndSecondPicks.add(finalRows);             //add the player selection to be detected.
                    firstAndSecondPicks.add(finalColumns);          //add the player selection to be detected.

                    if (isFinished(btImages)) {   //if the the game finished then stop the stopwatch(score) and add it to the array list.
                        animation.stop();
                        scores.add(yourScore);
                        youWin.play();
                    }

                    else if(firstAndSecondPicks.size() == 4){
                        if (!images[firstAndSecondPicks.get(0)][firstAndSecondPicks.get(1)].getImage().equals(images[firstAndSecondPicks.get(2)][firstAndSecondPicks.get(3)].getImage())) {
                            wrong.seek(Duration.ZERO);
                            wrong.play();
                        }
                        else {
                            correct.seek(Duration.ZERO);
                            correct.play();
                        }
                    }
                });
            }
        }

        Text watchStop = new Text();

        // Event Handler To keep track of time
        EventHandler<ActionEvent> eventHandler = e -> {
            yourScore.perSec();
            watchStop.setText("Score : " + yourScore.toString());
        };
        animation = new Timeline(
                new KeyFrame(Duration.millis(1000), eventHandler)
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

        Button btScores = new Button("Go to scores");
        Button btPlay = new Button("Play");
        Text score = new Text();
        btScores.setOnAction(e -> {
            Collections.sort(scores);
            primaryStage.setScene(scoreBored);
            String allScore="";
            for(int k=0;k<scores.size();k++){
                allScore += scores.get(0);
                allScore+="\n";
            }
            score.setText(allScore);
        });
        btPlay.setOnAction(e -> primaryStage.setScene(playField));


        VBox vboxForScores = new VBox();
        vboxForScores.setSpacing(10);
        vboxForScores.setAlignment(Pos.CENTER);
        vboxForScores.getChildren().addAll(new Text("Scores:-"), score, btPlay);

        watchStop.setFont(new Font(16));
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(watchStop, btScores);
        vbox.setPadding(new Insets(10, 20, 10, 10));

        gridPane.setAlignment(Pos.CENTER);
        //Creating BorderPane to organize all components
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setRight(vbox);


        //Setting Scene and primaryStage
        playField = new Scene(borderPane, 900, 760);
        scoreBored = new Scene(vboxForScores, 500, 500);
        primaryStage.setTitle("Memory Game");
        primaryStage.setScene(playField);
        primaryStage.show();
    }

    /** This method returns true if the player complete the game.
     *
     * @param btImages
     * Conditions: If all cover images are hidden
     * @return True
     */
    private boolean isFinished(ImageView[][] btImages) {
        for (int rows = 0; rows < btImages.length; rows++) {
            for (int columns = 0; columns < btImages[rows].length; columns++) {
                if (btImages[rows][columns].getImage().equals(coverImage))
                    return false;
            }
        }
        return true;
    }

    /** This method will set the play field by:
     * Creating a Grid Pane
     * Add buttons with cover images to Grid Pane
     *
     * @return GridPane
     */
    public GridPane setPlayField() {
        //Creating Grid Pane for Cards
        GridPane gridPane = new GridPane();
        for (int rows = 0; rows < buttons.length; rows++) {
            for (int columns = 0; columns < buttons[rows].length; columns++) {

                //Creating new ImageView for the cards cover
                btImages[rows][columns] = new ImageView();
                btImages[rows][columns] = new ImageView(coverImage);
                btImages[rows][columns].setFitHeight(150);
                btImages[rows][columns].setFitWidth(150);

                buttons[rows][columns] = new Button();                          //Instantiate new buttons
                buttons[rows][columns].setGraphic(btImages[rows][columns]);   //Set Cover Image for each button


                gridPane.add(buttons[rows][columns], rows, columns);            //Add button to Grid pane
            }
        }
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        return gridPane;
    }
}


