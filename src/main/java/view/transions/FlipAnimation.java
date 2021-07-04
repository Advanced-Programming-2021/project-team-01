package view.transions;

import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import view.CardView;

public class FlipAnimation extends Transition {
    private CardView node;
    private ImagePattern backImage = new ImagePattern(new Image(getClass().getResource("/Cards/Unknown.jpg").toExternalForm()));
    private boolean frontToBack = false, upsideDown = false;

    @Override
    protected void interpolate(double frac) {
        if (frontToBack) {
            if (upsideDown) flipFrontToBackUpsideDown(frac);
            else flipFrontToBackRightToLeft(frac);
        } else {
            if (upsideDown) flipBackToFrontUpsideDown(frac);
            else flipBackToFrontRightToLeft(frac);
        }
    }

    public void setNode(CardView node) {
        this.node = node;
    }

    public void setFrontToBack(boolean frontToBack) {
        this.frontToBack = frontToBack;
    }

    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }

    private void flipFrontToBackUpsideDown(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setFill(node.getImage());
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleY(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
            node.setFill(backImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleY(v);
        }
    }

    private void flipBackToFrontUpsideDown(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setFill(backImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleY(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
            node.setFill(node.getImage());
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleY(v);
        }
    }

    private void flipFrontToBackRightToLeft(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setFill(node.getImage());
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleX(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
            node.setFill(backImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleX(v);
        }
    }

    private void flipBackToFrontRightToLeft(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setFill(backImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleX(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
            node.setFill(node.getImage());
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleX(v);
        }
    }
}
