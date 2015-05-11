package com.orange.goldgame.util;

import java.util.List;

import com.juice.orange.game.util.OutputMessage;

public class OutPutMessageUtils{
    
    public static OutputMessage parseBoolean(boolean b){
    	int lenth = 2;
    	OutputMessage outPutMessage = new OutputMessage(lenth);
    	outPutMessage.putBoolean(b);
		return outPutMessage;
    }
    public static OutputMessage parseShort(short s){
    	int lenth = 2;
    	OutputMessage outPutMessage = new OutputMessage(lenth);
    	outPutMessage.putShort(s);
		return outPutMessage;
    }
    public static OutputMessage parseByte(Byte b){
    	int lenth = 1;
    	OutputMessage outPutMessage = new OutputMessage(lenth);
    	outPutMessage.putByte(b);
		return outPutMessage;
    }
    public static OutputMessage parseInt(int i){
    	int lenth = 4;
    	OutputMessage outPutMessage = new OutputMessage(lenth);
    	outPutMessage.putInt(i);
		return outPutMessage;
    }
    public static OutputMessage parseString(String str){
    	int lenth = 2+str.getBytes().length;
    	OutputMessage outPutMessage = new OutputMessage(lenth);
    	outPutMessage.putString(str);
		return outPutMessage;
    }
    public static OutputMessage parseVo(IOutputMeasageVo vo){
        return vo.parseMessage();
    }
    
    public static OutputMessage parse(List<IOutputMeasageVo> list){
        int objSize = list.size();
        if(list == null || objSize<=0){
            return null;
        }
        //单个对象的字节长度
        int sLen = list.get(0).getLength();
        //总共的字节长度
        int totalLen = sLen * objSize;
        totalLen += 2;
        
        OutputMessage totalMessage = new OutputMessage(totalLen);
        
        for(IOutputMeasageVo vo : list){
            OutputMessage mess = new OutputMessage(vo.getLength());
            mess = vo.parseMessage();
            totalMessage.putByte(mess.getBytes());
        }
        return totalMessage;
    }
    
    public static OutputMessage parseList(List<IOutputMeasageVo> list){
        OutputMessage totalMessage = new OutputMessage();
        totalMessage.putShort((short)list.size());
        for(IOutputMeasageVo vo : list){
            OutputMessage mess = vo.parseMessage();
            totalMessage.putByte(mess.getBytes());
        }
        return totalMessage;
    }
    
    
    public static OutputMessage parseStringList(List<String> list){
        int totalLength = 0;
        for(String obj : list) {
       	 	totalLength += obj.getBytes().length;
        }
        OutputMessage totalMessage = new OutputMessage();
        for(String obj : list) {
        	totalMessage.putByte(obj.getBytes());
        }
        return totalMessage;
   }
    
    public static OutputMessage parseObjectList(List<Object> list){
    	 int objSize = list.size();
         if(list == null || objSize<=0){
             return null;
         }
         //单个对象的字节长度
         int totalLength = 0;
         for(Object obj : list) {
        	 totalLength += ((IOutputMeasageVo) obj).getLength();
         }
         OutputMessage totalMessage = new OutputMessage(totalLength);
         for(Object vo : list){
             totalMessage.putByte(((IOutputMeasageVo) vo).parseMessage().getBytes());
         }
    	 return totalMessage;
    }
    
    public static OutputMessage parseOutputMessage(Object...objs){
    	OutputMessage totalOutPutMessage = new OutputMessage();
    	for(Object obj : objs){
    		totalOutPutMessage.putByte(((OutputMessage) obj).getBytes());
        }
		return totalOutPutMessage;
    }
    
    /**
     * 算出对象列表里的总字节长度
     * @param objectList
     * @return
     */
	public static int getObjectListLenth(List<Object> objectList) {
		int length = 0;
		for(Object obj : objectList) {
			length += ((IOutputMeasageVo) obj).getLength();
		}
		return length;
	}
    
    
}
