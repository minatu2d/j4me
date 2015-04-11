package org.j4me.ui;

import javax.microedition.lcdui.*;

/**
 * The <code>DeviceScreen</code> class is a base class for any screen that needs complete
 * control over how it is painted.  It is based on and similar to the MIDP
 * <code>Canvas</code> class.
 * <p>ge
 * This class removes the following methods from the MIDP <code>Canvas</code> class:
 * <ul>
 *  <li><code>isDoubleBuffered</code> - This class is always double buffered.
 *  <li><code>hasPointerEvents</code> and <code>hasPointerMotionEvents</code> - There is
 *      no use for this method.  Implement the pointer event methods and
 *      if the device has no pointer they will be ignored.  
 *  <li> - Same reason as <code>hasPointerEvents</code>.
 *  <li><code>hasRepeatEvents</code> - This class always has key repeat events.
 *  <li><code>getKeyName</code> - The application should define the names to make them
 *      consistent across all devices.
 *  <li><code>getKeyCode</code> - The key code is passed into the key event methods.
 *  <li><code>getGameAction</code> - The game action is passed into the key event methods.
 *  <li><code>sizeChanged</code> - This method is notoriously buggy and applications
 *      should use <code>getWidth</code> and <code>getHeight</code> instead.
 *  <li><code>getTicker</code> and <code>setTicker</code> - The ticker functionality has
 *      not been implemented.
 *  <li><code>addCommand</code>, <code>removeCommand</code>, and <code>setCommandListener</code> -
 *      Menu options have been replaced with something that works across all
 *      MIDP 2.0 devices.  There are left and right menu options.  See
 *      <code>setMenuText</code> for details.
 * </ul>
 * 
 * @see javax.microedition.lcdui.Canvas
 */
public abstract class DeviceScreen {

