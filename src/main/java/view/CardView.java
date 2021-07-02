package view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.card.Card;

public class CardView extends Rectangle {
    private Card card;
    private ImagePattern image;
    private boolean isHidden;

    public CardView(Card card){
        super(421.0/3,614.0/3);
        this.card = card;
        this.setOnMouseExited(event -> {
            setScaleX(1);
            setScaleY(1);
        });
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setImage(boolean isHidden, boolean is180){
        this.isHidden = isHidden;
        if(isHidden)
            image = card.getBackImage();
        else
            image = card.getCardImage();
        if (is180)
            this.rotateProperty().setValue(180);
        setFill(image);
    }

    public ImagePattern getImage() {
        return image;
    }

    public Card getCard() {
        return card;
    }
}
