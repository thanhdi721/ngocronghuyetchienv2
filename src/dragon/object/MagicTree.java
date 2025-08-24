package dragon.object;

import dragon.server.mResources;

/**
 *
 * @author Admin
 */
public class MagicTree {
    
    public static String infomagicTree = "Đậu thần cấp %d";
    public static int[] maxPeas = new int[] {5, 7, 9, 11, 13, 15, 17, 19, 21, 23};
    public static int[] ketnhanh = new int[] {2, 5, 8, 11, 14, 17, 20, 23, 26, 29};
    public static int[] up_coin = new int[] {5000, 10000, 100000, 1000000, 10000000, 20000000, 50000000, 100000000, 300000000, 500000000};
    public static long[] up_time = new long[] {
        600000,
        6000000,
        58920000,
        597600000,
        1202400000,
        2592000000l,
        4752000000l,
        5961600000l,
        8640000000l,
        8640000000l
    };
    public static int[] up_nhanh = new int[] {10, 500, 1000, 3000, 5000, 7000, 8000, 9000, 10000, 20000};
    public static int bagMaxPeas = 99;
    public static int boxMaxPeas = 20;
    public static int maxLevel = 10;
    
    public static int[][] magicTreeId = new int[][] {
        {
            84, 85, 86, 87, 88, 89, 90, 90, 90, 90
        },
        {
            371, 372, 373, 374, 375, 376, 377, 377, 377, 377
        },
        {
            378, 379, 380, 381, 382, 383, 384, 384, 384, 384
        }
    };
    
    public static int[] timePeas = new int[] {
        60000,
        120000,
        180000,
        240000,
        300000,
        360000,
        420000,
        420000,
        420000,
        420000
    };
    
    public static short[] templateId = new short[] {13, 60, 61, 62, 63, 64, 65, 352, 523, 595};
    public static short[] pack30templateId = new short[] {293, 293, 294, 295, 296, 297, 298, 299, 596, 597};
    public static short[] foods30templateId = new short[] {13, 13, 60, 61, 62, 63, 64, 65, 352, 523};
    
    public static void setPeas(Char charz) {
        int count = (int) ((System.currentTimeMillis() - charz.magicTree_miliseconds + timePeas[charz.magicTree_level - 1])) / (timePeas[charz.magicTree_level - 1]);
        if (count > 0) {
            charz.magicTree_currPeas += count;
            if (charz.magicTree_currPeas > maxPeas[charz.magicTree_level - 1]) {
                charz.magicTree_currPeas = maxPeas[charz.magicTree_level - 1];
            }
        }
    }
    
    public static void Harvest_beans(Char charz) {
        if (charz.isgiaodich) {
            charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.O_THE_THUC_HIEN, null, (byte)0);
        } else if (charz.isSecurity) {
            charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAOVE, null, 0);
        } else if (charz.magicTree_currPeas <= 0) {
        } else if (charz.getItemBagQuantity(6) >= bagMaxPeas) {
            charz.session.service.chatTHEGIOI(mResources.EMPTY, String.format(mResources.PEAS_FULL, charz.getItemBoxQuantity(6)), null, (byte)0);
        } else if (charz.getItemBagIndex(MagicTree.templateId[charz.magicTree_level - 1]) == -1 && charz.getEmptyBagIndex() == -1) {
            charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_BOX_FULL, null, (byte)0);
        } else {
            int indexUI = charz.getItemBagIndex(MagicTree.templateId[charz.magicTree_level - 1]);
            if (indexUI == -1) {
                indexUI = charz.getEmptyBagIndex();
            }
            if (indexUI != -1) {
                int num = charz.magicTree_currPeas;
                if (charz.magicTree_currPeas + charz.getItemBagQuantity(6) > bagMaxPeas) {
                    num = bagMaxPeas - charz.getItemBagQuantity(6);
                }
                if (charz.arrItemBag[indexUI] == null) {
                    charz.addItemBag(new Item(MagicTree.templateId[charz.magicTree_level - 1], true, num, ItemOption.getOption(MagicTree.templateId[charz.magicTree_level - 1], 0, -1), "", "", ""), indexUI);
                } else {
                    charz.addQuantityItemBag(indexUI, num);
                }
                charz.session.service.chatTHEGIOI(mResources.EMPTY, String.format(mResources.BEAN_HARVEST_2, num, charz.magicTree_level), null, (byte)0);
                charz.session.service.Bag(charz.arrItemBag);
                charz.magicTree_currPeas -= num;
                charz.magicTree_miliseconds = System.currentTimeMillis() + timePeas[charz.magicTree_level - 1];
                charz.session.service.magicTree2(charz.magicTree_currPeas, (int) ((charz.magicTree_miliseconds / 1000) - (System.currentTimeMillis() / 1000)));
                if (charz.ctaskId == 0 && charz.ctaskIndex == 4) {
                    charz.updateTask(1);
                }
            }
        }
    }
    
    public static void upMagicTree(Char charz, int type) {
        if (type == 0) {
            if (charz.isgiaodich) {
                charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.O_THE_THUC_HIEN, null, (byte)0);
            } else if (charz.isSecurity) {
                charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAOVE, null, 0);
            } else if (up_coin[charz.magicTree_level - 1] > charz.xu) {
                charz.session.service.chatTHEGIOI(mResources.EMPTY, String.format(mResources.UP_MAGIC_TREE_NOT_COIN, up_coin[charz.magicTree_level - 1] - charz.xu), null, (byte)0);
            } else if (!charz.magicTree_isUpdate) {
                charz.magicTree_miliseconds = System.currentTimeMillis() + up_time[charz.magicTree_level -1];
                charz.magicTree_isUpdate = true;
                charz.magicTree_currPeas = 0;
                charz.updateXu(-up_coin[charz.magicTree_level - 1], 2);
                charz.session.service.magicTree();
            }
        }
    }
    
    public static void ketHat(Char charz) {
        if (charz.magicTree_currPeas < maxPeas[charz.magicTree_level - 1]) {
            if (charz.isgiaodich) {
                charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.O_THE_THUC_HIEN, null, (byte)0);
            } else if (charz.isSecurity) {
                charz.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAOVE, null, 0);
            } else if (ketnhanh[charz.magicTree_level - 1] > charz.getLuong()) {

            } else {
                charz.updateLuong(-ketnhanh[charz.magicTree_level - 1], 2);
                charz.magicTree_currPeas = maxPeas[charz.magicTree_level - 1];
                charz.session.service.magicTree();
            }
        }
    }
    
}
