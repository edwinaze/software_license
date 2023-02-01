package javaproject.menu.css;

import java.awt.*;
import javax.swing.*;

public class setDisplay{
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 200;
    public setDisplay(){}
    public static void setDisplayMiddleScreen(JFrame jFrame)
    {
        var tool = Toolkit.getDefaultToolkit();
        var screen = tool.getScreenSize();
        int x = screen.width - DEFAULT_WIDTH;
        int y = screen.height - DEFAULT_HEIGHT;
        jFrame.setBounds(x/2,y/2,DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
    public static void setDisplayMiddleScreenServer(JFrame jFrame)
    {
        var tool = Toolkit.getDefaultToolkit();
        var screen = tool.getScreenSize();
        int x = screen.width - DEFAULT_WIDTH-100;
        int y = screen.height - DEFAULT_HEIGHT-60;
        jFrame.setBounds(x/2,y/2,DEFAULT_WIDTH+100,DEFAULT_HEIGHT+60);
    }
}
