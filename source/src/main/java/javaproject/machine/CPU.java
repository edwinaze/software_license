package javaproject.machine;

import java.io.IOException;
import java.util.Scanner;

public class CPU {
    /**
     * @param args
     * @throws IOException
     */
    public static String GetCPU(String[] a) throws IOException {
        // TODO Auto-generated method stub
        long start = System.currentTimeMillis();
        Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        return serial;
    }

    public String GetCPUsn(){
        String[] k = new String[110];
        String sn = new String();
        try {
            sn = GetCPU(k);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sn;
        
    }
}
