package cloud_platform;

public class UdpTcpServer {
    public UdpTcpServer() {
    }

    public static void main(String[] args) {
        (new Thread(new UDPServer())).start();
        (new Thread(new TCPServer())).start();
    }
}