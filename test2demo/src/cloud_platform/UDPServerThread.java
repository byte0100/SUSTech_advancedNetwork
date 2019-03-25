package cloud_platform;

import utils.DataMsg;
import utils.MessageQueue;
//import java.io.IOException;
import  java.net.DatagramPacket;
import  java.net.DatagramSocket;
import  java.net.InetAddress;
import java.util.Arrays;


public class UDPServerThread extends Thread{
    private DatagramPacket packet;
    private DatagramSocket socket;
    private byte[] data;
    private InetAddress address;
    private int port;
    private MessageQueue queue;

    public UDPServerThread(DatagramSocket socket, DatagramPacket packet, MessageQueue queue){
        this.socket = socket;
        this.packet = packet;
        int len = packet.getLength();
        this.data = Arrays.copyOfRange(packet.getData(), 0, len);
        this.address = packet.getAddress();
        this.port = packet.getPort();
        this.queue = queue;

    }
    public void run () {
        DataMsg message = new DataMsg(this.data);
        switch(message.getType()) {
            case LOGIN:
                byte[] ack = message.getACK();
                DatagramPacket sendPacket = new DatagramPacket(ack, ack.length, this.address, this.port);
                byte[] time = message.sendControlMsg((byte)33, (byte)5, message.timeCalendar());
                DatagramPacket timePacket = new DatagramPacket(time, time.length, this.address, this.port);

                try {
                    this.socket.send(sendPacket);
                    System.out.println(String.format("Reply ack，address:%s:%d, %s", this.address, this.port, utils.DataFormat.bytes2HexString(ack)));
                    Thread.sleep(1000L);
                    this.socket.send(timePacket);
                    System.out.println(String.format("Reply Timing，address:%s:%d, %s", this.address, this.port, utils.DataFormat.bytes2HexString(time)));
                    System.out.print("Login:");
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
                break;
            case TIME_ACK:
                System.out.print("Timing response:");
                break;
            default:
                System.out.print("Unknown message:");
        }

        this.queue.addMessage(message);
        System.out.println(message.toHexString());
    }
    }