    /**
     * Constant for the <code>LEFT</code> game action.
     */
    public static final int LEFT = -1 * javax.microedition.lcdui.Canvas.LEFT;
    /**
     * Constant for the <code>RIGHT</code> game action.
     */
    public static final int RIGHT = -1 * javax.microedition.lcdui.Canvas.RIGHT;
    /**
     * Constant for the <code>UP</code> game action.
     */
    public static final int UP = -1 * javax.microedition.lcdui.Canvas.UP;
    /**
     * Constant for the <code>DOWN</code> game action.
     */
    public static final int DOWN = -1 * javax.microedition.lcdui.Canvas.DOWN;
    /**
     * Constant for the <code>FIRE</code> game action.
     */
    public static final int FIRE = -1 * javax.microedition.lcdui.Canvas.FIRE;
    /**
     * Constant for the general purpose "A" game action.
     */
    public static final int GAME_A = -1 * javax.microedition.lcdui.Canvas.GAME_A;
    /**
     * Constant for the general purpose "B" game action.
     */
    public static final int GAME_B = -1 * javax.microedition.lcdui.Canvas.GAME_B;
    /**
     * Constant for the general purpose "C" game action.
     */
    public static final int GAME_C = -1 * javax.microedition.lcdui.Canvas.GAME_C;
    /**
     * Constant for the general purpose "D" game action.
     */
    public static final int GAME_D = -1 * javax.microedition.lcdui.Canvas.GAME_D;
    /**
     * <code>keyCode</code> for ITU-T key 0.
     * <p>
     * Constant value 48 is set to <code>KEY_NUM0</code>.
     */
    public static final int KEY_NUM0 = javax.microedition.lcdui.Canvas.KEY_NUM0;
    /**
     * <code>keyCode</code> for ITU-T key 1.
     * <p>
     * Constant value 49 is set to <code>KEY_NUM1</code>.
     */
    public static final int KEY_NUM1 = javax.microedition.lcdui.Canvas.KEY_NUM1;
    /**
     * <code>keyCode</code> for ITU-T key 2.
     * <p>
     * Constant value 50 is set to <code>KEY_NUM2</code>.
     */
    public static final int KEY_NUM2 = javax.microedition.lcdui.Canvas.KEY_NUM2;
    /**
     * <code>keyCode</code> for ITU-T key 3.
     * <p>
     * Constant value 51 is set to <code>KEY_NUM3</code>.
     */
    public static final int KEY_NUM3 = javax.microedition.lcdui.Canvas.KEY_NUM3;
    /**
     * <code>keyCode</code> for ITU-T key 4.
     * <p>
     * Constant value 52 is set to <code>KEY_NUM4</code>.
     */
    public static final int KEY_NUM4 = javax.microedition.lcdui.Canvas.KEY_NUM4;
    /**
     * <code>keyCode</code> for ITU-T key 5.
     * <p>
     * Constant value 53 is set to <code>KEY_NUM5</code>.
     */
    public static final int KEY_NUM5 = javax.microedition.lcdui.Canvas.KEY_NUM5;
    /**
     * <code>keyCode</code> for ITU-T key 6.
     * <p>
     * Constant value 54 is set to <code>KEY_NUM6</code>.
     */
    public static final int KEY_NUM6 = javax.microedition.lcdui.Canvas.KEY_NUM6;
    /**
     * <code>keyCode</code> for ITU-T key 7.
     * <p>
     * Constant value 55 is set to <code>KEY_NUM7</code>.
     */
    public static final int KEY_NUM7 = javax.microedition.lcdui.Canvas.KEY_NUM7;
    /**
     * <code>keyCode</code> for ITU-T key 8.
     * <p>
     * Constant value 56 is set to <code>KEY_NUM8</code>.
     */
    public static final int KEY_NUM8 = javax.microedition.lcdui.Canvas.KEY_NUM8;
    /**
     * <code>keyCode</code> for ITU-T key 9.
     * <p>
     * Constant value 57 is set to <code>KEY_NUM9</code>.
     */
    public static final int KEY_NUM9 = javax.microedition.lcdui.Canvas.KEY_NUM9;
    /**
     * <code>keyCode</code> for ITU-T key "pound" (#).
     * <p>
     * Constant value 35 is set to <code>KEY_POUND</code>.
     */
    public static final int KEY_POUND = javax.microedition.lcdui.Canvas.KEY_POUND;
    /**
     * <code>keyCode</code> for ITU-T key "star" (*).
     * <p>
     * Constant value 42 is set to <code>KEY_STAR</code>.
     */
    public static final int KEY_STAR = javax.microedition.lcdui.Canvas.KEY_STAR;
    /**
     * Constant for the left soft menu key found on MIDP 2.0 devices.
     */
    public static final int MENU_LEFT = -21;
    /**
     * Constant for the right soft menu key found on MIDP 2.0 devices.
     */
    public static final int MENU_RIGHT = -22;
    /**
     * The actual <code>Canvas</code> object that controls the device's screen.
     * This object wraps it.
     */
    public final CanvasWrapper slave;
    /**
     * When <code>false</code> this class will paint the menu bar at the bottom
     * of the screen.  When <code>true</code> it will not. 
     */
    public boolean fullScreenMode = false;
    /**
     * What is written as a title bar for this canvas.  When this is <code>null</code>
     * no title bar will be written.  To show the header without any text
     * set this to the empty string "".
     */
    public String title;
    /**
     * The text for the left menu button.  This is the negative side used
     * for cancelling and going back to previous screens.
     */
    public String leftMenu;
    /**
     * The text for the right menu button.  This is the positive side used
     * for accepting input, invoking menus, and moving forward in the
     * application's state.
     */
    public String rightMenu;

    /**
     * Implicitly called by derived classes to setup a new J4ME canvas.
     */
    public DeviceScreen() {
        // Create a wrapper around the canvas.
        if (UIManager.getSlave() == null) {
            slave = new CanvasWrapper(this);
            UIManager.setSlave(slave);
        } else {
            slave = UIManager.getSlave();
        }
        // this.setFullScreenMode(true);        
    }

    /**
     * Returns the LCDUI <code>Canvas</code> wrapped by this screen.  This is
     * required for some APIs.
     * 
     * @return The <code>javax.microedition.lcdui.Canvas</code> wrapped by this screen.
     */
    public Canvas getCanvas() {
        return slave;
    }

    /**
     * Makes this object take over the device's screen.
     * <p>
     * The previous screen will have its <code>hideNotify</code> method called.
     * Then this screen's <code>showNotify</code> method will be invoked followed
     * by the <code>paint</code> method.
     */
    public void show() {
        // Set the wrapped canvas as the current screen.
        UIManager.setScreen(this, slave);
    }

    public void run() {
        slave.run();
    }

    public void runForWelcome() {
        slave.runForWelcome();
    }

