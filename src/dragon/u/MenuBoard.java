package dragon.u;

import dragon.object.Char;
import dragon.object.Combine;
import dragon.object.Item;
import dragon.object.ItemMap;
import dragon.object.ItemOption;
import dragon.object.LuckyRound;
import dragon.object.MagicTree;
import dragon.object.NangCap;
import dragon.object.Npc;
import dragon.object.Panel;
import dragon.object.Skill;
import dragon.object.ZoneMap;
import dragon.server.Dragon;
import dragon.server.Server;
import dragon.server.Session_ME;
import dragon.server.mResources;
import java.util.ArrayList;
import dragon.t.CallDragon;
import dragon.t.CallDragon.MenuR;
import dragon.t.Clan;
import dragon.t.DoanhTrai;
import dragon.t.KhiHuyDiet;
import dragon.t.KhoBau;
import dragon.t.LuckyNumber;
import dragon.t.Money;
import dragon.t.NpcTask;
import dragon.t.Player;
import dragon.t.Rank;
import dragon.t.SeasonPass;
import dragon.t.Shop;
import dragon.t.DaoLu;
import dragon.t.ConstDaoLu;
import dragon.template.ItemTemplate;
import dragon.v.EffChar;
import dragon.v.Flag;
import dragon.v.LuckyRoundNew;
import dragon.v.LuyenTap;
import dragon.v.Memory;
import dragon.v.NapThe;
import dragon.v.RequestItem;
import dragon.v.RoadSnake;
import java.util.List;
import java.util.Random;
import dragon.server.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author TGDD
 */
public class MenuBoard {

    private final Session_ME session;
     
    private static final int[] GACHA_IDS = { 2045, 1901, 1902, 1903 };
    private static final int[] GACHA_PERCENTS = { 15, 35, 35, 15};
    public ArrayList<MenuInfo> arrMenu;
    public int select;
    public int typeInfo;
    public int npcId;
    public String chat;
    public int avatar;

    public MenuBoard(Session_ME session) {
        this.session = session;
        this.arrMenu = new ArrayList<>();
    }

    public void openUIConfirm(int npcId, String chat, ArrayList<MenuInfo> menu, int avatar) {
        this.npcId = npcId;
        if (chat != null) {
            this.chat = chat;
        }
        if (menu != null) {
            this.arrMenu = menu;
        }
        this.avatar = avatar;
        this.session.service.openUIConfirm(this.npcId, this.chat, this.arrMenu, this.avatar);
    }

    public void openUIConfirm(int npcId, ArrayList<MenuInfo> menu) {
        this.npcId = npcId;
        if (menu != null) {
            this.arrMenu = menu;
        }
        if (npcId == 4) {
            this.session.service.openMagicTreConfirm(arrMenu);
        }
    }

    public void menu(int npcId, int menuId, int optionId) {
        if (npcId == 4) {
            this.openUIConfirm(npcId, menuId);
        }
    }

