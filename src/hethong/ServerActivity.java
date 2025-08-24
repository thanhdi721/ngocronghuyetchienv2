package hethong;

import dragon.t.Clan;
import dragon.object.Map;
import dragon.server.Server;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import dragon.t.Money;
import dragon.t.Player;
import dragon.u.ItemKyGui;
import dragon.u.Util;
import dragon.u.mLog;
import dragon.v.BWar;
import dragon.v.LuckyRoundNew;

/**
 *
 * @author TGDD
 */
public class ServerActivity {
    
    private final int WIDTH = 700;
    private final int HEIGHT = 500;
    private final String NAME = "NroZenoV2 by Nguyen Gay";
    
    public JFrame mainFrame;
    private final ArrayList<JLabel> TEXTS =  new ArrayList<>();
    private final ArrayList<Button> BUTTONS =  new ArrayList<>();
    private final ArrayList<TextBox> TEXTBOXS =  new ArrayList<>();
    public boolean isStart;
    public boolean isMenu;
    
    private final Thread thr = new Thread() {
        @Override
        public void run() {
            while(isStart) {
                if (Server.gI().isAutoSave) {
                    ServerActivity.gI().getButton(6).setText("Tắt Auto Save");
                } else {
                    ServerActivity.gI().getButton(6).setText("Bật Auto Save");
                }
                ServerActivity.gI().getButton(7).setText("Time Save: "+ Util.gI().getStrTime(Server.gI().setSave));
                if (actionSet == 0) {
                    ServerActivity.gI().getButton(8).setText("+1 Giây");
                    ServerActivity.gI().getButton(9).setText("-1 Giây");
                }
                if (actionSet == 1) {
                    ServerActivity.gI().getButton(8).setText("+1 Phút");
                    ServerActivity.gI().getButton(9).setText("-1 Phút");
                }
                if (actionSet == 2) {
                    ServerActivity.gI().getButton(8).setText("+1 Giờ");
                    ServerActivity.gI().getButton(9).setText("-1 Giờ");
                }
                if (Server.gI().setSave <= 0) {
                    Server.gI().setSave = 0;
                }
                if (Server.gI().timeBaoTri != -1) {
                    ServerActivity.gI().getButton(14).setText("Tắt bảo trì sau "+ Util.gI().getStrTime(Server.gI().timeBaoTri)+" nữa");
                } else {
                    ServerActivity.gI().getButton(14).setText("Bật bảo trì sau 1p");
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public int actionSet;
    private int texType;
     
    private static ServerActivity instance;
    
    public static ServerActivity gI() {
        if (instance == null) {
            instance = new ServerActivity();
            instance.mainFrame = new JFrame();
        }
        return instance;
    }
    
    public void Activity() {
        this.mainFrame.setTitle(NAME);
        this.mainFrame.setSize(this.WIDTH, this.HEIGHT);
        this.mainFrame.setLayout(null);
        try {
            this.mainFrame.setIconImage(new ImageIcon("icon.png").getImage());
        } catch (Exception e) {
            mLog.log("Thieu icon.png");
        }
        
        // dóng chương trình khi đóng của sổ
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.mainFrame.setVisible(true);
        this.init();
    }
    
    public void getInfoServer() {
        String str = new String();
        str += "Kết nối:"+dragon.server.Server.gI().sizeConn()+"\nOnline:"+dragon.server.Server.gI().sizeByUId()+"\nĐang chơi:"+dragon.server.Server.gI().sizeByCId()+"\nClan:"+Clan.size()+"\nItemKyGui: "+ItemKyGui.countItemKyGui()+"\nBot:"+Player.sizeBot()+"\nMap:"+Map.size()+"\nThread:"+Thread.activeCount()+"\nmiliSecond Server: "+ Util.gI().getFormatNumber(System.currentTimeMillis() - Server.gI().lastUpdate) +"\ndelay Server: "+Server.gI().delay+"\nThời gian bật: "+ Util.gI().getFormatTime3(System.currentTimeMillis() - Server.gI().lastStart);
        if (!Player.BOTS.isEmpty() && !Server.start) {
            str += "\nKhông kick được:";
            for (int i = 0; i < Player.BOTS.size(); i++) {
                str += "\n"+Player.BOTS.get(i).cName;
            }
        }
        str += ("\n tạm đếm được "+ Money.ooo +" bùa");
        str += ("\nBWar "+ BWar.ARRBWAR.size());
        str += ("\nThỏi vàng thu được: "+ LuckyRoundNew.nPhi);
        setText(str);
    }
    
    public void getStay() {
        String str = "Danh sách kẹt";
        synchronized(Server.gI().connList) {
            for (int i = 0; i < Server.gI().connList.size(); i++) {
                if ((System.currentTimeMillis() - Server.gI().connList.get(i).l) > 10000L) {
                    str += ("\nstatus= "+ Server.gI().connList.get(i).status+" delay="+ (System.currentTimeMillis() - Server.gI().connList.get(i).l));
                    if (Server.gI().connList.get(i).isLogin) {
                        str += (" user="+Server.gI().connList.get(i).userName);
                    }
                }
            }
        }
        setText(str);
    }
    
    public void setText(String str) {
        while(!this.TEXTS.isEmpty()) {
            JLabel o = this.TEXTS.remove(0);
            o.setBounds(0, 0, 0, 0);
            this.mainFrame.remove(o);
        }
        if (str.length() > 0) {
            String[] array1 = str.split("\n");
            JLabel[] array2 = new JLabel[array1.length];
            for (int i = 0; i < array1.length; i++) {
                array2[i] = new JLabel();
                array2[i].setBounds(20, 20 * i, 1000, 20);
                this.mainFrame.add(array2[i]);
                array2[i].setText(array1[i]);
                this.TEXTS.add(array2[i]);
            }
        }
    }
    
    public void addButton(int action, String text, int x, int y, int width, int height) {
        Button o = new Button(action);
        o.setBounds(x, y, width, height);
        this.mainFrame.add(o);
        o.setText(text);
        BUTTONS.add(o);
    }
    
    public void setButton(int action, String text, int x, int y, int width, int height) {
        Button o = this.getButton(action);
        if (o == null) {
            o = new Button(action);
            BUTTONS.add(o);
            this.mainFrame.add(o);
        }
        o.setBounds(x, y, width, height);
        o.setText(text);
    }
    
    public Button getButton(int action) {
        for (int i = 0; i < BUTTONS.size(); i++) {
            if (BUTTONS.get(i).action == action) {
                return BUTTONS.get(i);
            }
        }
        return null;
    }
    
    public void init() {
        this.mainFrame.addMouseListener(new Mouse());
        //Phím bảo trì
        this.setButton(0, "Bảo trì", 450, 250, 100, 30);
        
        //Phím ẩn tab
        this.setButton(1, "Ẩn", 550, 250, 100, 30);
        
        //Phím bật tắt kết nối
        this.setButton(2, "Kết nối", 350, 250, 100, 30);
        
        //Phím thống kê
        this.setButton(3, "Thống kê", 350, 280, 100, 30);
        
        //Phím list
        this.setButton(4, "Kẹt", 450, 280, 100, 30);
        
        //Phím kick
        this.setButton(5, "Kick kẹt", 550, 280, 100, 30);
        
        //Tự lưu data
        this.setButton(6, "", 350, 10, 200, 30);
        
        //Phím hiện time
        this.setButton(7, "", 350, 40, 200, 30);
        
        //Phím - time
        this.setButton(8, "", 450, 70, 100, 30);
        
        //Phím + time
        this.setButton(9, "", 350, 70, 100, 30);
        
        //Phím lưu data
        this.setButton(10, "Save All Data", 350, 100, 200, 30);
        
        //Phím reset data
        this.setButton(11, "Reset Data", 350, 130, 200, 30);
        
        //Phím bao tri sau 30p
        this.setButton(14, "Bảo trì sau 30p", 350, 160, 200, 30);
        this.isStart = true;
        this.thr.start();
//        openTextBox();
    }
    
    public void openTextBox() {
        int x = (WIDTH / 2) - 100;
        int y = (HEIGHT/ 2);
        //Input
        this.setTextBox(0, "", x, y, 200, 30);
        this.getTextBox(0).setToolTipText("Nhập");
        
        //Phím Nhập
        this.setButton(13, "Đóng", x + 30, y + 30, 70, 30);
        //Phím Nhập
        this.setButton(12, "OK", x + 100, y + 30, 70, 30);
    }
    
    public void addTextBoxl(int action, String text, int x, int y, int width, int height) {
        TextBox o = new TextBox(action);
        o.setBounds(x, y, width, height);
        this.mainFrame.add(o);
        o.setText(text);
        TEXTBOXS.add(o);
    }
    
    public void setTextBox(int action, String text, int x, int y, int width, int height) {
        TextBox o = this.getTextBox(action);
        if (o == null) {
            o = new TextBox(action);
            TEXTBOXS.add(o);
            this.mainFrame.add(o);
        }
        o.setBounds(x, y, width, height);
        o.setText(text);
    }
    
    public TextBox getTextBox(int action) {
        for (int i = 0; i < TEXTBOXS.size(); i++) {
            if (TEXTBOXS.get(i).action == action) {
                return TEXTBOXS.get(i);
            }
        }
        return null;
    }
}
