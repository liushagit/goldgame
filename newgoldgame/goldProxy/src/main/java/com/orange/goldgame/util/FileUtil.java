package com.orange.goldgame.util;

import java.io.InputStream;
import java.io.PrintStream;

public class FileUtil {

	public static void createNewFile(String path,InputStream in){
		PrintStream out;
		try {
			out = new PrintStream(path);
			byte[] bt = new byte[2048];        //可以根据实际情况调整，建议使用1024，即每次读1KB
			int len = -1;
			while((len=in.read(bt)) != -1)      {
				out.write(bt,0,len);                    
			}
			out.flush();
			in.close();
			out.close();
		} catch (Exception e) {
		}
	}
}
