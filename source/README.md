# 运行环境：

IDEA2022.2

JDK18

# 软件验证模块项目文档

##  1 概述

⽬前⼤部分商业软件在提供注册码时，基本上采⽤了以下⼏种机制来实现：

- **远程联⽹激活**。在软件每次启动时，都会联⽹检查软件使⽤情况，检查使⽤时间是否 到期，是否是多设备使⽤等。这种⽅式控制是⾮常棒的，动态性、实时性都⾮常好， 但劣势也⾮常明显，就是客户机软件要联⽹，如若没有联⽹就⽆法控制了。 
- **本地⽣成注册**。这种⽅式是根据客户机的环境，获取客户机的信息，⽐如硬盘、 MAC 地址 、 CPU 等硬件信息，根据⼀定的算法将这些信息⽣成⼀个注册码。⽬前超过⼀半 的软件都是采⽤这种⽅式来实现的，这种⽅式的缺点是不能⾃由地控制软件的其它参 数，⽐如软件中可添加设备的数量。
- **配套密钥⽂件**。在软件发⾏的过程中，⽤软件运⾏到期时间、运⾏数量限制和已运⾏ 时间等参数⽣成⼀个密钥⽂件，配套发送给⽤户使⽤。在软件启动时，直接加载这个 密钥⽂件进⾏检查。这种⽅式的缺点在于密钥⽂件的参数选择上不好把控，若只仅仅 设置运⾏到期时间，⽤户可以轻松修改电脑时间来获取更⻓使⽤时间（在不联⽹同步 时间的情况下）。

而本项目采用的方法是本地生成注册, 即一机一码注册方式, 根据唯一的机器码对应生成注册码。

项目可以按应用端分成两个部分:

- 客户端（提供机器码的部分）
- 注册机（根据机器码计算其对应注册码）

核心技术是通过JAVA类库获取硬盘, CPU, 主板SN码 和 MAC 地址, 分别截取其中一部分字符拼接起来得到该机器的机器码。注册码则是在该机器机器码后面加上长度为8位的日期（如20221206)作为过期时间。验证过程是将接受到的注册码分离出机器码+日期两部分, 比对机器码与本机机器码是否一致, 若一致则注册码成立。

若采用明文传输, 用户可以很简单的修改后面的日期以达到无限注册, 所以需要采取加密手段保证验证码的保密性。我们采取**RSA+BASE64**加密来实现。

而对于如何实现在打开时自动读取之前的注册状态, 不需要重复注册的功能, 我们采用生成密钥配置文件, 即在软件目录下生成`config.properties`文件, 在软件启动时读取其中保存的注册码, 再次进行验证操作。



## 2 工程目录

```txt
.
├── main/java/javaproject                   
│   ├── authen // 验证模块
│   ├── machine // 获取机器码模块
│   ├── menu  // 前端模块
├── resources // 存放资源文件, 和验证状态保存
├── pom.xml                                      
```



# 3 验证模块authen

```
├──authen // 验证模块
│   ├── client // 客户端验证方法
│   ├── server  // 注册机验证方法
```

### 3.1 client

该类中存放客户端会用到的静态方法。

包含以下内容：

- 将传入的密文注册码用公钥解密并返回明文注册码（机器码+日期）

```java
public static String decodeAthuncode(String authencode) 
```

- 传入一个字符串日期"20221023", 判断是否过期, 即在当天之前

```java
public static boolean isExpired(String date)
```

- 将从注册码中提取的机器码与本机机器码进行比对

```java
public static boolean isValid(String deCodeMachineCode, String machineCode)
```

- 得到明文注册码的机器码部分

```java
public static String getMachineCode(String deCode)
```

- 得到明文注册码的日期部分

```java
public static String getDate(String deCode)
```

- 将字符串形式的日期转换成`Date`对象

```java
public static Date stringToDate(String date)
```

- 得到两个日期之间差距天数, 用于实现显示“注册剩余时间"

```java
public static int getTimeDistance(Date beginDate , Date endDate )
```

使用关系如下：

![image-20221216221754456](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216221754456.png)

![image-20221216221809370](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216221809370.png)

### 3.2 Server

该类存放注册机所需要的静态方法。

包含以下内容：

- 将明文通过私钥加密为密文

```java
public String getAuthencode(String orignal_data)
```

- 将输入的日期和机器码拼接为注册码

```java
public String getOrignal_data(int year, int month, int day, String machine)
```

使用关系如下：

![image-20221216222215397](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216222215397.png)

## 4 机器码模块

```
├── machine // 验证模块
│   ├── CPU // 获取CPU SN码
│   ├── DiskUils  // 获取硬盘 SN码
│   ├── MotherboardSN  // 获取主板 SN码
│   ├── MacTools  // 获取MAC码
│   ├── Machine  // 将获取到的码汇总截取一部分组成机器码
```

### 4.1 CPU

通过`Runtime.getRuntime().exec()`方法, 执行CMD命令 `wmic cpu get ProcessorId`之类并通过`Scanner`读取`process.getInpuStream`流的结果。

该CMD命令效果：

![image-20221216204205245](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216204205245.png)

`BFEBFBFF000806D1`就是我们需要的SN码。

### 4.2 DiskUils

采用运行vbs脚本的方法来获取。创建一个临时的vbs文件如下：

```vbscript
Set objFSO = CreateObject("Scripting.FileSystemObject") '利用FSO对象操作
Set colDrives = objFSO.Drives '返回可用的驱动器列表
Set objDrive = colDrives.item(drive) '获取drive驱动器的信息
Wscript.Echo objDrive.SerialNumber '控制台输出SN码
```

然后通过上一步类似的方法`Runtime.getRuntime().exec()`, 来进行调用并获取结果。

### 4.3 MotherboardSN

一样使用vbs脚本来获取。

```vbscript
Set objWMIService = GetObject("winmgmts:\\.\root\cimv2")
Set colItems = objWMIService.ExecQuery _ 
("Select * from Win32_BaseBoard")
For Each objItem in colItems
	Wscript.Echo objItem.SerialNumber
	exit for  ' do the first cpu only!
Next
```

然后通过上一步类似的方法`Runtime.getRuntime().exec()`, 来进行调用并获取结果。

### 4.4 MacTools

NetworkInterface是在JDK1.4是添加的一个类,使用 `getNetworkInterfaces` 方法即可得到当前机器上所有的网络接口。

细节请查看docx目录下的MAC physical address.md文件。

### 4.5 Machine

- 获取机器信息并拼接成一个机器码返回

```java
public String getMachineCode()
```

使用关系如下：

![image-20221216221440298](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216221440298.png)

## 5 menu

```
├── menu // 前端模块
│   ├── clientMenu // 客户端功能实现
│   ├── css  // 设定窗口大小
│   ├── serverMenu  // 注册机功能实现
```

### 5.1 clientMenu

使用JFrame类库实现GUI界面, 采用Group对齐方式排版, 同时使用FlatLaf类库来美化界面。

运行流程如图：

![image-20221216212806646](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216212806646.png)

![image-20221216214138325](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216214138325.png)![image-20221216214734138](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216214734138.png)

![image-20221216214718781](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216214718781.png)

![image-20221216214332943](C:\Users\aze\AppData\Roaming\Typora\typora-user-images\image-20221216214332943.png)

### 5.2 serverMenu

根据机器码和设定的到期日期来计算出注册码



