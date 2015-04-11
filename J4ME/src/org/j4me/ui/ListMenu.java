package org.j4me.ui;

import MainProcess.ConnectionCenter;
import MainProcess.IReceiver;
import MainProcess.RMS;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.j4me.examples.ui.Runner;
import org.j4me.ui.components.*;
import res.Adver;
import res.ResManager;

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
public class ListMenu
        extends Dialog implements IReceiver {

    public boolean isNodata = false;
    public int uni_que_id = 0;
    public Image headerIcon = null;
    /**
     * The screen that invoked this one or <code>null</code> if there is no
     * previous screen
     */
    public DeviceScreen previous;

    /**
     * Constructs a menu.
     */
    public ListMenu() {
        // No spacing between components.
        //   The MenuOption component will add spacing for us.
        setSpacing(10);
        // Add the menu bar.
        Theme theme = UIManager.getTheme();
        String rightMenuText = theme.getMenuTextForOK();
        setMenuText(null, rightMenuText);
        //System.out.println("(Width,Height) == (" + getWidth() + "," + getHeight() + ")");
        ResManager.res.setListArea(getWidth(), getHeight() - 2 * margin);

    }

    /**
     * Constructs a menu.
     */
    public ListMenu(int max_number_element) {
        // No spacing between components.
        //   The MenuOption component will add spacing for us.
        super(max_number_element);
        setSpacing(10);
        // Add the menu bar.
        Theme theme = UIManager.getTheme();
        String rightMenuText = theme.getMenuTextForOK();
        setMenuText(null, rightMenuText);
        //System.out.println("(Width,Height) == (" + getWidth() + "," + getHeight() + ")");
        ResManager.res.setListArea(getWidth(), getHeight() - 2 * margin);

    }

    /**
     * Constructs a menu.
     * 
     * @param name is the title for this menu, for example "Main ListMenu".  It
     *  appears at the top of the screen in the title area.
     * @param previous is the screen to return to if the user cancels this.
     */
    public ListMenu(String name, DeviceScreen previous, int max_num_element) {
        super(max_num_element);

        this.previous = previous;
        setTitle(name);
        setPrevious(previous);
        ResManager.res.setListArea(getWidth(), getHeight() - 2 * margin);
    }

    /**
     * Constructs a menu.
     * 
     * @param name is the title for this menu, for example "Main ListMenu".  It
     *  appears at the top of the screen in the title area.
     * @param previous is the screen to return to if the user cancels this.
     */
    public ListMenu(String name, DeviceScreen previous) {
        this();
        this.previous = previous;
        setTitle(name);
        setPrevious(previous);
        ResManager.res.setListArea(getWidth(), getHeight() - 2 * margin);
    }

    /**
     * Sets the screen to return to if the user cancels this menu.  If
     * <code>previous</code> is <code>null</code>, there will be no "Cancel" button.
     * 
     * @param previous is the screen to go to if the user presses "Cancel".
     */
    public void setPrevious(DeviceScreen previous) {
        // Record the previous screen.
        this.previous = previous;

        // Set the menu text.
        Theme theme = UIManager.getTheme();
        String leftMenuText = (previous == null ? null : theme.getMenuTextForCancel());
        String rightMenuText = theme.getMenuTextForOK();
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
     * Appends a new menu option to this menu.
     * 
     * @param option is the menu item to add.
     */
    public void appendMenuOption(MenuItem option, int layout) {
        MenuOption choice = new MenuOption(option, layout);
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
        MenuOption choice = new MenuOption(uni_que_id++, text, option);
        append(choice);
    }

    /**
     * Appends a screen as a menu option.  If selected the screen will be
     * shown.
     * 
     * @param text is string that appears in the menu option.
     * @param option is screen to add as a menu item.
     */
    public void appendMenuOption(String text, DeviceScreen option, int layout) {
        MenuOption choice = new MenuOption(uni_que_id++, text, option, layout);
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
     * Appends a screen as a menu option.  If selected the screen will be
     * shown.
     * 
     * @param text is string that appears in the menu option.
     * @param option is screen to add as a menu item.
     */
    public void appendOwnElement(int id, String text, DeviceScreen option) {
        MenuOption choice = new MenuOption(id, text, option);
        if (headerIcon != null) {
            choice.headerIcon = headerIcon;
        }
        append(choice);
    }

    /**
     * Appends a screen as a menu option.  If selected the screen will be
     * shown.
     * 
     * @param text is string that appears in the menu option.
     * @param option is screen to add as a menu item.
     */
    public void appendOwnElement(String text, DeviceScreen option) {
        MenuOption choice = new MenuOption(uni_que_id++, text, option);
        append(choice);
    }

    /**
     * The left menu button takes the user back to the previous screen.
     * If there is no previous screen it has no effect.
     */
    public void declineNotify() {
        //System.out.println("List view :" + UIManager.getOrderScreen());
        UIManager.decOrderScreen();
        // Go back to the previous screen.
        if (previous != null) {
            previous.show();
        }

        // Continue processing the event.
        super.declineNotify();
    }

    /**
     * The right menu button selects the highlighted menu item.
     */
    public void acceptNotify() {
        // Go to the highlighted screen.
        int highlighted = getSelected();
        selection(highlighted);

        // Continue processing the event.
        super.acceptNotify();
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
        if (key == DOWN) {
            if (getSelected() == size() - 1) {
                // Go to the first menu choice.
                // goToFirst = true;
            }
        } else if (key == UP) {
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
            setSelected(0);
        } else if (goToLast) {
            setSelected(size() - 1);
        }
    }

    /**
     * Selects a menu item.
     * 
     * @param selection is the index of <code>choice</code> that is selected.
     */
    public void selection(int selection) {
//        System.out.println("Selected id :" + selection);
        Component component = get(selection);

        if (component instanceof MenuOption) {
            MenuOption chosen = (MenuOption) component;

            // Record this as the selection.
            setSelected(selection);

            // Perform the selection operation.
            chosen.select();
        }
    }

    public void processMessage(String data) {
        data = data.trim();
        if (ConnectionCenter.getConnectionState() == ConnectionCenter.NOT_CONNECTED) {
            //   System.out.println("Not connected");
            return;
        }
        // System.out.println("Element : " + data);
        if (data.compareTo("ERROR") == 0) {
            //    System.out.println("Re-send");
        } else if (data.compareTo("ACTIVED") == 0) {
            String temp = data.substring(8);
            if (temp.length() != 0) {
                RMS.setApp_id(temp);
            }
        } else if (data.compareTo("NO_DATA") == 0) {
            isNodata = true;
        } else if (data.compareTo("ADV_RESPONSE") == 0) {
            //  System.out.println("Adv response message : " + data);
            Adver.setAdv(data.substring(12));
        } else {
            isNodata = false;
            if (UIManager.getOrderScreen() == Runner.CATEGORY_LIST_MENU) {
                int fieldIndex = data.indexOf('|');
                int order = Integer.parseInt(data.substring(0, fieldIndex));
                if (ConnectionCenter.getNumberofCategory() == 0) {
                    ConnectionCenter.setNumberOfCategory(order);
                    return;
                }
                this.appendOwnElement(order, data.substring(fieldIndex + 1, data.length()), UIManager.getArticleList());
                //this.invalidated = true;
            }
            if (UIManager.getOrderScreen() == Runner.ARTICLE_LIST_MENU) {
                //    System.out.println("Article label : " + data);
                int fieldIndex = data.indexOf('|');
                int articleId = Integer.parseInt(data.substring(0, fieldIndex));
                this.appendOwnElement(articleId, data.substring(fieldIndex + 1, data.length()), UIManager.getContentView());
                if (this.highlightedComponent == -1) {
                    this.setSelected(0);
                }
                //this.invalidated = true;
            }
        }
    }

    public synchronized void removeLastElement() {
        if (this.size() > 0) {
            this.delete(this.size() - 1);
            this.real_number_component--;
        }
    }

    public void reset() {
        this.deleteAll();
        isNodata = false;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (ConnectionCenter.getConnectionState() <= ConnectionCenter.NOT_CONNECTED) {
            if (UIManager.getOrderScreen() > Runner.WELCOME) {
                g.drawString("Không có kết nối", 30, this.getHeight() / 2 - 30, Graphics.LEFT | Graphics.TOP);
            }
            return;
        }
        if (isNodata) {
            g.drawString("Không có dữ liệu", 30, this.getHeight() / 2 - 15, Graphics.LEFT | Graphics.TOP);
        } else {
            if (this.size() == 0) {
                g.drawString("Đang tải dữ liệu", 30, this.getHeight() / 2 - 15, Graphics.LEFT | Graphics.TOP);
            }
        }
        if (UIManager.getOrderScreen() == Runner.CATEGORY_LIST_MENU) {
            if (Runner.getApp_id().compareTo("000") == 0) {
                g.drawString("Đang chờ mã kích hoạt", 30, this.getHeight() / 2, Graphics.LEFT | Graphics.TOP);
            }
        }
    }

    public void setTitle(String title) {
        super.setTitle(title + " - " + Adver.getText());
    }

    public void processMessage(byte[] data) {
    }

    /**
     * Moves the viewport of the form up or down and calls <code>repaint</code>.
     * <p>
     * The amount scrolled is dependent on the components shown.  Typically
     * one scroll increment advances to the next component.  However, if
     * components are taller than the screen they will be partially scrolled.
     * 
     * @param down is <code>true</code> when the form should scroll down and 
     *  <code>false</code> when it should scroll up.
     */
    public void scroll(boolean down) {
        // Safety checks.
        if (absoluteHeights == null) {
            // Calculated the layout.
            hasVerticalScrollbar();
        }

        if (components.isEmpty()) {
            // Can't scroll with no components.
            return;
        }

        // Get the dimensions of the form.
        int topOfForm = 0;
        int screenHeight = getHeight();
        int bottomOfForm = absoluteHeights[real_number_component] - screenHeight;
        int bottomOfScreen = topOfScreen + screenHeight;

        // We scroll 90% of the screen unless there is another highlightable
        // component within that 90%.  In which case we scroll only to the
        // highlightable component, not the full 90%.
        int max = screenHeight * 4 / 10;

        // Get the next component that can be highlighted.
        int current = highlightedComponent;
        highlightedComponent = nextHighlightableComponent(down, max);

        // Scroll.
        if (hasVerticalScrollbar()) {
            // Calculate the number of pixels to scroll the form.
            int scroll;

            // Calculate how far to scroll to get to the next highlighted component.
            if (down) {
                int currentBottom = absoluteHeights[current + 1];
                int nextTop = absoluteHeights[highlightedComponent];
                int nextBottom = absoluteHeights[highlightedComponent + 1];
                if (currentBottom > bottomOfScreen) {
                    // The current component actually is clipped by the bottom of
                    // the screen (because it is too big to show all at once).
                    // Just scroll down we can see more of it.
                    scroll = max;
                    if (nextBottom > bottomOfScreen) {
                        scroll = nextTop - topOfScreen;
                    }
                } else {
                    // Get the screen position of the next highlightable component.


                    if ((nextTop > topOfScreen) && (nextBottom < bottomOfScreen)) {
                        // Don't scroll if the next highlighted component fits
                        // completely on the screen already.
                        scroll = 0;
                    } else {
                        scroll = nextTop - topOfScreen;
                    }
                }
            } else // up
            {
                int currentTop = absoluteHeights[current];

                if (currentTop < topOfScreen) {
                    // The current component actually is clipped by the top of
                    // the screen (because it is too big to show all at once).
                    // Just scroll up we can see more of it.
                    scroll = max;
                } else {
                    if (highlightedComponent == 0) {
                        // Scroll to the very top of the dialog.
                        scroll = topOfScreen;
                    } else {
                        // Get the bottom of the component above the next highlighted one.
                        int previousBottom = absoluteHeights[highlightedComponent] - spacing;

                        scroll = topOfScreen - previousBottom;

                        // Don't scroll if the next highlighted component fits
                        // completely on the screen already.
                        if (previousBottom >= topOfScreen) {
                            scroll = 0;
                        }
                    }
                }
            }

//            // Set the position of the form.
//            if (scroll > max) {
//                scroll = max;
//            }

            if (down == false) {
                // If scrolling up, set the scroll to a negative number.
                scroll *= -1;
            }

            topOfScreen += scroll;

            if (topOfScreen < topOfForm) {
                topOfScreen = topOfForm;
            } else if (topOfScreen > bottomOfForm) {
                topOfScreen = bottomOfForm;
            }
        }
        // Redraw the screen at the scrolled position.
        ///repaint();
    }
}