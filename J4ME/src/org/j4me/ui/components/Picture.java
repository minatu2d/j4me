package org.j4me.ui.components;

import MainProcess.ConnectionCenter;
import MainProcess.IReceiver;
import java.io.*;
import javax.microedition.lcdui.*;
import org.j4me.ui.*;

/**
 * The <code>Picture</code> component shows an <code>Image</code>.  Typically this
 * is a PNG resource.  It can also be an image created manually using
 * the <code>Image.createImage</code> methods.
 * 
 * @see javax.microedition.lcdui.Image
 */
public class Picture
        extends Component implements IReceiver {

    public int[] preferSize = new int[2];
    /**
     * The image object displayed by this <code>Picture</code> component.
     */
    public Image image;
    public final static String viewImage = "Xem ?nh";
    public final static String errorLoad = "L?i t?i ?nh, t?i l?i";
    public final static String loading = "?ang t?i...";
    public final static String markImage = "<?nh>";
    public String label = markImage;
    public String text = null;
    public boolean isFinisLoad = true;

    /**
     * Constructs a <code>Picture</code> component.
     */
    public Picture() {
        preferSize[0] = UIManager.getImageView().getWidth();
        preferSize[1] = UIManager.getTheme().getFont().getHeight();
        this.setHorizontalAlignment(Graphics.HCENTER);
    }

    /**
     * @return The <code>Image</code> displayed by this component.
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image is the <code>Image</code> displayed by this component.
     */
    public void setImage(Image image) {
        this.image = image;
        invalidate();
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
        this.image = Image.createImage(location);
        invalidate();
    }

    /**
     * Paints the picture component.
     * 
     * @see org.j4me.ui.components.Component#paintComponent(javax.microedition.lcdui.Graphics, org.j4me.ui.Theme, int, int, boolean)
     */
    public void paintComponent(Graphics g, Theme theme, int width, int height, boolean selected) {
        if (image != null) {
            // Determine the screen position.
            int local_horizontalAlignment = getHorizontalAlignment();
            int anchor = local_horizontalAlignment | Graphics.TOP;

            int yy = (height - image.getHeight()) / 2;

            int xx;

            if (local_horizontalAlignment == Graphics.LEFT) {
                xx = 0;
            } else if (local_horizontalAlignment == Graphics.HCENTER) {
                xx = width / 2;
            } else // horizontalAlignment == Graphics.RIGHT
            {
                xx = width;
            }

            // Paint image.
            g.drawImage(image, xx, yy, anchor);
        } else {
            int local_horizontalAlignment = getHorizontalAlignment();
            int anchor = local_horizontalAlignment | Graphics.TOP;

            int yy = 0;

            int xx;

            if (local_horizontalAlignment == Graphics.LEFT) {
                xx = 0;
            } else if (local_horizontalAlignment == Graphics.HCENTER) {
                xx = width / 2;
            } else // horizontalAlignment == Graphics.RIGHT
            {
                xx = width;
            }
            // Paint image.
            if (selected) {
                g.setColor(255, 0, 0);
                g.fillRect(xx - 50, yy, 100, height);
            }
            g.setColor(0, 0, 0);
            g.drawString(label, xx, yy, anchor);
        }
    }

    /**
     * Returns the dimensions of the check box.
     * 
     * @see org.j4me.ui.components.Component#getPreferredComponentSize(org.j4me.ui.Theme, int, int)
     */
    public int[] getPreferredComponentSize(Theme theme, int viewportWidth, int viewportHeight) {
//        if (image == null) {
//            throw new IllegalStateException();
//        }
        return preferSize;
    }

    public void setTextLabel(String newText) {
        this.text = newText;
    }

    public boolean acceptsInput() {
        return true;
    }

    public void keyPressed(int keyCode) {
        //src="http://vnexpress.net/Files/Subject/3b/a2/d5/0c/vang1.jpg
        if (keyCode == DeviceScreen.FIRE) {
            if (image == null) {
                this.label = loading;
                ConnectionCenter.setReceiver(this);
                ConnectionCenter.sendMessage(text.substring(5));
            }
        }
    }

    public void processMessage(String data) {
       // System.out.println("Picture data :" + data);
        data = data.trim();
        if (data.length() == 0) {
            return;
        }
        if (data.compareTo("ERROR") == 0) {
          //  System.out.println("Re-send");
        } else if (data.startsWith("IMAGE_RESPONSE")) {
            int fieldIndex1 = data.indexOf('|');
            if (data.substring(fieldIndex1 + 1).compareTo("ERROR") == 0) {
                this.label = errorLoad;
                return;
            }
            isFinisLoad = false;
            this.label = loading;
            int fieldIndex2 = data.indexOf('|', fieldIndex1 + 1);
            //System.out.println("Image height : " + data.substring(fieldIndex2 + 1, data.length()));
            preferSize[1] = Integer.parseInt(data.substring(fieldIndex2 + 1, data.length()));
        }
    }

    public void processMessage(byte[] data) {
        image = Image.createImage(data, 0, data.length);
        isFinisLoad = true;
    }

    public void pointerPressed(int x, int y) {
        this.keyPressed(DeviceScreen.FIRE);
    }
}
