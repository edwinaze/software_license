package javaproject.menu.serverMenu;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javaproject.menu.css.setDisplay;
import javaproject.machine.Machine;
import javaproject.authen.Server;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Enumeration;

public class ServerMenu {
    private final JFrame jFrame;
    private JLabel label01;
    private JLabel label02;
    private JLabel label03;
    private JLabel label04;
    private JTextField jTextField01;
    private JTextField jTextField02;
    private JTextField jTextField03;
    private JButton button01;
    private JButton button02;
    private JButton button03;
    private JButton button04;
    private JCheckBox checkBox;
    private static final int GAP = 10;
    private static final String machineCode = new Machine().getMachineCode();
    private boolean isForever = false;
    public static void main(String[] arg)
    {
        InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));
        ServerMenu sm = new ServerMenu("注册机");
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

    public ServerMenu(String title) {

        FlatLightLaf.install();

        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        jFrame = new JFrame("注册机");
        jFrame.setIconImage(new ImageIcon(this.getClass().getResource("/slime.png")).getImage());
        //添加容器
        label01 = new JLabel("机器码:");
        label02 = new JLabel("注册码:");
        label03 = new JLabel("授权截止:");
        label04 = new JLabel("本机机器码:");
        jTextField01 = new JTextField(50);
        jTextField02 = new JTextField(50);
        jTextField03 = new JTextField();

        jTextField03.setFont(new Font("console", Font.PLAIN,13));
        jTextField03.setForeground(Color.darkGray);
        jTextField03.setOpaque(false);
        jTextField03.setText(machineCode);
        jTextField03.setEnabled(false);


        button01 = new JButton("复制");
        button02 = new JButton("复制");
        button03 = new JButton("生成");
        button04 = new JButton("复制");
        checkBox = new JCheckBox("永久授权");
        DateField date = new DateField();
        JPanel jp1 = new JPanel();
        jp1.add(date);
        jp1.add(checkBox);
        jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
        //jp1.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));


        GroupLayout layout = new GroupLayout(jFrame.getContentPane());
        jFrame.getContentPane().setLayout(layout);
        // 垂直布局
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(GAP*3);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label01).addComponent(label02).addComponent(label03).addComponent(label04));
        hGroup.addGap(GAP);
        hGroup.addGroup(layout.createParallelGroup().addComponent(jTextField01).addComponent(jTextField02).addComponent(jp1).addComponent(jTextField03));
        hGroup.addGap(GAP);
        hGroup.addGroup(layout.createParallelGroup().addComponent(button01).addComponent(button02).addComponent(button03).addComponent(button04));
        hGroup.addGap(GAP*2);
        layout.setHorizontalGroup(hGroup);

        // 水平布局
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(GAP*3);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label01).addGap(10).addComponent(jTextField01).addComponent(button01));
        vGroup.addGap(GAP);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label02).addComponent(jTextField02).addComponent(button02));
        vGroup.addGap(GAP);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label03).addComponent(jp1).addComponent(button03));
        vGroup.addGap(GAP);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label04).addComponent(jTextField03).addComponent(button04));
        vGroup.addGap(GAP*6);
        layout.setVerticalGroup(vGroup);

        //按钮监视器
        button01.addActionListener((e -> {
            seSysClipboardText(jTextField01.getText());
        }));
        button02.addActionListener((e -> {
            seSysClipboardText(jTextField02.getText());
        }));
        button03.addActionListener((e -> {
            String machineCode = jTextField01.getText();
            if(machineCode.length() <= 1)
                JOptionPane.showMessageDialog(jFrame, "机器码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            else {
                Server server = new Server();
                int y,m,d;
                if(isForever)
                    y = m = d = -1;
                else {
                    y = date.getYear();
                    m = date.getMonth();
                    d = date.getDateOfWeek();
                }
                String orignalData = server.getOrignal_data(y,m,d,machineCode);
                try {
                    String authCode = server.getAuthencode(orignalData);
                    jTextField02.setText(authCode);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        }));
        button04.addActionListener((e -> {
            seSysClipboardText(jTextField03.getText());
        }));
        checkBox.addActionListener(e -> {
            isForever = checkBox.isSelected();
            if(isForever) {
                date.setEnabled(false);
            }
            else date.setEnabled(true);
        });

        // 显示

        setDisplay.setDisplayMiddleScreenServer(jFrame);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);//窗体默认不显示，设置显示
    }

    private static void seSysClipboardText(String s)
    {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(s);
        clip.setContents(tText,null);
    }
}