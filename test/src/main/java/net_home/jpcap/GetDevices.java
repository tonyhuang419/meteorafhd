package net_home.jpcap;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;

public class GetDevices {
	public GetDevices(){
	}

	public static void ShowNeworkInterfaceDevices(){
		//获得网卡设备的实例列表
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		//循环输出全部网卡设备对象相应的信息
		for (int i = 0; i < devices.length; i++) {   //设备号 ,网卡名,网卡描述
			System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");
			//网卡所处数据链路层的名称与其描述 
			System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");
			//网卡MAC地址  
			System.out.print(" MAC address:");
			for (byte b : devices[i].mac_address)//JDK1.5以上版本的for语法的加强变种[循环遍历]
				//转化为十六进制的字串符表示    
				System.out.print(Integer.toHexString(b&0xff) + ":"); 
			System.out.println();  //print out its IP address, subnet mask and broadcast address 
			//输出网卡IP地址 IPV4 IPV6 子网地址 扩播地址 
			for (NetworkInterfaceAddress a : devices[i].addresses)    
				System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);}
	}

	public static void main(String[] args) {
		ShowNeworkInterfaceDevices();
	}
}
