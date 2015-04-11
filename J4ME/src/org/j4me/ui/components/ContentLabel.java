/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.j4me.ui.components;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.j4me.ui.Theme;
import org.j4me.ui.UIManager;
//</editor-fold>

/**
 *
 * @author phungvantu
 */
public class ContentLabel extends Label {
    /*Contain lines*/

    public final static int META_DATA = 0;
    public final static int HEADER = 1;
    public final static int TEXT = 2;
    public Vector dataQueue = new Vector();
    public Vector linesQueue = new Vector();
    public String lastRemainText = "";
    public boolean isLastEmptyLines = false;
    public boolean lastSpaceChar = false;
    public int type = TEXT;
    public StringBuffer incomBuffer = new StringBuffer(100);
    public char[] linesBuffer = new char[200];
    public char charAti = ' ';

    /**
     * @param text is the string to display.  If <code>null</code> the label will
     *  be hidden.
     */
    public ContentLabel() {
        super();
    }

    /**
     * @param text is the string to display.  If <code>null</code> the label will
     *  be hidden.
     */
    public ContentLabel(int width, int height) {
        super();
        text = "Hello";
        widthHeightProper[0] = width;
        widthHeightProper[1] = 0;
        //linesQueue.removeElementAt(l);
        linesQueue.addElement(lastRemainText);
    }

    public void appendText(String newText, int width) {
        String newLine;

        // The index of the character that starts this line.
        int lineStart = 0;
        font = getFont(UIManager.getTheme());
        if (linesQueue.size() > 0) {
            linesQueue.removeElementAt(linesQueue.size() - 1);
//            if ((lastRemainText != null) && lastRemainText.trim().length() != 0) {
//                height -= font.getHeight() / 2;
//            }
            if (lastRemainText.length() != 0) {
                height -= font.getHeight() / 2;
            }
        }

        // The index of a character that may appear on the
        // next line.  For example a space can appear on this
        // line or the next, but a letter in the middle of the
        // word must stay on the same line as the entire word.
        int lastBreakableSpot = 0;

        // The index of the last character that wasn't a
        // whitespace character such as ' '.
        int lastNonWhiteSpace = 0;

        // The width of a wide character used as a safety
        // margin in calculating the line width.  Not all font
        // width calculations are reliable (e.g. the default
        // font on the Motorola SLVR).  Adjusting the line
        // width by this amount assures no text will get clipped.
//        newText = lastRemainText + newText;
        incomBuffer.append(newText);
        int charWidth = font.charWidth('O');
        width -= charWidth;

        // Scan lengths character-by-character.
//        char[] chars = newText.toCharArray();
        for (int i = 0; i < incomBuffer.length(); i++) {
            charAti = incomBuffer.charAt(i);
            boolean isSeparator =
                    (charAti == '-')
                    || (charAti == '/');
            boolean isWhiteSpace =
                    (charAti == ' ');

            boolean isNewLine =
                    (charAti == '\n');
            boolean isCarrageReturn =
                    (charAti == '\r');

            boolean isLineBreak = isNewLine || isCarrageReturn;
            incomBuffer.getChars(lineStart, i, linesBuffer, 0);
            int lineWidth = font.charsWidth(linesBuffer, 0, i - lineStart);
            // Are we done with this line?
            if ((isLineBreak) || (lineWidth > width)) {
                int lineEnd;
                if (isLineBreak) {
                    lineEnd = i;
                    lastSpaceChar = true;
                } else if (lastBreakableSpot > lineStart) {
                    lineEnd = lastBreakableSpot;
                } else {
                    // This word is longer than the line so do a mid-word break
                    // with the current letter on the next line.
                    lineEnd = i - 1;
                }

                // Record the line.                
//                newLine = newText.substring(lineStart, lineEnd);
                newLine = new String(linesBuffer, 0, lineEnd - lineStart);
//                if (newLine.trim().length() != 0) {
                if (lineStart != lineEnd) {
                    isLastEmptyLines = false;
                    height += font.getHeight();
                    System.out.println("Newline : " + newLine);
                    if (newLine.trim().length() != 0) {
                        linesQueue.addElement(newLine);
                    }
                    lastSpaceChar = true;
                } else { // Append a space lines
                    if (!isLastEmptyLines) {
                        System.out.println("New empty line : " + newLine);
                        linesQueue.addElement(newLine);
                        height += font.getHeight() / 2;
                        isLastEmptyLines = true;
                        lastSpaceChar = true;
                    }
                }
                // Setup for the next line.
                if (isLineBreak) {
                    lineStart = lineEnd + 1;  // +1 to advance over the '\n'

                    // Add an empty line between the paragraphs.
                    if (isNewLine) {
                        System.out.println("New empty line ");
                        if (!isLastEmptyLines) {
                            System.out.println("New empty line ");
                            linesQueue.addElement(null);
                            height += font.getHeight() / 2;
                            isLastEmptyLines = true;
                            lastSpaceChar = true;
                        }
                    }
                } else // line break in the middle of a paragraph
                {
                    lineStart = lineEnd;
                }
            }
            // Is this a good place for a line-break?
            if (isSeparator) {
                // Put the separator char on this line (e.g. "full-").
                lastBreakableSpot = i + 1;
                lastSpaceChar = false;
            }

            if (isWhiteSpace) {
                // Break at the whitespace.  It will be trimmed later.
                lastBreakableSpot = lastNonWhiteSpace + 1;
                if (lastSpaceChar) {
                    lineStart++;
                }
                lastSpaceChar = true;
            } else {
                // Record this character as the last non-whitespace processed.
                lastNonWhiteSpace = i;
                lastSpaceChar = false;
            }
        }
        // Add the last line.
//        lastRemainText = newText.substring(lineStart);
        incomBuffer.delete(0, lineStart);
        lastRemainText = incomBuffer.toString();
        linesQueue.addElement(lastRemainText);
//        if ((lastRemainText != null) && (lastRemainText.trim().length() != 0)) {
//            height += font.getHeight() / 2;
//        }  
        if (lastRemainText.length() != 0) {
            height += font.getHeight() / 2;
        }
        widthHeightProper[1] = height + 5;

    }

