import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class UDPClient2 {
    public UDPClient2() {
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
//        InetAddress address = InetAddress.getByName("118.126.90.148");
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 60011;
        String str = "FE0F00000D050103010C091E1903181012189543543900873462110000000000007A6EFF";
        byte[] sendBuf = utils.DataFormat.hexStringToBytes(str);
        byte[] revBuf = new byte[100];
        DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        s.send(packet);
        Thread.sleep(1000L);
        DatagramPacket revPacket = new DatagramPacket(revBuf, revBuf.length);
        s.receive(revPacket);
        byte[] ack = Arrays.copyOfRange(revPacket.getData(), 0, revPacket.getLength());
        System.out.println(utils.DataFormat.bytes2HexString(ack));
        Thread.sleep(1000L);
        DatagramPacket timePacket = new DatagramPacket(revBuf, revBuf.length);
        s.receive(timePacket);
        byte[] time = Arrays.copyOfRange(timePacket.getData(), 0, timePacket.getLength());
        System.out.println(utils.DataFormat.bytes2HexString(time));
        if (time[4] == 1) {
            str = "fe0F00000D03010300010f087aff";
            sendBuf = utils.DataFormat.hexStringToBytes(str);
            sendBuf[9] = time[6];
            sendBuf[10] = time[7];
            sendBuf[12] = utils.CRC8.calcCrc8(sendBuf);
            DatagramPacket sendTimePacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            s.send(sendTimePacket);
        }
        String[] str1 = new String[]{"fe0F00000D0307000c12195a4321646300ff","fe0F00000D0307000c16197a4321646300ff",
                "fe0F00000D0307000c14199a4321646300ff","fe0F00000D0307000c12185a4321646300ff","fe0F00000D0307000c14186a4321646300ff",
                "fe0F00000D0307000c18186a4321646300ff","fe0F00000D0307000c11197a4321646300ff","fe0F00000D0307000c11198a4321646300ff",
                "fe0F00000D0307000c12186a4321646300ff","fe0F00000D0307000c12192a4321646300ff"};


        for(int i = 0; i < 9; ++i) {
//            str = "fe0F00000D0307000c13195a4321646300ff";

            sendBuf = utils.DataFormat.hexStringToBytes(str1[i]);
            sendBuf[sendBuf.length - 2] = utils.CRC8.calcCrc8(sendBuf);
            System.out.println("发送环境消息：" + utils.DataFormat.bytes2HexString(sendBuf));
            DatagramPacket sendSensorPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            s.send(sendSensorPacket);
            Thread.sleep(1000L);
        }

        s.close();
    }
}
