# MAC physical address

>[How can i get list of MAC address and IP address of computer through java? - Stack Overflow](https://stackoverflow.com/questions/12740905/how-can-i-get-list-of-mac-address-and-ip-address-of-computer-through-java)
>
>[ java如何获取mac物理地址？_码农下的天桥的博客-CSDN博客_java获取设备mac地址](https://blog.csdn.net/cdnight/article/details/86741265)

## 前言

#### *MAC是什么*

*MAC地址* 也叫物理地址、硬件地址，由网络设备制造商生产时烧录在网卡(Network lnterface Card)的[EPROM](https://baike.baidu.com/item/EPROM/1690813?fromModule=lemma_inlink)(一种闪存芯片，通常可以通过程序擦写)。[IP地址](https://baike.baidu.com/item/IP地址/150859?fromModule=lemma_inlink)与MAC地址在计算机里都是以[二进制](https://baike.baidu.com/item/二进制/361457?fromModule=lemma_inlink)表示的，IP地址是32位的，而MAC地址则是48位的.

MAC地址的长度为48位(6个字节)，通常表示为12个16进制数，如：00-16-EA-AE-3C-40就是一个MAC地址，其中前3个字节，16进制数00-16-EA代表网络硬件制造商的编号，它由[IEEE](https://baike.baidu.com/item/IEEE/150905?fromModule=lemma_inlink)(电气与电子工程师协会)分配，而后3个字节，16进制数AE-3C-40代表该制造商所制造的某个网络产品(如网卡)的系列号。只要*不更改自己的MAC地址，MAC地址在世界是唯一的*。形象地说，MAC地址就如同身份证上的身份证号码，具有**唯一性**。

> 正因为MAC地址具有唯一性，所以在注册机中也将它加入机器码中作为标识。



#### *如何利用CMD获取 MAC address?*

在CMD中输入"ipconfig /all"即可显示当前计算机的一些信息,其中“Physical Address”字样的这一项就是当前计算机中网卡的 MAC地址。当然，如果计算机中安装有多个网卡，则会有多个“Physical Address”字样。

> 具体样例

```
C:\Users\lenovo>ipconfig /all

Windows IP 配置

   主机名  . . . . . . . . . . . . . : LAPTOP-PSQOGF23
   主 DNS 后缀 . . . . . . . . . . . :
   节点类型  . . . . . . . . . . . . : 混合
   IP 路由已启用 . . . . . . . . . . : 否
   WINS 代理已启用 . . . . . . . . . : 否
   DNS 后缀搜索列表  . . . . . . . . : lan

以太网适配器 以太网:

   媒体状态  . . . . . . . . . . . . : 媒体已断开连接
   连接特定的 DNS 后缀 . . . . . . . :
   描述. . . . . . . . . . . . . . . : Realtek PCIe GbE Family Controller
   物理地址. . . . . . . . . . . . . : 8C-8C-AA-95-58-44
   DHCP 已启用 . . . . . . . . . . . : 是
   自动配置已启用. . . . . . . . . . : 是

无线局域网适配器 本地连接* 8:

   媒体状态  . . . . . . . . . . . . : 媒体已断开连接
   连接特定的 DNS 后缀 . . . . . . . :
   描述. . . . . . . . . . . . . . . : Microsoft Wi-Fi Direct Virtual Adapter
   物理地址. . . . . . . . . . . . . : DC-41-A9-5E-90-6C
   DHCP 已启用 . . . . . . . . . . . : 是
   自动配置已启用. . . . . . . . . . : 是

无线局域网适配器 本地连接* 10:

   媒体状态  . . . . . . . . . . . . : 媒体已断开连接
   连接特定的 DNS 后缀 . . . . . . . :
   描述. . . . . . . . . . . . . . . : Microsoft Wi-Fi Direct Virtual Adapter #2
   物理地址. . . . . . . . . . . . . : DE-41-A9-5E-90-6B
   DHCP 已启用 . . . . . . . . . . . : 否
   自动配置已启用. . . . . . . . . . : 是

以太网适配器 以太网 3:

   媒体状态  . . . . . . . . . . . . : 媒体已断开连接
   连接特定的 DNS 后缀 . . . . . . . :
   描述. . . . . . . . . . . . . . . : TAP-Windows Adapter V9
   物理地址. . . . . . . . . . . . . : 00-FF-43-02-FD-C7
   DHCP 已启用 . . . . . . . . . . . : 是
   自动配置已启用. . . . . . . . . . : 是

以太网适配器 以太网 4:

   媒体状态  . . . . . . . . . . . . : 媒体已断开连接
   连接特定的 DNS 后缀 . . . . . . . :
   描述. . . . . . . . . . . . . . . : Netease UU TAP-Win32 Adapter V9.21
   物理地址. . . . . . . . . . . . . : 00-FF-D4-E3-7D-24
   DHCP 已启用 . . . . . . . . . . . : 是
   自动配置已启用. . . . . . . . . . : 是

无线局域网适配器 WLAN:

   连接特定的 DNS 后缀 . . . . . . . : lan
   描述. . . . . . . . . . . . . . . : Intel(R) Wi-Fi 6 AX201 160MHz
   物理地址. . . . . . . . . . . . . : DC-41-A9-5E-90-6B
   DHCP 已启用 . . . . . . . . . . . : 是
   自动配置已启用. . . . . . . . . . : 是
   本地链接 IPv6 地址. . . . . . . . : fe80::fc7c:6e7:c190:61d2%17(首选)
   IPv4 地址 . . . . . . . . . . . . : 192.168.110.2(首选)
   子网掩码  . . . . . . . . . . . . : 255.255.255.0
   获得租约的时间  . . . . . . . . . : 2022年11月7日 18:32:43
   租约过期的时间  . . . . . . . . . : 2022年11月7日 19:58:09
   默认网关. . . . . . . . . . . . . : 192.168.110.1
   DHCP 服务器 . . . . . . . . . . . : 192.168.110.1
   DHCPv6 IAID . . . . . . . . . . . : 299647401
   DHCPv6 客户端 DUID  . . . . . . . : 00-01-00-01-27-9B-0A-54-8C-8C-AA-95-58-44
   DNS 服务器  . . . . . . . . . . . : 192.168.110.1
   TCPIP 上的 NetBIOS  . . . . . . . : 已启用
```



+++

# java代码

Enumeration接口中定义了一些方法，通过这些方法可以枚举（一次获得一个）对象集合中的元素。

NetworkInterface是在JDK1.4是添加的一个类,使用 *getNetworkInterfaces* 方法即可得到当前机器上所有的网络接口。

```java
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MacTools {
    /*** 因为一台机器不一定只有一个网卡呀，所以返回的是数组是很合理的 ***/
    public static List<String> getMacList() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); //getNetworkInterfaces()获取电脑上所有的网络接口，并用Enumeration接口来接收(Enumeration接口中定义了一些方法，通过这些方法可以枚举（一次获得一个）对象集合中的元素。)
        StringBuilder sb = new StringBuilder(); //建立一个StringBuilder的对象 String内容是不可变的，StringBuilder内容是可变的
        ArrayList<String> tmpMacList = new ArrayList<>(); // ArrayList 类是一个可以动态修改的数组，与普通数组的区别就是它是没有固定大小的限制，我们可以添加或删除元素。
        while (en.hasMoreElements())
        {//实现 Enumeration 接口的对象，它生成一系列元素，一次生成一个。连续调用 nextElement 方法将返回一系列的连续元素。
            NetworkInterface iface = en.nextElement(); //iface 依次接收接口数据
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();/*获取网络接口的InterfaceAddresses列表。通过使用InterfaceAddresses 类中的方法可以取得网络接口对应的IP地址、子网掩码和广播地址等相关信息。返回绑定到该网卡的所有ip地址 一个网络接口可能对应多个ip（当然正常是1对1关系）

对于IPv4地址，可以取得IP地址、子网掩码和广播地址，
对于IPv6地址，可以取得IP地址和网络前缀长度这样的信息。*/
            for (InterfaceAddress addr : addrs) /*Java有一个功能很强的循环结构，可以用来依次处理数组（或者其他元素集合）中的每个元素，而不必考虑指定的下标值。*/
            {
                InetAddress ip = addr.getAddress();/*ava提供了InetAddress类来代表IP地址，InetAddress下还有两个子类：Inet4Address、Inet6Address，它们分别代表Internet Protocol version 4（IPv4）地址和Internet Protocol version 6（IPv6）地址。
                 getAddress()：返回此InetAddress对象的原始 IP 地址。 结果按网络字节顺序排列：地址的最高顺序字节在getAddress()[0] 。
                */
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);//可以利用getByInetAddress方法来确定一个IP地址属于哪一个网络接口。由于getByInetAddress方法必须使用一个InetAddress对象封装的IP地址来作为参数，因此，在使用getByInetAddress方法之前，必须先创建一个InetAddress对象。


                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();/*public byte[] getHardwareAddress()方法的作用:获得网卡的硬件地址。(也就是MAC地址)*/
                if (mac == null) {
                    continue;
                }
                sb.delete(0, sb.length()); // 清空StringBuffer中内容
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
                }
                tmpMacList.add(sb.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        /*** 去重，别忘了同一个网卡的ipv4,ipv6得到的mac都是一样的，肯定有重复，下面这段代码是。。流式处理 ***/
        List<String> unique = tmpMacList.stream().distinct().collect(Collectors.toList());
        return unique;
    }
    
    public List<String> GetMac(String[] aStrings) throws Exception {
        List<String> macs = getMacList();
        return macs;
    }
}

```

# 具体变量的值

**java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();**

getNetworkInterfaces()获取电脑上所有的网络接口

eth指的是本地网卡，有mac地址

wlan指的无线网卡，也有mac地址

> en的数据

```
name:lo (Software Loopback Interface 1)
name:ppp0 (WAN Miniport (PPPOE))
name:net0 (Microsoft 6to4 Adapter)
name:eth0 (WAN Miniport (IP))
name:eth1 (Remote NDIS based Internet Sharing Device)
name:net1 (Microsoft IP-HTTPS Platform Adapter)      
name:eth2 (TAP-Windows Adapter V9)
name:eth3 (WAN Miniport (Network Monitor))
name:eth4 (Bluetooth Device (Personal Area Network)) 
name:net2 (WAN Miniport (SSTP))
name:eth5 (Microsoft Kernel Debug Network Adapter)   
name:wlan0 (Microsoft Wi-Fi Direct Virtual Adapter)  
name:net3 (WAN Miniport (PPTP))
name:net4 (Microsoft Teredo Tunneling Adapter)
name:net5 (WAN Miniport (IKEv2))
name:eth6 (Realtek PCIe GbE Family Controller)
name:wlan1 (Intel(R) Wi-Fi 6 AX201 160MHz)
name:net6 (WAN Miniport (L2TP))
name:wlan2 (Microsoft Wi-Fi Direct Virtual Adapter #2)
name:eth7 (Netease UU TAP-Win32 Adapter V9.21)
name:eth8 (WAN Miniport (IPv6))
name:eth9 (TAP-Windows Adapter V9-WFP Native MAC Layer LightWeight Filter-0000)
name:eth10 (TAP-Windows Adapter V9-Npcap Packet Driver (NPCAP)-0000)
name:eth11 (Netease UU TAP-Win32 Adapter V9.21-WFP Native MAC Layer LightWeight Filter-0000)
name:eth12 (TAP-Windows Adapter V9-QoS Packet Scheduler-0000)
name:eth13 (Netease UU TAP-Win32 Adapter V9.21-Npcap Packet Driver (NPCAP)-0000)
name:eth14 (TAP-Windows Adapter V9-WFP 802.3 MAC Layer LightWeight Filter-0000)
name:eth15 (Netease UU TAP-Win32 Adapter V9.21-QoS Packet Scheduler-0000)
name:eth16 (Netease UU TAP-Win32 Adapter V9.21-WFP 802.3 MAC Layer LightWeight Filter-0000)
name:eth17 (Realtek PCIe GbE Family Controller-WFP Native MAC Layer LightWeight Filter-0000)
name:eth18 (Realtek PCIe GbE Family Controller-Npcap Packet Driver (NPCAP)-0000)
name:eth19 (Realtek PCIe GbE Family Controller-QoS Packet Scheduler-0000)
name:eth20 (Realtek PCIe GbE Family Controller-WFP 802.3 MAC Layer LightWeight Filter-0000)
name:wlan3 (Intel(R) Wi-Fi 6 AX201 160MHz-WFP Native MAC Layer LightWeight Filter-0000)
name:eth21 (WAN Miniport (IP)-WFP Native MAC Layer LightWeight Filter-0000)
name:eth22 (WAN Miniport (IP)-Npcap Packet Driver (NPCAP)-0000)
name:eth23 (WAN Miniport (IP)-QoS Packet Scheduler-0000)
name:eth24 (WAN Miniport (IPv6)-WFP Native MAC Layer LightWeight Filter-0000)
name:eth25 (WAN Miniport (IPv6)-Npcap Packet Driver (NPCAP)-0000)
name:eth26 (WAN Miniport (IPv6)-QoS Packet Scheduler-0000)
name:eth27 (WAN Miniport (Network Monitor)-WFP Native MAC Layer LightWeight Filter-0000)
name:eth28 (WAN Miniport (Network Monitor)-Npcap Packet Driver (NPCAP)-0000)
name:eth29 (WAN Miniport (Network Monitor)-QoS Packet Scheduler-0000)
name:wlan4 (Intel(R) Wi-Fi 6 AX201 160MHz-Virtual WiFi Filter Driver-0000)
name:wlan5 (Intel(R) Wi-Fi 6 AX201 160MHz-Native WiFi Filter Driver-0000)
name:wlan6 (Intel(R) Wi-Fi 6 AX201 160MHz-Npcap Packet Driver (NPCAP)-0000)
name:wlan7 (Intel(R) Wi-Fi 6 AX201 160MHz-QoS Packet Scheduler-0000)
name:wlan8 (Intel(R) Wi-Fi 6 AX201 160MHz-WFP 802.3 MAC Layer LightWeight Filter-0000)
name:wlan9 (Microsoft Wi-Fi Direct Virtual Adapter-WFP Native MAC Layer LightWeight Filter-0000)
name:wlan10 (Microsoft Wi-Fi Direct Virtual Adapter-Native WiFi Filter Driver-0000)
name:wlan11 (Microsoft Wi-Fi Direct Virtual Adapter-Npcap Packet Driver (NPCAP)-0000)
name:wlan12 (Microsoft Wi-Fi Direct Virtual Adapter-QoS Packet Scheduler-0000)
name:wlan13 (Microsoft Wi-Fi Direct Virtual Adapter-WFP 802.3 MAC Layer LightWeight Filter-0000)
name:wlan14 (Microsoft Wi-Fi Direct Virtual Adapter #2-WFP Native MAC Layer LightWeight Filter-0000)
name:wlan15 (Microsoft Wi-Fi Direct Virtual Adapter #2-Native WiFi Filter Driver-0000)
name:wlan16 (Microsoft Wi-Fi Direct Virtual Adapter #2-Npcap Packet Driver (NPCAP)-0000)
name:wlan17 (Microsoft Wi-Fi Direct Virtual Adapter #2-QoS Packet Scheduler-0000)
name:wlan18 (Microsoft Wi-Fi Direct Virtual Adapter #2-WFP 802.3 MAC Layer LightWeight Filter-0000)
```



**System.out.println(iface.getInterfaceAddresses());**

> iface的值

```
C:\Users\lenovo\Desktop\Code>cd "c:\Users\lenovo\Desktop\Code\" && javac test.java && java test
[/127.0.0.1/8 [/127.255.255.255], /0:0:0:0:0:0:0:1/128 [null]]
[]
[]
[]
[]
[]
[/fe80:0:0:0:8c9e:7e3b:10a7:18c4%eth2/64 [null]] 
[]
[]
[]
[]
[/fe80:0:0:0:1121:8aff:f465:3a90%wlan0/64 [null]]
[]
[]
[]
[/fe80:0:0:0:cdee:db7f:9352:68dd%eth6/64 [null]]
[/192.168.43.16/24 [/192.168.43.255], /240e:43d:350:30c9:fc7c:6e7:c190:61d2/64 [null], /240e:43d:350:30c9:20c4:4221:efd0:23f3/128 [null], /fe80:0:0:0:fc7c:6e7:c190:61d2%wlan1/128 [null]]
[]
[/fe80:0:0:0:6db6:ed10:f717:befa%wlan2/64 [null]]
[/fe80:0:0:0:4469:a9ce:f836:abec%eth7/64 [null]]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
[]
```

 

**InetAddress ip = addr.getAddress();** 

**getAddress()** 返回InetAddress对象的**原始IP**.

> ip的值

```
C:\Users\lenovo\Desktop\Code>cd "c:\Users\lenovo\Desktop\Code\" && javac test.java && java test
/127.0.0.1
/0:0:0:0:0:0:0:1
/fe80:0:0:0:8c9e:7e3b:10a7:18c4%eth2 
/fe80:0:0:0:1121:8aff:f465:3a90%wlan0
/fe80:0:0:0:cdee:db7f:9352:68dd%eth6 
/192.168.110.254
/fe80:0:0:0:fc7c:6e7:c190:61d2%wlan1 
/fe80:0:0:0:6db6:ed10:f717:befa%wlan2
/fe80:0:0:0:4469:a9ce:f836:abec%eth7 
```



**NetworkInterface network = NetworkInterface.getByInetAddress(ip);**

可以利用getByInetAddress方法来确定一个IP地址属于哪一个网络接口。由于getByInetAddress方法必须使用一个InetAddress对象封装的IP地址来作为参数，因此，在使用getByInetAddress方法之前，必须先创建一个InetAddress对象。 

不能使用远程的IP的域名来创建InetAddress对象，否则getByInetAddress将返回null。

> network的值 去重 lo是本机地址 去lo后便是最终mac

```
name:lo (Software Loopback Interface 1)
name:lo (Software Loopback Interface 1)
name:eth2 (TAP-Windows Adapter V9)
name:wlan0 (Microsoft Wi-Fi Direct Virtual Adapter)
name:eth6 (Realtek PCIe GbE Family Controller)
name:wlan1 (Intel(R) Wi-Fi 6 AX201 160MHz)
name:wlan1 (Intel(R) Wi-Fi 6 AX201 160MHz)
name:wlan2 (Microsoft Wi-Fi Direct Virtual Adapter #2)
name:eth7 (Netease UU TAP-Win32 Adapter V9.21) 
```

