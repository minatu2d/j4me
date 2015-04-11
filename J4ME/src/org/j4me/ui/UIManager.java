package org.j4me.ui;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import org.j4me.examples.ui.Runner;

/**
 * The UI manager orchestrates the UI for a MIDlet.  There is one
 * <code>UIManager</code> object for the entire application and all of its
 * methods are static.  You must call the <code>init</code> method first.
 * <p>
 * Each screen, defined by a class derived from <code>DeviceScreen</code>, can be
 * attached to the manager.  The manager is responsible for navigating
 * between screens, getting them to paint themselves, and all other UI
 * control.
 * <p>
 * The application's appearance can be changed by providing a new theme.
 * Create a new theme class, derived from <code>Theme</code>, and then attach it
 * to the UI manager.  All the different screens will take on the colors
 * and layout defined by the new theme.
 * 
 * @see DeviceScreen
 * @see Theme
 */
public class UIManager {

    /**
     * The exception message text displayed when the user tries to set
     * screens without first calling <code>init</code>.
     */
    public static final String INIT_NOT_SET_EXCEPTION = "Must first call UIManager.init";
    /**
     * The application's theme.
     * <p>
     * This is protected so other classes in this package can directly
     * access it.
     */
    public static Theme theme = new Theme();  // Default theme
    /**
     * The MIDlet's <code>Display</code> object.
     * <p>
     * This is protected so other classes in this package can directly
     * access it.
     */
    public static Display display;
    /**
     * The object currently shown on the device's screen.
     */
    public static DeviceScreen current;
    /**
     * The object next shown on the device's screen.
     */
    public static DeviceScreen next;
    public static DeviceScreen newsSourceMenu;
    public static DeviceScreen categoryList;
    public static DeviceScreen articleList;
    public static DeviceScreen contentView;
    public static DeviceScreen imageView;
    public static CanvasWrapper slave;
    public static MIDlet runner;

    /**
     * Creates a <code>UIManager</code> implementation.  One of these should
     * be created per MIDlet and be stored in the application's model.
     * <p>
     * The theme is set to the default, represented by the <code>Theme</code> class.
     * To apply your own theme use the <code>setTheme</code> method. 
     * <p>
     * Note that you should ensure your application is running on MIDP 2.0+
     * device.  Otherwise the system will throw a runtime exception that
     * will immediately kill your MIDlet without warning.
     * 
     * @param midlet is the <code>MIDlet</code> class that defines this application.
     */
    public static void init(MIDlet midlet) {
        if (midlet == null) {
            // Unfortunately J2ME devices will just exit and will not show this.
            throw new IllegalArgumentException();
        }
        UIManager.runner = midlet;
        UIManager.display = Display.getDisplay(midlet);
    }

    /**
     * Returns the theme used throughout the application.  The theme controls
     * the color schemes and fonts used by the application.  It also defines
     * the appearance of the title bar at the top of any screens and the
     * menu bar at the bottom.
     * 
     * @return The current theme of the application.
     */
    public static Theme getTheme() {
        return theme;
    }

