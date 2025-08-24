package dragon.object;

import dragon.server.Server;
import dragon.u.Util;
import dragon.server.mResources;
import dragon.template.ItemTemplate;
import dragon.u.MenuInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class Combine {

    public static int[] percent_plh = new int[]{50, 20, 10, 5, 3, 1, 1, 1};
    public static int[] coin_plh = new int[]{50000000, 60000000, 80000000, 120000000, 150000000, 200000000, 300000000, 400000000};
    public static int[] ngoc_phl = new int[]{1, 2, 3, 4, 5, 6, 7, 30};

    public static int ngoc_ep = 10;

    public static int max_Star = 8;
    public static int coin_nhap = 2000;

    //Dap do
    public static int[] dap_percent = new int[]{80, 50, 20, 10, 5, 2, 1, 1};
    public static int[] dap_coin = new int[]{80000, 500000, 1000000, 2500000, 5000000, 12000000, 26000000, 50000000};
    public static int[] dap_dasl = new int[]{5, 7, 9, 12, 15, 18, 21, 22};
    public static int[] downgrade = new int[]{0, 1, 1, 3, 3, 5, 5, 6};
    public static int[] downparam_percent = new int[]{0, 0, 1, 0, 1, 0, 1, 1};
    public static int max_Up = 8;

    //Nahp ngoc rong
    public static int[][] nhap_nr = new int[][]{
        {20, 19},
        {19, 18},
        {18, 17},
        {17, 16},
        {16, 15},
        {15, 14}
    };

    public static int idOption(int type) {
        int id = -1;
        if (type == 0) {
            id = 68;
        }
        if (type == 1) {
            id = 69;
        }
        if (type == 2) {
            id = 67;
        }
        if (type == 3) {
            id = 70;
        }
        if (type == 4) {
            id = 71;
        }
        return id;
    }

    public static int nextParamOption(int id, int next, int param) {
        int i;
        int p;
        if (id == 0 || id == 6 || id == 7 || id == 14 || id == 22 || id == 23 || id == 27 || id == 28 || id == 47) {
            for (i = 0; i < Math.abs(next); i++) {
                p = param * 10 / 100;
                if (p < 1) {
                    p = 1;
                }
                if (next > 0) {
                    param += p;
                } else {
                    param -= p;
                }
            }
            if (param < 0) {
                param = 0;
            }
        }
        return param;
    }

    public static void Combine(Char charz, byte action, Item[] items) {
        //Lam phep nhap ngoc rong
        if (charz.menuBoard.typeInfo == 27) {
            if (items.length == 1) {
                if (items[0] != null && getNr(items[0].template.id) != null) {
                    if (charz.getEmptyBagIndex() == -1) {
                        charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    } else {
                        charz.arrItem = items;
                        int d = 1;
                        if (items[0].quantity < 7) {
                            d = 7;
                        }
                        charz.resetMenu();
                        charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT11, items[0].template.name, ItemTemplate.get((short) getNr(items[0].template.id)[1]).name, d, items[0].template.name);
                        charz.menuBoard.arrMenu.add(new MenuInfo(mResources.LAM_PHEP, 28));
                        charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 27));
                        charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                    }
                }
            } else {
                charz.session.service.startOKDlg(mResources.NHAP_NR_1);
            }
            charz.menuBoard.typeInfo = 27;
        }
        //Lam phep nhap da
        if (charz.menuBoard.typeInfo == 25) {
            if (items.length == 2 && items[0] != null && items[1] != null && ((items[0].quantity >= 10 && items[0].template.type == 15 && items[1].template.type == 16) || (items[1].quantity >= 10 && items[1].template.type == 15 && items[0].template.type == 16))) {
                if (charz.getEmptyBagIndex() == -1) {
                    charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                } else if (charz.xu < coin_nhap) {
                    charz.resetMenu();
                    charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT6, 7, Util.gI().numberTostring(coin_nhap));
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.CONTHIEU_VANG, Util.gI().numberTostring(coin_nhap - charz.xu)), 25));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                } else if (charz.xu >= coin_nhap) {
                    charz.arrItem = items;
                    charz.resetMenu();
                    charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT6, 2, Util.gI().numberTostring(coin_nhap));
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.PHEP_NHAP_DA_2, Util.gI().numberTostring(coin_nhap)), 26));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 25));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                }
            } else {
                charz.session.service.startOKDlg(mResources.PHEP_NHAP_DA_1);
            }
            charz.menuBoard.typeInfo = 25;
        }
        //Nang cap Porata
        if (charz.menuBoard.typeInfo == 23) {
            if (items.length == 2 && items[0] != null && items[1] != null && ((items[0].template.id == 454 && items[1].template.id == 933) || (items[1].template.id == 454 && items[0].template.id == 933))) {
                Item bt;
                Item md;
                if (items[0].template.id == 454) {
                    bt = items[0];
                    md = items[1];
                } else {
                    bt = items[1];
                    md = items[0];
                }
                int coinUp = 5000000;
                int goldUp = 20;
                int percent = 50;
                int numMD = 9999;
                int numMD2 = 99;
                charz.resetMenu();
                charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT9, percent, numMD > md.getParamOption(31) ? 7 : 2, numMD, md.template.name, coinUp > charz.xu ? 7 : 2, Util.gI().numberTostring(coinUp), goldUp > charz.getLuong() ? 7 : 2, Util.gI().numberTostring(goldUp), numMD2);
                if (charz.xu < coinUp || charz.getLuong() < goldUp || md.getParamOption(31) < numMD) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 23));
                } else {
                    charz.arrItem = items;
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UP_BT, Util.gI().numberTostring(coinUp), Util.gI().numberTostring(goldUp)), 24));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 23));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_2);
            }
            charz.menuBoard.typeInfo = 23;
        }
        // btc3
        if (charz.menuBoard.typeInfo == 517) {
            if (items.length == 2 && items[0] != null && items[1] != null && ((items[0].template.id == 921 && items[1].template.id == 2027) || (items[1].template.id == 921 && items[0].template.id == 2027))) {
                Item bt;
                Item md;
                if (items[0].template.id == 921) {
                    bt = items[0];
                    md = items[1];
                } else {
                    bt = items[1];
                    md = items[0];
                }
                int coinUp = 5000000;
                int goldUp = 20;
                int percent = 50;
                int numMD = 9999;
                int numMD2 = 99;
                charz.resetMenu();
                charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT21, percent, numMD > md.getParamOption(31) ? 7 : 2, numMD, md.template.name, coinUp > charz.xu ? 7 : 2, Util.gI().numberTostring(coinUp), goldUp > charz.getLuong() ? 7 : 2, Util.gI().numberTostring(goldUp), numMD2);
                if (charz.xu < coinUp || charz.getLuong() < goldUp || md.getParamOption(31) < numMD) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 23));
                } else {
                    charz.arrItem = items;
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UP_BT, Util.gI().numberTostring(coinUp), Util.gI().numberTostring(goldUp)), 519));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 23));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_BTC3_2);
            }
            charz.menuBoard.typeInfo = 517;
        }
        //Mo chi so Potara 2
        if (charz.menuBoard.typeInfo == 21) {
            if (isHaveItem(items, 921) && isHaveItem(items, 934) && isHaveItem(items, 935)) {
                Item bt = getItem(items, 921);
                Item mh = getItem(items, 934);
                Item dxl = getItem(items, 935);

                int percent = 50;
                int numMH = 99;
                int numDXL = 1;
                int goldUp = 250;
                charz.resetMenu();
                charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT10, percent, numMH > mh.quantity ? 7 : 2, numMH, mh.template.name, numDXL > dxl.quantity ? 7 : 2, numDXL, dxl.template.name, goldUp > charz.getLuong() ? 7 : 2, Util.gI().numberTostring(goldUp));
                if (charz.getLuong() < goldUp || mh.quantity < numMH || dxl.quantity < numDXL) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 21));
                } else {
                    charz.arrItem = items;
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UP_BT2, Util.gI().numberTostring(goldUp)), 22));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 21));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_3);
            }
            charz.menuBoard.typeInfo = 21;
        }
        //mo chi so btc3
        if (charz.menuBoard.typeInfo == 522) {
            if (isHaveItem(items, 2026) && isHaveItem(items, 934) && isHaveItem(items, 935)) {
                Item bt = getItem(items, 2026);
                Item mh = getItem(items, 934);
                Item dxl = getItem(items, 935);

                int percent = 50;
                int numMH = 99;
                int numDXL = 1;
                int goldUp = 250;
                charz.resetMenu();
                charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT_BTC3, percent, numMH > mh.quantity ? 7 : 2, numMH, mh.template.name, numDXL > dxl.quantity ? 7 : 2, numDXL, dxl.template.name, goldUp > charz.getLuong() ? 7 : 2, Util.gI().numberTostring(goldUp));
                if (charz.getLuong() < goldUp || mh.quantity < numMH || dxl.quantity < numDXL) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 21));
                } else {
                    charz.arrItem = items;
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UP_BT2, Util.gI().numberTostring(goldUp)), 524));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 21));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_3);
            }
            charz.menuBoard.typeInfo = 522;
        }
        //Nang cap
        if (charz.menuBoard.typeInfo == 19) {
            if (items != null && Combine.getItemBody(items) != null && Combine.getItemDa(items) != null && Combine.getItemDa(items).isOption(idOption(Combine.getItemBody(items).template.type)) && (items.length == 2 || (items.length == 3 && (getItem(items, 987) != null || getItem(items, 1143) != null || getItem(items, 205) != null)) || (items.length == 4 && (getItem(items, 987) != null || getItem(items, 1143) != null) && getItem(items, 205) != null))) {
                Item tb = Combine.getItemBody(items);
                Item da = Combine.getItemDa(items);
                Item dbv = getItem(items, 987) == null ? getItem(items, 1143) : getItem(items, 987);
                int upgrade = tb.getParamOption(72);
                if (upgrade >= max_Up) {
                    charz.session.service.startOKDlg(mResources.UP_FULL);
                } else {
                    byte b1 = 2;
                    byte b2 = 2;
                    if (dap_dasl[upgrade] > da.quantity) {
                        b1 = 7;
                    }
                    if (charz.xu < dap_coin[upgrade]) {
                        b2 = 7;
                    }
                    charz.resetMenu();
                    if (upgrade > 0) {
                        if (charz.xu < dap_coin[upgrade]) {
                            charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT8, tb.template.name, upgrade, tb.optionCombine(), upgrade + 1, tb.optionCombine1(), dap_percent[upgrade], b1, dap_dasl[upgrade], da.template.name, b2, Util.gI().numberTostring(dap_coin[upgrade]), downgrade[upgrade]);
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.CONTHIEU_VANG, Util.gI().numberTostring(dap_coin[upgrade] - charz.xu)), 19));
                        } else {
                            charz.arrItem = items;
                            charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT8, tb.template.name, upgrade, tb.optionCombine(), upgrade + 1, tb.optionCombine1(), dap_percent[upgrade], b1, dap_dasl[upgrade], da.template.name, b2, Util.gI().numberTostring(dap_coin[upgrade]), downgrade[upgrade]);
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UP_COIN, Util.gI().numberTostring(dap_coin[upgrade])), 20));
                            charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 19));
                        }
                    } else {
                        if (charz.xu < dap_coin[upgrade]) {
                            charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT7, tb.template.name, tb.optionCombine(), upgrade + 1, tb.optionCombine1(), dap_percent[upgrade], b1, dap_dasl[upgrade], da.template.name, b2, Util.gI().numberTostring(dap_coin[upgrade]));
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.CONTHIEU_VANG, Util.gI().numberTostring(dap_coin[upgrade] - charz.xu)), 19));
                        } else {
                            charz.arrItem = items;
                            charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT7, tb.template.name, tb.optionCombine(), upgrade + 1, tb.optionCombine1(), dap_percent[upgrade], b1, dap_dasl[upgrade], da.template.name, b2, Util.gI().numberTostring(dap_coin[upgrade]));
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UP_COIN, Util.gI().numberTostring(dap_coin[upgrade])), 20));
                            charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 19));
                        }
                    }
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                }
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_1);
            }
            charz.menuBoard.typeInfo = 19;
        }
        //Ep sao pha le
        if (charz.menuBoard.typeInfo == 10) {
            if (items != null && items.length == 2 && items[0] != null && items[1] != null && ((items[0].isTypeBody() && items[0].getStarWhite() > items[0].getStarBlue() && (items[1].isItemWishGem() || items[1].isItemStar())) || (items[1].isTypeBody() && items[1].getStarWhite() > items[1].getStarBlue() && (items[0].isItemWishGem() || items[0].isItemStar())))) {
                Item item2;
                Item itEp;
                if (items[0].isTypeBody()) {
                    item2 = items[0];
                    itEp = items[1];
                } else {
                    item2 = items[1];
                    itEp = items[0];
                }
                charz.resetMenu();
                if (charz.getLuong() < ngoc_ep) {
                    charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT5, item2.template.name, item2.optionCombine(itEp), 7, ngoc_ep);
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.CONTHIEU_VANG, Util.gI().numberTostring(ngoc_ep - charz.getLuong())), 10));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 10));
                } else if (charz.getLuong() >= ngoc_ep) {
                    charz.arrItem = items;
                    charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT5, item2.template.name, item2.optionCombine(itEp), 2, ngoc_ep);
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.NANGCAP_HOAPHALE, ngoc_ep), 11));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 10));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
            } else {
                charz.session.service.startOKDlg(mResources.JOIN_STAR_1);
            }
            charz.menuBoard.typeInfo = 10;
        }
        //Duc lo
        if (charz.menuBoard.typeInfo == 12) {
            if (items != null && items.length == 1 && items[0] != null && items[0].isTypeBody()) {
                Item item1 = items[0];
                if (!item1.isItemDucLo()) {
                    charz.session.service.startOKDlg(mResources.TRANGBI_O_PHU_HOP);
                } else {
                    int star = item1.getStarWhite() + 1;
                    if (item1.getStarWhite() >= Combine.max_Star) {
                        charz.session.service.sendThongBao(charz, mResources.STAR_FULL);
                        return;
                    } else {
                        int percent = percent_plh[star - 1];
                        int coin = coin_plh[star - 1];
                        int ngoc = ngoc_phl[star - 1];
                        charz.resetMenu();
                        if (charz.xu < coin) {
                            charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT4, item1.template.name, item1.optionCombine(star), percent, 7, Util.gI().numberTostring(coin));
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.CONTHIEU_VANG, Util.gI().numberTostring(coin - charz.xu)), 12));
                        } else if (charz.xu >= coin) {
                            charz.arrItem = items;
                            charz.menuBoard.chat = String.format(mResources.SAY_BA_HAT_MIT4, item1.template.name, item1.optionCombine(star), percent, 2, Util.gI().numberTostring(coin));
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.NANGCAP_HOAPHALE2, ngoc, 100), 13, 100));
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.NANGCAP_HOAPHALE2, ngoc, 10), 13, 10));
                            charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.NANGCAP_HOAPHALE, ngoc), 13, -1));
                            charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 12));
                        }
                        charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                    }
                }
            } else {
                charz.session.service.startOKDlg(mResources.TRANGBI_O_PHU_HOP);
            }
            charz.menuBoard.typeInfo = 12;
        }
        //Che tao tang bi thien su
        if (charz.menuBoard.typeInfo == 171) {
            if (items != null) {
                Item fm = null;
                Item gra = null;
                Item dnm = null;
                Item dmm = null;
                for (int i2 = 0; i2 < items.length; i2 = i2 + 1) {
                    if (items[i2] != null) {
                        if (items[i2].isItemFormula() && fm == null) {
                            fm = items[i2];
                        } else if (items[i2].isItemGraft() && gra == null) {
                            gra = items[i2];
                        } else if (items[i2].isItemRuby() && dnm == null) {
                            dnm = items[i2];
                        } else if (items[i2].isItemCrystal() && dmm == null) {
                            dmm = items[i2];
                        } else {
                            charz.session.service.startOKDlg(mResources.SAI_NGUYEN_LIEU);
                            return;
                        }
                    }
                }
                if (fm != null && gra != null) {
                    int totalPercent = 25;
                    if (fm.isItemFormulaVIP()) {
                        totalPercent = 35;
                    }
                    int addPercent = Combine.addPercentRuby(dnm);
                    totalPercent = totalPercent + addPercent;
                    boolean isGold = charz.xu >= 200000000;
                    boolean isGra = gra.quantity >= 999;
                    charz.resetMenu();
                    charz.menuBoard.chat = String.format(mResources.SAY_WISH_3, Combine.srcTX(gra, fm), isGra ? 2 : 7, gra.quantity, dnm != null ? dnm.template.name : mResources.KHONG_DUNG_DA_NANG_CAP, addPercent, dmm != null ? dmm.template.name : mResources.KHONG_DUNG_DA_MAY_MAN, Combine.addPercentCry(dmm), totalPercent, isGold ? 2 : 7);
                    if (isGold && isGra) {
                        charz.arrItem = items;
                        charz.menuBoard.arrMenu.add(new MenuInfo(mResources.AGREE, 172));
                    }
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 171));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                } else {
                    charz.session.service.startOKDlg(mResources.KHONG_DU_NGUYEN_LIEU);
                }
            }
            charz.menuBoard.typeInfo = 171;
        }
