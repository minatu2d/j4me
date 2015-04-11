package org.j4me.ui;

import MainProcess.IReceiver;
import javax.microedition.lcdui.Graphics;
import org.j4me.examples.ui.Runner;
import org.j4me.ui.components.*;
import res.Adver;

/**
 * The <code>ListMenu</code> class is used for creating the application's menus.
 * <p>
 * J2ME devices have small screens and are not all very responsive to scrolling.
 * However, they do have keypads.  The <code>ListMenu</code> class respects this and
 * limits menus to a total of 9 possible choices (1-9) plus "Exit".  Usually all
 * the choices can be seen on a single screen and selected with a single button.
 * <p>
 * Override this class to change how the menu is painted for your application.
 */
public class GridMenu
        extends Grid implements IReceiver {

    /**
     * The screen that invoked this one or <code>null</code> if there is no
     * previous screen
     */
    public DeviceScreen previous;

    /**
     * Constructs a menu.
     */
    public GridMenu() {
        // No spacing between components.
        //   The MenuOption component will add spacing for us.
        setSpacing(0);
        // Add the menu bar.
        Theme theme = UIManager.getTheme();
        String rightMenuText = theme.getMenuTextForOK();
        String leftMenuText = theme.getMenuTextForExit();
        setMenuText(leftMenuText, rightMenuText);
    }

    /**
     * Constructs a menu.
     * 
     * @param name is the title for this menu, for example "Main ListMenu".  It
     *  appears at the top of the screen in the title area.
     * @param previous is the screen to return to if the user cancels this.
     */
    public GridMenu(String name, DeviceScreen previous) {
        this();

        this.previous = previous;
        setTitle(name);
        setPrevious(previous);
    }

    /**
     * Sets the screen to return to if the user cancels this menu.  If
     * <code>previous</code> is <code>null</code>, there will be no "Cancel" button.
     * 
     * @param previous is the screen to go to if the user presses "Cancel".
     */
    private void setPrevious(DeviceScreen previous) {
        // Record the previous screen.
        this.previous = previous;

        // Set the menu text.
        Theme theme = UIManager.getTheme();
        String leftMenuText = theme.getMenuTextForOption();
        String rightMenuText = theme.getMenuTextForExit();
        setMenuText(leftMenuText, rightMenuText);
    }

    /**
     * Appends a new menu option to this menu.
     * 
     * @param option is the menu item to add.
     */
    public void appendMenuOption(MenuItem option) {
        MenuOption choice = new MenuOption(option);
        append(choice);
    }

    /**
     * Appends a screen as a menu option.  If selected the screen will be
     * shown.  The screen's title is used as its text.
     * 
     * @param option is screen to add as a menu item.
     */
    public void appendMenuOption(DeviceScreen option) {
        MenuOption choice = new MenuOption(option);
        append(choice);
    }

    /**
     * Appends a screen as a menu option.  If selected the screen will be
     * shown.
     * 
     * @param text is string that appears in the menu option.
     * @param option is screen to add as a menu item.
     */
    public void appendMenuOption(String text, DeviceScreen option) {
        MenuOption choice = new MenuOption(text, option);
        append(choice);
    }

    /**
     * Appends another menu as a menu option.  The submenu will have an
     * arrow next to it to indicate to the user it is another menu.
     * <p>
     * To use a <code>ListMenu</code> as a screen and not a submenu call the
     * <code>appendMenuOption</code> method instead.
     * 
     * @param submenu is the screen to add as a menu item.
     */
    public void appendSubmenu(ListMenu submenu) {
        MenuOption choice = new MenuOption(submenu, true);
        append(choice);
    }

    /**
     * The left menu button takes the user back to the previous screen.
     * If there is no previous screen it has no effect.
     */
    public void declineNotify() {
        // System.out.println("Grid view :" + UIManager.getOrderScreen());
        UIManager.setOrderScreen(Runner.GENERAL_PREFERENCE_VIEW);
        // Go back to the previous screen.
        if (previous != null) {
            previous.setTitle("Setting");
            previous.show();
        }
        // System.out.println("Soft left pressed");
        // Continue processing the event.
        super.declineNotify();
    }

    /**
     * The right menu button selects the highlighted menu item.
     */
    public void acceptNotify() {
        // Go to the highlighted screen.
        int highlighted = getSelected();
       // selection(highlighted);
        // Continue processing the event.
        //super.acceptNotify();
        Runtime.getRuntime().gc();
        UIManager.destroy();
    }

    /**
     * Responds to key press events that are specific to menu screens.
     * Selects the highlighted menu choice if the joystick's
     * <code>FIRE</code> key is pressed.  Scrolls from the last choice to
     * the first choice if <code>DOWN</code> is pressed and from the first to
     * the last if <code>UP</code> is pressed.
     * 
     * @param key is the key code of the button the user pressed.
     */
    public void keyPressed(int key) {

        boolean goToFirst = false;
        boolean goToLast = false;

        // Wrap the scroll around the screen?
        if ((key == DOWN) || (key == RIGHT)) {
            if (getSelected() == size() - 1) {
                // Go to the first menu choice.
                // goToFirst = true;
            }
        } else if ((key == UP) || (key == LEFT)) {
            if ((getSelected() == 0) && (size() > 1)) {
                // Go to the last menu choice.
                // goToLast = true;
            }
        }

        // Process the key event.
        super.keyPressed(key);

        // Were we going to the first or last menu choice?
        //   Only do these after super.keyPressed().  Otherwise
        //   keyPressed() will scroll again so we'll actually wind
        //   up on the second or second-to-last menu choice.
        if (goToFirst) {
            //  System.out.println("Go first");
            setSelected(0);
        } else if (goToLast) {
            //  System.out.println("Go last");
            setSelected(size() - 1);
        }
    }

    /**
     * Selects a menu item.
     * 
     * @param selection is the index of <code>choice</code> that is selected.
     */
    public void selection(int selection) {
        //System.out.println("Selected from GridMenu");
        Component component = get(selection);
        if (component instanceof GridElement) {
            GridElement chosen = (GridElement) component;

            // Record this as the selection.
            setSelected(selection);

            // Perform the selection operation.
            chosen.select();
        }
    }

    public void processMessage(String data) {
        System.out.println(" GridMenu >> Received data : " + data);
    }

    public void resetTitle() {
        this.setTitle("NEWS");
    }

    public void setTitle(String title) {
        super.setTitle(title + " - " + Adver.getText());
    }

    public void processMessage(byte[] data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}