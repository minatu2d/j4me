/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.j4me.util;

import javax.microedition.lcdui.Image;

/**
 *
 * @author phungvantu
 */
public class ImageTool {

    public static ImageTool imageTool = null;
    public int[] arr4IconOut = null;
    public int[] arr4IconIn = null;
    public int[] arr4SmallIconOut = null;
    public int[] arr4SmallIconIn = null;

    public static void init() {
        imageTool = new ImageTool();
    }

    public static Image reescalaArray(Image temp, int x, int y, int x2, int y2) {
        int ini[] = new int[temp.getWidth() * temp.getHeight()];
        int i = 0, t2;
        int dy, dx;
        temp.getRGB(ini, 0, temp.getWidth(), 0, 0, temp.getWidth(), temp.getHeight());
        int out[] = new int[x2 * y2];
//t1=y/y2; 
        t2 = x / x2;
        for (int yy = 0; yy < y2; yy++) {
            dy = yy * y / y2;

            for (int xx = 0; xx < x2; xx++) {
                dx = xx * t2;
                out[i++] = ini[(x * dy) + dx]; //(x2*yy)+xx 
            }
        }
        Image temp3 = Image.createRGBImage(out, x2, y2, true);
        return temp3;
    }

    public static Image reScala4Icon(Image temp, int x2, int y2) {
        return imageTool.reScalaIcon(temp, temp.getWidth(), temp.getHeight(), x2, y2);
    }

    public Image reScalaIcon(Image temp, int x, int y, int x2, int y2) {
        if (arr4IconIn == null) {
           // System.out.println("Init in array");
            arr4IconIn = new int[x * y];
        }
//        else {
//            for (int i = 0; i < x * y; i++) {
//                arr4IconIn[i] = 0x00;
//            }
//        }
        if (arr4IconOut == null) {
            arr4IconOut = new int[x2 * y2];
          //  System.out.println("Init out array");
        }
//        else {
//            for (int i = 0; i < x2 * y2; i++) {
//                arr4IconIn[i] = 0x00;
//            }
//        }
        int i = 0, t2;
        int dy, dx;
        temp.getRGB(arr4IconIn, 0, x, 0, 0, x, y);
//        y2; 
        t2 = x / x2;
        for (int yy = 0; yy < y2; yy++) {
            dy = yy * y / y2;
            for (int xx = 0; xx < x2; xx++) {
                dx = xx * t2;
                arr4IconOut[i++] = arr4IconIn[(x * dy) + dx]; //(x2*yy)+xx 
            }
        }
        Image temp3 = Image.createRGBImage(arr4IconOut, x2, y2, true);
        return temp3;
    }

    public static Image reScala4SmallIcon(Image temp, int x2, int y2) {
        return imageTool.reScalaIcon(temp, temp.getWidth(), temp.getHeight(), x2, y2);
    }

    public Image reScala4Small(Image temp, int x, int y, int x2, int y2) {
        if (arr4SmallIconIn == null) {
           // System.out.println("Init small in array");
            arr4SmallIconIn = new int[x * y];
        }

        if (arr4SmallIconOut == null) {
            arr4SmallIconOut = new int[x2 * y2];
            //System.out.println("Init small out array");
        }
        int i = 0, t2;
        int dy, dx;
        temp.getRGB(arr4SmallIconIn, 0, x, 0, 0, x, y);
//        y2; 
        t2 = x / x2;
        for (int yy = 0; yy < y2; yy++) {
            dy = yy * y / y2;
            for (int xx = 0; xx < x2; xx++) {
                dx = xx * t2;
                arr4SmallIconOut[i++] = arr4SmallIconIn[(x * dy) + dx]; //(x2*yy)+xx 
            }
        }
        Image temp3 = Image.createRGBImage(arr4SmallIconOut, x2, y2, true);
        return temp3;
    }
}