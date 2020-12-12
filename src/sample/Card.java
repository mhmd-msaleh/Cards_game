package sample;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends Button implements Comparable<Card>{
    private int state;  // state1: open, state 0: closed, state -1: hidden
    private final ImageView[] imageView = new ImageView[2];

    public Card(int id, int state) {
        this.state = state;
    }


    public void setState(int state) {
        this.state = state;
        setGraphic(imageView[state]);
    }

    public void filp(){
        if(this.state == 1)
            setState(0);

        else
            setState(1);
    }

    public void hide(){
        state = -1;
        getGraphic().setOpacity(0);
    }

    public int getState() {
        return state;
    }

    public void addImageView(int index, Image image){
        imageView[index] = new ImageView(image);
    }

    @Override
    public int compareTo(Card o) {
        if(getGraphic().equals(o.getGraphic()))
            return 1;
        else
            return -1;
    }
}