package cloud_platform;

import utils.DataMsg;
import utils.MessageQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;



public class TCPThreadServerSocket implements Runnable {
    private Socket client ;
    private MessageQueue queue;
    public TCPThreadServerSocket(Socket client, MessageQueue queue){
        this.queue = queue;
        this.client = client;

    }

    public void run() {
        try{
            //获取Socket的输出流，用来向客户端发送数据
            OutputStream out = this.client.getOutputStream();
            System.out.println("Get TCP request,Queue size:"+this.queue.size());
            DataMsg res = this.queue.getMessage();
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            if(res != null){
                out.write(res.getBuffer());
            }else {
                out.write(-1);
            }
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
