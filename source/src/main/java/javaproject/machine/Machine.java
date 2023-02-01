package javaproject.machine;

import java.io.IOException;
import java.util.List;

import javax.crypto.Mac;

public class Machine {

    public String sn;
    public String sn1;
    public String sn2;
    public String sn3;

    public static void main(String[] args) {
        String MachineCode = new String();

        // CPUsn
        CPU jk = new CPU();
        String sn = jk.GetCPUsn();
        MachineCode += sn;

        // 主板sn
        MotherboardSN mo = new MotherboardSN();
        String sn1 = mo.GetMotherboardSN();
        MachineCode += sn1;

        // 硬盘sn 截取掉第一位(操作在DiskUtils里完成)
        DiskUtils op = new DiskUtils();
        String sn2 = op.GetDiskUtils();
        MachineCode += sn2;
        // System.out.println(sn2);

        // Mac地址
        MacTools kl = new MacTools();
        try {
            List<String> cv = kl.getMacList();
            for (int i = 0; i < cv.size(); i++)
                MachineCode += cv.get(i);
                //System.out.println(cv);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //sn = CPU sn  ;  sn1 = MotherboardSn  ;  sn2 = MacTools;

         //System.out.println(sn);
         //System.out.println(sn1);
         //System.out.println(sn2);

        System.out.println(MachineCode); // 检验MachineCode 内容
        Machine machine = new Machine();
        String s = machine.getMachineCode();
        System.out.println(s);
        if(machine.authenMachineCode(s))
            System.out.println("yes");
        else
            System.out.println("no");
    }

    public Machine(){
        sn = sn1 = sn2 = sn3 = "";
        // CPUsn
        CPU jk = new CPU();
        sn = jk.GetCPUsn();


        // 主板sn
        MotherboardSN mo = new MotherboardSN();
        sn1 = mo.GetMotherboardSN();

        // 硬盘sn 截取掉第一位(操作在DiskUtils里完成)
        DiskUtils op = new DiskUtils();
        sn2 = op.GetDiskUtils();
        // System.out.println(sn2);

        // Mac地址
        MacTools kl = new MacTools();
        try {
            List<String> cv = kl.getMacList();
            for (int i = 0; i < cv.size(); i++)
                sn3 += cv.get(i);
            //System.out.println(cv);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(sn3);
    }

    public String getMachineCode() {
        String MachineCode = new String();
        MachineCode = sn + sn1 + sn2 + sn3;

        //sn = CPU sn  ;  sn1 = MotherboardSn  ;  sn2 = MacTools;

        //System.out.println(sn);
        //System.out.println(sn1);
        //System.out.println(sn2);

        //System.out.println(MachineCode); // 检验MachineCode 内容
        return MachineCode;
    }

    public boolean authenMachineCode(String machinecode) {
        String res = sn + sn1 + sn2 + sn3;
        if(res.equals(machinecode))
            return true;
        return false;
    }

}
