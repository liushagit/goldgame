package com.orange.goldgame.admin.util;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.orange.goldgame.admin.exception.GoldException;


public class Util {

	public static MessagePacg getPacg(DataInputStream in) throws GoldException{
		MessagePacg msg = null;
		try {
			short head = in.readShort();
			short len = in.readShort();
			byte[] data = new byte[len-4];
			for(int i=0;i<len-4;i++){
				data[i] = in.readByte(); 
			}
			byte msgData[] = new byte[len];
		    msgData[0] = (byte) ((head >>> 8) & 0xFF);
		    msgData[1] = (byte) (head & 0xFF);
		    msgData[2] = (byte) ((len >>> 8) & 0xFF);
		    msgData[3] = (byte) (len & 0xFF);
		    System.arraycopy(data, 0, msgData, 4, len-4);
		    msg = new MessagePacg(msgData);
//		    System.out.println(msgData);
		} catch (Exception e) {
			throw new GoldException();
		}
		return msg;
	}
	
	public static void sendEnd(MessagePacg msg,Socket socket){
		msg.putMSG_END();
		byte[] send = new byte[msg.getMSG_LEN()];
		System.arraycopy(msg.getMsgData(), 0, send, 0, msg.getMSG_LEN());
		byte[] sendser = new byte[msg.getMSG_LEN()];
    	System.arraycopy(msg.getMsgData(),0, sendser, 0, msg.getMSG_LEN());
    	try {
			OutputStream out = socket.getOutputStream();
			out.write(sendser);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
