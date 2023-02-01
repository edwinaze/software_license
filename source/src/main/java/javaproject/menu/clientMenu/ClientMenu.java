package javaproject.menu.clientMenu;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javaproject.menu.css.setDisplay;
import javaproject.machine.Machine;
import javaproject.authen.client;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;

public class ClientMenu {

    private JFrame jFrame;
    //创建标签
    private JLabel label01;
    private JLabel label02;
    private JLabel label03;
    private JLabel label_time;
    //创建文本框
    private JTextField jTextField01;
    private JTextField jTextField02;
    //创建按钮
    private JButton button01;
    private JButton button02;
    private JPanel jp1, jp2, jp3, jp4;
    private static final String machineCode = new Machine().getMachineCode();
    private static final int GAP = 10;

    public static void main(String[] args) throws IOException {
        FlatLightLaf.install();
        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));
        ClientMenu clientMenu = new ClientMenu(" 主菜单");

    }

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
    public ClientMenu(String title)
    {
        label03 = new JLabel("软件剩下使用时间 :");
        label_time = new JLabel("未激活");
        JFrame mainWindow = new JFrame(title);

        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDisplay.setDisplayMiddleScreen(mainWindow);

        Panel p1 = new Panel();
        p1.add(label03);
        p1.add(label_time);

        mainWindow.getContentPane().add(p1);

        // 添加工具栏
        JMenuBar jMenuBar = new JMenuBar();
        JMenu file = new JMenu("文件");
        JMenu authen = new JMenu("关于");
        // 加入一级菜单
        jMenuBar.add(file);
        jMenuBar.add(authen);
        // 加入二级菜单
        JMenuItem jMenuItem = new JMenuItem("激活");
        jMenuItem.addActionListener(e -> {
            try {
                if(label_time.getText().equals("未激活") || label_time.getText().equals("激活码已过期"))
                    Authener("注册");
                else
                    JOptionPane.showMessageDialog(null, "已激活, 无需再次激活");

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        authen.add(jMenuItem);

        mainWindow.setJMenuBar(jMenuBar);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);

        // 进行ini验证机制
        try
        {
            authByIni();
        }catch (IOException io) {
            io.printStackTrace();
        }

    }

    private void Authener(String title) throws IOException {


        jFrame = new JFrame(title);
        jFrame.setIconImage(new ImageIcon(this.getClass().getResource("/slime.png")).getImage());

        // 设置格式
        label01 = new JLabel("注册码:");
        label02 = new JLabel("本机机器码:");
        //创建文本框
        jTextField01 = new JTextField(40);
        jTextField02 = new JTextField(40);
        jTextField02.setEnabled(false);
        jTextField02.setText(machineCode);
        //创建按钮
        button01 = new JButton("效验");
        button02 = new JButton("复制");

        // 开始布局
        GroupLayout layout = new GroupLayout(jFrame.getContentPane());
        jFrame.getContentPane().setLayout(layout);

        // 水平连续组（列）
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(GAP);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label01).addComponent(label02));
        hGroup.addGap(GAP);
        hGroup.addGroup(layout.createParallelGroup().addComponent(jTextField01).addComponent(jTextField02));
        hGroup.addGap(GAP);
        hGroup.addGroup(layout.createParallelGroup().addComponent(button01).addComponent(button02));
        hGroup.addGap(GAP);
        layout.setHorizontalGroup(hGroup);
        // 垂直连续组（行）
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(GAP * 2);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label01).addComponent(jTextField01).addComponent(button01));
        vGroup.addGap(GAP);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label02).addComponent(jTextField02).addComponent(button02));
        vGroup.addGap(GAP * 6);
        layout.setVerticalGroup(vGroup);

        // 按钮监视器
        /// 验证
        button01.addActionListener((e -> {
                authByWindow();
            }
        ));
        /// 复制
        button02.addActionListener((e -> {
            Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable tText = new StringSelection(jTextField02.getText());
            clip.setContents(tText,null);
        }));
        setDisplay.setDisplayMiddleScreen(jFrame);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }

    private void authByIni() throws IOException {
        String nowPath = System.getProperty("user.dir") + "\\config.properties";
        System.out.println(nowPath);
        InputStream inputStream = new BufferedInputStream(new FileInputStream(nowPath));
        //InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.list(System.out);
        String authCode = properties.getProperty("key");
        System.out.println(authCode);
        try {
            authCode = client.decodeAthuncode(authCode);
        } catch (Exception ex) {
            return;
        }

        String date = client.getDate(authCode);
        if (client.isExpired(date))
            label_time.setText("激活码已过期");
        else {
            try {
                if(date.equals("99999999"))
                {
                    label_time.setText("永久授权");
                }
                else {
                    String time = Integer.toString(client.getTimeDistance(client.getNow(), client.stringToDate(date)));
                    if (time.equals("0"))
                        time = "1";
                    time += "天";
                    label_time.setText(time);
                }
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private void authByWindow() {
        String original = jTextField01.getText();
        String authCode = "";
        try {
            authCode = client.decodeAthuncode(original);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(jFrame, "无效验证码", "激活失败", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
        if (client.isValid(client.getMachineCode(authCode), machineCode))
            JOptionPane.showMessageDialog(jFrame, "无效验证码", "激活失败", JOptionPane.ERROR_MESSAGE);
        else {
            String date = client.getDate(authCode);
            if (client.isExpired(date))
                JOptionPane.showMessageDialog(jFrame, "验证码已过期", "激活失败", JOptionPane.ERROR_MESSAGE);
            else {
                JOptionPane.showMessageDialog(null, "激活成功！");
                try {
                    if(date.equals("99999999"))
                    {
                        label_time.setText("永久授权");
                    }
                    else {
                        String time = Integer.toString(client.getTimeDistance(client.getNow(), client.stringToDate(date)));
                        if (time.equals("0"))
                            time = "1";
                        time += "天";
                        label_time.setText(time);
                    }
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                jFrame.setVisible(false);
                Properties properties = new Properties();
                OutputStream outputStream = null;
                try {
                    String nowPath = System.getProperty("user.dir") + "\\config.properties";
                    System.out.println(nowPath);
                    outputStream = new FileOutputStream(nowPath);
                    properties.setProperty("key", original);
                    properties.store(outputStream,"Admain");
                }catch (IOException io) {
                    io.printStackTrace();
                } finally {
                    if(outputStream != null) {
                        try {
                            outputStream.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

