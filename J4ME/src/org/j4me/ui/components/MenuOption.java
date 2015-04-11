package org.j4me.ui.components;

import MainProcess.ConnectionCenter;
import MainProcess.IReceiver;
import javax.microedition.lcdui.*;
import org.j4me.ui.*;
import res.ResManager;

/**
 * The <code>ListMenu</code> screen uses one of these <code>MenuOption</code> components
 * for each menu choice.
 * <p>
 * The default implementation shows the choice number on the left, then
 * the text for the choice, and an arrow on the right if it is a sub-menu.
 * The text is clipped if it runs over a single line.  This keeps all
 * menu items the same height.
 * 
 * @see ListMenu
 */
public class MenuOption
        extends Component {

    public int[] preferSize = new int[2];
    public int id = 0;
    public int layout = Graphics.LEFT;
    /**
     * Check whether font change
     * 
     */
    public boolean isChangeFont = true;
    /**
     * The number of pixels that separates the text and sub-menu
     * indicator from the left and right edges of the component.
     * Increasing this number reduces the width of usable space
     * to write the menu choice 's text.
     */
    public static final int HORIZONTAL_MARGIN = 3;
    /**
     * The number of pixels that separate the text from the top
     * and bottom of the menu indicator.  Increasing this value
     * also increases the spacing between menu items by a factor
     * of two (i.e. the bottom of this one plus top of the next).
     */
    public static final int VERTICAL_MARGIN = HORIZONTAL_MARGIN;
    /**
     * The <code>MenuItem</code> encapsulated by this component.  If this
     * component is for a <code>DeviceScreen</code> option, this will be
     * <code>null</code>.
     */
    public final MenuItem menuItem;
    /**
     * The <code>DeviceScreen</code> encapsulated by this component.  If this
     * component is for a <code>MenuItem</code> option, this will be
     * <code>null</code>.
     */
    public final DeviceScreen screen;
    /**
     * The menu text to use if explicitly set.  This can be <code>null</code>
     * in which case <code>menuItem.getText()</code> or <code>screen.getTitle()</code>
     * should be used.
     */
    public String screenText;
    /**
     * This is the menu text for this choice.  It comes from the
     * <code>MenuItem</code> or <code>DeviceScreen</code> this choice represents.
     */
    public final Label text = new Label();
    /**
     * When <code>true</code> it indicates this option is for a submenu of
     * the current menu.  Submenus have an arrow that appears to the
     * right of the option text.  There is nothing else special about
     * them.
     */
    public boolean submenu = false;
    public int[] subMenuIndiSize = new int[2];
    public Image headerIcon = null;

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>MenuItem</code>.
     * 
     * @param choice is the command that is represented by this component.
     */
    public MenuOption(MenuItem choice) {
        if (choice == null) {
            throw new IllegalArgumentException("choice cannot be null");
        }

        if (choice.getText() == null) {
            throw new IllegalArgumentException("choice text cannot be null");
        }

        this.menuItem = choice;
        this.screen = null;
    }

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>MenuItem</code>.
     * 
     * @param choice is the command that is represented by this component.
     */
    public MenuOption(MenuItem choice, int layout) {
        if (choice == null) {
            throw new IllegalArgumentException("choice cannot be null");
        }

        if (choice.getText() == null) {
            throw new IllegalArgumentException("choice text cannot be null");
        }

        this.menuItem = choice;
        this.screen = null;
        this.text.setHorizontalAlignment(layout);
    }

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>DeviceScreen</code>.
     * 
     * @param choice is the command that is represented by this component.
     */
    public MenuOption(DeviceScreen choice) {
        if (choice == null) {
            throw new IllegalArgumentException("choice cannot be null");
        }

        this.menuItem = null;
        this.screen = choice;
    }

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>DeviceScreen</code>.
     * 
     * @param text is string that appears in the menu option.
     * @param choice is the command that is represented by this component.
     */
    public MenuOption(String text, DeviceScreen choice) {
        if (choice == null) {
            throw new IllegalArgumentException("choice cannot be null");
        }

        this.menuItem = null;
        this.screen = choice;
        this.screenText = text;
    }

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>DeviceScreen</code>.
     * 
     * @param text is string that appears in the menu option.
     * @param choice is the command that is represented by this component.
     */
    public MenuOption(int id, String text, DeviceScreen choice) {
        if (choice == null) {
            throw new IllegalArgumentException("choice cannot be null");
        }

        this.menuItem = null;
        this.screen = choice;
        this.screenText = text;
        this.id = id;
    }

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>DeviceScreen</code>.
     * 
     * @param text is string that appears in the menu option.
     * @param choice is the command that is represented by this component.
     */
    public MenuOption(int id, String text, DeviceScreen choice, int layout) {
        if (choice == null) {
            throw new IllegalArgumentException("choice cannot be null");
        }

        this.menuItem = null;
        this.screen = choice;
        this.screenText = text;
        this.id = id;
        this.text.setHorizontalAlignment(layout);
    }

    /**
     * Creates a new <code>MenuOption</code> component that encapsulates a
     * <code>ListMenu</code>.
     * 
     * @param choice is the command that is represented by this component.
     * @param submenu when <code>true</code> indicates this is a submenu of the
     *  current menu.
     */
    public MenuOption(ListMenu choice, boolean submenu) {
        this(choice);
        this.submenu = submenu;
    }

    /**
     * @return <code>true</code> if this choice represents a submenu;
     *  <code>false</code> otherwise.
     */
    public boolean isSubmenu() {
        return submenu;
    }

    /**
     * Explicitly sets the text shown in this menu option.  This
     * overrides any value set by the <code>MenuItem</code> or the title
     * of the <code>DeviceScreen</code>.  To clear explicitly set text pass
     * in <code>null</code>.
     *
     * @param label is the text that will appear for this menu item.
     *  If <code>null</code> the <code>MenuItem.getText()</code> or
     *  <code>DeviceScreen.getTitle()</code> will be used.
     */
    public void setLabel(String label) {
        screenText = label;
        invalidate();
    }

    /**
     * @return The text displayed for this menu item.  It is never
     *  <code>null</code>.
     */
    public String getLabel() {
        // Always use explicitly set text.
        if (screenText != null) {
            return screenText;
        }

        // Otherwise use implied text.
        String label;

        if (menuItem != null) {
            label = menuItem.getText();
        } else // ( screen != null )
        {
            label = screen.getTitle();
        }

        if (label == null) {
            label = "";
        }

        return label;
    }

    /**
     * Activates the command represented by this choice.  The <code>ListMenu</code>
     * class will call this method when the user selects this choice.
     */
    public void select() {
        //System.out.println("Screen order :" + UIManager.getOrderScreen());
        if (menuItem != null) {
            menuItem.onSelection();
        } else // screen != null            
        {
            if (this.getID() >= 0) {
                UIManager.incOrderScreen();
                screen.setTitle(screenText);
                ConnectionCenter.setReceiver((IReceiver) screen);
                ConnectionCenter.sendMessage(this.getID(), 0);
                screen.show();
            } else {                
                ConnectionCenter.sendMessage(-this.getID());
                ((ListMenu)UIManager.getArticleList()).removeLastElement();
            }
        }
    }

    /**
     * An event raised whenever the component is made visible on the screen.
     * This is called before the <code>paintComponent</code> method.
     */
    public void showNotify() {
        // Pass the event to contained components.
        text.visible(true);

        // Continue processing the event.
        super.showNotify();
    }

    /**
     * An event raised whenever the component is removed from the screen.
     */
    public void hideNotify() {
        // Pass the event to contained components.
        text.visible(false);

        // Continue processing the event.
        super.hideNotify();
    }

    /**
     * Paints a <code>MenuOption</code>.  On the left is the choice number in
     * a box.  The middle has the text for the choice.  If it is a submenu
     * the right has an arrow.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @param theme is the application's theme used to paint the menu item.
     * @param width is the width of the menu item area in pixels.
     * @param height is the height of the menu item area in pixels.
     * @param selected is <code>true</code> if this is the currently highlighted
     *  menu choice.
     *  
     * @see org.j4me.ui.components.Component#paintComponent(javax.microedition.lcdui.Graphics, org.j4me.ui.Theme, int, int, boolean)
     */
    public void paintComponent(Graphics g, Theme theme, int width, int height, boolean selected) {
        // Paint the background for the menu item.
        int backgroundColor = selected
                ? theme.getHighlightColor()
                : theme.getBackgroundColor();
        g.setColor(backgroundColor);


        //g.fillRect(0, 0, width, height);
        int fontHeight = UIManager.getTheme().getFont().getHeight();
        int[] submenuDimensions = getSubmenuIndicatorSize(theme, width, height);
        int textWidth = width - 3 * HORIZONTAL_MARGIN - submenuDimensions[0];
        int[] textDimensions = getPreferredTextSize(theme, textWidth, height);
        if (selected) {
            int i = textDimensions[1] / fontHeight;
            int temp = 0;
            for (; i >= 0; i--) {
                g.drawImage(ResManager.getSelectedInList(), 0, temp, Graphics.LEFT | Graphics.TOP);
                temp += ResManager.res.selectedHighlightHeight;
                if (temp >= textDimensions[1]) {
                    break;
                }
            }
            //g.fillRect(0, 0, width, height);
        }

        // Calculate the dimensions of the text and submenu arrow.

        if (headerIcon != null) {
            g.drawImage(headerIcon, 0, 0, Graphics.TOP | Graphics.LEFT);
        }
        int textHeight = textDimensions[1];
        int textTop = (height - textHeight) / 2;
        int textLeft = HORIZONTAL_MARGIN;
        if (headerIcon != null) {
            textLeft += headerIcon.getWidth() + 5;
        }

        // Paint the menu text.
        paintText(g, theme, textLeft, textTop, textWidth, textHeight, selected);

        // If this is a submenu, paint an arrow next to it.
        if (submenu) {
            // Get the dimensions of the arrow.
            int arrowHeight = submenuDimensions[1];

            if (arrowHeight % 2 == 0) {
                // The arrow height must odd to make a good looking triangle.
                arrowHeight--;
            }

            int arrowWidth = submenuDimensions[0];
            int arrowX = width - HORIZONTAL_MARGIN - arrowWidth;
            int arrowY = (height - arrowHeight) / 2;

            // Set the color of the arrow.
            int arrowColor = selected
                    ? theme.getMenuFontColor()
                    : theme.getFontColor();
            g.setColor(arrowColor);

            // Draw the arrow as a triangle.
            g.fillTriangle(
                    arrowX, arrowY,
                    arrowX, arrowY + arrowHeight,
                    arrowX + arrowWidth, arrowY + arrowHeight / 2 + 1);
        }
    }

    /**
     * Paints a the text within the menu option component.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     * @param theme is the application's theme used to paint the menu item.
     * @param x is the left of the text area.
     * @param y is the top of the text area.
     * @param width is the width of the menu item area in pixels.
     * @param height is the height of the menu item area in pixels.
     * @param selected is <code>true</code> if this is the currently highlighted
     *  menu choice.
     *  
     * @see #paintComponent(Graphics, Theme, int, int, boolean)
     */
    public void paintText(Graphics g, Theme theme, int x, int y, int width, int height, boolean selected) {
        // Set the font color.
        int fontColor = selected
                ? theme.getBackgroundColor()
                : theme.getFontTextColor();
        text.setFontColor(fontColor);

        // Paint the text.
        text.paint(g, theme, getScreen(),
                x, y, width, height,
                selected);
    }

    /**
     * Returns the size of the menu choice.  It will be one line of text
     * high and as wide as the screen.
     * 
     * @see org.j4me.ui.components.Component#getPreferredComponentSize(org.j4me.ui.Theme, int, int)
     */
    public int[] getPreferredComponentSize(Theme theme, int viewportWidth, int viewportHeight) {
        if (!isChangeFont) {
            return preferSize;
        }
        // Calculate the dimensions of the non-text parts.
        int[] submenuDimensions = getSubmenuIndicatorSize(theme, viewportWidth, viewportHeight);

        // Calculate the width of the area the text can appear in.
        int textWidth = viewportWidth - 3 * HORIZONTAL_MARGIN - submenuDimensions[0];

        // Calculate the dimensions of the text.
        int[] textDimensions = getPreferredTextSize(theme, textWidth, viewportHeight);

        // The height is the greater of them.
        int height = Math.max(textDimensions[1], submenuDimensions[1]);
        height += 2 * VERTICAL_MARGIN;

        preferSize[0] = viewportWidth;
        preferSize[1] = height;
        isChangeFont = false;

        return preferSize;
    }

    /**
     * Returns the size of the menu text.
     * 
     * @param theme is the application's <code>Theme</code>.
     * @param viewportWidth is the width of the screen in pixels.
     * @param viewportHeight is the height of the screen in pixels.
     * @return An array with two elements where the first is the width
     *  in pixels and the second is the height.
     */
    public int[] getPreferredTextSize(Theme theme, int viewportWidth, int viewportHeight) {
        text.setLabel(getLabel());
        return text.getPreferredSize(theme, viewportWidth, viewportHeight);
    }

    /**
     * Returns the size of the submenu indicator.  It appears at the right
     * of this component.
     * 
     * @param theme is the application's <code>Theme</code>.
     * @param viewportWidth is the width of the screen in pixels.
     * @param viewportHeight is the height of the screen in pixels.
     * @return An array with two elements where the first is the width
     *  in pixels and the second is the height.
     */
    public int[] getSubmenuIndicatorSize(Theme theme, int viewportWidth, int viewportHeight) {
        // Get the dimensions of the arrow.
        if (!isChangeFont) {
            return subMenuIndiSize;
        }
        Font textFont = theme.getFont();
        int arrowHeight = textFont.getHeight() * 4 / 5;  // 80%

        if (arrowHeight % 2 == 0) {
            // The arrow height must odd to make a good looking triangle.
            arrowHeight--;
        }
        int arrowWidth = arrowHeight / 2;
        subMenuIndiSize[0] = arrowWidth;
        subMenuIndiSize[1] = arrowHeight;
        isChangeFont = false;

        return subMenuIndiSize;
    }

    /**
     * @return <code>true</code> because this component accepts user input.
     */
    public boolean acceptsInput() {
        return true;
    }

    /**
     * Called when the user presses any key.
     * 
     * @param key is code of the key that was pressed.
     * 
     * @see Component#keyPressed(int)
     */
    public void keyPressed(int key) {
        // The joystick's fire button means to select this item.
        if (key == DeviceScreen.FIRE) {
            select();
        }
        // Continue processing the key event.
        super.keyPressed(key);
    }

    /**
     * Called when the pointer is pressed.
     * 
     * @param x is the horizontal location where the pointer was pressed
     *  relative to the top-left corner of the component.
     * @param y is the vertical location where the pointer was pressed
     *  relative to the top-left corner of the component.
     */
    public void pointerPressed(int x, int y) {
        // If anywhere on the menu item is pressed it has been selected.
        select();

        // Continue processing the pointer event.
        super.pointerPressed(x, y);
    }

    public int getID() {
        return id;
    }
}