    public void openMenuUI(int npcId) {
        // Kiem tra nv
        if (!NpcTask.updateTask(this.session.myCharz(), npcId)) {
            this.typeInfo = -1;
            return;
        }
        // Vong xoay may man
//        if (npcId == 54) {
//            this.session.myCharz().openTaiXiu();
//            return;
//        }
        // Find npc and open
        if (this.session.myCharz().zoneMap.isHaveNpc(npcId)) {
            Npc npc = this.session.myCharz().zoneMap.findNPCInMap(npcId);
            if (npc.isHide) {
                return;
            }
            switch (npc.template.npcTemplateId) {
                // Ong noi
                case 0:
                case 1:
                case 2: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_MIAN;
                    this.arrMenu.add(new MenuInfo(mResources.GIFT_CODE, 208));
                    this.arrMenu.add(new MenuInfo(mResources.NAP_TIEN, 209));
                    if (!this.session.myCharz().isCan()) {
                        this.arrMenu.add(new MenuInfo(mResources.OPEN_MEMBER_1, 214));
                    }
                    this.arrMenu.add(new MenuInfo(mResources.NHAN_NGOC_FREE, 397));
                    this.arrMenu.add(new MenuInfo(mResources.DANH_HIEU, 198));
//                    this.arrMenu.add(new MenuInfo(mResources.CHANGE_DH_FREE, 520));
                    if (Dragon.isEvent_VIP) {
                        this.arrMenu.add(new MenuInfo(mResources.EVENT_VIP, 367));
                    }
                    if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().isHopThe != 0) {
                        this.arrMenu.add(new MenuInfo(mResources.ON_FUSION, 319));
                    }
                    this.arrMenu.add(new MenuInfo(mResources.MAILBOX1, 331));
                    // this.arrMenu.add(new MenuInfo(mResources.NAP_THE, 204));
//                    this.arrMenu.add(new MenuInfo("Map Chân Mệnh",527));
                    // [ĐẠO LỮ] Thêm option Đạo Lữ vào menu NPC Ông Nội
                    this.arrMenu.add(new MenuInfo("Đạo Lữ", 2070));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // //Ruong do
                case 3: {
                    if (this.session.myCharz().mapTemplateId == 21 || this.session.myCharz().mapTemplateId == 22
                            || this.session.myCharz().mapTemplateId == 23
                            || this.session.myCharz().mapTemplateId == 153) {
                        this.session.service.openBox();
                    }
                    break;
                }
                // //Cay dau than
                case 4: {
                    this.session.myCharz().resetMenu();
                    if (this.session.myCharz().magicTree_isUpdate) {
                        this.arrMenu.add(new MenuInfo(String.format(mResources.UP_NEXT,
                                MagicTree.up_nhanh[this.session.myCharz().magicTree_level - 1]), 313));
                        this.arrMenu.add(new MenuInfo(
                                String.format(mResources.UP_CANCEL,
                                        Util.gI().numberTostring(
                                                MagicTree.up_coin[this.session.myCharz().magicTree_level - 1] / 2)),
                                314));
                    } else {
                        this.arrMenu.add(new MenuInfo(mResources.BEAN_HARVEST, 312));
                        if (this.session.myCharz().ctaskId >= 1) {
                            if (this.session.myCharz().magicTree_level < MagicTree.maxLevel) {
                                this.arrMenu.add(new MenuInfo(String.format(mResources.MAGIC_TREE_UP,
                                        Util.gI().getFormatTime3(
                                                MagicTree.up_time[this.session.myCharz().magicTree_level - 1]),
                                        Util.gI().numberTostring(
                                                MagicTree.up_coin[this.session.myCharz().magicTree_level - 1])),
                                        315));
                            }
                            if (this.session.myCharz().magicTree_currPeas < MagicTree.maxPeas[this.session
                                    .myCharz().magicTree_level - 1]) {
                                this.arrMenu.add(new MenuInfo(String.format(mResources.PEAS_NEXT,
                                        MagicTree.ketnhanh[this.session.myCharz().magicTree_level - 1]), 316));
                            }
                        }
                    }
                    this.openUIConfirm(npcId, null);
                    break;
                }
                // Bunma
                case 7: {
                    if (this.session.myCharz().mapTemplateId == 0 || this.session.myCharz().mapTemplateId == 84) {
                        if (this.session.myCharz().cgender != 0) {
                            this.session.service.openUISay(npcId, mResources.BUY_OTHER_TRAIDAT, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_BUNMA;
                            this.arrMenu.add(new MenuInfo(mResources.SHOP, 121));
                            if (Dragon.isEvent_Girl) {
                                this.arrMenu.add(new MenuInfo(mResources.TANG_HOA_XANH, 122));
                                this.arrMenu.add(new MenuInfo(mResources.TANG_CHAU_HOA_XANH, 123));
                                this.arrMenu.add(new MenuInfo(mResources.TANG_BONG_HOA, 124));
                            }
                            if (!this.session.myCharz().myObj().reBuyItem.isEmpty()) {
                                this.arrMenu.add(new MenuInfo(mResources.REBUY_ITEM, 181));
                            }
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // Dende
                case 8: {
                    if (this.session.myCharz().mapTemplateId == 7 || this.session.myCharz().mapTemplateId == 84) {
                        if (this.session.myCharz().itemNamekBall != null) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_DENDE1;
                            this.arrMenu.add(new MenuInfo(mResources.HD_GOI_RONG, 103));
                            this.arrMenu.add(new MenuInfo(mResources.CALL_DRAGON2, 104));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            if (this.session.myCharz().cgender != 1) {
                                this.session.service.openUISay(npcId, mResources.BUY_OTHER_NAMEK, -1);
                            } else {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_DENDE;
                                this.arrMenu.add(new MenuInfo(mResources.SHOP, 102));
                                if (!this.session.myCharz().myObj().reBuyItem.isEmpty()) {
                                    this.arrMenu.add(new MenuInfo(mResources.REBUY_ITEM, 181));
                                }
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                        }
                    }
                    break;
                }
                // Appule
                case 9: {
                    if (this.session.myCharz().mapTemplateId == 14 || this.session.myCharz().mapTemplateId == 84) {
                        if (this.session.myCharz().cgender != 2) {
                            this.session.service.openUISay(npcId, mResources.BUY_OTHER_XAYDA, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_APPULE;
                            this.arrMenu.add(new MenuInfo(mResources.SHOP, 180));
                            if (!this.session.myCharz().myObj().reBuyItem.isEmpty()) {
                                this.arrMenu.add(new MenuInfo(mResources.REBUY_ITEM, 181));
                            }
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // Dr Brief
                case 10: {
                    if (this.session.myCharz().mapTemplateId == 84) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DR_BRIEF;
                        if (this.session.myCharz().cgender == 0) {
                            this.arrMenu.add(new MenuInfo(mResources.GO_TRAIDAT, 56));
                        }
                        if (this.session.myCharz().cgender == 1) {
                            this.arrMenu.add(new MenuInfo(mResources.GO_NAMEK, 57));
                        }
                        if (this.session.myCharz().cgender == 2) {
                            this.arrMenu.add(new MenuInfo(mResources.GO_XAYDA, 58));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 153) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DR_BRIEF_2;
                        this.arrMenu.add(new MenuInfo(mResources.CHANGE_SHORT_TEXT, 80));
                        this.arrMenu.add(new MenuInfo(mResources.CHANGE_SHORT_TEXT_RANDOM, 79));
                        this.arrMenu.add(new MenuInfo(mResources.GO_KAME, 78));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 24) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DR_BRIEF;
                        this.arrMenu.add(new MenuInfo(mResources.GO_NAMEK, 57));
                        this.arrMenu.add(new MenuInfo(mResources.GO_XAYDA, 58));
                        this.arrMenu.add(new MenuInfo(mResources.SIEUTHI, 81));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 101) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DR_BRIEF_3;
                        this.arrMenu.add(new MenuInfo(mResources.UP_PETZ, 82));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // //Cargo
                case 11: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_CARGO;
                    this.arrMenu.add(new MenuInfo(mResources.GO_TRAIDAT, 56));
                    this.arrMenu.add(new MenuInfo(mResources.GO_XAYDA, 58));
                    this.arrMenu.add(new MenuInfo(mResources.SIEUTHI, 81));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // //Cui
                case 12: {
                    if (this.session.myCharz().mapTemplateId == 19) {
                        if (this.session.myCharz().ctaskId == 23 && this.session.myCharz().ctaskIndex != 3) {
                            if (this.session.myCharz().ctaskIndex == 0) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_CUI4;
                                this.arrMenu.add(new MenuInfo(mResources.AGREE, 219));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.arrMenu.add(new MenuInfo(mResources.GO_KUKU, 221));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                            if (this.session.myCharz().ctaskIndex == 1) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_CUI5;
                                this.arrMenu.add(new MenuInfo(mResources.AGREE, 219));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.arrMenu.add(new MenuInfo(mResources.GO_MAP_DAU_DINH, 222));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                            if (this.session.myCharz().ctaskIndex == 2) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_CUI6;
                                this.arrMenu.add(new MenuInfo(mResources.AGREE, 219));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.arrMenu.add(new MenuInfo(mResources.GO_RAMBO, 223));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_CUI2;
                            this.arrMenu.add(new MenuInfo(mResources.GO_COLD, 218));
                            this.arrMenu.add(new MenuInfo(mResources.GO_NAPPA, 219));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    if (this.session.myCharz().mapTemplateId == 26) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_CUI;
                        this.arrMenu.add(new MenuInfo(mResources.GO_TRAIDAT, 56));
                        this.arrMenu.add(new MenuInfo(mResources.GO_NAMEK, 57));
                        this.arrMenu.add(new MenuInfo(mResources.SIEUTHI, 81));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 68) {
                        if (this.session.myCharz().ctaskId == 23 && this.session.myCharz().ctaskIndex != 3) {
                            if (this.session.myCharz().ctaskIndex == 0) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_CUI7;
                                this.arrMenu.add(new MenuInfo(mResources.AGREE, 220));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.arrMenu.add(new MenuInfo(mResources.GO_KUKU, 221));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                            if (this.session.myCharz().ctaskIndex == 1) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_CUI8;
                                this.arrMenu.add(new MenuInfo(mResources.AGREE, 220));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.arrMenu.add(new MenuInfo(mResources.GO_MAP_DAU_DINH, 222));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                            if (this.session.myCharz().ctaskIndex == 2) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_CUI9;
                                this.arrMenu.add(new MenuInfo(mResources.AGREE, 220));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.arrMenu.add(new MenuInfo(mResources.GO_RAMBO, 223));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_CUI3;
                            this.arrMenu.add(new MenuInfo(mResources.AGREE, 220));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // Quy lao kame
                case 13: {
                    if (this.session.myCharz().isPetThiTheo
                            && this.session.myCharz().zoneMap.getBossPlayer(this.session.myCharz().playerId) != null
                            && Math.abs(npc.cx - this.session.myCharz().zoneMap
                                    .getBossPlayer(this.session.myCharz().playerId).cx) <= 50) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.DUA_PET_SAY_1;
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 30));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUY_LAO_KAME_3;
                        if (this.session.myCharz().isHaveItemBag(874)) {
                            this.arrMenu.add(new MenuInfo(mResources.GIAO_RUA_CON, 151));
                        }
                        if (this.session.myCharz().clan != null) {
                            this.arrMenu.add(new MenuInfo(mResources.BANG_HOI, 39));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.TALK, 43));
                        this.arrMenu.add(new MenuInfo(mResources.IDOL_DUOIBIEN, 44));
                        this.arrMenu.add(new MenuInfo(String.format(mResources.DOI_TICH_LUY, (int) this.session.myCharz().valueById(10), 50), 50));
                        if (Dragon.isEvent_TetNguyenDan) {
                            this.arrMenu.add(new MenuInfo(mResources.EVENT_TETNGUYENDAN, 54));
                        }
                        if (Dragon.isEvent_Girl) {
                            this.arrMenu.add(new MenuInfo(mResources.EVENT_GIRL, 116));
                        }
                        if (Dragon.isEvent_HungVuong) {
                            this.arrMenu.add(new MenuInfo(mResources.EVENT_KING, 159));
                        }
                        if (Dragon.isEvent_HE2023) {
                            this.arrMenu.add(new MenuInfo(mResources.EVENT_NGAYHE, 193));
                            this.arrMenu.add(new MenuInfo(mResources.CHANGE_DANHHIEU, 200));
                        }

                        if (Dragon.isEvent_DIET_SAU_BO_2023) {
                            this.arrMenu.add(new MenuInfo(mResources.EVENT_HE_2023, 332));
                            this.arrMenu.add(new MenuInfo(mResources.CHANGE_GIFT1, 341));
                            this.arrMenu.add(new MenuInfo(mResources.DONATE_BUG1, 339));
                            this.arrMenu.add(new MenuInfo(mResources.DONATE_BUG2, 340));
                            this.arrMenu.add(new MenuInfo(mResources.BXH_EVENT, 0) {
                                @Override
                                public boolean excute() {
                                    Rank.getRank(3).loadRank();
                                    MenuBoard.this.session.service.top(Rank.getRank(3));
                                    return false;
                                }
                            });
                        }
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Truong lao guru
                case 14: {
                    if (this.session.myCharz().isPetThiTheo && Math.abs(npc.cx - this.session.myCharz().cx) <= 50
                            && Math.abs(npc.cy - this.session.myCharz().cy) <= 50) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.DUA_PET_SAY_1;
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 30));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {

                    }
                    break;
                }
                // Vua vegeta
                case 15: {
                    if (this.session.myCharz().isPetThiTheo && Math.abs(npc.cx - this.session.myCharz().cx) <= 50
                            && Math.abs(npc.cy - this.session.myCharz().cy) <= 50) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.DUA_PET_SAY_1;
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 30));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {

                    }
                    break;
                }
                // Uron
                case 16: {
                    if (this.session.myCharz().cgender == 0) {
                        // TraiDat
                        this.session.myCharz().shopId = 7;
                    } // Namek
                    if (this.session.myCharz().cgender == 1) {
                        this.session.myCharz().shopId = 8;
                    } // Xayda
                    if (this.session.myCharz().cgender == 2) {
                        this.session.myCharz().shopId = 9;
                    }
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                    break;
                }
                // Bo mong
                case 17: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_BO_MONG_1;
//                    this.arrMenu.add(new MenuInfo(mResources.NVHN, 227));
                    this.arrMenu.add(new MenuInfo(mResources.BXH, 224));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // Than meo karin
                case 18: {
                    if (this.session.myCharz().isNhanDauThan) {
                        this.session.myCharz().resetMenu();
                        this.chat = String.format(mResources.SAY_THAN_MEO_KARIN2, this.session.myCharz().cName);
                        this.arrMenu.add(new MenuInfo(mResources.THANK_TEACHER, 329));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_THAN_MEO_KARIN_1;
                        this.arrMenu.add(new MenuInfo(mResources.TD_THAN_MEO, 228));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Thuong de
                case 19: {
                    if (this.session.myCharz().mapTemplateId == 45) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_THUONG_DE_1;
                        this.arrMenu.add(new MenuInfo(mResources.GO_KAIO, 110));
                        this.arrMenu.add(new MenuInfo(mResources.QUAY_NGOC_LUCKY, 111));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 141
                            && this.session.myCharz().zoneMap.map.phoban.getMap(144).isOpen) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_THUONG_DE2;
                        this.arrMenu.add(new MenuInfo(mResources.VE_THAN_DIEN, 328));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Than vu tru
                case 20: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_THAN_VU_TRU;
                    this.arrMenu.add(new MenuInfo(mResources.DI_CHUYEN, 230));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // Ba hat mit
                case 21: {
                    if (this.session.myCharz().mapTemplateId == 5) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BA_HAT_MIT;
                        this.arrMenu.add(new MenuInfo(mResources.EPSAO_TRANGBI, 1));
                        this.arrMenu.add(new MenuInfo(mResources.PHALEHOA_TRANGBI, 2));
                      this.arrMenu.add(new MenuInfo(mResources.TBANHSANG,528));
                 //       this.arrMenu.add(new MenuInfo(mResources.NANG_SET_KICH_HOAT, 357));
                       this.arrMenu.add(new MenuInfo(mResources.NANG_SET_KICH_HOAT, 505));
                        this.arrMenu.add(new MenuInfo(mResources.NANG_CAP_DE_TU, 360));
                        this.arrMenu.add(new MenuInfo(mResources.DOI_CHAN_THIEN_TU, 512));
                        this.arrMenu.add(new MenuInfo(mResources.NANG_CAP_THIEN_TU, 515));
                      //  this.arrMenu.add(new MenuInfo(mResources.MUA_VIP,998));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 42 || this.session.myCharz().mapTemplateId == 43
                            || this.session.myCharz().mapTemplateId == 44
                            | this.session.myCharz().mapTemplateId == 84) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BA_HAT_MIT;
                        this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY1, 460));
                        this.arrMenu.add(new MenuInfo(mResources.SHOP_BUA, 3));
                        this.arrMenu.add(new MenuInfo(mResources.UPGRADE_ITEM, 4));
//                        if (this.session.myCharz().getBongTai() != null
//                                && this.session.myCharz().getBongTai().template.id == 921) {
//                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT2_2, 5));
//                        } else {
//                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT2, 6));
//                        }
                        if (this.session.myCharz().getBongTai() != null
                                && this.session.myCharz().getBongTai().template.id == 921) {
                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT2_2, 5));
                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT3, 518));
                        } else if (this.session.myCharz().getBongTai() != null && this.session.myCharz().getBongTai().template.id == 2026) {
                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT4, 523));

                        } else {
//                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT4, 523));
                            this.arrMenu.add(new MenuInfo(mResources.UPDATE_BT2, 6));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.NHAP_DA, 7));
                        this.arrMenu.add(new MenuInfo(mResources.NNR, 8));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Trong tai
                case 22: {
                    if (this.session.myCharz().mapTemplateId == 113) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_TRONG_TAI1;
                        this.arrMenu.add(new MenuInfo(mResources.TOP_100CT, 154));
                        this.arrMenu.add(new MenuInfo(mResources.HDT, 155));
                        if (this.session.myCharz().myObj().nFreeTicket > 0) {
                            this.arrMenu.add(new MenuInfo(
                                    String.format(mResources.FREE_TICKET, this.session.myCharz().myObj().nFreeTicket),
                                    156));
                        } else {
                            this.arrMenu.add(new MenuInfo(mResources.PRI_FIGHT, 157));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.GO_DHVT, 131));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Ghi danh
                case 23: {
                    if (this.session.myCharz().mapTemplateId == 52) {
                        if (this.session.myCharz().isWaitWar && DaiHoi.isWar) {
                            this.session.service.openUISay(npcId, String.format(mResources.BAN_VAO_VONG, DaiHoi.nTurn),
                                    -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            if (DaiHoi.isRegister) {
                                this.chat = String.format(mResources.SAY_GHI_DANH_5, DaiHoi.name, DaiHoi.sizeFighter());
                            } else {
                                this.chat = String.format(mResources.SAY_GHI_DANH_1, DaiHoi.getNextHour());
                            }
                            this.arrMenu.add(new MenuInfo(mResources.DETAIL_INFO, 137));
                            if (DaiHoi.isRegister) {
                                if (DaiHoi.isHaveFighter(this.session.myCharz().playerId)) {
                                    this.arrMenu.add(new MenuInfo(mResources.CANCEL_REGISTER, 140));
                                } else {
                                    this.arrMenu.add(new MenuInfo(mResources.REGISTER, 138));
                                }
                            } else {
                                this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                            }
                            this.arrMenu.add(new MenuInfo(mResources.GIAI_SIEU_HANG, 153));
                            this.arrMenu.add(new MenuInfo(mResources.DHVT_23, 130));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    if (this.session.myCharz().mapTemplateId == 129) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_GHI_DANH_2;
                        this.arrMenu.add(new MenuInfo(mResources.HDT, 136));
                        if (this.session.myCharz().myObj().nWinHD23 < 11) {
                            this.arrMenu.add(new MenuInfo(String.format(mResources.THI_DAU_1,
                                    1 * (this.session.myCharz().myObj().nJoinDH23 + 1)), 132));
                            this.arrMenu
                                    .add(new MenuInfo(
                                            String.format(mResources.THI_DAU_2,
                                                    Util.gI().numberTostring(
                                                            50000 * (this.session.myCharz().myObj().nJoinDH23 + 1))),
                                            133));
                        }
                        if (this.session.myCharz().myObj().nWinHD23 > 0) {
                            this.arrMenu.add(new MenuInfo(
                                    String.format(mResources.NHAN_RUONG, this.session.myCharz().myObj().nWinHD23),
                                    134));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.GO_DHVT, 131));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Linh canh
                case 25: {
                    if (this.session.myCharz().clan != null) {
                        if ((this.session.myCharz().zoneMap.getCountPlayerInClan(this.session.myCharz()) < 1
                                || this.session.myCharz().clan.getSizeMember() < 3)
                                && this.session.myCharz().clan.doanhTrai == null) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_LINH_CANH_5;
                            this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                            this.arrMenu.add(new MenuInfo(mResources.HDT, 233));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else if (this.session.myCharz().clan.doanhTrai == null
                                && this.session.myCharz().clan.countbarrack >= Clan.MAX_JOIN_BARRACK) {
                            this.session.myCharz().resetMenu();
                            this.chat = String.format(mResources.SAY_LINH_CANH_6,
                                    this.session.myCharz().clan.countbarrack, this.session.myCharz().clan.playerBarrack,
                                    this.session.myCharz().clan.timeBarrack);
                            this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                            this.arrMenu.add(new MenuInfo(mResources.HDT, 233));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else if (this.session.myCharz().clan.doanhTrai == null) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_LINH_CANH_1;
                            this.arrMenu.add(new MenuInfo(mResources.VAO_MIEN_PHI, 234));
                            this.arrMenu.add(new MenuInfo(mResources.NO, 0));
                            this.arrMenu.add(new MenuInfo(mResources.HDT, 233));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else if (this.session.myCharz().clan.doanhTrai != null) {
                            this.session.myCharz().resetMenu();
                            this.chat = String.format(mResources.SAY_LINH_CANH_2,
                                    Util.gI().getStrTime(this.session.myCharz().clan.doanhTrai.miliTime));
                            this.arrMenu.add(new MenuInfo(mResources.JOIN, 235));
                            this.arrMenu.add(new MenuInfo(mResources.NO, 0));
                            this.arrMenu.add(new MenuInfo(mResources.HDT, 233));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    } else {
                        this.session.service.openUISay(npcId, mResources.SAY_LINH_CANH_4, -1);
                    }
                    break;
                }
                // Doc Nhan
                case 26: {
                    if (this.session.myCharz().zoneMap.map.doanhTrai != null) {
                        DoanhTrai dt = this.session.myCharz().zoneMap.map.doanhTrai;
                        if (dt.isWin && !dt.isGift) {
                            dt.isGift = true;
                            dt.miliTime = 1000 * 60 * 5;
                            dt.updateWin();
                            dt.addChatPet(mResources.WIN_DOANH_TRAI);
                            this.session.service.openUISay(npcId, mResources.SAY_DOC_NHAN, -1);
                        }
                    }
                    break;
                }
                // Cua hang ky gui
                case 28: {
                    if (this.session.myCharz().mapTemplateId == 84) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_SHOP_KY_GUI_1;
//                        this.arrMenu.add(new MenuInfo(mResources.HDT, 236));
//                        this.arrMenu.add(new MenuInfo(mResources.SHOP_KY_GUI, 237));
//                        this.arrMenu.add(new MenuInfo(mResources.SHOP_KY_GUI_2, 238));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Rong Omega
                case 29: {
                    if (BlackBall.gI().isBlackBall) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_OMEGA2;
                        this.arrMenu.add(new MenuInfo(mResources.HDT, 239));
                        this.arrMenu.add(new MenuInfo(mResources.JOIN, 240));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_OMEGA1;
                        this.arrMenu.add(new MenuInfo(mResources.HDT, 239));
                        if (!this.session.myCharz().myObj().blackBalls.isEmpty()) {
                            this.arrMenu.add(new MenuInfo(mResources.NHAN_THUONG, 241));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Rong sao
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_RONG_SAO;
                    if (this.session.myCharz().itemBlackBall != null && this.session.myCharz().phuHoBlackBall == 0) {
                        this.arrMenu.add(new MenuInfo(mResources.X3HP, 243));
                        this.arrMenu.add(new MenuInfo(mResources.X5HP, 244));
                        this.arrMenu.add(new MenuInfo(mResources.X7HP, 245));
                    }
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // Bunma TL
                case 37: {
                    if (this.session.myCharz().mapTemplateId == 102) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BUNMA_2;
                        this.arrMenu.add(new MenuInfo(mResources.KE_CHUYEN, 93));
                        this.arrMenu.add(new MenuInfo(mResources.SHOP, 94));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 101) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BUNMA_3;
                        this.arrMenu.add(new MenuInfo(mResources.SHOP, 95));
                        this.arrMenu.add(new MenuInfo(mResources.BUY_DLL, 98));
                        this.arrMenu.add(new MenuInfo(mResources.BUY_BLT, 99));
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Ca lich
                case 38: {
                    if (this.session.myCharz().mapTemplateId == 102) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_CA_LICH;
                        this.arrMenu.add(new MenuInfo(mResources.KE_CHUYEN, 246));
                        this.arrMenu.add(new MenuInfo(mResources.GO_QK, 247));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {
                        if (this.session.myCharz().ctaskId == 26 && this.session.myCharz().ctaskIndex > 2) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_CA_LICH2;
                            this.arrMenu.add(new MenuInfo(mResources.GO_TL, 248));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_CA_LICH;
                            this.arrMenu.add(new MenuInfo(mResources.KE_CHUYEN, 246));
                            this.arrMenu.add(new MenuInfo(mResources.GO_TL, 248));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // Santa
                case 39: {
                    Player o = null;
                    if (Dragon.isEvent_Mabu) {
                        o = Player.getBossPlayer(this.session.myCharz().playerId, 9);
                    }
                    if (o != null && o.zoneMap == this.session.myCharz().zoneMap
                            && Math.abs(o.cx - this.session.myCharz().cx) < 50
                            && Math.abs(o.cy - this.session.myCharz().cy) < 30 && o.timeClear == -1) {
                        o.timeClear = 1;
                        this.session.service.openUISay(39, mResources.THANK_SANTA, -1);
                        Item it = null;
                        if (Util.gI().nextInt(100) < 50) {
                            int idT = Util.gI().nextInt(441, 447);
                            it = new Item(idT, false, 1, ItemOption.getOption(idT, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 5) {
                            this.session.myCharz().updateLuongKhoa(Util.gI().nextInt(1, 5), 2);
                        } else if (Util.gI().nextInt(100) < 5) {
                            this.session.myCharz().updateXu(Util.gI().nextInt(1, 10) * 1000000, 1);
                        } else if (Util.gI().nextInt(100) < 10) {
                            it = new Item(20, false, 1, ItemOption.getOption(20, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else {
                            it = new Item(757, false, 1, ItemOption.getOption(757, 30, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        }
                        if (it != null) {
                            if (it.isHaveOption(93)) {
                                it.setExpires(System.currentTimeMillis()
                                        + (long) (1000l * 60l * 60l * 24l * (long) (it.getParamOption(93) + 1)));
                            }
                            this.session.myCharz().addItemBag(0, it);
                        }
                    } else {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_SANTA;
                        this.arrMenu.add(new MenuInfo(mResources.SHOP, 182));
                        if (!this.session.myCharz().myObj().reBuyItem.isEmpty()) {
                            this.arrMenu.add(new MenuInfo(mResources.REBUY_ITEM, 181));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.CUAHANG_DACBIET, 361));
                        this.arrMenu.add(new MenuInfo(mResources.TRANG_BI, 185));
                        this.arrMenu.add(new MenuInfo(mResources.THINH_HANH, 186));
                        this.arrMenu.add(new MenuInfo(mResources.DANH_HIEU1, 525));
                        this.arrMenu.add(new MenuInfo(mResources.SHOP_VIP, 526));
                        this.arrMenu.add(new MenuInfo(mResources.SHOP_HEAD, 183));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Mabu map
                case 40: {
                   if (this.session.myCharz().mapTemplateId == 5) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_MABU_MAP;
                        this.arrMenu.add(new MenuInfo(mResources.SHOP, 184));
                        this.arrMenu.add(new MenuInfo(mResources.TRANG_BI, 185));
                        this.arrMenu.add(new MenuInfo(mResources.THINH_HANH, 186));
                        this.arrMenu.add(new MenuInfo(mResources.DANH_HIEU1, 525));
                        this.arrMenu.add(new MenuInfo(mResources.SHOP_VIP, 526));
                         this.arrMenu.add(new MenuInfo("Gacha", 550));
                        this.openUIConfirm(npcId, null, null, -1);
                   }
                    break;
                }
                // Trung thu
                case 41: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_TRUNG_THU;
                    this.arrMenu.add(new MenuInfo(mResources.SHOP2, 249));
                    this.arrMenu.add(new MenuInfo(mResources.DOI_DUOI_KHI, 250));
                    this.arrMenu.add(new MenuInfo(mResources.TOP_TRUNG_THU, 253));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // //Quoc vuong
                case 42: {
                    if (this.session.myCharz().mapTemplateId == 43) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUOC_VUONG_1;
                        if (this.session.myCharz().cPowerLimit < 5) {
                            this.arrMenu.add(new MenuInfo(mResources.USER, 141));
                        }
                        if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().cPowerLimit < 5) {
                            this.arrMenu.add(new MenuInfo(mResources.DE_TU, 142));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // //To su kaio
                case 43: {
                    if (this.session.myCharz().mapTemplateId == 50) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_TO_SU_KAIO_1;
                        this.arrMenu.add(new MenuInfo(mResources.UP_LIMIT_POWER, 146));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Osin
                case 44: {
                    if (this.session.myCharz().mapTemplateId == 50) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_OSIN;
                        this.arrMenu.add(new MenuInfo(mResources.GO_KAIO, 254));
                        if (this.session.myCharz().cPower >= 40000000000L) {
                            this.arrMenu.add(new MenuInfo(mResources.GO_BILL, 255));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 52) {
                        if (Server.gI().isMabu) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_OSIN_5;
                            this.arrMenu.add(new MenuInfo(mResources.OK, 263));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_OSIN_1;
                            this.arrMenu.add(new MenuInfo(mResources.OK, 256));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    if (this.session.myCharz().mapTemplateId == 114 || this.session.myCharz().mapTemplateId == 115
                            || this.session.myCharz().mapTemplateId == 117
                            || this.session.myCharz().mapTemplateId == 118
                            || this.session.myCharz().mapTemplateId == 119
                            || this.session.myCharz().mapTemplateId == 120) {
                        if (Flag.FLAGS.get(this.session.myCharz().cFlag).itemFlag.template.id == 519) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_OSIN_2;
                            this.arrMenu.add(new MenuInfo(mResources.HDT, 257));
                            this.arrMenu.add(new MenuInfo(mResources.GIAI_PT, 258));
                            if (this.session.myCharz().mapTemplateId == 120) {
                                this.arrMenu.add(new MenuInfo(mResources.GO_HOME, 259));
                            } else {
                                this.arrMenu.add(new MenuInfo(mResources.DOWN_ROOM, 260));
                            }
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.service.openUISay(npcId, mResources.SAY_OSIN_3, -1);
                        }
                    }
                    if (this.session.myCharz().mapTemplateId == 127) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_OSIN_6;
                        this.arrMenu.add(new MenuInfo(mResources.PHU_HO, 261));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.arrMenu.add(new MenuInfo(mResources.GO_DHVT, 262));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 154) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_OSIN;
//                        this.arrMenu.add(new MenuInfo(mResources.SHOP_2, 264));
                        this.arrMenu.add(new MenuInfo(mResources.GO_NGUC_TU, 265));
                        this.arrMenu.add(new MenuInfo(mResources.GO_BILL_2,2484));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 155) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_OSIN;
                        this.arrMenu.add(new MenuInfo(mResources.GO_BILL, 255));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Kibit
                case 45: {
                    if (this.session.myCharz().mapTemplateId == 50) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_KIBIT1;
                        this.arrMenu.add(new MenuInfo(mResources.GO_KAIO, 254));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Babiday
                case 46: {
                    if (this.session.myCharz().mapTemplateId == 114 || this.session.myCharz().mapTemplateId == 115
                            || this.session.myCharz().mapTemplateId == 117
                            || this.session.myCharz().mapTemplateId == 118
                            || this.session.myCharz().mapTemplateId == 119
                            || this.session.myCharz().mapTemplateId == 120) {
                        if (Flag.FLAGS.get(this.session.myCharz().cFlag).itemFlag.template.id == 520) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_BABIDAY_2;
                            this.arrMenu.add(new MenuInfo(mResources.HDT, 257));
                            this.arrMenu.add(new MenuInfo(mResources.GIAI_PT, 258));
                            if (this.session.myCharz().mapTemplateId != 120) {
                                this.arrMenu.add(new MenuInfo(mResources.DOWN_ROOM, 260));
                            }
                            this.arrMenu.add(new MenuInfo(mResources.GO_HOME, 259));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.service.openUISay(npcId, mResources.SAY_OSIN_3, -1);
                        }
                    }
                    break;
                }
                // Giu-ma Dau BO
                case 47: {
                    if (this.session.myCharz().mapTemplateId == 153) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_GU_UMA_DAU_BO_1;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 85));
                        if (this.session.myCharz().clan != null
                                && Util.gI().getDayGap(this.session.myCharz().clan.lastReport) > 0) {
                            this.arrMenu.add(new MenuInfo(mResources.REPORT_CLAN, 86));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Ngo khong
                case 48: {
                    if (this.session.myCharz().mapTemplateId == 124) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_NGO_KHONG_1;
                        this.arrMenu.add(new MenuInfo(mResources.TANG_QUA_HONG_DAO, 266));
                        this.arrMenu.add(new MenuInfo(mResources.TANG_QUA_HONG_DAO_CHIN, 267));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Duong tang
                case 49: {
                    if (this.session.myCharz().mapTemplateId == 0) {
                        if (this.session.myCharz().isMonkeyCheat) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_DUONG_TANG_4;
                            this.arrMenu.add(new MenuInfo(mResources.AGREE, 318));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.arrMenu.add(new MenuInfo(mResources.BONUS, 269));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_DUONG_TANG_1;
                            this.arrMenu.add(new MenuInfo(mResources.AGREE, 268));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.arrMenu.add(new MenuInfo(mResources.BONUS, 269));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    if (this.session.myCharz().mapTemplateId == 122) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DUONG_TANG_2;
                        this.arrMenu.add(new MenuInfo(mResources.GO_ARU, 270));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 124) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DUONG_TANG_3;
                        this.arrMenu.add(new MenuInfo(mResources.UNSE, 271));
                        this.arrMenu.add(new MenuInfo(mResources.GO_ARU, 270));
                        this.arrMenu.add(new MenuInfo(mResources.TOP_HOA_QUA, 272));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Trung Mabu
                case 50: {
                    if (this.session.myCharz().mapTemplateId == this.session.myCharz().mainHome()) {
                        if (this.session.myCharz().isHaveDuaHau(50, 4664)) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_QUA_TRUNG1;
                            if (this.session.myCharz().getDuaHau(50, 4664).second
                                    - ((System.currentTimeMillis() / 1000L)
                                    - this.session.myCharz().getDuaHau(50, 4664).last) <= 0) {
                                this.arrMenu.add(new MenuInfo(mResources.HATCH, 61));
                            } else {
                                this.arrMenu.add(new MenuInfo(mResources.NO_TRUNG_NHANH, 65));
                            }
                            this.arrMenu.add(new MenuInfo(mResources.CANCEL_TRUNG, 63));
                            this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    if (this.session.myCharz().mapTemplateId == 101) {
                        if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_QUA_TRUNG5;
                            if (this.session.myCharz().getDuaHau(50, 6546).second
                                    - ((System.currentTimeMillis() / 1000L)
                                    - this.session.myCharz().getDuaHau(50, 6546).last) <= 0) {
                                this.arrMenu.add(new MenuInfo(mResources.HATCH, 67));
                            } else {
                                this.arrMenu.add(new MenuInfo(mResources.ADD_LINHLUC, 71));
                            }
                            this.arrMenu.add(new MenuInfo(mResources.CANCEL_TRUNG, 69));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // dua hau
                case 51: {
                    if (this.session.myCharz().mapTemplateId == this.session.myCharz().mainHome()) {
                        if (this.session.myCharz().isHaveDuaHau(51, -1)) {
                            if (this.session.myCharz().getDuaHau(51, -1).duaHauIndex == 3) {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_DUA_HAU2;
                                this.arrMenu.add(new MenuInfo(mResources.THU_HOACH, 158));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.openUIConfirm(npcId, null, null, -1);
                            } else {
                                this.session.myCharz().resetMenu();
                                this.chat = mResources.SAY_DUA_HAU1;
                                this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                                this.openUIConfirm(npcId, null, null, -1);
                            }
                        }
                    }
                    break;
                }
                // Hung Vuong
                case 52: {
                    if (this.session.myCharz().mapTemplateId == 0) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_HUNG_VUONG1;
                        this.arrMenu.add(new MenuInfo(mResources.TANG_MAM_BAC, 164));
                        this.arrMenu.add(new MenuInfo(mResources.TANG_MAM_VANG, 165));
                        this.arrMenu.add(new MenuInfo(mResources.CHANGE_GIFT_EVENT, 166));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Tapion
                case 53: {
                    if (this.session.myCharz().mapTemplateId == 19) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_TAPION_1;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 273));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 126) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_TAPION_2;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 274));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                //Ly Tieu Nuong
//                case 54:
//                    if (this.session.myCharz().isCan()) {
//                        if (this.session.myCharz().mapTemplateId == 5) {
//                            this.session.myCharz().resetMenu();
//                            this.chat = "Mini Game\n";
////                            this.arrMenu.add(new MenuInfo("Kéo\nBúa\nBao", 405));
//                            //this.arrMenu.add(new MenuInfo("Vòng Quay\nMay Mắn", 421));
//                            this.arrMenu.add(new MenuInfo("Chọn ai đây", 175));
//                            this.arrMenu.add(new MenuInfo("Từ chối", 0));
//                            this.openUIConfirm(npcId, null, null, -1);
//                        }
//                        break;
//                    } else {
//                        this.session.myCharz().addInfo1("Tài khoản cần phải mở thành viên để sử dụng tính năng này.");
//                    }
                // Bill
                case 55: {
                    if (this.session.myCharz().mapTemplateId == 48) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BILL_1;
                        this.arrMenu.add(new MenuInfo(mResources.TALK, 275));
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 154) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BILL_4;
                        this.arrMenu.add(new MenuInfo(mResources.GO_KAIO_2, 277));
                        this.arrMenu.add(new MenuInfo("Cửa hàng", 989));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Wish
                case 56: {
                    if (this.session.myCharz().mapTemplateId == 48) {

                    }
                    if (this.session.myCharz().mapTemplateId == 154) {
                        this.session.myCharz().resetMenu();
                        this.chat = String.format(mResources.SAY_WISH_1, Memory.get(this.session.userId).nFreeWish);
                        this.arrMenu.add(new MenuInfo(mResources.TALK, 167));
                        this.arrMenu.add(new MenuInfo(mResources.HOC_TUYET_KY, 170));
                        this.arrMenu.add(new MenuInfo(mResources.TOP_100, 168));
                        if (Memory.get(this.session.userId).nFreeWish > 0) {
                            this.arrMenu.add(new MenuInfo(
                                    String.format(mResources.LEVEL_2, LuyenTap.getLevel(this.session.myCharz().cName)),
                                    169));
                        } else {
                            this.chat = mResources.SAY_WISH2;
                            this.arrMenu.add(new MenuInfo(mResources.DONATE_FODS, 320));
                        }
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Goku SSJ
                case 60: {
                    if (this.session.myCharz().mapTemplateId == 80) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_GOKU_SSJ;
                        this.arrMenu.add(new MenuInfo(mResources.CHUAN, 278));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 131) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_GOKU_SSJ2;
                        this.arrMenu.add(new MenuInfo(mResources.BO_TAY, 0));
                        this.arrMenu.add(new MenuInfo(mResources.GO_MAP_OLD, 279));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Goku SSJ
                case 61: {
                    if (this.session.myCharz().mapTemplateId == 133) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_GOKU_SSJ_2;
                        this.arrMenu.add(new MenuInfo(mResources.NHAN_THUONG, 280));
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Potage
                case 62: {
                    if (this.session.myCharz().mapTemplateId == 140) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_POTAGE1;
                        this.arrMenu.add(new MenuInfo(mResources.HDT, 59));
                        this.arrMenu.add(new MenuInfo(mResources.OK, 60));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Jaco
                case 63: {
                    if (this.session.myCharz().mapTemplateId == 24) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_JACO1;
                        this.arrMenu.add(new MenuInfo(mResources.GO_POTAUFEU, 55));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().mapTemplateId == 139) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_JACO2;
                        this.arrMenu.add(new MenuInfo(mResources.GO_TRAIDAT, 56));
                        this.arrMenu.add(new MenuInfo(mResources.GO_NAMEK, 57));
                        this.arrMenu.add(new MenuInfo(mResources.GO_XAYDA, 58));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Dai Thien Su
                case 64: {
                    if (this.session.myCharz().mapTemplateId == 0 || this.session.myCharz().mapTemplateId == 7
                            || this.session.myCharz().mapTemplateId == 14) {
                        if (RongVoCuc.gI().isCallRongVoCuc) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_DAD_1;
                            for (int i = 0; i < RongVoCuc.gI().arrStrWish.length; i++) {
                                this.arrMenu.add(new MenuInfo(String.format(mResources.WISH_PERCENT,
                                        RongVoCuc.gI().arrStrWish[i], RongVoCuc.gI().getPercent(i)), 281, i));
                            }
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // Noi Banh
                case 66: {
                    if (Dragon.isEvent_TetNguyenDan) {
                        if (this.session.myCharz().isNauBanhOK()) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_NOI_BANH_5;
                            this.arrMenu.add(new MenuInfo(String.format(mResources.CHANGE_POINT, this.session.myCharz().myObj().pointEvent), 31));
                            this.arrMenu.add(new MenuInfo(this.session.myCharz().strNauBanhOK(), 33));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = String.format(mResources.SAY_NOI_BANH_1, this.session.myCharz().cName);
                            this.arrMenu.add(new MenuInfo(String.format(mResources.CHANGE_POINT, this.session.myCharz().myObj().pointEvent), 31));
                            this.arrMenu.add(new MenuInfo(mResources.TU_NAU_BANH, 32));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    // Lech Teamobi
                    if (Dragon.isEvent_HE2023) {
                        if (this.session.myCharz().isNauBanhOK()) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_NOI_BANH_6;
                            this.arrMenu.add(new MenuInfo(
                                    String.format(mResources.CHANGE_POINT, this.session.myCharz().myObj().pointEvent),
                                    192));
                            this.arrMenu.add(new MenuInfo(this.session.myCharz().strNauBanhOK(), 33));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = String.format(mResources.SAY_NOI_BANH_7, this.session.myCharz().cName);
                            this.arrMenu.add(new MenuInfo(
                                    String.format(mResources.CHANGE_POINT, this.session.myCharz().myObj().pointEvent),
                                    192));
                            this.arrMenu.add(new MenuInfo(mResources.TU_NAU_BANH2, 187));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // MR PoPo
                case 67: {
                    if (this.session.myCharz().mapTemplateId == 0) {
                        if (this.session.myCharz().clan == null || this.session.myCharz().clan.destronGas == null) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_MR_POPO_1;
                            this.arrMenu.add(new MenuInfo(mResources.INFO, 87));
                            this.arrMenu.add(new MenuInfo(mResources.TOP100_BAG, 88));
                            if (this.session.myCharz().clan != null) {
                                this.arrMenu.add(new MenuInfo(mResources.THANH_TICH_BANG, 89));
                            }
                            this.arrMenu.add(new MenuInfo(mResources.OK, 90));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.myCharz().resetMenu();
                            this.chat = String.format(mResources.SAY_MR_POPO_2,
                                    this.session.myCharz().clan.destronGas.level,
                                    Util.gI().getStrTime(System.currentTimeMillis()
                                            - this.session.myCharz().clan.destronGas.lastOpen));
                            this.arrMenu.add(new MenuInfo(mResources.OK, 91));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        }
                    }
                    break;
                }
                // Tho Dai Ca
                case 69: {
                    if (this.session.myCharz().mapTemplateId == 5) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_THO_DAI_CA_1;
                        if (!(boolean) this.session.myCharz().valueById(2)) {
                            this.arrMenu.add(new MenuInfo(mResources.WISH_0, 282));
                        }
                        this.arrMenu.add(new MenuInfo(mResources.WISH_1, 284));
                        this.arrMenu.add(new MenuInfo(mResources.WISH_2, 286));
                        this.arrMenu.add(new MenuInfo(mResources.WISH_3, 288));
                        this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Bardock
                case 70: {
                    if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 2
                            && this.session.myCharz().mapTemplateId == 160) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BARDOCK_1;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 290));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 4
                            && this.session.myCharz().mapTemplateId == 160) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BARDOCK_2;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 291));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 6
                            && this.session.myCharz().mapTemplateId == 160) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BARDOCK_3;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 292));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Berry
                case 71: {
                    if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 5
                            && this.session.myCharz().mapTemplateId == 161) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_BERRY_1;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 293));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Tori - Bot
                case 72: {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_TORI_BOT_1;
                    this.arrMenu.add(new MenuInfo(mResources.SHOP2, 217));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                // Mai
                case 76: {
                    if (this.session.myCharz().mapTemplateId == 166) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_MAI1;
                        this.arrMenu.add(new MenuInfo(mResources.SHOP, 347));
                        this.arrMenu.add(new MenuInfo(mResources.EXCHANGE, 348));
                        this.arrMenu.add(new MenuInfo(mResources.NANG_CAP1, 351));
                        this.arrMenu.add(new MenuInfo(mResources.DANH_HIEU, 353));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                // Fu
                case 77: {
                    if (this.session.myCharz().mapTemplateId == 166) {
                        if (this.session.myCharz().isCallCumber == 2) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY_FU1;
                            this.arrMenu.add(new MenuInfo(mResources.OK, 330));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                            this.openUIConfirm(npcId, null, null, -1);
                        } else {
                            this.session.service.openUISay(npcId, mResources.CHAT_FU1, npc.avatar);
                            if (this.session.myCharz().isCallCumber == 1) {
                                this.session.myCharz().isCallCumber = 2;
                            }
                        }
                    }
                    break;
                }
                // Admin
//                case 78: {
//                    if (this.session.myCharz().mapTemplateId == 5) {
//                        this.session.myCharz().resetMenu();
//                        this.chat = mResources.SAY_ADMIN;
//                        // this.arrMenu.add(new MenuInfo(mResources.SHOP, 182));
////                        this.arrMenu.add(new MenuInfo(mResources.CUAHANG_DACBIET, 361));
//                        // this.arrMenu.add(new MenuInfo(mResources.SHOP_HEAD, 183));
//                        this.openUIConfirm(npcId, null, null, -1);
//                    }
//                    break;
//                }

                // NPC Trai Đẹp - Bán Đan Đạo Lữ + Hồn Đạo Lữ (Làng Kakarot)
                case 83: {
                    this.session.myCharz().resetMenu();
                    this.chat = "Trai đẹp đây! Bạn muốn mua gì?";
                    this.arrMenu.add(new MenuInfo("Đan Đạo Lữ", 2090));
                    this.arrMenu.add(new MenuInfo("Mua Hồn Đạo Lữ\n100,000 VND", 2091));
                    this.arrMenu.add(new MenuInfo("Đóng", 0));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                case 84: {
                    // NPC Điểm online: thể lệ + số điểm + đổi quà (tất cả đồ khóa giao dịch - ItemOption 30)
                    this.session.myCharz().resetMenu();
                    int diem = this.session.myCharz().onlinePoints;
                    this.chat = "|7|=== THỂ LỆ ĐỔI ĐIỂM ONLINE ===\n"
                            + "|0|Mỗi 15 phút online = 1 điểm (tối đa 8 lần/ngày).\n"
                            + "|0|Điểm reset theo ngày. Đổi quà tại đây.\n"
                            + "|6|--------------------------------\n"
                            + "|2|Số điểm hiện có: " + diem + " điểm\n"
                            + "|6|--------------------------------\n"
                            + "|0|Chọn đổi điểm nhận quà bên dưới:";
                    this.arrMenu.add(new MenuInfo("Đổi 4 điểm - 10 thỏi vàng", 2092));
                    this.arrMenu.add(new MenuInfo("Đổi 5 điểm - 1 trứng đệ vip", 2093));
                    this.arrMenu.add(new MenuInfo("Đổi 8 điểm - 1 gói túi mù", 2094));
                    this.arrMenu.add(new MenuInfo("Đóng", 0));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                case 85: {
                    // NPC Điểm Danh (Đảo Kame): chuỗi 7 ngày, quà theo bảng; đủ 7 ngày hoặc đứt chuỗi thì reset về 1
                    if (this.session.myCharz().mapTemplateId != 5) {
                        this.session.myCharz().goManorClan();
                        break;
                    }
                    this.session.myCharz().resetMenu();
                    int streak = this.session.myCharz().loginStreakDays;
                    String lastDate = this.session.myCharz().loginStreakLastDate;
                    this.chat = "|7|=== ĐIỂM DANH HÀNG NGÀY ===\n"
                            + "|0|Ngày 1: Bình tiềm năng x3,x5 mỗi loại x2 + Trứng đệ tử VIP\n"
                            + "|0|Ngày 2: Bình tiềm năng x3,x5,x7 mỗi x2 + 20 Thỏi vàng\n"
                            + "|0|Ngày 3: Cuồng nộ, Bổ huyết, Giáp sên, Bổ khí mỗi x30\n"
                            + "|0|Ngày 4: Cuồng nộ/Bổ huyết/Bổ khí/Giáp sên cấp 2 mỗi x20\n"
                            + "|0|Ngày 5: Đá nâng cấp mỗi loại x50\n"
                            + "|0|Ngày 6: 100 Đá ngục tù + 100 Thỏi vàng\n"
                            + "|0|Ngày 7: 3 Bánh chưng, 3 Bánh tét, 2 bộ Ngọc rồng băng\n"
                            + "|0|Tất cả quà khóa giao dịch. Lỡ 1 ngày = reset về ngày 1.\n"
                            + "|6|--------------------------------\n"
                            + "|2|Bạn đang điểm danh ngày: " + streak + " / 7\n"
                            + (lastDate != null && !lastDate.isEmpty() ? "|0|Nhận quà gần nhất: " + lastDate + "\n" : "|0|Chưa nhận quà lần nào.\n")
                            + "|6|--------------------------------\n"
                            + "|0|Chọn bên dưới (cần đủ ô trống hành trang):";
                    this.arrMenu.add(new MenuInfo("Nhận quà điểm danh hôm nay", 2095));
                    this.arrMenu.add(new MenuInfo("Đóng", 0));
                    this.openUIConfirm(npcId, null, null, npc.avatar);
                    break;
                }
                case 86: {
                    // NPC Hành trình mùa (Battle Pass) – tiến độ, nhận quà, mua Pro/Master
                    if (this.session.myCharz().mapTemplateId != 5) {
                        this.session.myCharz().goManorClan();
                        break;
                    }
                    this.session.myCharz().resetMenu();
                    String seasonId = SeasonPass.getCurrentSeasonId();
                    SeasonPass.SeasonPassData d = SeasonPass.gI().getOrCreate(this.session.myCharz().playerId, seasonId);
                    int maxLv = SeasonPass.gI().getMaxLevel(d.passType);
                    String passName = d.passType == SeasonPass.PASS_MASTER ? "Master" : (d.passType == SeasonPass.PASS_PRO ? "Pro" : "Free");
                    this.chat = "|7|=== HÀNH TRÌNH MÙA " + seasonId + " ===\n"
                            + "|0|Gói: " + passName + " | Cấp: " + d.level + " / " + maxLv + " | Điểm: " + d.exp + " / 10 (mỗi cấp 10 điểm)\n"
                            + "|6|--------------------------------\n"
                            + "|0|Fide Đại Ca 3: " + d.fideKillCount + " | Tiểu đội trưởng: " + d.tieuDoiTruongKillCount + " | Super Bojack: " + d.superBojackKillCount + "\n"
                            + "|0|King Kong: " + d.kingKongKillCount + " | Online (phút): " + d.onlineMinutes + " | Quái: " + d.monsterKillCount + " | PvP kill: " + d.pvpKillCount + "\n"
                            + "|6|--------------------------------\n"
                            + "|0|Chọn bên dưới:";
                    this.arrMenu.add(new MenuInfo("Nhận quà theo cấp", 2100));
                    this.arrMenu.add(new MenuInfo("Mua điểm (100 thỏi vàng = 10 điểm)", 2104));
                    this.arrMenu.add(new MenuInfo("Xem bảng quà theo cấp", 2105));
                    if (d.passType == SeasonPass.PASS_FREE) {
                        this.arrMenu.add(new MenuInfo("Mua Pro (150.000 VND)", 2102));
                        this.arrMenu.add(new MenuInfo("Mua Master (200.000 VND)", 2103));
                    } else {
                        this.arrMenu.add(new MenuInfo("Đã kích hoạt gói " + passName, -1));
                    }
                    this.arrMenu.add(new MenuInfo("Đóng", 0));
                    this.openUIConfirm(npcId, null, null, -1);
                    break;
                }
                case 80: {
                    if (this.session.myCharz().mapTemplateId == 169) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DR_MY_1;
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 2482));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_DR_MY;
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 2481));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                case 81: {
                    if (this.session.myCharz().mapTemplateId == 14) {
                        if (Dragon.isEvent_Noel) {
                            this.session.myCharz().resetMenu();
                            this.chat = mResources.SAY1_EVENT_NOEL;
//                            this.arrMenu.add(new MenuInfo(mResources.EVENT_NOEL, 535));
//                            this.arrMenu.add(new MenuInfo(mResources.EVENT_MAP_NOEL, 2483));
                            this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                        }
                        // this.arrMenu.add(new MenuInfo(mResources.SHOP, 182));
//                        this.arrMenu.add(new MenuInfo(mResources.CUAHANG_DACBIET, 361));
                        // this.arrMenu.add(new MenuInfo(mResources.SHOP_HEAD, 183));
                        this.openUIConfirm(npcId, null, null, -1);
                    }
                    break;
                }
                default: {

                    break;
                }
            }
            this.session.service.meLoadPoint();
        }
    }

    public void openUIConfirm(int npcId, int select) {
        MenuInfo info;
        try {
            info = this.arrMenu.get(select);
            this.typeInfo = this.arrMenu.get(select).type;
            this.select = select;
            if (!info.excute()) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        if (info.index == -2003) {
            Admin.gI().select(this.session.myCharz(), info, select);
            return;
        }
        switch (this.typeInfo) {
            case 1: {
                this.typeInfo = 10;
                this.session.service.combine(mResources.SRC_PHALEHOA_3, mResources.SRC_PHALEHOA_4, -1);
                this.session.myCharz().nangcap = null;

                break;
            }
            case 2: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_BA_HAT_MIT3;
                this.arrMenu.add(new MenuInfo(mResources.USE_LUONG, 14));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 3: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_BA_HAT_MIT2;
                this.arrMenu.add(new MenuInfo(mResources.SHOP_BUA_1H, 15));
                this.arrMenu.add(new MenuInfo(mResources.SHOP_BUA_8H, 16));
                this.arrMenu.add(new MenuInfo(mResources.SHOP_BUA_30D, 17));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 4: {
                this.typeInfo = 19;
                this.session.service.combine(mResources.SRC_NANGCAP_3, mResources.SRC_NANGCAP_4, -1);
                break;
            }
            case 5: {
                this.typeInfo = 21;
                this.session.service.combine(mResources.SRC_NANGCAP_7, mResources.SRC_NANGCAP_8, -1);
                break;
            }
            case 6: {
                this.typeInfo = 23;
                this.session.service.combine(mResources.SRC_NANGCAP_5, mResources.SRC_NANGCAP_6, -1);
                break;
            }
            case 7: {
                this.typeInfo = 25;
                this.session.service.combine(mResources.SRC_NANGCAP_1, mResources.SRC_NANGCAP_2, -1);
                break;
            }
            case 8: {
                this.typeInfo = 27;
                this.session.service.combine(mResources.SRC_NANGCAP_9, mResources.SRC_NANGCAP_10, -1);
                break;
            }
            case 10: {
                break;
            }
            case 11: {
                Combine.EpNgoc(this.session.myCharz());
                this.typeInfo = 10;
                break;
            }
            case 12: {
                break;
            }
            case 13: {
                if (info.index == -1) {
                    Combine.DucLo(this.session.myCharz());
                } else {
                    batdaunangcap:
                    {
                        for (int i = 0; i < info.index; i++) {
                            int status = Combine.DucLo(this.session.myCharz());
                            if (status == 1) {
                                this.session.myCharz().addInfo1(5000, String.format(mResources.STR_NANGCAP1, i + 1));
                                break batdaunangcap;
                            }
                            if (status == 2) {
                                this.session.myCharz().addInfo1(5000, String.format(mResources.STR_NANGCAP2, i + 1));
                                break batdaunangcap;
                            }
                            if (status == 3) {
                                this.session.myCharz().addInfo1(5000, String.format(mResources.STR_NANGCAP3, i + 1));
                                break batdaunangcap;
                            }
                        }
                        this.session.myCharz().addInfo1(5000, String.format(mResources.STR_NANGCAP4, info.index));
                    }
                }
                // if (this.session.myCharz().arrItem != null) {
                // this.session.service.setCombineEff(this.session.myCharz().arrItem);
                // }
                this.typeInfo = 12;
                break;
            }
            case 14: {
                this.typeInfo = 12;
                this.session.service.combine(mResources.SRC_PHALEHOA, mResources.SRC_PHALEHOA_2, -1);
                break;
            }
            case 15: {
                this.session.myCharz().shopId = 13;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 16: {
                this.session.myCharz().shopId = 14;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 17: {
                this.session.myCharz().shopId = 15;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 18: {
                break;
            }
            case 19: {
                break;
            }
            case 20: {
                Combine.NangcCap(this.session.myCharz());
                this.typeInfo = 19;
                break;
            }
            case 21: {
                break;
            }
            case 22: {
                Combine.openOptionBongTai(this.session.myCharz());
                this.typeInfo = 21;
                break;
            }
            case 23: {
                break;
            }
            case 24: {
                Combine.NangcCapBongTai(this.session.myCharz());
                this.typeInfo = 23;
                break;
            }
            case 25: {
                break;
            }
            case 26: {
                Combine.NhapDa(this.session.myCharz());
                this.typeInfo = 25;
                break;
            }
            case 27: {
                break;
            }
            case 28: {
                Combine.NhapNgoc(this.session.myCharz());
                this.typeInfo = 27;
                break;
            }
            case 29: {
                if (this.session.myCharz().requestOpenUIItem(this.npcId, mResources.YOU_FRUIT_TRAY,
                        new int[]{1177, 1178, 1179, 1180, 1181, 1183}, new int[]{20, 20, 20, 20, 20, 3}, 0,
                        5000000, -1, false, -1)) {
                    this.session.myCharz().clientInput.openClientInput(14, mResources.QUANTITY_FRUIT_TRAY,
                            new String[]{mResources.QUANTITY_FRUIT_TRAY_KEY}, new int[]{0});
                }
                break;
            }
            case 30: {
                this.session.myCharz().duaPet();
                break;
            }
            case 31: {
                this.session.myCharz().shopId = 23;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 32: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_NOI_BANH_2;
                if (this.session.myCharz().myObj().arrBanh.isEmpty()) {
                    this.arrMenu.add(new MenuInfo(mResources.NAU_BANH_TET, 34));
                    this.arrMenu.add(new MenuInfo(mResources.NAU_BANH_CHUNG, 36));
                }
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 33: {
                if (this.session.myCharz().isNauBanhOK()) {
                    if (this.session.myCharz().checkBag(this.session.myCharz().itemNauBanhOK())) {
                        this.session.myCharz().addItemBag(0, this.session.myCharz().itemNauBanhOK());
                        this.session.myCharz().removeNauBanhOK();
                        this.session.myCharz().myObj().pointEvent++;
                    } else {
                        this.session.myCharz().addInfo1(mResources.BAG_FULL);
                    }
                }
                break;
            }
            case 34: {
                if (this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_REQUEST_BANH_TET_1,
                        new int[]{748, 749, 750, 751}, new int[]{10, 10, 10, 10}, 0, 5000000, -1, false, -1)) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_NOI_BANH_3;
                    this.arrMenu.add(new MenuInfo(mResources.AGREE, 35));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 35: {
                if (this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_REQUEST_BANH_TET_1,
                        new int[]{748, 749, 750, 751}, new int[]{10, 10, 10, 10}, 0, 5000000, -1, true, -1)) {
                    this.session.myCharz().addNauBanh(npcId, 180, 752);
                }
                break;
            }
            case 36: {
                if (this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_REQUEST_BANH_CHUNG_1,
                        new int[]{748, 749, 750, 751, 886}, new int[]{10, 10, 10, 10, 1}, 0, 5000000, -1, false,
                        -1)) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_NOI_BANH_4;
                    this.arrMenu.add(new MenuInfo(mResources.AGREE, 37));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 37: {
                if (this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_REQUEST_BANH_CHUNG_1,
                        new int[]{748, 749, 750, 751, 886}, new int[]{10, 10, 10, 10, 1}, 0, 5000000, -1, true,
                        -1)) {
                    this.session.myCharz().addNauBanh(npcId, 300, 753);
                }
                break;
            }
            case 38: {
                if (this.session.myCharz().requestOpenUIItem(this.npcId, mResources.YOU_GIFT_TET,
                        new int[]{1182, 1184}, new int[]{1, 1}, 0, -1, -1, false, -1)) {
                    this.session.myCharz().clientInput.openClientInput(15, mResources.QUANTITY_GIFT_TET,
                            new String[]{mResources.QUANTITY_GIFT_TET_KEY}, new int[]{0});
                }
                break;
            }
            case 39: {
                if (this.session.myCharz().clan != null) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_QUY_LAO_KAME;
                    this.arrMenu.add(new MenuInfo(mResources.GO_ZONE_BANG, 40));
                    if (this.session.myCharz().clanMember.role == 0) {
                        this.arrMenu.add(new MenuInfo(mResources.GIAI_TAN_BANG, 41));
                    }
                    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 40: {
                if (this.session.myCharz().clan != null) {
                    ZoneMap zoneBag = this.session.myCharz().clan.getMap(153).getZone(this.session.myCharz());
                    if (zoneBag != null) {
                        this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                        zoneBag.join(this.session.myCharz(), 0, 160, 432);
                    } else {
                        this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                    }
                }
                break;
            }
            case 41: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUY_LAO_KAME_2;
                this.arrMenu.add(new MenuInfo(mResources.OK, 42));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 42: {
                if (this.session.myCharz().clan != null) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().clan.isWork()) {
                        this.session.myCharz().addInfo1(mResources.NOT_SET_CLAN);
                    } else {
                        this.session.myCharz().clan.dissolution();
                    }
                }
                break;
            }
            case 43: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUY_LAO_KAME_1;
                this.arrMenu.add(new MenuInfo(mResources.HOC_KY_NANG, 503));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 44: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUY_LAO_KAME_5;
                this.arrMenu.add(new MenuInfo(mResources.TOP100_BAG, 45));
                if (this.session.myCharz().clan != null) {
                    this.arrMenu.add(new MenuInfo(mResources.THANH_TICH_BANG, 46));
                    if (this.session.myCharz().clan.khobau != null) {
                        this.chat = String.format(mResources.SAY_QUY_LAO_KAME_9,
                                this.session.myCharz().clan.khobau.level);
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 49));
                    } else {
                        this.arrMenu.add(new MenuInfo(mResources.SELECT_LEVEL_3, 47));
                    }
                }
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 45: {
                Rank.getRank(5).loadRank();
                this.session.service.top(Rank.getRank(5));
                break;
            }
            case 46: {
                break;
            }
            case 47: {
                if (this.session.myCharz().cPower < 200000000) {
                    this.session.service.chatTHEGIOI(mResources.EMPTY,
                            String.format(mResources.YEU_CAU_SUC_MANH, Util.gI().numberTostring(200000000)), null, 0);
                } else if (this.session.myCharz().clan == null || this.session.myCharz().clanMember.role != 0) {
                    this.session.service.openUISay(npcId, mResources.INVATE_BANG_CHU, -1);
                } else {
                    this.session.myCharz().clientInput.openClientInput(7, mResources.SELECT_LEVEL_2,
                            new String[]{mResources.LEVEL}, new int[]{0});
                }
                break;
            }
            case 48: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clanMember.role == 0) {
                    if (this.session.myCharz().clan.khobau == null && (byte) this.session.myCharz().valueById(0) <= 0) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUY_LAO_KAME_10;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if (this.session.myCharz().cPower < 200000000) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY,
                                String.format(mResources.YEU_CAU_SUC_MANH, Util.gI().numberTostring(200000000)), null,
                                0);
                    } else if ((System.currentTimeMillis() / 1000)
                            - this.session.myCharz().clanMember.joinTime < 3600) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY,
                                String.format(mResources.GIA_NHAP, Util.gI().getStrTime(3600000L)), null, 0);
                    } else {
                        // Check ban do kho bau
                        Item it = this.session.myCharz().getItemBag(611);
                        if (it == null) {
                            this.session.service.chatTHEGIOI(mResources.EMPTY,
                                    String.format(mResources.NEED_ITEM, ItemTemplate.get((short) 611).name), null, 0);
                            break;
                        }
                        this.session.myCharz().useItemBag(it.indexUI, 1);

                        // Init
                        KhoBau kb = new KhoBau();
                        kb.initKhoBau(this.session.myCharz().clan, this.session.myCharz().clan.selectLevel);
                        Server.gI().add(kb);
                        ZoneMap zone = kb.maps.get(0).getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().transPort(-1, 1, zone, 0, 0, 120, 150);
                            this.session.myCharz().setText(0, mResources.KHO_BAU, kb.miliTime / 1000, 0, 0);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                        if (!kb.isJoin(this.session.myCharz().playerId)) {
                            this.session.myCharz().setValue(0, (byte) ((byte) this.session.myCharz().valueById(0) - 1));
                        }
                        kb.join(this.session.myCharz().playerId);
                    }
                }
                break;
            }
            case 49: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.khobau != null) {
                    KhoBau kb = this.session.myCharz().clan.khobau;
                    if (!kb.isJoin(this.session.myCharz().playerId)
                            && (byte) this.session.myCharz().valueById(0) <= 0) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUY_LAO_KAME_10;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if (this.session.myCharz().cPower < 200000000) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY,
                                String.format(mResources.YEU_CAU_SUC_MANH, Util.gI().numberTostring(200000000)), null,
                                0);
                    } else if ((System.currentTimeMillis() / 1000)
                            - this.session.myCharz().clanMember.joinTime < 3600) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY,
                                String.format(mResources.GIA_NHAP, Util.gI().getStrTime(3600000L)), null, 0);
                    } else {
                        if (!kb.isJoin(this.session.myCharz().playerId)) {
                            // Check ban do kho bau
                            // Item it = this.session.myCharz().getItemBag(611);
                            // if (it == null) {
                            // this.session.service.chatTHEGIOI(mResources.EMPTY,
                            // String.format(mResources.NEED_ITEM, GameData.itemTemplates[611].name), null,
                            // 0);
                            // return;
                            // }
                            // this.session.myCharz().useItemBag(it.indexUI, 1);
                            kb.join(this.session.myCharz().playerId);
                            this.session.myCharz().setValue(0, (byte) ((byte) this.session.myCharz().valueById(0) - 1));
                        }
                        ZoneMap zone = kb.maps.get(0).getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().transPort(-1, 1, zone, 0, 0, 120, 150);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                    }
                }
                break;
            }
            case 50: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_QUY_LAO_KAME_11, 50);
                this.arrMenu.add(new MenuInfo(mResources.AGREE, 51));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 51: {
                this.session.myCharz().changePoint();
                break;
            }
            case 52: {
                Rank.getRank(3).loadRank();
                this.session.service.top(Rank.getRank(3));
                break;
            }
            case 53: {
                break;
            }

            case 54: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUY_LAO_KAME_20;
                this.arrMenu.add(new MenuInfo(mResources.FRUIT_TRAY, 29));
                this.arrMenu.add(new MenuInfo(mResources.GIFT_TET, 38));
                this.arrMenu.add(new MenuInfo(mResources.TOP100_OPEN_GIFT_TET, 52));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 55: {
                this.session.myCharz().goPotaufe();
                break;
            }
            case 56: {
                this.session.myCharz().goTramTauTraiDat();
                break;
            }
            case 57: {
                this.session.myCharz().goTramTauNamek();
                break;
            }
            case 58: {
                this.session.myCharz().goTramTauXayda();
                break;
            }
            case 59: {
                this.session.service.openUISay(npcId, mResources.HD_COMMESON, -1);
                break;
            }
            case 60: {
                if (this.session.myCharz().myPet3 != null) {
                } else if (this.session.myCharz().zoneMap.isHaveBoss(129)) {
                    this.session.myCharz().resetMenu();
                    this.chat = String.format(mResources.SAY_POTAGE2,
                            this.session.myCharz().findBossInMapById(129).cName);
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                } else if (this.session.myCharz().myObj().isPotage) {
                    this.session.myCharz().addInfo1(mResources.WAIT_DAY);
                } else if (this.session.myCharz().myObj().lastNB > System.currentTimeMillis()) {
                    this.session.myCharz().addInfo1(String.format(mResources.TIME_WITE,
                            Util.gI().getStrTime(this.session.myCharz().myObj().lastNB - System.currentTimeMillis())));
                } else {
                    this.session.myCharz().myObj().lastNB = System.currentTimeMillis() + 300000;
                    // this.session.myCharz().myObj().isPotage = true;
                    Char bot = Player.addBoss(129, 5, -1, -1, true, 400, 336, null, -1, -1);
                    // set clone
                    bot.setClone(this.session.myCharz());
                    this.session.myCharz().setItem(5222, (this.session.myCharz().timePkMyPet = 300000) / 1000, 0, 0);
                    this.session.myCharz().zoneMap.join(bot, 0, -1, -1);
                }
                break;
            }
            case 61: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG2;
                this.arrMenu.add(new MenuInfo(mResources.CHANGE, 62));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 62: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 4664)) {
                    if (this.session.myCharz().getDuaHau(50, 4664).second - ((System.currentTimeMillis() / 1000L)
                            - this.session.myCharz().getDuaHau(50, 4664).last) <= 0) {
                        this.session.myCharz().tMabu(4664);
                        this.session.myCharz().zoneMap.removeNpc(50);
                        Player.mabuInfo(this.session.myCharz());
                    }
                }
                break;
            }
            case 63: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG3;
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.arrMenu.add(new MenuInfo(mResources.OK, 64));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 64: {
                if (this.session.myCharz().isHaveDuaHau(50, 4664)) {
                    this.session.myCharz().tMabu(4664);
                    this.session.myCharz().zoneMap.removeNpc(50);
                }
                break;
            }
            case 65: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG4;
                this.arrMenu.add(new MenuInfo(mResources.CHANGE, 66));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 66: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 4664)) {
                    if (this.session.myCharz().xu < 2000000000L) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, String.format(mResources.CONTHIEU_VANG,
                                Util.gI().getStrNumber(2000000000L - this.session.myCharz().xu)), null, 0);
                    } else {
                        this.session.myCharz().updateXu(-2000000000L, 2);
                        this.session.myCharz().tMabu(4664);
                        this.session.myCharz().zoneMap.removeNpc(50);
                        Player.mabuInfo(this.session.myCharz());
                    }
                }
                break;
            }
            case 67: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG7;
                this.arrMenu.add(new MenuInfo(mResources.HATCH, 68));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 68: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                    if (this.session.myCharz().requestOpenUIItem(this.npcId, mResources.SAY_QUA_TRUNG12,
                            new int[]{700, 699, 457}, new int[]{200, 5, 15}, 0, -1, -1, true, 1)) {
                        if (this.session.myCharz().getDuaHau(50, 6546).second - ((System.currentTimeMillis() / 1000L)
                                - this.session.myCharz().getDuaHau(50, 6546).last) <= 0) {
                            this.session.myCharz().duahaus.remove(this.session.myCharz().getDuaHau(50, 6546));
                            this.session.myCharz().zoneMap.removeNpc(50);
                            // Eff
                            this.session.myCharz().eggEffHide(363, 240);
                            // Add Item
                            int templateId = Util.gI().nextInt(972, 977);
                            Item item = new Item(templateId, false, 1, ItemOption.getOption(templateId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            // option 1
                            int option1 = new int[]{0, 6, 7}[Util.gI().nextInt(3)];
                            if (option1 == 0) {
                                item.options.add(new ItemOption(0, Util.gI().nextInt(150, 300)));
                            } else {
                                item.options.add(new ItemOption(option1, Util.gI().nextInt(1500, 3000)));
                            }
                            // option 2
                            int option2 = new int[]{103, 94, 77, 50, 108}[Util.gI().nextInt(5)];
                            item.options.add(new ItemOption(option2, 1));
                            this.session.myCharz().addItemBag(0, item);
                        }
                    }
                }
                break;
            }
            case 69: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG6;
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.arrMenu.add(new MenuInfo(mResources.OK, 70));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 70: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                    this.session.myCharz().duahaus.remove(this.session.myCharz().getDuaHau(50, 6546));
                    this.session.myCharz().zoneMap.removeNpc(50);
                    // Eff
                    this.session.myCharz().eggEffHide(363, 240);
                }
                break;
            }
            case 71: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG8;
                this.arrMenu.add(new MenuInfo(mResources.ADD_LINHLUC_1, 72));
                this.arrMenu.add(new MenuInfo(mResources.ADD_LINHLUC_2, 74));
                this.arrMenu.add(new MenuInfo(mResources.ADD_LINHLUC_3, 76));
                this.arrMenu.add(new MenuInfo(mResources.ADD_LINHLUC_4, 776));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                // this.session.service.addEffectServer(5, 3, 19,
                // this.session.myCharz().zoneMap.findNPCInMap(50).cx,
                // this.session.myCharz().zoneMap.findNPCInMap(50).cy, 1);
                break;
            }
            case 72: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG9;
                this.arrMenu.add(new MenuInfo(mResources.OK, 73));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 73: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                    if (this.session.myCharz().requestOpenUIItem(this.session.myCharz().menuBoard.npcId,
                            mResources.SAY_QUA_TRUNG12, new int[]{700}, new int[]{2}, 0, 50000000, -1, true, 1)) {
                        this.session.myCharz().getDuaHau(50, 6546).second -= 3600;
                        this.session.myCharz().eggEffStatus(363, 240, this.session.myCharz().getDuaHau(50, 6546).duahau,
                                this.session.myCharz().getDuaHau(50, 6546).duaHauIndex,
                                (int) (this.session.myCharz().getDuaHau(50, 6546).second
                                - ((System.currentTimeMillis() / 1000L)
                                - this.session.myCharz().getDuaHau(50, 6546).last)));
                    }
                }
                break;
            }
            case 74: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG10;
                this.arrMenu.add(new MenuInfo(mResources.OK, 75));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 75: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                    if (this.session.myCharz().requestOpenUIItem(this.session.myCharz().menuBoard.npcId,
                            mResources.SAY_QUA_TRUNG12, new int[]{700}, new int[]{15}, 0, 1000000000, -1, true,
                            1)) {
                        this.session.myCharz().getDuaHau(50, 6546).second -= 36000;
                        this.session.myCharz().eggEffStatus(363, 240, this.session.myCharz().getDuaHau(50, 6546).duahau,
                                this.session.myCharz().getDuaHau(50, 6546).duaHauIndex,
                                (int) (this.session.myCharz().getDuaHau(50, 6546).second
                                - ((System.currentTimeMillis() / 1000L)
                                - this.session.myCharz().getDuaHau(50, 6546).last)));
                    }
                }
                break;
            }
            case 76: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG11;
                this.arrMenu.add(new MenuInfo(mResources.OK, 77));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 776: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUA_TRUNG13;
                this.arrMenu.add(new MenuInfo(mResources.OK, 777));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 77: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                    if (this.session.myCharz().requestOpenUIItem(this.session.myCharz().menuBoard.npcId,
                            mResources.SAY_QUA_TRUNG12, new int[]{700, 457}, new int[]{30, 4}, 0, -1, -1, true,
                            1)) {
                        this.session.myCharz().getDuaHau(50, 6546).second -= 86400;
                        this.session.myCharz().eggEffStatus(363, 240, this.session.myCharz().getDuaHau(50, 6546).duahau,
                                this.session.myCharz().getDuaHau(50, 6546).duaHauIndex,
                                (int) (this.session.myCharz().getDuaHau(50, 6546).second
                                - ((System.currentTimeMillis() / 1000L)
                                - this.session.myCharz().getDuaHau(50, 6546).last)));
                    }
                }
                break;
            }
            case 777: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().isHaveDuaHau(50, 6546)) {
                    if (this.session.myCharz().requestOpenUIItem(this.session.myCharz().menuBoard.npcId,
                            mResources.SAY_QUA_TRUNG12, new int[]{700, 457}, new int[]{30, 4}, 0, -1, -1, true,
                            1)) {
                        this.session.myCharz().getDuaHau(50, 6546).second -= 432000;
                        this.session.myCharz().eggEffStatus(363, 240, this.session.myCharz().getDuaHau(50, 6546).duahau,
                                this.session.myCharz().getDuaHau(50, 6546).duaHauIndex,
                                (int) (this.session.myCharz().getDuaHau(50, 6546).second
                                - ((System.currentTimeMillis() / 1000L)
                                - this.session.myCharz().getDuaHau(50, 6546).last)));
                    }
                }
                break;
            }
            case 78: {
                this.session.myCharz().goDaoKame();
                break;
            }
            case 79: {
                if (this.session.myCharz().clan == null || this.session.myCharz().clanMember.role != 0) {
                    this.session.myCharz().addInfo1(mResources.INVATE_BANG_CHU);
                } else if (this.session.myCharz().clan.lastChangeShortName > System.currentTimeMillis()) {
                    this.session.myCharz().addInfo1(String.format(mResources.TIME_WAIT_RONG, Util.gI()
                            .getStrTime(this.session.myCharz().clan.lastChangeShortName - System.currentTimeMillis())));
                } else {
                    this.session.myCharz().clan.setShortName(this.session.myCharz(),
                            Util.gI().nextString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", Util.gI().nextInt(2, 4)));
                }
                break;
            }
            case 80: {
                if (this.session.myCharz().clan == null || this.session.myCharz().clanMember.role != 0) {
                    this.session.myCharz().addInfo1(mResources.INVATE_BANG_CHU);
                } else if (this.session.myCharz().clan.lastChangeShortName > System.currentTimeMillis()) {
                    this.session.myCharz().addInfo1(String.format(mResources.TIME_WAIT_RONG, Util.gI()
                            .getStrTime(this.session.myCharz().clan.lastChangeShortName - System.currentTimeMillis())));
                } else {
                    this.session.myCharz().clientInput.openClientInput(13, mResources.NHAP_SHORT_NAME,
                            new String[]{mResources.KEY_SHORT_NAME}, new int[]{1});
                }
                break;
            }
            case 81: {
                this.session.myCharz().goSieuThi();
                break;
            }
            case 82: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().usePetFollowz == null) {
                    this.session.myCharz().addInfo1(mResources.YOU_USE_PETZ);
                } else if (this.session.myCharz().usePetFollowz.getParamOption(72) >= 9) {
                    this.session.myCharz().addInfo1(String.format(mResources.USE_PETZ_LEVEL,
                            this.session.myCharz().usePetFollowz.getParamOption(72)));
                } else {
                    int[] arr_percent = new int[]{100, 50, 30, 20, 10, 5, 4, 3, 2, 1};
                    this.session.myCharz().resetMenu();
                    this.chat = String.format(mResources.SAY_DR_BRIEF_4,
                            this.session.myCharz().usePetFollowz.template.name,
                            this.session.myCharz().usePetFollowz.getParamOption(72) + 1,
                            this.session.myCharz().usePetFollowz.template.description,
                            this.session.myCharz().usePetFollowz.getParamOption(72) + 1,
                            this.session.myCharz().usePetFollowz.getParamOption(72) + 1,
                            arr_percent[this.session.myCharz().usePetFollowz.getParamOption(72) + 1]);
                    this.arrMenu.add(new MenuInfo(mResources.UP_PETZ, 83));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 83: {

                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().usePetFollowz == null) {
                    this.session.myCharz().addInfo1(mResources.YOU_USE_PETZ);
                } else if (this.session.myCharz().usePetFollowz.getParamOption(72) >= 9) {
                    this.session.myCharz().addInfo1(String.format(mResources.USE_PETZ_LEVEL,
                            this.session.myCharz().usePetFollowz.getParamOption(72)));
                } else {
                    if (this.session.myCharz().requestOpenUIItem(npcId,
                            String.format(mResources.SAY_DR_BRIEF_5, this.session.myCharz().usePetFollowz.template.name,
                                    this.session.myCharz().usePetFollowz.getParamOption(72) + 1),
                            new int[]{701, 969},
                            new int[]{this.session.myCharz().usePetFollowz.getParamOption(72) + 1,
                                this.session.myCharz().usePetFollowz.getParamOption(72) + 1},
                            0, -1, -1, true, -1)) {
                        boolean flag = Util.gI().nextInt(100) <= new int[]{100, 50, 30, 20, 10, 5, 4, 3, 2,
                            1}[this.session.myCharz().usePetFollowz.getParamOption(72) + 1];
                        if (!flag) {
                            this.session.myCharz().addInfo1(mResources.FAILD);
                        } else {
                            // add ko co tao moi
                            if (this.session.myCharz().usePetFollowz.isHaveOption(72)) {
                                this.session.myCharz().usePetFollowz.getOption(72).param++;
                            } else {
                                this.session.myCharz().usePetFollowz.options.add(new ItemOption(72, 1));
                            }
                            // add
                            if (!this.session.myCharz().usePetFollowz.options.isEmpty()) {
                                int numPetz = 0;
                                for (; numPetz < this.session.myCharz().usePetFollowz.options.size();) {
                                    ItemOption optionPetz = this.session.myCharz().usePetFollowz.options.get(numPetz);
                                    if (optionPetz.optionTemplate.id == 0 || optionPetz.optionTemplate.id == 6
                                            || optionPetz.optionTemplate.id == 7) {
                                        optionPetz.param = (int) (optionPetz.param + (optionPetz.param * 0.2F));
                                    }
                                    if (optionPetz.optionTemplate.id == 50 || optionPetz.optionTemplate.id == 77
                                            || optionPetz.optionTemplate.id == 94 || optionPetz.optionTemplate.id == 103
                                            || optionPetz.optionTemplate.id == 108) {
                                        optionPetz.param = optionPetz.param + 1;
                                    }
                                    numPetz++;
                                }
                            }
                            this.session.myCharz()
                                    .addInfo1(String.format(mResources.COMPLATE_UP_PETZ,
                                            this.session.myCharz().usePetFollowz.template.name,
                                            this.session.myCharz().usePetFollowz.getParamOption(72)));
                            this.session.service.Bag(this.session.myCharz().arrItemBag);
                        }
                    }
                }
            }
            case 84: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItemBag[this.session.myCharz().arrItem[0].indexUI] == this.session
                        .myCharz().arrItem[0]
                        && this.session.myCharz().arrItem[0].template.id == 970) {
                    this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                    this.session.myCharz().addDuaHau(50, new int[]{6546}, 0, 2592000, 6546);
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 85: {
                if (this.session.myCharz().mapTemplateId != 5) {
                    this.session.myCharz().goManorClan();
                }
                break;
            }
            case 86: {
                if (this.session.myCharz().clan != null
                        && Util.gI().getDayGap(this.session.myCharz().clan.lastReport) > 0) {
                    this.session.myCharz().clan.lastReport = System.currentTimeMillis();
                }
                break;
            }
            case 87: {
                this.session.service.openUISay(npcId, mResources.HD_DESTRONGAS1 + mResources.HD_DESTRONGAS2, -1);
                break;
            }
            case 88: {
                Rank.getRank(0).loadRank();
                this.session.service.top(Rank.getRank(0));
                break;
            }
            case 89: {
                break;
            }
            case 90: {
                if (this.session.myCharz().clan == null || this.session.myCharz().clanMember.role != 0) {
                    this.session.service.openUISay(npcId, mResources.INVATE_BANG_CHU, -1);
                } else {
                    this.session.myCharz().clientInput.openClientInput(3, mResources.SELECT_LEVEL,
                            new String[]{mResources.LEVEL}, new int[]{0});
                }
            }
            case 91: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.destronGas != null) {
                    KhiHuyDiet khiga = this.session.myCharz().clan.destronGas;
                    if (!khiga.isJoin(this.session.myCharz().playerId)
                            && (byte) this.session.myCharz().valueById(1) <= 0) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_MR_POPO_4;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if ((System.currentTimeMillis() / 1000)
                            - this.session.myCharz().clanMember.joinTime < 3600) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY,
                                String.format(mResources.GIA_NHAP, Util.gI().getStrTime(3600000L)), null, 0);
                    } else {
                        if (!khiga.isJoin(this.session.myCharz().playerId)) {
                            this.session.myCharz().setValue(1, (byte) ((byte) this.session.myCharz().valueById(1) - 1));
                            khiga.join(this.session.myCharz().playerId);
                        }
                        ZoneMap zone = this.session.myCharz().clan.destronGas.maps.get(0)
                                .getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().transPort(-1, 1, zone, 0, 0, 75, 366);
                            this.session.myCharz().updateTask(2, 1);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                    }
                }
                break;
            }
            case 92: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clanMember.role == 0) {
                    if (this.session.myCharz().clan.destronGas == null
                            && (byte) this.session.myCharz().valueById(1) <= 0) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_MR_POPO_4;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if ((System.currentTimeMillis() / 1000)
                            - this.session.myCharz().clanMember.joinTime < 3600) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY,
                                String.format(mResources.GIA_NHAP, Util.gI().getStrTime(3600000L)), null, 0);
                    } else {
                        KhiHuyDiet gas = new KhiHuyDiet();
                        gas.initDestronGas(this.session.myCharz().clan, this.session.myCharz().clan.selectLevel);
                        this.session.myCharz().setValue(1, (byte) ((byte) this.session.myCharz().valueById(1) - 1));
                        Server.gI().add(gas);
                        ZoneMap zone = gas.maps.get(0).getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().transPort(-1, 1, zone, 0, 0, 75, 366);
                            this.session.myCharz().updateTask(2, 1);
                            this.session.myCharz().setText(0, mResources.KHI_GAS, 1800, 0, 0);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                        gas.join(this.session.myCharz().playerId);
                    }
                }
                break;
            }
            case 93: {
                break;
            }
            case 94: {
                this.session.myCharz().shopId = 16;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 95: {
                this.session.myCharz().shopId = 24;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 96: {
                if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].isItemPetFollowz()) {
                    if (this.session.myCharz().timeUsePet > 0) {
                        this.session.myCharz().addInfo1(String.format(mResources.DELAY_THAO_TAC,
                                Util.gI().getStrTime(this.session.myCharz().timeUsePet)));
                    } else {
                        if (this.session.myCharz().checkPetFollowz(this.session.myCharz().arrItem[0])) {
                            this.session.myCharz().checkClearPetFollowz(this.session.myCharz().arrItem[0]);
                        } else {
                            this.session.myCharz().usePetFollowz(this.session.myCharz().arrItem[0]);
                        }
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 97: {
                if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].isItemPetFollowz()) {
                    if (this.session.myCharz().timeUsePet > 0) {
                        this.session.myCharz().addInfo1(String.format(mResources.DELAY_THAO_TAC,
                                Util.gI().getStrTime(this.session.myCharz().timeUsePet)));
                    } else {
                        if (this.session.myCharz().checkPetFollowz(this.session.myCharz().arrItem[0])) {
                            this.session.myCharz().checkClearPetFollowz(this.session.myCharz().arrItem[0]);
                        } else {
                            this.session.myCharz().myPetz().usePetFollowz(this.session.myCharz().arrItem[0]);
                        }
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 98: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    if (this.session.myCharz().requestOpenUIItem(npcId, mResources.YEU_CAU, new int[]{457},
                            new int[]{52}, 0, -1, -1, true, 1)) {
                        this.session.myCharz().addItemBag(0, new Item(700, false, 99, ItemOption.getOption(700, 0, 0),
                                mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                    }
                }
                break;
            }
            case 99: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    if (this.session.myCharz().requestOpenUIItem(npcId, mResources.YEU_CAU, new int[]{457},
                            new int[]{42}, 0, -1, -1, true, 1)) {
                        this.session.myCharz().addItemBag(0, new Item(969, false, 99, ItemOption.getOption(969, 0, 0),
                                mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                    }
                }
                break;
            }
            case 100: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    if (this.session.myCharz().getLuong() < 10) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 10 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().updateLuong(-10, 2);
                        if (this.session.myCharz().itemNamekBall != null) {
                            this.session.myCharz().throwNamekBall();
                        }
                        BallRada ballRada = null;
                        if (this.session.myCharz().goNamekBallStar == 1) {
                            ballRada = BallRada.getById(353);
                        }
                        if (this.session.myCharz().goNamekBallStar == 2) {
                            ballRada = BallRada.getById(354);
                        }
                        if (this.session.myCharz().goNamekBallStar == 3) {
                            ballRada = BallRada.getById(355);
                        }
                        if (this.session.myCharz().goNamekBallStar == 4) {
                            ballRada = BallRada.getById(356);
                        }
                        if (this.session.myCharz().goNamekBallStar == 5) {
                            ballRada = BallRada.getById(357);
                        }
                        if (this.session.myCharz().goNamekBallStar == 6) {
                            ballRada = BallRada.getById(358);
                        }
                        if (this.session.myCharz().goNamekBallStar == 7) {
                            ballRada = BallRada.getById(359);
                        }
                        if (ballRada != null) {
                            this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                            ballRada.zoneMap.join(this.session.myCharz(), 0, ballRada.x, ballRada.y);
                        }
                    }
                }
                break;
            }
            case 101: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    if (this.session.myCharz().xu < 100000) {
                        this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                                Util.gI().getStrNumber(100000 - this.session.myCharz().xu)));
                    } else {
                        this.session.myCharz().updateXu(-100000, 1);
                        if (this.session.myCharz().itemNamekBall != null) {
                            this.session.myCharz().throwNamekBall();
                        }
                        BallRada ballRada = null;
                        if (this.session.myCharz().goNamekBallStar == 1) {
                            ballRada = BallRada.getById(353);
                        }
                        if (this.session.myCharz().goNamekBallStar == 2) {
                            ballRada = BallRada.getById(354);
                        }
                        if (this.session.myCharz().goNamekBallStar == 3) {
                            ballRada = BallRada.getById(355);
                        }
                        if (this.session.myCharz().goNamekBallStar == 4) {
                            ballRada = BallRada.getById(356);
                        }
                        if (this.session.myCharz().goNamekBallStar == 5) {
                            ballRada = BallRada.getById(357);
                        }
                        if (this.session.myCharz().goNamekBallStar == 6) {
                            ballRada = BallRada.getById(358);
                        }
                        if (this.session.myCharz().goNamekBallStar == 7) {
                            ballRada = BallRada.getById(359);
                        }
                        if (ballRada != null) {
                            this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                            ballRada.zoneMap.join(this.session.myCharz(), 0, ballRada.x, ballRada.y);
                        }
                    }
                }
                break;
            }
            case 102: {
                this.session.myCharz().shopId = 11;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 103: {
                this.session.service.openUISay(npcId, mResources.HD_NAMEK_BALL, -1);
                break;
            }
            case 104: {
                if (this.session.myCharz().itemNamekBall != null) {
                    if (!NamekBall.gI().isDendeRanh) {
                        this.session.service.openUISay(npcId, mResources.DENDE_RANH, -1);
                    } else if (this.session.myCharz().itemNamekBall.template.id != 353) {
                        this.session.service.openUISay(npcId, mResources.INVITE_ONE_START_NAMEK, -1);
                    } else {
                        if (this.session.myCharz().checkFullNamekBall()) {
                            if (BallRada.timeCleanBall == -1) {
                                BallRada.timeCleanBall = System.currentTimeMillis() + 599999L;
                                // BallRada.timeCleanBall = System.currentTimeMillis() + 10000L;
                                this.session.service.openUISay(npcId, String.format(mResources.CLEAN_BALL,
                                        Util.gI().getStrTime(BallRada.timeCleanBall - System.currentTimeMillis())), -1);
                            } else {
                                if (BallRada.timeCleanBall > System.currentTimeMillis()) {
                                    this.session.service.openUISay(npcId,
                                            String.format(mResources.CLEAN_BALL, Util.gI()
                                                    .getStrTime(BallRada.timeCleanBall - System.currentTimeMillis())),
                                            -1);
                                } else {
                                    if (CallDragon.gI().isRongThanXuatHien) {
                                        this.session.service.openUISay(npcId, mResources.HAVE_CALL_RONG, -1);
                                    } else {
                                        NamekBall.gI().thungoc();
                                        this.session.service.openUISay(npcId, mResources.CALL_NAMEK_BALL, -1);
                                        CallDragon.gI().setup(this.session.myCharz(), this.session.myCharz().zoneMap, 1,
                                                2);
                                    }
                                }
                            }
                        } else {
                            this.session.service.openUISay(npcId, mResources.NOT_FULL_BALL, -1);
                        }
                    }
                }
                break;
            }
            case 105: {
                if (((MenuR) info).typeM == 1) {
                    if (CallDragon.gI().status == 0) {
                        CallDragon.gI().status = 1;
                    } else if (CallDragon.gI().status == 1) {
                        CallDragon.gI().status = 0;
                    }
                    CallDragon.gI().openmenuRong(this.session.myCharz());
                } else if (select >= 0 && select < CallDragon.gI().menuR.size()) {
                    if (CallDragon.gI().isRongThanXuatHien
                            && CallDragon.gI().playerId == this.session.myCharz().playerId) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_RONG_THAN_3;
                        this.arrMenu.add(
                                new MenuInfo(CallDragon.gI().menuR.get(CallDragon.gI().select = select).strMenu, 106));
                        this.arrMenu.add(new MenuInfo(mResources.REFUSE, 107));
                        this.openUIConfirm(npcId, null, null, this.avatar);
                    }
                }
                break;
            }
            case 106: {
                CallDragon.gI().finishCall(this.session.myCharz());
                break;
            }
            case 107: {
                CallDragon.gI().openmenuRong(this.session.myCharz());
                break;
            }
            case 108: {
                break;
            }
            case 109: {
                CallDragon.gI().dragonControl(this.session.myCharz(), this.session.myCharz().zoneMap);
                break;
            }
            case 110: {
                this.session.myCharz().goPlanetKaio();
                break;
            }
            case 111: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THUONG_DE_2;
                this.arrMenu.add(new MenuInfo(mResources.TOP_100, 112));
                this.arrMenu.add(new MenuInfo(mResources.VQ_GOLD, 113));
                this.arrMenu.add(new MenuInfo(mResources.VQ_NGOC, 114));

                if (!this.session.myCharz().arrItemMore.isEmpty()) {
                    this.arrMenu.add(new MenuInfo(
                            String.format(mResources.RUONG_PHU, this.session.myCharz().arrItemMore.size()), 115));
                }
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 112: {
                Rank.getRank(6).loadRank();
                this.session.service.top(Rank.getRank(6));
                break;
            }
            case 113: {
                this.session.myCharz().typeLucky = 0;
                this.session.service.sendLuckyRound(this.session.myCharz().typeLucky,
                        LuckyRound.gI().price[this.session.myCharz().typeLucky], 821);
                break;
            }
            case 114: {
                if (this.session.myCharz().cPower < 150000000L) {
                    this.session.myCharz().addInfo1(String.format(mResources.YEU_CAU_SUC_MANH,
                            Util.gI().numberTostring(150000000L - this.session.myCharz().cPower)));
                } else {
                    this.session.myCharz().typeLucky = 1;
                    this.session.service.sendLuckyRound(this.session.myCharz().typeLucky,
                            LuckyRound.gI().price[this.session.myCharz().typeLucky], 821);
                }
                break;
            }
            case 115: {
                this.session.myCharz().isItemMore = 1;
                this.session.service.openItemMore(this.session.myCharz().arrItemMore);
                break;
            }
            case 116: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_EVENT_GIRL1;
                this.arrMenu.add(new MenuInfo(mResources.FLOWER_BLUE, 117));
                this.arrMenu.add(new MenuInfo(mResources.FLOWER_POT_BLUE, 119));
                this.arrMenu.add(new MenuInfo(mResources.TOP10_EVENT1, 129));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 117: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_FLOWER_BLUE;
                this.arrMenu.add(new MenuInfo(mResources.OK, 118));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 118: {
                this.session.myCharz().clientInput.openClientInput(16, mResources.QUANTITY_FLOWER_BLUE,
                        new String[]{mResources.QUANTITY_FLOWER_BLUE_KEY}, new int[]{0});
                break;
            }
            case 119: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_FLOWER_POT_BLUE;
                this.arrMenu.add(new MenuInfo(mResources.OK, 120));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 120: {
                this.session.myCharz().clientInput.openClientInput(17, mResources.QUANTITY_FLOWER_POT_BLUE,
                        new String[]{mResources.QUANTITY_FLOWER_POT_BLUE_KEY}, new int[]{0});
                break;
            }
            case 121: {
                this.session.myCharz().shopId = 10;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 122: {
                this.session.myCharz().tangBongHoaXanh();
                break;
            }
            case 123: {
                this.session.myCharz().tangChauHoaXanh();
                break;
            }
            case 124: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.REQUEST_TANG_HOA1, Server.gI().nSendFl1,
                        this.session.myCharz().myObj().nPointTang1, Server.gI().timeTangGift1 / 60000);
                this.arrMenu.add(new MenuInfo(mResources.TANG_FL1, 125));
                this.arrMenu.add(new MenuInfo(mResources.TANG_FL10, 126));
                this.arrMenu.add(new MenuInfo(mResources.TANG_FL20, 127));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 125: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangBongHoa1(1);
                }
                break;
            }
            case 126: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangBongHoa1(10);
                }
                break;
            }
            case 127: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangBongHoa1(99);
                }
                break;
            }
            case 128: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 401) {
                    if (this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        int cg = this.session.myCharz().myPetz().cgender + 1;
                        if (cg > 2) {
                            cg = 0;
                        }
                        Player.petInfo(this.session.myCharz(), cg);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 129: {
                Rank.getRank(3).loadRank();
                this.session.service.top(Rank.getRank(3));
                break;
            }
            case 130: {
                this.session.myCharz().goDaiHoiVoThuat();
                break;
            }
            case 131: {
                if (this.session.myCharz().cy >= 360) {
                    this.session.myCharz().goDaiHoi();
                }
                break;
            }
            case 132: {
                if (this.session.myCharz().isHaveItem(570)) {
                    this.session.myCharz().addInfo1(mResources.OPEN_BOX);
                } else if (this.session.myCharz().getLuong() < 1 * (this.session.myCharz().myObj().nJoinDH23 + 1)) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_NGOC,
                            (1 * (this.session.myCharz().myObj().nJoinDH23 + 1)) - this.session.myCharz().getLuong()));
                } else if (this.session.myCharz().cy >= 360 && this.session.myCharz().myObj().nWinHD23 < 11) {
                    this.session.myCharz().updateLuong(-(1 * (this.session.myCharz().myObj().nJoinDH23 + 1)), 2);
                    // this.session.myCharz().addInfo1(String.format(mResources.STT_DHVT,
                    // this.session.myCharz().myObj().nWinHD23 + 1));
                    this.session.myCharz().resetYard(1);
                }
                break;
            }
            case 133: {
                if (this.session.myCharz().isHaveItem(570)) {
                    this.session.myCharz().addInfo1(mResources.OPEN_BOX);
                } else if (this.session.myCharz().xu < 50000 * (this.session.myCharz().myObj().nJoinDH23 + 1)) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG, Util.gI().numberTostring(
                            (50000 * (this.session.myCharz().myObj().nJoinDH23 + 1)) - this.session.myCharz().xu)));
                } else if (this.session.myCharz().cy >= 360 && this.session.myCharz().myObj().nWinHD23 < 11) {
                    this.session.myCharz().updateXu(-(50000 * (this.session.myCharz().myObj().nJoinDH23 + 1)), 2);
                    // this.session.myCharz().addInfo1(String.format(mResources.STT_DHVT,
                    // this.session.myCharz().myObj().nWinHD23 + 1));
                    this.session.myCharz().resetYard(1);
                }
                break;
            }
            case 134: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_GHI_DANH_4, this.session.myCharz().myObj().nWinHD23);
                this.arrMenu.add(new MenuInfo(mResources.OK, 135));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 135: {
                if (this.session.myCharz().isHaveItem(570)) {
                    this.session.myCharz().addInfo1(mResources.OPEN_BOX);
                } else if (this.session.myCharz().getEmptyBagCount() < 1) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().myObj().nWinHD23 > 0 && this.session.myCharz().cy >= 360) {
                    this.session.myCharz().setValue(9, true);
                    Item box = new Item(570, false, 1, ItemOption.getOption(570, 0, 0), mResources.EMPTY,
                            mResources.EMPTY, mResources.EMPTY);
                    box.options.add(new ItemOption(72, this.session.myCharz().myObj().nWinHD23));
                    this.session.myCharz().addItemBag(0, box);
                    this.session.myCharz().myObj().nWinHD23 = 0;
                }
                break;
            }
            case 136: {
                break;
            }
            case 137: {
                this.session.service.openUISay(npcId, mResources.HD_DHVT, -1);
                break;
            }
            case 138: {
                if (DaiHoi.isRegister) {
                    this.session.myCharz().resetMenu();
                    this.chat = String.format(mResources.SAY_GHI_DANH_6, DaiHoi.name);
                    if (DaiHoi.priceNgoc > 0) {
                        this.arrMenu.add(new MenuInfo(String.format(mResources.REGISTER_PRICE1, DaiHoi.name,
                                Util.gI().numberTostring(DaiHoi.priceNgoc)), 139));
                    } else {
                        this.arrMenu.add(new MenuInfo(String.format(mResources.REGISTER_PRICE2, DaiHoi.name,
                                Util.gI().numberTostring(DaiHoi.priceVang)), 139));
                    }
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 139: {
                if (DaiHoi.isRegister) {
                    if ((DaiHoi.minPower != -1 && this.session.myCharz().cPower < DaiHoi.minPower)
                            || (DaiHoi.maxPower != -1 && this.session.myCharz().cPower > DaiHoi.maxPower)) {
                        this.session.myCharz()
                                .addInfo1(String.format(mResources.LIMIT_POWER_PRIZE,
                                        Util.gI().numberTostring(DaiHoi.minPower < 0 ? 0 : DaiHoi.minPower),
                                        Util.gI().numberTostring(DaiHoi.maxPower < 0 ? 0 : DaiHoi.maxPower)));
                    } else {
                        long totalTN = Char.getPotentialAdded(this.session.myCharz().cgender,
                                this.session.myCharz().cHPGoc, this.session.myCharz().cMPGoc,
                                this.session.myCharz().cDamGoc, this.session.myCharz().cDefGoc,
                                this.session.myCharz().cCriticalGoc);
                        if ((DaiHoi.minTN != -1 && totalTN < DaiHoi.minTN)
                                || (DaiHoi.maxTN != -1 && totalTN > DaiHoi.maxTN)) {
                            this.session.myCharz()
                                    .addInfo1(String.format(mResources.LIMIT_TN_PRIZE,
                                            Util.gI().numberTostring(DaiHoi.minTN < 0 ? 0 : DaiHoi.minTN),
                                            Util.gI().numberTostring(DaiHoi.maxTN < 0 ? 0 : DaiHoi.maxTN)));
                        } else if (DaiHoi.sizeFighter() >= 100) {
                            this.session.myCharz().addInfo1(mResources.MAX_REGISTER);
                        } else if (DaiHoi.isHaveFighter(this.session.myCharz().playerId)) {

                        } else if (DaiHoi.priceVang > this.session.myCharz().xu) {
                            this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                                    Util.gI().numberTostring(DaiHoi.priceVang)));
                        } else if (DaiHoi.priceNgoc > this.session.myCharz().getLuong()) {
                            this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_NGOC, DaiHoi.priceNgoc));
                        } else {
                            if (DaiHoi.priceNgoc > 0) {
                                this.session.myCharz().updateLuong(-DaiHoi.priceNgoc, 2);
                            }
                            if (DaiHoi.priceVang > 0) {
                                this.session.myCharz().updateXu(-DaiHoi.priceVang, 2);
                            }
                            DaiHoi.addFighter(this.session.myCharz().playerId);
                            this.session.service.openUISay(npcId,
                                    String.format(mResources.FINISH_REGISTER_PRIZE,
                                            Util.gI().convertTime(DaiHoi.timeRegister),
                                            Util.gI().convertTime(System.currentTimeMillis())),
                                    -1);
                        }
                    }
                }
                break;
            }
            case 140: {
                if (DaiHoi.isRegister) {
                    if (DaiHoi.isHaveFighter(this.session.myCharz().playerId)) {
                        DaiHoi.removeFighter(this.session.myCharz().playerId);
                    } else {

                    }
                }
                break;
            }
            case 141: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_QUOC_VUONG_2,
                        Util.gI().numberTostring(Char.getPowerLimit(this.session.myCharz().cPowerLimit + 1) + 1));
                this.arrMenu.add(new MenuInfo(mResources.UP_LIMIT_POWER_NOW, 143));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 142: {
                if (this.session.myCharz().myPet != null) {
                    this.session.myCharz().resetMenu();
                    this.chat = String.format(mResources.SAY_QUOC_VUONG_6, Util.gI()
                            .numberTostring(Char.getPowerLimit(this.session.myCharz().myPetz().cPowerLimit + 1) + 1));
                    this.arrMenu.add(new MenuInfo(mResources.UP_LIMIT_POWER_DETU_NOW, 144));
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 143: {
                if (this.session.myCharz().cPowerLimit < 5) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().cPowerLimit > 3 && !this.session.myCharz().isEquipThan()) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUOC_VUONG_3;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if (this.session.myCharz().getLuong() < 100) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 100 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().cPowerLimit++;
                        this.session.myCharz().updateLuong(-100, 2);
                        this.session.myCharz().addInfo1(String.format(mResources.UP_POWER_LIMIT_SUCCESS,
                                Util.gI().numberTostring(Char.getPowerLimit(this.session.myCharz().cPowerLimit) + 1)));
                    }
                }
                break;
            }
            case 144: {
                if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().cPowerLimit < 5) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().myPetz().cPowerLimit > 3
                            && !this.session.myCharz().myPetz().isEquipThan()) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUOC_VUONG_3;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if (this.session.myCharz().getLuong() < 100) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 100 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().myPetz().cPowerLimit++;
                        this.session.myCharz().updateLuong(-100, 2);
                        this.session.myCharz().addInfo1(String.format(mResources.UP_POWER_LIMIT_SUCCESS2, Util.gI()
                                .numberTostring(Char.getPowerLimit(this.session.myCharz().myPetz().cPowerLimit) + 1)));
                    }
                }
                break;
            }
            case 145: {
                break;
            }
            case 146: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_TO_SU_KAIO_2;
                if (this.session.myCharz().cPowerLimit >= 5
                        && this.session.myCharz().cPowerLimit < this.session.myCharz().maxLimitKaio) {
                    this.arrMenu.add(new MenuInfo(mResources.USER, 147));
                }
                if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().cPowerLimit >= 5
                        && this.session.myCharz().myPetz().cPowerLimit < this.session.myCharz().myPetz().maxLimitKaio) {
                    this.arrMenu.add(new MenuInfo(mResources.DE_TU, 149));
                }
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 147: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_TO_SU_KAIO_3,
                        Util.gI().numberTostring(Char.getPowerLimit(this.session.myCharz().cPowerLimit + 1) + 1));
                this.arrMenu.add(new MenuInfo(mResources.UP_LIMIT_POWER_NOW2, 148));
                this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 148: {
                if (this.session.myCharz().cPowerLimit >= 5
                        && this.session.myCharz().cPowerLimit < this.session.myCharz().maxLimitKaio) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().cPowerLimit > 3 && !this.session.myCharz().isEquipThan()) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUOC_VUONG_4;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if (this.session.myCharz().getLuong() < 199) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 199 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().cPowerLimit++;
                        this.session.myCharz().updateLuong(-199, 2);
                        this.session.myCharz().addInfo1(String.format(mResources.UP_POWER_LIMIT_SUCCESS,
                                Util.gI().numberTostring(Char.getPowerLimit(this.session.myCharz().cPowerLimit) + 1)));
                    }
                }
                break;
            }
            case 149: {
                if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().cPowerLimit >= 5
                        && this.session.myCharz().myPetz().cPowerLimit < this.session.myCharz().myPetz().maxLimitKaio) {
                    this.session.myCharz().resetMenu();
                    this.chat = String.format(mResources.SAY_QUOC_VUONG_6, Util.gI()
                            .numberTostring(Char.getPowerLimit(this.session.myCharz().myPetz().cPowerLimit + 1) + 1));
                    this.arrMenu.add(new MenuInfo(mResources.UP_LIMIT_POWER_DETU_NOW, 150));
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 150: {
                if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().cPowerLimit >= 5
                        && this.session.myCharz().myPetz().cPowerLimit < this.session.myCharz().myPetz().maxLimitKaio) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().myPetz().cPowerLimit > 3
                            && !this.session.myCharz().myPetz().isEquipThan()) {
                        this.session.myCharz().resetMenu();
                        this.chat = mResources.SAY_QUOC_VUONG_3;
                        this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                        this.openUIConfirm(npcId, null, null, -1);
                    } else if (this.session.myCharz().getLuong() < 199) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 199 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().myPetz().cPowerLimit++;
                        this.session.myCharz().updateLuong(-199, 2);
                        this.session.myCharz().addInfo1(String.format(mResources.UP_POWER_LIMIT_SUCCESS2, Util.gI()
                                .numberTostring(Char.getPowerLimit(this.session.myCharz().myPetz().cPowerLimit) + 1)));
                    }
                }
                break;
            }
            case 151: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUY_LAO2;
                this.arrMenu.add(new MenuInfo(mResources.NHAN_QUA, 152));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 152: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().myObj().nRuaCon > 2) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.MAX_GIAO_RUA_CON, this.session.myCharz().myObj().nRuaCon));
                } else if (this.session.myCharz().isHaveItemBag(874)) {
                    this.session.myCharz().myObj().nRuaCon++;
                    if (this.session.myCharz().getEmptyBagIndex() < 1) {
                        this.session.myCharz().addInfo1(1, String.format(mResources.BAG_FULL_2, 1));
                    } else {
                        this.session.myCharz().useItemBagById(874, 1);
                        // cho qua tai day
                        Item item2;
                        if (Util.gI().nextInt(100) < 25) {
                            item2 = new Item(190, false, Util.gI().nextInt(10000000, 100000000),
                                    ItemOption.getOption(190, 0, 0), mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 20) {
                            item2 = new Item(861, false, Util.gI().nextInt(10, 100), ItemOption.getOption(861, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 30) {
                            int id = Util.gI().nextInt(213, 219);
                            item2 = new Item(id, false, 1, ItemOption.getOption(id, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 20) {
                            item2 = new Item(1229, false, 1, ItemOption.getOption(1229, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 15) {
                            int id = Util.gI().nextInt(828, 836);
                            item2 = new Item(id, false, 1, ItemOption.getOption(id, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 10) {
                            item2 = new Item(1151, false, 1, ItemOption.getOption(1151, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 5) {
                            item2 = new Item(1150, false, 1, ItemOption.getOption(1150, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 5) {
                            int id = Util.gI().nextInt(1152, 1153);
                            item2 = new Item(id, false, 1, ItemOption.getOption(id, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 10) {
                            item2 = new Item(17, false, 1, ItemOption.getOption(17, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                            // } else if (Util.gI().nextInt(100) < 10) {
                            // item2 = new Item(872, false, 1, ItemOption.getOption(872, 0, 0),
                            // mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            // } else if (Util.gI().nextInt(100) < 10) {
                            // item2 = new Item(873, false, 1, ItemOption.getOption(873, 0, 0),
                            // mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            // } else if (Util.gI().nextInt(100) < 5) {
                            // item2 = new Item(865, false, 1, ItemOption.getOption(865, 0, 0),
                            // mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                        } else {
                            item2 = new Item(190, false, Util.gI().nextInt(10000000, 100000000),
                                    ItemOption.getOption(190, 0, 0), mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                        }
                        this.session.myCharz().addItemBag(0, item2);
                    }
                }
                break;
            }
            case 153: {
                this.session.myCharz().goDaiHoiVoThuat2();
                break;
            }
            case 154: {
                this.session.myCharz().openTop100CT();
                break;
            }
            case 155: {
                break;
            }
            case 156: {
                this.session.myCharz().setSuperRank(1);
                this.session.myCharz().openThachDau();
                break;
            }
            case 157: {
                this.session.myCharz().setSuperRank(1);
                this.session.myCharz().openThachDau();
                break;
            }
            case 158: {
                if (this.session.myCharz().isHaveDuaHau(51, -1)) {
                    if (this.session.myCharz().getDuaHau(51, -1).duaHauIndex == 3) {
                        if (this.session.myCharz().isgiaodich) {
                            this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                        } else if (this.session.myCharz().isSecurity) {
                            this.session.myCharz().addInfo1(mResources.BAOVE);
                        } else if (this.session.myCharz().getEmptyBagCount() < 1) {
                            this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                        } else {
                            this.session.myCharz().clearDuaHau();
                            this.session.myCharz().addItemBag(0,
                                    new Item(569, false, 1, ItemOption.getOption(569, 0, 0), mResources.EMPTY,
                                            mResources.EMPTY, mResources.EMPTY));
                        }
                    }
                }
                break;
            }
            case 159: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_EVENT_KING1;
                this.arrMenu.add(new MenuInfo(mResources.MAM_LE_BAC, 160));
                this.arrMenu.add(new MenuInfo(mResources.MAM_LE_VANG, 162));
                this.arrMenu.add(new MenuInfo(mResources.TOP10_EVENT1, 129));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 160: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_MAM_LE_BAC;
                this.arrMenu.add(new MenuInfo(mResources.OK, 161));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 161: {
                this.session.myCharz().clientInput.openClientInput(18, mResources.INPUT_QUANTITY,
                        new String[]{mResources.QUANTITY_KEY}, new int[]{0});
                break;
            }
            case 162: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_MAM_LE_VANG;
                this.arrMenu.add(new MenuInfo(mResources.OK, 163));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 163: {
                this.session.myCharz().clientInput.openClientInput(19, mResources.INPUT_QUANTITY,
                        new String[]{mResources.QUANTITY_KEY}, new int[]{0});
                break;
            }
            case 164: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangmamlebac(1);
                }
                break;
            }
            case 165: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangmamlevang(1);
                }
                break;
            }
            case 166: {
                this.session.myCharz().shopId = 25;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 167: {
                if (!this.session.myCharz().isFullTBHD) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_WISH_2;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                } else {
                    if (this.session.getIntVersion() >= 222) {
                        this.typeInfo = 171;
                        this.session.service.combine(mResources.SRC_NANGCAP_11, mResources.SRC_NANGCAP_12, 56);
                    } else {
                        this.session.myCharz().addInfo1(mResources.DOWN_222);
                    }
                }
                break;
            }
            case 168: {
                LuyenTap.openTOP(this.session.myCharz());
                break;
            }
            case 169: {
                if (this.session.myCharz().fighter2 == null && Memory.get(this.session.userId).nFreeWish > 0) {
                    this.session.myCharz().addFightWish();
                }
                break;
            }
            case 170: {
                if (this.session.myCharz().cPower < 40000000000L) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.YEU_CAU_SUC_MANH, Util.gI().numberTostring(40000000000L)));
                } else {
                    Skill skill = null;
                    if (this.session.myCharz().cgender == 0) {
                        skill = this.session.myCharz().getSkill(24);
                    }
                    if (this.session.myCharz().cgender == 1) {
                        skill = this.session.myCharz().getSkill(26);
                    }
                    if (this.session.myCharz().cgender == 2) {
                        skill = this.session.myCharz().getSkill(25);
                    }
                    if (skill == null) {
                        this.session.myCharz().requestOpenUIItem(npcId, String.format(mResources.LEARN_SKILL1,
                                Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].name));
                        // this.session.myCharz().requestOpenUIItem(npcId,
                        // String.format(mResources.SAY_WISH3, , new int[]{1229}, new int[]{9999}, 174,
                        // mResources.AGREE, 0, mResources.REFUSE, 10000000, 99, 0, true, "");
                    } else if (skill.point < skill.template.maxPoint) {
                        if (skill.curExp < 1000) {
                            this.session.myCharz().addInfo1(mResources.NOT_THANH_THAO);
                        } else {
                            this.session.myCharz().requestOpenUIItem(npcId,
                                    String.format(mResources.LEARN_SKILL2, skill.template.name, skill.point + 1));
                            // this.session.myCharz().requestOpenUIItem(npcId,
                            // String.format(mResources.SAY_WISH4, ), new int[]{1229}, new int[]{999}, 174,
                            // mResources.AGREE, 0, mResources.REFUSE, 10000000, -1, 0, true, "");
                        }
                    }
                }
                break;
            }
            case 171: {
                break;
            }
            case 172: {
                if (!this.session.myCharz().isFullTBHD) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_WISH_2;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 171));
                    this.openUIConfirm(npcId, null, null, -1);
                } else {
                    Combine.Make(this.session.myCharz());
                    this.typeInfo = 171;
                }
                break;
            }
            // su dung de quy
            case 173: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().requestItem != null) {
                    RequestItem request = this.session.myCharz().requestItem;
                    this.session.myCharz().requestItem = null;
                    if (this.session.myCharz().requestOpenUIItem(request.npcId, request.tile, request.templateId,
                            request.quantity, request.typeBoeard, request.xu, request.luong, true,
                            request.quntityEmptyBag)) {
                        if (!request.execute()) {
                            // de quy
                            this.session.myCharz().resetMenu();
                            this.arrMenu.add(new MenuInfo(request.strOK, request.typeBoeardOK));
                            this.openUIConfirm(request.npcId, 0);
                            // set lai
                            this.typeInfo = 173;
                        }
                    }
                }
                break;
            }
            case 174: {
                this.session.service.getFlag();
                this.session.service.npcChat(this.npcId, mResources.CHAT_WISH2);
                this.session.service.setCombineEff(7,
                        Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].iconId, -1,
                        this.npcId);
                Skill skill = null;
                if (this.session.myCharz().cgender == 0) {
                    skill = this.session.myCharz().getSkill(24);
                }
                if (this.session.myCharz().cgender == 1) {
                    skill = this.session.myCharz().getSkill(26);
                }
                if (this.session.myCharz().cgender == 2) {
                    skill = this.session.myCharz().getSkill(25);
                }
                if (skill == null) {
                    this.session.myCharz().addInfo1(3000, String.format(mResources.HOC_DUOC_SKILL,
                            Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].name));
                    if (this.session.myCharz()
                            .getSkill((int) Char.ARRHOCSKILL[this.session.myCharz().cgender]) == null) {
                        this.session.myCharz().skills
                                .add(Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].skills[0]
                                        .clone());
                    }
                } else if (skill.point < skill.template.maxPoint) {
                    this.session.myCharz().skills.set(this.session.myCharz().skills.indexOf(skill),
                            skill.template.skills[skill.point].clone());
                }
                this.session.service.meLoadSkill(this.session.myCharz().skills);
                this.session.myCharz().resetMenu();
                break;
            }
            case 175: {
                this.session.myCharz().openTabGold();
                break;
            }
            case 176: {
                LuckyRoundNew.joinGold(this.session.myCharz(), 1);
                this.session.myCharz().openTabGold();
                break;
            }
            case 177: {
                LuckyRoundNew.joinGold(this.session.myCharz(), 10);
                this.session.myCharz().openTabGold();
                break;
            }
            case 178: {
                LuckyRoundNew.joinGold(this.session.myCharz(), 20);
                this.session.myCharz().openTabGold();
                break;
            }
            case 179: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().winLuckyRoundNew();
                }
                break;
            }
            case 180: {
                this.session.myCharz().shopId = 12;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 181: {
                if (this.session.getIntVersion() >= 214) {
                    this.session.myCharz().shopId = -2003;
                    this.session.service.shopReBuyItem(this.session.myCharz().myObj().reBuyItem);
                }
                break;
            }
            case 182: {
                // TraiDat
                if (this.session.myCharz().cgender == 0) {
                    this.session.myCharz().shopId = 1;
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                }
                // Namek
                if (this.session.myCharz().cgender == 1) {
                    this.session.myCharz().shopId = 2;
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                }
                // Xayda
                if (this.session.myCharz().cgender == 2) {
                    this.session.myCharz().shopId = 3;
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                }
                break;
            }
            case 183: {
                // TraiDat
                if (this.session.myCharz().cgender == 0) {
                    this.session.myCharz().shopId = 4;
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                }
                // Namek
                if (this.session.myCharz().cgender == 1) {
                    this.session.myCharz().shopId = 5;
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                }
                // Xayda
                if (this.session.myCharz().cgender == 2) {
                    this.session.myCharz().shopId = 6;
                    this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                }
                break;
            }
            case 184: {
                this.session.myCharz().shopId = 21;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 185: {
                this.session.myCharz().shopId = 20;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 186: {
                this.session.myCharz().shopId = 18;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            // Lech Teamobi
            case 187: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_NOI_BANH_8;
                if (this.session.myCharz().myObj().arrBanh.isEmpty()) {
                    this.arrMenu.add(new MenuInfo(mResources.NAU_SUP1, 188));
                    this.arrMenu.add(new MenuInfo(mResources.NAU_SUP2, 190));
                }
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 188: {
                this.session.myCharz().requestOpenUIItem(npcId, mResources.COOK_CAKE1);
                // this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_REQUEST_SUP_1,
                // new int[]{1991, 1992, 1993}, new int[]{3, 3, 3}, 189, mResources.AGREE, 0,
                // mResources.REFUSE, -1, -1, 0, false, "");
                break;
            }
            case 189: {
                this.session.myCharz().addNauBanh(npcId, 180, 1997);
                this.session.myCharz().resetMenu();
                break;
            }
            case 190: {
                this.session.myCharz().requestOpenUIItem(npcId, mResources.COOK_CAKE2);
                // this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_REQUEST_SUP_2,
                // new int[]{1991, 1992, 1993}, new int[]{5, 5, 5}, 191, mResources.AGREE, 0,
                // mResources.REFUSE, -1, -1, 0, false, "");
                break;
            }
            case 191: {
                this.session.myCharz().addNauBanh(npcId, 300, 1998);
                this.session.myCharz().resetMenu();
                break;
            }
            case 192: {
                this.session.myCharz().shopId = 26;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 193: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_EVENT_NGAYHE1;
                this.arrMenu.add(new MenuInfo(mResources.DOI_XOCA1, 194));
                this.arrMenu.add(new MenuInfo(mResources.DOI_XOCA2, 196));
                this.arrMenu.add(new MenuInfo(mResources.TOP10_EVENT1, 129));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 194: {
                this.session.myCharz().clientInput.openClientInput(20, mResources.INPUT_NUMBER,
                        new String[]{mResources.INPUT_NUMBER2}, new int[]{0});
                break;
            }
            case 195: {

                break;
            }
            case 196: {
                this.session.myCharz().clientInput.openClientInput(21, mResources.INPUT_NUMBER,
                        new String[]{mResources.INPUT_NUMBER2}, new int[]{0});
                break;
            }
            case 197: {

                break;
            }
            case 198: {
                // Tao chat
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_DANH_HIEU;
                for (int i = 0; i < this.session.myCharz().aEffChar.size(); i++) {
                    if (this.session.myCharz().aEffChar.get(i).typeEff == 1) {
                        this.chat += (i + 1) + ". " + this.session.myCharz().aEffChar.get(i).getName() + ": "
                                + this.session.myCharz().aEffChar.get(i).item.strOption() + "\n";
                    }
                }
                // Tao danh sach menu
                for (int i = 0; i < this.session.myCharz().aEffChar.size(); i++) {
                    if (this.session.myCharz().aEffChar.get(i).typeEff == 1) {
                        this.arrMenu.add(new MenuInfo(String.format(mResources.DANH_HIEU2, i + 1,
                                this.session.myCharz().aEffChar.get(i).status(),
                                this.session.myCharz().aEffChar.get(i).hsd()), 199, i));
                    }
                }
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 199: {
                if (info.index >= 0 && info.index < this.session.myCharz().aEffChar.size()
                        && this.session.myCharz().aEffChar.get(info.index).typeEff == 1) {
                    this.session.myCharz().aEffChar
                            .get(info.index).isPaint = !this.session.myCharz().aEffChar.get(info.index).isPaint;
                    if (!this.session.myCharz().aEffChar.get(info.index).isPaint) {
                        this.session.myCharz().zoneMap.removeEffChar(this.session.myCharz().charID,
                                this.session.myCharz().aEffChar.get(info.index).effId);
                    } else {
                        this.session.myCharz().zoneMap.addEffectChar(this.session.myCharz().charID,
                                this.session.myCharz().aEffChar.get(info.index).effId,
                                this.session.myCharz().aEffChar.get(info.index).layer,
                                this.session.myCharz().aEffChar.get(info.index).loop,
                                this.session.myCharz().aEffChar.get(info.index).tLoop,
                                this.session.myCharz().aEffChar.get(info.index).isStand);
                    }
                    this.session.myCharz().updateAll();
                    this.session.service.meLoadPoint();
                    this.session.myCharz().zoneMap.playerLoadAll(this.session.myCharz());
                }
                break;
            }
            case 200: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_DANHHIEU, this.session.myCharz().myObj().pointEvent);
                this.arrMenu.add(new MenuInfo(mResources.DANHHIEU3, 203));
                this.arrMenu.add(new MenuInfo(mResources.DANHHIEU2, 202));
                this.arrMenu.add(new MenuInfo(mResources.DANHHIEU1, 201));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 201: {
                if (this.session.myCharz().myObj().pointEvent < 100) {
                    this.session.myCharz().addInfo1(String.format(mResources.NOT_POINT, 100));
                } else {
                    int sc = 0;
                    EffChar eff = this.session.myCharz().getEffCharById(1000);
                    if (eff != null) {
                        sc = (int) (((eff.last / 1000) + eff.second) - (System.currentTimeMillis() / 1000));
                    }
                    this.session.myCharz().removeEffVip();
                    this.session.myCharz().myObj().pointEvent -= 100;
                    Item item = new Item(395, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(77, 8));
                    item.options.add(new ItemOption(103, 8));
                    item.options.add(new ItemOption(50, 8));
                    this.session.myCharz().addEffectChar(1000, 1, 0, 1, 1, 604800 + sc, 1, true, item);
                }
                break;
            }
            case 202: {
                if (this.session.myCharz().myObj().pointEvent < 75) {
                    this.session.myCharz().addInfo1(String.format(mResources.NOT_POINT, 75));
                } else {
                    int sc = 0;
                    EffChar eff = this.session.myCharz().getEffCharById(1001);
                    if (eff != null) {
                        sc = (int) (((eff.last / 1000) + eff.second) - (System.currentTimeMillis() / 1000));
                    }
                    this.session.myCharz().removeEffVip();
                    this.session.myCharz().myObj().pointEvent -= 75;
                    Item item = new Item(395, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(77, 5));
                    item.options.add(new ItemOption(103, 5));
                    item.options.add(new ItemOption(50, 5));
                    this.session.myCharz().addEffectChar(1001, 1, 0, 1, 1, 604800 + sc, 1, true, item);
                }
                break;
            }
            case 203: {
                if (this.session.myCharz().myObj().pointEvent < 50) {
                    this.session.myCharz().addInfo1(String.format(mResources.NOT_POINT, 50));
                } else {
                    int sc = 0;
                    EffChar eff = this.session.myCharz().getEffCharById(1002);
                    if (eff != null) {
                        sc = (int) (((eff.last / 1000) + eff.second) - (System.currentTimeMillis() / 1000));
                    }
                    this.session.myCharz().removeEffVip();
                    this.session.myCharz().myObj().pointEvent -= 50;
                    Item item = new Item(395, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(77, 2));
                    item.options.add(new ItemOption(103, 2));
                    item.options.add(new ItemOption(50, 2));
                    this.session.myCharz().addEffectChar(1002, 1, 0, 1, 1, 604800 + sc, 1, true, item);
                }
                break;
            }
            case 204: {
                if (this.session.myCharz().cardLoad == null) {
                    this.session.myCharz().cardLoad = new NapThe(this.session);
                }
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_NAPTHE1;
                for (int i = 0; i < NapThe.NETWORK.length; i++) {
                    this.arrMenu.add(new MenuInfo(NapThe.NETWORK[i], 205, i));
                }
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 205: {
                if (this.session.myCharz().cardLoad != null && info.index >= 0 && info.index < NapThe.NETWORK.length) {
                    this.session.myCharz().resetMenu();
                    this.session.myCharz().cardLoad.netWork = NapThe.NETWORK[info.index];
                    this.chat = mResources.SAY_NAPTHE2;
                    for (int i = 0; i < NapThe.PRICE.length; i++) {
                        this.arrMenu.add(new MenuInfo(
                                String.format("%sVNĐ", Util.gI().getFormatNumber(NapThe.PRICE[i])), 206, i));
                    }
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 206: {
                if (this.session.myCharz().cardLoad != null && info.index >= 0 && info.index < NapThe.PRICE.length) {
                    this.session.myCharz().cardLoad.price = NapThe.PRICE[info.index];
                    this.session.myCharz().clientInput.openClientInput(22, mResources.NAP_THE1,
                            new String[]{mResources.NAP_THE2, mResources.NAP_THE3}, new int[]{1, 1});
                }
                break;
            }
            case 207: {
                if (this.session.myCharz().cardLoad != null && this.session.myCharz().cardLoad.netWork != null
                        && this.session.myCharz().cardLoad.price != 0 && this.session.myCharz().cardLoad.code != null
                        && this.session.myCharz().cardLoad.seri != null) {
                    this.session.myCharz().cardLoad.okNapThe();
                }
                break;
            }
            case 208: {
                this.session.myCharz().clientInput.openClientInput(2, mResources.GIFT_CODE,
                        new String[]{mResources.GIFT_CODE}, new int[]{1});
                break;
            }
            case 209: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_NAP_1,
                        Util.gI().getFormatNumber(Money.gI().getMoney(this.session.myCharz())),
                        Util.gI().getFormatNumber(this.session.myCharz().totalGold));
                this.arrMenu.add(new MenuInfo(mResources.MONEY_TO_NGOC, 210));
                this.arrMenu.add(new MenuInfo(mResources.MONEY_TO_GOLD, 212));
                if (this.session.myCharz().totalGold > 0) {
                    this.arrMenu.add(new MenuInfo(mResources.NHAN_THOI_VANG, 216));
                }
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 210: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (!Money.gI().isNapNgoc) {
                    this.session.myCharz().addInfo1(mResources.CHUA_MO);
                } else {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_NAP_2;
                    for (int i = 0; i < Money.gI().arrMoneyNgoc.length; i++) {
                        this.arrMenu.add(new MenuInfo(
                                String.format(mResources.MONEY_TO_NGOC2,
                                        Util.gI().getFormatNumber(Money.gI().arrMoneyNgoc[i][0]),
                                        Util.gI().getFormatNumber(Money.gI().arrMoneyNgoc[i][1] * Money.gI().xNgoc)),
                                211, i) {
                            @Override
                            public boolean excute() {
                                Money.gI().changeMoney(MenuBoard.this.session.myCharz(), 0, super.index);
                                return true;
                            }
                        });
                    }
                    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 211: {
                break;
            }
            case 212: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (!Money.gI().isNapVang) {
                    this.session.myCharz().addInfo1(mResources.CHUA_MO);
                } else {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_NAP_3;
                    for (int i = 0; i < Money.gI().arrMoneyGold.length; i++) {
                        this.arrMenu.add(new MenuInfo(
                                String.format(mResources.MONEY_TO_GOLD2,
                                        Util.gI().getFormatNumber(Money.gI().arrMoneyGold[i][0]),
                                        Util.gI().getFormatNumber(Money.gI().arrMoneyGold[i][1] * Money.gI().xVang)),
                                213, i) {
                            @Override
                            public boolean excute() {
                                Money.gI().changeMoney(MenuBoard.this.session.myCharz(), 1, super.index);
                                return true;
                            }
                        });
                    }
                    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 213: {
                break;
            }
            case 214: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_NAP_4;
                this.arrMenu.add(new MenuInfo(String.format(mResources.OPEN_MEMBER_2, Server.gI().MoneyOpen), 215));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 215: {
                if (Money.gI().getMoney(this.session.myCharz()) >= Server.gI().MoneyOpen) {
                    Money.gI().updateMoeny(this.session.myCharz(), -Server.gI().MoneyOpen);
                    // this.session.myCharz().updateLuong(10000, 2);
                    // this.session.myCharz().updateXu(3000000000L, 2);
                    this.session.myCharz().isCan = true;
                }
                break;
            }
            case 216: {
                this.session.myCharz().clientInput.openClientInput(6, mResources.INPUT_THOI_VANG,
                        new String[]{mResources.INPUT_THOI_VANG}, new int[]{0});
                break;
            }
            case 217: {
                this.session.myCharz().shopId = 22;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 218: {
                // Den cold
                if (this.session.myCharz().ctaskId >= 31 && this.session.myCharz().ctaskIndex >= 0) {
                    this.session.myCharz().goCold();
                } else {
                    this.session.myCharz().addInfo1(mResources.YOUR_NOT_TO);
                }
                break;
            }
            case 219: {
                // Den Nappa
                if (this.session.myCharz().ctaskId >= 22 && this.session.myCharz().ctaskIndex >= 0) {
                    this.session.myCharz().goNappa();
                } else {
                    this.session.myCharz().addInfo1(mResources.YOUR_NOT_TO);
                }
                break;
            }
            case 220: {
                // Den tp vegeta
                this.session.myCharz().goVegetaCity();
                break;
            }
            case 221: {
                if (this.session.myCharz().getLuong() < 5) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.CONTHIEU_NGOC, 5 - this.session.myCharz().getLuong()));
                } else {
                    Char player = Player.findRandomBoss(55);
                    if (player != null) {
                        this.session.myCharz().updateLuong(-5, 2);
                        this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                        player.zoneMap.join(this.session.myCharz(), 0, player.cx, player.cy);
                    } else {
                        this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                    }
                }
                break;
            }
            case 222: {
                if (this.session.myCharz().getLuong() < 5) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.CONTHIEU_NGOC, 5 - this.session.myCharz().getLuong()));
                } else {
                    Char player = Player.findRandomBoss(57);
                    if (player != null) {
                        this.session.myCharz().updateLuong(-5, 2);
                        this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                        player.zoneMap.join(this.session.myCharz(), 0, player.cx, player.cy);
                    } else {
                        this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                    }
                }
                break;
            }
            case 223: {
                if (this.session.myCharz().getLuong() < 5) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.CONTHIEU_NGOC, 5 - this.session.myCharz().getLuong()));
                } else {
                    Char player = Player.findRandomBoss(56);
                    if (player != null) {
                        this.session.myCharz().updateLuong(-5, 2);
                        this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                        player.zoneMap.join(this.session.myCharz(), 0, player.cx, player.cy);
                    } else {
                        this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                    }
                }
                break;
            }
            case 224: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_BO_MONG_2;
                this.arrMenu.add(new MenuInfo(mResources.TOP_CT, 225));
                this.arrMenu.add(new MenuInfo(mResources.TOP_DG, 226));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 225: {
                Rank.getRank(1).loadRank();
                this.session.service.top(Rank.getRank(1));
                break;
            }
            case 226: {
                Rank.getRank(2).loadRank();
                this.session.service.top(Rank.getRank(2));
                break;
            }
            case 227: {
                this.session.myCharz().openDailyTask();
                break;
            }
            case 228: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THAN_MEO_KARIN_2;
                this.arrMenu.add(new MenuInfo(mResources.YES_TD, 229));
                this.arrMenu.add(new MenuInfo(mResources.NO_TD, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 229: {
                if (this.session.myCharz().mapTemplateId == 46) {
                    Npc npc = this.session.myCharz().zoneMap.findNPCInMap(18);
                    if (npc != null && !npc.isHide) {
                        this.session.myCharz().zoneMap.hideNpc(18, true);
                        Player.addBoss(54, 0, -1, -1, true, npc.cx, npc.cy, this.session.myCharz().zoneMap, 5000, -1);
                        this.session.myCharz().updateTask(15, 1);
                    }
                }
                break;
            }
            case 230: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THAN_VU_TRU_2;
                this.arrMenu.add(new MenuInfo(mResources.DOWN_THAN_DIEN, 231));
                this.arrMenu.add(new MenuInfo(mResources.GO_THANH_DIA_KIAO, 232));
                this.arrMenu.add(new MenuInfo(mResources.GO_ROAD_SNAKE, 322));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 231: {
                this.session.myCharz().goThanDien();
                break;
            }
            case 232: {
                this.session.myCharz().goSanKaio();
                break;
            }
            case 233: {
                break;
            }
            case 234: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.doanhTrai == null
                        && this.session.myCharz().clan.countbarrack < Clan.MAX_JOIN_BARRACK
                        && this.session.myCharz().clan.getSizeMember() >= 3) {
                    int secondJoin = 60 * 60 * 24 * 1;
                    int limidDam = 1000;
                    if (limidDam > this.session.myCharz().cDamGoc) {
                        this.session.service.openUISay(npcId,
                                String.format(mResources.SAY_LINH_CANH_7, Util.gI().getFormatNumber(limidDam)), -1);
                    } else if ((System.currentTimeMillis() / 1000)
                            - this.session.myCharz().clanMember.joinTime < secondJoin) {
                        this.session.service.openUISay(npcId,
                                String.format(mResources.SAY_LINH_CANH_3, Util.gI().getStrTime(secondJoin * 1000)), -1);
                    } else {
                        DoanhTrai dt = new DoanhTrai();
                        dt.initDoanhTrai(this.session.myCharz().clan);
                        this.session.myCharz().clan.playerBarrack = this.session.myCharz().cName;
                        this.session.myCharz().clan.countbarrack++;
                        this.session.myCharz().clan.timeBarrack = Util.gI().strHoursNow();
                        Server.gI().add(dt);
                        ZoneMap zone = dt.maps.get(0).getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                            zone.join(this.session.myCharz(), 0, 90, 432);
                            this.session.myCharz().updateTask(1, 1);
                            this.session.myCharz().setText(0, mResources.TRAI_DOC_NHAN, 1800, 0, 0);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                    }
                }
                break;
            }
            case 235: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.doanhTrai != null) {
                    int secondJoin = 60 * 60 * 24 * 1;
                    int limidDam = 1000;
                    if (limidDam > this.session.myCharz().cDamGoc) {
                        this.session.service.openUISay(npcId,
                                String.format(mResources.SAY_LINH_CANH_7, Util.gI().getFormatNumber(limidDam)), -1);
                    } else if ((System.currentTimeMillis() / 1000)
                            - this.session.myCharz().clanMember.joinTime < secondJoin) {
                        this.session.service.openUISay(npcId,
                                String.format(mResources.SAY_LINH_CANH_3, Util.gI().getStrTime(secondJoin * 1000)), -1);
                    } else {
                        ZoneMap zone = this.session.myCharz().clan.doanhTrai.maps.get(0)
                                .getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                            zone.join(this.session.myCharz(), 0, 90, 432);
                            this.session.myCharz().updateTask(1, 1);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                    }
                }
                break;
            }
            case 236: {
                this.session.service.openUISay(npcId,
                        String.format(mResources.SAY_SHOP_KY_GUI_2, Util.gI().numberTostring(ItemKyGui.NGOC_SALE),
                                ItemKyGui.FEE, Util.gI().numberTostring(ItemKyGui.MINXU),
                                Util.gI().numberTostring(ItemKyGui.MAXXU), Util.gI().numberTostring(ItemKyGui.MINLUONG),
                                Util.gI().numberTostring(ItemKyGui.MAXLUONG)),
                        -1);
                break;
            }
            case 237: {
                this.session.myCharz().isThoiVang = false;
                ItemKyGui.openShop(this.session.myCharz());
                break;
            }
            case 238: {
                this.session.myCharz().isThoiVang = true;
                ItemKyGui.openShop(this.session.myCharz());
                break;
            }
            case 239: {
                this.session.service.openUISay(npcId, mResources.HD_BLACKBALL1 + mResources.HD_BLACKBALL2, -1);
                break;
            }
            case 240: {
                if (this.session.myCharz().cPower < 1000000000L) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.YEU_CAU_SUC_MANH, Util.gI().numberTostring(1000000000L)));
                } else {
                    this.session.myCharz().openTransport(1);
                }
                break;
            }
            case 241: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_OMEGA3;
                for (int i = 0; i < this.session.myCharz().myObj().blackBalls.size(); i++) {
                    this.arrMenu
                            .add(new MenuInfo(this.session.myCharz().myObj().blackBalls.get(i).template.name, 242, i));
                }
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 242: {
                if (info.index >= 0 && info.index < this.session.myCharz().myObj().blackBalls.size()) {
                    Item item = this.session.myCharz().myObj().blackBalls.get(info.index);
                    if (this.session.myCharz().isHaveItems(item.template.id)) {
                        this.session.myCharz().setItemsById(item.template.id, item);
                    } else {
                        this.session.myCharz().items.add(item);
                    }
                    this.session.myCharz().updateAll();
                    this.session.service.meLoadPoint();
                    if (this.session.myCharz().zoneMap != null) {
                        this.session.myCharz().zoneMap.playerLoadAll(this.session.myCharz());
                    }
                    this.session.myCharz().myObj().blackBalls.remove(info.index);
                    this.session.myCharz().addInfo1(String.format(mResources.YOUR_GET_REWARDED, item.template.name));
                }
                break;
            }
            case 243: {
                if (this.session.myCharz().itemBlackBall != null && this.session.myCharz().phuHoBlackBall == 0) {
                    if (this.session.myCharz().getLuong() < 10) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 10 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().updateLuong(-10, 2);
                        this.session.myCharz().phuHoBlackBall = 3;
                        this.session.myCharz().updateAll();
                        this.session.service.meLoadPoint();
                        this.session.myCharz().cHP = this.session.myCharz().cHPFull;
                        this.session.myCharz().cMP = this.session.myCharz().cMPFull;
                        this.session.service.meLoadHP(this.session.myCharz().cHP);
                        this.session.service.meLoadMP(this.session.myCharz().cMP);
                        this.session.myCharz().zoneMap.playerLoadAll(this.session.myCharz());
                    }
                }
                break;
            }
            case 244: {
                if (this.session.myCharz().itemBlackBall != null && this.session.myCharz().phuHoBlackBall == 0) {
                    if (this.session.myCharz().getLuong() < 20) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 20 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().updateLuong(-20, 2);
                        this.session.myCharz().phuHoBlackBall = 5;
                        this.session.myCharz().updateAll();
                        this.session.service.meLoadPoint();
                        this.session.myCharz().cHP = this.session.myCharz().cHPFull;
                        this.session.myCharz().cMP = this.session.myCharz().cMPFull;
                        this.session.service.meLoadHP(this.session.myCharz().cHP);
                        this.session.service.meLoadMP(this.session.myCharz().cMP);
                        this.session.myCharz().zoneMap.playerLoadAll(this.session.myCharz());
                    }
                }
                break;
            }
            case 245: {
                if (this.session.myCharz().itemBlackBall != null && this.session.myCharz().phuHoBlackBall == 0) {
                    if (this.session.myCharz().getLuong() < 30) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 30 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().updateLuong(-30, 2);
                        this.session.myCharz().phuHoBlackBall = 7;
                        this.session.myCharz().updateAll();
                        this.session.service.meLoadPoint();
                        this.session.myCharz().cHP = this.session.myCharz().cHPFull;
                        this.session.myCharz().cMP = this.session.myCharz().cMPFull;
                        this.session.service.meLoadHP(this.session.myCharz().cHP);
                        this.session.service.meLoadMP(this.session.myCharz().cMP);
                        this.session.myCharz().zoneMap.playerLoadAll(this.session.myCharz());
                    }
                }
                break;
            }
            case 246: {
                break;
            }
            case 247: {
                if (this.session.myCharz().cgender == 0) {
                    this.session.myCharz().goTramTauTraiDat();
                }
                if (this.session.myCharz().cgender == 1) {
                    this.session.myCharz().goTramTauNamek();
                }
                if (this.session.myCharz().cgender == 2) {
                    this.session.myCharz().goTramTauXayda();
                }
                break;
            }
            case 248: {
                if (this.session.myCharz().ctaskId >= 25 && this.session.myCharz().ctaskIndex >= 0) {
                    this.session.myCharz().transPort(60, 0, null, this.session.myCharz().typeTeleport,
                            this.session.myCharz().typeTeleport, 200, 5);
                } else {
                    this.session.myCharz().addInfo1(mResources.YOUR_NOT_TO);
                }
                break;
            }
            case 2481: {
                this.session.myCharz().goPhongThiNghiem();
                break;
            }
            case 2482: {
                this.session.myCharz().goVachNuiDen();
                break;
            }
            case 2483: {
                this.session.myCharz().goMapNoel();
                break;
            }
            case 2484: {
                this.session.myCharz().goBill2();
                break;
            }
            case 249: {
                this.session.myCharz().shopId = 19;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 250: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_TRUNG_THU_2;
                this.arrMenu.add(new MenuInfo(String.format(mResources.TUY_CHON, 1), 251));
                this.arrMenu.add(new MenuInfo(String.format(mResources.TUY_CHON, 2), 252));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 251: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().getItemBagQuantityById(1045) < 99) {
                    this.session.myCharz().addInfo1(String.format(mResources.CAN_ITEM,
                            99 - this.session.myCharz().getItemBagQuantityById(1045), "đuôi khỉ"));
                } else {
                    this.session.myCharz().useItemBagById(1045, 99);
                    this.session.myCharz().addItemBag(0,
                            new Item(1046, false, 1, ItemOption.getOption(1046, 7, 0), "", "", ""));
                }
                break;
            }
            case 252: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().getItemBagQuantityById(1045) < 99) {
                    this.session.myCharz().addInfo1(String.format(mResources.CAN_ITEM,
                            99 - this.session.myCharz().getItemBagQuantityById(1045), "đuôi khỉ"));
                } else {
                    if (this.session.myCharz().xu < 2000000000L) {
                        this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                                Util.gI().numberTostring(2000000000L - this.session.myCharz().xu)));
                    } else if (this.session.myCharz().getLuong() < 50) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 50 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().updateLuong(-50, 2);
                        this.session.myCharz().updateXu(-2000000000L, 2);
                        this.session.myCharz().useItemBagById(1045, 99);
                        this.session.myCharz().addItemBag(0,
                                new Item(1046, false, 1, ItemOption.getOption(1046, 30, 0), "", "", ""));
                    }
                }
                break;
            }
            case 253: {
                Rank.getRank(3).loadRank();
                this.session.service.top(Rank.getRank(3));
                break;
            }
            case 254: {
                this.session.myCharz().goKaiO();
                break;
            }
            case 255: {
                this.session.myCharz().goPlanetBill();
                break;
            }
            case 256: {
                if (Server.gI().isButcher) {
                    this.session.myCharz().goPortSpace1();
                }
                break;
            }
            case 257: {
                break;
            }
            case 258: {
                if (this.session.myCharz().getLuong() < 1) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_NGOC, 1));
                } else {
                    this.session.myCharz().updateLuong(-1, 2);
                    this.session.myCharz().isSeal = false;
                    this.session.myCharz().updateAll();
                    this.session.service.meLoadPoint();
                }
                break;
            }
            case 259: {
                this.session.myCharz().goHome();
                break;
            }
            case 260: {
                this.session.myCharz().downRoom();
                break;
            }
            case 261: {
                if (this.session.myCharz().getLuong() >= 10) {
                    this.session.myCharz().updateLuong(-10, 2);
                    this.session.myCharz().isBlKaio = true;
                    this.session.myCharz().updateAll();
                    this.session.myCharz().cHP = this.session.myCharz().cHPFull;
                    this.session.myCharz().cMP = this.session.myCharz().cMPFull;
                    this.session.myCharz().zoneMap.playerLoadHP(this.session.myCharz(), this.session.myCharz().cHPFull);
                    this.session.service.meLoadPoint();
                }
                break;
            }
            case 262: {
                this.session.myCharz().goDaiHoi2();
                break;
            }
            case 263: {
                if (Server.gI().isMabu) {
                    this.session.myCharz().goPortSpace2();
                }
                break;
            }
            case 264: {
                this.session.myCharz().shopId = 27;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 265: {
                this.session.myCharz().goPrisonPalnet();
                break;
            }
            case 266: {
                Item item = this.session.myCharz().getItemBag(541);
                if (item == null) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 541).name));
                } else {
                    Npc npc = this.session.myCharz().zoneMap.findNPCInMap(48);
                    if (npc != null) {
                        this.session.myCharz().useItemBag(item.indexUI, 1);
                        int toX = Util.gI().nextInt(npc.cx - 70, npc.cx + 70);
                        int toY = this.session.myCharz().zoneMap.mapTemplate.touchY(toX, npc.cy - 10);
                        int templateId = Util.gI().nextInt(537, 540);
                        ItemMap itemMap = this.session.myCharz().zoneMap.addItemMap(this.session.myCharz().charID,
                                new Item(templateId, false, 1, ItemOption.getOption(templateId, 0, 0), mResources.EMPTY,
                                        mResources.EMPTY, mResources.EMPTY),
                                toX, toY, 0, -1);
                        this.session.myCharz().zoneMap.itemMapAdd(itemMap);
                        this.session.myCharz().zoneMap.npcChat(npcId, mResources.NGO_KHONG_CHAT_1);
                        Rank.getRank(4).addTop(this.session.myCharz().cName, this.session.myCharz().head,
                                this.session.myCharz().headICON, this.session.myCharz().body,
                                this.session.myCharz().leg, this.session.myCharz().charID,
                                ++this.session.myCharz().pointEvent, -1);
                    }
                }
                break;
            }
            case 267: {
                Item item = this.session.myCharz().getItemBag(542);
                if (item == null) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 542).name));
                } else {
                    Npc npc = this.session.myCharz().zoneMap.findNPCInMap(48);
                    if (npc != null) {
                        this.session.myCharz().useItemBag(item.indexUI, 1);
                        int toX = Util.gI().nextInt(npc.cx - 70, npc.cx + 70);
                        int toY = this.session.myCharz().zoneMap.mapTemplate.touchY(toX, npc.cy - 10);
                        int templateId = Util.gI().nextInt(539, 540);
                        ItemMap itemMap = this.session.myCharz().zoneMap.addItemMap(this.session.myCharz().charID,
                                new Item(templateId, false, 1, ItemOption.getOption(templateId, 0, 0), mResources.EMPTY,
                                        mResources.EMPTY, mResources.EMPTY),
                                toX, toY, 0, -1);
                        this.session.myCharz().zoneMap.itemMapAdd(itemMap);
                        this.session.myCharz().zoneMap.npcChat(npcId, mResources.NGO_KHONG_CHAT_1);
                        Rank.getRank(4).addTop(this.session.myCharz().cName, this.session.myCharz().head,
                                this.session.myCharz().headICON, this.session.myCharz().body,
                                this.session.myCharz().leg, this.session.myCharz().charID,
                                ++this.session.myCharz().pointEvent, -1);
                    }
                }
                break;
            }
            case 268: {
                this.session.myCharz().goMarble();
                break;
            }
            case 269: {
                break;
            }
            case 270: {
                this.session.myCharz().goAru();
                break;
            }
            case 271: {
                this.session.myCharz().unseal();
                break;
            }
            case 272: {
                Rank.getRank(4).loadRank();
                this.session.service.top(Rank.getRank(4));
                break;
            }
            case 273: {
                if (!Server.gI().isBigBoss) {
                    this.session.myCharz().addInfo1(mResources.WAIT_BIG_BOSS);
                } else {
                    this.session.myCharz().goSanTaCity();
                }
                break;
            }
            case 274: {
                this.session.myCharz().goVegetaCity2();
                break;
            }
            case 275: {
                if (!this.session.myCharz().isFullTBT || this.session.myCharz().indexUIFoods99() == -1) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_BILL_2;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                } else {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_BILL_3;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 276));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 276: {
                this.session.myCharz().shopId = 17;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                // Canh bao
                this.session.myCharz().resetMenu();
                this.chat = mResources.HUONG_DAN_2;
                this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 277: {
                this.session.myCharz().goSanKaio2();
                break;
            }
            case 278: {
                this.session.myCharz().goYardart();
                break;
            }
            case 279: {
                this.session.myCharz().goMonkeyGold();
                break;
            }
            case 280: {
                Item item = this.session.myCharz().getItemBag(590);
                if (item != null && item.getOption(31).param >= 9999) {
                    if (this.session.myCharz().getEmptyBagCount() > 0) {
                        this.session.myCharz().addQuantityItemBag(item.indexUI, -9999);
                        Item itemNew = null;
                        if (this.session.myCharz().cgender == 0) {
                            itemNew = new Item(592, false, 1, ItemOption.getOption(592, 0, -1), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        }
                        if (this.session.myCharz().cgender == 1) {
                            itemNew = new Item(593, false, 1, ItemOption.getOption(593, 0, -1), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        }
                        if (this.session.myCharz().cgender == 2) {
                            itemNew = new Item(594, false, 1, ItemOption.getOption(594, 0, -1), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        }
                        if (itemNew != null) {
                            this.session.myCharz().addItemBag(0, itemNew);
                        }
                    } else {
                        this.session.myCharz().addInfo1(mResources.BAG_FULL);
                    }
                } else {
                    this.session.myCharz().addInfo1(mResources.NOT_FIND);
                }
                break;
            }
            case 281: {
                if (this.session.myCharz().cPower < 200000000) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.YEU_CAU_SUC_MANH, Util.gI().numberTostring(200000000)));
                } else {
                    RongVoCuc.gI().addWish(this.session.myCharz().playerId, info.index);
                }
                break;
            }
            case 282: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THO_DAI_CA_2;
                this.arrMenu.add(new MenuInfo(mResources.AGREE, 283));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 283: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (!(boolean) this.session.myCharz().valueById(2)) {
                    this.session.myCharz().setValue(2, true);
                    this.session.myCharz().wish();
                    //
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_THO_DAI_CA_3;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 284: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THO_DAI_CA_4;
                this.arrMenu.add(new MenuInfo(mResources.AGREE, 285));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 285: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().getLuong() < 50) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.CONTHIEU_NGOC, 50 - this.session.myCharz().getLuong()));
                } else {
                    this.session.myCharz().updateLuong(-50, 2);
                    this.session.myCharz().wish();
                    //
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_THO_DAI_CA_3;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 286: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THO_DAI_CA_5;
                this.arrMenu.add(new MenuInfo(mResources.AGREE, 287));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 287: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().getItemBagQuantityById(462) < 99) {
                    this.session.myCharz().addInfo1(String.format(mResources.CAN_ITEM,
                            99 - this.session.myCharz().getItemBagQuantityById(462), "củ cà rốt"));
                } else {
                    this.session.myCharz().useItemBagById(462, 99);
                    this.session.myCharz().wish();
                    //
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_THO_DAI_CA_3;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 288: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_THO_DAI_CA_6;
                this.arrMenu.add(new MenuInfo(mResources.AGREE, 289));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 289: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                    this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, 1));
                } else if (this.session.myCharz().getItemBagQuantityById(457) < 1) {
                    this.session.myCharz().addInfo1(String.format(mResources.CAN_ITEM,
                            1 - this.session.myCharz().getItemBagQuantityById(457), "thỏi vàng"));
                } else {
                    this.session.myCharz().useItemBagById(457, 1);
                    this.session.myCharz().wish();
                    //
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_THO_DAI_CA_3;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 0));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 290: {
                if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 2) {
                    this.session.myCharz().nextTask(3);
                }
            }
            case 291: {
                if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 4) {
                    this.session.myCharz().nextTask(3);
                }
            }
            case 292: {
                if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 6) {
                    this.session.myCharz().nextTask(3);
                }
            }
            case 293: {
                if (this.session.myCharz().ctaskId == 34 && this.session.myCharz().ctaskIndex == 5) {
                    this.session.myCharz().nextTask(2);
                }
                break;
            }
            case 294: {
                if (!this.session.myCharz().isGoiRong) {
                    this.session.myCharz().menuRong();
                }
                break;
            }
            case 295: {
                this.session.myCharz().selectRong = info.index;
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_RONG_THAN_3;
                this.arrMenu
                        .add(new MenuInfo(String.format(mResources.DIEU_UOC, this.session.myCharz().selectRong), 297));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 296));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 296: {
                if (this.session.myCharz().isGoiRong) {
                    this.session.myCharz().openMenuRong();
                }
                break;
            }
            case 297: {
                if (this.session.myCharz().isGoiRong) {
                    this.session.myCharz().finishRong();
                }
                break;
            }
            case 298: {
                int nShooot = this.session.myCharz().clientInputInt;
                if (nShooot > 0) {
                    if (nShooot > this.session.myCharz().phaohoa.quantity) {
                        nShooot = this.session.myCharz().phaohoa.quantity;
                    }
                    this.session.myCharz().shootHoaDangLoiChuc(nShooot, this.session.myCharz().phaohoa,
                            dragon.u.PhaoHoa.ARRAY_LOI_CHUC[info.index]);
                }
                break;
            }
            case 299: {
                MagicTree.upMagicTree(this.session.myCharz(), 0);
                break;
            }
            case 300: {
                // Thach dau
                int gia = info.index;
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().zoneMap.map.isMapCace23_2()) {
                    this.session.myCharz().addInfo1(mResources.NOT_THACH_DAU);
                } else if (this.session.myCharz().challengeCharId != -9999) {
                    Char player122 = this.session.myCharz().zoneMap
                            .findCharInMap(this.session.myCharz().challengeCharId);
                    if (player122 != null && player122.me && !player122.isTemplate && !player122.isDie
                            && !player122.isChallenge) {
                        player122.session.service.player_vs_player(3, this.session.myCharz().charID, 0,
                                String.format(mResources.CHALLENGE_PLAYERS, this.session.myCharz().cName,
                                        Util.gI().numberTostring(this.session.myCharz().cPower), 0));
                    }
                }
                break;
            }
            case 301: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clanMember.role == 0) {
                    this.session.myCharz().addInfo1(mResources.NOT_BC);
                } else if (this.session.myCharz().clan != null) {
                    this.session.myCharz().clan.removeMember(null, this.session.myCharz().charID);
                }
                break;
            }
            case 302: {
                this.session.service.speacialSkill(this.session.myCharz().cgender);
                break;
            }
            case 303: {
                this.session.myCharz().resetMenu();
                this.chat = String.format(mResources.SAY_NOI_TAI2,
                        Util.gI().numberTostring(this.session.myCharz().getCoinNT()));
                this.arrMenu.add(new MenuInfo(mResources.OPEN_NOITAI, 304));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(5, null, null, -1);
                break;
            }
            case 304: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().cPower < 5_000_000_000L) {
                    this.session.myCharz().addInfo1(String.format(mResources.CAN_SUC_MANH,
                            Util.gI().numberTostring(5_000_000_000L - this.session.myCharz().cPower)));
                } else if (this.session.myCharz().xu < this.session.myCharz().getCoinNT()) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                            Util.gI().numberTostring(this.session.myCharz().getCoinNT() - this.session.myCharz().xu)));
                } else {
                    this.session.myCharz().cspeacialSkill = Util.gI()
                            .nextInt(Panel.gI().infoSpeacialSkill[this.session.myCharz().cgender].length);
                    this.session.myCharz().paramSpeacialSkill = Util.gI().nextInt(
                            Panel.gI().nextSpeacialSkill[this.session.myCharz().cgender][this.session
                            .myCharz().cspeacialSkill][0],
                            Panel.gI().nextSpeacialSkill[this.session.myCharz().cgender][this.session
                            .myCharz().cspeacialSkill][1]);
                    this.session.myCharz().updateXu(-this.session.myCharz().getCoinNT(), 2);
                    this.session.myCharz().ncoinSpeacialSkill++;
                    this.session.service.speacialSkill(this.session.myCharz().cgender,
                            this.session.myCharz().cspeacialSkill, this.session.myCharz().paramSpeacialSkill);
                    if (this.session.myCharz().cgender == 0 && this.session.myCharz().cspeacialSkill == 2) {
                        this.session.myCharz()
                                .addInfo1(String.format(mResources.ND_NT, String.format(
                                        Panel.gI().infoSpeacialSkill[this.session.myCharz().cgender][this.session
                                        .myCharz().cspeacialSkill],
                                        String.format(Panel.gI().numP, this.session.myCharz().paramSpeacialSkill),
                                        String.format(Panel.gI().numP, this.session.myCharz().paramSpeacialSkill),
                                        mResources.EMPTY)));
                    } else {
                        this.session.myCharz()
                                .addInfo1(String.format(mResources.ND_NT, String.format(
                                        Panel.gI().infoSpeacialSkill[this.session.myCharz().cgender][this.session
                                        .myCharz().cspeacialSkill],
                                        String.format(Panel.gI().numP, this.session.myCharz().paramSpeacialSkill),
                                        mResources.EMPTY)));
                    }
                }
                break;
            }
            case 305: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_NOI_TAI3;
                this.arrMenu.add(new MenuInfo(mResources.OPEN_NOITAI_VIP, 306));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(5, null, null, -1);
                break;
            }
            case 306: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().cPower < 100000) {
                    this.session.myCharz().addInfo1(String.format(mResources.CAN_SUC_MANH,
                            Util.gI().numberTostring(100000 - this.session.myCharz().cPower)));
                } else if (this.session.myCharz().getLuong() < 100) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.CONTHIEU_NGOC, 100 - this.session.myCharz().getLuong()));
                } else {
                    this.session.myCharz().cspeacialSkill = Util.gI()
                            .nextInt(Panel.gI().infoSpeacialSkill[this.session.myCharz().cgender].length);
                    this.session.myCharz().paramSpeacialSkill = Util.gI().nextInt(
                            Panel.gI().nextSpeacialSkill[this.session.myCharz().cgender][this.session
                            .myCharz().cspeacialSkill][0],
                            Panel.gI().nextSpeacialSkill[this.session.myCharz().cgender][this.session
                            .myCharz().cspeacialSkill][1]);
                    this.session.myCharz().updateLuong(-100, 2);
                    this.session.myCharz().ncoinSpeacialSkill = 0;
                    this.session.service.speacialSkill(this.session.myCharz().cgender,
                            this.session.myCharz().cspeacialSkill, this.session.myCharz().paramSpeacialSkill);
                    if (this.session.myCharz().cgender == 0 && this.session.myCharz().cspeacialSkill == 2) {
                        this.session.myCharz()
                                .addInfo1(String.format(mResources.ND_NT, String.format(
                                        Panel.gI().infoSpeacialSkill[this.session.myCharz().cgender][this.session
                                        .myCharz().cspeacialSkill],
                                        String.format(Panel.gI().numP, this.session.myCharz().paramSpeacialSkill),
                                        String.format(Panel.gI().numP, this.session.myCharz().paramSpeacialSkill),
                                        mResources.EMPTY)));
                    } else {
                        this.session.myCharz()
                                .addInfo1(String.format(mResources.ND_NT, String.format(
                                        Panel.gI().infoSpeacialSkill[this.session.myCharz().cgender][this.session
                                        .myCharz().cspeacialSkill],
                                        String.format(Panel.gI().numP, this.session.myCharz().paramSpeacialSkill),
                                        mResources.EMPTY)));
                    }
                }
                break;
            }
            case 307: {
                this.session.myCharz().nLuckyIndex = info.index;
                this.session.myCharz().clientInput.openClientInput(5,
                        String.format(mResources.INPUT_TILE_LUCKY_NUMBER,
                                LuckyNumber.gI().arrNumber[this.session.myCharz().nLuckyIndex][0],
                                LuckyNumber.gI().arrNumber[this.session.myCharz().nLuckyIndex][1]),
                        new String[]{mResources.INPUT_SELECT_LUCKY_NUMBER}, new int[]{0});
                break;
            }
            case 308: {
                if (this.session.myCharz().securityCode == -1) {
                    if (this.session.myCharz().getLuong() < 1000) {
                        this.session.myCharz().addInfo1(
                                String.format(mResources.CONTHIEU_NGOC, 1000 - this.session.myCharz().getLuong()));
                    } else {
                        this.session.myCharz().updateLuong(-1000, 2);
                        this.session.myCharz().securityCode = this.session.myCharz().securityCode2;
                        this.session.myCharz().timeSecurity = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7);
                        this.session.myCharz()
                                .addInfo1(String.format(mResources.CODE_OK, this.session.myCharz().securityCode2));
                        this.session.myCharz().setLockInventory(this.session.myCharz().securityCode2);
                    }
                }
                break;
            }
            case 309: {
                if (this.session.myCharz().securityCode != -1
                        && this.session.myCharz().securityCode == this.session.myCharz().securityCode2) {
                    this.session.myCharz().isSecurity = !this.session.myCharz().isSecurity;
                    this.session.myCharz().setLockInventory(this.session.myCharz().securityCode2);
                }
                break;
            }
            case 310: {
                if (this.session.myCharz().securityCode != -1
                        && this.session.myCharz().securityCode == this.session.myCharz().securityCode2) {
                    this.session.myCharz().clientInput.openClientInput(1, mResources.CODE_NEW,
                            new String[]{mResources.CODE_INFO}, new int[]{0});
                }
                break;
            }
            case 311: {
                if (this.session.myCharz().securityCode != -1
                        && this.session.myCharz().securityCode == this.session.myCharz().securityCode2) {
                    this.session.myCharz().securityCode = -1;
                    this.session.myCharz().securityCode2 = 0;
                    this.session.myCharz().isSecurity = false;
                    this.session.myCharz().addInfo1(mResources.CANCEL_CODE_OK);
                }
                break;
            }
            case 312: {
                MagicTree.Harvest_beans(this.session.myCharz());
                break;
            }
            case 313: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().magicTree_isUpdate) {
                    if (MagicTree.up_nhanh[this.session.myCharz().magicTree_level - 1] > this.session.myCharz()
                            .getLuong()) {

                    } else {
                        this.session.myCharz()
                                .updateLuong(-MagicTree.up_nhanh[this.session.myCharz().magicTree_level - 1], 2);
                        this.session.myCharz().magicTree_level++;
                        this.session.myCharz().magicTree_isUpdate = false;
                        this.session.myCharz().magicTree_miliseconds = System.currentTimeMillis()
                                + (MagicTree.timePeas[this.session.myCharz().magicTree_level - 1]);
                        this.session.myCharz().session.service.magicTree();
                    }
                }
                break;
            }
            case 314: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().magicTree_isUpdate) {
                    this.session.myCharz().magicTree_isUpdate = false;
                    this.session.myCharz().magicTree_miliseconds = System.currentTimeMillis()
                            + (MagicTree.timePeas[this.session.myCharz().magicTree_level - 1]);
                    this.session.myCharz().updateXu(MagicTree.up_coin[this.session.myCharz().magicTree_level - 1] / 2,
                            2);
                    this.session.myCharz().session.service.magicTree();
                }
                break;
            }
            case 315: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.OK_OR_NOT_UP_SAY;
                this.arrMenu.add(new MenuInfo(mResources.OK, 299));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(5, null, null, -1);
                break;
            }
            case 316: {
                MagicTree.ketHat(this.session.myCharz());
                break;
            }
            case 317: {
                this.session.service.getFlag();
                this.session.service.npcChat(this.npcId, mResources.CHAT_WISH2);
                this.session.service.setCombineEff(7,
                        Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].iconId, -1,
                        this.npcId);
                this.session.myCharz().addInfo1(3000, String.format(mResources.HOC_DUOC_SKILL,
                        Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].name));
                if (this.session.myCharz().getSkill((int) Char.ARRHOCSKILL[this.session.myCharz().cgender]) == null) {
                    this.session.myCharz().skills.add(
                            Skill.arrSkillTemplate[Char.ARRHOCSKILL[this.session.myCharz().cgender]].skills[0].clone());
                }
                this.session.service.meLoadSkill(this.session.myCharz().skills);
                this.session.myCharz().resetMenu();
                break;
            }
            case 318: {
                if (this.session.myCharz().myPet4 == null && this.session.myCharz().isMonkeyCheat) {
                    this.session.myCharz().addDuongTang();
                }
                break;
            }
            case 319: {
                if (this.session.myCharz().myPet != null && this.session.myCharz().myPetz().timeVeNha == 0) {
                    this.session.myCharz().myObj().isHideFusion = !this.session.myCharz().myObj().isHideFusion;
                    this.session.myCharz().updateAll();
                    this.session.myCharz().zoneMap.playerLoadAll(this.session.myCharz());
                    this.session.myCharz().session.service.updateBody(1, this.session.myCharz().charID,
                            this.session.myCharz().head, this.session.myCharz().body, this.session.myCharz().leg,
                            this.session.myCharz().isMonkey);
                    this.session.myCharz().zoneMap.addEffectServer(5, 3, 21, this.session.myCharz().cx,
                            this.session.myCharz().cy, 1);
                    if (this.session.myCharz().myObj().isHideFusion) {
                        this.session.myCharz().addInfo1(mResources.ON_FUSION2);
                    } else {
                        this.session.myCharz().addInfo1(mResources.OFF_FUSION3);
                    }
                }
                break;
            }
            case 320: {
                if (!this.session.myCharz().isHaveItemBag(880, 881, 882)) {
                    this.session.myCharz().addInfo1(mResources.REQUEST_FODS);
                } else {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_WISH2;
                    for (int i = 0; i < this.session.myCharz().arrItemBag.length; i++) {
                        if (this.session.myCharz().arrItemBag[i] != null
                                && this.session.myCharz().arrItemBag[i].isItemPractice()) {
                            this.arrMenu.add(new MenuInfo(String.format(mResources.DONATE_FODS2,
                                    this.session.myCharz().arrItemBag[i].template.name), 321, i));
                        }
                    }
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(5, null, null, -1);
                }
                break;
            }
            case 321: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (info.index >= 0 && info.index < this.session.myCharz().arrItemBag.length
                        && this.session.myCharz().arrItemBag[info.index] != null
                        && this.session.myCharz().arrItemBag[info.index].isItemPractice()) {
                    this.session.myCharz().useItemBag(info.index, 1);
                    Memory.get(this.session.userId).nFreeWish++;
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_WISH5;
                    this.arrMenu.add(new MenuInfo(mResources.OK, 169));
                    this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                    this.openUIConfirm(5, null, null, -1);
                }
                break;
            }
            case 322: {
                this.session.myCharz().resetMenu();
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.roadSnake != null) {
                    this.chat = String.format(mResources.SAY_THAN_VU_TRU3, this.session.myCharz().clan.roadSnake.level,
                            Util.gI().getStrTime(
                                    System.currentTimeMillis() - this.session.myCharz().clan.roadSnake.lastOpen));
                } else {
                    this.chat = mResources.SAY_THAN_VU_TRU1;
                }
                this.arrMenu.add(new MenuInfo(mResources.TOP_CLAN, 323));
                if (this.session.myCharz().clan != null) {
                    this.arrMenu.add(new MenuInfo(mResources.THANH_TICH_BANG, 324));
                    if (this.session.myCharz().clan.roadSnake != null) {
                        this.arrMenu.add(new MenuInfo(mResources.AGREE, 327));
                    } else {
                        this.arrMenu.add(new MenuInfo(mResources.SELECT_LEVEL2, 325));
                    }
                }
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(5, null, null, -1);
                break;
            }
            case 323: {
                Rank.getRank(7).loadRank();
                this.session.service.top(Rank.getRank(7));
                break;
            }
            case 325: {
                this.session.myCharz().clientInput.openClientInput(23, mResources.SELECT_LEVEL,
                        new String[]{mResources.LEVEL}, new int[]{0});
                break;
            }
            case 326: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.roadSnake == null
                        && info.index > 0 && info.index <= 110) {
                    if (Memory.get(this.session.userId).timeWaitRoadSnake > System.currentTimeMillis()) {
                        this.session.myCharz().addInfo1(String.format(mResources.WAIT_PHOBAN, Util.gI().getStrTime(
                                Memory.get(this.session.userId).timeWaitRoadSnake - System.currentTimeMillis())));
                    } else {
                        // Init
                        RoadSnake randoc = new RoadSnake();
                        randoc.init(this.session.myCharz().clan, info.index);
                        Server.gI().add(randoc);
                        ZoneMap zone = randoc.maps.get(0).getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                            zone.join(this.session.myCharz(), 0, 1555, 336);
                            this.session.myCharz().setText(0, mResources.ROAD_SNAKE, randoc.miliTime / 1000, 0, 0);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                        if (randoc.join(this.session.myCharz().playerId)) {
                            Memory.get(this.session.userId).timeWaitRoadSnake = System.currentTimeMillis() + 86400000L;
                        }
                    }
                }
                break;
            }
            case 327: {
                if (this.session.myCharz().clan != null && this.session.myCharz().clan.roadSnake != null) {
                    RoadSnake randoc = this.session.myCharz().clan.roadSnake;
                    if (Memory.get(this.session.userId).timeWaitRoadSnake > System.currentTimeMillis()
                            && !randoc.isJoin(this.session.myCharz().playerId)) {
                        this.session.myCharz().addInfo1(String.format(mResources.WAIT_PHOBAN, Util.gI().getStrTime(
                                Memory.get(this.session.userId).timeWaitRoadSnake - System.currentTimeMillis())));
                    } else {
                        ZoneMap zone = randoc.maps.get(0).getZone(this.session.myCharz());
                        if (zone != null) {
                            this.session.myCharz().zoneMap.exit(this.session.myCharz(), 0);
                            zone.join(this.session.myCharz(), 0, 1555, 336);
                            this.session.myCharz().setText(0, mResources.ROAD_SNAKE, randoc.miliTime / 1000, 0, 0);
                        } else {
                            this.session.service.startOKDlg(mResources.MAP_OVERLOAD);
                        }
                        if (randoc.join(this.session.myCharz().playerId)) {
                            Memory.get(this.session.userId).timeWaitRoadSnake = System.currentTimeMillis() + 86400000L;
                        }
                    }
                }
                break;
            }
            case 328: {
                if (this.session.myCharz().mapTemplateId == 141
                        && this.session.myCharz().zoneMap.map.phoban.getMap(144).isOpen) {
                    this.session.myCharz().goThanDien2();
                    this.session.myCharz().isNhanDauThan = true;
                    this.session.myCharz().addInfo1(mResources.GAP_MEO_KARIN);
                }
                break;
            }
            case 329: {
                if (this.session.myCharz().isNhanDauThan) {
                    this.session.myCharz().isNhanDauThan = false;
                    this.session.myCharz().addInfo1(mResources.FLY_LEG_KARIN);
                    this.session.myCharz().addItemBag(1,
                            new Item(MagicTree.templateId[this.session.myCharz().magicTree_level - 1], false, 2,
                                    ItemOption.getOption(
                                            MagicTree.templateId[this.session.myCharz().magicTree_level - 1], 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                }
                break;
            }
            case 330: {
                if (this.session.myCharz().isCallCumber == 2) {
                    if (this.session.myCharz().zoneMap.isHaveBoss(158)
                            || this.session.myCharz().zoneMap.isHaveBoss(159)) {
                        this.session.myCharz().isCallCumber = 0;
                        this.session.myCharz().addChat(1000, mResources.CHAT_ME_ARR.split("\\|")[4]);
                    } else if (System.currentTimeMillis() - this.session.myCharz().zoneMap.lastCumber < 14400000L) {
                        this.session.myCharz().addInfo1(String.format(mResources.WAIT_CUMBER1, Util.gI().getFormatTime4(
                                14400000L - (System.currentTimeMillis() - this.session.myCharz().zoneMap.lastCumber))));
                    } else {
                        this.session.myCharz().zoneMap.lastCumber = System.currentTimeMillis();
                        this.session.myCharz().isCallCumber = 0;
                        this.session.myCharz().addChat(1000, mResources.CHAT_ME_ARR.split("\\|")[4]);
                        this.session.myCharz().addChat(4000, mResources.CHAT_ME_ARR.split("\\|")[5]);
                        this.session.myCharz().zoneMap.npcChat(this.npcId, mResources.CHAT_FU_ARR.split("\\|")[6]);
                        Player cumber = Player.addBoss(158, 5, -1, -1, true, 670, 300, this.session.myCharz().zoneMap,
                                -1, -1);
                        cumber.gong(3000);
                        cumber.addChat(3000, mResources.CHAT_CUMBER1);
                        cumber.timeLoadSkill = 5000;
                        cumber.isSendHPData = true;
                        cumber.maxTimeEff = 4000;
                        Server.gI().chatVip(String.format(mResources.BOSS_HAVE, cumber.cName,
                                this.session.myCharz().zoneMap.mapTemplate.mapName));
                    }
                }
                break;
            }
            case 331: {
                this.session.myCharz().isItemMore = 2;
                this.session.service.openItemMore(this.session.myCharz().arrItemMore2);
                break;
            }
            case 332: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_SUKIEN1;
                this.arrMenu.add(new MenuInfo(mResources.MAKE_BATHTUB1, 333));
                this.arrMenu.add(new MenuInfo(mResources.MAKE_BATHTUB2, 335));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 333: {
                this.session.myCharz().requestOpenUIItem(npcId, mResources.MAKE_BATHTUB3);
                // this.session.myCharz().requestOpenUIItem(this.npcId,
                // mResources.MAKE_BATHTUB1, new int[]{1237,1238, 1239, 1240}, new int[]{50, 20,
                // 20, 2}, 334, mResources.AGREE, 0, mResources.REFUSE, 5000000, -1, 1, true,
                // "");
                break;
            }
            case 334: {
                this.session.service.getFlag();
                this.session.service.npcChat(this.npcId, mResources.CHAT_MAKE);
                this.session.service.setCombineEff(7, 11250, -1, this.npcId);
                this.session.myCharz().addItemBag(0, new Item(1241, false, 1, ItemOption.getOption(1241, 0, 0),
                        mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                this.session.myCharz().resetMenu();
                break;
            }
            case 335: {
                this.session.myCharz().requestOpenUIItem(npcId, mResources.MAKE_BATHTUB4);
                // this.session.myCharz().requestOpenUIItem(this.npcId,
                // mResources.MAKE_BATHTUB2, new int[]{1237,1238, 1239, 1240}, new int[]{50, 20,
                // 20, 2}, 336, mResources.AGREE, 0, mResources.REFUSE, 10000000, 5, 1, true,
                // "");
                break;
            }
            case 336: {
                this.session.service.getFlag();
                this.session.service.npcChat(this.npcId, mResources.CHAT_MAKE);
                this.session.service.setCombineEff(7, 11251, -1, this.npcId);
                this.session.myCharz().addItemBag(0, new Item(1242, false, 1, ItemOption.getOption(1242, 0, 0),
                        mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                this.session.myCharz().resetMenu();
                break;
            }
            case 337: {
                this.session.service.getFlag();
                this.session.service.setCombineEff(6, 11257, -1, -1);
                this.session.service.setCombineEff(7, 11258, -1, -1);
                Char bot = Player.addBoss(160, 5, -1, -1, true, 400, 150, null, -1, -1);
                bot.joinZone(this.session.myCharz().zoneMap, 400, 150, 0, 5000);
                bot.vatChuId = this.session.myCharz().charID;
                bot.timeClear = 600000;
                this.session.myCharz().setItem(433, bot.timeClear / 1000, 0, 0);
                break;
            }
            case 338: {
                this.session.service.getFlag();
                this.session.service.setCombineEff(6, 11258, -1, -1);
                this.session.service.setCombineEff(7, 11258, -1, -1);
                Char bot = Player.addBoss(161, 5, -1, -1, true, 400, 150, null, -1, -1);
                bot.joinZone(this.session.myCharz().zoneMap, 400, 150, 0, 5000);
                bot.vatChuId = this.session.myCharz().charID;
                bot.timeClear = 600000;
                this.session.myCharz().setItem(433, bot.timeClear / 1000, 0, 0);
                break;
            }
            case 339: {
                Item item = this.session.myCharz().getItemBag(1248);
                if (item != null) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                        this.session.myCharz().addInfo1(mResources.BAG_FULL);
                    } else {
                        this.session.myCharz().useItemBag(item.indexUI, 1);
                        Item itemnew;
                        // Qua bo canh cung
                        if (Util.gI().nextInt(100) < 20) {
                            itemnew = new Item(883, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(50, Util.gI().nextInt(25, Util.gI().nextInt(28, 33))));
                            itemnew.options.add(new ItemOption(77, Util.gI().nextInt(25, Util.gI().nextInt(28, 33))));
                            itemnew.options.add(new ItemOption(103, Util.gI().nextInt(25, Util.gI().nextInt(28, 33))));
                            itemnew.options.add(new ItemOption(94, Util.gI().nextInt(5, 15)));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 30) {
                            itemnew = new Item(884, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(5, Util.gI().nextInt(30, Util.gI().nextInt(40,
                                    Util.gI().nextInt(50, Util.gI().nextInt(50, Util.gI().nextInt(55, 90)))))));
                            itemnew.options.add(new ItemOption(50, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(14, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 2)));
                        } else if (Util.gI().nextInt(100) < 10) {
                            itemnew = new Item(701, false, Util.gI().nextInt(1, 2), ItemOption.getOption(701, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 5) {
                            itemnew = new Item(16, false, 1, ItemOption.getOption(16, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 5) {
                            int id = Util.gI().nextInt(712, 716);
                            itemnew = new Item(id, false, 1, ItemOption.getOption(id, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 10) {
                            itemnew = new Item(740, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(50, Util.gI().nextInt(3, Util.gI().nextInt(8, 12))));
                            itemnew.options.add(new ItemOption(77, Util.gI().nextInt(3, Util.gI().nextInt(8, 12))));
                            itemnew.options.add(new ItemOption(103, Util.gI().nextInt(3, Util.gI().nextInt(8, 12))));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 2)));
                        } else if (Util.gI().nextInt(100) < 1) {
                            itemnew = new Item(883, false, 1, ItemOption.getOption(883, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(150) < 1) {
                            itemnew = new Item(884, false, 1, ItemOption.getOption(884, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 2) {
                            itemnew = new Item(740, false, 1, ItemOption.getOption(740, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else {
                            itemnew = new Item(883, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(50, Util.gI().nextInt(20, Util.gI().nextInt(25, 30))));
                            itemnew.options.add(new ItemOption(77, Util.gI().nextInt(20, Util.gI().nextInt(25, 30))));
                            itemnew.options.add(new ItemOption(103, Util.gI().nextInt(20, Util.gI().nextInt(25, 30))));
                            itemnew.options.add(new ItemOption(94, Util.gI().nextInt(5, 10)));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        }
                        Rank.getRank(3).addTop(this.session.myCharz().cName, this.session.myCharz().head,
                                this.session.myCharz().headICON, this.session.myCharz().body,
                                this.session.myCharz().leg, this.session.myCharz().charID,
                                this.session.myCharz().pointEvent += 1, -1);
                        this.session.myCharz().addItemBag(0, itemnew);
                        // this.session.service.setCombineEff(new Item[]{item});
                        // this.session.service.getFlag();
                        this.session.service.npcChat(this.npcId, mResources.CHAT_QUYLAO_KAME);
                        // this.session.service.setCombineEff(7, itemnew.template.iconID, -1,
                        // this.npcId);
                    }
                } else {

                }
                break;
            }
            case 340: {
                Item item = this.session.myCharz().getItemBag(1249);
                if (item != null) {
                    if (this.session.myCharz().isgiaodich) {
                        this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                    } else if (this.session.myCharz().isSecurity) {
                        this.session.myCharz().addInfo1(mResources.BAOVE);
                    } else if (this.session.myCharz().getEmptyBagCount() == 0) {
                        this.session.myCharz().addInfo1(mResources.BAG_FULL);
                    } else {
                        this.session.myCharz().useItemBag(item.indexUI, 1);
                        Item itemnew;
                        // Qua ngai dem
                        if (Util.gI().nextInt(100) < 15) {
                            int id = Util.gI().nextInt(1066, 1070);
                            itemnew = new Item(id, false, Util.gI().nextInt(1, 10), ItemOption.getOption(id, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 25) {
                            itemnew = new Item(1243, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(103,
                                    Util.gI().nextInt(7, Util.gI().nextInt(12, Util.gI().nextInt(15, 18)))));
                            itemnew.options.add(new ItemOption(203, Util.gI().nextInt(1, 5)));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 25) {
                            itemnew = new Item(1244, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(77,
                                    Util.gI().nextInt(7, Util.gI().nextInt(12, Util.gI().nextInt(15, 18)))));
                            itemnew.options.add(new ItemOption(94, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 25) {
                            itemnew = new Item(919, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(50,
                                    Util.gI().nextInt(7, Util.gI().nextInt(12, Util.gI().nextInt(15, 18)))));
                            itemnew.options.add(new ItemOption(95, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 5) {
                            itemnew = new Item(16, false, 1, ItemOption.getOption(16, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 10) {
                            itemnew = new Item(987, false, 1, ItemOption.getOption(987, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 5) {
                            itemnew = new Item(1158, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(103,
                                    Util.gI().nextInt(5, Util.gI().nextInt(8, Util.gI().nextInt(10, 17)))));
                            itemnew.options.add(new ItemOption(94, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 5) {
                            itemnew = new Item(1159, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(77,
                                    Util.gI().nextInt(5, Util.gI().nextInt(8, Util.gI().nextInt(10, 17)))));
                            itemnew.options.add(new ItemOption(95, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 5) {
                            itemnew = new Item(1160, false, 1, null, mResources.EMPTY, mResources.EMPTY,
                                    mResources.EMPTY);
                            itemnew.options.add(new ItemOption(50,
                                    Util.gI().nextInt(5, Util.gI().nextInt(8, Util.gI().nextInt(10, 17)))));
                            itemnew.options.add(new ItemOption(14, Util.gI().nextInt(1, 10)));
                            itemnew.options.add(new ItemOption(30, 1));
                            itemnew.options.add(new ItemOption(93, Util.gI().nextInt(1, 3)));
                        } else if (Util.gI().nextInt(100) < 5) {
                            int id = Util.gI().nextInt(712, 716);
                            itemnew = new Item(id, false, 1, ItemOption.getOption(id, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 1) {
                            itemnew = new Item(1204, false, 1, ItemOption.getOption(1204, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);

                        } else if (Util.gI().nextInt(100) < 3) {
                            itemnew = new Item(1158, false, 1, ItemOption.getOption(1158, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 2) {
                            itemnew = new Item(1159, false, 1, ItemOption.getOption(1159, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(100) < 1) {
                            itemnew = new Item(1160, false, 1, ItemOption.getOption(1160, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);

                        } else if (Util.gI().nextInt(100) < 1) {
                            itemnew = new Item(1243, false, 1, ItemOption.getOption(1243, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(120) < 1) {
                            itemnew = new Item(1244, false, 1, ItemOption.getOption(1244, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);
                        } else if (Util.gI().nextInt(130) < 1) {
                            itemnew = new Item(919, false, 1, ItemOption.getOption(919, 0, 0), mResources.EMPTY,
                                    mResources.EMPTY, mResources.EMPTY);

                        } else {
                            int id = Util.gI().nextInt(1066, 1070);
                            itemnew = new Item(id, false, Util.gI().nextInt(1, 10), ItemOption.getOption(id, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);

                        }
                        Rank.getRank(3).addTop(this.session.myCharz().cName, this.session.myCharz().head,
                                this.session.myCharz().headICON, this.session.myCharz().body,
                                this.session.myCharz().leg, this.session.myCharz().charID,
                                this.session.myCharz().pointEvent += 3, -1);
                        this.session.myCharz().addItemBag(0, itemnew);
                        // this.session.service.setCombineEff(new Item[]{item});
                        // this.session.service.getFlag();
                        this.session.service.npcChat(this.npcId, mResources.CHAT_QUYLAO_KAME);
                        // this.session.service.setCombineEff(7, itemnew.template.iconID, -1,
                        // this.npcId);

                    }
                } else {

                }
                break;
            }
            case 341: {
                this.session.myCharz().requestOpenUIItem(npcId, mResources.CHANGE_GIFT2);
                break;
            }
            case 342: {
                Item item;
                if (Util.gI().nextInt(100) < 20) {
                    item = new Item(954, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(77, 5));
                    item.options.add(new ItemOption(95, 5));
                    item.options.add(new ItemOption(30, 1));
                    item.options.add(new ItemOption(93, 3));
                } else if (Util.gI().nextInt(100) < 20) {
                    item = new Item(955, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(50, 5));
                    item.options.add(new ItemOption(94, 5));
                    item.options.add(new ItemOption(30, 1));
                    item.options.add(new ItemOption(93, 3));
                } else {
                    item = new Item(995, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(103, 5));
                    item.options.add(new ItemOption(96, 5));
                    item.options.add(new ItemOption(30, 1));
                    item.options.add(new ItemOption(93, 3));
                }
                this.session.myCharz().addItemBag(0, item);
                break;
            }
            case 343: {
                this.session.myCharz().addItemBag(0, new Item(970, false, 1, ItemOption.getOption(970, 0, 0),
                        mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                break;
            }
            case 344: {
                int id = new int[]{1144, 897}[Util.gI().nextInt(2)];
                this.session.myCharz().addItemBag(0, new Item(id, false, 1, ItemOption.getOption(id, 0, 0),
                        mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                break;
            }
            case 345: {
                Item item;
                if (Util.gI().nextInt(100) < 50) {
                    item = new Item(1039, false, 1, ItemOption.getOption(1039, 0, 0), mResources.EMPTY,
                            mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(93, 3));
                } else {
                    item = new Item(1040, false, 1, ItemOption.getOption(1040, 0, 0), mResources.EMPTY,
                            mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(93, 3));
                }
                this.session.myCharz().addItemBag(0, item);
                break;
            }
            case 346: {
                Item item;
                if (Util.gI().nextInt(100) < 20) {
                    item = new Item(591, false, 1, ItemOption.getOption(591, 0, 0), mResources.EMPTY, mResources.EMPTY,
                            mResources.EMPTY);
                    item.options.add(new ItemOption(93, 3));
                } else {
                    item = new Item(591, false, 1, ItemOption.getOption(591, 0, 0), mResources.EMPTY, mResources.EMPTY,
                            mResources.EMPTY);
                    item.options.add(new ItemOption(93, 3));
                }
                this.session.myCharz().addItemBag(0, item);
                break;
            }
            case 347: {
                this.session.myCharz().shopId = 28;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 348: {
                this.session.myCharz().requestOpenUIItem(npcId, mResources.SAY_MAI2);
                break;
            }
            case 349: {
                if (this.session.myCharz().xu < 5000000000L) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                            Util.gI().numberTostring(5000000000L - this.session.myCharz().xu)));
                } else if (this.session.myCharz().getItemBagQuantityById(1987) < 9) {
                } else {
                    this.session.myCharz().useItemBagById(1987, 9);
                    this.session.myCharz().updateXu(-5000000000L, 2);
                    Item item = new Item(1986, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(50, Util.gI().nextInt(1, Util.gI().nextInt(3, 5))));
                    item.options.add(new ItemOption(77, Util.gI().nextInt(1, Util.gI().nextInt(3, 5))));
                    item.options.add(new ItemOption(103, Util.gI().nextInt(1, Util.gI().nextInt(3, 5))));
                    item.options.add(new ItemOption(30, 1));
                    item.options.add(new ItemOption(93, 15));
                    this.session.myCharz().addItemBag(0, item);
                }
                break;
            }
            case 350: {
                if (this.session.myCharz().xu < 9000000000L) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                            Util.gI().numberTostring(9000000000L - this.session.myCharz().xu)));
                } else if (this.session.myCharz().getItemBagQuantityById(1987) < 99) {
                } else {
                    this.session.myCharz().useItemBagById(1987, 99);
                    this.session.myCharz().updateXu(-9000000000L, 2);
                    Item item = new Item(1986, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(50, Util.gI().nextInt(1, Util.gI().nextInt(2, 5))));
                    item.options.add(new ItemOption(77, Util.gI().nextInt(1, Util.gI().nextInt(2, 5))));
                    item.options.add(new ItemOption(103, Util.gI().nextInt(1, Util.gI().nextInt(2, 5))));
                    item.options.add(new ItemOption(30, 1));
                    this.session.myCharz().addItemBag(0, item);
                }
                break;
            }
            case 351: {
                this.session.service.openUISay(npcId, mResources.SAY_MAI4, 5824);
                break;
            }
            case 352: {
                // Lay item tu index
                this.session.myCharz().nangKiemZ(1,
                        this.session.myCharz().arrItemBag[Integer.parseInt(info.arrayStr0[1])]);
                break;
            }
            case 353: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_MAI3;
                this.arrMenu.add(new MenuInfo(mResources.NHAN_NGAY1, 354));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 354: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().xu < 500000000) {
                    this.session.myCharz().addInfo1(String.format(mResources.CONTHIEU_VANG,
                            Util.gI().numberTostring(500000000 - this.session.myCharz().xu)));
                } else {
                    this.session.myCharz().updateXu(-500000000, 2);
                    this.session.myCharz().removeEffChar(0, 1008);
                    Item item = new Item(395, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(77, 2));
                    item.options.add(new ItemOption(103, 2));
                    item.options.add(new ItemOption(50, 2));
                    this.session.myCharz().addEffectChar(1008, 1, 0, 1, 1, 3600, 1, true, item);
                }
                break;
            }
            case 355: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    int num = Integer.parseInt(info.strMenu.split("\nX")[1]);
                    int indexUI = Integer.parseInt(info.arrayStr0[1]);
                    nangcap:
                    {
                        int i = 1;
                        for (; i <= num; i++) {
                            Object array[] = this.session.myCharz().checkString(info.stm, 0);
                            if (!(boolean) array[0]) {
                                this.session.myCharz().addInfo1(String.format(mResources.STR_NANGCAP5, i));
                                break nangcap;
                            }
                            this.session.myCharz().checkString(info.stm, 1);
                            // Lay item tu index
                            if (this.session.myCharz().nangKiemZ(0, this.session.myCharz().arrItemBag[indexUI]) == 1) {
                                this.session.myCharz().addInfo1(String.format(mResources.STR_NANGCAP6, i));
                                break nangcap;
                            }
                        }
                        this.session.myCharz().addInfo1(String.format(mResources.STR_NANGCAP7, i));
                    }
                    this.session.myCharz().resetMenu();
                }
                break;
            }
            case 356: {

                break;
            }

            case 357: {

                break;
            }
            case 358: {
                this.typeInfo = 357;
                this.session.service.combine(mResources.SRC_SKH_THAN_LINH, mResources.SRC_SKH_THAN_LINH2, -1);
                break;
            }
            case 359: {
                Combine.NangsetkichhoatThanlinh(this.session.myCharz());
                this.typeInfo = 357;
                break;
            }
            case 360: {
                if (this.session.myCharz().mapTemplateId == 5) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_BA_HAT_MIT_DT;
                    this.arrMenu.add(new MenuInfo(mResources.NANG_CAP_DE_TU_BLACK, 362));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }
            case 361: {
                this.session.myCharz().shopId = 33;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 362: {
                this.typeInfo = 361;
                this.session.service.combine(mResources.NANG_DE_TU_BLACK, mResources.NANG_DE_TU_SAY, -1);
                break;
            }
            case 363: {
                Combine.NangcapDetuBlack(this.session.myCharz());
                this.typeInfo = 361;
                break;
            }
            // case 364: {
            // this.session.myCharz().shopId = 33;
            // this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
            // break;
            // }

//            case 364: {
//                this.session.myCharz().resetMenu();
//                this.chat = "Chào con, đây là quà mốc nạp của con!";
//                this.arrMenu.add(new MenuInfo("Nhận\nMốc Nạp", 365));
//                this.arrMenu.add(new MenuInfo("Xem Quà\nMốc Nạp", 366));
//                this.openUIConfirm(npcId, null, null, -1);
//                break;
//            }
//            case 365: {
//                MocNapHandle.instance().showReveice(session);
//                break;
//            }
//            case 366: {
//                MocNapHandle.instance().showReward(session);
//                break;
//            }
           case 367: {
    this.session.myCharz().resetMenu();
    this.chat = mResources.SAY_SUKIEN2;
    this.arrMenu.add(new MenuInfo(String.format(mResources.VIP_NUMBER, 3), 368));
    this.arrMenu.add(new MenuInfo(String.format(mResources.VIP_NUMBER, 2), 369));
    this.arrMenu.add(new MenuInfo(String.format(mResources.VIP_NUMBER, 1), 370));
    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
    this.openUIConfirm(npcId, null, null, -1);
    break;
}

case 368: { // VIP 1 - 2000 VND
    this.session.myCharz().resetMenu();
    this.chat = mResources.SAY_SUKIEN3;

    long vnd = 0;
    try {
        MySQL mySQL = MySQL.createData2();
        try {
            ResultSet red = mySQL.getConnection().prepareStatement(
                    String.format(mResources.QUERY_SELECT_USER_MONEY, this.session.userId),
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            ).executeQuery();
            red.first();
            vnd = red.getLong(1);
        } finally {
            mySQL.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    this.arrMenu.add(new MenuInfo(
            String.format(mResources.POINT_SEASON2, 500000, Util.gI().getFormatNumber(vnd)),
            371, new int[]{0, 1, 0}
    ));
    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
    this.openUIConfirm(npcId, null, null, -1);
    break;
}

case 369: { // VIP 2 - 500 VND
    this.session.myCharz().resetMenu();
    this.chat = mResources.SAY_SUKIEN4;

    long vnd = 0;
    try {
        MySQL mySQL = MySQL.createData2();
        try {
            ResultSet red = mySQL.getConnection().prepareStatement(
                    String.format(mResources.QUERY_SELECT_USER_MONEY, this.session.userId),
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            ).executeQuery();
            red.first();
            vnd = red.getLong(1);
        } finally {
            mySQL.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    this.arrMenu.add(new MenuInfo(
            String.format(mResources.POINT_SEASON2, 100000, Util.gI().getFormatNumber(vnd)),
            371, new int[]{0, 2, 0}
    ));
    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
    this.openUIConfirm(npcId, null, null, -1);
    break;
}

case 370: { // VIP 3 - 200 VND
    this.session.myCharz().resetMenu();
    this.chat = mResources.SAY_SUKIEN5;

    long vnd = 0;
    try {
        MySQL mySQL = MySQL.createData2();
        try {
            ResultSet red = mySQL.getConnection().prepareStatement(
                    String.format(mResources.QUERY_SELECT_USER_MONEY, this.session.userId),
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            ).executeQuery();
            red.first();
            vnd = red.getLong(1);
        } finally {
            mySQL.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    this.arrMenu.add(new MenuInfo(
            String.format(mResources.POINT_SEASON2, 50000, Util.gI().getFormatNumber(vnd)),
            371, new int[]{0, 3, 0}
    ));
    this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
    this.openUIConfirm(npcId, null, null, -1);
    break;
}
case 371: {
    int[] array = (int[]) info.p;
    int vip = this.session.myCharz().myObj().vip_1;

    // Ép mua theo bậc: VIP3 (vip=0) -> VIP2 (vip=1) -> VIP1 (vip=2)
    if (array[1] == 3 && vip != 0) { 
        this.session.myCharz().addInfo1("Mua Rồi Thì Nhịn Đi");
        break;
    }
    if (array[1] == 2 && vip != 1) { 
        this.session.myCharz().addInfo1("Bạn phải mua VIP 1 trước.");
        break;
    }
    if (array[1] == 1 && vip != 2) { 
        this.session.myCharz().addInfo1("Bạn phải mua VIP 2 trước.");
        break;
    }

    // Bước hỏi nhận pet / không nhận pet
    if (array[0] == 0 && this.session.myCharz().myPet != null) {
        this.session.myCharz().resetMenu();
        this.chat = mResources.SAY_SUKIEN6;
        this.arrMenu.add(new MenuInfo(mResources.AGREE, 371, new int[]{1, array[1], 0}));
        this.arrMenu.add(new MenuInfo(mResources.NOT_RECEIVED_PET, 371, new int[]{1, array[1], 1}));
        this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
        this.openUIConfirm(npcId, null, null, -1);
        break;
    }

    // Giá theo gói
    int cost = 0;
    int needEmpty = 15;

    if (array[1] == 1) cost = 500000;      // VIP 1
    else if (array[1] == 2) cost = 100000;  // VIP 2
    else if (array[1] == 3) cost = 50000;  // VIP 3

    if (cost <= 0) {
        this.session.myCharz().addInfo1("Lựa chọn không hợp lệ.");
        break;
    }

    // Check túi trước khi trừ tiền
    if (this.session.myCharz().getEmptyBagCount() < needEmpty) {
        this.session.myCharz().addInfo1(String.format(mResources.BAG_FULL_2, needEmpty));
        break;
    }

    // Check canProceed trước khi trừ tiền
    if (!this.session.myCharz().canProceed()) {
        break;
    }

    try {
        MySQL mySQL = MySQL.createData2(); // nro_root theo bạn nói
        try {
            // Lấy VND hiện có
            ResultSet red = mySQL.getConnection().prepareStatement(
                    String.format(mResources.QUERY_SELECT_USER_MONEY, this.session.userId),
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            ).executeQuery();
            red.first();
            long vnd = red.getLong(1);

            if (vnd < cost) {
                this.session.myCharz().addInfo1(
                        String.format(mResources.MONEY_NOT, Util.gI().getFormatNumber(cost - vnd))
                );
                break;
            }

            mySQL.getConnection().setAutoCommit(false);
            try {
                // Trừ tiền
                mySQL.getConnection().prepareStatement(
                        String.format(mResources.UPDATE_USER_MONEY, -cost, this.session.userId)
                ).executeUpdate();

                // Trao quà
                if (array[1] == 1) this.session.myCharz().giftSeason1(array[2]);
                else if (array[1] == 2) this.session.myCharz().giftSeason2(array[2]);
                else if (array[1] == 3) this.session.myCharz().giftSeason3(array[2]);

                // Nâng bậc VIP (mua thành công)
                int newVip = vip;
                if (array[1] == 3) newVip = 1;
                else if (array[1] == 2) newVip = 2;
                else if (array[1] == 1) newVip = 3;

                this.session.myCharz().myObj().vip_1 = newVip;

                // Update DB player.vip_1 (cùng DB createData2)
                mySQL.getConnection().prepareStatement(
                        "UPDATE `player` SET `vip_1` = " + newVip +
                        " WHERE `playerId` = " + this.session.myCharz().playerId
                ).executeUpdate();

                mySQL.getConnection().commit();
                this.session.isSave = true;

                this.session.myCharz().addInfo1("Mua thành công!");

            } catch (SQLException e) {
                mySQL.getConnection().rollback();
                e.printStackTrace();
            }
        } finally {
            mySQL.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    break;
}
            case 372: {
                break;
            }

            case 397: {
                if (this.session.myCharz().luong < 500000000) {
                    this.session.myCharz().updateLuong(1000000, 2);
                    this.session.myCharz().addInfo1("Bạn vừa nhận 1.000.000 ngọc xanh");
                }
                break;
            }
            // --------------------- đệ tử black -------------------
            case 411: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 1981) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.Blackinfo(this.session.myCharz(), 0);
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 412: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 1981) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.Blackinfo(this.session.myCharz(), 1);
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 413: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 1981) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.Blackinfo(this.session.myCharz(), 2);
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }

            // --------------------- đệ tử thường -------------------
            case 414: {
                if (this.session.myCharz().myPetz() == null) {
                    this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                    Player.petInfo(this.session.myCharz(), 0);
                    this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                            mResources.XIN_HAY_NHAN);
                    this.session.myCharz().arrItem = null;
                } else {
                    this.session.service.startOKDlg("Bạn có đệ tử rồi không thể sử dụng nữa.");
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 415: {
                if (this.session.myCharz().myPetz() == null) {
                    this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                    Player.petInfo(this.session.myCharz(), 1);
                    this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                            mResources.XIN_HAY_NHAN);
                    this.session.myCharz().arrItem = null;
                } else {
                    this.session.service.startOKDlg("Bạn có đệ tử rồi không thể sử dụng nữa.");
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 416: {
                if (this.session.myCharz().myPetz() == null) {
                    this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                    Player.petInfo(this.session.myCharz(), 2);
                    this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                            mResources.XIN_HAY_NHAN);
                    this.session.myCharz().arrItem = null;
                } else {
                    this.session.service.startOKDlg("Bạn có đệ tử rồi không thể sử dụng nữa.");
                }
                this.session.myCharz().resetMenu();
                break;
            }
            // --------------------- đệ tử Mabu -------------------
  case 417: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 568) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.mabuInfo(this.session.myCharz());
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 418: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                       && this.session.myCharz().arrItem[0].template.id == 568) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.mabuInfo(this.session.myCharz());
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
               break;
            }
            case 419: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 568) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                       this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                       Player.mabuInfo(this.session.myCharz());
                       this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                               mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 421: {
                Item itemhop = this.session.myCharz().getItemBag(2028);
                if (itemhop == null) {
                    this.session.myCharz().addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1976).name));
                } else {
                    int indexUI = this.session.myCharz().getEmptyBagIndex();
                    if (indexUI != -1) {
                        int[] itemIds = {555, 556, 562, 563, 561};
                        for (int itemId : itemIds) {
                            Item item = new Item(itemId, false, 1, ItemOption.getOption(itemId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            item.options.add(new ItemOption(30, 0));
                            this.session.myCharz().addItemBag(0, item);
                        }
                        this.session.myCharz().useItemBag(itemhop.indexUI, 1);
                        this.session.service.Bag(this.session.myCharz().arrItemBag);
                        this.session.myCharz().updateAll();
                    } else {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    }
                }
                break;
            }
            case 422: {
                Item itemhop = this.session.myCharz().getItemBag(2028);
                if (itemhop == null) {
                    this.session.myCharz().addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1976).name));
                } else {
                    int indexUI = this.session.myCharz().getEmptyBagIndex();
                    if (indexUI != -1) {
                        int[] itemIds = {557, 558, 564, 565, 561};
                        for (int itemId : itemIds) {
                            Item item = new Item(itemId, false, 1, ItemOption.getOption(itemId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            item.options.add(new ItemOption(30, 0));
                            this.session.myCharz().addItemBag(0, item);
                        }
                        this.session.myCharz().useItemBag(itemhop.indexUI, 1);
                        this.session.service.Bag(this.session.myCharz().arrItemBag);
                        this.session.myCharz().updateAll();
                    } else {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    }
                }
                break;
            }
            case 423: {
                Item itemhop = this.session.myCharz().getItemBag(2028);
                if (itemhop == null) {
                    this.session.myCharz().addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1976).name));
                } else {
                    int indexUI = this.session.myCharz().getEmptyBagIndex();
                    if (indexUI != -1) {
                        int[] itemIds = {559, 560, 566, 567, 561};
                        for (int itemId : itemIds) {
                            Item item = new Item(itemId, false, 1, ItemOption.getOption(itemId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            item.options.add(new ItemOption(30, 0));
                            this.session.myCharz().addItemBag(0, item);
                        }
                        this.session.myCharz().useItemBag(itemhop.indexUI, 1);
                        this.session.service.Bag(this.session.myCharz().arrItemBag);
                        this.session.myCharz().updateAll();
                    } else {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    }
                }
                break;
            }
            case 424: {
                Item itemhop = this.session.myCharz().getItemBag(2029);
                if (itemhop == null) {
                    this.session.myCharz().addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1976).name));
                } else {
                    int indexUI = this.session.myCharz().getEmptyBagIndex();
                    if (indexUI != -1) {
                        int[] itemIds = {650, 651, 657, 658, 656};
                        for (int itemId : itemIds) {
                            Item item = new Item(itemId, false, 1, ItemOption.getOption(itemId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            this.session.myCharz().addItemBag(0, item);
                        }
                        this.session.myCharz().useItemBag(itemhop.indexUI, 1);
                        this.session.service.Bag(this.session.myCharz().arrItemBag);
                        this.session.myCharz().updateAll();
                    } else {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    }
                }
                break;
            }
            case 425: {
                Item itemhop = this.session.myCharz().getItemBag(2029);
                if (itemhop == null) {
                    this.session.myCharz().addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1976).name));
                } else {
                    int indexUI = this.session.myCharz().getEmptyBagIndex();
                    if (indexUI != -1) {
                        int[] itemIds = {652, 653, 659, 660, 656};
                        for (int itemId : itemIds) {
                            Item item = new Item(itemId, false, 1, ItemOption.getOption(itemId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            this.session.myCharz().addItemBag(0, item);
                        }
                        this.session.myCharz().useItemBag(itemhop.indexUI, 1);
                        this.session.service.Bag(this.session.myCharz().arrItemBag);
                        this.session.myCharz().updateAll();
                    } else {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    }
                }
                break;
            }
            case 426: {
                Item itemhop = this.session.myCharz().getItemBag(2029);
                if (itemhop == null) {
                    this.session.myCharz().addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1976).name));
                } else {
                    int indexUI = this.session.myCharz().getEmptyBagIndex();
                    if (indexUI != -1) {
                        int[] itemIds = {654, 655, 661, 662, 656};
                        for (int itemId : itemIds) {
                            Item item = new Item(itemId, false, 1, ItemOption.getOption(itemId, 0, 0),
                                    mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                            this.session.myCharz().addItemBag(0, item);
                        }
                        this.session.myCharz().useItemBag(itemhop.indexUI, 1);
                        this.session.service.Bag(this.session.myCharz().arrItemBag);
                        this.session.myCharz().updateAll();
                    } else {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                    }
                }
                break;
            }

            case 434: {
                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }
                Item thoivang = this.session.myCharz().getItemBag(457);

                if (thoivang == null || thoivang.quantity < 1) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 457).name));
                    return;
                }

                long goldReceived = 500_000_000;
                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }
                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, 1);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                // Thông báo đổi thành công
                this.session.myCharz().addChat(1, "Bạn đã đổi 1 thỏi vàng thành 500.000.000 vàng.");

                this.session.myCharz().resetMenu();
                break;
            }
            case 435: { // Đổi 10 thỏi vàng
                int quantityToUse = 10;
                long goldReceived = quantityToUse * 500_000_000L;

                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }

                Item thoivang = this.session.myCharz().getItemBag(457);
                if (thoivang == null || thoivang.quantity < quantityToUse) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.THIEU, quantityToUse, ItemTemplate.get((short) 457).name));
                    return;
                }

                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, quantityToUse);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1,
                        "Bạn đã đổi " + quantityToUse + " thỏi vàng thành " + goldReceived + " vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 436: { // Đổi 100 thỏi vàng
                int quantityToUse = 100;
                long goldReceived = quantityToUse * 500_000_000L;

                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }

                Item thoivang = this.session.myCharz().getItemBag(457);
                if (thoivang == null || thoivang.quantity < quantityToUse) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.THIEU, quantityToUse, ItemTemplate.get((short) 457).name));
                    return;
                }

                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, quantityToUse);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1,
                        "Bạn đã đổi " + quantityToUse + " thỏi vàng thành " + goldReceived + " vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 437: { // Đổi 1000 thỏi vàng
                int quantityToUse = 1000;
                long goldReceived = quantityToUse * 500_000_000L;

                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }

                Item thoivang = this.session.myCharz().getItemBag(457);
                if (thoivang == null || thoivang.quantity < quantityToUse) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.THIEU, quantityToUse, ItemTemplate.get((short) 457).name));
                    return;
                }

                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, quantityToUse);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1,
                        "Bạn đã đổi " + quantityToUse + " thỏi vàng thành " + goldReceived + " vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 438: {
                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }
                Item thoivang = this.session.myCharz().getItemBag(1901);

                if (thoivang == null || thoivang.quantity < 1) {
                    this.session.myCharz()
                            .addInfo1(String.format(mResources.THIEU, 1, ItemTemplate.get((short) 1901).name));
                    return;
                }

                long goldReceived = 500_000_000;
                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, 1);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1, "Bạn đã đổi 1 thỏi vàng thành 500.000.000 vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 439: { // Đổi 10 thỏi vàng
                int quantityToUse = 10;
                long goldReceived = quantityToUse * 500_000_000L;

                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }

                Item thoivang = this.session.myCharz().getItemBag(1901);
                if (thoivang == null || thoivang.quantity < quantityToUse) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.THIEU, quantityToUse, ItemTemplate.get((short) 1901).name));
                    return;
                }

                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, quantityToUse);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1,
                        "Bạn đã đổi " + quantityToUse + " thỏi vàng thành " + goldReceived + " vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 440: { // Đổi 100 thỏi vàng
                int quantityToUse = 100;
                long goldReceived = quantityToUse * 500_000_000L;

                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }

                Item thoivang = this.session.myCharz().getItemBag(1901);
                if (thoivang == null || thoivang.quantity < quantityToUse) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.THIEU, quantityToUse, ItemTemplate.get((short) 1901).name));
                    return;
                }

                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, quantityToUse);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1,
                        "Bạn đã đổi " + quantityToUse + " thỏi vàng thành " + goldReceived + " vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 441: { // Đổi 1000 thỏi vàng
                int quantityToUse = 1000;
                long goldReceived = quantityToUse * 500_000_000L;

                if (this.session.myCharz().xu >= this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Giới hạn vàng hiện tại đạt giới hạn.");
                    return;
                }

                Item thoivang = this.session.myCharz().getItemBag(1901);
                if (thoivang == null || thoivang.quantity < quantityToUse) {
                    this.session.myCharz().addInfo1(
                            String.format(mResources.THIEU, quantityToUse, ItemTemplate.get((short) 1901).name));
                    return;
                }

                if (this.session.myCharz().xu + goldReceived > this.session.myCharz().maxXu) {
                    this.session.myCharz().addInfo1("Không thể nhận thêm vàng do vượt quá giới hạn.");
                    return;
                }

                this.session.myCharz().updateXu(goldReceived, 2);
                this.session.myCharz().useItemBag(thoivang.indexUI, quantityToUse);
                this.session.service.Bag(this.session.myCharz().arrItemBag);
                this.session.myCharz().updateAll();

                this.session.myCharz().addChat(1,
                        "Bạn đã đổi " + quantityToUse + " thỏi vàng thành " + goldReceived + " vàng.");
                this.session.myCharz().resetMenu();
                break;
            }

            case 460: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_BA_HAT_MIT12;
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY2, 461));
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY3, 463));
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY4, 465));
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY5, 466));
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY6, 467));
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY7, 468));
                this.arrMenu.add(new MenuInfo(mResources.SACH_TUYET_KY8, 469));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }

            case 461: {
                this.session.myCharz().requestOpenUIItem(this.npcId, mResources.MAKE_BOOK1);
                break;
            }
            case 462: {
                this.session.service.getFlag();
                this.session.service.npcChat(this.npcId, mResources.UM_BALA);
                boolean flag = Util.gI().nextInt(100) < 20;
                if (flag) {
                    this.session.myCharz().useItemBagById(1281, 99);
                    Item item = new Item(1283, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    this.session.service.setCombineEff(7, item.template.iconID, -1, this.npcId);
                    this.session.myCharz().addItemBag(1, item);
                    this.session.myCharz().addInfo1(3000, String.format(mResources.BAN_NHAN_DUOC, item.template.name));
                } else {
                    this.session.myCharz().useItemBagById(1281, 9);
                    this.session.service.setCombineEff(8, -1, -1, this.npcId);
                    this.session.service.npcChat(this.npcId, mResources.CHUCCON_MM_LAN_SAU);
                }
                this.session.myCharz().useItemBagById(1282, 1);
                this.session.myCharz().resetMenu();
                break;
            }
            case 463: {
                this.session.myCharz().requestOpenUIItem(this.npcId, mResources.MAKE_BOOK2);
                break;
            }
            case 464: {
                this.session.service.getFlag();
                this.session.service.npcChat(this.npcId, mResources.UM_BALA);
                boolean flag = Util.gI().nextInt(100) < 20;
                if (flag) {
                    Item item = new Item(new int[]{1044, 1211, 1212}[Util.gI().nextInt(3)], false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    for (int i = Util.gI().nextInt(3); i >= 0; i--) {
                        item.options.add(new ItemOption(240, 0));
                    }
                    item.options.add(new ItemOption(21, 40));
                    item.options.add(new ItemOption(30, 0));
                    item.options.add(new ItemOption(86, 0));
                    item.options.add(new ItemOption(242, 5));
                    item.options.add(new ItemOption(212, 1000));
                    this.session.service.setCombineEff(7, item.template.iconID, -1, this.npcId);
                    this.session.myCharz().addItemBag(1, item);
                    this.session.myCharz().addInfo1(3000, String.format(mResources.BAN_NHAN_DUOC, item.template.name));
                } else {
                    this.session.service.setCombineEff(8, -1, -1, this.npcId);
                    this.session.service.npcChat(this.npcId, mResources.CHUCCON_MM_LAN_SAU);
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 465: {
                this.session.service.combine(mResources.SRC_NANGCAP_15, mResources.SRC_NANGCAP_16, -1);
                this.session.myCharz().nangcap = new NangCap(this.session) {
                    @Override
                    public void init() {
                    }

                    @Override
                    public void execute() {

                        if (super.myItem.size() == 2 && super.checkBagOK() && super.isHaveItem(1044, 1211, 1212, 1278, 1279, 1280) && super.isHaveItem(1284)) {
                            Item book = super.getItemById(1044, 1211, 1212, 1278, 1279, 1280);
                            Item amu = super.getItemById(1284);
                            if (!book.isHaveOption(240)) {
                                super.session.myCharz().addInfo1(mResources.FAILED_GIAM_DINH_1);
                            } else if (super.type == 0) {
                                super.session.myCharz().requestOpenUIItem(npcId, String.format(mResources.MAKE_BOOK3, book.template.name));
                            } else {
                                ArrayList<Integer> myList = new ArrayList<>();
                                myList.add(50);
                                myList.add(77);
                                myList.add(103);
                                myList.add(108);
                                myList.add(97);
                                myList.add(94);
                                myList.add(14);
                                myList.add(237);
                                myList.add(80);
                                myList.add(81);
                                myList.add(175);
                                myList.add(5);
                                for (int i = 0; i < book.options.size(); i++) {
                                    if (book.options.get(i).optionTemplate.id == 240) {
                                        int optionId = myList.remove(Util.gI().nextInt(myList.size()));
                                        if (optionId == 5) {
                                            book.options.set(i, new ItemOption(optionId, Util.gI().nextInt(1, 5)));
                                        } else if (optionId == 50 || optionId == 77 || optionId == 103
                                                || optionId == 108 || optionId == 97 || optionId == 94
                                                || optionId == 14 || optionId == 237 || optionId == 80
                                                || optionId == 81 || optionId == 175) {
                                            book.options.set(i, new ItemOption(optionId, Util.gI().nextInt(1, 10)));
                                        }
                                    }
                                }
                                //Eff
                                this.session.service.setCombineEff(2, -1, -1, npcId);
                                super.session.service.Bag(super.session.myCharz().arrItemBag);
                                super.session.service.setCombineEff(super.myItem, npcId);
                            }
                        } else {
                            this.session.service.startOKDlg(mResources.BOOK_AND_AMU);
                        }
                    }
                };
                break;
            }
            case 466: {
                this.session.service.combine(mResources.SRC_NANGCAP_17, mResources.SRC_NANGCAP_18, -1);
                this.session.myCharz().nangcap = new NangCap(this.session) {
                    @Override
                    public void init() {
                    }

                    @Override
                    public void execute() {
                        if (super.myItem.size() == 1 && super.checkBagOK() && super.isHaveItem(1044, 1211, 1212, 1278, 1279, 1280)) {
                            Item book = super.getItemById(1044, 1211, 1212, 1278, 1279, 1280);
                            if (book.isHaveOption(240) || book.getParamOption(242) == 0) {
                                super.session.myCharz().addInfo1(mResources.FAILED_GIAM_DINH_2);
                            } else if (super.type == 0) {
                                super.session.myCharz().requestOpenUIItem(npcId, mResources.MAKE_BOOK4);
                            } else {
                                for (int i = 0; i < book.options.size(); i++) {
                                    int optionId = book.options.get(i).optionTemplate.id;
                                    if (optionId == 50 || optionId == 77 || optionId == 103
                                            || optionId == 108 || optionId == 97 || optionId == 94
                                            || optionId == 14 || optionId == 237 || optionId == 80
                                            || optionId == 81 || optionId == 175 || optionId == 5) {
                                        book.options.set(i, new ItemOption(240, 0));
                                    }
                                }
                                book.getOption(242).param--;
                                //Eff
                                this.session.service.setCombineEff(2, -1, -1, npcId);
                                super.session.service.Bag(super.session.myCharz().arrItemBag);
                                super.session.service.setCombineEff(super.myItem, npcId);
                            }
                        } else {
                            this.session.service.startOKDlg(mResources.CLEAR_BOOK_FAILD);
                        }
                    }
                };
                break;
            }
            case 467: {
                this.session.service.combine(mResources.SRC_NANGCAP_19, mResources.SRC_NANGCAP_20, -1);
                this.session.myCharz().nangcap = new NangCap(this.session) {
                    @Override
                    public void init() {
                    }

                    @Override
                    public void execute() {
                        if (super.myItem.size() == 2 && super.checkBagOK() && super.isHaveItem(1044, 1211, 1212) && super.isHaveItem(1285)) {
                            Item book = super.getItemById(1044, 1211, 1212);
                            Item pliers = super.getItemById(1285);
                            if (super.type == 0) {
                                super.session.myCharz().requestOpenUIItem(npcId, mResources.MAKE_BOOK5);
                            } else {
                                boolean flag = Util.gI().nextInt(100) < 10;
                                //Eff
                                if (flag) {
                                    if (book.template.id == 1044) {
                                        book.setTemplate(1278);
                                    }
                                    if (book.template.id == 1211) {
                                        book.setTemplate(1279);
                                    }
                                    if (book.template.id == 1212) {
                                        book.setTemplate(1280);
                                    }
                                    this.session.service.setCombineEff(2, -1, -1, npcId);
                                } else {
                                    this.session.service.setCombineEff(3, -1, -1, npcId);
                                }
                                super.session.service.Bag(super.session.myCharz().arrItemBag);
                                super.session.service.setCombineEff(super.myItem, npcId);
                            }
                        } else {
                            this.session.service.startOKDlg(mResources.UPGRADE_BOOK_FAILD);
                        }
                    }
                };
                break;
            }
            case 468: {
                this.session.service.combine(mResources.SRC_NANGCAP_21, mResources.SRC_NANGCAP_22, -1);
                this.session.myCharz().nangcap = new NangCap(this.session) {
                    @Override
                    public void init() {
                    }

                    @Override
                    public void execute() {
                        if (super.myItem.size() == 1 && super.checkBagOK() && super.isHaveItem(1044, 1211, 1212, 1278, 1279, 1280)) {
                            Item book = super.getItemById(1044, 1211, 1212, 1278, 1279, 1280);
                            if (super.type == 0) {
                                super.session.myCharz().requestOpenUIItem(npcId, mResources.MAKE_BOOK6);
                            } else {
                                if (super.session.myCharz().getXu() >= 100000000) {
                                    super.session.myCharz().updateXu(-100000000, 2);
                                    book.getOption(212).param += 1;
                                    if (book.getOption(212).param > 1000) {
                                        book.getOption(212).param = 1000;
                                    }
                                    this.session.service.setCombineEff(2, -1, -1, npcId);
                                    super.session.service.Bag(super.session.myCharz().arrItemBag);
                                    super.session.service.setCombineEff(super.myItem, npcId);
                                }
                            }
                        } else {
                            this.session.service.startOKDlg(mResources.BROKEN_BOOK_FAILD);
                        }
                    }
                };
                break;
            }
            case 469: {
                this.session.service.combine(mResources.SRC_NANGCAP_23, mResources.SRC_NANGCAP_24, -1);
                this.session.myCharz().nangcap = new NangCap(this.session) {
                    @Override
                    public void init() {
                    }

                    @Override
                    public void execute() {
                        if (super.myItem.size() == 1 && super.checkBagOK() && super.isHaveItem(1044, 1211, 1212, 1278, 1279, 1280)) {
                            Item book = super.getItemById(1044, 1211, 1212, 1278, 1279, 1280);
                            if (super.type == 0) {
                                super.session.myCharz().requestOpenUIItem(npcId, mResources.MAKE_BOOK7);
                            } else {
                                if (super.session.myCharz().xu >= 10000000) {
                                    super.session.myCharz().useItemBag(book.indexUI, 1);
                                    super.session.myCharz().updateXu(-10000000, 2);

                                    this.session.service.setCombineEff(2, -1, -1, npcId);
                                    super.session.service.Bag(super.session.myCharz().arrItemBag);
                                    super.session.service.setCombineEff(super.myItem, npcId);
                                    super.session.myCharz().addItemBag(1, new Item(1283, false, 5, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY));
                                }
                            }
                        } else {
                            this.session.service.startOKDlg(mResources.DECAY_BOOK_FAILD);
                        }
                    }
                };
                break;
            }
            case 630: {
                if (this.session.myCharz().nangcap != null) {
                    this.session.myCharz().nangcap.type = 1;
                    this.session.myCharz().nangcap.execute();
                }
                break;
            }

            case 503: {
                if (this.session.myCharz().cgender == 0) {
                    // TraiDat
                    this.session.myCharz().shopId = 29;
                } // Namek
                if (this.session.myCharz().cgender == 1) {
                    this.session.myCharz().shopId = 30;
                } // Xayda
                if (this.session.myCharz().cgender == 2) {
                    this.session.myCharz().shopId = 31;
                }
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 504: {
                break;
            }
            case 505: {
                this.typeInfo = 504;
                this.session.service.combine(mResources.SRC_SKH_NEW, mResources.SAY_SKH_NEW, -1);
                break;
            }
            case 506: {
                Combine.nangSKH(this.session.myCharz());
                this.typeInfo = 504;
                break;
            }
            case 512: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.BA_HAT_MIT_SAY_CHAN_MENH;
                this.arrMenu.add(new MenuInfo("Đồng ý", 513));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 513: { // đổi chân thiên tử
                int indexUI = this.session.myCharz().getEmptyBagIndex();
                int idItemDa = 1988;
                if (indexUI != -1) {
                    Item itemId = this.session.myCharz().getItemBag(idItemDa);
                    if (itemId == null || itemId.quantity < 50) {
                        this.session.service.chatTHEGIOI(mResources.EMPTY, String.format(mResources.NEED_ITEM, ItemTemplate.get((short) idItemDa).name), null, 0);
                        break;
                    }
                    Item chanmenh = new Item(2013, false, 1, ItemOption.getOption(73, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    List<Integer> regularOptions = List.of(0, 6, 7);
                    List<Integer> percentOptions = List.of(50, 77, 103);
                    Random random = new Random();
                    int regularOptionId = regularOptions.get(random.nextInt(regularOptions.size()));
                    int percentOptionId = percentOptions.get(random.nextInt(percentOptions.size()));
                    chanmenh.options.add(new ItemOption(regularOptionId, Util.gI().nextInt(100, 1500)));
                    chanmenh.options.add(new ItemOption(percentOptionId, Util.gI().nextInt(1, 7)));
                    this.session.myCharz().useItemBag(itemId.indexUI, 50); // 
                    this.session.myCharz().addItemBag(chanmenh, indexUI);
                    this.session.service.Bag(this.session.myCharz().arrItemBag);
                    this.session.myCharz().addChat(1, "Đổi thành công Chân mệnh cấp 1");
                    this.session.myCharz().updateAll();
                    break;
                } else {
                    this.session.service.chatTHEGIOI(mResources.EMPTY, mResources.BAG_FULL, null, (byte) 0);
                }
            }
            case 514: {
                break;

            }
            case 515: {
                this.typeInfo = 514;
                this.session.service.combine(mResources.NANG_CHAN_MENH, mResources.NANG_CHAN_MENH_SAY, -1);
                break;

            }
            case 516: {
                Combine.NangcapChanMenh(this.session.myCharz());
                this.typeInfo = 514;
                break;

            }
            case 517: {
                break;
            }

            case 518: {
                this.typeInfo = 517;
                this.session.service.combine(mResources.SRC_NANGCAP_BTC3, mResources.SRC_NANGCAP_BTC3_1, -1);

                break;
            }

            case 519: {
                Combine.NangcCapBongTai3(this.session.myCharz());
                this.typeInfo = 517;
                break;
            }
            case 520: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_QUYLAO_N;
                this.arrMenu.add(new MenuInfo(mResources.NHAN_NGAY2, 521));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 521: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().removeEffChar(0, 1018);
                    Item item = new Item(395, false, 1, null, mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                    item.options.add(new ItemOption(77, 5));
                    item.options.add(new ItemOption(103, 5));
                    item.options.add(new ItemOption(50, 5));
                    this.session.myCharz().addEffectChar(1018, 1, 0, 1, 1, 36000000, 1, true, item);
                }
                break;
            }
            case 522: {
                break;
            }
            case 523: {
                this.typeInfo = 522;
                this.session.service.combine(mResources.SRC_NANGCAP_BTC41, mResources.SRC_NANGCAP_BTC42, -1);
                break;
            }
            case 524: {
                Combine.openOptionBongTai3(this.session.myCharz());
                this.typeInfo = 522;
                break;
            }
            case 525: {
                this.session.myCharz().shopId = 35;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 526: {
                this.session.myCharz().shopId = 34;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            // Gacha (NPC 40): mở hộp thoại thông tin + nút Quay / Từ chối (tỉ lệ lấy từ GACHA_IDS, GACHA_PERCENTS)
            case 550: {
                this.session.myCharz().resetMenu();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < GACHA_IDS.length; i++) {
                    ItemTemplate t = ItemTemplate.get((short) GACHA_IDS[i]);
                    sb.append(t != null ? t.name : ("ID" + GACHA_IDS[i])).append(" ").append(GACHA_PERCENTS[i]).append("%");
                    if (i < GACHA_IDS.length - 1) sb.append(", ");
                }
                this.chat = "Gacha tốn 5000 VND. Tỉ lệ: " + sb.toString();
                this.arrMenu.add(new MenuInfo("Quay - 5000 VND", 552));
                this.arrMenu.add(new MenuInfo(mResources.REFUSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            // Gacha thực hiện: -5000 VND, quay theo % (GACHA_IDS, GACHA_PERCENTS — tổng 100%) vào hành trang
            case 552: {
                Char pl = this.session.myCharz();
                long vnd = Money.gI().getMoney(pl);
                if (vnd < 5000) {
                    pl.addInfo1("Không đủ 5000 VND trong tài khoản! Cần 5000 VND.");
                    break;
                }
                if (pl.getEmptyBagCount() < 1) {
                    pl.addInfo1(mResources.BAG_FULL);
                    break;
                }
                int r = Util.gI().nextInt(100);
                int cum = 0;
                int id = GACHA_IDS[0];
                for (int i = 0; i < GACHA_IDS.length; i++) {
                    cum += GACHA_PERCENTS[i];
                    if (r < cum) {
                        id = GACHA_IDS[i];
                        break;
                    }
                }
                Money.gI().updateMoeny(pl, -5000);
                Item item = new Item(id, false, 1, ItemOption.getOption(id, 0, 0), mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                // ID 15, 16, 17 (Ngọc Rồng): cho giao dịch và gộp — không thêm Option 30. Các id khác: Option 30 = "Không thể giao dịch".
                if (id != 15 && id != 16 && id != 17) {
                    item.options.add(new ItemOption(30, 1));
                }
                pl.addItemBag(0, item);
                pl.addInfo1("Bạn nhận được: " + item.template.name);
                break;
            }
            case 528: {
                this.typeInfo = -1;
                if (this.session.myCharz().mapTemplateId == 5) {
                    this.session.myCharz().resetMenu();
                    this.chat = mResources.SAY_HAT_MIT_BT_AS;
                    this.arrMenu.add(new MenuInfo(mResources.BONG_TOI_HOA, 530));
                    this.arrMenu.add(new MenuInfo(mResources.TAY_BONG_TOI, 531));
                    this.arrMenu.add(new MenuInfo(mResources.ANH_SANG_HOA, 532));
                    this.arrMenu.add(new MenuInfo(mResources.TAY_ANH_SANG, 533));
                    this.arrMenu.add(new MenuInfo(mResources.GHEP_TINH_THE, 534));
                    this.openUIConfirm(npcId, null, null, -1);
                }
                break;
            }

            case 530: {
                this.typeInfo = 530;
                this.session.service.combine(mResources.SAY_BONG_TOI_HOA_0, mResources.SAY_BONG_TOI_HOA_1, -1);
                break;
            }
            case -530: {
                this.typeInfo = 530;
                Combine.bongToiHoaTB(this.session.myCharz(), info.index);
                break;
            }

            case 531: {
                this.typeInfo = 531;
                this.session.service.combine(mResources.SAY_TAY_BONG_TOI_0, mResources.SAY_TAY_BONG_TOI_1, -1);
                //menu confirm tay bong toi
                break;
            }

            case -531: {
                //handle tay bong toi
                Combine.tayThuocTinhTB(this.session.myCharz(), info.index, 0);
                this.typeInfo = 531;
                break;
            }

            case 532: {
                //menu confirm anh sang hoa trang bi
                this.typeInfo = 532;
                this.session.service.combine(mResources.SAY_ANH_SANG_HOA_0, mResources.SAY_ANH_SANG_HOA_1, -1);
                break;
            }
            case -532: {
                Combine.anhSangHoaTB(this.session.myCharz(), info.index);
                this.typeInfo = 532;
                break;
            }

            case 533: {
                //menu confirm tay anh sang
                this.typeInfo = 533;
                this.session.service.combine(mResources.SAY_TAY_ANH_SANG_0, mResources.SAY_TAY_ANH_SANG_1, -1);
                break;
            }
            case -533: {
                //handle tay anh sang
                Combine.tayThuocTinhTB(this.session.myCharz(), info.index, 1);
                this.typeInfo = 533;
                break;
            }

            case 534: {
                //menu confirm ghep tinh the
                this.typeInfo = 534;
                this.session.service.combine(mResources.SAY_GHEP_TINH_THE_0, mResources.SAY_GHEP_TINH_THE_1, -1);
                break;
            }
            case -534: {
                //menu confirm ghep tinh the
                Combine.nangCapTinhTheBTAS(this.session.myCharz(), info.index);
                this.typeInfo = 534;
                break;
            }
            //noel
            case 535: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY1_EVENT_NOEL;
//                this.arrMenu.add(new MenuInfo(mResources.CAY_THONG_NOEL, 536));
//                this.arrMenu.add(new MenuInfo(mResources.NGOC_RONG, 538));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 536: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_CAY_THONG_NOEL;
                this.arrMenu.add(new MenuInfo(mResources.OK, 537));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 537: {
                this.session.myCharz().clientInput.openClientInput(18, mResources.INPUT_QUANTITY,
                        new String[]{mResources.QUANTITY_KEY}, new int[]{0});
                break;
            }
            case 538: {
                this.session.myCharz().resetMenu();
                this.chat = mResources.SAY_NGOC_RONG;
                this.arrMenu.add(new MenuInfo(mResources.OK, 539));
                this.arrMenu.add(new MenuInfo(mResources.CLOSE, 0));
                this.openUIConfirm(npcId, null, null, -1);
                break;
            }
            case 539: {
                this.session.myCharz().clientInput.openClientInput(19, mResources.INPUT_QUANTITY,
                        new String[]{mResources.QUANTITY_KEY}, new int[]{0});
                break;
            }
            case 540: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangmamlebac(1);
                }
                break;
            }
            case 541: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else {
                    this.session.myCharz().tangmamlevang(1);
                }
                break;
            }
                 // --------------------- đệ tử cell nhí-------------------
            case 542: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 2043) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.CellnhiInfo(this.session.myCharz(), 0);
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
            case 543: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                       && this.session.myCharz().arrItem[0].template.id == 2043) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                        Player.CellnhiInfo(this.session.myCharz(), 1);
                        this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                                mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
               break;
            }
            case 544: {
                if (this.session.myCharz().isgiaodich) {
                    this.session.myCharz().addInfo1(mResources.O_THE_THUC_HIEN);
                } else if (this.session.myCharz().isSecurity) {
                    this.session.myCharz().addInfo1(mResources.BAOVE);
                } else if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
                        && this.session.myCharz().arrItem[0].template.id == 2043) {
                    if (this.session.myCharz().myPetz() != null && this.session.myCharz().myPetz().petStatus == 4) {
                        this.session.myCharz().addInfo1(mResources.NO_FINNISH);
                    } else {
                       this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);
                       Player.CellnhiInfo(this.session.myCharz(), 2);
                       this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID,
                               mResources.XIN_HAY_NHAN);
                        this.session.myCharz().arrItem = null;
                    }
                }
                this.session.myCharz().resetMenu();
                break;
            }
          case 20441: {
    if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
            && this.session.myCharz().arrItem[0].template.id == 2044) {

        int r = Util.gI().nextInt(100); // 0..99

        // nếu bạn muốn TRƯỢT vẫn mất trứng: trừ luôn ở đây
        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);

        if (r < 50) { // 50%
            // y hệt logic cũ của 414
            Player.petInfo(this.session.myCharz(), 0);
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else if (r < 70) { // 20%
            // y hệt logic cũ của 417
            Player.mabuInfo(this.session.myCharz());
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else if (r < 80) { // 10%
            // y hệt logic cũ của 542
            Player.CellnhiInfo(this.session.myCharz(), 0);
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else {
            // 20% còn lại: để đó (không làm gì)
            // nếu muốn báo trượt thì mở dòng dưới:
            // this.session.service.startOKDlg("Trượt (không nhận được đệ).");
        }

        this.session.myCharz().arrItem = null;
    }
    this.session.myCharz().resetMenu();
    break;
}
          case 20442: {
    if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
            && this.session.myCharz().arrItem[0].template.id == 2044) {

        int r = Util.gI().nextInt(100);

        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);

        if (r < 50) {
            Player.petInfo(this.session.myCharz(), 1);
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else if (r < 70) {
            Player.mabuInfo(this.session.myCharz());
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else if (r < 80) {
            Player.CellnhiInfo(this.session.myCharz(), 1);
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else {
            // để đó
        }

        this.session.myCharz().arrItem = null;
    }
    this.session.myCharz().resetMenu();
    break;
}
          case 20443: {
    if (this.session.myCharz().arrItem != null && this.session.myCharz().arrItem.length == 1
            && this.session.myCharz().arrItem[0].template.id == 2044) {

        int r = Util.gI().nextInt(100);

        this.session.myCharz().useItemBag(this.session.myCharz().arrItem[0].indexUI, 1);

        if (r < 50) {
            Player.petInfo(this.session.myCharz(), 2);
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else if (r < 70) {
            Player.mabuInfo(this.session.myCharz());
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else if (r < 80) {
            Player.CellnhiInfo(this.session.myCharz(), 2);
            this.session.myCharz().session.service.chat(this.session.myCharz().myPetz().charID, mResources.XIN_HAY_NHAN);

        } else {
            // để đó
        }

        this.session.myCharz().arrItem = null;
    }
    this.session.myCharz().resetMenu();
    break;
}
            
            case 989: {
                this.session.myCharz().shopId = 32;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
            case 990: {

                break;
            }
            case 994: {
                this.session.myCharz().openTaiXiu();
                break;
            }
            case 995: {
                break;
            }
            case 996: {
                break;
            }
            case 997: {
                break;
            }

            // ĐẠO LỮ cases 2070-2081 → tách sang handleDaoLuMenu() tránh "code too large"
            // [FIX] Truyền this.typeInfo (= info.type) thay vì info.index vì MenuInfo(str, type)
            // chỉ set field type, còn index = 0 (default)
            case 2070: case 2071: case 2072: case 2073: case 2074:
            case 2075: case 2076: case 2077: case 2078: case 2079:
            case 2080: case 2081: {
                try {
                    handleDaoLuMenu(this.typeInfo, npcId);
                } catch (Exception eDL) {
                    System.out.println("[DaoLu] Lỗi handleDaoLuMenu caseId=" + this.typeInfo + ": " + eDL.getMessage());
                    eDL.printStackTrace();
                    if (this.session.myCharz() != null) {
                        this.session.myCharz().addInfo1("|1|Lỗi hệ thống Đạo Lữ! " + eDL.getMessage());
                    }
                }
                break;
            }

            // NPC Trai Đẹp - Mở shop Đan Đạo Lữ
            case 2090: {
                this.session.myCharz().shopId = 45;
                this.session.service.openShop(Shop.shops.get(this.session.myCharz().shopId));
                break;
            }
    
            // NPC Trai Đẹp - Mua Hồn Đạo Lữ bằng VND
            case 2091: {
                Char me = this.session.myCharz();
                long cost = 100000;
                try {
                    long vnd = Money.gI().getMoney(me);
                    if (vnd < cost) {
                        me.addInfo1(String.format(mResources.MONEY_NOT, Util.gI().getFormatNumber(cost - vnd)));
                    } else {
                        // Trừ VND
                        Money.gI().updateMoeny(me, -cost);
                        // Tạo item Hồn Đạo Lữ (ID 2070) khóa giao dịch + thêm vào hành trang
                        Item honDaoLu = new Item(ConstDaoLu.ITEM_HON_DAO_LU, false, 1,
                                ItemOption.getOption(ConstDaoLu.ITEM_HON_DAO_LU, 0, -1),
                                mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
                        honDaoLu.options.add(new ItemOption(30, 0));
                        boolean added = me.addItemBag(0, honDaoLu);
                        if (added) {
                            me.addInfo1("|2|Mua thành công Hồn Đạo Lữ! Trừ 100,000 VND.");
                        } else {
                            // Hành trang đầy → hoàn tiền
                            Money.gI().updateMoeny(me, cost);
                            me.addInfo1("|1|Hành trang đầy! Không thể mua.");
                        }
                    }
                } catch (Exception e) {
                    me.addInfo1("|1|Lỗi hệ thống! Thử lại sau.");
                    System.out.println("[TraiDep] Lỗi mua Hồn Đạo Lữ: " + e.getMessage());
                }
                break;
            }
            // NPC Trai Đẹp - Đổi điểm lấy item (khóa giao dịch)
            case 2092:
                handleDoiDiemLayItem(4, 457, 10);
                break;
            case 2093:
                handleDoiDiemLayItem(5, 2044, 1);
                break;
            case 2094:
                handleDoiDiemLayItem(8, 2045, 1);
                break;
            case 2095:
                handleDiemDanhNhanQua();
                break;
                case 2100:
                handleBattlePassClaim();
                break;
            case 2102:
                handleBattlePassBuyPro();
                break;
            case 2103:
                handleBattlePassBuyMaster();
                break;
            case 2104:
                handleBattlePassBuyPoints();
                break;
            case 2105:
                handleBattlePassShowRewards();
                break;
        }
        info.clean();
    }

    /** Số ô hành trang tối thiểu cần trống để nhận quà điểm danh theo ngày (1-7). */
    private int diemDanhRequiredSlots(int day) {
        switch (day) {
            case 1: return 3;  // 1901x2(1 ô), 1902x2(1 ô), 2044x1(1 ô)
            case 2: return 4;  // 1901x2, 1902x2, 1903x2, 457x20 – mỗi 1 ô
            case 3: return 4;  // 381,382,383,384 x30 – 4 ô
            case 4: return 4;  // 1150,1151,1152,1153 x20 – 4 ô
            case 5: return 5;  // 220,221,222,223,224 x50 – 5 ô
            case 6: return 2;  // 1988x100, 457x100 – 2 ô
            case 7: return 9;  // 753x3, 752x3 + 925-931 x2 mỗi loại – 2+7 ô
            default: return 9;
        }
    }

    /** Thêm 1 item vào túi với khóa giao dịch (option 30 = 1). Trả về true nếu thêm được. */
    private boolean addDiemDanhItem(dragon.object.Char me, int itemId, int quantity) {
        dragon.object.Item item = new dragon.object.Item(itemId, false, quantity,
                dragon.object.ItemOption.getOption(itemId, 0, -1),
                mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
        item.options.add(new dragon.object.ItemOption(30, 1)); // 30 = khóa giao dịch
        return me.addItemBag(0, item);
    }

    /** [Điểm danh] Nhận quà chuỗi đăng nhập 7 ngày theo bảng quà; check đúng ngày và đủ chỗ hành trang. */
    private void handleDiemDanhNhanQua() {
        dragon.object.Char me = this.session.myCharz();
        if (me.isgiaodich) {
            me.addInfo1(mResources.O_THE_THUC_HIEN);
            return;
        }
        if (me.isSecurity) {
            me.addInfo1(mResources.BAOVE);
            return;
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        String today = String.format("%04d-%02d-%02d", cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) + 1, cal.get(java.util.Calendar.DAY_OF_MONTH));
        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        String yesterday = String.format("%04d-%02d-%02d", cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) + 1, cal.get(java.util.Calendar.DAY_OF_MONTH));
        String last = me.loginStreakLastDate != null ? me.loginStreakLastDate : "";
        int streak = me.loginStreakDays;
        if (last.equals(today)) {
            me.addInfo1("|0|Bạn đã nhận quà điểm danh hôm nay rồi. Mai quay lại nhé!");
            return;
        }
        int newStreak;
        if (last.equals(yesterday)) {
            newStreak = (streak >= 7) ? 1 : (streak + 1);
        } else {
            newStreak = 1;
        }
        int needSlots = diemDanhRequiredSlots(newStreak);
        if (me.getEmptyBagCount() < needSlots) {
            me.addInfo1("|1|Hành trang cần ít nhất " + needSlots + " ô trống để nhận quà ngày " + newStreak + ". Hiện có: " + me.getEmptyBagCount() + " ô.");
            return;
        }
        boolean ok = true;
        switch (newStreak) {
            case 1:
                ok = addDiemDanhItem(me, 1901, 2) && addDiemDanhItem(me, 1902, 2) && addDiemDanhItem(me, 2044, 1);
                break;
            case 2:
                ok = addDiemDanhItem(me, 1901, 2) && addDiemDanhItem(me, 1902, 2) && addDiemDanhItem(me, 1903, 2) && addDiemDanhItem(me, 457, 20);
                break;
            case 3:
                ok = addDiemDanhItem(me, 381, 30) && addDiemDanhItem(me, 382, 30) && addDiemDanhItem(me, 383, 30) && addDiemDanhItem(me, 384, 30);
                break;
            case 4:
                ok = addDiemDanhItem(me, 1150, 20) && addDiemDanhItem(me, 1151, 20) && addDiemDanhItem(me, 1152, 20) && addDiemDanhItem(me, 1153, 20);
                break;
            case 5:
                ok = addDiemDanhItem(me, 220, 50) && addDiemDanhItem(me, 221, 50) && addDiemDanhItem(me, 222, 50) && addDiemDanhItem(me, 223, 50) && addDiemDanhItem(me, 224, 50);
                break;
            case 6:
                ok = addDiemDanhItem(me, 1988, 100) && addDiemDanhItem(me, 457, 100);
                break;
            case 7:
                ok = addDiemDanhItem(me, 753, 3) && addDiemDanhItem(me, 752, 3)
                        && addDiemDanhItem(me, 925, 2) && addDiemDanhItem(me, 926, 2) && addDiemDanhItem(me, 927, 2) && addDiemDanhItem(me, 928, 2)
                        && addDiemDanhItem(me, 929, 2) && addDiemDanhItem(me, 930, 2) && addDiemDanhItem(me, 931, 2);
                break;
        }
        if (ok) {
            me.loginStreakLastDate = today;
            me.loginStreakDays = newStreak;
            this.session.saveLoginStreak();
            me.addInfo1("|2|Chúc mừng! Nhận quà điểm danh ngày " + newStreak + "/7 thành công." + (newStreak >= 7 ? " Chuỗi hoàn thành, ngày mai bắt đầu lại từ ngày 1." : ""));
        } else {
            me.addInfo1("|1|Hành trang đầy! Không thể nhận. Cần ít nhất " + needSlots + " ô trống.");
        }
    }

    /** Đổi điểm online (onlinePoints) lấy item có khóa giao dịch; dùng cho case 2092, 2093, 2094. Trùng với số điểm hiện có trên box NPC. */
    private void handleDoiDiemLayItem(int pointCost, int itemId, int quantity) {
        Char me = this.session.myCharz();
        if (me.isgiaodich) {
            me.addInfo1(mResources.O_THE_THUC_HIEN);
            return;
        }
        if (me.isSecurity) {
            me.addInfo1(mResources.BAOVE);
            return;
        }
        if (me.getEmptyBagCount() < 1) {
            me.addInfo1(mResources.BAG_FULL);
            return;
        }
        if (me.onlinePoints < pointCost) {
            me.addInfo1("Cần " + pointCost + " điểm thời gian. Bạn đang có: " + me.onlinePoints);
            return;
        }
        me.onlinePoints -= pointCost;
        Item item = new Item(itemId, false, quantity,
                ItemOption.getOption(itemId, 0, -1),
                mResources.EMPTY, mResources.EMPTY, mResources.EMPTY);
        item.options.add(new ItemOption(30, 0));
        if (me.addItemBag(0, item)) {
            me.addInfo1("|2|Đổi thành công " + (quantity > 1 ? quantity + " vật phẩm" : "vật phẩm") + "! Trừ " + pointCost + " điểm.");
        } else {
            me.onlinePoints += pointCost;
            me.addInfo1("|1|Hành trang đầy! Không thể nhận.");
        }
    }
    /** [Battle Pass] Nhận quà từ cấp (lastClaimLevel+1) đến level hiện tại. */
    private void handleBattlePassClaim() {
        Char me = this.session.myCharz();
        if (me.isgiaodich) {
            me.addInfo1(mResources.O_THE_THUC_HIEN);
            return;
        }
        if (me.isSecurity) {
            me.addInfo1(mResources.BAOVE);
            return;
        }
        String seasonId = SeasonPass.getCurrentSeasonId();
        SeasonPass.SeasonPassData d = SeasonPass.gI().getOrCreate(me.playerId, seasonId);
        if (d.lastClaimLevel >= d.level) {
            me.addInfo1("|0|Không có quà chưa nhận. Cấp hiện tại: " + d.level + ".");
            return;
        }
        int fromLv = d.lastClaimLevel + 1;
        int toLv = d.level;
        int needSlots = toLv - d.lastClaimLevel;
        if (me.getEmptyBagCount() < needSlots) {
            me.addInfo1("|1|Cần ít nhất " + needSlots + " ô trống hành trang để nhận quà.");
            return;
        }
        if (SeasonPass.gI().claimRewards(me, d)) {
            me.addInfo1("|2|Nhận quà Hành trình mùa thành công! (cấp " + fromLv + " → " + toLv + ")");
        } else {
            me.addInfo1("|1|Hành trang đầy! Không thể nhận quà.");
        }
    }

    /** [Battle Pass] Mua Pro 150.000 VND. */
    private void handleBattlePassBuyPro() {
        Char me = this.session.myCharz();
        if (me.isgiaodich) {
            me.addInfo1(mResources.O_THE_THUC_HIEN);
            return;
        }
        if (me.isSecurity) {
            me.addInfo1(mResources.BAOVE);
            return;
        }
        String msg = SeasonPass.gI().buyPro(me);
        me.addInfo1(msg);
    }

    /** [Battle Pass] Mua Master 200.000 VND. */
    private void handleBattlePassBuyMaster() {
        Char me = this.session.myCharz();
        if (me.isgiaodich) {
            me.addInfo1(mResources.O_THE_THUC_HIEN);
            return;
        }
        if (me.isSecurity) {
            me.addInfo1(mResources.BAOVE);
            return;
        }
        String msg = SeasonPass.gI().buyMaster(me);
        me.addInfo1(msg);
    }

    /** [Battle Pass] Mua điểm: 100 thỏi vàng (457) = 10 điểm. */
    private void handleBattlePassBuyPoints() {
        Char me = this.session.myCharz();
        if (me.isgiaodich) {
            me.addInfo1(mResources.O_THE_THUC_HIEN);
            return;
        }
        if (me.isSecurity) {
            me.addInfo1(mResources.BAOVE);
            return;
        }
        String msg = SeasonPass.gI().buyPointsWithGold(me);
        me.addInfo1(msg);
    }

    /** [Battle Pass] Hiển thị bảng quà theo cấp (1–50). */
    private void handleBattlePassShowRewards() {
        StringBuilder sb = new StringBuilder();
        sb.append("|7|=== BẢNG QUÀ HÀNH TRÌNH MÙA THEO CẤP ===\n");
        sb.append("|0|Cấp 1–20: Free & VIP đều nhận. Cấp 21–50: Chỉ Pro/Master.\n|6|--------------------------------\n");
        for (int line = 0; line < 50; line += 5) {
            boolean first = true;
            for (int i = 0; i < 5; i++) {
                int lv = line + i + 1;
                if (lv > 50) break;
                int[] r = SeasonPass.gI().getRewardForLevel(lv);
                if (r != null && r[0] != 0) {
                    String name = "";
                    try {
                        name = dragon.template.ItemTemplate.get((short) r[0]).name;
                    } catch (Exception e) {
                        name = "ID " + r[0];
                    }
                    if (!first) sb.append(" | ");
                    sb.append("|0|Cấp ").append(lv).append(": ").append(name).append(" x").append(r[1]);
                    first = false;
                }
            }
            if (!first) sb.append("\n");
        }
        sb.append("|6|--------------------------------\n|0|Đóng để quay lại.");
        this.session.service.openUISay(this.npcId, sb.toString(), -1);
    }
    /**
     * Mở menu Đạo Lữ từ bên ngoài (ví dụ: chat command "dl").
     * Public wrapper để ZoneMap.chat() gọi được mà không cần tự build menu.
     */
    public void openDaoLuMenu() {
        try {
            handleDaoLuMenu(2070, 5);
        } catch (Exception e) {
            System.out.println("[DaoLu] Lỗi openDaoLuMenu: " + e.getMessage());
            if (this.session.myCharz() != null) {
                this.session.myCharz().addInfo1("|1|Lỗi mở menu Đạo Lữ!");
            }
        }
    }

    /**
     * Xử lý menu Đạo Lữ - tách riêng để method openUIConfirm không vượt 64KB bytecode.
     */
    private void handleDaoLuMenu(int caseId, int npcId) {
        switch (caseId) {
            case 2070: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                me.resetMenu();
                if (dl == null) {
                    this.chat = "Bạn chưa có Đạo Lữ.\n"
                            + "|7|Cần 1 item \"Hồn Đạo Lữ\" (ID " + ConstDaoLu.ITEM_HON_DAO_LU + ") để chiêu mộ.\n"
                            + "|0|Bạn có muốn tạo Đạo Lữ không?";
                    this.arrMenu.add(new MenuInfo("Tạo Đạo Lữ", 2080));
                    this.arrMenu.add(new MenuInfo("Đóng", 0));
                    this.openUIConfirm(npcId, this.chat, this.arrMenu, -1);
                } else {
                    int[] buffPct = dl.getBuffPercentDetail();
                    int[] buffReal = dl.calcBuff(me.cHPFull, me.cMPFull, me.cDamFull);
                    this.chat = "|7|=== ĐẠO LỮ THÔNG TIN ===\n"
                            + "|2|Tên: " + dl.nameDaoLu + "\n"
                            + "|5|Phẩm: " + ConstDaoLu.getTenPham(dl.typeDaoLu) + "\n"
                            + "|0|Cảnh Giới: " + ConstDaoLu.getFullCapBac(dl.pointCapCanhGioi, dl.pointCapTinh) + "\n"
                            + "|8|Tu Vi: " + dl.pointTuVi + "/" + ConstDaoLu.MAX_TU_VI + "\n"
                            + "|6|Trạng Thái: " + dl.getTextStatusInfo() + "\n"
                            + "|7|--------------------\n"
                            + "|3|Buff Sư Phụ: HP +" + buffPct[0] + "% | KI +" + buffPct[1] + "% | SD +" + buffPct[2] + "%\n"
                            + "|2|Cộng: HP +" + buffReal[0] + " | KI +" + buffReal[1] + " | SD +" + buffReal[2];
                    this.arrMenu.add(new MenuInfo("Đi Theo", 2071));
                    this.arrMenu.add(new MenuInfo("Tu Luyện", 2072));
                    this.arrMenu.add(new MenuInfo("Về Nhà", 2073));
                    this.arrMenu.add(new MenuInfo("Thăng Tinh", 2074));
                    this.arrMenu.add(new MenuInfo("Thăng Cảnh Giới", 2075));
                    if (dl.pointCapCanhGioi == ConstDaoLu.MAX_CAP_BAC - 1) {
                        this.arrMenu.add(new MenuInfo("Đột Phá Đấu Đế", 2076));
                    }
                    this.arrMenu.add(new MenuInfo("Đóng", 0));
                    this.openUIConfirm(npcId, this.chat, this.arrMenu, -1);
                }
                break;
            }
            case 2071: {
                DaoLu dl = this.session.myCharz().myDaoLu;
                if (dl != null) {
                    dl.changeStatus(ConstDaoLu.STATUS_FOLLOW);
                    this.session.service.addInfo("|2|Đạo Lữ đang đi theo bạn!");
                } else {
                    this.session.service.addInfo("|1|Bạn chưa có Đạo Lữ!");
                }
                break;
            }
            case 2072: {
                DaoLu dl = this.session.myCharz().myDaoLu;
                if (dl != null) {
                    dl.changeStatus(ConstDaoLu.STATUS_ATTACK);
                    this.session.service.addInfo("|2|Đạo Lữ đang tu luyện!");
                } else {
                    this.session.service.addInfo("|1|Bạn chưa có Đạo Lữ!");
                }
                break;
            }
            case 2073: {
                DaoLu dl = this.session.myCharz().myDaoLu;
                if (dl != null) {
                    dl.changeStatus(ConstDaoLu.STATUS_GOHOME);
                    this.session.service.addInfo("|2|Đạo Lữ đang về nhà!");
                } else {
                    this.session.service.addInfo("|1|Bạn chưa có Đạo Lữ!");
                }
                break;
            }
            case 2074: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                if (dl == null) { break; }
                me.resetMenu();
                int tiLe = ConstDaoLu.getTiLeThangTinh(dl.pointCapCanhGioi, dl.pointCapTinh);
                this.chat = "|7|=== THĂNG TINH ===\n"
                        + "|0|Cảnh Giới: " + ConstDaoLu.getFullCapBac(dl.pointCapCanhGioi, dl.pointCapTinh) + "\n"
                        + "|8|Tu Vi: " + dl.pointTuVi + "/" + ConstDaoLu.MAX_TU_VI + "\n"
                        + "|5|Tỷ lệ thành công: " + tiLe + "%\n"
                        + "|1|Yêu cầu: Tu Vi >= " + ConstDaoLu.MIN_TU_VI_THANG_TINH + "\n"
                        + "|7|Tiêu hao: TOÀN BỘ Tu Vi\n"
                        + "|1|Thất bại: Rớt hết Tu Vi";
                this.arrMenu.add(new MenuInfo("Xác Nhận Thăng Tinh", 2077));
                this.arrMenu.add(new MenuInfo("Hủy", 0));
                this.openUIConfirm(npcId, this.chat, this.arrMenu, -1);
                break;
            }
            case 2075: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                if (dl == null) { break; }
                me.resetMenu();
                int tiLeCG = ConstDaoLu.getTiLeThangCanhGioi(dl.pointCapCanhGioi);
                int soTinhMat = ConstDaoLu.getSoTinhMatKhiThatBai(dl.pointCapCanhGioi);
                this.chat = "|7|=== THĂNG CẢNH GIỚI ===\n"
                        + "|0|Hiện tại: " + ConstDaoLu.getFullCapBac(dl.pointCapCanhGioi, dl.pointCapTinh) + "\n"
                        + "|2|Thăng lên: " + ConstDaoLu.getTenCanhGioi(dl.pointCapCanhGioi + 1) + "\n"
                        + "|5|Tỷ lệ: " + tiLeCG + "%\n"
                        + "|1|Yêu cầu: Tu Vi=" + ConstDaoLu.MAX_TU_VI + " + Max Tinh\n"
                        + "|1|Thất bại: -" + soTinhMat + " tinh + chết";
                this.arrMenu.add(new MenuInfo("Xác Nhận Thăng Bậc", 2078));
                this.arrMenu.add(new MenuInfo("Hủy", 0));
                this.openUIConfirm(npcId, this.chat, this.arrMenu, -1);
                break;
            }
            case 2076: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                if (dl == null || dl.pointCapCanhGioi != ConstDaoLu.MAX_CAP_BAC - 1) { break; }
                me.resetMenu();
                String txtDauDe = "|7|=== ĐỘT PHÁ ĐẤU ĐẾ ===\n"
                        + "|1|CẢNH BÁO: Đột phá cuối cùng!\n"
                        + "|0|Nguyên liệu cần:\n";
                for (int i = 0; i < ConstDaoLu.ITEM_DAN_DUOC.length; i++) {
                    txtDauDe += "|8| Dan #" + (i + 1) + " (ID " + ConstDaoLu.ITEM_DAN_DUOC[i] + "): " + ConstDaoLu.SO_LUONG_DAN_CAN + "\n";
                }
                txtDauDe += "|6| Đà Xá Cổ Đế (ID " + ConstDaoLu.ITEM_DA_XA_CO_DE + "): " + ConstDaoLu.SO_LUONG_DA_XA_CAN + "\n"
                        + "|2|Tỷ lệ: 100% nếu đủ liệu";
                this.chat = txtDauDe;
                this.arrMenu.add(new MenuInfo("Đột Phá!", 2079));
                this.arrMenu.add(new MenuInfo("Hủy", 0));
                this.openUIConfirm(npcId, this.chat, this.arrMenu, -1);
                break;
            }
            case 2077: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                if (dl == null) { break; }
                if (dl.pointTuVi < ConstDaoLu.MIN_TU_VI_THANG_TINH) {
                    me.addInfo1("|1|Tu Vi chưa đủ " + ConstDaoLu.MIN_TU_VI_THANG_TINH);
                    break;
                }
                int maxT = ConstDaoLu.getMaxTinh(dl.pointCapCanhGioi);
                if (dl.pointCapTinh >= maxT) {
                    me.addInfo1("|1|Đã max tinh! Hãy thăng cảnh giới.");
                    break;
                }
                boolean ok = dl.thangTinh();
                if (ok) {
                    me.addInfo1("|2|Thăng tinh OK! " + ConstDaoLu.getFullCapBac(dl.pointCapCanhGioi, dl.pointCapTinh));
                    me.updateAll();
                } else {
                    me.addInfo1("|1|Thăng tinh THẤT BẠI! Rớt hết Tu Vi.");
                }
                break;
            }
            case 2078: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                if (dl == null) { break; }
                if (dl.pointTuVi < ConstDaoLu.MAX_TU_VI) {
                    me.addInfo1("|1|Tu Vi chưa max (" + ConstDaoLu.MAX_TU_VI + ")");
                    break;
                }
                int maxTinh = ConstDaoLu.getMaxTinh(dl.pointCapCanhGioi);
                if (dl.pointCapTinh < maxTinh) {
                    me.addInfo1("|1|Chưa max tinh (" + dl.pointCapTinh + "/" + maxTinh + ")");
                    break;
                }
                if (dl.pointCapCanhGioi >= ConstDaoLu.MAX_CAP_BAC - 1) {
                    me.addInfo1("|1|Dùng Đột Phá Đấu Đế!");
                    break;
                }
                boolean okCG = dl.thangCanhGioi();
                if (okCG) {
                    if (dl.pointCapCanhGioi >= 5) {
                        for (Session_ME conn : Server.gI().connList) {
                            if (conn != null && conn.myCharz() != null) {
                                conn.service.chatTHEGIOI("",
                                        me.cName + " đã thăng " + ConstDaoLu.getTenCanhGioi(dl.pointCapCanhGioi) + "!",
                                        null, 0);
                            }
                        }
                    }
                    me.addInfo1("|2|Thăng bậc OK! " + ConstDaoLu.getFullCapBac(dl.pointCapCanhGioi, dl.pointCapTinh));
                    me.updateAll();
                } else {
                    int soMat = ConstDaoLu.getSoTinhMatKhiThatBai(dl.pointCapCanhGioi);
                    me.addInfo1("|1|Thăng bậc THẤT BẠI! -" + soMat + " tinh, Đạo Lữ ngã...");
                }
                break;
            }
            case 2079: {
                Char me = this.session.myCharz();
                DaoLu dl = me.myDaoLu;
                if (dl == null || dl.pointCapCanhGioi != ConstDaoLu.MAX_CAP_BAC - 1) { break; }
                boolean duNguyenLieu = true;
                for (int i = 0; i < ConstDaoLu.ITEM_DAN_DUOC.length; i++) {
                    int itemId = ConstDaoLu.ITEM_DAN_DUOC[i];
                    int cnt = me.getItemBagQuantityById(itemId);
                    if (cnt < ConstDaoLu.SO_LUONG_DAN_CAN) {
                        me.addInfo1("|1|Thiếu Đan #" + (i + 1) + ": cần " + ConstDaoLu.SO_LUONG_DAN_CAN + ", có " + cnt);
                        duNguyenLieu = false;
                        break;
                    }
                }
                if (!duNguyenLieu) { break; }
                int cntDaXa = me.getItemBagQuantityById(ConstDaoLu.ITEM_DA_XA_CO_DE);
                if (cntDaXa < ConstDaoLu.SO_LUONG_DA_XA_CAN) {
                    me.addInfo1("|1|Thiếu Đà Xá: cần " + ConstDaoLu.SO_LUONG_DA_XA_CAN + ", có " + cntDaXa);
                    break;
                }
                for (int i = 0; i < ConstDaoLu.ITEM_DAN_DUOC.length; i++) {
                    me.removeItemBagByTemplateId(ConstDaoLu.ITEM_DAN_DUOC[i], ConstDaoLu.SO_LUONG_DAN_CAN);
                }
                me.removeItemBagByTemplateId(ConstDaoLu.ITEM_DA_XA_CO_DE, ConstDaoLu.SO_LUONG_DA_XA_CAN);
                dl.thangDauDe();
                dl.effDauDeXuatHien();
                me.addInfo1("|7|=== ĐẤU ĐẾ XUẤT HIỆN ===\n|2|Đạo Lữ đã đạt đỉnh cao!");
                me.updateAll();
                break;
            }
            case 2080: {
                Char me = this.session.myCharz();
                me.resetMenu();
                if (me.myDaoLu != null) {
                    String tenPham = ConstDaoLu.getTenPham(me.myDaoLu.typeDaoLu);
                    this.chat = "|1|Bạn đang có Đạo Lữ |7|" + tenPham + "|0|\n"
                            + "Dùng Hồn Đạo Lữ sẽ |1|XÓA Đạo Lữ cũ|0| và tạo mới!\n"
                            + "|5|Tỷ lệ phẩm: 60% Nhất, 30% Nhị, 10% Tam\n"
                            + "|1|Mất toàn bộ tiến trình tu luyện!\n"
                            + "|0|Xác nhận đổi?";
                } else {
                    this.chat = "|0|Tạo Đạo Lữ\n"
                            + "|7|Cần item Hồn Đạo Lữ (ID " + ConstDaoLu.ITEM_HON_DAO_LU + ")\n"
                            + "|5|Tỷ lệ phẩm: 60% Nhất, 30% Nhị, 10% Tam\n"
                            + "|2|Tam Phẩm mạnh nhất, Nhất Phẩm yếu nhất\n"
                            + "|0|Xác nhận tạo?";
                }
                this.arrMenu.add(new MenuInfo("Xác Nhận", 2081));
                this.arrMenu.add(new MenuInfo("Hủy", 0));
                this.openUIConfirm(npcId, this.chat, this.arrMenu, -1);
                break;
            }
            case 2081: {
                Char me = this.session.myCharz();
                if (!me.isHaveItemBag(ConstDaoLu.ITEM_HON_DAO_LU)) {
                    me.addInfo1("|1|Không có Hồn Đạo Lữ!");
                    break;
                }
                // Xóa Đạo Lữ cũ nếu có
                if (me.myDaoLu != null) {
                    me.myDaoLu.dispose();
                    me.myDaoLu = null;
                }
                // Tiêu hao 1 Hồn Đạo Lữ
                me.removeItemBagByTemplateId(ConstDaoLu.ITEM_HON_DAO_LU, 1);
                // Random phẩm: 10% Tam, 30% Nhị, 60% Nhất
                byte pham;
                int rnd = Util.gI().nextInt(100);
                if (rnd < 10) {
                    pham = ConstDaoLu.TYPE_TAM_PHAM;
                } else if (rnd < 40) {
                    pham = ConstDaoLu.TYPE_NHI_PHAM;
                } else {
                    pham = ConstDaoLu.TYPE_NHAT_PHAM;
                }
                // Tạo Đạo Lữ mới - tên = tên nhân vật (phẩm đã hiện trong prefix $[Phẩm])
                String dlName = me.cName;
                DaoLu newDL = new DaoLu(me);
                newDL.initNew(dlName, pham, me.cgender);
                me.myDaoLu = newDL;
                me.addInfo1(ConstDaoLu.getTextTaoDaoLu(pham));
                newDL.joinMapMaster();
                break;
            }
        }
    }
}
