package view.transions;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Lighting extends Transition {
    private Rectangle brick;
    public Lighting(Rectangle brick) {
        this.brick = brick;
        setCycleDuration(Duration.millis(300));
        setCycleCount(2);
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 3);
        brick.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/ligthing/"+frame+".png").toExternalForm())));
    }
}
