package dragon.object;

import dragon.t.CaiTrang;
import dragon.server.mResources;
import dragon.t.Rank;
import dragon.u.Util;
import dragon.u.mLog;

/**
 *
 * @author TGDD
 */
public class LuckyRound {

    private static LuckyRound instance;

    public short[] BallInfo_idImage = new short[]{419, 420, 421, 422, 423, 424, 425};

    public short[] rand_Item1 = new short[]{190, 828, 829, 830, 831, 832, 833, 834, 835, 836, 837, 838, 220, 221, 222, 223, 224, 441, 442, 443, 444, 445, 446, 447, 20, 19, 938, 939, 860, 956};
    public byte[] rand_percent1 = new byte[]{50, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 20, 20, 20, 20, 20, 15, 15, 15, 15, 15, 15, 15, 5, 5, 2, 2, 1, 1};
    public int[] rand_coin_K1 = new int[]{100, 120, 150, 200, 240, 290, 350, 390, 420, 460, 500, 1000};

    public short[] rand_Item2 = new short[]{220, 221, 222, 224, 342, 343, 344, 345, 18, 19, 20, 934, 190, 956, 968, 860};
    public byte[] rand_percent2 = new byte[]{20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30, 1, 1, 1};
    public int[] rand_coin_K2 = new int[]{100, 120, 150, 200, 240, 290, 300, 390, 420, 460, 500, 1000};

    public int[] price = new int[]{
        25000000,
        10
    };

    public static LuckyRound gI() {
        if (instance == null) {
            instance = new LuckyRound();
        }
        return instance;
    }

