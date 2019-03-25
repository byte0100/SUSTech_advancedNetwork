package utils;

import java.io.OutputStream;
import java.util.Arrays;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import utils.DataFormat.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DataMsg {
    private byte[] buffer;
    private DataFormat.Type type;
    private int len;
    private byte[] id;
    private byte[] data;
    private byte location;
    private byte func;
    private final static int FuncData = 3;  //子功能数据长度大小
    private final static int RandomRange = 1000; //随机数范围获取

    public byte[] buildRandom(int d) {
        int random = (int) (Math.random() * d);
        byte[] buf = new byte[2];
        buf[0] = (byte) random;
        buf[1] = (byte) (random >> 8);  //高位
        return buf;
    }
    public DataMsg(byte[] message) {
        this.buffer = message;
        this.len = message.length;
        if (this.buffer[0] == -2 && this.buffer[this.len - 1] == -1) {
            this.id = Arrays.copyOfRange(this.buffer, 1, 5);
            this.data = Arrays.copyOfRange(this.buffer, 12, this.len - 2);
            this.location = this.buffer[5];
            this.func = this.buffer[6];
            switch(this.location) {
                case 3:
                    if (this.buffer[7] == 1) {
                        this.type = Type.TIME_ACK;
                    } else {
                        this.type = Type.SENSOR_UPLOAD;
                    }
                    break;
                case 5:
                    this.type = Type.LOGIN;
            }

        } else {
            System.out.println("错误数据");
        }
    }
    public String toHexString() {
        return utils.DataFormat.bytes2HexString(this.buffer);
    }

    public byte[] getACK() {
        byte[] ack = new byte[]{-2, 33, 1, 3, 0, this.buffer[9], this.buffer[10], 8, 0, -1};
        ack[8] = CRC8.calcCrc8(ack, 1, 7);
        return ack;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public byte[] getIcContent(byte result, byte state, int consume, int money) {
        byte[] content = new byte[6];
        content[0] = result;
        content[1] = state;
        content[2] = (byte) (consume >> 8);
        content[3] = (byte) consume;
        content[4] = (byte) (money >> 8);
        content[5] = (byte) money;
        return content;
    }

    public byte[] sendControlMsg(byte addr, byte func, byte[] content) {
        byte[] buf = null;
        try {
            // FE(1) + 地址位（1） + 功能码（1） + 数据量（1） + 子功能内容（1+2）+ 数据长度（1） + 数据内容（n） + crc(1) + FF(1)
            byte crc = 0x00;
            int len = 7 + FuncData;
            if (content != null)
                len = 7 + FuncData + content.length;
            System.out.println("时间：" + DataFormat.bytes2HexString(content));
            buf = new byte[len];
            buf[0] = (byte) 0xFE;
            buf[1] = addr; //地址
            buf[2] = func;  //功能码
            buf[3] = FuncData;  //子功能码数据长度  默认 3
            byte[] random = buildRandom(RandomRange);
            buf[5] = random[0]; //随机数
            buf[6] = random[1]; //随机数
            buf[7] = (byte) (len - 2);  //数据长度位
            switch (func) {
                case TransFunc.GPSUPDATE: //gps手动更新
                    buf[4] = 0x02; //终端需要回复内容信息
                    break;
                case TransFunc.LOCK: //电磁锁操作
//                    buf[4] = (byte) 0x2D;
//                    System.arraycopy(content, 0, buf, 8, content.length);  //内容数据复制
//                    break;
                case TransFunc.GPSTIME://gps上传时间
                case TransFunc.GIVETIME://平台授时
                case TransFunc.LOGIN:  //平台登录
                case TransFunc.ICACCREDIT:  //ic卡授权
                case TransFunc.SENSORTIME: //传感器间隔信息设置
                    buf[4] = 0x01;  //回复ack即可
                    System.arraycopy(content, 0, buf, 8, content.length);  //内容数据复制
                    break;
            }
            buf[len - 1 - 1] = CRC8.calcCrc8(buf, 1, len - 3, crc);
            buf[len - 1] = (byte) 0xFF;
        } catch (Exception e) {
        }
        return buf;
    }

    public byte[] timeCalendar() {
        SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd,HH:mm:ss+08");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        byte[] timeBuf = time.getBytes();
        int len = timeBuf.length + 3;
        byte[] buf = new byte[len];
        System.arraycopy(timeBuf, 0, buf, 0, timeBuf.length);
        buf[len - 1] = 0x00;  //最后三位补充
        buf[len - 2] = 0x00;
        buf[len - 3] = 0x01;  //保留为格式
        return buf;
    }
    public Type getType() {
        return this.type;
    }




}