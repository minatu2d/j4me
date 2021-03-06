package org.j4me.examples.ui.themes;

import MainProcess.ConnectionCenter;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.j4me.examples.ui.Runner;
import org.j4me.ui.*;
import res.NewsSource;
import res.ResManager;

/**
 * A red on white theme.
 */
public class sunnetTheme
        extends Theme {

    public long lastTimeForTitleBar = 0;
    public final static long DELAY_TITLE_BAR = 300;
    public int titleBarOffset = 0;
    public int sourceNameLength = 0;
    public String newTitle = " ";

    public sunnetTheme() {
        super();
    }

    /**
     * @see Theme#getBackgroundColor()
     */
    public int getBackgroundColor() {
        return BROWN;
    }

    /**
     * @see Theme#getFontColor()
     */
    public int getFontColor() {
        return WHITE;
    }

    /**
     * @see Theme#getBorderColor()
     */
    public int getBorderColor() {
        return MAROON;
    }

    /**
     * @see Theme#getHighlightColor()
     */
    public int getHighlightColor() {
        return LIGHT_RED;
    }

    /**
     * @see Theme#getMenuBarBackgroundColor()
     */
    public int getMenuBarBackgroundColor() {
        return MAROON;
    }

    /**
     * @see Theme#getMenuBarHighlightColor()
     */
    public int getMenuBarHighlightColor() {
        return RED;
    }

    /**
     * @see Theme#getMenuBarBorderColor()
     */
    public int getMenuBarBorderColor() {
        return WHITE;
    }

    /**
     * @see Theme#getMenuFontColor()
     */
    public int getMenuFontColor() {
        return WHITE;
    }

    /**
     * @see Theme#getMenuFontHighlightColor()
     */
    public int getMenuFontHighlightColor() {
        return RED;
    }

    /**
     * @see Theme#getTitleBarBackgroundColor()
     */
    public int getTitleBarBackgroundColor() {
        return MAROON;
    }

    /**
     * @see Theme#getTitleBarHighlightColor()
     */
    public int getTitleBarHighlightColor() {
        return RED;
    }

    /**
     * @see Theme#getTitleBarBorderColor()
     */
    public int getTitleBarBorderColor() {
        return MAROON;
    }

    /**
     * @see Theme#getTitleFontColor()
     */
    public int getTitleFontColor() {
        return WHITE;
    }

    /**
     * @see Theme#getScrollbarBackgroundColor()
     */
    public int getScrollbarBackgroundColor() {
        return MAROON;
    }

    /**
     * @see Theme#getScrollbarHighlightColor()
     */
    public int getScrollbarHighlightColor() {
        return SILVER;
    }

    /**
     * @see Theme#getScrollbarBorderColor()
     */
    public int getScrollbarBorderColor() {
        return MAROON;
    }

    /**
     * @see Theme#getScrollbarTrackbarColor()
     */
    public int getScrollbarTrackbarColor() {
        return RED;
    }

    public void paintPageInfo(Graphics g) {
    }

    public void paintFullBackground(Graphics g) {
        g.drawImage(ResManager.getGridBackGroundImage(), 0, 0, Graphics.LEFT | Graphics.TOP);
    }

    public void paintGridBackground(Graphics g) {
        g.drawImage(ResManager.getGridBackGroundImage(), 0, 0, Graphics.LEFT | Graphics.TOP);
    }

    public void paintListAreaBackground(Graphics g, int vmargin) {
        g.drawImage(ResManager.getListAreaBackGround(), vmargin, vmargin, Graphics.LEFT | Graphics.TOP);
    }

    public void paintContentViewBackground(Graphics g, int vmargin) {
        //g.drawImage(ResManager.getContentViewBackground(), vmargin, vmargin, Graphics.LEFT | Graphics.TOP);
        g.setColor(Theme.LIGHT_GRAY);
        g.fillRoundRect(5, 5, g.getClipWidth(), g.getClipHeight(), 30, 30);
//        g.drawImage(ResManager.getContentViewCornerLeftTopImage(), vmargin - 10, vmargin - 10, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewHorizonalTopEdge(), vmargin, vmargin - 10, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewCornerRightTopImage(), vmargin + ResManager.res.content_view_width, vmargin - 10, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewVerticalLeftEdge(), vmargin - 10, vmargin, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewVerticalRightEdge(), vmargin + ResManager.res.content_view_width, vmargin, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewCornerLeftBottomImage(), vmargin - 10, vmargin + ResManager.res.content_view_height, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewHorizonalBottomEdge(), vmargin, vmargin + ResManager.res.content_view_height, Graphics.LEFT | Graphics.TOP);
//        g.drawImage(ResManager.getContentViewCornerRightBottomImage(), vmargin + ResManager.res.content_view_width, vmargin + ResManager.res.content_view_height, Graphics.LEFT | Graphics.TOP);
    }

    public void paintBackground(Graphics g) {
        //g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
        //paintGridBackground(g);
        if (UIManager.getOrderScreen() != Runner.NEWS_SOURCE_GRID_MENU) {
            paintContentViewBackground(g, 10);
        }
    }

    public void paintTitleBar(Graphics g, String title, int width, int height) {
        // Fill the background of the title bar.
        paintTitleBarBackground(g, 0, 0, width, height);
        g.setColor(getTitleBarBorderColor());
        g.drawLine(0, height - 1, width, height - 1);
        g.setFont(getTitleFont());
        g.setColor(getTitleFontColor());
        if (UIManager.getOrderScreen() >= Runner.WELCOME) {
            if (title.compareTo(newTitle) != 0) {
                newTitle = title;
            }
            sourceNameLength = 0;
            if (ConnectionCenter.getSourceID() != -1) {
                sourceNameLength = getFont().charWidth('A') * NewsSource.newsSource.name[ConnectionCenter.getSourceID()].length();
                //g.drawImage(ResManager.getSmallIcon(), 0, 5, Graphics.LEFT | Graphics.TOP);
                g.drawString(NewsSource.newsSource.name[ConnectionCenter.getSourceID()], 0, height / 2 - getTitleFont().getHeight() / 2, Graphics.LEFT | Graphics.TOP);
                g.fillRect(sourceNameLength + 3, 3, 3, height - 6);
            }
            // g.drawString(title, width / 2 + 70, height / 2 - 5, Graphics.HCENTER | Graphics.TOP);
            // if ((getFont().charsWidth(temp, 0, temp.length)) > width - 3) {        
            if (System.currentTimeMillis() > lastTimeForTitleBar + DELAY_TITLE_BAR) {
                lastTimeForTitleBar = System.currentTimeMillis();
                titleBarOffset++;
                if (titleBarOffset >= newTitle.length()) {
                    titleBarOffset = 0;
                }
            }
            // g.drawString(title.substring(titleBarOffset++), 0, 1, Graphics.LEFT | Graphics.TOP);            
            if (newTitle.length() > titleBarOffset) {
                g.drawSubstring(newTitle, titleBarOffset, newTitle.length() - titleBarOffset, sourceNameLength + 6, height / 2 - getTitleFont().getHeight() / 2, Graphics.LEFT | Graphics.TOP);
            }
        } else // Draw a Cline below the title bar to separate it from the canvas.
        // Write the title text.            
        {
            g.drawString(newTitle, width / 2, height / 2 - getTitleFont().getHeight() / 2, Graphics.HCENTER | Graphics.TOP);
        }
    }

    public void paintMenuBar(Graphics g,
            String left, boolean highlightLeft,
            String right, boolean highlightRight,
            int width, int height) {
        super.paintMenuBar(g, left, highlightLeft, right, highlightRight, width, height);
        if (ConnectionCenter.getConnectionState() == ConnectionCenter.NOT_CONNECTED) {
            g.drawString("NOT CONNECTED", width / 2, height / 2 - getTitleFont().getHeight() / 2, Graphics.HCENTER | Graphics.TOP);
        } else if (ConnectionCenter.getConnectionState() == ConnectionCenter.ERROR) {
            g.drawString("ERROR", width / 2, height / 2 - getTitleFont().getHeight() / 2, Graphics.HCENTER | Graphics.TOP);
        } else if (ConnectionCenter.getConnectionState() == ConnectionCenter.CONNECTING) {
            g.drawString("CONNECTING", width / 2, height / 2 - getTitleFont().getHeight() / 2, Graphics.HCENTER | Graphics.TOP);
        } else if (ConnectionCenter.getConnectionState() == ConnectionCenter.KEEP_CONNECTION) {
            g.drawString("CONNECTED", width / 2, height / 2 - getTitleFont().getHeight() / 2, Graphics.HCENTER | Graphics.TOP);
        } else {
            g.drawString("WHAT", width / 2, height / 2 - getTitleFont().getHeight() / 2, Graphics.HCENTER | Graphics.TOP);
        }
    }

    public int getContentFontColor() {
        return BLACK;
    }

    public int getFontTextColor() {
        return BLUE;
    }

    public Font getFont() {
        return titleFont;
    }

    public int getTitleHeight() {
        return 30;
    }

    public void paintPageInfo(Graphics g, int currentPage, int max_page) {
        //g.fillRect(0, g.getClipHeight() - this.getPageInfoHeight(), g.getClipWidth(), this.getPageInfoHeight());
        if (currentPage == 0) {
            g.drawImage(ResManager.getUnavailPrevPage(), g.getClipWidth() / 2 - 6, g.getClipHeight() - this.getPageInfoHeight() / 2 - 5, Graphics.TOP | Graphics.LEFT);
        } else {
            g.drawImage(ResManager.getAvailPrevPage(), g.getClipWidth() / 2 - 6, g.getClipHeight() - this.getPageInfoHeight() / 2 - 5, Graphics.TOP | Graphics.LEFT);
        }
        if (currentPage == max_page) {
            g.drawImage(ResManager.getUnavailNextPage(), g.getClipWidth() / 2 + 6, g.getClipHeight() - this.getPageInfoHeight() / 2 - 5, Graphics.TOP | Graphics.LEFT);
        } else {
            g.drawImage(ResManager.getAvailNextPage(), g.getClipWidth() / 2 + 6, g.getClipHeight() - this.getPageInfoHeight() / 2 - 5, Graphics.TOP | Graphics.LEFT);
        }
    }
    
    public String getMenuTextForExit()
    {
        return "Thoát";
    }
}
