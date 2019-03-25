//package cloud_platform;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//
//public class UdpSendThread implements Runnable{
//    private DatagramPacket packet;
//    private DatagramSocket socket;
//    public  UdpSendThread(DatagramSocket socket, DatagramPacket packet){
//        this.socket = socket;
//        this.packet = packet;
//    }
//    @Override
//    public void run() {
//        System.out.println("send start");
//        InetAddress address = packet.getAddress();
//        int port = packet.getPort();
//        byte[] data2 = "Ack！".getBytes();
//        DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
//        try {
//            socket.send(packet2);
//            System.out.println("服务器端数据发送完毕");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//
//        }
//    }
//}
