import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public TCPClient() {
    }

    public static void main(String[] args) {
        try {
            while(true) {
                Socket s = new Socket("127.0.0.1", 50011);
                s.setSoTimeout(10000);
                OutputStream out = s.getOutputStream();
                BufferedInputStream in = new BufferedInputStream(s.getInputStream());
                out.write(utils.DataFormat.hexStringToBytes("FE0F00000D050103010C091E1903181012189543543900873462110000000000007A6EFF"));
                out.flush();

                while(in.available() == 0) {
                }

                int len = in.available();
                if (len != 1) {
                    byte[] content = new byte[len];
                    in.read(content);
                    System.out.println(utils.DataFormat.bytes2HexString(content));
                    Socket socket = new Socket("10.21.84.112",10086);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(content);
                    out.close();
                    socket.close();
                } else {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException var6) {
                        var6.printStackTrace();
                    }
                }

                in.close();
                out.close();
                s.close();
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        }
    }
}
