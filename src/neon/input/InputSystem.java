package neon.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neon.physics.Vector2D;

/** A class used to get user input. */
public class InputSystem {
  private static final ArrayList<KeyInteraction> keyPressQueue = new ArrayList<>();
  private static final ArrayList<KeyInteraction> keyReleaseQueue = new ArrayList<>();
  private static final ArrayList<KeyInteraction> keyTypeQueue = new ArrayList<>();
  private static final ArrayList<KeyInteraction> keyHeldQueue = new ArrayList<>();
  private static final List<MouseInteraction> mousePressQueue = new ArrayList<>();
  private static final List<MouseInteraction> mouseReleaseQueue = new ArrayList<>();
  private static boolean isLeftMouseButtonHeld;
  private static boolean isMiddleMouseButtonHeld;
  private static boolean isRightMouseButtonHeld;
  private static boolean isOtherMouseButtonHeld;
  private static Vector2D rawMousePosition;
  private static Vector2D mousePosition;
  private static KeyInteraction[] keyPresses;
  private static KeyInteraction[] keyReleases;
  private static KeyInteraction[] keysTyped;
  private static KeyInteraction[] keysHeld;
  private static MouseInteraction[] mousePresses;
  private static MouseInteraction[] mouseReleases;

  /** WARNING: Do not call this method. It is only to be called by core library classes. */
  public static void update() {
    keyPresses = keyPressQueue.toArray(new KeyInteraction[0]);
    keyPressQueue.clear();
    keyReleases = keyReleaseQueue.toArray(new KeyInteraction[0]);
    keyReleaseQueue.clear();
    keysTyped = keyTypeQueue.toArray(new KeyInteraction[0]);
    keyTypeQueue.clear();
    keysHeld = keyHeldQueue.toArray(new KeyInteraction[0]);

    mousePresses = mousePressQueue.toArray(new MouseInteraction[0]);
    mousePressQueue.clear();
    mouseReleases = mouseReleaseQueue.toArray(new MouseInteraction[0]);
    mouseReleaseQueue.clear();
  }

  /**
   * The value is cleared after each render.
   *
   * @return recent key presses
   */
  public static KeyInteraction[] getKeyPresses() {
    return keyPresses;
  }

  /**
   * The value is cleared after each render.
   *
   * @return recent key releases
   */
  public static KeyInteraction[] getKeyReleases() {
    return keyReleases;
  }

  /**
   * The value is cleared after each render.
   *
   * @return recent key types
   */
  public static KeyInteraction[] getKeysTyped() {
    return keysTyped;
  }

  /**
   * The value is cleared after each render.
   *
   * @return keys held
   */
  public static KeyInteraction[] getKeysHeld() {
    return keysHeld;
  }

  /**
   * The value is updated before each tick.
   *
   * @return the raw mouse position
   */
  public static Vector2D getRawMousePosition() {
    return rawMousePosition;
  }

  /**
   * WARNING: Do not call this method. It is only to be called by core library classes.
   *
   * @param rawMousePosition the new raw mouse position
   */
  public static void setRawMousePosition(Vector2D rawMousePosition) {
    InputSystem.rawMousePosition = rawMousePosition;
  }

  /**
   * The value is updated before each tick.
   *
   * @return the mouse position
   */
  public static Vector2D getMousePosition() {
    return mousePosition;
  }

  /**
   * WARNING: Do not call this method. It is only to be called by core library classes.
   *
   * @param mousePosition the new mouse position
   */
  public static void setMousePosition(Vector2D mousePosition) {
    InputSystem.mousePosition = mousePosition;
  }

  /**
   * The value is cleared after each render.
   *
   * @return recent mouse presses
   */
  public static MouseInteraction[] getMousePresses() {
    return mousePresses;
  }

  /**
   * The value is cleared after each render.
   *
   * @return recent mouse releases
   */
  public static MouseInteraction[] getMouseReleases() {
    return mouseReleases;
  }

  /** @return whether the left mouse button is currently being held */
  public static boolean isLeftMouseButtonHeld() {
    return isLeftMouseButtonHeld;
  }

  /** @return whether the middle mouse button is currently being held */
  public static boolean isMiddleMouseButtonHeld() {
    return isMiddleMouseButtonHeld;
  }

  /** @return whether the right mouse button is currently being held */
  public static boolean isRightMouseButtonHeld() {
    return isRightMouseButtonHeld;
  }

  /** @return whether another mouse button is currently being held */
  public static boolean isOtherMouseButtonHeld() {
    return isOtherMouseButtonHeld;
  }

  /**
   * @param character the character
   * @return whether the key matching the character is currently being held
   */
  public static boolean isKeyHeld(char character) {
    return Arrays.stream(keysHeld).anyMatch(x -> x.character == character);
  }

  /**
   * @param keyCode the key code
   * @return whether the key matching the key code is currently being held
   */
  public static boolean isKeyHeld(int keyCode) {
    return Arrays.stream(keysHeld).anyMatch(x -> x.keyCode == keyCode);
  }

  /** WARNING: Do not use this class. It is only to be used by core library classes. */
  public static class MouseInput extends MouseAdapter {
    private static void setMouseButtonHeld(MouseButtonType type, boolean value) {
      switch (type) {
        case LEFT -> isLeftMouseButtonHeld = value;
        case MIDDLE -> isMiddleMouseButtonHeld = value;
        case RIGHT -> isRightMouseButtonHeld = value;
        case OTHER -> isOtherMouseButtonHeld = value;
      }
    }

    /** WARNING: Do not call this method. It is only to be called by core library classes. */
    @Override
    public void mousePressed(MouseEvent event) {
      MouseInteraction interaction = new MouseInteraction(event);
      setMouseButtonHeld(interaction.mouseButtonType, true);
      mousePressQueue.add(interaction);
    }

    /** WARNING: Do not call this method. It is only to be called by core library classes. */
    @Override
    public void mouseReleased(MouseEvent event) {
      MouseInteraction interaction = new MouseInteraction(event);
      setMouseButtonHeld(interaction.mouseButtonType, false);
      mouseReleaseQueue.add(interaction);
    }
  }

  /** WARNING: Do not use this class. It is only to be used by core library classes. */
  public static class KeyInput extends KeyAdapter {
    /** WARNING: Do not call this method. It is only to be called by core library classes. */
    @Override
    public void keyPressed(KeyEvent event) {
      KeyInteraction interaction = new KeyInteraction(event);
      keyPressQueue.add(interaction);
      if (!isKeyHeld(event.getKeyCode())) {
        keyHeldQueue.add(interaction);
      }
    }

    /** WARNING: Do not call this method. It is only to be called by core library classes. */
    @Override
    public void keyReleased(KeyEvent event) {
      keyReleaseQueue.add(new KeyInteraction(event));
      keyHeldQueue.removeIf(x -> x.keyCode == event.getKeyCode());
    }

    /** WARNING: Do not call this method. It is only to be called by core library classes. */
    @Override
    public void keyTyped(KeyEvent event) {
      keyTypeQueue.add(new KeyInteraction(event));
    }
  }
}
