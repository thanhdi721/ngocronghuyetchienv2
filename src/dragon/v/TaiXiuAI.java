//package dragon.v;
//
//import dragon.object.Char;
//import dragon.u.Util;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class TaiXiuAI {
//
//    private static final int BET_ITEM_ID = 457;
//    private static final Random random = new Random();
//    private static final ArrayList<PlayerBet> playerBets = new ArrayList<>();
//    private static int totalBet = 0;
////
//
//    private static class PlayerBet {
//
//        String name;
//        int playerId;
//        int betAmount;
//        boolean betTai; // true = Tài, false = Xỉu
//
//        PlayerBet(String name, int playerId, int betAmount, boolean betTai) {
//            this.name = name;
//            this.playerId = playerId;
//            this.betAmount = betAmount;
//            this.betTai = betTai;
//        }
//    }
//
//    public static void placeBet(Char player, int betAmount, boolean betTai) {
//        if (betAmount <= 0 || betAmount > player.getItemBagQuantityById(BET_ITEM_ID)) {
//            player.addInfo1("Số tiền cược không hợp lệ!");
//            return;
//        }
//
//        player.useItemBagById(BET_ITEM_ID, betAmount);
//        playerBets.add(new PlayerBet(player.cName, player.playerId, betAmount, betTai));
//        totalBet += betAmount;
//        player.addInfo1("Bạn đã đặt cược " + betAmount + " vào " + (betTai ? "Tài" : "Xỉu"));
//    }
//
//    public static void endGame() {
//        if (playerBets.isEmpty()) {
//            return;
//        }
//
//        int dice1 = random.nextInt(6) + 1;
//        int dice2 = random.nextInt(6) + 1;
//        int dice3 = random.nextInt(6) + 1;
//        int total = dice1 + dice2 + dice3;
//        boolean isTai = total >= 11;
//
//        ArrayList<PlayerBet> winners = new ArrayList<>();
//        for (PlayerBet bet : playerBets) {
//            if (bet.betTai == isTai) {
//                winners.add(bet);
//            }
//
//            int winnings = totalBet * 95 / 100; // 5% phí nhà cái
//            if (!winners.isEmpty()) {
//                int prizePerWinner = winnings / winners.size();
//                for (PlayerBet winner : winners) {
//                    Char player = Char.getCharById(winner.playerId);
//                    if (player != null) {
//                        player.addInfo1("Bạn đã thắng " + prizePerWinner + "!");
//                    }
//                }
//            }
//
//                   // Thông báo kết quả
//            String resultMessage = "Kết quả: " + dice1 + " + " + dice2 + " + " + dice3 + " = " + total + " (" + (isTai ? "Tài" : "Xỉu") + ")";
//            for (PlayerBet bet : playerBets) {
//                Char player = Char.(bet.playerId);
//                if (player != null) {
//                    player.addInfo1(resultMessage);
//                }
//            }
//        
//        // Reset game
//            playerBets.clear();
//            totalBet = 0;
//        }
//    }
//}