    public synchronized void appendData(String newData) {
        //System.out.println("Append to data queue :" + newData);
        dataQueue.addElement(newData);
    }

    public void paintComponent(Graphics g, Theme theme, int width, int height, boolean selected) {
        if (!dataQueue.isEmpty()) {
            reCalcLineQueue();
        }

        // Set the font on the graphics object.
        Font local_font = getFont(theme);
        if (type == HEADER) {
            local_font = getFontHeader(theme);
        }
        g.setFont(local_font);
        int fontHeight = local_font.getHeight();
        int paragraphSpacing = fontHeight / 2;
        int yy = 0;

        int local_fontColor = getContentFontColor(theme);
        if (type == HEADER) {
            local_fontColor = getHeaderFontColor(theme);
        } else if (type == META_DATA) {
            local_fontColor = getMetadataFontColor(theme);
        }
        g.setColor(local_fontColor);

        // Determine the positioning of the line.
        int xx;
        int local_horizontalAlignment = getHorizontalAlignment();
        int anchor = local_horizontalAlignment | Graphics.TOP;

        if (local_horizontalAlignment == Graphics.LEFT) {
            xx = 0;
        } else if (local_horizontalAlignment == Graphics.HCENTER) {
            xx = width / 2;
        } else // horizontalAlignment == Graphics.RIGHT
        {
            xx = width;
        }
        // Get the top and bottom of current graphics clip.
        int clipTop = g.getClipY();
        int clipBottom = clipTop + g.getClipHeight();
//     
        String s = null;
        //
        for (int i = 0; i < linesQueue.size(); i++) {
            s = (String) linesQueue.elementAt(i);
            if (s == null) {
                yy += paragraphSpacing;
            } else {
                //System.out.println("Lines : " + s);
                if ((yy + fontHeight >= clipTop) && (yy <= clipBottom)) {
                    g.drawString(s, xx, yy, anchor);
                    System.out.println("Lines : " + s);
                }
                yy += fontHeight;
            }
        }
//        this.height = yy;
    }

    public int[] getPreferredComponentSize(Theme theme, int viewportWidth, int viewportHeight) {
        return widthHeightProper;
    }

    public void reCalcLineQueue() {
        String element = null;
        //while (!dataQueue.isEmpty()) {
        element = (String) dataQueue.firstElement();
        //System.out.println("Read data queue :" + element);
        this.appendText(element, UIManager.getContentView().getWidth());
        dataQueue.removeElementAt(0);
        // }
    }

    public void reset() {
        this.linesQueue.removeAllElements();
        this.dataQueue.removeAllElements();
        incomBuffer.delete(0, incomBuffer.length());
//        countImage = 0;
//        isImageURL = false;
        lastRemainText = "";
        widthHeightProper[0] = 0;
        widthHeightProper[1] = 0;
    }

    public int getContentFontColor(Theme theme) {
        return theme.getContentFontColor();
    }

    public Font getFont(Theme theme) {
        return theme.getContentFont();
    }

    public Font getFontHeader(Theme theme) {
        return theme.getFont();
    }

    public int getHeaderFontColor(Theme theme) {
        return theme.getHeaderColor();
    }

    public int getMetadataFontColor(Theme theme) {
        return theme.getMetadataColor();
    }
}