//        if (charz.menuBoard.typeInfo == 357) { // nâng skh thần linh
//            if (items.length == 6 && items[0] != null && items[1] != null && items[2] != null && items[3] != null && items[4] != null && items[5] != null) {
//                Item thanLinh1 = null;
//                Item thanLinh2 = null;
//                Item thanLinh3 = null;
//                Item thanLinh4 = null;
//                Item nhanthan = null;
//                Item dangusac = null;
//                Item tv = null;
//                int count = 0;
//                for (Item item : items) {
//                    if (item.isDothanlinh()) {
//                        switch (count) {
//                            case 0:
//                                thanLinh1 = item;
//                                break;
//                            case 1:
//                                thanLinh2 = item;
//                                break;
//                            case 2:
//                                thanLinh3 = item;
//                                break;
//                            case 3:
//                                thanLinh4 = item;
//                                break;
//                        }
//                        count++;
//                    } else if (item.template.id == 561) {
//                        nhanthan = item;
//                    } else if (item.template.id == 674) {
//                        dangusac = item;
//                    }
//                }
//                charz.resetMenu();
//                if (thanLinh1 == null || thanLinh2 == null || thanLinh3 == null || thanLinh4 == null || nhanthan == null || dangusac == null) {
//                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 357));
//                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
//                } else {
//                    charz.arrItem = items;
//                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UPTV, Util.gI().numberTostring(5)), 359));
//                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 357));
//                }
//                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, "Bạn có muốn nâng cấp Trang bị thành Sét kích hoạt \n"
//                        + "Với tỉ lệ : 30% \n"
//                        + "Nếu sịt mất 2 món thần linh"
//                        + " không?", null, -1); //
//            } else {
//                charz.session.service.startOKDlg("Hãy đặt đủ nguyên liệu và trang bị vào để nâng cấp.");
//            }
//            charz.menuBoard.typeInfo = 357;
//        }
        if (charz.menuBoard.typeInfo == 357) { // nâng skh 181120
            if (items.length == 3 && items[0] != null && items[1] != null && items[2] != null) {
                Item huydiet = null;
                Item thanlinh = null;
                final int THOI_VANG_ID = 457;
                Item tv = null;
                for (Item item : items) {
                    if (item.isDohuydiet()) {
                        huydiet = item;
                    } else if (item.isDothanlinh()) {
                        thanlinh = item;
                    } else if (item.template.id == THOI_VANG_ID) {
                        tv = item;
                    }
                }
                charz.resetMenu();
                if (huydiet == null || thanlinh == null) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 357));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                } else {
                    charz.arrItem = items;
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UPTV, Util.gI().numberTostring(20)), 359));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 357));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, "Bạn có muốn nâng cấp Trang bị thành Sét kích hoạt \n"
                        + "Với tỉ lệ : 30% \n"
                        + "Nếu sịt mất 1 món hủy diệt"
                        + " không?", null, -1);
            } else {
                charz.session.service.startOKDlg("Hãy đặt đủ nguyên liệu và trang bị vào để nâng cấp.");
            }
            charz.menuBoard.typeInfo = 357;
        }

        if (charz.menuBoard.typeInfo == 361) { // nâng cấp level black
            if (items.length == 2 && items[0] != null && items[1] != null) {
                final int DA_NGUYEN_AN_ID = 1904;
                final int THOI_VANG_ID = 457;
                Item danguyenan = null;
                Item thoivang = null;
                for (Item item : items) {
                    if (item.template.id == DA_NGUYEN_AN_ID) {
                        danguyenan = item;
                    } else if (item.template.id == THOI_VANG_ID) {
                        thoivang = item;
                    }
                }
                charz.resetMenu();
                if (thoivang == null || danguyenan == null) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 361));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                } else {
                    charz.arrItem = items;
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format(mResources.UPTV, Util.gI().numberTostring(10)), 363));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 361));
                }
                charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, "|7|Bạn có muốn nâng cấp đệ tử black lên level này\n"
                        + "|3|Với tỉ lệ : 30% \n"
                        + " không?", null, -1);
            } else {
                charz.session.service.startOKDlg("Hãy đặt đủ nguyên liệu ô nâng cấp.");
            }
            charz.menuBoard.typeInfo = 361;
        }
        if (charz.menuBoard.typeInfo == 504) {
            if (items != null && items.length == 3) {
                List<Item> huyDietList = new ArrayList<>();

                for (Item item : items) {
                    if (item == null) {
                        continue;
                    }
                    int id = item.template.id;
                    if (id >= 650 && id <= 662) {
                        huyDietList.add(item);
                    }
                }

                if (huyDietList.size() < 3) {
                    charz.session.service.startOKDlg("Cần đúng 3 món Hủy Diệt.");
                } else if (charz.xu < 500_000_000) {
                    charz.session.service.startOKDlg("Bạn không đủ 500 triệu vàng để nâng cấp.");
                } else if (charz.getEmptyBagCount() < 1) {
                    charz.session.service.startOKDlg("Không còn chỗ trống trong túi đồ.");
                } else {
                    charz.arrItem = items;
                    charz.resetMenu();
                    charz.menuBoard.chat = "Bạn chắc chắn muốn nâng cấp thành Set Kích Hoạt?";
                    charz.menuBoard.arrMenu.add(new MenuInfo("Đồng ý", 506)); // ID thực hiện nâng cấp
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 504));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                }
            } else {
                charz.session.service.startOKDlg("Hãy đặt đúng 3 huỷ diệt vào khung nâng cấp.");
            }
            charz.menuBoard.typeInfo = 504;
        }
        if (charz != null && charz.menuBoard != null && charz.menuBoard.typeInfo == 514) { // nâng chân mệnh
            if (items != null && items.length == 2
                    && items[0] != null
                    && items[1] != null) {

                Item danguctu = null;
                Item chan_menh = null;
                for (Item item : items) {
                    if (item != null && item.template != null) { // Kiểm tra null trước khi dùng
                        if (item.template.id == 1988) {
                            danguctu = item;
                        } else if (item.template.id >= 2013 && item.template.id <= 2021) {
                            chan_menh = item;
                        }
                    }
                }

                charz.resetMenu();

                if (danguctu == null || chan_menh == null) {
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 514));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                } else {
                    charz.menuBoard.arrMenu.add(new MenuInfo(String.format("Nâng cấp %s Ngọc", Util.gI().numberTostring(10)), 516));
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 514));
                }
                charz.arrItem = items;
                java.util.Map<Integer, Integer> successRate = new HashMap<>();
                successRate.put(4, 50);
                successRate.put(5, 30);
                successRate.put(6, 20);
                successRate.put(7, 10);
                successRate.put(8, 5);
                if (chan_menh != null && chan_menh.template != null) {
                    int capChanMenh = (chan_menh.template.id - 2013) + 1; // Vì ID 2013 tương ứng cấp 1
                    int tyLe = successRate.getOrDefault(capChanMenh, 100);

                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId,
                            "Ngươi có muốn nâng cấp Chân mệnh này không?\n"
                            + "Với tỉ lệ : " + tyLe + "%\n"
                            + "Cần x50 Đá ngục tu\n"
                            + " không?", null, -1);
                } else {
                    charz.session.service.startOKDlg("Bạn chưa đặt Chân Mệnh vào ");
                }
            } else {
                charz.session.service.startOKDlg("Hãy đặt đủ nguyên liệu vào ô Nâng cấp ");
            }

            charz.menuBoard.typeInfo = 514;
        }
    }

    public static void NangcapDetuBlack(Char charz) {
        final int DA_NGUYEN_AN_ID = 1904;
        final int THOI_VANG_ID = 457;
        final int QUANTITY_DA_NGUYEN_AN = 10;
        final int QUANTITY_THOI_VANG = 10;

        if (charz.myPet == null || !charz.myPet.isBlack) {
            charz.session.service.startOKDlg("Cần có đệ tử Black.");
            return;
        }

        if (charz.myPet.levelpet >= 7) {
            charz.session.service.startOKDlg("Cấp đệ tử tối đa.");
            return;
        }

        Item danguyenan = null;
        Item thoivang = null;

        for (Item item : charz.arrItem) {
            if (item != null) {
                if (item.template.id == DA_NGUYEN_AN_ID) {
                    danguyenan = item;
                } else if (item.template.id == THOI_VANG_ID) {
                    thoivang = item;
                }
            }
        }

        if (danguyenan == null || thoivang == null) {
            charz.session.service.startOKDlg("Cần có x10 đá nguyền ấn và x10 thỏi vàng.");
            return;
        }

        int danguyenanQuantity = charz.getItemQuantity(DA_NGUYEN_AN_ID);
        int ThoivangQuantity = charz.getItemQuantity(THOI_VANG_ID);

        if (danguyenanQuantity < QUANTITY_DA_NGUYEN_AN) {
            charz.session.service.startOKDlg("Không đủ x10 đá nguyền ấn để thực hiện!");
            return;
        }
        if (ThoivangQuantity < QUANTITY_THOI_VANG) {
            charz.session.service.startOKDlg("Không đủ x10 thỏi vàng để thực hiện!");
            return;
        }

        charz.addQuantityItemBag(danguyenan.indexUI, -QUANTITY_DA_NGUYEN_AN);
        charz.addQuantityItemBag(thoivang.indexUI, -QUANTITY_THOI_VANG);
        charz.session.service.Bag(charz.arrItemBag);
        if (Util.isTrue(90, 100)) {
            charz.session.service.setCombineEff(3, -1, -1, -1);
            return;
        }
        charz.myPet.levelpet += 1;
        charz.session.service.setCombineEff(2, -1, -1, -1);
        charz.session.service.Bag(charz.arrItemBag);
        int[] arr = new int[charz.arrItem.length];
        for (int i = 0; i < charz.arrItem.length; i++) {
            arr[i] = charz.arrItem[i].indexUI;
        }
        charz.session.service.setCombineEff(arr, -1);
    }
