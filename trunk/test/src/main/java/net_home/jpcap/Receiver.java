package net_home.jpcap;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;


class Receiver implements PacketReceiver
{   
    /*实例receivePacket方法*/
   public void receivePacket(Packet packet)
   {
      /*进行简单的处理*/
    System.out.println(packet);
   }
}
