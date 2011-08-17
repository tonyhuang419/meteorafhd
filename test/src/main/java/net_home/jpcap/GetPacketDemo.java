package net_home.jpcap;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class GetPacketDemo {
	public static void main(String [] args) throws Exception 
	{
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0;
		//if(devices.length>1)/*去掉虚拟网卡的处理*/
		index = 2;
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
		captor.loopPacket(-1, new Receiver()); 
	}
}
