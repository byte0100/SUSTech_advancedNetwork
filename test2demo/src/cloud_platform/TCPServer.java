package cloud_platform;


import utils.MessageQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {
    public TCPServer(){
    }
    public  void run() {
        MessageQueue queue = MessageQueue.getInstance();

        try{
            ServerSocket server = new ServerSocket(50011);
            System.out.println("TCPServer run");
            boolean f = true;
            while(f){
                Socket client = server.accept();
                System.out.println("Client Connect Success!");
                //为每个客户端连接开启一个线程
                (new Thread(new TCPThreadServerSocket(client, queue))).start();
            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }
    }
}