    public void crackBall(Char charz, int type, int soluong) {
        Util.gI().log("type =" + type + " num =" + soluong);
        int i;
        int j;
        if (type == charz.typeLucky) {
            charz.session.service.sendLuckyRound(charz.typeLucky, price[charz.typeLucky], 821);
        }
        int numVe = charz.getItemBagQuantityById(821);
        int soluong2 = soluong;
        int soluong3 = 0;
        if (numVe > 0) {
            if (soluong > numVe) {
                soluong2 = soluong - numVe;
                soluong3 = numVe;
            } else {
                soluong2 = 0;
                soluong3 = soluong;
            }
        }
        if (type == 2) {
            if (soluong > 0 && soluong < 8) {
                int prices = soluong2 * price[charz.typeLucky];
                if (charz.xu < prices) {
                    charz.session.service.chatTHEGIOI(mResources.EMPTY, "Bạn không đủ vàng", null, (byte) 0);
                    return;
                }
                if (charz.arrItemMore.size() > 100) {
                    charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.HT_PHU_FULL, null, (byte) 0);
                } else {
                    if (charz.typeLucky == 0) {
                        if (prices > charz.xu) {

                        } else {
                            short[] idImage = new short[soluong];
                            for (i = 0; i < idImage.length; ++i) {
                                Item itnew = null;
                                //do quay vàng
                                if (Util.gI().nextInt(100) < 5) {
                                    if (Util.gI().nextInt(100) < 1) {
                                        int id2 = new int[]{1208, 1209, 1210}[Util.gI().nextInt(3)];
                                        itnew = new Item(id2, false, 1, ItemOption.getOption(id2, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    } else {
                                        itnew = new Item(20, false, 1, ItemOption.getOption(20, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    }
                                } else if (Util.gI().nextInt(100) < 1) {
                                    if (Util.gI().nextInt(200) < 1) {
                                        int id3 = new int[]{1234, 1235, 1236}[Util.gI().nextInt(3)];
                                        itnew = new Item(id3, false, 1, ItemOption.getOption(id3, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    } else {
                                        int id3 = new int[]{1234, 1235, 1236}[Util.gI().nextInt(3)];
                                        itnew = new Item(id3, false, 1, ItemOption.getOption(id3, Util.gI().nextInt(1, 3), 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    }
                                } else if (Util.gI().nextInt(100) < 1) { // vip
                                    itnew = new Item(1501, false, 1, ItemOption.getOption(1501, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    itnew.options.add(new ItemOption(73, 0));
                                } else if (Util.gI().nextInt(100) < 1) { //thường
                                    itnew = new Item(1496, false, 1, ItemOption.getOption(1496, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    itnew.options.add(new ItemOption(73, 0));
                                } else {
                                    if (itnew == null) {
                                        for (j = 0; j < rand_Item2.length; j++) {
                                            if (Util.gI().nextInt(100) < rand_percent2[j]) {
                                                itnew = new Item(rand_Item2[j], false, 1, ItemOption.getOption(rand_Item2[j], 0, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                                break;
                                            }
                                        }
                                    }
                                    if (itnew == null) {
                                        itnew = new Item(rand_Item2[0], false, 1, ItemOption.getOption(rand_Item2[0], 0, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    }
                                }

                                if (itnew.template.type == 9) {
                                    itnew.options.clear();
                                    itnew.options.add(new ItemOption(171, rand_coin_K2[Util.gI().nextInt(rand_coin_K2.length)]));
                                }
                                CaiTrang.gI().setPartTemp(itnew);
                                if (itnew.isHaveOption(93)) {
                                    itnew.setExpires(System.currentTimeMillis() + (long) (1000l * 60l * 60l * 24l * (long) (itnew.getParamOption(93) + 1)));
                                }
                                if (itnew.template.type != 9) {
                                    charz.addItemMore(1, itnew);
                                } else {
                                    charz.addItemBag(0, itnew);
                                }
                                idImage[i] = itnew.template.iconID;
                                charz.updateTask(14, 1);
                            }
                            charz.session.service.sendLuckyRound(idImage);
                            if (prices > 0) {
                                charz.updateXu(-prices, 2);
                            }
                            if (soluong3 > 0) {
                                charz.useItemBagById(821, soluong3);
                            }
                            Rank.RANKS.get(6).addTop(charz.cName, charz.head, charz.headICON, charz.body, charz.leg, charz.charID, (long) ((int) charz.setValue(4, (int) ((int) charz.valueById(4) + 1))), -1);
                        }
                    } else {
                        if (charz.getLuong() < prices) {
                            charz.session.service.chatTHEGIOI(mResources.EMPTY, "Bạn không đủ ngọc", null, (byte) 0);
                            return;
                        }
                        if (charz.cPower < 150000000L) {
                        } else if (prices > charz.getLuong()) {
                        } else {
                            short[] idImage = new short[soluong];
                            for (i = 0; i < idImage.length; ++i) {
                                Item itnew = null;
                                //do goi rineg
                                if (Util.gI().nextInt(150) < 1) {
                                    if (Util.gI().nextInt(200) < 1) {
                                        itnew = new Item(968, false, 1, ItemOption.getOption(968, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    } else {
                                        itnew = new Item(20, false, 1, ItemOption.getOption(20, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    }
                                } else if (Util.gI().nextInt(100) < 1) {
                                    if (Util.gI().nextInt(100) < 1) {
                                        int id4 = new int[]{1208, 1209, 1210}[Util.gI().nextInt(3)];
                                        itnew = new Item(id4, false, 1, ItemOption.getOption(id4, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    } else {
                                        itnew = new Item(20, false, 1, ItemOption.getOption(20, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    }
                                } else if (Util.gI().nextInt(100) < 1) { // vip
                                    itnew = new Item(1501, false, 1, ItemOption.getOption(1501, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    itnew.options.add(new ItemOption(73, 0));
                                } else if (Util.gI().nextInt(100) < 1) { //thường
                                    itnew = new Item(1496, false, 1, ItemOption.getOption(1496, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                    itnew.options.add(new ItemOption(73, 0));
                                } else {
                                    if (itnew == null) {
                                        if (dragon.server.Dragon.isEvent_NgayHe) {
                                            if (Util.gI().nextInt(100) < 2) {
                                                if (Util.gI().nextInt(200) < 1) {
                                                    itnew = new Item(998, false, 1, ItemOption.getOption(998, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                                    mLog.log("Quay ra vv " + itnew.template.name);
                                                } else {
                                                    itnew = new Item(998, false, 1, ItemOption.getOption(998, Util.gI().nextInt(3, 7), 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                                }
                                            } else if (Util.gI().nextInt(100) < 2) {
                                                if (Util.gI().nextInt(200) < 1) {
                                                    itnew = new Item(1007, false, 1, ItemOption.getOption(1007, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                                    mLog.log("Quay ra vv " + itnew.template.name);
                                                } else {
                                                    itnew = new Item(1007, false, 1, ItemOption.getOption(1007, Util.gI().nextInt(3, 7), 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                                }
                                            }
                                        }
                                    }
                                }
                                if (itnew == null) {
                                    for (j = 0; j < rand_Item1.length; j++) {
                                        if (Util.gI().nextInt(100) < rand_percent1[j]) {
                                            itnew = new Item(rand_Item1[j], false, 1, ItemOption.getOption(rand_Item1[j], 0, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                            break;
                                        }
                                    }
                                }
                                if (itnew == null) {
                                    if (dragon.server.Dragon.isEvent_NHS) {
                                        if (Util.gI().nextInt(100) < 10) {
                                            itnew = new Item(542, false, 1, ItemOption.getOption(542, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                        }
                                    }
                                }
                                if (itnew == null) {
                                    itnew = new Item(rand_Item1[0], false, 1, ItemOption.getOption(rand_Item1[0], 0, -1), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                                }

                                if (itnew.template.type == 9) {
                                    itnew.options.clear();
                                    itnew.options.add(new ItemOption(171, rand_coin_K1[Util.gI().nextInt(rand_coin_K1.length)]));
                                }
                                CaiTrang.gI().setPartTemp(itnew);
                                if (itnew.isHaveOption(93)) {
                                    itnew.setExpires(System.currentTimeMillis() + (long) (1000l * 60l * 60l * 24l * (long) (itnew.getParamOption(93) + 1)));
                                }
                                if (itnew.template.type != 9) {
                                    charz.addItemMore(1, itnew);
                                } else {
                                    charz.addItemBag(0, itnew);
                                }
                                idImage[i] = itnew.template.iconID;
                                charz.updateTask(14, 1);
                            }
                            charz.session.service.sendLuckyRound(idImage);
                            if (prices > 0) {
                                charz.updateLuong(-prices, 2);
                            }
                            if (soluong3 > 0) {
                                charz.useItemBagById(821, soluong3);
                            }
                            Rank.RANKS.get(6).addTop(charz.cName, charz.head, charz.headICON, charz.body, charz.leg, charz.charID, (long) ((int) charz.setValue(4, (int) charz.valueById(4) + 1)), -1);
                        }
                    }
                }
            }
        }
    }

}
