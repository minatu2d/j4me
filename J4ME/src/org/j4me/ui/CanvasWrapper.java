/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.j4me.ui;

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import org.j4me.examples.ui.Runner;
import res.ResManager;

/**
 * Wraps the LCDUI's <code>Canvas</code> class.  It masks differences between
 * platforms.  It is used as the actual <code>Screen</code> for all J4ME screens.
 */
public class CanvasWrapper
        extends javax.microedition.lcdui.game.GameCanvas implements Runnable {

    public String totalMemory = null;
    public String freeMemory = null;
    /**/
//    private int counter = 0;
    /**
     * The interval, in milliseconds, between signaling repeat events.
     * It can be thought of as events raised per second by taking 1000
     * and dividing it by this number (e.g. 1000 / 250 = 4 events per second).
     */
    public static final short REPEAT_PERIOD = 100; // Modify by PVT 200 -> 100
    /**
     * When <code>true</code> this is running on a BlackBerry device.  When
     * <code>false</code> it is not.
     * <p>
     * BlackBerry phones have limited native MIDlet support.  The biggest
     * issue is they do not have the left and right menu buttons on other
     * MIDP 2.0 phones.  Instead they have a "Menu" key and a "Return"
     * key that are big parts of the overall BlackBerry experience.  To
     * capture these key events, and make the application consistent with
     * the BlackBerry experience, we need to create a standard LCDUI menu
     * which the BlackBerry framework ties to these buttons.
     */
    public static boolean blackberry;
    /**
     * When <code>true</code> this is running in IBM's J9 JVM (also known as
     * WEME or WebSphere Everyplace Micro Edition).  When <code>false</code>
     * it is not.
     * <p>
     * For Windows Mobile and Palm phones the only reliable J2ME JVM is
     * IBM's J9 JVM.  There are some other good implementations that
     * come with some phones, such as the Motorola Q which uses Motorola's
     * JVM.
     * <p>
     * J9 does not support detecting soft menu buttons.  We need to use
     * its title area and menu bar instead of our own to capture these
     * events.  This also hooks into the design of these devices more
     * closely so the application resembles others on the phone.
     */
    public static boolean ibmJ9;
    /**
     * When <code>true</code> this is running on Tao's JVM.  When <code>false</code>
     * it is not.
     * <p>
     * The Tao JVM runs on Windows Mobile and behaves similarly to IBM's J9.
     */
    public static boolean tao;
    /**
     * The screen that uses this object for screen operations.
     * <p>
     * This should only be <code>null</code> when using a dummy screen to get
     * the dimensions.  For more information see the <code>sizeChanged</code>
     * method's comments for more information.
     */
    public DeviceScreen master;
    /**
     * Executes the <code>keyRepeated</code> job every <code>REPEAT_PERIOD</code>.
     * This value will be <code>null</code> if the user is not holding down
     * any keys.
     * <p>
     * Note we have to create new <code>Timer</code> objects every time we
     * repeat keys.  It does not work to create a timer once here and
     * then try to reuse it.  <i>To prevent multiple timer objects
     * synchronize access on <code>this</code> when dealing with
     * <code>keyRepeatTimer</code>.</i>
     */
    public Timer keyRepeatTimer = null;
    /**
     * If <code>true</code> the left menu button text should be highlighted.  This
     * happens when the user presses the left menu button to indicate the
     * event was received.  Normally this will be <code>false</code> showing no
     * menu work is in progress.
     */
    public boolean highlightLeftMenu;
    /**
     * If <code>true</code> the right menu button text should be highlighted.  This
     * happens when the user presses the right menu button to indicate the
     * event was received.  Normally this will be <code>false</code> showing no
     * menu work is in progress.
     */
    public boolean highlightRightMenu;
    /**
     * When we do not paint the menu bar, such as with BlackBerries and IBM's
     * J9 JVM, this contains the left menu choice.  Otherwise this will be
     * <code>null</code>.
     * 
     * @see #lcduiRightMenuCommand
     */
    public Command lcduiLeftMenuCommand;
    /**
     * When we do not paint the menu bar, such as with BlackBerries and IBM's
     * J9 JVM, this contains the right menu choice.  Otherwise this will be
     * <code>null</code>.
     * 
     * @see #lcduiLeftMenuCommand
     */
    public Command lcduiRightMenuCommand;
    /**
     * Check whether this screen on device or no.
     */
    public boolean isStop = false;
    /**
     * Graphics object which is used to paint all others.
     */
    public Graphics g = null;

    /**
     * Static initializer for data that is shared by all screens.
     */
    static {
        String platform = System.getProperty("microedition.platform");
        platform = platform.toLowerCase();

        // Check if running on a BlackBerry.
        try {
            Class.forName("net.rim.device.api.ui.UiApplication");
            blackberry = true;
        } catch (Throwable e) // ClassNotFoundException, NoClassDefFoundError
        {
            blackberry = false;
        }

        // Check if running on IBM's J9 JVM.
        try {
            Class.forName("java.lang.J9VMInternals");
            ibmJ9 = true;
        } catch (Throwable e) // ClassNotFoundException, NoClassDefFoundError
        {
            ibmJ9 = false;
        }

        // Check if running on Tao's JVM.
        if (platform.indexOf("intent") > -1) {
            tao = true;
        } else {
            tao = false;
        }
    }
    public long lastTime = 0;

    /**
     * Constructs a wrapper for a <code>Canvas</code>.
     * 
     * @param master is the J4ME screen that uses this object. 
     */
    public CanvasWrapper(DeviceScreen master) {
        super(false);
        // System.out.println("1 times constructor ");
        this.master = master;

        // Always remove the menu bar and replace with our own.
        //  There are special cases, like BlackBerry's and IBM's J9
        //  on Windows Mobile, where this is not true.  These are
        //  handled by setMenuText() and other methods.
        setFullScreenMode(true);
        //Thread thread = new Thread(this);
        //thread.start();
        // Register for getting LCDUI menu commands.
        //setCommandListener(this);
//        this.setFullScreenMode(true);

        g = this.getGraphics();
        this.checkMemory();
    }

    /**
     * Set new value for isStop
     * @param stopValue 
     */
    public synchronized void setStop(boolean stopValue) {
        this.isStop = stopValue;
    }

    /**
     * Main loop for update screen frequently
     */
    public void run() {
        //     System.out.println("Enter slave run loop");
        if (UIManager.getOrderScreen() == Runner.WELCOME) {
            this.runForWelcome();
        }
        while (!isStop) {
            paint(g);
            //g.setColor(0, 0, 0);      
//            g.setColor(0, 0, 0);
//            g.fillRect(40, 40, 100, 100);
//            g.setColor(255, 255, 255);
//            g.drawString(totalMemory, 0, getHeight() - 60, Graphics.LEFT | Graphics.TOP);
//            g.drawString(freeMemory, 0, getHeight() - 40, Graphics.LEFT | Graphics.TOP);
            flushGraphics();
            try {
                Thread.sleep(150);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void runForWelcome() {
        long lastTimeWelcome = System.currentTimeMillis();
        while (!isStop) {
            g.drawImage(ResManager.getWelcome(), 0, 0, Graphics.LEFT | Graphics.TOP);
//            g.setColor(0, 0, 0);
//            g.fillRect(40, 40, 100, 100);
//            g.setColor(255, 255, 255);
//            g.drawString(totalMemory, 50, 50, Graphics.LEFT | Graphics.TOP);
//            g.drawString(freeMemory, 50, 80, Graphics.LEFT | Graphics.TOP);
            if (System.currentTimeMillis() > lastTimeWelcome + Runner.WELCOME_TIMEOUT) {
                UIManager.incOrderScreen();
                g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
                flushGraphics();
                break;
            }
            flushGraphics();
            try {
                Thread.sleep(80);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Causes the <code>keyRepeated</code> method to fire on devices that do
     * not natively support it.
     */
    public final class KeyRepeater
            extends TimerTask {

        public int key;

        public KeyRepeater(int key) {
            this.key = key;
        }

        public void run() {
            if (master.isShown()) {
                try {
                    master.keyRepeated(key);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    /**
     * If <code>keyRepeatTimer</code> is running, this method stops it.
     */
    public /*synchronized*/ void stopRepeatTimer() {
        if (keyRepeatTimer != null) {
            keyRepeatTimer.cancel();
            keyRepeatTimer = null;
        }
    }

    /**
     * Called when the user presses any key.  This method checks
     * for joystick movements which work the Yardage Anywhere
     * cursor.
     * 
     * @param key is code of the key that was pressed.
     */
    protected void keyPressed(int key) {
        //this.checkMemory();
        if (System.currentTimeMillis() > lastTime + REPEAT_PERIOD) {
            lastTime = System.currentTimeMillis();
        } else {
            return;
        }
        int translatedKey = translateKeyCode(key);

        // Stop simulating key repeated events.
        //   Holding a key down, then pressing another key, will stop
        //   the first key's keyReleased() method from being called on
        //   some phones like Sony Ericssons.  Kill the repeat timer
        //   here so that key no longer receives keyRepeat() events.
        stopRepeatTimer();

        // Notify the master.
        master.keyPressed(translatedKey);

        // If this is a menu key raise an event.
        if (translatedKey == DeviceScreen.MENU_LEFT) {
            // Highlight the menu option immediately.
            if (master.hasMenuBar()) {
                highlightLeftMenu = true;
                repaintMenuBar(true);
            }

            // Raise a menu event.
            master.declineNotify();
        } else if (translatedKey == DeviceScreen.MENU_RIGHT) {
            // Highlight the menu option immediately.
            if (master.hasMenuBar()) {
                highlightRightMenu = true;
                repaintMenuBar(true);
            }

            // Raise a menu event.
            master.acceptNotify();
        }

        // Do not forward the key event!
        //  super.keyPressed and .keyReleased can cause platform-specific
        //  and often undesirable behavior.  For example on the Sony Ericsson
        //  w810i they can turn on the music player or browser.

        // Start a timer for generating key repeat events.
        //if (keyRepeatTimer == null) {
        //  synchronized (this) // synchronize so we don't ever create more than one timer
        //{
        //   keyRepeatTimer = new Timer();
        //  keyRepeatTimer.schedule(new KeyRepeater(translatedKey), REPEAT_PERIOD, REPEAT_PERIOD);
        // }
        // }
    }

    /**
     * Called when the user releases any key.
     * 
     * @param key is code of the key that was released.
     */
    protected void keyReleased(int key) {
        // Stop simulating key repeated events.
        stopRepeatTimer();

        // Notify the master.
        int translatedKey = translateKeyCode(key);
        master.keyReleased(translatedKey);

        // If this is a menu key stop highlighting it.
        if (master.hasMenuBar()) {
            if (translatedKey == DeviceScreen.MENU_LEFT) {
                highlightLeftMenu = false;
                repaintMenuBar(false);
            } else if (translatedKey == DeviceScreen.MENU_RIGHT) {
                highlightRightMenu = false;
                repaintMenuBar(false);
            }
        }

        // Do not forward the key event!
        //  super.keyPressed and .keyReleased can cause platform-specific
        //  and often undesirable behavior.  For example on the Sony Ericsson
        //  w810i they can turn on the music player or browser.
    }

    /**
     * Maps key values to the constants defined in the outter class.
     * 
     * @param key is code of the button pressed.
     * @return The integer value for the key.
     */
    public int translateKeyCode(int key) {
        // Some phones have a bug where they treat the FIRE key as "Enter". 
        if (tao && (key == 13)) {
            return DeviceScreen.FIRE;
        }

        // Is it a normal key?
        //   BlackBerry devices give the trackwheel and trackball movements
        //   as values through 6 and getGameAction does not translate them.
        //   There are no ASCII values used below 8 (backspace) so this is
        //   fine.
        if (key > 6) {
            return key;
        }

        // Is it a well defined game key such as a joystick movement?
        int action;

        try {
            action = getGameAction(key);
        } catch (Exception e) {
            // Some phones throw an exception for unsupported keys.
            // For example the Sony Ericsson K700.
            return key;
        }

        if (action != 0) {
            // We make all action keys negative.  This allows code to
            // check for special keys by seeing if the value is less
            // than 0.
            return -1 * action;
        }

        // Is it the left menu button?
        if ((key == -6) || (key == -21) || (key == -1)) {
            // -6:   The Sun WTK emulator and Sony Ericcson phones
            // -21:  Motorola phones such as the SLVR
            // -1:   Siemens
            return DeviceScreen.MENU_LEFT;
        }

        // Is it the right menu button?
        if ((key == -7) || (key == -22) || (key == -4)) {
            // -7:   The Sun WTK emulator and Sony Ericcson phones
            // -22:  Motorola phones such as the SLVR
            // -4:   Siemens
            return DeviceScreen.MENU_RIGHT;
        }

        // Otherwise it is undefined such as:
        //   Motorola center "Menu" soft key:  -23
        //   Sony Ericsson "Return" soft key under the left soft key:  -11
        //   Sony Ericsson "Clear" soft key under the right soft key:  -8
        //   Sony Ericsson "Camera" key on side of phone:  -25
        //   Sony Ericsson "Volume Up" key on side of phone:  -36
        //   Sony Ericsson "Volume Down" key on side of phone:  -37
        //   Sony Ericsson "Play/Pause Music" key on side of phone:  -23 (Note this is the same as Motorola's center menu button)
        //   Sony Ericsson "Music Player" key:  -22 (Note this is the same as Motorola's right menu button)
        //   Sony Ericsson "Internet Browser" key:  (Unavailable)
        return key;
    }

    /**
     * Called when a stylus presses the screen.
     *
     * @param x is the horizontal location where the pointer was pressed
     * @param y is the vertical location where the pointer was pressed
     */
    protected void pointerPressed(int x, int y) {
        Theme theme = UIManager.getTheme();
        boolean processed = false;

        // Was the stylus pressed over a menu item?
        if (master.hasMenuBar()) {
            int menuHeight = theme.getMenuHeight();
            int menuStart = super.getHeight() - menuHeight;

            if (y > menuStart) {
                // The user clicked on the menu.
                int width = super.getWidth();

                if (x < (width / 2)) {
                    // The left menu item was clicked.
                    master.declineNotify();
                } else {
                    // The right menu item was clicked.
                    master.acceptNotify();
                }

                processed = true;
            }
        }

        // Was the stylus pressed over the title bar?
        boolean hasTitle = master.hasTitleBar();

        if ((processed == false) && (hasTitle == true)) {
            int titleHeight = theme.getTitleHeight();

            if (y < titleHeight) {
                // Ignore clicks on the title bar.
                processed = true;
            }
        }

        // Notify the master.
        if (processed == false) {
            // Adjust the press location to fit in the canvas area.
            int py = y;

            if (hasTitle) {
                py -= theme.getTitleHeight();
            }

            // Forward the event.
            master.pointerPressed(x, py);
        }

        // Continue processing the pointer event.
        super.pointerPressed(x, y);
    }

    /**
     * Called when a stylus drags across the screen.
     *
     * @param x is the horizontal location where the pointer was pressed
     * @param y is the vertical location where the pointer was pressed
     */
    protected void pointerDragged(int x, int y) {
        // Adjust the press location to fit in the canvas area.
        int py = y;

        if (master.hasTitleBar()) {
            Theme theme = UIManager.getTheme();
            py -= theme.getTitleHeight();
        }

        // Notify the master.
        master.pointerDragged(x, py);

        // Forward the pointer event.
        super.pointerDragged(x, y);
    }

    /**
     * Called when a stylus is lifted off the screen.
     *
     * @param x is the horizontal location where the pointer was pressed
     * @param y is the vertical location where the pointer was pressed
     */
    protected void pointerReleased(int x, int y) {
        // Adjust the press location to fit in the canvas area.
        int py = y;

        if (master.hasTitleBar()) {
            Theme theme = UIManager.getTheme();
            py -= theme.getTitleHeight();
        }

        // Notify the master.
        master.pointerReleased(x, py);

        // Forward the pointer event.
        super.pointerReleased(x, y);
    }

    /**
     * Sets the title bar text.  This is called by the <code>master</code> when
     * its title is changed.
     * 
     * @param title is the text that appears in the title bar across the
     *  top of the screen.
     *  
     * @see DeviceScreen#setTitle(String)
     */
    public void setTitle(String title) {
        // Does this JVM support our title bar feature?
        if (supportsTitleBar() == false) {
            // These JVMs always shows a title bar.  We might as well use that
            // instead of painting a duplicate on our own.
            super.setTitle(title);
        }

        // For other JVMs we'll display the title on our own.  There is no
        // need to have the LCDUI do it for us.
    }

    /**
     * Sets the menu bar text.  This is called by the <code>master</code> when
     * its menu is changed.
     * 
     * @param left is the text for the negative menu option or <code>null</code>
     *  to remove the button.  Negative menu options are things like canceling
     *  a form and moving back to a previous screen.
     * @param right is the text for the positive menu option or <code>null</code>
     *  to remove the button.  Positive menu options are things like accepting
     *  a form, advancing to the next screen, or displaying a menu.
     *  
     * @see DeviceScreen#setMenuText(String, String)
     */
    public void setMenuText(String left, String right) {
        // Does this JVM support our own menu bar?
        if (supportsMenuBar() == false) {
            // BlackBerry phones do not have MIDP 2.0 left and right menu buttons.
            // We must capture the BlackBerry phone's "Menu" and "Return" buttons
            // using the LCDUI menu functionality.
            //
            // IBM's J9 does not forward the left and right menu button codes to
            // us.  We have to use LCDUI menu functionality.

            // Remove the existing menu commands.
            if (lcduiLeftMenuCommand != null) {
                removeCommand(lcduiLeftMenuCommand);
                lcduiLeftMenuCommand = null;
            }

            if (lcduiRightMenuCommand != null) {
                removeCommand(lcduiRightMenuCommand);
                lcduiRightMenuCommand = null;
            }

            // Register new LCDUI menu commands.
            if (left != null) {
                int position;

                if (blackberry) {
                    // This will be hooked up to the "Return" key and appear
                    // second on the BlackBerry menu.
                    position = 2;
                } else // ibmJ9
                {
                    // This will make the button appear on the left side of the menu.
                    position = 1;
                }

                lcduiLeftMenuCommand = new Command(left, Command.CANCEL, position);
                addCommand(lcduiLeftMenuCommand);
            }

            if (right != null) {
                int position;

                if (blackberry) {
                    // This will appear first on the BlackBerry menu.
                    position = 1;
                } else // ibmJ9
                {
                    // This will make the button appear on the right side of the menu.
                    position = 2;
                }

                lcduiRightMenuCommand = new Command(right, Command.OK, position);
                addCommand(lcduiRightMenuCommand);

                // Add a dummy left menu for IBM's J9 JVM.  Otherwise the right
                // menu would actually be on the left.
                if ((ibmJ9 || tao) && (left == null)) {
                    lcduiLeftMenuCommand = new Command("", Command.CANCEL, 1);
                    addCommand(lcduiLeftMenuCommand);
                }
            }
        }
    }

    /**
     * Indicates that a command event <code>c</code> has occurred on
     * <code>Displayable<code> d.
     * 
     * @param c is a <code>Command</code> object identifying the command.
     * @param d is the <code>Displayable</code> on which this event has occurred.
     *  It will always be <code>this</code> screen.
     *  
     * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
     */
    public void commandAction(Command c, Displayable d) {
        // Was it our left menu command?
        if ((lcduiLeftMenuCommand != null) && (c == lcduiLeftMenuCommand)) {
            keyPressed(DeviceScreen.MENU_LEFT);
            keyReleased(DeviceScreen.MENU_LEFT);
        }

        // Was it our right menu command?
        if ((lcduiRightMenuCommand != null) && (c == lcduiRightMenuCommand)) {
            keyPressed(DeviceScreen.MENU_RIGHT);
            keyReleased(DeviceScreen.MENU_RIGHT);
        }
    }

    /**
     * Returns if the device supports having a menu bar at the bottom of the
     * screen.  If not then no menu bar will painted at the bottom of the
     * screen and a standard LCDUI menu will be used instead.
     * 
     * @return <code>true</code> if the device supports a menu bar or <code>false</code>
     *  if it does not.
     */
    public boolean supportsMenuBar() {
        if (blackberry || ibmJ9 || tao) {
            // These JVMs do not show our menu bar at the bottom of the
            // screen.  Instead they use the LCDUI menu system.
            return false;
        } else {
            // This phone can have a menu bar at the bottom of the screen.
            return true;
        }
    }

    /**
     * Returns if the device supports having a title bar at the top of the
     * screen.  If not then no title bar will painted at the top of the
     * screen and a standard LCDUI title will be used instead.
     * 
     * @return <code>true</code> if the device supports a title bar or <code>false</code>
     *  if it does not.
     */
    public boolean supportsTitleBar() {
        if (ibmJ9 || tao) {
            // These JVMs always show a title bar.  We might as well use it.
            return false;
        } else {
            // This phone can have a title bar at the top of the screen.
            return true;
        }
    }

    /**
     * Called when this screen is no longer going to be displayed.
     * 
     * @see javax.microedition.lcdui.Canvas#hideNotify()
     */
    public void hideNotify() {
        // If the key repeat timer is running, stop it.
        stopRepeatTimer();

        // Don't highlight the menu options (in case we return to this screen).
        highlightLeftMenu = false;
        highlightRightMenu = false;

        // Continue to hide the screen.
        super.hideNotify();
    }

    /**
     * Forces a repaint of the menu bar.
     * 
     * @param immediate when <code>true</code> finishes the painting before
     *  this method returns; <code>false</code> does it in the normal paint
     *  cycle.
     */
    public void repaintMenuBar(boolean immediate) {
        Theme theme = UIManager.getTheme();
        int menuHeight = theme.getMenuHeight();
        int y = getHeight() - menuHeight;

        this.repaint(0, y, getWidth(), menuHeight);
//
//        if (immediate) {
//            this.serviceRepaints();
//        }
    }

    /**
     * Paints the screen.  If not in full screen mode, this includes the
     * title bar and menu bar.
     * <p>
     * Typically derviced classes will only override the <code>paintCanvas</code>
     * method.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @see #paintTitleBar(Graphics, String, int, int, int, int)
     * @see #paintBackground(Graphics, int, int, int, int)
     * @see #paintCanvas(Graphics, int, int, int, int)
     * @see #paintMenuBar(Graphics, String, String, int, int, int, int)
     */
    public void paint(Graphics g) {
//        counter++;
        //  System.out.println(">>Call paint at : " + counter + "times");
//        if (UIManager.getOrderScreen() == Runner.WELCOME) {
//            g.drawImage(ResManager.getWelcome(), 0, 0, Graphics.LEFT | Graphics.TOP);
//            if (System.currentTimeMillis() > lastTimeWelcome + Runner.WELCOME_TIMEOUT) {
//                UIManager.incOrderScreen();
//                lastTimeWelcome = 0;
//                UIManager.getNewsSourceMenu().show();
//            }
//            return;
//        }
        try {
            // Get some painting attributes.
            Theme theme = UIManager.getTheme();

            int width = getWidth();
            int height = getHeight();

            int titleHeight = 0;
            int menuHeight = 0;

            String title = null;
            String leftMenuText = null;
            String rightMenuText = null;

            // Get the original clip.
            int clipX = g.getClipX();
            int clipY = g.getClipY();
            int clipWidth = g.getClipWidth();
            int clipHeight = g.getClipHeight();

            // Paint the title bar and/or menu bar?
            if (master.isFullScreenMode() == false) {
                // Paint the title bar at the top of the screen.
                title = master.getTitle();

                if (master.hasTitleBar()) {
                    // We'll paint the title after the canvas area.
                    titleHeight = theme.getTitleHeight();
                }

                // Paint the menu bar at the bottom of the screen.
                if (master.hasMenuBar()) {
                    // Set the menu text.
                    leftMenuText = master.getLeftMenuText();
                    rightMenuText = master.getRightMenuText();

                    if (leftMenuText == null) {
                        leftMenuText = "";
                    }

                    if (rightMenuText == null) {
                        rightMenuText = "";
                    }

                    // Set the height of the menu.
                    menuHeight = theme.getMenuHeight();
                }

                // Set the graphics object for painting the canvas area.
                height = height - titleHeight - menuHeight;

                g.translate(0, titleHeight);
                master.paintFullBackground(g);
                g.clipRect(5, 5, width - 10  , height - 10);
            }

            // Paint the canvas area.
            if (DeviceScreen.intersects(g, 0, 0, width, height)) {
                master.paintBackground(g);

                g.setFont(theme.getFont());
                g.setColor(theme.getFontColor());
                master.paint(g);
            }

            // Restore the original graphics object.
            g.translate(0, -titleHeight);
            g.setClip(clipX, clipY, clipWidth, clipHeight);

            // Paint the title bar.
            //  We do this after the canvas so it will paint over any
            //  canvas spillage.
            if (titleHeight > 0) {
                if (DeviceScreen.intersects(g, 0, 0, width, titleHeight)) {
                    // Set the graphics object for painting the title bar.
                    g.clipRect(0, 0, width, titleHeight);

                    // Actually paint the title bar.
                    master.paintTitleBar(g, title, width, titleHeight);

                    // Restore the original graphics object.
                    g.setClip(clipX, clipY, clipWidth, clipHeight);
                }
            }

            // Paint the menu bar.
            if (menuHeight > 0) {
                int y = getHeight() - menuHeight;

                if (DeviceScreen.intersects(g, 0, y, width, menuHeight)) {
                    // Set the graphics object for painting the menu bar.
                    g.translate(0, y);
                    g.clipRect(0, 0, width, menuHeight);

                    // Clear the background first.
                    //   On the Sony Ericsson w810i things drawn accidentally
                    //   over the menu space can show through gradient backgrounds.
                    //   Clear the area completely first.
                    int menuBackgroundColor = theme.getMenuBarBackgroundColor();
                    g.setColor(menuBackgroundColor);
                    g.fillRect(0, 0, width, menuHeight);

                    // Actually paint the menu bar.
                    master.paintMenuBar(g,
                            leftMenuText, highlightLeftMenu,
                            rightMenuText, highlightRightMenu,
                            width, menuHeight);

                    // Restore the original graphics object.
                    g.translate(0, -y);
                    g.setClip(clipX, clipY, clipWidth, clipHeight);
                }
            }
        } catch (Throwable t) {
            // Unhandled exception in paint() will crash an application and not
            // tell you why.  This lets the programmer know what caused the problem.
            t.printStackTrace();
        }
    }

    public void checkMemory() {
        totalMemory = String.valueOf(Runtime.getRuntime().totalMemory());
        freeMemory = String.valueOf(Runtime.getRuntime().freeMemory());
    }
}