    /**
     * Checks if this screen is actually visible on the display.  In
     * order for a screen to be visible, all of the following must be true:
     * the MIDlet must be running in the foreground, the screen must be the
     * current one, and the screen must not be obscured by a system screen.
     * 
     * @return <code>true</code> if this screen is currently visible; <code>false</code>
     *  otherwise.
     */
    public boolean isShown() {
        if (UIManager.getScreen() == this) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The implementation calls <code>showNotify()</code> immediately prior to
     * this <code>Canvas</code> being made visible on the display.  <code>Canvas</code>
     * subclasses may override this method to perform tasks before being
     * shown, such as setting up animations, starting timers, etc.  The
     * default implementation of this method in class <code>Canvas</code> is empty.
     */
    public void showNotify() {
    }

    /**
     * The implementation calls <code>hideNotify()</code> shortly after the
     * <code>Canvas</code> has been removed from the display.  <code>Canvas</code>
     * subclasses may override this method in order to pause animations,
     * revoke timers, etc.  The default implementation of this method in
     * class <code>Canvas</code> is empty.
     */
    public void hideNotify() {
    }

    /**
     * Shows or hides the menu bar at the bottom of the screen.
     * 
     * @param mode is <code>true</code> if the <code>DeviceScreen</code> is to be in full
     *  screen mode, <code>false</code> otherwise.
     */
    public void setFullScreenMode(boolean mode) {
        this.fullScreenMode = mode;
    }

    /**
     * Returns if the title bar and menu bar are hidden or not.
     *  
     * @return <code>true</code> if in full screen mode (title bar and menu
     *  bar are hidden); <code>false</code> otherwise.
     */
    public boolean isFullScreenMode() {
        return fullScreenMode;
    }

    /**
     * Gets the title of this screen.  If this returns <code>null</code> the
     * screen has no title.
     * 
     * @return The title of this screen.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this screen.  The default is <code>null</code> meaning no
     * title.
     * <p>
     * For the title to be visible full screen mode must be off.
     * This can be done with the <code>setFullScreenMode</code> method.
     * 
     * @param title is the new title for the screen.
     */
    public void setTitle(String title) {
        this.title = title;

        // Notify the slave screen.        
        slave.setTitle(title);
        //slave.repaint();
    }

    /**
     * Set new value stop variable for CanvasWrapper
     * @param stopValue 
     */
    public synchronized void setStop(boolean stopValue) {
        //   System.out.println("Force slave stop");
        this.slave.setStop(stopValue);
    }

    /**
     * Returns if this screen displays a title bar at the top.  Title
     * bars require both setting a title (through <code>setTitle</code>) and
     * that full screen mode is off (<code>setFullScreenMode(false)</code>).
     * 
     * @return <code>true</code> if the screen has a title bar; <code>false</code>
     *  if it does not.
     */
    public boolean hasTitleBar() {
        // Full screen mode off?
        if (fullScreenMode == false) {
            // There is some title?
            if (title != null) {
                // Device supports title bars?
                if (slave.supportsTitleBar()) {
                    return true;
                }
            }
        }
        if (fullScreenMode == true) {
            // There is some title?
            if (title != null) {
                // Device supports title bars?
                if (slave.supportsTitleBar()) {
                    return true;
                }
            }
        }

        // If we made it here no title bar should be displayed.
        return false;
    }

    /**
     * Returns the title of this screen.
     * 
     * @return The title of this screen.  If no title is set this returns
     *  the empty string "".
     */
    public String toString() {
        if (title == null) {
            return getClass().getName();
        } else {
            return title;
        }
    }

    /**
     * Sets the menu bar text.
     * <p>
     * For the menu to be visible full screen mode must be off.
     * This can be done with the <code>setFullScreenMode</code> method.
     * 
     * @param left is the text for the negative menu option or <code>null</code>
     *  to remove the button.  Negative menu options are things like canceling
     *  a form and moving back to a previous screen.
     * @param right is the text for the positive menu option or <code>null</code>
     *  to remove the button.  Positive menu options are things like accepting
     *  a form, advancing to the next screen, or displaying a menu.
     * @see #declineNotify()
     * @see #acceptNotify()
     */
    public void setMenuText(String left, String right) {
        this.leftMenu = left;
        this.rightMenu = right;

        // Notify the slave screen.
        slave.setMenuText(left, right);
        //slave.repaint();
    }

    /**
     * Returns the text for the left menu button.  The left menu button is
     * for negative operations such as canceling a form and going back to
     * a previous screen.
     * 
     * @return The text for the left menu button.  If there is no button
     *  this returns <code>null</code>.
     */
    public String getLeftMenuText() {
        return leftMenu;
    }

    /**
     * Returns the text for the right menu button.  The right menu button is
     * for positive operations such as accepting a form and opening a menu.
     * 
     * @return The text for the right menu button.  If there is no button
     *  this returns <code>null</code>.
     */
    public String getRightMenuText() {
        return rightMenu;
    }

    /**
     * Returns if this screen displays a menu bar at the bottom.  Menu bars
     * require both setting at least one menu option (through
     * <code>setMenuText</code>) and that full screen mode is off
     * (<code>setFullScreenMode(false)</code>).
     * 
     * @return <code>true</code> if the screen has a menu bar; <code>false</code>
     *  if it does not.
     */
    public boolean hasMenuBar() {
        // Full screen mode off?
        if (fullScreenMode == false) {
            // There is some menu text?
            if ((leftMenu != null) || (rightMenu != null)) {
                // Device supports menus?
                if (slave.supportsMenuBar()) {
                    return true;
                }
            }
        }
        if (fullScreenMode == true) {
            // There is some menu text?
            if ((leftMenu != null) || (rightMenu != null)) {
                // Device supports menus?
                if (slave.supportsMenuBar()) {
                    return true;
                }
            }
        }

        // If we made it here no menu bar should be displayed.
        return false;
    }

    /**
     * Returns the width of the usuable portion of this canvas.  The usable
     * portion excludes anything on the sides of the screen such as scroll
     * bars.
     * 
     * @return The number of pixels wide the usable portion of the canvas is.
     */
    public int getWidth() {
        return slave.getWidth();
    }

    /**
     * Returns the height of the usuable portion of this canvas.  The usable
     * portion excludes the title area and menu bar unless this canvas has been
     * set to full screen mode.
     * 
     * @return The number of pixels high the usable portion of the canvas is.
     */
    public int getHeight() {
        Theme theme = UIManager.getTheme();

        // Get the height of the entire canvas.
        int height = getScreenHeight();

        // Remove the height of the title bar. 
        // if (hasTitleBar()) {
        height -= theme.getTitleHeight();
        //  }

        // Remove the height of the menu bar.
        // if (hasMenuBar()) {
        height -= theme.getMenuHeight();
        //   }

        return height - 10;
    }

    /**
     * Gets the width of the entire screen in pixels.
     * <p>
     * <i>Platform bug note.</i>  Motorola and early Nokia phones return the
     * incorrect size until after the first screen has actually been displayed.
     * So, for example, calling this from a constructor before any screen has
     * been displayed will give incorrect data.  The workaround is to put up
     * another screen first, such as a splash screen.
     * 
     * @return The number of pixels wide the entire screen is.
     */
    public int getScreenWidth() {
        return slave.getWidth();
    }

    /**
     * Gets the height of the entire screen in pixels.  This includes
     * the title area at the top of the screen and menu bar at the bottom.
     * Use <code>getHeight</code> to get the actual usable area of the canvas.
     * <p>
     * <i>Platform bug note.</i>  Motorola and early Nokia phones return the
     * incorrect size until after the first screen has actually been displayed.
     * So, for example, calling this from a constructor before any screen has
     * been displayed will give incorrect data.  The workaround is to put up
     * another screen first, such as a splash screen.
     * 
     * @return The number of pixels high the entire screen is.
     */
    public int getScreenHeight() {
        return slave.getHeight();
    }

    /**
     * Method called by the framework when the user clicks on the left menu button.
     * The default implementation does nothing.
     * 
     * @see #getLeftMenuText()
     */
    public void declineNotify() {
    }

    /**
     * Method called by the framework when the user clicks the right menu button.
     * The default implementation does nothing.
     * 
     * @see #getRightMenuText()
     */
    public void acceptNotify() {
    }

    /**
     * Called when a key is pressed.  It can be identified using the
     * constants defined in this class.
     * <p>
     * Special keys, like the joystick and menu buttons, have negative
     * values.  Input characters, like the number keys, are positive.
     * <p>
     * Unlike the MIDP <code>Canvas</code> class which requires tranlation of game keys
     * this implementation does not.  The <code>keyCode</code> value will match the
     * constants of this class.
     * 
     * @param keyCode is the key code of the key that was pressed.
     *  Negative values are special keys like the joystick and menu buttons.
     */
    public void keyPressed(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).  It can be identified using the
     * constants defined in this class.
     * <p>
     * Special keys, like the joystick and menu buttons, have negative
     * values.  Input characters, like the number keys, are positive.
     * <p>
     * Unlike the MIDP <code>Canvas</code> class which requires tranlation of game keys
     * this implementation does not.  The <code>keyCode</code> value will match the
     * constants of this class.
     * <p>
     * Also unlike the MIDP <code>Canvas</code> class, this implementation always
     * supports this method and does so the same across all devices.
     * 
     * @param keyCode is the key code of the key that was held down.
     *  Negative values are special keys like the joystick and menu buttons.
     */
    public void keyRepeated(int keyCode) {
    }

    /**
     * Called when a key is released.  It can be identified using the
     * constants defined in this class.
     * <p>
     * Special keys, like the joystick and menu buttons, have negative
     * values.  Input characters, like the number keys, are positive.
     * <p>
     * Unlike the MIDP <code>Canvas</code> class which requires tranlation of game keys
     * this implementation does not.  The <code>keyCode</code> value will match the
     * constants of this class.
     * 
     * @param keyCode is the key code of the key that was released.
     *  Negative values are special keys like the joystick and menu buttons.
     */
    public void keyReleased(int keyCode) {
    }

    /**
     * Called when the pointer is pressed.
     * 
     * @param x is the horizontal location where the pointer was pressed
     *  relative to the canvas area (i.e. does not include the title or
     *  menu bars).
     * @param y is the vertical location where the pointer was pressed
     *  relative to the canvas area (i.e. does not include the title or
     *  menu bars).
     */
    public void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     * 
     * @param x is the horizontal location where the pointer was pressed
     *  relative to the canvas area (i.e. does not include the title or
     *  menu bars).
     * @param y is the vertical location where the pointer was pressed
     *  relative to the canvas area (i.e. does not include the title or
     *  menu bars).
     */
    public void pointerReleased(int x, int y) {
    }

    /**
     * Called when the pointer is dragged.
     * 
     * @param x is the horizontal location where the pointer was pressed
     *  relative to the canvas area (i.e. does not include the title or
     *  menu bars).
     * @param y is the vertical location where the pointer was pressed
     *  relative to the canvas area (i.e. does not include the title or
     *  menu bars).
     */
    public void pointerDragged(int x, int y) {
    }

    /**
     * Requests a repaint for the entire <code>Canvas</code>. The effect is identical to
     * <code>repaint(0, 0, getWidth(), getHeight());</code>. 
     */
    public void repaint() {
        // Make sure the wrapper is in full-screen mode.
        //   There is a bug on some implementations that turns the screen
        //   off full-screen mode.  This can be seen when going to a
        //   javax.microedition.lcdui.TextBox screen and back to this one.
        //slave.setFullScreenMode(true);
        // Do the repaint.
        //slave.repaint();
    }

    /**
     * Requests a repaint for the specified region of the <code>Canvas<code>.  Calling this
     * method may result in subsequent call to <code>paint()</code>, where the passed
     * <code>Graphics</code> object's clip region will include at least the specified region.
     * <p>
     * If the canvas is not visible, or if width and height are zero or less, or if
     * the rectangle does not specify a visible region of the display, this call has
     * no effect.
     * <p>
     * The call to <code>paint()</code> occurs asynchronously of the call to <code>repaint()</code>.
     * That is, <code>repaint()</code> will not block waiting for <code>paint()</code> to finish.  The
     * <code>paint()</code> method will either be called after the caller of <code>repaint()</code> returns
     * to the implementation (if the caller is a callback) or on another thread entirely.
     * <p>
     * To synchronize with its <code>paint()</code> routine applications can use
     * <code>serviceRepaints()</code>, or they can code explicit synchronization into their
     * <code>paint()</code> routine.
     * <p>
     * The origin of the coordinate system is above and to the left of the pixel in
     * the upper left corner of the displayable area of the <code>Canvas</code>. The 
     * X-coordinate is positive right and the Y-coordinate is positive downwards.
     * 
     * @param x is the x coordinate of the rectangle to be repainted.
     * @param y is the y coordinate of the rectangle to be repainted.
     * @param width is the width of the rectangle to be repainted.
     * @param height is the height of the rectangle to be repainted.
     * @see Canvas#serviceRepaints()
     */
    public void repaint(int x, int y, int width, int height) {
        if (hasTitleBar()) {
            // Offset the user's y by the height of the title bar.
            Theme theme = UIManager.getTheme();
            int titleHeight = theme.getTitleHeight();
            y += titleHeight;
        }
        //slave.repaint(x, y, width, height);
    }

    /**
     * Forces any pending repaint requests to be serviced immediately.  This
     * method blocks until the pending requests have been serviced.  If there
     * are no pending repaints, or if this canvas is not visible on the
     * display, this call does nothing and returns immediately.
     */
    public void serviceRepaints() {
        slave.serviceRepaints();
    }

    /**
     * Paints the background of the main section of the screen.  This includes
     * everything except for the title bar at the top and menu bar at the bottom.
     * However, if this canvas is in full screen mode, then this method paints the entire
     * screen.
     * <p>
     * After this method is called, the <code>paintCanvas</code> method will be.
     * <p>
     * Override this method to change the background for just this screen.  Override
     * <code>Theme.paintBackground</code> to change the background for the entire application.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @see #paint(Graphics)
     */
    public void paintBackground(Graphics g) {
        UIManager.getTheme().paintBackground(g);
    }

    /**
     * Paints the main section of the screen.  This includes everything except
     * for the title bar at the top and menu bar at the bottom.  However,
     * if this canvas is in full screen mode, then this method paints the entire
     * screen.
     * <p>
     * Before this method is called, the <code>paintBackground</code> method will be.
     * Any painting done here will go over the background.
     * <p>
     * Override this method to paint the main area of the screen.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @see #paintBackground(Graphics)
     */
    public abstract void paint(Graphics g);

    /**
     * Paints the title bar of the canvas.  This method is called only
     * when the title has been set through <code>setTitle</code> and the canvas
     * is not in full screen mode.
     * <p>
     * Override this method to change the appearance of the title bar
     * for just this canvas.  To change them for the entire application,
     * override <code>Theme.paintTitleBar</code>.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @param title is the text for the title bar as defined by the
     *  canvas class.
     * @param width is the width of the title bar in pixels.
     * @param height is the height of the title bar in pixels.
     */
    public void paintTitleBar(Graphics g, String title, int width, int height) {
        UIManager.getTheme().paintTitleBar(g, title, width, height);
    }

    /**
     * Paints the menu bar at the bottom of the canvas.  This method is
     * not called if the canvas is in full screen mode.
     * <p>
     * Override this method to change the appearance or functionality of
     * the menu for just this canvas.  To change them for the entire
     * application, override <code>Theme.paintMenuBar</code>.  Be careful not
     * to write strings that are too long and will not fit on the menu bar.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @param left is the text to write on the left side of the menu bar.
     *  The left side is associated with dimissing input such as a
     *  "Cancel" button.
     * @param highlightLeft is <code>true</code> if the menu text <code>left</code>
     *  should be highlighted to indicate the left menu button is currently
     *  pressed.
     * @param right is the text to write on the right side of the menu bar.
     *  The right side is associated with accepting input such as an
     *  "OK" button.
     * @param highlightRight is <code>true</code> if the menu text <code>right</code>
     *  should be highlighted to indicate the right menu button is currently
     *  pressed.
     * @param width is the width of the menu bar in pixels.
     * @param height is the height of the menu bar in pixels.
     */
    public void paintMenuBar(Graphics g,
            String left, boolean highlightLeft,
            String right, boolean highlightRight,
            int width, int height) {
        UIManager.getTheme().paintMenuBar(g, left, highlightLeft, right, highlightRight, width, height);
    }

    /**
     * Returns if the clip area of <code>g</code> intersects the given rectangle.
     * 
     * @param g is the current <code>Graphics</code> object for a <code>paint</code>
     *  operation.
     * @param x is the pixel at the left edge of the rectangle.
     * @param y is the pixel at the top edge of the rectangle.
     * @param w is width of the rectangle in pixels.
     * @param h is height of the rectangle in pixels.
     * @return <code>true</code> if the rectangle is to be painted by <code>g</code>;
     *  <code>false</code> otherwise.
     */
    public static boolean intersects(Graphics g, int x, int y, int w, int h) {
        // Get the graphic's clip dimensions.
        int gx = g.getClipX();
        int gy = g.getClipY();
        int gw = g.getClipWidth();
        int gh = g.getClipHeight();

        // Make the width/height into the right/bottom.
        gw += gx;
        gh += gy;
        w += x;
        h += y;

        // Check for intersections.
        //  (overflow || intersect)
        boolean intersects =
                (w < x || w > gx)
                && (h < y || h > gy)
                && (gw < gx || gw > x)
                && (gh < gy || gh > y);
        return intersects;
    }

    public void paintFullBackground(Graphics g) {
        UIManager.getTheme().paintFullBackground(g);
    }
}
