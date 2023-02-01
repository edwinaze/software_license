# SN获取



> [java Runtime.exec方法详解！ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/33547017)
>
> [java Process类详解！ - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/44957705)



## Cpu sn码获取

> **完整代码**

```java

import java.io.IOException;
import java.util.Scanner;

public class test {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });

        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        System.out.println(property + ": " + serial);

    }

}
```

+++

### 注释

#### Runtime.getRuntime().exec()

**Runtime.getRuntime().exec(要执行的程序名)**用于调用外部可执行程序或系统命令,在Java中实现了**调用服务器命令脚本**来执行功能需要。exec方法还有一个返回的对象**Process**，这个对象是给我们用来**控制exec方法创建的进程**的，*该对象可用来控制进程并获得相关信息*。Process 类提供了执行从进程输入、执行输出到进程、等待进程完成、检查进程的退出状态以及销毁(杀掉)进程的方法.

Runtime.exec()返回的是**Process子类**的一个实例.

> 该指令的具体形式

![img](https://pic3.zhimg.com/80/v2-ce760fdffdc0d92ab70c92fc0d28e856_720w.webp)

+++

#### wmic

**wmic**是**cmd**中的命令行工具，**wmic cpu get ProcessorId** 意为获取CPU的序列号.(如要测试是否获得正确的SN码，可自行运行cmd进行测试)

**wmic 对象名 get 对象的某一属性** 能够获取对象属性的值.



> *下图为 **wmic cpu get ProcessorId** 在CMD中运行的结果*

```
C:\Users\lenovo>wmic cpu get ProcessorId
ProcessorId
BFEBFBFF000A0652
```

+++

#### Process

获取Process的数据流呢，要依靠**getInputStream**和**getOutputStream**方法.

**!!!注意!!!**

**Input和Output都是针对当前调用Process的程序而言的，而不是针对Process！**

如果要往process里**输入**数据，要使用getOutputStream().

反之，如果要获取process里的**输出**数据，要用getInputStream().