//    public static void NangsetkichhoatThanlinh(Char charz) {
//        final int FAILURE_RATE = 30;
//
//        if (charz.arrItem.length == 6
//                && charz.arrItem[0] != null
//                && charz.arrItem[1] != null
//                && charz.arrItem[2] != null
//                && charz.arrItem[3] != null
//                && charz.arrItem[4] != null
//                && charz.arrItem[5] != null
//                && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0]
//                && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]
//                && charz.arrItemBag[charz.arrItem[2].indexUI] == charz.arrItem[2]
//                && charz.arrItemBag[charz.arrItem[3].indexUI] == charz.arrItem[3]
//                && charz.arrItemBag[charz.arrItem[4].indexUI] == charz.arrItem[4]
//                && charz.arrItemBag[charz.arrItem[5].indexUI] == charz.arrItem[5]) {
//
//            Item thanLinh1 = null;
//            Item thanLinh2 = null;
//            Item thanLinh3 = null;
//            Item thanLinh4 = null;
//            Item nhanthan = null;
//            Item dangusac = null;
//            Item tv = null;
//
//            int count = 0;
//            for (Item item : charz.arrItem) {
//                if (item == null) {
//                    continue;
//                }
//                if (item.isSKHVip()) {
//                    thanLinh1 = item;
//                } else if (item.isDothanlinh()) {
//                    switch (count) {
//                        case 0:
//                            thanLinh2 = item;
//                            break;
//                        case 1:
//                            thanLinh3 = item;
//                            break;
//                        case 2:
//                            thanLinh4 = item;
//                            break;
//                    }
//                    count++;
//                } else if (item.template.id == 561) {
//                    nhanthan = item;
//                } else if (item.template.id == 674) {
//                    dangusac = item;
//                } else if (item.template.id == 457) {
//                    tv = item;
//                }
//            }
//            // Kiểm tra đủ điều kiện nâng cấp
//            if (thanLinh1 == null || thanLinh2 == null || thanLinh3 == null || thanLinh4 == null || nhanthan == null || dangusac == null) {
//                charz.session.service.startOKDlg("Hãy đạt 1 set thần linh bất kì.");
//                return;
//            }
//            int tvquanti = charz.getItemQuantity(5);
//            if (tvquanti < 5) {
//                charz.session.service.startOKDlg("Không đủ thỏi vàng để thực hiện!");
//                return;
//            }
//            charz.addQuantityItemBag(tv.indexUI, -5);
//            charz.addQuantityItemBag(thanLinh2.indexUI, -1);
//            charz.addQuantityItemBag(thanLinh3.indexUI, -1);
//            // Kiểm tra tỉ lệ sịt
//            if (Util.isTrue(FAILURE_RATE, 100)) {
//                charz.addQuantityItemBag(thanLinh1.indexUI, -1);
//                charz.session.service.setCombineEff(1, -1, -1, -1);
//                return;
//            }
//            // Trừ nguyên liệu
//            // charz.addQuantityItemBag(thanunh2.indexUI, -1);
//            charz.addQuantityItemBag(nhanthan.indexUI, -1);
//            for (ItemOption option : tv.options) {
//                if (option.optionTemplate.id >= 127 && option.optionTemplate.id <= 144) {
//                    thanLinh1.options.add(new ItemOption(option.optionTemplate.id, option.param));
//                }
//            }
//
//            // Cập nhật thành công
//            charz.session.service.setCombineEff(2, -1, -1, -1); // Hiệu ứng thành công
//            charz.session.service.Bag(charz.arrItemBag);
//            int[] arr = new int[charz.arrItem.length];
//            for (int i = 0; i < charz.arrItem.length; i++) {
//                arr[i] = charz.arrItem[i].indexUI;
//            }
//        } else {
//            charz.session.service.startOKDlg("Hãy đặt đúng nguyên liệu và trang bị vào ô nâng cấp.");
//        }
//    }

    public static void NangsetkichhoatThanlinh(Char charz) {
        final int FAILURE_RATE = 30;
        final int QUANTITY_THOI_VANG = 10;
        final int THOI_VANG_ID = 457;

        if (charz.arrItem.length == 3
                && charz.arrItem[0] != null
                && charz.arrItem[1] != null
                && charz.arrItem[2] != null
                && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0]
                && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]
                && charz.arrItemBag[charz.arrItem[2].indexUI] == charz.arrItem[2]) {

            Item huydiet = null;
            Item thanlinh = null;
            Item tv = null;

            for (Item item : charz.arrItem) {
                if (item.isDohuydiet()) {
                    huydiet = item;
                } else if (item.isDothanlinh()) {
                    thanlinh = item;
                } else if (item.template.id == THOI_VANG_ID) {
                    tv = item;
                }
            }
            // Kiểm tra đủ điều kiện nâng cấp
            if (huydiet == null || thanlinh == null) {
                charz.session.service.startOKDlg("Hãy đặt đồ thần linh , 1 đồ hủy diệt cùng loại.");
                return;
            }
            int tvquanti = charz.getItemQuantity(20);
            if (tvquanti < 20) {
                charz.session.service.startOKDlg("Không đủ thỏi vàng để thực hiện!");
                return;
            }
            charz.addQuantityItemBag(thanlinh.indexUI, -1);
            charz.addQuantityItemBag(huydiet.indexUI, -1);
            charz.addQuantityItemBag(tv.indexUI, -QUANTITY_THOI_VANG);
            charz.session.service.Bag(charz.arrItemBag);
            // Kiểm tra tỉ lệ sịt
            if (Util.isTrue(FAILURE_RATE, 100)) {
//                charz.addQuantityItemBag(huydiet.indexUI, -1);
//                charz.session.service.setCombineEff(3, -1, -1, -1);
                charz.session.service.setCombineEff(1, -1, -1, -1);
                return;
            }
            // Trừ nguyên liệu
            charz.addQuantityItemBag(thanlinh.indexUI, -1);
            for (ItemOption option : huydiet.options) {
                if (option.optionTemplate.id >= 127 && option.optionTemplate.id <= 144) {
                    thanlinh.options.add(new ItemOption(option.optionTemplate.id, option.param));
                }
            }

            // Cập nhật thành công
            charz.session.service.setCombineEff(2, -1, -1, -1); // Hiệu ứng thành công
            charz.session.service.Bag(charz.arrItemBag);
            int[] arr = new int[charz.arrItem.length];
            for (int i = 0; i < charz.arrItem.length; i++) {
                arr[i] = charz.arrItem[i].indexUI;
            }
        } else {
            charz.session.service.startOKDlg("Hãy đặt đúng nguyên liệu và trang bị vào ô nâng cấp.");
        }
    }

    public static void nangSKH(Char charz) {
        if (charz == null || charz.arrItem == null || charz.arrItem.length < 3) {
            charz.session.service.startOKDlg("đặt đúng 3 món vật phẩm vào nâng cấp");
            return;
        }
        List<Item> huyDietList = new ArrayList<>();
        for (Item item : charz.arrItem) {
            if (item == null) {
                continue;
            }
            int id = item.template.id;
            if (id >= 650 && id <= 662) {
                huyDietList.add(item);
            }
        }
        if (huyDietList.size() < 3) {
            charz.session.service.startOKDlg("Cần  3 món huỷ diệt");
            return;
        }
        if (charz.xu < 500_000_000) {
            charz.session.service.startOKDlg("Cần 500tr vàng");
            return;
        }
        int[] arrIndex = new int[3];
        for (int i = 0; i < 3; i++) {
            arrIndex[i] = (charz.arrItem[i] != null) ? charz.arrItem[i].indexUI : -1;
        }

        for (Item item : huyDietList.subList(0, 3)) {
            charz.removeItemBag(item.indexUI);
        }
        Arrays.fill(charz.arrItem, null);

        int gender = charz.cgender;

        int type = huyDietList.get(0).template.type;
// int[] arrSetId = (gender == 0) ? new int[]{0, 6, 12, 21, 27} // td
//                : (gender == 1) ? new int[]{1, 7, 12, 22, 28} // nm
//                : new int[]{2, 8, 12, 23, 29}; // xd 
        // type ao = 0
        // type quan = 1
        // type gang = 2
        // type giay = 3
        // type rada = 4        

//        if (type == 0) {
////            if gender == 0 ? new int [] {0, 33, 3, 34, 136, 137, 139, 230, 231, 232, 233} 
////                : gender == 1 ? new int[]{1, 41, 4, 42, 152, 153, 154, 155, 234, 235, 236, 237}
////                : new int [] {2, 49, 5, 50, 168, 169, 170, 171, 238, 239, 240,  241};
//            setId = gender == 0 ? 0 : gender == 1 ? 1 : 2;
//        } else if (type == 1) {
//            setId = gender == 0 ? 6 : gender == 1 ? 7 : 8;
//        } else if (type == 2) {
//            setId = gender == 0 ? 21 : gender == 1 ? 22 : 23;
//        } else if (type == 3) {
//            setId = gender == 0 ? 27 : gender == 1 ? 28 : 29;
//        } else if (type == 4) {
//            setId = 12;
//        } else {
//            charz.session.service.startOKDlg("Lỗi mẹ rồi chửi thằng aadmin thôi");
//            return;
//        }
        int setId;
        int[] items;
        if (type == 0) {
            if (gender == 0) {
                items = new int[]{0, 33, 3, 34, 136, 137, 138, 139, 230, 231, 232, 233};
            } else if (gender == 1) {
                items = new int[]{1, 41, 4, 42, 152, 153, 154, 155, 234, 235, 236, 237};
            } else {
                items = new int[]{2, 49, 5, 50, 168, 169, 170, 171, 238, 239, 240, 241};
            }
        } else if (type == 1) {
            if (gender == 0) {
                items = new int[]{6, 35, 9, 36, 140, 141, 142, 143, 242, 243, 244, 245};
            } else if (gender == 1) {
                items = new int[]{7, 43, 10, 44, 156, 157, 158, 159, 246, 247, 248, 249};
            } else {
                items = new int[]{8, 51, 11, 52, 172, 173, 174, 175, 250, 251, 252, 253};
            }
        } else if (type == 2) {
            if (gender == 0) {
                items = new int[]{21, 24, 37, 38, 144, 145, 146, 147, 254, 255, 256, 257};
            } else if (gender == 1) {
                items = new int[]{22, 46, 25, 45, 160, 161, 162, 163, 258, 259, 260, 261};
            } else {
                items = new int[]{23, 53, 26, 54, 176, 177, 178, 179, 262, 263, 264, 265};
            }
        } else if (type == 3) {
            if (gender == 0) {
                items = new int[]{27, 30, 39, 40, 148, 149, 150, 151, 266, 267, 268, 269};
            } else if (gender == 1) {
                items = new int[]{28, 47, 31, 48, 164, 165, 166, 167, 270, 271, 272, 273};
            } else {
                items = new int[]{29, 55, 32, 56, 180, 181, 182, 183, 274, 275, 276, 277};
            }
        } else if (type == 4) {
            items = new int[]{12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281};
        } else {
            charz.session.service.startOKDlg("Lỗi con mẹ nó rồi");
            return;
        }

        Random rand = new Random();
        setId = items[rand.nextInt(items.length)];

        int status = Util.gI().nextInt(1, 3);
        ArrayList<ItemOption> options = ItemOption.getOption(setId, status, gender);
        Item itemSKH = new Item(setId, false, 1, options, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
//        Item itemSKH = new Item(setId, false, 1, new ArrayList<>(), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
        ItemOption.addSKH(charz, itemSKH, Util.gI().nextInt(1, 3));
//        ArrayList<ItemOption> options = ItemOption.getoption(charz, itemSKH, Util.gI().nextInt(1, 3));
//        Item itemSKH = new Item(setId, false, 1, options, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);

        for (ItemOption option : itemSKH.options) {
            System.out.println("option item: > " + option.optionTemplate.name);
        }
        int indexBag = charz.getEmptyBagIndex();
        charz.addItemBag(itemSKH, indexBag);
        charz.updateXu(-500_000_000, 2);
        charz.session.service.Bag(charz.arrItemBag);
        charz.session.service.setCombineEff(2, -1, -1, -1);
        int[] arr = new int[charz.arrItem.length];
        for (int i = 0; i < charz.arrItem.length; i++) {
            arr[i] = (charz.arrItem[i] != null) ? charz.arrItem[i].indexUI : -1;
        }
        charz.session.service.setCombineEff(arr, -1);
    }

    public static void EpNgoc(Char charz) {
        int i;
        if (charz.arrItem.length == 2 && charz.arrItem[0] != null && charz.arrItem[1] != null && ((charz.arrItem[0].isTypeBody() && charz.arrItem[0].getStarWhite() > charz.arrItem[0].getStarBlue() && (charz.arrItem[1].isItemWishGem() || charz.arrItem[1].isItemStar())) || (charz.arrItem[1].isTypeBody() && charz.arrItem[1].getStarWhite() > charz.arrItem[1].getStarBlue() && (charz.arrItem[0].isItemWishGem() || charz.arrItem[0].isItemStar()))) && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0] && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]) {
            Item item2;
            Item itEp;
            if (charz.arrItem[0].isTypeBody()) {
                item2 = charz.arrItem[0];
                itEp = charz.arrItem[1];
            } else {
                item2 = charz.arrItem[1];
                itEp = charz.arrItem[0];
            }
            int star = item2.getStarWhite() + 1;
            if (ngoc_ep > charz.getLuong()) {

            } else {
                boolean b1 = false;
                boolean b2 = false;
                //Neu co tang them ko co tao moi
                for (i = 0; i < item2.options.size(); i++) {
                    if (item2.options.get(i).optionTemplate.id == 102) {
                        item2.options.get(i).param++;
                        b2 = true;
                    }
                    if (itEp.template.id == 14 && item2.options.get(i).optionTemplate.id == 108) {
                        b1 = true;
                        item2.options.get(i).param += 2;
                    }
                    if (itEp.template.id == 15 && item2.options.get(i).optionTemplate.id == 94) {
                        b1 = true;
                        item2.options.get(i).param += 2;
                    }
                    if (itEp.template.id == 16 && item2.options.get(i).optionTemplate.id == 147) {
                        b1 = true;
                        item2.options.get(i).param += 3;
                    }
                    if (itEp.template.id == 17 && item2.options.get(i).optionTemplate.id == 81) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 18 && item2.options.get(i).optionTemplate.id == 80) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 19 && item2.options.get(i).optionTemplate.id == 103) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 20 && item2.options.get(i).optionTemplate.id == 77) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 441 && item2.options.get(i).optionTemplate.id == 95) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 442 && item2.options.get(i).optionTemplate.id == 96) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 443 && item2.options.get(i).optionTemplate.id == 97) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 444 && item2.options.get(i).optionTemplate.id == 98) {
                        b1 = true;
                        item2.options.get(i).param += 3;
                    }
                    if (itEp.template.id == 445 && item2.options.get(i).optionTemplate.id == 99) {
                        b1 = true;
                        item2.options.get(i).param += 3;
                    }
                    if (itEp.template.id == 446 && item2.options.get(i).optionTemplate.id == 100) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                    if (itEp.template.id == 447 && item2.options.get(i).optionTemplate.id == 101) {
                        b1 = true;
                        item2.options.get(i).param += 5;
                    }
                }
                if (!b1) {
                    if (itEp.template.id == 14) {
                        item2.options.add(new ItemOption(108, 2));
                    }
                    if (itEp.template.id == 15) {
                        item2.options.add(new ItemOption(94, 2));
                    }
                    if (itEp.template.id == 16) {
                        item2.options.add(new ItemOption(147, 3));
                    }
                    if (itEp.template.id == 17) {
                        item2.options.add(new ItemOption(81, 5));
                    }
                    if (itEp.template.id == 18) {
                        item2.options.add(new ItemOption(80, 5));
                    }
                    if (itEp.template.id == 19) {
                        item2.options.add(new ItemOption(103, 5));
                    }
                    if (itEp.template.id == 20) {
                        item2.options.add(new ItemOption(77, 5));
                    }
                    if (itEp.template.id == 441) {
                        item2.options.add(new ItemOption(95, 5));
                    }
                    if (itEp.template.id == 442) {
                        item2.options.add(new ItemOption(96, 5));
                    }
                    if (itEp.template.id == 443) {
                        item2.options.add(new ItemOption(97, 5));
                    }
                    if (itEp.template.id == 444) {
                        item2.options.add(new ItemOption(98, 3));
                    }
                    if (itEp.template.id == 445) {
                        item2.options.add(new ItemOption(99, 3));
                    }
                    if (itEp.template.id == 446) {
                        item2.options.add(new ItemOption(100, 5));
                    }
                    if (itEp.template.id == 447) {
                        item2.options.add(new ItemOption(101, 5));
                    }
                }
                if (!b2) {
                    item2.options.add(new ItemOption(102, 1));
                }
                charz.addQuantityItemBag(itEp.indexUI, -1);
                charz.session.service.setCombineEff(2, -1, -1, -1);
                charz.updateLuong(-ngoc_ep, 0);
                charz.session.service.meLoadInfo();
                charz.session.service.Bag(charz.arrItemBag);
            }
        } else {
            charz.session.service.startOKDlg(mResources.JOIN_STAR_1);
        }
    }

    public static int DucLo(Char charz) {
        if (charz.arrItem != null && charz.arrItem.length == 1 && charz.arrItem[0] != null && charz.arrItem[0].isTypeBody() && charz.arrItem[0] == charz.arrItemBag[charz.arrItem[0].indexUI]) {
            Item item1 = charz.arrItem[0];
            if (!item1.isItemDucLo()) {
                charz.session.service.startOKDlg(mResources.TRANGBI_O_PHU_HOP);
            } else {
                int star = item1.getStarWhite() + 1;
                if (item1.getStarWhite() >= Combine.max_Star) {
                    charz.session.service.sendThongBao(charz, mResources.STAR_FULL);
                } else {
                    int percent = percent_plh[star - 1];
                    int coin = coin_plh[star - 1];
                    int ngoc = ngoc_phl[star - 1];
                    if (charz.xu < coin) {
                        return 1;
                    } else if (ngoc > charz.getLuong()) {
                        return 2;
                    } else {
                        boolean b1;
                        int pP;
                        if (item1.getStarWhite() >= 7) {
                            pP = Util.gI().nextInt(650);
                        } else if (item1.getStarWhite() >= 6) {
                            pP = Util.gI().nextInt(540);
                        } else if (item1.getStarWhite() >= 5) {
                            pP = Util.gI().nextInt(430);
                        } else if (item1.getStarWhite() >= 4) {
                            pP = Util.gI().nextInt(320);
                        } else if (item1.getStarWhite() >= 3) {
                            pP = Util.gI().nextInt(120);
                        } else {
                            pP = Util.gI().nextInt(Util.gI().nextInt(1, 100));
                        }
                        if (item1.isItemSKH() || item1.template.type == 23) {
                            pP = pP * 2;
                        }
                        b1 = pP < percent;
                        boolean b2 = false;
                        if (b1) {
                            //Neu co tang them ko co tao moi
                            for (int i = 0; i < item1.options.size(); i++) {
                                if (item1.options.get(i).optionTemplate.id == 107) {
                                    item1.options.get(i).param++;
                                    b2 = true;
                                    break;
                                }
                            }
                            if (!b2) {
                                item1.options.add(new ItemOption(107, 1));
                            }
                            charz.session.service.setCombineEff(2, -1, -1, -1);
                            charz.updateTask(11, 1);
                        } else {
                            charz.session.service.setCombineEff(3, -1, -1, -1);
                        }
                        charz.updateXu(-coin, 0);
                        charz.updateLuong(-ngoc, 0);
                        charz.session.service.meLoadInfo();
                        charz.session.service.Bag(charz.arrItemBag);

                        int[] arr = new int[charz.arrItem.length];
                        for (int i = 0; i < charz.arrItem.length; i++) {
                            arr[i] = charz.arrItem[i].indexUI;
                        }
                        charz.session.service.setCombineEff(arr, -1);
                        return b1 ? 3 : 4;
                    }
                }
            }
        } else {
            charz.session.service.startOKDlg(mResources.TRANGBI_O_PHU_HOP);
        }
        return 0;
    }

    public static void NhapDa(Char charz) {
        int i;
        if (charz.arrItem != null && charz.arrItem.length == 2 && charz.arrItem[0] != null && charz.arrItem[1] != null && ((charz.arrItem[0].quantity >= 10 && charz.arrItem[0].template.type == 15 && charz.arrItem[1].template.type == 16) || (charz.arrItem[1].quantity >= 10 && charz.arrItem[1].template.type == 15 && charz.arrItem[0].template.type == 16)) && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0] && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]) {
            int indexUI = charz.getEmptyBagIndex();
            if (indexUI == -1) {
                charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
            } else if (charz.xu < coin_nhap) {

            } else {
                charz.updateXu(-coin_nhap, 2);
                if (charz.arrItem[0].template.type == 15) {
                    charz.addQuantityItemBag(charz.arrItem[0].indexUI, -10);
                    charz.addQuantityItemBag(charz.arrItem[1].indexUI, -1);
                } else {
                    charz.addQuantityItemBag(charz.arrItem[1].indexUI, -10);
                    charz.addQuantityItemBag(charz.arrItem[0].indexUI, -1);
                }
                short templateId = (short) Util.gI().nextInt(220, 224);
                if (charz.getItemBagIndex(templateId) != -1) {
                    charz.addQuantityItemBag(charz.getItemBagIndex(templateId), 1);
                } else {
                    charz.addItemBag(new Item(templateId, false, 1, ItemOption.getOption(templateId, 0, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY), indexUI);
                }
                charz.session.service.setCombineEff(4, ItemTemplate.getIcon((short) templateId), -1, -1);
                charz.session.service.Bag(charz.arrItemBag);
            }
        } else {
            charz.session.service.startOKDlg(mResources.PHEP_NHAP_DA_1);
        }
    }

    public static void NangcCap(Char charz) {
        int upgrade;
        if (contains(charz.arrItemBag, charz.arrItem) && Combine.getItemBody(charz.arrItem) != null && Combine.getItemDa(charz.arrItem) != null && Combine.getItemDa(charz.arrItem).isOption(idOption(Combine.getItemBody(charz.arrItem).template.type)) && (charz.arrItem.length == 2 || (charz.arrItem.length == 3 && (getItem(charz.arrItem, 987) != null || getItem(charz.arrItem, 1143) != null || getItem(charz.arrItem, 205) != null)) || (charz.arrItem.length == 4 && (getItem(charz.arrItem, 987) != null || getItem(charz.arrItem, 1143) != null) && getItem(charz.arrItem, 205) != null))) {
            Item tb = Combine.getItemBody(charz.arrItem);
            Item da = Combine.getItemDa(charz.arrItem);
            Item dbv = getItem(charz.arrItem, 987) == null ? getItem(charz.arrItem, 1143) : getItem(charz.arrItem, 987);
            Item bua100 = getItem(charz.arrItem, 205);
            upgrade = tb.getParamOption(72);
            if (upgrade >= max_Up) {
                charz.session.service.startOKDlg(mResources.UP_FULL);
            } else if (dap_dasl[upgrade] > da.quantity || charz.xu < dap_coin[upgrade]) {

            } else {
                charz.updateXu(-dap_coin[upgrade], 2);
                charz.addQuantityItemBag(da.indexUI, -dap_dasl[upgrade]);
                boolean b1;
                int pP;
                if (upgrade >= 7) {
                    pP = Util.gI().nextInt(1, 200);
                } else {
                    pP = Util.gI().nextInt(1, 100);
                }
                b1 = pP <= dap_percent[upgrade];
                if (bua100 != null && upgrade < 7) {
                    b1 = true;
                    charz.addQuantityItemBag(bua100.indexUI, -1);
                }
                if (dbv != null) {
                    charz.addQuantityItemBag(dbv.indexUI, -1);
                }
                if (b1) {
                    up(tb);
                    charz.session.service.setCombineEff(2, -1, -1, -1);
                } else {
                    if (upgrade > 0 && dbv == null) {
                        int newupgrade = downgrade[upgrade];
                        for (int i = 0; i < tb.options.size(); i++) {
                            if (tb.options.get(i).optionTemplate.id == 72) {
                                tb.options.get(i).param = newupgrade;
                            } else {
                                tb.options.get(i).param = nextParamOption(tb.options.get(i).optionTemplate.id, -(upgrade - newupgrade), tb.options.get(i).param);
                                if (tb.options.get(i).optionTemplate.id == 0 || tb.options.get(i).optionTemplate.id == 6 || tb.options.get(i).optionTemplate.id == 7 || tb.options.get(i).optionTemplate.id == 14 || tb.options.get(i).optionTemplate.id == 27 || tb.options.get(i).optionTemplate.id == 28 || tb.options.get(i).optionTemplate.id == 47) {
                                    if (downparam_percent[upgrade] > 0) {
//                                        int p = (tb.options.get(i).param * downparam_percent[upgrade] / 100);
//                                        if (p < 1) {
//                                            p = 1;
//                                        }
//                                        tb.options.get(i).param -= p;
//                                        if (tb.options.get(i).param < 0) {
//                                            tb.options.get(i).param = 0;
//                                        }
                                    }
                                }
                            }
                        }
                        if (newupgrade != upgrade) {
                            if (tb.isHaveOption(209)) {
                                tb.getOption(209).param++;
                            } else {
                                tb.options.add(new ItemOption(209, 1));
                            }
                        }
                    }
                    charz.session.service.setCombineEff(3, -1, -1, -1);
                }
                charz.session.service.Bag(charz.arrItemBag);
            }
        } else {
            charz.session.service.startOKDlg(mResources.NANG_CAP_1);
        }
    }

    public static void up(Item item) {
        boolean b2 = false;
        //Neu co tang them ko co tao moi
        for (int i = 0; i < item.options.size(); i++) {
            if (item.options.get(i).optionTemplate.id == 72) {
                item.options.get(i).param++;
                b2 = true;
            } else {
                item.options.get(i).param = nextParamOption(item.options.get(i).optionTemplate.id, 1, item.options.get(i).param);
            }
        }
        if (!b2) {
            item.options.add(new ItemOption(72, 1));
        }
    }

    public static void NangcCapBongTai(Char charz) {
        if (charz.myPet != null && charz.myPetz().isHopThe > 0) {
            charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.TACH_HOP_THE, null, 0);
        } else {
            if (charz.arrItem.length == 2 && charz.arrItem[0] != null && charz.arrItem[1] != null && ((charz.arrItem[0].template.id == 454 && charz.arrItem[1].template.id == 933) || (charz.arrItem[1].template.id == 454 && charz.arrItem[0].template.id == 933)) && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0] && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]) {
                Item bt;
                Item md;
                if (charz.arrItem[0].template.id == 454) {
                    bt = charz.arrItem[0];
                    md = charz.arrItem[1];
                } else {
                    bt = charz.arrItem[1];
                    md = charz.arrItem[0];
                }
                int coinUp = 5000000;
                int goldUp = 20;
                int numMD = 9999;
                int numMD2 = 99;
                if (charz.xu >= coinUp && charz.getLuong() >= goldUp && md.getParamOption(31) >= numMD) {
                    charz.updateXu(-coinUp, 1);
                    charz.updateLuong(-goldUp, 2);
                    boolean isSc = Util.gI().nextInt(100) < 50;
                    if (isSc) {
                        bt.template = ItemTemplate.get((short) (bt.itemId = 921));
                        bt.options.clear();
                        bt.options.add(new ItemOption(72, 2));
                        charz.addQuantityItemBag(md.indexUI, -numMD);
                        charz.session.service.setCombineEff(2, -1, -1, -1);
                    } else {
                        charz.addQuantityItemBag(md.indexUI, -numMD2);
                        charz.session.service.setCombineEff(3, -1, -1, -1);
                    }
                    charz.session.service.Bag(charz.arrItemBag);
                }
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_2);
            }
        }
    }
    public static void NangcCapBongTai3(Char charz) {
        if (charz.myPet != null && charz.myPetz().isHopThe > 0) {
            charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.TACH_HOP_THE, null, 0);
        } else {
            if (charz.arrItem.length == 2 && charz.arrItem[0] != null && charz.arrItem[1] != null
                    && ((charz.arrItem[0].template.id == 921 && charz.arrItem[1].template.id == 2027)
                    || (charz.arrItem[1].template.id == 921 && charz.arrItem[0].template.id == 2027))
                    && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0]
                    && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]) {
                Item bt;
                Item md;
                if (charz.arrItem[0].template.id == 921) {
                    bt = charz.arrItem[0];
                    md = charz.arrItem[1];
                } else {
                    bt = charz.arrItem[1];
                    md = charz.arrItem[0];
                }
                int coinUp = 5000000;
                int goldUp = 20;
                int numMD = 9999;
                int numMD2 = 99;
                if (charz.xu >= coinUp && charz.getLuong() >= goldUp && md.getParamOption(31) >= numMD) {
                    charz.updateXu(-coinUp, 1);
                    charz.updateLuong(-goldUp, 2);
                    boolean isSc = Util.gI().nextInt(100) < 50;
                    if (isSc) {
                        bt.template = ItemTemplate.get((short) (bt.itemId = 2026));
                        bt.options.clear();
                        bt.options.add(new ItemOption(72, 3));
                        charz.addQuantityItemBag(md.indexUI, -numMD);
                        charz.session.service.setCombineEff(2, -1, -1, -1);
                    } else {
                        charz.addQuantityItemBag(md.indexUI, -numMD2);
                        charz.session.service.setCombineEff(3, -1, -1, -1);
                    }
                    charz.session.service.Bag(charz.arrItemBag);
                }
            } else {
                charz.session.service.startOKDlg(mResources.NANG_CAP_BTC3_2);
            }
        }
    }

    public static boolean isHaveItem(Item[] arrItem, int id) {
        int i;
        if (arrItem != null) {
            for (i = 0; i < arrItem.length; i++) {
                if (arrItem[i] != null && arrItem[i].template.id == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Item getItem(Item[] arrItem, int id) {
        int i;
        for (i = 0; i < arrItem.length; i++) {
            if (arrItem[i] != null && arrItem[i].template.id == id) {
                return arrItem[i];
            }
        }
        return null;
    }

    public static void openOptionBongTai(Char charz) {
        if (isHaveItem(charz.arrItem, 921) && isHaveItem(charz.arrItem, 934) && isHaveItem(charz.arrItem, 935) && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0] && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1] && charz.arrItemBag[charz.arrItem[2].indexUI] == charz.arrItem[2]) {
            Item bt = getItem(charz.arrItem, 921);
            Item mh = getItem(charz.arrItem, 934);
            Item dxl = getItem(charz.arrItem, 935);

            int percent = 70;
            int numMH = 99;
            int numDXL = 1;
            int goldUp = 250;
            if (charz.getLuong() >= goldUp && mh.quantity >= numMH && dxl.quantity >= numDXL) {
                charz.updateLuong(-goldUp, 2);
                charz.addQuantityItemBag(mh.indexUI, -numMH);
                charz.addQuantityItemBag(dxl.indexUI, -numDXL);
                boolean isSc = Util.gI().nextInt(100) < percent;
                if (isSc) {
                    bt.options.clear();
                    int idOption = new int[]{5, 14, 17, 27, 28, 50, 77, 94, 103, 175}[Util.gI().nextInt(10)];
                    int param = 0;
                    if (idOption == 17 || idOption == 27 || idOption == 28 || idOption == 50 || idOption == 77 || idOption == 103 || idOption == 175) {
                        param = Util.gI().nextInt(5, Util.gI().nextInt(5, 15));
                    }
                    if (idOption == 94) {
                        param = Util.gI().nextInt(5, Util.gI().nextInt(5, 10));
                    }
                    if (idOption == 14) {
                        param = Util.gI().nextInt(2, Util.gI().nextInt(2, 10));
                    }
                    if (idOption == 5) {
                        param = Util.gI().nextInt(1, Util.gI().nextInt(1, 5));
                    }
                    bt.options.add(new ItemOption(idOption, param));
                    bt.options.add(new ItemOption(38, 0));
                    bt.options.add(new ItemOption(72, 2));
                    charz.session.service.setCombineEff(2, -1, -1, -1);
                } else {
                    charz.session.service.setCombineEff(3, -1, -1, -1);
                }
                charz.session.service.Bag(charz.arrItemBag);
            }
        } else {
            charz.session.service.startOKDlg(mResources.NANG_CAP_2);
        }
    }
    public static void openOptionBongTai3(Char charz) {
        if (isHaveItem(charz.arrItem, 2026) && isHaveItem(charz.arrItem, 934) && isHaveItem(charz.arrItem, 935) && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0] && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1] && charz.arrItemBag[charz.arrItem[2].indexUI] == charz.arrItem[2]) {
            Item bt = getItem(charz.arrItem, 2026);
            Item mh = getItem(charz.arrItem, 934);
            Item dxl = getItem(charz.arrItem, 935);

            int percent = 70;
            int numMH = 99;
            int numDXL = 1;
            int goldUp = 250;
            if (charz.getLuong() >= goldUp && mh.quantity >= numMH && dxl.quantity >= numDXL) {
                charz.updateLuong(-goldUp, 2);
                charz.addQuantityItemBag(mh.indexUI, -numMH);
                charz.addQuantityItemBag(dxl.indexUI, -numDXL);
                boolean isSc = Util.gI().nextInt(100) < percent;
                if (isSc) {
                    bt.options.clear();
                    int idOption = new int[]{5, 14, 17, 27, 28, 50, 77, 94, 103, 175}[Util.gI().nextInt(10)];
                    int param = 0;
                    if (idOption == 17 || idOption == 27 || idOption == 28 || idOption == 50 || idOption == 77 || idOption == 103 || idOption == 175) {
                        param = Util.gI().nextInt(5, Util.gI().nextInt(5, 20));
                    }
                    if (idOption == 94) {
                        param = Util.gI().nextInt(5, Util.gI().nextInt(5, 20));
                    }
                    if (idOption == 14) {
                        param = Util.gI().nextInt(2, Util.gI().nextInt(2, 20));
                    }
                    if (idOption == 5) {
                        param = Util.gI().nextInt(1, Util.gI().nextInt(1, 20));
                    }
                    bt.options.add(new ItemOption(idOption, param));
                    bt.options.add(new ItemOption(38, 0));
                    bt.options.add(new ItemOption(72, 3));
                    charz.session.service.setCombineEff(2, -1, -1, -1);
                } else {
                    charz.session.service.setCombineEff(3, -1, -1, -1);
                }
                charz.session.service.Bag(charz.arrItemBag);
            }
        } else {
            charz.session.service.startOKDlg(mResources.NANG_CAP_BTC3_2);
        }
    }

    public static Item getItemBody(Item[] arrItem) {
        int i;
        for (i = 0; i < arrItem.length; i++) {
            if (arrItem[i] != null && arrItem[i].isTypeBody()) {
                return arrItem[i];
            }
        }
        return null;
    }

    public static Item getItemDa(Item[] arrItem) {
        int i;
        for (i = 0; i < arrItem.length; i++) {
            if (arrItem[i] != null && arrItem[i].template.type == 14) {
                return arrItem[i];
            }
        }
        return null;
    }

    public static void NhapNgoc(Char charz) {
        if (charz.arrItem != null && charz.arrItem.length == 1 && charz.arrItem[0] != null && charz.arrItem[0].quantity >= 7 && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0]) {
            if (charz.getEmptyBagCount() > 0 && getNr(charz.arrItem[0].template.id) != null) {
                charz.useItemBag(charz.arrItem[0].indexUI, 7);
                charz.addItemBag(2, new Item(getNr(charz.arrItem[0].template.id)[1], false, 1, ItemOption.getOption(getNr(charz.arrItem[0].template.id)[1], -1, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                charz.session.service.setCombineEff(5, ItemTemplate.getIcon((short) getNr(charz.arrItem[0].template.id)[1]), -1, -1);
                charz.session.service.Bag(charz.arrItemBag);
            }
        }
    }

    public static int[] getNr(int id) {
        int i;
        for (i = 0; i < nhap_nr.length; i++) {
            if (nhap_nr[i][0] == id) {
                return nhap_nr[i];
            }
        }
        return null;
    }

    private static boolean contains(Item[] array1, Item[] array2) {
        try {
            for (int i = 0; i < array2.length; ++i) {
                if (array1[array2[i].indexUI] != array2[i]) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static boolean contains(Item[] o, int... v) {
        for (int j = 0; j < v.length; j++) {
            for (int i = 0; i < o.length; ++i) {
                if (o[i] != null && o[i].template.id == v[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int addPercentRuby(Item item) {
        if (item != null && item.template.id == 1074) {
            return 10;
        }
        if (item != null && item.template.id == 1075) {
            return 20;
        }
        if (item != null && item.template.id == 1076) {
            return 30;
        }
        if (item != null && item.template.id == 1077) {
            return 40;
        }
        if (item != null && item.template.id == 1078) {
            return 50;
        }
        return 0;
    }

    private static int addPercentCry(Item item) {
        if (item != null && item.template.id == 1079) {
            return 10;
        }
        if (item != null && item.template.id == 1080) {
            return 20;
        }
        if (item != null && item.template.id == 1081) {
            return 30;
        }
        if (item != null && item.template.id == 1082) {
            return 40;
        }
        if (item != null && item.template.id == 1083) {
            return 50;
        }
        return 0;
    }

    private static String srcTX(Item gra, Item fm) {
        if (gra.template.id == 1066) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return mResources.CHE_TAO_THIEN_SU_1;
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return mResources.CHE_TAO_THIEN_SU_2;
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return mResources.CHE_TAO_THIEN_SU_3;
            }
        }
        if (gra.template.id == 1067) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return mResources.CHE_TAO_THIEN_SU_4;
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return mResources.CHE_TAO_THIEN_SU_5;
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return mResources.CHE_TAO_THIEN_SU_6;
            }
        }
        if (gra.template.id == 1068) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return mResources.CHE_TAO_THIEN_SU_7;
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return mResources.CHE_TAO_THIEN_SU_8;
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return mResources.CHE_TAO_THIEN_SU_9;
            }
        }
        if (gra.template.id == 1069) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return mResources.CHE_TAO_THIEN_SU_10;
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return mResources.CHE_TAO_THIEN_SU_11;
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return mResources.CHE_TAO_THIEN_SU_12;
            }
        }
        if (gra.template.id == 1070) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return mResources.CHE_TAO_THIEN_SU_13;
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return mResources.CHE_TAO_THIEN_SU_14;
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return mResources.CHE_TAO_THIEN_SU_15;
            }
        }
        return "";
    }

    public static void Make(Char charz) {
        if (charz.getEmptyBagCount() == 0) {
            charz.addInfo1(mResources.BAG_FULL);
        } else if (charz.arrItem != null && Combine.contains(charz.arrItemBag, charz.arrItem)) {
            Item fm = null;
            Item gra = null;
            Item dnm = null;
            Item dmm = null;
            for (int i2 = 0; i2 < charz.arrItem.length; i2 = i2 + 1) {
                if (charz.arrItem[i2] != null) {
                    if (charz.arrItem[i2].isItemFormula() && fm == null) {
                        fm = charz.arrItem[i2];
                    } else if (charz.arrItem[i2].isItemGraft() && gra == null) {
                        gra = charz.arrItem[i2];
                    } else if (charz.arrItem[i2].isItemRuby() && dnm == null) {
                        dnm = charz.arrItem[i2];
                    } else if (charz.arrItem[i2].isItemCrystal() && dmm == null) {
                        dmm = charz.arrItem[i2];
                    } else {
                        charz.session.service.startOKDlg(mResources.SAI_NGUYEN_LIEU);
                        return;
                    }
                }
            }
            if (fm != null && gra != null) {
                int totalPercent = 25;
                if (fm.isItemFormulaVIP()) {
                    totalPercent = 35;
                }
                int addPercent = Combine.addPercentRuby(dnm);
                totalPercent = totalPercent + addPercent;
                boolean isGold = charz.xu >= 200000000L;
                boolean isGra = gra.quantity >= 999;
                if (isGold && isGra) {
                    boolean isSuccess = totalPercent > Util.gI().nextInt(100);
                    boolean isOption = Combine.addPercentCry(dmm) > Util.gI().nextInt(100);
                    charz.useItemBag(fm.indexUI, 1);
                    if (isSuccess) {
                        charz.useItemBag(gra.indexUI, 999);
                    } else {
                        charz.useItemBag(gra.indexUI, 99);
                    }
                    if (dnm != null) {
                        charz.useItemBag(dnm.indexUI, 1);
                    }
                    if (dmm != null) {
                        charz.useItemBag(dmm.indexUI, 1);
                    }
                    charz.updateXu(-200000000L, 2);
                    if (isSuccess) {
                        Item item = Combine.itemMake(gra, fm);
                        int percent = Util.gI().nextInt(20, Util.gI().nextInt(21, Util.gI().nextInt(23, Util.gI().nextInt(25, 35))));
                        //Add param option
                        for (int i = 0; i < item.options.size(); i++) {
                            ItemOption option = item.options.get(i);
                            if (option.optionTemplate.id != 30 && option.optionTemplate.id != 21) {
                                option.param = option.param + (option.param * percent / 100);
                            }
                        }
                        //Add option
                        if (isOption) {
                            int num = Util.gI().nextInt(1, Util.gI().nextInt(1, 3));
                            item.options.add(new ItemOption(41, num));
                            charz.addOptionHide(item, num);
                        }
                        charz.session.service.setCombineEff(7, item.template.iconID, -1, -1);
                        charz.addItemBag(1, item);
                        if (isOption) {
                            Server.gI().chatVip(String.format(mResources.CHAT_VIP, "Wish", String.format(mResources.WISH_CHAT_2, charz.cName, item.template.name, item.getParamOption(41))));
                        } else {
                            Server.gI().chatVip(String.format(mResources.CHAT_VIP, "Wish", String.format(mResources.WISH_CHAT, charz.cName, item.template.name)));
                        }
                    } else {
                        charz.session.service.setCombineEff(8, -1, -1, -1);
                    }
                } else {
                    charz.resetMenu();
                    charz.menuBoard.chat = String.format(mResources.SAY_WISH_3, Combine.srcTX(gra, fm), isGra ? 2 : 7, gra.quantity, dnm != null ? dnm.template.name : mResources.KHONG_DUNG_DA_NANG_CAP, addPercent, dmm != null ? dmm.template.name : mResources.KHONG_DUNG_DA_MAY_MAN, Combine.addPercentCry(dmm), totalPercent, isGold ? 2 : 7);
                    charz.menuBoard.arrMenu.add(new MenuInfo(mResources.REFUSE, 171));
                    charz.menuBoard.openUIConfirm(charz.menuBoard.npcId, null, null, -1);
                }
            } else {
                charz.session.service.startOKDlg(mResources.KHONG_DU_NGUYEN_LIEU);
            }
        }
    }

    private static Item itemMake(Item gra, Item fm) {
        if (gra.template.id == 1066) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return new Item(1048, false, 1, ItemOption.getOption(1048, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return new Item(1049, false, 1, ItemOption.getOption(1049, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return new Item(1050, false, 1, ItemOption.getOption(1050, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
        }
        if (gra.template.id == 1067) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return new Item(1051, false, 1, ItemOption.getOption(1051, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return new Item(1052, false, 1, ItemOption.getOption(1052, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return new Item(1053, false, 1, ItemOption.getOption(1053, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
        }
        if (gra.template.id == 1068) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return new Item(1057, false, 1, ItemOption.getOption(1057, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return new Item(1058, false, 1, ItemOption.getOption(1058, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return new Item(1059, false, 1, ItemOption.getOption(1059, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
        }
        if (gra.template.id == 1069) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return new Item(1060, false, 1, ItemOption.getOption(1060, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return new Item(1061, false, 1, ItemOption.getOption(1061, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return new Item(1062, false, 1, ItemOption.getOption(1062, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
        }
        if (gra.template.id == 1070) {
            if (fm.template.id == 1071 || fm.template.id == 1084) {
                return new Item(1054, false, 1, ItemOption.getOption(1054, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1072 || fm.template.id == 1085) {
                return new Item(1055, false, 1, ItemOption.getOption(1055, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
            if (fm.template.id == 1073 || fm.template.id == 1086) {
                return new Item(1056, false, 1, ItemOption.getOption(1056, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            }
        }
        return null;
    }

    public static void NangcapChanMenh(Char charz) {
        if (charz.arrItem.length == 2 && charz.arrItem[0] != null && charz.arrItem[1] != null
                && charz.arrItemBag[charz.arrItem[0].indexUI] == charz.arrItem[0]
                && charz.arrItemBag[charz.arrItem[1].indexUI] == charz.arrItem[1]) {

            Item Danguctu = null;
            Item chan_menh = null;
            for (Item item : charz.arrItem) {
                if (item.template.id == 1988) {
                    Danguctu = item;
                } else if (item.template.id >= 2013 && item.template.id <= 2021) {
                    chan_menh = item;
                }
            }

            if (Danguctu == null || chan_menh == null) {
                charz.session.service.startOKDlg("Hãy đặt đủ x50 đá ngục tù  và 1 chân mệnh");
                return;
            }
            int DanguctuQuanty = charz.getItemQuantity(1988);
            if (DanguctuQuanty < 50) {
                charz.session.service.startOKDlg("Không đủ x50 đá ngục tù");
                return;
            }
            charz.addQuantityItemBag(Danguctu.indexUI, -50);

            int indexUI = charz.getEmptyBagIndex();
            int chanmenh = chan_menh.template.id + 1;
            int cap = chan_menh.template.id - 2013 + 1;
            java.util.Map<Integer, Integer> successRate = new HashMap<>();
            successRate.put(5, 50);
            successRate.put(6, 30);
            successRate.put(7, 20);
            successRate.put(8, 10);
            successRate.put(9, 5);
            if (cap >= 5 && successRate.containsKey(cap)) {
                if (!Util.isTrue(successRate.get(cap), 100)) {
                    charz.session.service.setCombineEff(3, -1, -1, -1); // thất bại
                    return;
                }
            }
            charz.addQuantityItemBag(chan_menh.indexUI, -1);
            Item chanMenhMoi = new Item(chanmenh, false, 1,
                    ItemOption.getOption(chanmenh, -1, -1),
                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
            int capChanMenh = chanMenhMoi.template.id - 2013 + 1;

            Random random = new Random();
            List<Integer> advancedOptions = List.of(227, 228, 229);
            for (ItemOption option : chan_menh.options) {
                chanMenhMoi.options.add(new ItemOption(option.optionTemplate.id, option.param));
            }

            if (capChanMenh == 1) {
                int randomOption = advancedOptions.get(random.nextInt(advancedOptions.size()));
                chanMenhMoi.options.add(new ItemOption(randomOption, random.nextInt(7) + 1));

            } else if (capChanMenh == 2) { // Cấp 3 (ID 1281)
                ItemOption previousOption = chanMenhMoi.options.stream()
                        .filter(option -> advancedOptions.contains(option.optionTemplate.id))
                        .findFirst().orElse(null);
                if (previousOption != null) {
                    previousOption.param += 2; // Tăng thêm 2%
                }
            } else if (capChanMenh == 3) { // Cấp 4 (ID 1282)
                List<Integer> remainingOptions = advancedOptions.stream()
                        .filter(opt -> chanMenhMoi.options.stream().noneMatch(o -> o.optionTemplate.id == opt))
                        .collect(Collectors.toList());
                if (!remainingOptions.isEmpty()) {
                    int newOption = remainingOptions.get(random.nextInt(remainingOptions.size()));
                    chanMenhMoi.options.add(new ItemOption(newOption, random.nextInt(7) + 1)); // Chỉ số từ 1 đến 7%
                }
            } else if (capChanMenh == 4) { // Cấp 5 (ID 1283)
                List<Integer> remainingOptions = advancedOptions.stream()
                        .filter(opt -> chanMenhMoi.options.stream().noneMatch(o -> o.optionTemplate.id == opt))
                        .collect(Collectors.toList());
                if (!remainingOptions.isEmpty()) {
                    int newOption = remainingOptions.get(0);
                    chanMenhMoi.options.add(new ItemOption(newOption, random.nextInt(7) + 1)); // Chỉ số từ 1 đến 7%
                }
            } else if (capChanMenh >= 5) { // Cấp 6 trở lên (ID 1284+)
                ItemOption previousOption = chanMenhMoi.options.stream()
                        .filter(option -> advancedOptions.contains(option.optionTemplate.id))
                        .findFirst().orElse(null);
                if (previousOption != null) {
                    previousOption.param += 2; // Tăng thêm 2%
                }
            }
            charz.addItemBag(chanMenhMoi, indexUI);
            // Cập nhật thành công
            charz.session.service.setCombineEff(2, -1, -1, -1); // Hiệu ứng thành công
            charz.session.service.Bag(charz.arrItemBag);
            int[] arr = new int[charz.arrItem.length];
            for (int i = 0; i < charz.arrItem.length; i++) {
                arr[i] = charz.arrItem[i].indexUI;
            }
            charz.session.service.setCombineEff(arr, -1);

        } else {
            charz.session.service.startOKDlg("Hãy đặt đúng nguyên liệu vào ô nâng cấp.");
        }
    }

}
