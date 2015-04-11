package org.j4me.examples.ui.components;

import MainProcess.IReceiver;
import javax.microedition.lcdui.*;
import org.j4me.examples.ui.Runner;
import org.j4me.ui.*;
import org.j4me.ui.components.*;
import res.Adver;
import res.ResManager;

/**
 * Shows a <code>Label</code> component in action.  The label displays
 * multiple paragraphs, line breaks, and text justification.
 */
public class ContentView
        extends Dialog implements IReceiver {

    /**
     * The previous screen.
     */
    public DeviceScreen previous;
    /**
     * The label demonstrated by this example.
     */
    public static final int DELAY_PAINT = 1000;
    public ContentLabel metadata_label = null;
    public ContentLabel articleTitleLabel = null;
    public ContentLabel label = null;
    public StringBuffer buff = new StringBuffer();
    public ImageView imageScreen = null;
//    public int imgOrder = -1;
    public boolean isURL = false;
    public StringBuffer urlBuffer = new StringBuffer();
    public int endURl = -1;
    public boolean isFinishReceivingData = false;
    public int nSegmentContent = 0;
    public int loadedSegment = 0;
    public long lastCheckNewContent = System.currentTimeMillis();
    public boolean tempIsFinishReceive = false;

    /**
     * Constructs a screen that shows a <code>Label</code> component in action.
     * 
     * @param previous is the screen to return to once this done.
     */
    public ContentView(DeviceScreen previous) {
        super(50);
        this.previous = previous;
        //this.setMargin(10);
        ResManager.res.setContentViewSize(this.getWidth() - UIManager.theme.getVerticalScrollbarWidth(), this.getHeight() - 2 * margin);
//        label = new ContentLabel(this.getWidth(), getHeight() - 2 * margin);

        // Set the title and menu for this screen.
        setTitle("Conten view");
        setMenuText("Trở về", "Ảnh");
        metadata_label = new ContentLabel(this.getWidth(), getHeight() - 2 * margin);
        metadata_label.setHorizontalAlignment(Graphics.RIGHT);
        metadata_label.type = ContentLabel.META_DATA;
        articleTitleLabel = new ContentLabel(this.getWidth() - 10, getHeight() - 2 * margin);
        articleTitleLabel.setHorizontalAlignment(Graphics.LEFT);
        articleTitleLabel.type = ContentLabel.HEADER;
        imageScreen = new ImageView(this);
        UIManager.setImageView(imageScreen);
    }

    public void appendContent(String newContent) {
        //System.out.println("Data size :" + newContent.getBytes().length);
        if (newContent.startsWith("src=\"")) {
            isURL = true;
            endURl = newContent.indexOf('\"', 6);
            if (endURl != -1) {
                urlBuffer.append(newContent.substring(0, endURl));
                Picture newPic = new Picture();
                newPic.setHorizontalAlignment(Graphics.HCENTER);
                newPic.setTextLabel(urlBuffer.toString());
                this.append(newPic);
//                System.out.println("image URL full : " + urlBuffer.toString());
//                System.out.println("End url");
                urlBuffer.delete(0, urlBuffer.length());
                isURL = false;
                //System.out.println("New content");
                label = new ContentLabel(this.getWidth(), getHeight() - 2 * margin);
                label.appendData(newContent.substring(endURl + 1));
                label.setHorizontalAlignment(Graphics.LEFT);
                this.append(label);
                invalidate();
                return;
            }
            urlBuffer.append(newContent);
            return;
        }
        if (isURL) {
            endURl = newContent.indexOf('\"', 6);
            if (endURl != -1) {
                urlBuffer.append(newContent.substring(0, endURl));
                Picture newPic = new Picture();
                newPic.setHorizontalAlignment(Graphics.HCENTER);
                newPic.setTextLabel(urlBuffer.toString());
                this.append(newPic);
//                System.out.println("image URL full : " + urlBuffer.toString());
//                System.out.println("End url");
                urlBuffer.delete(0, urlBuffer.length());
                isURL = false;
                //System.out.println("New content");
                label = new ContentLabel(this.getWidth(), getHeight() - 2 * margin);
                label.appendData(newContent.substring(endURl + 1));
                label.setHorizontalAlignment(Graphics.LEFT);
                this.append(label);
                invalidate();
                return;
            }
            urlBuffer.append(newContent);
        }
        if (label == null) {
            //System.out.println("New content");
            label = new ContentLabel(this.getWidth(), getHeight() - 2 * margin);
            this.append(label);
            this.invalidate();
        }
        label.appendData(newContent);
        invalidate();
    }

    /**
     * Takes the user to the previous screen.
     */
    public void declineNotify() {
        if (isFinishReceivingData) {
            //  System.out.println("List view :" + UIManager.getOrderScreen());
            UIManager.decOrderScreen();
            previous.show();
        }
    }

    public void acceptNotify() {
        //src="http://24hn02.24hstatic.com:8008/upload/3-2011/images/2011-08-08/1312806196-chan-rac-1.jpg"
        // System.out.println("Accept");
//        if (imageScreen != null) {
//            ConnectionCenter.setReceiver(imageScreen);
//            UIManager.incOrderScreen();
//            imageScreen.show();
//        }
    }

    public void processMessage(String data) {
        //System.out.println("Element of content view : " + data);
        if (data.trim().length() == 0) {
            return;
        }
        if (data.compareTo("ERROR") == 0) {
            System.out.println("Re-send");
        }
        if (data.trim().startsWith("RESPONSE_CONTENT")) {
            int fieldIndex = data.indexOf('|');
            //System.out.println("nSegmenet : " + data.substring(fieldIndex + 1).trim());
            nSegmentContent = Integer.parseInt(data.substring(fieldIndex + 1).trim());
            loadedSegment = 0;
        } else {
            if (UIManager.getOrderScreen() == Runner.CONTENT_VIEW) {
                int fieldIndex = data.indexOf('|');
                //System.out.println("Field index :" + fieldIndex);
                //System.out.println("Segment " + data.substring(0, fieldIndex));                
                try {
                    if (data.substring(0, fieldIndex).trim().compareTo("10000") == 0) {
                        this.isFinishReceivingData = true;
                        nSegmentContent = loadedSegment;
                    } else {
                        if (fieldIndex + 1 < data.length() - 1) {
                            this.appendContent(data.substring(fieldIndex + 1, data.length()));
                            loadedSegment++;
                            // System.out.println("Loaded : " + loadedSegment++ + "/" + nSegmentContent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void reset() {
        //label.setSize(UIManager.getContentView().getWidth(), UIManager.getContentView().getHeight());
        // System.out.println("Reset content");
        this.deleteAll();
        if (label != null) {
            label.reset();
            label = null;
            buff.delete(0, buff.length());
        }
        isFinishReceivingData = false;
        topOfScreen = 0;
        this.setNewArticleTitle();
    }

    public synchronized void setNewArticleTitle() {
        //System.out.println("Title : " + this.title);
        int i1 = title.indexOf('|');
        int i2 = title.indexOf('|', i1 + 1);
        int i3 = title.indexOf('-', i2 + 1);
        metadata_label.reset();
        articleTitleLabel.reset();
        if ((i1 > 0) && (i3 > i1)) {
            //   System.out.println("Meta data : " + title.substring(i1 + 1, i3));
            metadata_label.appendData(title.substring(i1 + 1, i3));
        }
        if (i1 > 0) {
            articleTitleLabel.appendData(title.substring(0, i1));
            this.setTitle(title.substring(0, i1));
        }
        this.append(metadata_label);
        this.append(articleTitleLabel);
        this.invalidate();
    }

    /**
     * Paints the form and its components.  The layout is calculated from the
     * components and their order.
     * 
     * @param g is the <code>Graphics</code> object to paint with.
     */
    public /*synchronized*/ void paint(Graphics g) {
//        absoluteHeights[real_number_component] = margin + spacing + label.height;
        // g.setClip(margin, margin, 100, 100);
//        g.setClip(margin, margin, this.getWidth(), this.getHeight() / 2);        
//        System.out.println("Clip before :");
//        System.out.println("Clip X :" + g.getClipX());
//        System.out.println("Clip Y :" + g.getClipY());
//        System.out.println("Clip width : " + g.getClipWidth());
//        System.out.println("Clip height : " + g.getClipHeight());
        // Set the graphics properties for painting the component.
//        originalClipX = g.getClipX();
//        originalClipY = g.getClipY();
//        originalClipWidth = g.getClipWidth();
//        originalClipHeight = g.getClipHeight();

        // Have we determined the layout?
//        if (countInvalidate-- >= -50) {
        if (System.currentTimeMillis() > lastCheckNewContent + 200) {
            lastCheckNewContent = System.currentTimeMillis();
//            if (hasContentChange) {
//                tempIsFinishReceive = isFinishReceivingData;
            calculateLayout(UIManager.getTheme(), this.getWidth(), this.getHeight());
//                if ((isFinishReceivingData) && (tempIsFinishReceive)) {
//                    if (overCount++ > 10) {
//                        hasContentChange = false;
//                    }
//                }
//            }
        }
        // Get the height of the screen.
        Theme theme = UIManager.getTheme();
        int height = getHeight();

        // Add a vertical scrollbar?
        if (hasVerticalScrollbar()) {
            // Paint the scrollbar.
            int width = super.getOriginWidth();  // Exclude the margins and scrollbar
            int heightOfAllComponents = absoluteHeights[real_number_component];
            paintVerticalScrollbar(g, 0, 12, width - 7, height - 22, topOfScreen, heightOfAllComponents);
        }

        // Get the rest of the form dimensions.
        int formWidth = getWidth();
        int bottomOfScreen = topOfScreen + height;


        Component c = null;
        
        for (int i = 0; i < real_number_component; i++) {
            c = (Component) components.elementAt(i);
            int componentTop = absoluteHeights[i];
            int componentBottom = absoluteHeights[i + 1] - spacing;
            // Is the component visible on the screen?
            if (componentTop >= bottomOfScreen) {
                // System.out.println("InVisible");
                // Skip drawing components below the screen.                
                c.visible(false);
            } else if (componentBottom <= topOfScreen) {
                //System.out.println("InVisible");
                c.visible(false);
            } else {
                //System.out.println("Visible");
                c.visible(true);
                // Calculate the position of the component.
                int componentX = margin;
                int componentY = componentTop - topOfScreen;
                int componentWidth = formWidth;
                int componentHeight = componentBottom - componentTop;

                // Paint this component.
                if (intersects(g, componentX, componentY, componentWidth, componentHeight)) {
                    boolean selected = (i == highlightedComponent);
                    c.paint(g, theme, this,
                            componentX, componentY,
                            componentWidth, componentHeight,
                            selected);
                }
            }
        }
        if (loadedSegment != nSegmentContent) {
            g.setColor(Theme.BLACK);
            g.drawString(loadedSegment + "/" + nSegmentContent, this.getWidth() / 2 - 10, 0, Graphics.LEFT | Graphics.TOP);
        }
    }

    public void setTitle(String title) {
        super.setTitle(title + " - " + Adver.getText());
    }

    public void processMessage(byte[] data) {
    }

    public void invalidate() {
//        countInvalidate++;
        super.invalidate();
    }

    public void appendPic() {
    }

    public void appendLabel() {
    }

    public void keyPressed(int keycode) {
        if (keycode == DeviceScreen.FIRE) {
            if (isFinishReceivingData) {
                super.keyPressed(keycode);
            }
        } else {
            super.keyPressed(keycode);
        }
    }
}