package dragon.t;

import dragon.object.Item;

/**
 *
 * @author Admin
 */
public class CaiTrang {

    private static CaiTrang instance;

    public static CaiTrang gI() {
        if (instance == null) {
            instance = new CaiTrang();
        }
        return instance;
    }

    public void setPartTemp(Item item) {
        switch (item.template.id) {
            case 0:
                item.headTemp = 64;
                item.bodyTemp = 14;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 3:
                item.headTemp = 64;
                item.bodyTemp = 1;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 33:
                item.headTemp = 64;
                item.bodyTemp = 14;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;

            case 34:
                item.headTemp = 64;
                item.bodyTemp = 1;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 136:
                item.headTemp = 64;
                item.bodyTemp = 65;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 137:
                item.headTemp = 64;
                item.bodyTemp = 65;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 138:
                item.headTemp = 64;
                item.bodyTemp = 71;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 139:
                item.headTemp = 64;
                item.bodyTemp = 71;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 230:
                item.headTemp = 64;
                item.bodyTemp = 155;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;

            case 231:
                item.headTemp = 64;
                item.bodyTemp = 155;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 232:
                item.headTemp = 64;
                item.bodyTemp = 157;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 233:
                item.headTemp = 64;
                item.bodyTemp = 157;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 6:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 15;
                item.bagTemp = -1;
                break;
            case 35:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 15;
                item.bagTemp = -1;
                break;
            case 9:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 2;
                item.bagTemp = -1;
                break;
            case 36:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 2;
                item.bagTemp = -1;
                break;
            case 140:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 66;
                item.bagTemp = -1;
                break;
            case 141:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 66;
                item.bagTemp = -1;
                break;
            case 142:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 72;
                item.bagTemp = -1;
                break;
            case 143:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 72;
                item.bagTemp = -1;
                break;
            case 242:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 156;
                item.bagTemp = -1;
                break;
            case 243:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 156;
                item.bagTemp = -1;
                break;
            case 244:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 158;
                item.bagTemp = -1;
                break;
            case 245:
                item.headTemp = 64;
                item.bodyTemp = 57;
                item.legTemp = 158;
                item.bagTemp = -1;
                break;

            case 1:
                item.headTemp = 29;
                item.bodyTemp = 10;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 41:
                item.headTemp = 29;
                item.bodyTemp = 10;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 4:
                item.headTemp = 29;
                item.bodyTemp = 12;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 42:
                item.headTemp = 29;
                item.bodyTemp = 12;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 152:
                item.headTemp = 29;
                item.bodyTemp = 67;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 153:
                item.headTemp = 29;
                item.bodyTemp = 67;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 154:
                item.headTemp = 29;
                item.bodyTemp = 75;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 155:
                item.headTemp = 29;
                item.bodyTemp = 75;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 234:
                item.headTemp = 29;
                item.bodyTemp = 149;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 235:
                item.headTemp = 29;
                item.bodyTemp = 149;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 236:
                item.headTemp = 29;
                item.bodyTemp = 153;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 237:
                item.headTemp = 29;
                item.bodyTemp = 153;
                item.legTemp = 60;
                item.bagTemp = -1;
                break;
            case 7:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 11;
                item.bagTemp = -1;
                break;
            case 43:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 11;
                item.bagTemp = -1;
                break;
            case 10:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 13;
                item.bagTemp = -1;
                break;
            case 44:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 13;
                item.bagTemp = -1;
                break;
            case 156:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 68;
                item.bagTemp = -1;
                break;
            case 157:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 68;
                item.bagTemp = -1;
                break;
            case 158:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 76;
                item.bagTemp = -1;
                break;
            case 159:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 76;
                item.bagTemp = -1;
                break;
            case 246:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 150;
                item.bagTemp = -1;
                break;
            case 247:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 150;
                item.bagTemp = -1;
                break;
            case 248:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 154;
                item.bagTemp = -1;
                break;
            case 249:
                item.headTemp = 29;
                item.bodyTemp = 59;
                item.legTemp = 154;
                item.bagTemp = -1;
                break;

            case 2:
                item.headTemp = 28;
                item.bodyTemp = 16;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 49:
                item.headTemp = 28;
                item.bodyTemp = 16;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 5:
                item.headTemp = 28;
                item.bodyTemp = 7;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 50:
                item.headTemp = 28;
                item.bodyTemp = 7;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 168:
                item.headTemp = 28;
                item.bodyTemp = 69;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 169:
                item.headTemp = 28;
                item.bodyTemp = 69;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 170:
                item.headTemp = 28;
                item.bodyTemp = 73;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 171:
                item.headTemp = 28;
                item.bodyTemp = 73;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 238:
                item.headTemp = 28;
                item.bodyTemp = 147;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 239:
                item.headTemp = 28;
                item.bodyTemp = 147;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 240:
                item.headTemp = 28;
                item.bodyTemp = 151;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 241:
                item.headTemp = 28;
                item.bodyTemp = 151;
                item.legTemp = 58;
                item.bagTemp = -1;
                break;
            case 8:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 17;
                item.bagTemp = -1;
                break;
            case 51:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 17;
                item.bagTemp = -1;
                break;
            case 11:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 8;
                item.bagTemp = -1;
                break;
            case 52:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 8;
                item.bagTemp = -1;
                break;
            case 172:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 70;
                item.bagTemp = -1;
                break;
            case 173:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 70;
                item.bagTemp = -1;
                break;
            case 174:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 74;
                item.bagTemp = -1;
                break;
            case 175:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 74;
                item.bagTemp = -1;
                break;
            case 250:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 148;
                item.bagTemp = -1;
                break;
            case 251:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 148;
                item.bagTemp = -1;
                break;
            case 252:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 152;
                item.bagTemp = -1;
                break;
            case 253:
                item.headTemp = 28;
                item.bodyTemp = 57;
                item.legTemp = 152;
                item.bagTemp = -1;
                break;

            case 761:
                item.headTemp = 775;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 688:
                item.headTemp = 106;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 689:
                item.headTemp = 108;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 690:
                item.headTemp = 110;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 710:
                item.headTemp = 554;
                item.bodyTemp = 555;
                item.legTemp = 556;
                item.bagTemp = -1;
                break;
            case 711:
                item.headTemp = 356;
                item.bodyTemp = 357;
                item.legTemp = 358;
                item.bagTemp = -1;
                break;
            case 719:
                item.headTemp = 724;
                item.bodyTemp = 725;
                item.legTemp = 726;
                item.bagTemp = -1;
                break;
            case 210:
                item.headTemp = 0;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 227:
                item.headTemp = 127;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 228:
                item.headTemp = 128;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 229:
                item.headTemp = 126;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 762:
                item.headTemp = 777;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 682:
                item.headTemp = 112;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 683:
                item.headTemp = 111;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 684:
                item.headTemp = 113;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 763:
                item.headTemp = 776;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 765:
                item.headTemp = 427;
                item.bodyTemp = 428;
                item.legTemp = 429;
                item.bagTemp = -1;
                break;
            case 806:
                item.headTemp = 790;
                item.bodyTemp = 791;
                item.legTemp = 792;
                item.bagTemp = -1;
                break;
            case 819:
                item.headTemp = 802;
                item.bodyTemp = 803;
                item.legTemp = 804;
                item.bagTemp = -1;
                break;
            case 824:
                item.headTemp = 805;
                item.bodyTemp = 806;
                item.legTemp = 807;
                item.bagTemp = -1;
                break;
            case 825:
                item.headTemp = 809;
                item.bodyTemp = 806;
                item.legTemp = 807;
                item.bagTemp = -1;
                break;
            case 826:
                item.headTemp = 808;
                item.bodyTemp = 806;
                item.legTemp = 807;
                item.bagTemp = -1;
                break;
            case 827:
                item.headTemp = 810;
                item.bodyTemp = 811;
                item.legTemp = 812;
                item.bagTemp = -1;
                break;
            case 843:
                item.headTemp = 816;
                item.bodyTemp = 817;
                item.legTemp = 818;
                item.bagTemp = -1;
                break;
            case 844:
                item.headTemp = 822;
                item.bodyTemp = 823;
                item.legTemp = 824;
                item.bagTemp = -1;
                break;
            case 845:
                item.headTemp = 819;
                item.bodyTemp = 820;
                item.legTemp = 821;
                item.bagTemp = -1;
                break;
            case 846:
                item.headTemp = 825;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 847:
                item.headTemp = 827;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 848:
                item.headTemp = 826;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 685:
                item.headTemp = 101;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 686:
                item.headTemp = 103;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 687:
                item.headTemp = 107;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 196:
                item.headTemp = 550;
                item.bodyTemp = 551;
                item.legTemp = 552;
                item.bagTemp = -1;
                break;
            case 197:
                item.headTemp = 553;
                item.bodyTemp = 551;
                item.legTemp = 552;
                item.bagTemp = -1;
                break;
            case 198:
                item.headTemp = 508;
                item.bodyTemp = 509;
                item.legTemp = 510;
                item.bagTemp = -1;
                break;
            case 412:
                item.headTemp = 64;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 413:
                item.headTemp = 30;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 414:
                item.headTemp = 31;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;

            case 415:
                item.headTemp = 9;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 416:
                item.headTemp = 29;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 417:
                item.headTemp = 32;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;

            case 418:
                item.headTemp = 6;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 419:
                item.headTemp = 27;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 420:
                item.headTemp = 28;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 421:
                item.headTemp = 314;
                item.bodyTemp = 315;
                item.legTemp = 316;
                item.bagTemp = -1;
                break;
            case 422:
                item.headTemp = 311;
                item.bodyTemp = 312;
                item.legTemp = 313;
                item.bagTemp = -1;
                break;
            case 386:
                item.headTemp = 277;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 388:
                item.headTemp = 278;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 283:
                item.headTemp = 77;
                item.bodyTemp = 78;
                item.legTemp = 79;
                item.bagTemp = -1;
                break;
            case 284:
                item.headTemp = 92;
                item.bodyTemp = 93;
                item.legTemp = 94;
                item.bagTemp = -1;
                break;
            case 285:
                item.headTemp = 89;
                item.bodyTemp = 90;
                item.legTemp = 91;
                item.bagTemp = -1;
                break;
            case 290:
                item.headTemp = 171;
                item.bodyTemp = 172;
                item.legTemp = 173;
                item.bagTemp = -1;
                break;
            case 291:
                item.headTemp = 123;
                item.bodyTemp = 124;
                item.legTemp = 125;
                item.bagTemp = -1;
                break;
            case 292:
                item.headTemp = 174;
                item.bodyTemp = 175;
                item.legTemp = 176;
                item.bagTemp = -1;
                break;
            case 405:
                item.headTemp = 183;
                item.bodyTemp = 184;
                item.legTemp = 185;
                item.bagTemp = -1;
                break;
            case 406:
                item.headTemp = 186;
                item.bodyTemp = 187;
                item.legTemp = 188;
                item.bagTemp = -1;
                break;
            case 407:
                item.headTemp = 189;
                item.bodyTemp = 190;
                item.legTemp = 191;
                item.bagTemp = -1;
                break;
            case 408:
                item.headTemp = 309;
                item.bodyTemp = 241;
                item.legTemp = 242;
                item.bagTemp = -1;
            case 409:
                item.headTemp = 310;
                item.bodyTemp = 241;
                item.legTemp = 242;
                item.bagTemp = -1;
            case 410:
                item.headTemp = 306;
                item.bodyTemp = 307;
                item.legTemp = 308;
                item.bagTemp = -1;
            case 411:
                item.headTemp = 240;
                item.bodyTemp = 241;
                item.legTemp = 242;
                item.bagTemp = -1;
            case 423:
                item.headTemp = 341;
                item.bodyTemp = 342;
                item.legTemp = 343;
                item.bagTemp = -1;
                break;
            case 424:
                item.headTemp = 329;
                item.bodyTemp = 330;
                item.legTemp = 331;
                item.bagTemp = -1;
                break;
            case 425:
                item.headTemp = 332;
                item.bodyTemp = 333;
                item.legTemp = 334;
                item.bagTemp = -1;
                break;
            case 426:
                item.headTemp = 335;
                item.bodyTemp = 336;
                item.legTemp = 337;
                item.bagTemp = -1;
                break;
            case 427:
                item.headTemp = 323;
                item.bodyTemp = 324;
                item.legTemp = 325;
                item.bagTemp = -1;
                break;
            case 428:
                item.headTemp = 326;
                item.bodyTemp = 327;
                item.legTemp = 328;
                item.bagTemp = -1;
                break;
            case 429:
                item.headTemp = 168;
                item.bodyTemp = 169;
                item.legTemp = 170;
                item.bagTemp = -1;
                break;
            case 430:
                item.headTemp = 174;
                item.bodyTemp = 175;
                item.legTemp = 176;
                item.bagTemp = -1;
                break;
            case 431:
                item.headTemp = 171;
                item.bodyTemp = 172;
                item.legTemp = 173;
                item.bagTemp = -1;
                break;
            case 432:
                item.headTemp = 177;
                item.bodyTemp = 178;
                item.legTemp = 179;
                item.bagTemp = -1;
                break;
            case 433:
                item.headTemp = 180;
                item.bodyTemp = 181;
                item.legTemp = 182;
                item.bagTemp = -1;
                break;
            case 448:
                item.headTemp = 353;
                item.bodyTemp = 354;
                item.legTemp = 355;
                item.bagTemp = -1;
                break;
            case 449:
                item.headTemp = 377;
                item.bodyTemp = 378;
                item.legTemp = 379;
                item.bagTemp = -1;
                break;
            case 450:
                item.headTemp = 350;
                item.bodyTemp = 351;
                item.legTemp = 352;
                item.bagTemp = -1;
                break;
            case 451:
                item.headTemp = 344;
                item.bodyTemp = 345;
                item.legTemp = 346;
                item.bagTemp = -1;
                break;
            case 452:
                item.headTemp = 347;
                item.bodyTemp = 348;
                item.legTemp = 349;
                item.bagTemp = -1;
                break;
            case 455:
                item.headTemp = 400;
                item.bodyTemp = 401;
                item.legTemp = 402;
                item.bagTemp = -1;
                break;
            case 458:
                item.headTemp = 359;
                item.bodyTemp = 360;
                item.legTemp = 361;
                item.bagTemp = -1;
                break;
            case 461:
                item.headTemp = 394;
                item.bodyTemp = 395;
                item.legTemp = 396;
                item.bagTemp = -1;
                break;
            case 463:
                item.headTemp = 403;
                item.bodyTemp = 404;
                item.legTemp = 405;
                item.bagTemp = -1;
                break;
            case 464:
                item.headTemp = 409;
                item.bodyTemp = 410;
                item.legTemp = 411;
                item.bagTemp = -1;
                break;
            case 524:
                item.headTemp = 249;
                item.bodyTemp = 250;
                item.legTemp = 251;
                item.bagTemp = -1;
                break;
            case 525:
                item.headTemp = 255;
                item.bodyTemp = 256;
                item.legTemp = 257;
                item.bagTemp = -1;
                break;
            case 526:
                item.headTemp = 228;
                item.bodyTemp = 229;
                item.legTemp = 230;
                item.bagTemp = -1;
                break;
            case 527:
                item.headTemp = 231;
                item.bodyTemp = 232;
                item.legTemp = 233;
                item.bagTemp = -1;
                break;
            case 528:
                item.headTemp = 234;
                item.bodyTemp = 235;
                item.legTemp = 236;
                item.bagTemp = -1;
                break;
            case 544:
                item.headTemp = 457;
                item.bodyTemp = 458;
                item.legTemp = 459;
                item.bagTemp = -1;
                break;
            case 545:
                item.headTemp = 460;
                item.bodyTemp = 458;
                item.legTemp = 459;
                item.bagTemp = -1;
                break;
            case 546:
                item.headTemp = 461;
                item.bodyTemp = 458;
                item.legTemp = 459;
                item.bagTemp = -1;
                break;
            case 547:
                item.headTemp = 462;
                item.bodyTemp = 463;
                item.legTemp = 464;
                item.bagTemp = -1;
                break;
            case 548:
                item.headTemp = 465;
                item.bodyTemp = 466;
                item.legTemp = 464;
                item.bagTemp = -1;
                break;
            case 549:
                item.headTemp = 264;
                item.bodyTemp = 265;
                item.legTemp = 266;
                item.bagTemp = -1;
                break;
            case 550:
                item.headTemp = 252;
                item.bodyTemp = 253;
                item.legTemp = 254;
                item.bagTemp = -1;
                break;
            case 551:
                item.headTemp = 246;
                item.bodyTemp = 247;
                item.legTemp = 248;
                item.bagTemp = -1;
                break;
            case 552:
                item.headTemp = 261;
                item.bodyTemp = 262;
                item.legTemp = 263;
                item.bagTemp = -1;
                break;
            case 575:
                item.headTemp = 451;
                item.bodyTemp = 452;
                item.legTemp = 453;
                item.bagTemp = -1;
                break;
            case 576:
                item.headTemp = 415;
                item.bodyTemp = 416;
                item.legTemp = 417;
                item.bagTemp = -1;
                break;
            case 577:
                item.headTemp = 418;
                item.bodyTemp = 419;
                item.legTemp = 420;
                item.bagTemp = -1;
                break;
            case 578:
                item.headTemp = 297;
                item.bodyTemp = 298;
                item.legTemp = 299;
                item.bagTemp = -1;
                break;
            case 580:
                item.headTemp = 310;
                item.bodyTemp = 298;
                item.legTemp = 299;
                item.bagTemp = -1;
                break;
            case 581:
                item.headTemp = 309;
                item.bodyTemp = 298;
                item.legTemp = 299;
                item.bagTemp = -1;
                break;
            case 582:
                item.headTemp = 306;
                item.bodyTemp = 307;
                item.legTemp = 308;
                item.bagTemp = -1;
                break;
            case 583:
                item.headTemp = 240;
                item.bodyTemp = 241;
                item.legTemp = 242;
                item.bagTemp = -1;
                break;
            case 584:
                item.headTemp = 409;
                item.bodyTemp = 410;
                item.legTemp = 411;
                item.bagTemp = -1;
                break;
            case 586:
                item.headTemp = 350;
                item.bodyTemp = 351;
                item.legTemp = 352;
                item.bagTemp = -1;
                break;
            case 587:
                item.headTemp = 344;
                item.bodyTemp = 345;
                item.legTemp = 346;
                item.bagTemp = -1;
                break;
            case 588:
                item.headTemp = 353;
                item.bodyTemp = 354;
                item.legTemp = 355;
                item.bagTemp = -1;
                break;
            case 591:
                item.headTemp = 542;
                item.bodyTemp = 523;
                item.legTemp = 524;
                item.bagTemp = -1;
                break;
            case 592:
                item.headTemp = 0;
                item.bodyTemp = 523;
                item.legTemp = 524;
                item.bagTemp = -1;
                break;
            case 593:
                item.headTemp = 112;
                item.bodyTemp = 523;
                item.legTemp = 524;
                item.bagTemp = -1;
                break;
            case 594:
                item.headTemp = 103;
                item.bodyTemp = 523;
                item.legTemp = 524;
                item.bagTemp = -1;
                break;
            case 598:
                item.headTemp = 561;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 599:
                item.headTemp = 560;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 600:
                item.headTemp = 562;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 601:
                item.headTemp = 566;
                item.bodyTemp = 567;
                item.legTemp = 568;
                item.bagTemp = -1;
                break;
            case 604:
                item.headTemp = 569;
                item.bodyTemp = 472;
                item.legTemp = 473;
                item.bagTemp = -1;
                break;
            case 607:
                item.headTemp = 371;
                item.bodyTemp = 372;
                item.legTemp = 373;
                item.bagTemp = -1;
                break;
            case 612:
                item.headTemp = 573;
                item.bodyTemp = 574;
                item.legTemp = 575;
                item.bagTemp = -1;
                break;
            case 613:
                item.headTemp = 579;
                item.bodyTemp = 580;
                item.legTemp = 581;
                item.bagTemp = -1;
                break;
            case 614:
                item.headTemp = 576;
                item.bodyTemp = 577;
                item.legTemp = 578;
                item.bagTemp = -1;
                break;
            case 615:
                item.headTemp = 177;
                item.bodyTemp = 178;
                item.legTemp = 179;
                item.bagTemp = -1;
                break;
            case 616:
                item.headTemp = 168;
                item.bodyTemp = 169;
                item.legTemp = 170;
                item.bagTemp = -1;
                break;
            case 617:
                item.headTemp = 180;
                item.bodyTemp = 181;
                item.legTemp = 182;
                item.bagTemp = -1;
                break;
            case 618:
                item.headTemp = 582;
                item.bodyTemp = 583;
                item.legTemp = 584;
                item.bagTemp = -1;
                break;
            case 619:
                item.headTemp = 585;
                item.bodyTemp = 586;
                item.legTemp = 587;
                item.bagTemp = -1;
                break;
            case 620:
                item.headTemp = 588;
                item.bodyTemp = 589;
                item.legTemp = 590;
                item.bagTemp = -1;
                break;
            case 621:
                item.headTemp = 591;
                item.bodyTemp = 592;
                item.legTemp = 593;
                item.bagTemp = -1;
                break;
            case 622:
                item.headTemp = 606;
                item.bodyTemp = 607;
                item.legTemp = 608;
                item.bagTemp = -1;
                break;
            case 623:
                item.headTemp = 600;
                item.bodyTemp = 601;
                item.legTemp = 602;
                item.bagTemp = -1;
                break;
            case 624:
                item.headTemp = 594;
                item.bodyTemp = 595;
                item.legTemp = 596;
                item.bagTemp = -1;
                break;
            case 625:
                item.headTemp = 597;
                item.bodyTemp = 598;
                item.legTemp = 599;
                item.bagTemp = -1;
                break;
            case 626:
                item.headTemp = 603;
                item.bodyTemp = 604;
                item.legTemp = 605;
                item.bagTemp = -1;
                break;
            case 629:
                item.headTemp = 502;
                item.bodyTemp = 503;
                item.legTemp = 504;
                item.bagTemp = -1;
                break;
            case 630:
                item.headTemp = 493;
                item.bodyTemp = 494;
                item.legTemp = 495;
                item.bagTemp = -1;
                break;
            case 631:
                item.headTemp = 496;
                item.bodyTemp = 497;
                item.legTemp = 498;
                item.bagTemp = -1;
                break;
            case 632:
                item.headTemp = 499;
                item.bodyTemp = 500;
                item.legTemp = 501;
                item.bagTemp = -1;
                break;
            case 633:
                item.headTemp = 237;
                item.bodyTemp = 238;
                item.legTemp = 239;
                item.bagTemp = -1;
                break;
            case 634:
                item.headTemp = 243;
                item.bodyTemp = 244;
                item.legTemp = 245;
                item.bagTemp = -1;
                break;
            case 635:
                item.headTemp = 612;
                item.bodyTemp = 613;
                item.legTemp = 614;
                item.bagTemp = -1;
                break;
            case 636:
                item.headTemp = 615;
                item.bodyTemp = 616;
                item.legTemp = 617;
                item.bagTemp = -1;
                break;
            case 637:
                item.headTemp = 618;
                item.bodyTemp = 619;
                item.legTemp = 620;
                item.bagTemp = -1;
                break;
            case 640:
                item.headTemp = 630;
                item.bodyTemp = 631;
                item.legTemp = 632;
                item.bagTemp = -1;
                break;
            case 642:
                item.headTemp = 651;
                item.bodyTemp = 652;
                item.legTemp = 653;
                item.bagTemp = -1;
                break;
            case 643:
                item.headTemp = 654;
                item.bodyTemp = 655;
                item.legTemp = 656;
                item.bagTemp = -1;
                break;
            case 644:
                item.headTemp = 545;
                item.bodyTemp = 548;
                item.legTemp = 549;
                item.bagTemp = -1;
                break;
            case 645:
                item.headTemp = 547;
                item.bodyTemp = 548;
                item.legTemp = 549;
                item.bagTemp = -1;
                break;
            case 646:
                item.headTemp = 546;
                item.bodyTemp = 548;
                item.legTemp = 549;
                item.bagTemp = -1;
                break;
            case 647:
                item.headTemp = 642;
                item.bodyTemp = 643;
                item.legTemp = 644;
                item.bagTemp = -1;
                break;
            case 724:
                item.headTemp = 736;
                item.bodyTemp = 737;
                item.legTemp = 738;
                item.bagTemp = -1;
                break;
            case 729:
                item.headTemp = 639;
                item.bodyTemp = 640;
                item.legTemp = 641;
                item.bagTemp = -1;
                break;
            case 730:
                item.headTemp = 745;
                item.bodyTemp = 746;
                item.legTemp = 747;
                item.bagTemp = -1;
                break;
            case 731:
                item.headTemp = 748;
                item.bodyTemp = 749;
                item.legTemp = 750;
                item.bagTemp = -1;
                break;
            case 732:
                item.headTemp = 751;
                item.bodyTemp = 752;
                item.legTemp = 753;
                item.bagTemp = -1;
                break;
            case 738:
                item.headTemp = 742;
                item.bodyTemp = 743;
                item.legTemp = 744;
                item.bagTemp = -1;
                break;
            case 739:
                item.headTemp = 754;
                item.bodyTemp = 755;
                item.legTemp = 756;
                item.bagTemp = -1;
                break;
            case 742:
                item.headTemp = 757;
                item.bodyTemp = 758;
                item.legTemp = 759;
                item.bagTemp = -1;
                break;
            case 754:
                item.headTemp = 769;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 755:
                item.headTemp = 771;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 756:
                item.headTemp = 770;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 760:
                item.headTemp = 772;
                item.bodyTemp = 773;
                item.legTemp = 774;
                item.bagTemp = -1;
                break;
            case 389:
                item.headTemp = 274;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 390:
                item.headTemp = 273;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 391:
                item.headTemp = 275;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 282:
                item.headTemp = 98;
                item.bodyTemp = 99;
                item.legTemp = 100;
                item.bagTemp = -1;
                break;
            case 286:
                item.headTemp = 86;
                item.bodyTemp = 87;
                item.legTemp = 88;
                item.bagTemp = -1;
                break;
            case 287:
                item.headTemp = 83;
                item.bodyTemp = 84;
                item.legTemp = 85;
                item.bagTemp = -1;
                break;
            case 602:
                item.headTemp = 570;
                item.bodyTemp = 571;
                item.legTemp = 572;
                item.bagTemp = -1;
                break;
            case 605:
                item.headTemp = 536;
                item.bodyTemp = 476;
                item.legTemp = 477;
                item.bagTemp = -1;
                break;
            case 608:
                item.headTemp = 397;
                item.bodyTemp = 398;
                item.legTemp = 399;
                item.bagTemp = -1;
                break;
            case 641:
                item.headTemp = 633;
                item.bodyTemp = 634;
                item.legTemp = 635;
                item.bagTemp = -1;
                break;
            case 675:
                item.headTemp = 696;
                item.bodyTemp = 697;
                item.legTemp = 698;
                item.bagTemp = -1;
                break;
            case 676:
                item.headTemp = 684;
                item.bodyTemp = 685;
                item.legTemp = 686;
                item.bagTemp = -1;
                break;
            case 677:
                item.headTemp = 693;
                item.bodyTemp = 694;
                item.legTemp = 695;
                item.bagTemp = -1;
                break;
            case 678:
                item.headTemp = 687;
                item.bodyTemp = 688;
                item.legTemp = 689;
                item.bagTemp = -1;
                break;
            case 679:
                item.headTemp = 681;
                item.bodyTemp = 682;
                item.legTemp = 683;
                item.bagTemp = -1;
                break;
            case 680:
                item.headTemp = 690;
                item.bodyTemp = 691;
                item.legTemp = 692;
                item.bagTemp = -1;
                break;
            case 681:
                item.headTemp = 678;
                item.bodyTemp = 679;
                item.legTemp = 680;
                item.bagTemp = -1;
                break;
            case 392:
                item.headTemp = 280;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 393:
                item.headTemp = 279;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 394:
                item.headTemp = 281;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 288:
                item.headTemp = 159;
                item.bodyTemp = 160;
                item.legTemp = 161;
                item.bagTemp = -1;
                break;
            case 289:
                item.headTemp = 162;
                item.bodyTemp = 163;
                item.legTemp = 164;
                item.bagTemp = -1;
                break;
            case 603:
                item.headTemp = 563;
                item.bodyTemp = 564;
                item.legTemp = 565;
                item.bagTemp = -1;
                break;
            case 606:
                item.headTemp = 538;
                item.bodyTemp = 474;
                item.legTemp = 475;
                item.bagTemp = -1;
                break;
            case 609:
                item.headTemp = 517;
                item.bodyTemp = 518;
                item.legTemp = 519;
                item.bagTemp = -1;
                break;
            case 639:
                item.headTemp = 627;
                item.bodyTemp = 628;
                item.legTemp = 629;
                item.bagTemp = -1;
                break;
            case 385:
                item.headTemp = 89;
                item.bodyTemp = 90;
                item.legTemp = 91;
                item.bagTemp = -1;
                break;
            //////BEGIN HIEU
            case 853:
                item.headTemp = 833;
                item.bodyTemp = 834;
                item.legTemp = 835;
                item.bagTemp = -1;
                break;
            case 854:
                item.headTemp = 837;
                item.bodyTemp = 834;
                item.legTemp = 835;
                item.bagTemp = -1;
                break;
            case 855:
                item.headTemp = 836;
                item.bodyTemp = 834;
                item.legTemp = 835;
                item.bagTemp = -1;
                break;
            case 856:
                item.headTemp = 828;
                item.bodyTemp = 829;
                item.legTemp = 830;
                item.bagTemp = -1;
                break;
            case 857:
                item.headTemp = 832;
                item.bodyTemp = 829;
                item.legTemp = 830;
                item.bagTemp = -1;
                break;
            case 858:
                item.headTemp = 831;
                item.bodyTemp = 829;
                item.legTemp = 830;
                item.bagTemp = -1;
                break;
            case 860:
                item.headTemp = 841;
                item.bodyTemp = 842;
                item.legTemp = 843;
                item.bagTemp = -1;
                break;
            case 862:
                item.headTemp = 844;
                item.bodyTemp = 845;
                item.legTemp = 846;
                item.bagTemp = -1;
                break;
            case 863:
                item.headTemp = 850;
                item.bodyTemp = 851;
                item.legTemp = 852;
                item.bagTemp = -1;
                break;
            case 864:
                item.headTemp = 847;
                item.bodyTemp = 848;
                item.legTemp = 849;
                item.bagTemp = -1;
                break;
            case 866:
                item.headTemp = 1033;
                item.bodyTemp = 1027;
                item.legTemp = 1028;
                item.bagTemp = -1;
                break;
            case 867:
                item.headTemp = 1032;
                item.bodyTemp = 1034;
                item.legTemp = 1035;
                item.bagTemp = -1;
                break;
            case 868:
                item.headTemp = 1042;
                item.bodyTemp = 1047;
                item.legTemp = 1048;
                item.bagTemp = -1;
                break;
            case 872:
                item.headTemp = 856;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 873:
                item.headTemp = 857;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 875:
                item.headTemp = 858;
                item.bodyTemp = 859;
                item.legTemp = 860;
                item.bagTemp = -1;
                break;
            case 876:
                item.headTemp = 861;
                item.bodyTemp = 862;
                item.legTemp = 863;
                item.bagTemp = -1;
                break;
            case 877:
                item.headTemp = 864;
                item.bodyTemp = 865;
                item.legTemp = 866;
                item.bagTemp = -1;
                break;
            case 878:
                item.headTemp = 709;
                item.bodyTemp = 710;
                item.legTemp = 711;
                item.bagTemp = -1;
                break;
            case 879:
                item.headTemp = 712;
                item.bodyTemp = 713;
                item.legTemp = 714;
                item.bagTemp = -1;
                break;
            case 884:
                item.headTemp = 520;
                item.bodyTemp = 521;
                item.legTemp = 522;
                item.bagTemp = -1;
                break;
            case 885:
                item.headTemp = 888;
                item.bodyTemp = 889;
                item.legTemp = 890;
                item.bagTemp = -1;
                break;
            case 896:
                item.headTemp = 901;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 898:
                item.headTemp = 903;
                item.bodyTemp = 904;
                item.legTemp = 905;
                item.bagTemp = -1;
                break;
            case 904:
                item.headTemp = 906;
                item.bodyTemp = 880;
                item.legTemp = 881;
                item.bagTemp = -1;
                break;
            case 905:
                item.headTemp = 910;
                item.bodyTemp = 911;
                item.legTemp = 812;
                item.bagTemp = -1;
                break;
            case 906:
                item.headTemp = 907;
                item.bodyTemp = 908;
                item.legTemp = 909;
                item.bagTemp = -1;
                break;
            case 907:
                item.headTemp = 913;
                item.bodyTemp = 914;
                item.legTemp = 915;
                item.bagTemp = -1;
                break;
            case 911:
                item.headTemp = 542;
                item.bodyTemp = 523;
                item.legTemp = 524;
                item.bagTemp = -1;
                break;
            case 912:
                item.headTemp = 916;
                item.bodyTemp = 917;
                item.legTemp = 918;
                item.bagTemp = -1;
                break;
            case 914:
                item.headTemp = 922;
                item.bodyTemp = 923;
                item.legTemp = 924;
                item.bagTemp = -1;
                break;
            case 913:
                item.headTemp = 919;
                item.bodyTemp = 920;
                item.legTemp = 921;
                item.bagTemp = -1;
                break;
            case 922:
                item.headTemp = 937;
                item.bodyTemp = 938;
                item.legTemp = 939;
                item.bagTemp = -1;
                break;
            case 923:
                item.headTemp = 943;
                item.bodyTemp = 944;
                item.legTemp = 945;
                item.bagTemp = -1;
                break;
            case 924:
                item.headTemp = 940;
                item.bodyTemp = 941;
                item.legTemp = 942;
                item.bagTemp = -1;
                break;
            case 932:
                item.headTemp = 946;
                item.bodyTemp = 947;
                item.legTemp = 948;
                item.bagTemp = -1;
                break;
            case 937:
                item.headTemp = 950;
                item.bodyTemp = 951;
                item.legTemp = 952;
                item.bagTemp = -1;
                break;
            case 938:
                item.headTemp = 956;
                item.bodyTemp = 957;
                item.legTemp = 958;
                item.bagTemp = -1;
                break;
            case 940:
                item.headTemp = 962;
                item.bodyTemp = 963;
                item.legTemp = 964;
                item.bagTemp = -1;
                break;
            case 939:
                item.headTemp = 959;
                item.bodyTemp = 960;
                item.legTemp = 961;
                item.bagTemp = -1;
                break;
            case 941:
                item.headTemp = 965;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 945:
                item.headTemp = 975;
                item.bodyTemp = 976;
                item.legTemp = 977;
                item.bagTemp = -1;
                break;
            case 946:
                item.headTemp = 979;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 947:
                item.headTemp = 981;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 948:
                item.headTemp = 983;
                item.bodyTemp = 984;
                item.legTemp = 985;
                item.bagTemp = -1;
                break;
            case 951:
                item.headTemp = 992;
                item.bodyTemp = 976;
                item.legTemp = 977;
                item.bagTemp = -1;
                break;
            case 952:
                item.headTemp = 986;
                item.bodyTemp = 987;
                item.legTemp = 988;
                item.bagTemp = -1;
                break;
            case 953:
                item.headTemp = 989;
                item.bodyTemp = 990;
                item.legTemp = 991;
                item.bagTemp = -1;
                break;
            case 883:
                item.headTemp = 879;
                item.bodyTemp = 880;
                item.legTemp = 881;
                item.bagTemp = -1;
                break;
            case 957:
                item.headTemp = 997;
                item.bodyTemp = 998;
                item.legTemp = 999;
                item.bagTemp = -1;
                break;
            case 958:
                item.headTemp = 1000;
                item.bodyTemp = 1001;
                item.legTemp = 1002;
                item.bagTemp = -1;
                break;
            case 959:
                item.headTemp = 1003;
                item.bodyTemp = 1004;
                item.legTemp = 1005;
                item.bagTemp = -1;
                break;
            case 1010:
                item.headTemp = 1065;
                item.bodyTemp = 1066;
                item.legTemp = 1067;
                item.bagTemp = -1;
                break;
            case 1011:
                item.headTemp = 1068;
                item.bodyTemp = 1069;
                item.legTemp = 1070;
                item.bagTemp = -1;
                break;
            case 1012:
                item.headTemp = 1071;
                item.bodyTemp = 1072;
                item.legTemp = 1073;
                item.bagTemp = -1;
                break;
            case 1018:
                item.headTemp = 1080;
                item.bodyTemp = 1081;
                item.legTemp = 1082;
                item.bagTemp = -1;
                break;
            case 1019:
                item.headTemp = 1086;
                item.bodyTemp = 1087;
                item.legTemp = 1088;
                item.bagTemp = -1;
                break;
            case 1020:
                item.headTemp = 1083;
                item.bodyTemp = 1084;
                item.legTemp = 1085;
                item.bagTemp = -1;
                break;
            case 989:
                item.headTemp = 1053;
                item.bodyTemp = 1054;
                item.legTemp = 1055;
                item.bagTemp = -1;
                break;
            case 990:
                item.headTemp = 1056;
                item.bodyTemp = 1057;
                item.legTemp = 1058;
                item.bagTemp = -1;
                break;
            case 991:
                item.headTemp = 1059;
                item.bodyTemp = 1060;
                item.legTemp = 1061;
                item.bagTemp = -1;
                break;
            case 1041:
                item.headTemp = 1095;
                item.bodyTemp = 1096;
                item.legTemp = 1097;
                item.bagTemp = -1;
                break;
            case 1042:
                item.headTemp = 1098;
                item.bodyTemp = 1099;
                item.legTemp = 1100;
                item.bagTemp = -1;
                break;
            case 1043:
                item.headTemp = 1101;
                item.bodyTemp = 1102;
                item.legTemp = 1103;
                item.bagTemp = -1;
                break;
            case 1047:
                item.headTemp = -1;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = 80;
                break;
            case 1087:
                item.headTemp = 1119;
                item.bodyTemp = 1120;
                item.legTemp = 1121;
                item.bagTemp = -1;
                break;
            case 1088:
                item.headTemp = 1122;
                item.bodyTemp = 1123;
                item.legTemp = 1124;
                item.bagTemp = -1;
                break;
            case 1090:
                item.headTemp = 1125;
                item.bodyTemp = 1126;
                item.legTemp = 1127;
                item.bagTemp = -1;
                break;
            case 1091:
                item.headTemp = 1128;
                item.bodyTemp = 1129;
                item.legTemp = 1130;
                item.bagTemp = -1;
                break;
            case 1089:
                item.headTemp = 1131;
                item.bodyTemp = 1132;
                item.legTemp = 1133;
                item.bagTemp = -1;
                break;
            case 1103:
                item.headTemp = 1140;
                item.bodyTemp = 1141;
                item.legTemp = 1142;
                item.bagTemp = -1;
                break;
            case 1104:
                item.headTemp = 1146;
                item.bodyTemp = 1147;
                item.legTemp = 1148;
                item.bagTemp = -1;
                break;
            case 1105:
                item.headTemp = 1149;
                item.bodyTemp = 1150;
                item.legTemp = 1151;
                item.bagTemp = -1;
                break;
            case 1106:
                item.headTemp = 1152;
                item.bodyTemp = 1153;
                item.legTemp = 1154;
                item.bagTemp = -1;
                break;
            case 1174:
                item.headTemp = 1189;
                item.bodyTemp = 1190;
                item.legTemp = 1191;
                item.bagTemp = -1;
                break;
            case 1175:
                item.headTemp = 1195;
                item.bodyTemp = 1196;
                item.legTemp = 1197;
                item.bagTemp = -1;
                break;
            case 1176:
                item.headTemp = 1192;
                item.bodyTemp = 1193;
                item.legTemp = 1194;
                item.bagTemp = -1;
                break;
            case 1198:
                item.headTemp = 1186;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 1199:
                item.headTemp = 1188;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 1200:
                item.headTemp = 1187;
                item.bodyTemp = -1;
                item.legTemp = -1;
                item.bagTemp = -1;
                break;
            case 1145:
                item.headTemp = 1169;
                item.bodyTemp = 880;
                item.legTemp = 881;
                item.bagTemp = -1;
                break;
            case 1205:
                item.headTemp = 1207;
                item.bodyTemp = 1208;
                item.legTemp = 1209;
                item.bagTemp = -1;
                break;
            // sk he
            case 1208:
                item.headTemp = 1213;
                item.bodyTemp = 1214;
                item.legTemp = 1215;
                item.bagTemp = -1;
                break;
            case 1209:
                item.headTemp = 1216;
                item.bodyTemp = 1214;
                item.legTemp = 1215;
                item.bagTemp = -1;
                break;
            case 1210:
                item.headTemp = 1217;
                item.bodyTemp = 1214;
                item.legTemp = 1215;
                item.bagTemp = -1;
                break;
            case 1234:
                item.headTemp = 1239;
                item.bodyTemp = 1240;
                item.legTemp = 1241;
                item.bagTemp = -1;
                break;
            case 1235:
                item.headTemp = 1236;
                item.bodyTemp = 1237;
                item.legTemp = 1238;
                item.bagTemp = -1;
                break;
            case 1236:
                item.headTemp = 1242;
                item.bodyTemp = 1243;
                item.legTemp = 1244;
                item.bagTemp = -1;
                break;
            case 894:
                item.headTemp = 645;
                item.bodyTemp = 646;
                item.legTemp = 647;
                item.bagTemp = -1;
                break;
            case 895:
                item.headTemp = 648;
                item.bodyTemp = 649;
                item.legTemp = 650;
                item.bagTemp = -1;
                break;
            case 1265:// Yajirobe Zoro
                item.headTemp = 1295;
                item.bodyTemp = 1296;
                item.legTemp = 1297;
                item.bagTemp = -1;
                break;
            case 1274: // cumber
                item.headTemp = 1282;
                item.bodyTemp = 1283;
                item.legTemp = 1284;
                item.bagTemp = -1;
                break;
            case 1275://cumber SSJ
                item.headTemp = 1285;
                item.bodyTemp = 1283;
                item.legTemp = 1284;
                item.bagTemp = -1;
                break;
            case 1276:// nguyệt thần
                item.headTemp = 1286;
                item.bodyTemp = 1287;
                item.legTemp = 1288;
                item.bagTemp = -1;
                break;
            case 1277: // nhật nguyệt
                item.headTemp = 1289;
                item.bodyTemp = 1290;
                item.legTemp = 1291;
                item.bagTemp = -1;
                break;
            case 1309:// bunma ả rập xê út
                item.headTemp = 1292;
                item.bodyTemp = 1293;
                item.legTemp = 1294;
                item.bagTemp = -1;
                break;
            case 1362:// thầy giáo calic
                item.headTemp = 1298;
                item.bodyTemp = 1299;
                item.legTemp = 1300;
                item.bagTemp = -1;
                break;
            case 1378:// Cải trang Thầy giáo Cađíc
                item.headTemp = 1301;
                item.bodyTemp = 1302;
                item.legTemp = 1303;
                item.bagTemp = -1;
                break;
            case 1379:// Cải trang Thầy giáo picolo
                item.headTemp = 1304;
                item.bodyTemp = 1305;
                item.legTemp = 1306;
                item.bagTemp = -1;
                break;
            case 1380:// Cải trang Sửu
                item.headTemp = 1307;
                item.bodyTemp = 1308;
                item.legTemp = 1309;
                item.bagTemp = -1;
                break;
            case 1381:// Cải trang dần
                item.headTemp = 1310;
                item.bodyTemp = 1311;
                item.legTemp = 1312;
                item.bagTemp = -1;
                break;
            case 1382:// Cải trang raddit
                item.headTemp = 1313;
                item.bodyTemp = 1314;
                item.legTemp = 1315;
                item.bagTemp = -1;
                break;
            case 1383:// Cải trang goku
                item.headTemp = 1316;
                item.bodyTemp = 1317;
                item.legTemp = 1318;
                item.bagTemp = -1;
                break;
            case 1384:// Cải trang quy lão
                item.headTemp = 1319;
                item.bodyTemp = 1320;
                item.legTemp = 1321;
                item.bagTemp = -1;
                break;
            case 1396:// Cải trang superpic
                item.headTemp = 636;
                item.bodyTemp = 637;
                item.legTemp = 638;
                item.bagTemp = -1;
                break;
            case 1424:// Cải trang goku santa
                item.headTemp = 1325;
                item.bodyTemp = 1326;
                item.legTemp = 1327;
                item.bagTemp = -1;
                break;
            case 1436:// Cải trang chi chi tuần lộc
                item.headTemp = 1331;
                item.bodyTemp = 1332;
                item.legTemp = 1333;
                item.bagTemp = -1;
                break;
            case 1437:// Cải trang bông băng vàng
                item.headTemp = 1334;
                item.bodyTemp = 1335;
                item.legTemp = 1336;
                item.bagTemp = -1;
                break;
            case 1442:// Cải trang super xayda god 
                item.headTemp = 1337;
                item.bodyTemp = 1338;
                item.legTemp = 1339;
                item.bagTemp = -1;
                break;
            case 1450:// Cải trang S
                item.headTemp = 1340;
                item.bodyTemp = 1341;
                item.legTemp = 1342;
                item.bagTemp = -1;
                break;
            case 1454:// Cải trang Goku black Rose
                item.headTemp = 553;
                item.bodyTemp = 551;
                item.legTemp = 552;
                item.bagTemp = -1;
                break;
            case 1476:// Cải trang bunma rực rỡ
                item.headTemp = 1349;
                item.bodyTemp = 1350;
                item.legTemp = 1351;
                item.bagTemp = -1;
                break;
            case 1483:// Cải trang bunma thanh lịch
                item.headTemp = 1355;
                item.bodyTemp = 1356;
                item.legTemp = 1357;
                item.bagTemp = -1;
                break;
            case 1484:// Cải trang thần tài trái đất
                item.headTemp = 1358;
                item.bodyTemp = 1359;
                item.legTemp = 1360;
                item.bagTemp = -1;
                break;
            case 1485:// Cải trang thần tài namec
                item.headTemp = 1361;
                item.bodyTemp = 1362;
                item.legTemp = 1363;
                item.bagTemp = -1;
                break;
            case 1486:// Cải trang thần tài xayda
                item.headTemp = 1364;
                item.bodyTemp = 1365;
                item.legTemp = 1366;
                item.bagTemp = -1;
                break;
            case 1498:// Cải trang Goku Dragon
                item.headTemp = 1370;
                item.bodyTemp = 1371;
                item.legTemp = 1372;
                item.bagTemp = -1;
                break;
            case 1499:// Cải trang Pôcôlô Dragon
                item.headTemp = 1373;
                item.bodyTemp = 1374;
                item.legTemp = 1375;
                item.bagTemp = -1;
                break;
            case 1500:// Cải trang Pôcôlô Dragon
                item.headTemp = 1376;
                item.bodyTemp = 1377;
                item.legTemp = 1378;
                item.bagTemp = -1;
                break;
            case 1503:// Cải trang hầu gái xanh
                item.headTemp = 1379;
                item.bodyTemp = 1380;
                item.legTemp = 1381;
                item.bagTemp = -1;
                break;
            case 1504:// Cải trang hầu gái hồng
                item.headTemp = 1382;
                item.bodyTemp = 1383;
                item.legTemp = 1384;
                item.bagTemp = -1;
                break;
            case 1512:// CT Lý Tiểu Nương Bikini
                item.headTemp = 1385;
                item.bodyTemp = 1386;
                item.legTemp = 1387;
                item.bagTemp = -1;
                break;
            case 1553:// CT Goku ssj 4
                item.headTemp = 1394;
                item.bodyTemp = 1395;
                item.legTemp = 1396;
                item.bagTemp = -1;
                break;
            case 1557:// CT Hắc Mị Nương
                item.headTemp = 1397;
                item.bodyTemp = 1398;
                item.legTemp = 1399;
                item.bagTemp = -1;
                break;
            case 1567:// CT Frieren
                item.headTemp = 1403;
                item.bodyTemp = 1404;
                item.legTemp = 1405;
                item.bagTemp = -1;
                break;
            case 1587: // Goku ssj 3
                item.headTemp = 1414;
                item.bodyTemp = 1415;
                item.legTemp = 1416;
                item.bagTemp = -1;
                break;
            case 1590: // Goku blue
                item.headTemp = 1419;
                item.bodyTemp = 1420;
                item.legTemp = 1421;
                item.bagTemp = -1;
                break;
            case 1593: // Goku god
                item.headTemp = 1423;
                item.bodyTemp = 1420;
                item.legTemp = 1421;
                item.bagTemp = -1;
                break;
            case 1595: // Goku ssj 2
                item.headTemp = 1426;
                item.bodyTemp = 1415;
                item.legTemp = 1416;
                item.bagTemp = -1;
                break;
            case 1599: // Fide nhí
                item.headTemp = 1434;
                item.bodyTemp = 1435;
                item.legTemp = 1436;
                item.bagTemp = -1;
                break;
            case 1600: // Xên nhí
                item.headTemp = 1437;
                item.bodyTemp = 1438;
                item.legTemp = 1439;
                item.bagTemp = -1;
                break;
            case 1601: // Mabư nhí
                item.headTemp = 1440;
                item.bodyTemp = 1441;
                item.legTemp = 1442;
                item.bagTemp = -1;
                break;
            case 1602: // Ct androi 21 Kid
                item.headTemp = 1443;
                item.bodyTemp = 1444;
                item.legTemp = 1445;
                item.bagTemp = -1;
                break;
            case 1632: // Ct Himel
                item.headTemp = 1464;
                item.bodyTemp = 1467;
                item.legTemp = 1468;
                item.bagTemp = -1;
                break;
            case 1657: // Ct Gohan đi biển
                item.headTemp = 1472;
                item.bodyTemp = 1473;
                item.legTemp = 1474;
                item.bagTemp = -1;
                break;
            case 1667: // Ct Gohan kinh mat
                item.headTemp = 1475;
                item.bodyTemp = 1477;
                item.legTemp = 1478;
                item.bagTemp = -1;
                break;
            case 1674: // Ct Pikkon
                item.headTemp = 1482;
                item.bodyTemp = 1485;
                item.legTemp = 1486;
                item.bagTemp = -1;
                break;
            case 1684: // Ct Kirin mua lân
                item.headTemp = 1496;
                item.bodyTemp = 1498;
                item.legTemp = 1499;
                item.bagTemp = -1;
                break;
            case 1685: // CT Omega
                item.headTemp = 1500;
                item.bodyTemp = 1501;
                item.legTemp = 1502;
                item.bagTemp = -1;
                break;
            case 1693: // CT Cađíc SSJ4
                item.headTemp = 1503;
                item.bodyTemp = 1504;
                item.legTemp = 1505;
                item.bagTemp = -1;
                break;
            case 1697: // CT Gogeta
                item.headTemp = 1506;
                item.bodyTemp = 1507;
                item.legTemp = 1508;
                item.bagTemp = -1;
                break;
            case 1698: // CT Uron Trư bắt giới
                item.headTemp = 1511;
                item.bodyTemp = 1512;
                item.legTemp = 1513;
                item.bagTemp = -1;
                break;
            case 1700: // CT Hằng nga
                item.headTemp = 1514;
                item.bodyTemp = 1515;
                item.legTemp = 1516;
                item.bagTemp = -1;
                break;
            case 1708: // CT Androi21 Thân thiện
                item.headTemp = 1517;
                item.bodyTemp = 1520;
                item.legTemp = 1521;
                item.bagTemp = -1;
                break;
            case 1709: // CT Androi21 Evil
                item.headTemp = 1522;
                item.bodyTemp = 1520;
                item.legTemp = 1521;
                item.bagTemp = -1;
                break;
            case 1710: // CT Androi21 Tiến sĩ
                item.headTemp = 1525;
                item.bodyTemp = 1528;
                item.legTemp = 1529;
                item.bagTemp = -1;
                break;
            case 1731: // CT Black Goku Rose
                item.headTemp = 1541;
                item.bodyTemp = 1545;
                item.legTemp = 1546;
                item.bagTemp = -1;
                break;
            case 1732: // CT Black Goku 
                item.headTemp = 1544;
                item.bodyTemp = 1545;
                item.legTemp = 1546;
                item.bagTemp = -1;
                break;
            case 1741: // CT Cadic
                item.headTemp = 1547;
                item.bodyTemp = 1548;
                item.legTemp = 1549;
                item.bagTemp = -1;
                break;
            case 1742: // CT SSJ
                item.headTemp = 1550;
                item.bodyTemp = 1552;
                item.legTemp = 1553;
                item.bagTemp = -1;
                break;
            case 1743: // CT SSJ2
                item.headTemp = 1554;
                item.bodyTemp = 1552;
                item.legTemp = 1553;
                item.bagTemp = -1;
                break;
            case 1744: // CT SSJ M
                item.headTemp = 1556;
                item.bodyTemp = 1559;
                item.legTemp = 1549;
                ;
                item.bagTemp = -1;
                break;
            case 1745: // CT SSJ 3
                item.headTemp = 1550;
                item.bodyTemp = 1565;
                item.legTemp = 1566;
                item.bagTemp = -1;
                break;
            case 1746: // CT SSJ Blue
                item.headTemp = 1563;
                item.bodyTemp = 1565;
                item.legTemp = 1566;
                item.bagTemp = -1;
                break;

            case 1983: // Champa tí hon
                item.headTemp = 1573;
                item.bodyTemp = 1574;
                item.legTemp = 1575;
                item.bagTemp = -1;
                break;
            case 1762: // cải trang baby
                item.headTemp = 1576;
                item.bodyTemp = 1577;
                item.legTemp = 1578;
                item.bagTemp = -1;
                break;
            case 1763: // cải trang baby
                item.headTemp = 1579;
                item.bodyTemp = 1580;
                item.legTemp = 1581;
                item.bagTemp = -1;
                break;
            case 1764: // cải trang baby
                item.headTemp = 1582;
                item.bodyTemp = 1583;
                item.legTemp = 1584;
                item.bagTemp = -1;
                break;
            case 1775://CT Broly SSJ4
                item.headTemp = 1585;
                item.bodyTemp = 1588;
                item.legTemp = 1589;
                item.bagTemp = -1;
                break;
            case 1777:// CT Broly SSJ God
                item.headTemp = 1590;
                item.bodyTemp = 1593;
                item.legTemp = 1594;
                item.bagTemp = -1;
                break;
            case 1778://CT Lý Tiểu Nương Rực Rỡ
                item.headTemp = 1595;
                item.bodyTemp = 1596;
                item.legTemp = 1597;
                item.bagTemp = -1;
                break;
            case 1803://CT Goku SSJ 4 Red
                item.headTemp = 1622;
                item.bodyTemp = 1625;
                item.legTemp = 1626;
                item.bagTemp = -1;
                break;
            case 2032://CT Goku SSJ 4 Red
                item.headTemp = 1678;
                item.bodyTemp = 1681;
                item.legTemp = 1682;
                item.bagTemp = -1;
                break;
                case 1809:
    item.headTemp = 1717;
    item.bodyTemp = 1718;
    item.legTemp = 1719;
    item.bagTemp = -1;
    break;

case 1810:
    item.headTemp = 1720;
    item.bodyTemp = 1721;
    item.legTemp = 1722;
    item.bagTemp = -1;
    break;

case 1811:
    item.headTemp = 1723;
    item.bodyTemp = 1724;
    item.legTemp = 1725;
    item.bagTemp = -1;
    break;

case 1812:
    item.headTemp = 1726;
    item.bodyTemp = 1727;
    item.legTemp = 1728;
    item.bagTemp = -1;
    break;

case 1813:
    item.headTemp = 1729;
    item.bodyTemp = 1730;
    item.legTemp = 1731;
    item.bagTemp = -1;
    break;

case 1814:
    item.headTemp = 1732;
    item.bodyTemp = 1733;
    item.legTemp = 1734;
    item.bagTemp = -1;
    break;

case 1815:
    item.headTemp = 1735;
    item.bodyTemp = 1736;
    item.legTemp = 1737;
    item.bagTemp = -1;
    break;

case 1816:
    item.headTemp = 1738;
    item.bodyTemp = 1739;
    item.legTemp = 1740;
    item.bagTemp = -1;
    break;

case 1817:
    item.headTemp = 1741;
    item.bodyTemp = 1747;
    item.legTemp = 1748;
    item.bagTemp = -1;
    break;

case 1818:
    item.headTemp = 1749;
    item.bodyTemp = 1750;
    item.legTemp = 1751;
    item.bagTemp = -1;
    break;

case 1819:
    item.headTemp = 1752;
    item.bodyTemp = 1753;
    item.legTemp = 1754;
    item.bagTemp = -1;
    break;

case 1820:
    item.headTemp = 1755;
    item.bodyTemp = 1756;
    item.legTemp = 1757;
    item.bagTemp = -1;
    break;

case 1821:
    item.headTemp = 1758;
    item.bodyTemp = 1759;
    item.legTemp = 1760;
    item.bagTemp = -1;
    break;

case 1822:
    item.headTemp = 1761;
    item.bodyTemp = 1762;
    item.legTemp = 1763;
    item.bagTemp = -1;
    break;

case 1823:
    item.headTemp = 1764;
    item.bodyTemp = 1765;
    item.legTemp = 1766;
    item.bagTemp = -1;
    break;

case 1824:
    item.headTemp = 1767;
    item.bodyTemp = 1768;
    item.legTemp = 1769;
    item.bagTemp = -1;
    break;

case 1825:
    item.headTemp = 1770;
    item.bodyTemp = 1771;
    item.legTemp = 1772;
    item.bagTemp = -1;
    break;

case 1826:
    item.headTemp = 1773;
    item.bodyTemp = 1774;
    item.legTemp = 1775;
    item.bagTemp = -1;
    break;

case 1827:
    item.headTemp = 1776;
    item.bodyTemp = 1777;
    item.legTemp = 1778;
    item.bagTemp = -1;
    break;

case 1828:
    item.headTemp = 1779;
    item.bodyTemp = 1780;
    item.legTemp = 1781;
    item.bagTemp = -1;
    break;

case 1829:
    item.headTemp = 1782;
    item.bodyTemp = 1783;
    item.legTemp = 1784;
    item.bagTemp = -1;
    break;

case 1830:
    item.headTemp = 1785;
    item.bodyTemp = 1786;
    item.legTemp = 1787;
    item.bagTemp = -1;
    break;

case 1831:
    item.headTemp = 1788;
    item.bodyTemp = 1789;
    item.legTemp = 1790;
    item.bagTemp = -1;
    break;

case 1832:
    item.headTemp = 1791;
    item.bodyTemp = 1792;
    item.legTemp = 1793;
    item.bagTemp = -1;
    break;

case 1833:
    item.headTemp = 1794;
    item.bodyTemp = 1795;
    item.legTemp = 1796;
    item.bagTemp = -1;
    break;

case 1834:
    item.headTemp = 1797;
    item.bodyTemp = 1798;
    item.legTemp = 1799;
    item.bagTemp = -1;
    break;

case 1835:
    item.headTemp = 1800;
    item.bodyTemp = 1801;
    item.legTemp = 1802;
    item.bagTemp = -1;
    break;

case 1836:
    item.headTemp = 1803;
    item.bodyTemp = 1804;
    item.legTemp = 1805;
    item.bagTemp = -1;
    break;

case 1837:
    item.headTemp = 1806;
    item.bodyTemp = 1807;
    item.legTemp = 1808;
    item.bagTemp = -1;
    break;

case 1838:
    item.headTemp = 1809;
    item.bodyTemp = 1810;
    item.legTemp = 1811;
    item.bagTemp = -1;
    break;

case 1839:
    item.headTemp = 1812;
    item.bodyTemp = 1813;
    item.legTemp = 1814;
    item.bagTemp = -1;
    break;

case 1840:
    item.headTemp = 1815;
    item.bodyTemp = 1816;
    item.legTemp = 1817;
    item.bagTemp = -1;
    break;

case 1841:
    item.headTemp = 1818;
    item.bodyTemp = 1819;
    item.legTemp = 1820;
    item.bagTemp = -1;
    break;

case 1842:
    item.headTemp = 1821;
    item.bodyTemp = 1822;
    item.legTemp = 1823;
    item.bagTemp = -1;
    break;

case 1843:
    item.headTemp = 1824;
    item.bodyTemp = 1825;
    item.legTemp = 1826;
    item.bagTemp = -1;
    break;

case 1844:
    item.headTemp = 1827;
    item.bodyTemp = 1828;
    item.legTemp = 1829;
    item.bagTemp = -1;
    break;
            case 2033:
                item.headTemp = 1683;
                item.bodyTemp = 1684;
                item.legTemp = 1685;
                item.bagTemp = -1;
                break;
            case 2034:
                item.headTemp = 1686;
                item.bodyTemp = 1687;
                item.legTemp = 1688;
                item.bagTemp = -1;
                break;
            case 2035:
                item.headTemp = 1689;
                item.bodyTemp = 1692;
                item.legTemp = 1693;
                item.bagTemp = -1;
                break;
            case 2041:
                item.headTemp = 1697;
                item.bodyTemp = 1700;
                item.legTemp = 1701;
                item.bagTemp = -1;
                break;
            case 2046:
                item.headTemp = 1702;
                item.bodyTemp = 1703;
                item.legTemp = 1704;
                item.bagTemp = -1;
                break;
        }
    }

}
