package sample;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Picture {




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
        javafx.scene.image.Image[] images = new javafx.scene.image.Image[8];
        for (int i = 0; i < 8; i++) {
            String imageName = "file:images/" + (i + 1) + ".jpg";     //image path => images/num.jpg
            images[i] = new javafx.scene.image.Image(imageName);                   //add image to imagersLIst
        }

        //Random number generator
        Random random = new Random();
        int randomIndexRow;                    //Random index for the row of the 2D array.
        int randomIndexColumn;                  //Random index for the Column of the 2D array.
        ImageView[][] imageViews = new ImageView[4][4];                  //2D array that stores random images throughout out unique random indexes.
        for (int k = 0; k < 16; k++) {
            randomIndexRow = (int) (random.nextDouble() * 4);             //random integer number from 0 to 3.
            randomIndexColumn = (int) (random.nextDouble() * 4);          //random integer number from 0 to 3.
            if (imageViews[randomIndexRow][randomIndexColumn] == null)     //if the place is not used then sign it in.
                imageViews[randomIndexRow][randomIndexColumn] = new ImageView(images[k < 8 ? k : k - 8]);
            else            //if the place is used then keep the loop running(stuck) until finding unused place.
                k--;
        }
        return imageViews;
    }
}