    /**
     * Sets the theme used throughout the application.  The theme controls
     * the color schemes and fonts used by the application.  It also defines
     * the appearance of the title bar at the top of any screens and the
     * menu bar at the bottom.
     * 
     * @param theme is the new theme for the application.
     */
    public static void setTheme(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("Cannot set a null theme.");
        }
        UIManager.theme = theme;
    }

    /**
     * Sets the current screen for the application.  This is used
     * exclusively by the canvas class in this package.
     * 
     * @param canvas is the J4ME object that wraps <code>screen</code>.
     * @param screen is the actual LCDUI <code>Displayable</code> which takes
     *  over the device's screen.
     * @see DeviceScreen#show()
     */
    public static void setScreen(DeviceScreen canvas, Displayable screen) {
        if (display == null) {
            throw new IllegalStateException(INIT_NOT_SET_EXCEPTION);
        }

        if (screen == null) {
            throw new IllegalArgumentException();
        }

        // /*synchronized*/ (display) {
        // Deselect the current screen.
        if (current != null) {
            try {
                current.hideNotify();
            } catch (Throwable t) {
                // Log.warn("Unhandled exception in hideNotify() of " + current, t);
            }
        }
        if (current != null) {
            current.setStop(true);
        }
        next = canvas;
        UIManager.getSlave().master = next;
        next.setStop(false);
        // Select and set the new screen.

        try {
            next.showNotify();
        } catch (Throwable t) {
            // Log.warn("Unhandled exception in showNotify() of " + current, t);
        }

        display.setCurrent(screen);

        // Issue a repaint command to the screen.
        //  This fixes problems on some phones to make sure the screen
        //  appears correctly.  For example BlackBerry phones sometimes
        //  render only a part of the screen.
        //canvas.repaint();

//            if (Log.isDebugEnabled()) {
//                Log.debug("Screen switched to " + canvas);
//            }
        //   }
    }

    /**
     * Returns the currently selected J4ME screen.  If no screen is set, or
     * a non-J4ME screen is displayed, this will return <code>null</code>.
     * <p>
     * The application can call <code>toString</code> on the returned screen to
     * get its name for logging purposes.
     * 
     * @return The currently displayed J4ME screen or <code>null</code> if none is
     *  set.
     */
    public static DeviceScreen getScreen() {
        return current;
    }

    /**
     * Gets the <code>Display</code> for this MIDlet.
     * 
     * @return The application's <code>Display</code>.
     */
    public static Display getDisplay() {
        if (display == null) {
            throw new IllegalStateException(INIT_NOT_SET_EXCEPTION);
        }
        return display;
    }

    public static int getOrderScreen() {
        return ((Runner) runner).screen_order;
    }

    public static void decOrderScreen() {
        ((Runner) runner).screen_order = ((Runner) runner).screen_order - 1;
    }

    public static void setOrderScreen(int order) {
        ((Runner) runner).screen_order = order;
    }

    public static void incOrderScreen() {
        ((Runner) runner).screen_order = ((Runner) runner).screen_order + 1;
    }

    public static DeviceScreen getArticleList() {
        return articleList;
    }

    public static DeviceScreen getCategoryList() {
        return categoryList;
    }

    public static DeviceScreen getContentView() {
        return contentView;
    }

    public static DeviceScreen getNewsSourceMenu() {
        return newsSourceMenu;
    }

    public static DeviceScreen getImageView() {
        return imageView;
    }

    public static CanvasWrapper getSlave() {
        return slave;
    }

    public static void setSlave(CanvasWrapper slave) {
        UIManager.slave = slave;
    }

    public static void setArticleList(DeviceScreen articleList) {
        UIManager.articleList = articleList;
    }

    public static void setCategoryList(DeviceScreen categoryList) {
        UIManager.categoryList = categoryList;
    }

    public static void setContentView(DeviceScreen contentView) {
        UIManager.contentView = contentView;
    }

    public static void setNewsSourceMenu(DeviceScreen newsSourceMenu) {
        UIManager.newsSourceMenu = newsSourceMenu;
    }

    public static void setImageView(DeviceScreen imageView) {
        UIManager.imageView = imageView;
    }

    public static void destroy() {
        runner.notifyDestroyed();
    }

    /**
     * Main loop from UIManager to manage all Canvas in this application
     */
    public static void mainRun() {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                //     System.out.println("New thread created");
                while (true) {
                    if (next != null) {
                        current = next;
                        if (UIManager.getOrderScreen() == Runner.NEWS_SOURCE_GRID_MENU) {
                            ((GridMenu) UIManager.getNewsSourceMenu()).resetTitle();
                        }
                        next = null;
                    }
                    if (current == null) {
                        //      System.out.println("Exit application");
                        break;
                    }
                    try {
                        current.run();
                        current = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
