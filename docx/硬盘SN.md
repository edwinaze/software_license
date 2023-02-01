# 硬盘SN



### 完整JAVA代码

```java
package MACHINECODE;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DiskUtils{

    public static String getSerialNumber(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto",".vbs");
            //File.createTempFile创建临时文件，指定文件名和后缀
            file.deleteOnExit();
            //JVM终止时删除文件
            FileWriter fw = new java.io.FileWriter(file);
            //FileWrite类按字符向流中写入数据

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    +"Set colDrives = objFSO.Drives\n"
                    +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    +"Wscript.Echo objDrive.SerialNumber";  // see note
            //Set objFSO = CreateObject 该绑定方式无需库 利用FSO对象操作文件 CreateObject(名称.类型)
            //objFSO.Drives 返回本地计算机可用的驱动器列表
            /*colDrives.item(Key)返回一个键为Key（驱动器名）的Drive对象。Drive对象集合不是普通的集合，因此不能使用驱动器的索引值（在集合中的位置序号），否则将产生运行时错误“无效的过程调用或参数”。*/
            //Wscript.Echo:显示简单的文本信息  objDrive.SerialNumber:返回驱动器系列号，Long类型。
            fw.write(vbs);//向文件内写入内容
            fw.close(); // 关闭流
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
           /*Runtime.getRuntime().exec(要执行的程序名)用于调用外部可执行程序或系统命令,在Java中实现了调用服务器命令脚本来执行功能需要。exec方法还有一个返回的对象Process，这个对象是给我们用来控制exec方法创建的进程的，该对象可用来控制进程并获得相关信息。Process 类提供了执行从进程输入、执行输出到进程、等待进程完成、检查进程的退出状态以及销毁(杀掉)进程的方法.
Runtime.exec()返回的是**Process子类**的一个实例.
	exec(String)现已被弃用？
	file.getPath()此函数返回给定文件对象的路径。该函数返回一个字符串对象，其中包含给定文件对象的路径。
*/
            BufferedReader input =new BufferedReader(new InputStreamReader(p.getInputStream()));
            /*BuffereReader 缓冲区读取内容，完成字节流转换至字符流的操作 
            new BufferedReader()将字符流放到字符流缓冲区中
            new InputStreamReader()将字节流转化为字符流
            p.getInputStream() 字节流输入
            */
            String line;
            while ((line = input.readLine()) != null) //按行完成读取
            {
                result += line;
            }
            input.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result.trim();
        //trim() 用于删除字符串的头尾空白符。
    }

    public String GetDiskUtils()
    {
        String sn = DiskUtils.getSerialNumber("C");
        return sn.substring(1);
        //substring 返回字符串的子字符串
    }
}

```

