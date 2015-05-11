package com.orange.goldgame.admin.socket;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class SocketSet {

	private static final SocketSet install = new SocketSet();
	private Socket socket;
	Map<String, Socket> socketMap = new HashMap<String, Socket>();
	
	public static SocketSet getInstall() {
		return install;
	}
	private SocketSet(){}
	
	public void init(){
		
	}
	public Socket getSocket(String ip,int port){
	    Socket socket = socketMap.get(ip + port);
	    if(socket  == null){
	        socket = new Socket();
	        SocketAddress address = new InetSocketAddress(ip, port);
	        try {
	            socket.connect(address, 3000);
	            socketMap.put(ip + port, socket);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    }
		if (socket != null && socket.isConnected() && !socket.isClosed()) {
			return socket;
		}
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return socket;
	}
}
