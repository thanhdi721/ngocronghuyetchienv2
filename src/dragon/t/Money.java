package dragon.t;

import dragon.object.Char;
import dragon.object.Item;
import dragon.object.ItemOption;
import dragon.server.Dragon;
import dragon.server.MySQL;
import dragon.server.mResources;
import dragon.u.Util;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author TGDD
 */
public class Money {
    
    private static Money instance;
    
    public static int ooo = 0;
    
    public static Money gI() {
        if (instance == null) {
            instance = new Money();
        }
        return instance;
    }
    
    public boolean isNapNgoc = true;
    public boolean isNapVang = true;
    
    public int xNgoc = 1;
    public int xVang = 1;
    
    public int[][] arrMoneyNgoc = new int[][] {{10000, 2000}, {50000, 10000}, {100000, 20000}, {200000, 40000}, {500000, 100000}, {1000000, 200000}, {5000000, 1000000}};
    public int[][] arrMoneyGold = new int[][] {{9999, 10}, {20000, 40}, {30000, 60}, {50000, 100}, {100000, 200}, {200000, 400}, {500000, 1100}, {1000000, 2300}};
    
    public long getMoney(Char charz) {
        try {
            MySQL mySQL = MySQL.createData2();
            try {
                ResultSet red = mySQL.getConnection().prepareStatement(String.format(mResources.QUERY_SELECT_USER_MONEY, charz.session.userId), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();
                red.first();
                return red.getLong(1);
            } finally {
                mySQL.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void changeMoney(Char charz, int type, int select) {
        switch (type) {
            case 0:
            {
                if (select >= 0 && select < arrMoneyNgoc.length) {
                    int max = arrMoneyNgoc[select][0];
                    int min = arrMoneyNgoc[select][1];
                    try {
                        MySQL mySQL = MySQL.createData2();
                        try {
                            ResultSet red = mySQL.getConnection().prepareStatement(String.format(mResources.QUERY_SELECT_USER_MONEY, charz.session.userId), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();
                            red.first();
                            if (max > red.getLong(1)) {
                                charz.session.service.startOKDlg(String.format(mResources.MONEY_NOT, Util.gI().getFormatNumber(max - red.getLong(1))));
                            } else {
                                mySQL.getConnection().setAutoCommit(false);
                                try {
                                    mySQL.getConnection().prepareStatement(String.format(mResources.UPDATE_USER_MONEY, -max, charz.session.userId)).executeUpdate();
                                    if (dragon.server.Dragon.isEvent_NHS) {
                                        if (max >= 10000) {
                                            Item it3e4 = null;
                                            int ctId = -1;
                                            if (charz.cgender == 0) {
                                                ctId = 1010;
                                            }
                                            if (charz.cgender == 1) {
                                                ctId = 1012;
                                            }
                                            if (charz.cgender == 2) {
                                                ctId = 1011;
                                            }
                                            if (ctId != -1) {
                                                it3e4 = new Item(ctId, false, 1, ItemOption.getOption(ctId, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                            }
                                            if (it3e4 != null) {
                                                if (it3e4.isHaveOption(93)) {
                                                    it3e4.setExpires(System.currentTimeMillis() + (long)(1000l * 60l * 60l * 24l * (long)(it3e4.getParamOption(93) + 1)));
                                                }
                                                charz.addItemBag(0, it3e4);
                                            }
                                        }
                                    }
                                    //Su kien halloween
                                    if (dragon.server.Dragon.isEvent_Halloween) {
                                        if (max >= 100000) {
                                            if (charz.cgender == 0) {
                                                charz.addItemMore(0, new Item(644, false, 1, ItemOption.getOption(644, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                            }
                                            if (charz.cgender == 1) {
                                               charz.addItemMore(0, new Item(645, false, 1, ItemOption.getOption(645, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                            }
                                            if (charz.cgender == 2) {
                                                charz.addItemMore(0, new Item(646, false, 1, ItemOption.getOption(646, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                            }
                                        }
                                    }
                                    //Bua nang cap
                                    if (max >= 1000000 && (int)charz.valueById(11) != 5) {
                                        ooo++;
                                        charz.setValue(11, 5);
                                        charz.addItemMore2(0, new Item(205, false, 1, ItemOption.getOption(205, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                    }
                                    //Cai trang
//                                    if (max >= 50000) {
//                                       if (Util.gI().nextInt(100) < 50) {
//                                            charz.addItemMore2(0, new Item(732, false, 1, ItemOption.getOption(732, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
//                                        } else if (Util.gI().nextInt(100) < 30) {
//                                            charz.addItemMore2(0, new Item(731, false, 1, ItemOption.getOption(731, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY)); 
//                                        } else if (Util.gI().nextInt(100) < 10) {
//                                            charz.addItemMore2(0, new Item(730, false, 1, ItemOption.getOption(730, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY)); 
//                                        } else {
//                                            charz.addItemMore2(0, new Item(732, false, 1, ItemOption.getOption(732, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY)); 
//                                        }
//                                    }
                                    charz.updateLuong((min * this.xNgoc), 2);
                                    mySQL.getConnection().commit();
                                    charz.session.isSave = true;
                                    if (max >= 10000) {
                                        int tichluy = max / 1000;
                                        charz.setValue(10, (int)charz.valueById(10) + tichluy);
                                    }
                                    if (Dragon.isEvent_TetNguyenDan && max >= 1000) {
                                        int diem = max / 1000;
                                        charz.myObj().pointEvent += diem;
                                    }
                                    //qua dua hau
                                    if (Dragon.isEvent_HungVuong && max >= 10000) {
                                        int soluong = max / 1000;
                                        charz.addItemBag(0, new Item(569, false, soluong, ItemOption.getOption(569, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                    }
                                    //Su kien he 2025
                                    if (Dragon.isEvent_HE2023 && max >= 1000) {
                                        int diem = max / 1000;
                                        charz.myObj().pointEvent += diem;
                                    }
                                    //Su kien vip
                                    if (Dragon.isEvent_VIP && max >= 10000) {
                                        int diem = max / 1000;
                                        charz.myObj().pointEventVIP += diem;
                                    }
                                } catch (SQLException e) {
                                    mySQL.getConnection().rollback();
                                    e.printStackTrace();
                                }
                            }
                        } finally {
                            mySQL.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case 1:
            {
                if (select >= 0 && select < arrMoneyGold.length) {
                    int max2 = arrMoneyGold[select][0];
                    int min2 = arrMoneyGold[select][1];
                    try {
                        MySQL mySQL2 = MySQL.createData2();
                        try {
                            ResultSet red2 = mySQL2.getConnection().prepareStatement(String.format(mResources.QUERY_SELECT_USER_MONEY, charz.session.userId), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();
                            red2.first();
                            if (max2 > red2.getLong(1)) {
                                charz.session.service.startOKDlg(String.format(mResources.MONEY_NOT, Util.gI().getFormatNumber(max2 - red2.getLong(1))));
                            } else {
                                mySQL2.getConnection().setAutoCommit(false);
                                try {
                                    mySQL2.getConnection().prepareStatement(String.format(mResources.UPDATE_USER_MONEY, -max2, charz.session.userId)).executeUpdate();
                                    //Su kien halloween
                                    if (dragon.server.Dragon.isEvent_Halloween) {
                                        if (max2 >= 100000) {
                                            if (charz.cgender == 0) {
                                                charz.addItemMore(0, new Item(644, false, 1, ItemOption.getOption(644, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                            }
                                            if (charz.cgender == 1) {
                                                charz.addItemMore(0, new Item(645, false, 1, ItemOption.getOption(645, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                            }
                                            if (charz.cgender == 2) {
                                                charz.addItemMore(0, new Item(646, false, 1, ItemOption.getOption(646, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                            }
                                        }
                                    }
//                                  Bua nang cap
                                    if (max2 >= 1000000 && (int)charz.valueById(11) != 5) {
                                        ooo++;
                                        charz.setValue(11, (int)5);
                                        charz.addItemMore2 (0, new Item(205, false, 1, ItemOption.getOption(205, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                    }
                                    //Cai trang
//                                    if (max2 >= 50000) {
//                                        if (Util.gI().nextInt(100) < 50) {
//                                            charz.addItemMore2 (0, new Item(732, false, 1, ItemOption.getOption(732, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
//                                        } else if (Util.gI().nextInt(100) < 30) {
//                                            charz.addItemMore2 (0, new Item(731, false, 1, ItemOption.getOption(731, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY)); 
//                                        } else if (Util.gI().nextInt(100) < 10) {
//                                            charz.addItemMore2 (0, new Item(730, false, 1, ItemOption.getOption(730, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY)); 
//                                        } else {
//                                            charz.addItemMore2 (0, new Item(732, false, 1, ItemOption.getOption(732, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY)); 
//                                        }
//                                    }     
                                    charz.totalGold = charz.totalGold + (min2 * this.xVang);
                                    charz.session.service.chatTHEGIOI(mResources.EMPTY, String.format(mResources.TOTAL_GOLD, Util.gI().getFormatNumber(charz.totalGold)), null, 0);
                                    mySQL2.getConnection().commit();
                                    charz.session.isSave = true;
                                    if (max2 >= 10000) {
                                        int tichluy2 = max2 / 1000;
                                        charz.setValue(10, (int)charz.valueById(10) + tichluy2);
                                    }
                                    if (Dragon.isEvent_TetNguyenDan && max2 >= 1000) {
                                        int diem2 = max2 / 1000;
                                        charz.myObj().pointEvent += diem2;
                                    }
                                    //qua dua hau
                                    if (Dragon.isEvent_HungVuong && max2 >= 10000) {
                                        int soluong = max2 / 1000;
                                        charz.addItemBag(0, new Item(569, false, soluong, ItemOption.getOption(569, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                    }
                                    //Su kien he 2025
                                    if (Dragon.isEvent_HE2023 && max2 >= 1000) {
                                        int diem2 = max2 / 1000;
                                        charz.myObj().pointEvent += diem2;
                                    }
                                    //Su kien vip
                                    if (Dragon.isEvent_VIP && max2 >= 10000) {
                                        int diem = max2 / 1000;
                                        charz.myObj().pointEventVIP += diem;
                                    }
                                } catch (SQLException e) {
                                    mySQL2.getConnection().rollback();
                                    e.printStackTrace();
                                }
                            }
                        } finally {
                            mySQL2.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }
    
    public void updateMoeny(Char charz, long x) {
        try {
            MySQL mySQL = MySQL.createData2();
            try {
                mySQL.getConnection().setAutoCommit(false);
                try {
                    mySQL.getConnection().prepareStatement(String.format(mResources.UPDATE_USER_MONEY, x, charz.session.userId)).executeUpdate();
                    mySQL.getConnection().commit();
                } catch (SQLException e){
                    mySQL.getConnection().rollback();
                    e.printStackTrace();
                }
            } finally {
                mySQL.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getTongNap(Char charz) {
        try {
            MySQL mySQL = MySQL.createData2();
            try {
                ResultSet red = mySQL.getConnection().prepareStatement(String.format(mResources.QUERY_SELECT_USER_TONG_NAP, charz.session.userId), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();
                red.first();
                return red.getLong(1);
            } finally {
                mySQL.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
}
