package flamesdev.neon.rendering;

import flamesdev.neon.critical.GameSettings;
import flamesdev.neon.physics.Hitbox;

import java.awt.*;

/**
 * A class used to render graphics.
 */
public class RenderSystem {
    private static GameSettings settings;

    /**
     * WARNING: Do not call this method. It is only to be called by core library classes.<br>
     * Sets the settings of the render engine. Once a value is defined, it cannot be
     * changed.
     *
     * @param settings the game settings
     */
    public static void setSettings(GameSettings settings) {
        if (RenderSystem.settings == null)
            RenderSystem.settings = settings;
    }

    /**
     * Draws a rectangle.
     *
     * @param graphics  the graphics object used to draw the game's graphics
     * @param rectangle the rectangle to be drawn
     */
    public static void drawRectangle(Graphics graphics, Rectangle rectangle) {
        Color color = rectangle.getColor();
        if (color != null)
            graphics.setColor(color);

        Hitbox hitbox = rectangle.getHitbox();
        int width = settings.getWidth();
        int height = settings.getHeight();
        int[] parameters = new int[] { (int) Math.round(hitbox.getLowerXBound() * width),
                (int) Math.round(reverseY(hitbox.getHigherYBound() * height)),
                (int) Math.round(hitbox.getWidth() * width),
                (int) Math.round(hitbox.getHeight() * height) };
        if (rectangle.isFill())
            graphics.fillRect(parameters[0], parameters[1], parameters[2], parameters[3]);
        else
            graphics.drawRect(parameters[0], parameters[1], parameters[2], parameters[3]);
    }

    /**
     * Draws an image.
     *
     * @param graphics the graphics object used to draw the game's graphics
     * @param imgObj   the image to be drawn
     */
    public static void drawImage(Graphics graphics, ImageObject imgObj) {
        Hitbox hitbox = imgObj.getHitbox();
        int width = settings.getWidth();
        int height = settings.getHeight();
        graphics.drawImage(imgObj.getSprite(), (int) Math.round(hitbox.getLowerXBound() * width),
                (int) Math.round(reverseY(hitbox.getHigherYBound() * height)),
                (int) Math.round(hitbox.getWidth() * width), (int) Math.round(hitbox.getHeight() * height), null);
    }

    /**
     * Draws text.
     *
     * @param graphics the graphics object used to draw the game's graphics
     * @param textObj  the text to be drawn
     */
    public static void drawText(Graphics graphics, TextObject textObj) {
        graphics.setFont(textObj.getFont());
        graphics.setColor(textObj.getColor());

        Hitbox hitbox = textObj.getHitbox();
        int width = settings.getWidth();
        int height = settings.getHeight();
        graphics.drawString(textObj.getText(), (int) Math.round(hitbox.getLowerXBound() * width),
                (int) Math.round(reverseY(hitbox.getCenter().getY() * height)));
    }

    private static double reverseY(double y) {
        return settings.getHeight() - y;
    }
}