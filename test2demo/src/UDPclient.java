//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.util.Arrays;
////import util.Util;
//
//public class UDPclient {
//        public static void main(String[] args) throws Exception{
//            InetAddress address=InetAddress.getByName("127.0.0.1");
//            int port=8080;
//            DatagramSocket socket=new DatagramSocket();
////            while (true) {
//            String data = "用户名：abc;密码：555" ;
//            byte[] dataByte = data.getBytes();
//            DatagramPacket packet = new DatagramPacket(dataByte, dataByte.length, address, port);
//            socket.send(packet);
//            byte[] fromClient = new byte[1024];
//            DatagramPacket frompacket = new DatagramPacket(fromClient, fromClient.length);//创建数据包，将会用数据包接受收到的信息
//                //Thread.sleep(1000L);
//            socket.receive(frompacket);
//            String fromServer = new String(fromClient, 0, frompacket.getLength());
//            System.out.println("我是客户端，服务器端说： " + fromServer);
////            }
//        }
//
//}
