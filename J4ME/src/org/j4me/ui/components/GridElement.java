package org.j4me.ui.components;

import MainProcess.ConnectionCenter;
import MainProcess.IReceiver;
import java.io.*;
import javax.microedition.lcdui.*;
import org.j4me.examples.ui.Runner;
import org.j4me.ui.*;
import res.NewsSource;
import res.ResManager;

/**
 * The <code>Picture</code> component shows an <code>Image</code>.  Typically this
 * is a PNG resource.  It can also be an image created manually using
 * the <code>Image.createImage</code> methods.
 * 
 * @see javax.microedition.lcdui.Image
 */
public class GridElement
        extends Component {

    public DeviceScreen screen = null;
    /**
     * The image object displayed by this <code>Picture</code> component.
     */
    public Image icon;
    public int iconId = 0;
    public String name = null;
    public int[] properSize = new int[2];
    public int id = -1;

    /**
     * Constructs a <code>Picture</code> component.
     */
    public GridElement() {
        this.horizontalAlignment = Graphics.HCENTER;
    }

    /**
     * Constructs an Picture with a specified name
     */
    public GridElement(String name) {
        this.name = name;
        this.horizontalAlignment = Graphics.HCENTER;
    }

    public GridElement(DeviceScreen screen, int id) {
        this.horizontalAlignment = Graphics.HCENTER;
        this.screen = screen;
        properSize[0] = ResManager.res.icon_width;
        properSize[1] = ResManager.res.icon_height;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return (name == null) ? "no_name" : name;
    }

    /**
     * @return The <code>Image</code> displayed by this component.
     */
    public Image getImage() {
        return icon;
    }

    /**
     * @param image is the <code>Image</code> displayed by this component.
     */
    public void setImage(Image image) {
        this.icon = image;
        invalidate();
    }

    public void setIconID(int iconid) {
        this.iconId = iconid;
    }

    /**
     * Sets the image displayed by this component to a PNG resource. 
     * 
     * @param location is place in the Jar file the PNG resource is located.
     *  For example if it were in a directory called <code>img</code> this would
     *  be <code>"/img/filename.png"</code>.
     * @throws IOException if the PNG could not be loaded from <code>location</code>.
     */
    public void setImage(String location)
            throws IOException {
        this.icon = Image.createImage(location);
        invalidate();
    }

    /**
     * Paints the picture component.
     * 
     * @see org.j4me.ui.components.Component#paintComponent(javax.microedition.lcdui.Graphics, org.j4me.ui.Theme, int, int, boolean)
     */
    public void paintComponent(Graphics g, Theme theme, int width, int height, boolean selected) {
        if (icon != null) {
            // Determine the screen position.
            int horizontalAlig = getHorizontalAlignment();
            int anchor = horizontalAlig | Graphics.TOP;

            int yy = (height - icon.getHeight()) / 2;

            int xx;

            if (horizontalAlig == Graphics.LEFT) {
                xx = 0;
            } else if (horizontalAlig == Graphics.HCENTER) {
                xx = width / 2;
            } else // horizontalAlignment == Graphics.RIGHT
            {
                xx = width;
            }
            //g.setColor(255, 0, 0);            
            g.fillRect(xx, yy, g.getClipX(), g.getClipY());
            if (selected) {
                //g.setColor(255, 0, 0);
                //g.fillRect(xx - icon.getWidth() , yy - icon.getHeight() / 2 - 2, g.getClipX(), g.getClipY());

                g.drawImage(ResManager.getSelectedIcon(), xx - 1, yy - 4, anchor);
            }
//            } else {
//                g.drawImage(ResManager.getUnselectedIcon(), xx - 2, yy - 4, anchor);
//            }
            // Paint image.
            //System.out.println("Clip X : " + g.getClipX());
            //System.out.println("Clip X : " + g.getClipX());

            g.drawImage(icon, xx, yy, anchor);
        } else {
            icon = NewsSource.getICon(iconId);
        }
    }

    /**
     * Returns the dimensions of the check box.
     * 
     * @see org.j4me.ui.components.Component#getPreferredComponentSize(org.j4me.ui.Theme, int, int)
     */
    public int[] getPreferredComponentSize(Theme theme, int viewportWidth, int viewportHeight) {
        if (icon == null) {
            throw new IllegalStateException();
        }

        return properSize;
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

    /**
     * Activates the command represented by this choice.  The <code>Menu</code>
     * class will call this method when the user selects this choice.
     */
    public void select() {
        //  System.out.println("Grid view :" + UIManager.getOrderScreen());
        UIManager.incOrderScreen();
        if (screen != null) {
            screen.setTitle(name);
            if (Runner.getApp_id().compareTo("000") != 0) {
                ConnectionCenter.setReceiver((IReceiver) screen);
            }
            ConnectionCenter.conCenter.setSourceID(id);
            ConnectionCenter.sendMessage(this.getName());
            screen.show();
        }
    }
}
