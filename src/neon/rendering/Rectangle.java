package neon.rendering;

import neon.physics.RectangularHitbox;
import neon.physics.Vector2D;

import java.awt.Color;

/**
 * A representation of a rectangle with a hitbox, color, and fill setting.
 */
public class Rectangle {
    private RectangularHitbox hitbox;
    private Color color;
    private boolean fill;

    /**
     * @param hitbox the hitbox of the rectangle
     * @param color  the color of the rectangle
     * @param fill   whether the rectangle should be filled when drawn
     */
    public Rectangle(RectangularHitbox hitbox, Color color, boolean fill) {
        this.hitbox = hitbox;
        this.color = color;
        this.fill = fill;
    }

    /**
     * @return the rectangle's hitbox
     */
    public RectangularHitbox getHitbox() {
        return hitbox;
    }

    /**
     * Sets the rectangle's hitbox.
     *
     * @param hitbox the new hitbox
     */
    public void setHitbox(RectangularHitbox hitbox) {
        this.hitbox = hitbox;
    }

    /**
     * @return the rectangle's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the rectangle's color.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return whether is rectangle is to be filled
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * Sets whether the rectangle is to be filled.
     *
     * @param fill the new value
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public Rectangle copy() {
        return new Rectangle(hitbox.copy(), new Color(color.getRGB()), fill);
    }
}