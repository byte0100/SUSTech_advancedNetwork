package cloud_platform;//package test2demo;

import utils.Constant;
import utils.DataMsg;
import utils.MessageQueue;

import  java.io.IOException;
import  java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer implements Runnable{
    public UDPServer() {
    }
    public void run() {
        MessageQueue queue = MessageQueue.getInstance();

        try {
            byte[] buf = new byte[1024];
            DatagramSocket ds = new DatagramSocket(Constant.PORT);
            DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
            System.out.println("UDP server has run,waiting for client to send data......");
            boolean f = true;
            while(f){
                ds.receive(dp_receive);//阻塞
                //ds.setSoTimeout(1000);
//            System.out.println("server received data from client:");
//                UdpSendThread send = new UdpSendThread(ds,dp_receive);
                (new Thread(new UDPServerThread(ds,dp_receive,queue))).start();
//            send.run();//拉起发送线程

            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }
//    public static void  main(String[] args)throws IOException{
//
//        byte[] buf = new byte[1024];
//        DatagramSocket ds = new DatagramSocket(Constant.PORT);
//        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
//        System.out.println("UDP server has run,waiting for client to send data......");
//        boolean f = true;
//        while(f){
//            ds.receive(dp_receive);//阻塞
//            //ds.setSoTimeout(1000);
////            System.out.println("server received data from client:");
//            UdpSendThread send = new UdpSendThread(ds,dp_receive);
//            UDPServerThread thread = new UDPServerThread(buf,ds, dp_receive);
//            thread.start();
////            send.run();//拉起发送线程
//
//        }
//    }
}

