package hethong;

import dragon.server.Server;
import dragon.server.Session_ME;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import dragon.u.GameData;

/**
 *
 * @author TGDD
 */
public class Button extends JButton {
    
    public int action;
    
    public Button(int action) {
        super.addActionListener(this::actionPerformed);
        this.action = action;
    }
    
    public void actionPerformed(ActionEvent e) {
        switch (this.action) {
            case 0:
            {
                if (dragon.server.Server.start) {
                    ServerActivity.gI().setText("Đang bảo trì...");
                    new Thread() {
                        @Override
                        public void run() {
                            Server.start = false;
                        }
                    }.start();
                } else {
                    ServerActivity.gI().setText("Đã bảo trì");
                }
                break;
            }
            case 1:
            {
                ServerActivity.gI().isStart = false;
                ServerActivity.gI().mainFrame.dispose();
                break;
            }
            case 2:
            {
                if (dragon.server.Server.isGoKN) {
                    dragon.server.Server.isGoKN = false;
                    ServerActivity.gI().setText("Tạm ngừng cho kết nối");
                } else {
                    dragon.server.Server.isGoKN = true;
                    ServerActivity.gI().setText("Đã bật cho kết nối");
                }
                break;
            }
            case 3:
            {
                ServerActivity.gI().getInfoServer();
                break;
            }
            case 4:
            {
                ServerActivity.gI().getStay();
                break;
            }
            case 5:
            {
                Session_ME[] array;
                synchronized(Server.gI().connList) {
                    array = new Session_ME[Server.gI().connList.size()];
                    for (int i = 0; i < Server.gI().connList.size(); i++) {
                        if ((System.currentTimeMillis() - Server.gI().connList.get(i).l) > 10000L) {
                            array[i] = Server.gI().connList.get(i);
                        }
                    }
                }
                for (int i2 = 0; i2 < array.length; i2++) {
                    if (array[i2] != null) {
                        array[i2].close();
                    }
                }
                break;
            }
            case 6:
            {
                Server.gI().isAutoSave = !Server.gI().isAutoSave;
                break;
            }
            case 7:
            {
                ServerActivity.gI().actionSet++;
                if (ServerActivity.gI().actionSet > 2) {
                    ServerActivity.gI().actionSet = 0;
                }
                break;
            }
            case 8:
            {
                if (ServerActivity.gI().actionSet == 0) {
                    Server.gI().setSave += 1000;
                }
                if (ServerActivity.gI().actionSet == 1) {
                    Server.gI().setSave += 60000;
                }
                if (ServerActivity.gI().actionSet == 2) {
                    Server.gI().setSave += 3600000;
                }
                break;
            }
            case 9:
            {
                if (ServerActivity.gI().actionSet == 0) {
                    Server.gI().setSave -= 1000;
                }
                if (ServerActivity.gI().actionSet == 1) {
                    Server.gI().setSave -= 60000;
                }
                if (ServerActivity.gI().actionSet == 2) {
                    Server.gI().setSave -= 3600000;
                }
                break;
            }
            case 10:
            {
                ServerActivity.gI().setText("Đang lưu...");
                Server.gI().saveData();
                ServerActivity.gI().setText("Đã lưu thành công");
                break;
            }
            case 11:
            {
                ServerActivity.gI().setText("Đang tải lại data...");
                GameData.init();
                ServerActivity.gI().setText("Đã tải thành công");
                break;
            }
            case 12:
            {
                String input = ServerActivity.gI().getTextBox(0).getText();
            }
            case 13:
            {
                ServerActivity.gI().isMenu = !ServerActivity.gI().isMenu;
                break;
            }
            case 14:
            {
                if (Server.gI().timeBaoTri != -1) {
                    Server.gI().timeBaoTri = -1;
                } else {
                    Server.gI().timeBaoTri = 60000;
                }
                break;
            }
        }
    }
    
}
