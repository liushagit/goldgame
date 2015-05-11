package com.orange.goldgame.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.orange.goldgame.protocol.Protocol;


public class SoketRequestUtils {
    
    /**服务器的ip*/
    private static String host = "127.0.0.1";
    /**端口*/
    private static int gameServerPort  = 10001;
    
    
    public static String sendMessage(){
        Socket socket = new Socket();
        InetSocketAddress gameServeraAdress = new InetSocketAddress(host,gameServerPort); 
        StringBuffer sb = new StringBuffer();
        try {
            socket.connect(gameServeraAdress, 3000);
            MessagePacg msg = new MessagePacg(ComandConfig.role_type);
            msg.putMsgHead();
            msg.putAgreeID(Protocol.REQUEST_PROP_LIST);
            /********************数据包内容begin********************/
            msg.putInt(1);
            /********************数据包内容end********************/
            msg.putMSG_END();
            byte[] send = new byte[msg.getMSG_LEN()];
            System.arraycopy(msg.getMsgData(),0, send, 0, msg.getMSG_LEN());
            socket.getOutputStream().write(send);
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            while(bis.read(buffer)!=-1){
                sb.append(new String(buffer));
            }
            is.close();
            bis.close();
            socket.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
}
