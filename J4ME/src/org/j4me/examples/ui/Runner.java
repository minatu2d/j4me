package org.j4me.examples.ui;

import MainProcess.ConnectionCenter;
import MainProcess.MessageType;
import MainProcess.RMS;
import MainProcess.SMS;
import javax.microedition.midlet.*;
import org.j4me.examples.ui.components.ContentView;
import org.j4me.ui.GridMenu;
import org.j4me.ui.ListMenu;
import org.j4me.ui.Preference;
import org.j4me.ui.Theme;
import org.j4me.ui.UIManager;
import org.j4me.ui.components.GridElement;
import res.Adver;
import res.NewsSource;
import res.ResManager;

/**
 * The demonstration MIDlet for the J4ME UI.
 */
public class Runner
        extends MIDlet {

    public final static String arrProperty[] = new String[]{"com.siemens.IMEI", "com.samsung.imei", "com.sonyericsson.imei", "com.motorola.IMEI", "com.nokia.mid.imei", "com.nokia.imei", "phone.imei", "IMEI", "com.lge.imei"};
    public final static int NEWS_SOURCE_GRID_MENU = 0;
    public final static int CATEGORY_LIST_MENU = 1;
    public final static int ARTICLE_LIST_MENU = 2;
    public final static int CONTENT_VIEW = 3;
    public final static int IMAGE_VIEW = 4;
    public final static int GENERAL_PREFERENCE_VIEW = -10;
    public final static int DETALI_PREFERNCE_VIEW = -9;
    public int screen_order = WELCOME;
    public static int WELCOME = -1;
    public final static int WELCOME_TIMEOUT = 2000;
    public static String imei = null;
    public final static byte FIRST_NOT_ACTIVATED = 0;
    public final static byte NOT_ACTIVATED = 1;
    public final static byte FIRST_TIME_ACTIVATED = 2;
    public final static byte ACTIVATED = 3;
    /* (non-Javadoc)
     * @see javax.microedition.midlet.MIDlet#startApp()
     */

    public Runner() {
        imei = Runner.getImei();
        //System.out.println("Imei : " + imei);
        //SMS.sendKichHoat(imei);
    }

    protected void startApp() throws MIDletStateChangeException {
        /* Initialize the J4ME UI manager.*/
        RMS.init();
        SMS.init();
        Adver.init();
        if (Runner.getApp_id().compareTo("000") == 0) {
            if (imei.compareTo("000000000000000") == 0) {
                // SMS.sendKichHoat(imei);
            }
        }
        UIManager.init(this);
        UIManager.setTheme(Theme.getDefaltTheme());
        ResManager.init();
        NewsSource.init();


        GridMenu newsSourceMenu = new GridMenu("NEWS", null);
        UIManager.setNewsSourceMenu(newsSourceMenu);
        Preference preference = new Preference(newsSourceMenu);
        newsSourceMenu.previous = preference;
//        newsSourceMenu.previous = preference;
        // Create a submenu for showing component example screens.

        ListMenu categoryList = new ListMenu("Chủ đề", newsSourceMenu, 15);
        categoryList.headerIcon = ResManager.getCategoryIcon();
        UIManager.setCategoryList(categoryList);
        //newsSourceMenu.previous = categoryList;
//
        ListMenu articleList = new ListMenu("Article list", categoryList);
        UIManager.setArticleList(articleList);
//
        ContentView contentView = new ContentView(articleList);
        UIManager.setContentView(contentView);
        int i = 0;
        for (i = 0; i < NewsSource.newsSource.currentNSource; i++) {
            GridElement item = new GridElement(categoryList, i);
            //item.setImage(NewsSource.getICon(i));
            item.setIconID(i);
            item.setName(NewsSource.getAddress(i));
            newsSourceMenu.append(item);
        }
        //newsSourceMenu.show();
        newsSourceMenu.show();
//        UIManager.display.setCurrent(waitingScreen);
        /*Init for connection center*/
        UIManager.mainRun();
        ConnectionCenter.init();
        if (Runner.getApp_id().compareTo("000") == 0) {
            ConnectionCenter.setReceiver(RMS.rms);
            if (imei.compareTo("000000000000000") == 0) {
                ConnectionCenter.conCenter.send(MessageType.RQ_APPID + ";");
            } else {
                ConnectionCenter.conCenter.send(MessageType.RQ_APPID + ":" + imei + ";");
            }
        } else {
            ConnectionCenter.setReceiver(Adver.adv);
            ConnectionCenter.conCenter.send("RQ_ADV");
        }
    }

    /* (non-Javadoc)
     * @see javax.microedition.midlet.MIDlet#pauseApp()
     */
    protected void pauseApp() {
        // The application has no state so ignore pauses.
    }

    /* (non-Javadoc)
     * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
     */
    protected void destroyApp(boolean cleanup) throws MIDletStateChangeException {
        // The application holds no resources that need cleanup.
    }

    public static String getImei() {
        String sim = null;
        try {
            System.getProperty("microedition.platform");
            for (int i = 0; i < arrProperty.length; ++i) {
                if (sim == null) {
                    sim = System.getProperty(arrProperty[i]);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            sim = "000000000000000";
        }
        if (sim == null) {
            sim = "000000000000000";
        }
        return sim;
    }

    public static String getApp_id() {
        return RMS.getapp_id();
    }

    public static int getRemainOpen() {
        return RMS.getRemain();
    }

    public static void setApp_id(String app_id) {
        RMS.setApp_id(app_id);
    }

    public static void setRemainOpen(int remainOpen) {
        RMS.setRemain(remainOpen);
    }

    public static byte getActivation() {
        return RMS.getActivation();
    }

    public static void setActivation(byte state) {
        RMS.setActivation(state);
    }
}