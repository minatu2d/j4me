/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.j4me.ui;

import MainProcess.RMS;
import MainProcess.SMS;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.j4me.examples.ui.Runner;
import org.j4me.examples.ui.themes.sunnetTheme;
import org.j4me.ui.components.TextBox;
import org.j4me.ui.components.Label;

/**
 *
 * @author phungvantu
 */
public class Preference extends ListMenu {

    public ListMenu sendAsGift = null;
    public ListMenu option = null;
    public ListMenu account = null;

    public ListMenu getSendGiftForm() {
        if (sendAsGift == null) {
            sendAsGift = sendAsGift = makeSendForm();
        }
        return sendAsGift;
    }

    public ListMenu getOptionForm()
    {
        if (option == null)
        {
            option = makeOptionForm();
        }
        return option;
    }
    public ListMenu getAccountForm()
    {
        if (account == null)
        {
            account = makeAccountForm();
        }
        return account;
    }
    public Preference(DeviceScreen previous) {
        super("Tùy chọn", previous, 3);
        setMenuText("Đọc tin", "Chọn");        
        setSpacing(30);
        this.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Gửi tặng";
            }

            public void onSelection() {
                getSendGiftForm().show();
            }
        }, Graphics.HCENTER);
        this.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Tùy chỉnh";
            }

            public void onSelection() {
                getOptionForm().show();
            }
        }, Graphics.HCENTER);
        this.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Tài khoản";
            }

            public void onSelection() {
                getAccountForm().show();
            }
        }, Graphics.HCENTER);
    }

    public ListMenu makeSendForm() {
        ListMenu sendForm = new ListMenu("Gửi tặng", this, 2) {

            public void acceptNotify() {
                String temp = ((TextBox) sendAsGift.get(1)).getString();
                if (temp != null) {
                    System.out.println("Send SMS as Gift to :" + temp);
                }
            }
        };
        sendForm.setMenuText("Trước", "Gửi");
        Label label = new Label("Số điện thoại người nhận");
        //label.setFontColor(Theme.BLUE);
        sendForm.append(label);
        TextBox entry = new TextBox();
        entry.setForPhoneNumber();
        entry.setMaxSize(11);
//        entry.setLabel("Số điện thoại người nhận");
        sendForm.append(entry);
        sendForm.setSelected(1);
        return sendForm;
    }

    public ListMenu makeOptionForm() {
        ListMenu list = new ListMenu("Tùy chỉnh", this, 3);
        list.setSpacing(30);
        ListMenu fontSetting = new ListMenu("Font chữ", list, 3);
        list.setSpacing(30);
        MenuItem smallFontItem = new MenuItem() {

            public String getText() {
                return "Nhỏ";
            }

            public void onSelection() {
                UIManager.getTheme().setSize(Font.SIZE_SMALL);
            }
        };
        MenuItem mediumFontItem = new MenuItem() {

            public String getText() {
                return "Vừa";
            }

            public void onSelection() {
                UIManager.getTheme().setSize(Font.SIZE_MEDIUM);
            }
        };
        MenuItem bigFontItem = new MenuItem() {

            public String getText() {
                return "To";
            }

            public void onSelection() {
                UIManager.getTheme().setSize(Font.SIZE_LARGE);
            }
        };
        fontSetting.appendMenuOption(smallFontItem, Graphics.HCENTER);
        fontSetting.appendMenuOption(mediumFontItem, Graphics.HCENTER);
        fontSetting.appendMenuOption(bigFontItem, Graphics.HCENTER);
        fontSetting.setSelected(2);
        list.appendMenuOption("Font chữ", fontSetting, Graphics.HCENTER);

        ListMenu imageSetting = new ListMenu("Hình ảnh", list, 4);
        MenuItem noImage = new MenuItem() {

            public String getText() {
                return "Không hiển thị";
            }

            public void onSelection() {
            }
        };
        MenuItem smallImage = new MenuItem() {

            public String getText() {
                return "Nhỏ";
            }

            public void onSelection() {
            }
        };
        MenuItem mediumImage = new MenuItem() {

            public String getText() {
                return "Vừa";
            }

            public void onSelection() {
            }
        };
        MenuItem bigImage = new MenuItem() {

            public String getText() {
                return "To";
            }

            public void onSelection() {
            }
        };
        imageSetting.appendMenuOption(noImage, Graphics.HCENTER);
        imageSetting.appendMenuOption(smallImage, Graphics.HCENTER);
        imageSetting.appendMenuOption(mediumImage, Graphics.HCENTER);
        imageSetting.appendMenuOption(bigImage, Graphics.HCENTER);
        list.appendMenuOption("Hình ảnh", imageSetting, Graphics.HCENTER);
        ListMenu themeSetting = new ListMenu("Giao diện", list, 4);
        themeSetting.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Mặc định";
            }

            public void onSelection() {
                UIManager.setTheme(Theme.getDefaltTheme());
            }
        });
        themeSetting.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Console";
            }

            public void onSelection() {
                UIManager.setTheme(Theme.getConsoleTheme());
            }
        });
        themeSetting.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Xanh lá ";
            }

            public void onSelection() {
                UIManager.setTheme(Theme.getGreenTheme());
            }
        });
        themeSetting.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Xanh da trời";
            }

            public void onSelection() {
                UIManager.setTheme(Theme.getBlueTheme());
            }
        });
        list.appendMenuOption("Giao diện", themeSetting, Graphics.HCENTER);
        if (UIManager.getTheme() instanceof sunnetTheme) {
        }
        return list;
    }

    public ListMenu makeAccountForm() {
        ListMenu list = new ListMenu("Tài khoản", this, 3);
        list.setSpacing(30);
        ListMenu accountInfo = new ListMenu("Tài khoản", list, 3);
        accountInfo.setSpacing(30);
        accountInfo.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Ngày kích hoạt : ";
            }

            public void onSelection() {
            }
        });
        accountInfo.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Ngày gia hạn cuối cùng : ";
            }

            public void onSelection() {
            }
        });
        accountInfo.appendMenuOption(new MenuItem() {

            public String getText() {
                return "Thời gian còn lại : ";
            }

            public void onSelection() {
            }
        });
        list.appendMenuOption("Tài khoản", accountInfo, Graphics.HCENTER);
        ListMenu activeForm = new ListMenu("Gia hạn", list, 3);
        activeForm.setSpacing(30);
        list.appendMenuOption("Gia hạn", activeForm, Graphics.HCENTER);
        activeForm.appendMenuOption(new MenuItem() {

            public String getText() {
                return "15 ngày";
            }

            public void onSelection() {
                SMS.sendRenewal(RMS.getapp_id(), SMS.MORE_15_DAY);
            }
        }, Graphics.HCENTER);
        activeForm.appendMenuOption(new MenuItem() {

            public String getText() {
                return "30 ngày";
            }

            public void onSelection() {
                SMS.sendRenewal(RMS.getapp_id(), SMS.MORE_30_DAY);
            }
        }, Graphics.HCENTER);
        activeForm.appendMenuOption(new MenuItem() {

            public String getText() {
                return "60 ngày";
            }

            public void onSelection() {
                SMS.sendRenewal(RMS.getapp_id(), SMS.MORE_60_DAY);
            }
        }, Graphics.HCENTER);
        return list;
    }

    /**
     * The left menu button takes the user back to the previous screen.
     * If there is no previous screen it has no effect.
     */
    public void declineNotify() {
        System.out.println("List view :" + UIManager.getOrderScreen());
        UIManager.setOrderScreen(Runner.NEWS_SOURCE_GRID_MENU);
        // Go back to the previous screen.
        if (previous != null) {
            previous.show();
        }
    }
}
