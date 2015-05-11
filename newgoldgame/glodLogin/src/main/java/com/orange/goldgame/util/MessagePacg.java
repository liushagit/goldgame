package com.orange.goldgame.util;

public class MessagePacg {



    byte[] msgData = null;      //信息内容
    private int inc = 0;                //游标
    public final int MAX_SIZE = 1024;         //最大字节
    short count = 0;
    public short msgLen;               //长度
    public int role_ID;                //玩家ID
    public int check_NUM;              //检验码
    public short type;                 //协议类型
    public short agreeNUM;             //协议个数
    public short agree_ID;             //协议号
    public short agreeLen;             //协议内容长度
    public String agree;               //协议内容1 ··· string类型
    public short command;             //协议内容2 --- int类型
    public int[] data;
     
    /**
     * 封包、解析和协议
     * @param type 类型
     */
    public MessagePacg(short type)
    {
     this.type = type;
     setMsgData(new byte[MAX_SIZE]);
     setInc(0);
    }
    
    /**
     * 封包、解析和协议
     * @param data
     */
    public MessagePacg(byte[] bytes){
     this.msgData = bytes;
     this.setMsgData(msgData);
     setInc(0);
    }
    
    /**
     * 重置
     */
    public void reset()
    {
     setInc(0);
     check_NUM = 0;
     msgLen = 0;
     agreeNUM = 0;
    }
    
    /**
     * 清除
     */
    public void clean()
    {
     setInc(0);
     setMsgData(null);
     msgLen = 0;
    }
    
    /**
     * 用2个字节代表包头
     * */
    public void putMsgHead()
    {
     check_NUM += ComandConfig.msg_head;
     this.msgLen += 2;
         msgData[inc++] = (byte) (( ComandConfig.msg_head >>> 8) & 0xFF);
     msgData[inc++] = (byte) ( ComandConfig.msg_head & 0xFF);
    }
    
    public short getMsgHead()
    {
     inc = 0;
     return (short) (((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
    }
    
    /**
     * 判断包头是否合法
     */
    public boolean checkMSG_HEAD()
    {
     if(getMsgHead() !=  ComandConfig.msg_head) return false;
     return true;
    }
    
    /** 
     * 用2个字节代表包尾
     */   
//    public void putMSG_END()
//    {
//       check_NUM +=  ComandConfig.msg_end;
//       msgData[inc++] = (byte) ((ComandConfig.msg_end >>> 8) & 0xFF);
//       msgData[inc++] = (byte) (ComandConfig.msg_end & 0xFF);
//       this.msgLen = (short) getInc();
//       data = new int[getInc()-2]; 
//       for(int i=8;i<getInc()-2;i++){
//           data[i] = msgData[i];
//           if(msgData[i]<0){
//               data[i] += 256;
//           }
//           if(data[i] != 0) check_NUM += data[i] * i;
//           else  check_NUM += i;
//       }
//       check_NUM += msgLen;
//       msgData[2] = (byte) ((msgLen >>> 8) & 0xFF);
//       msgData[3] = (byte) (msgLen & 0xFF);
//       putCheck_NUM(check_NUM);
//    }
    
    public void putMSG_END()
    {
//       check_NUM +=  ComandConfig.msg_end;
     msgData[inc++] = (byte) ((ComandConfig.msg_end >>> 8) & 0xFF);
     msgData[inc++] = (byte) (ComandConfig.msg_end & 0xFF);
     this.msgLen = (short) getInc();
//       data = new int[getInc()-2]; 
//       for(int i=8;i<getInc()-2;i++){
//           data[i] = msgData[i];
//           if(msgData[i]<0){
//               data[i] += 256;
//           }
//           if(data[i] != 0) check_NUM += data[i] * i;
//           else  check_NUM += i;
//       }
//       check_NUM += msgLen;
     msgData[2] = (byte) ((msgLen >>> 8) & 0xFF);
     msgData[3] = (byte) (msgLen & 0xFF);
//       putCheck_NUM(check_NUM);
    }
    
    
    public short getMSG_END()
    {
     inc = getMSG_LEN()-2;
     return (short) (((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
    }
    
    /** 
     * 判断包尾是否合法
     */
    public boolean checkMSG_END()
    {
     if(getMSG_END() != ComandConfig.msg_end) return false;
     return true;
    }
    
    /** 
     * 用2个字节代表长度
     */   
    public void putMSG_LEN(short msgLen)
    {
     this.msgLen = msgLen;
     msgData[inc++] = (byte) ((msgLen >>> 8) & 0xFF);
     msgData[inc++] = (byte) (msgLen & 0xFF);
    }
    
    public short getMSG_LEN()
    {
     inc = 2;
     return (short) (((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
    }
    
    /** 
     * 判断长度是否正确
     */
    public boolean checkMSG_LEN()
    {
    if(getMSG_LEN() != msgData.length) return false;
        return true;
    }
    
    /**
     * 用4个字节代表检验码
     */   
    public void putCheck_NUM(int check_NUM)
    {
     this.check_NUM = check_NUM;
     msgData[4] = (byte) ((check_NUM >>> 24) & 0xFF);
     msgData[5] = (byte) ((check_NUM >>> 16) & 0xFF);
     msgData[6] = (byte) ((check_NUM >>> 8) & 0xFF);
     msgData[7] = (byte) (check_NUM & 0xFF);
    }
    
    public int getCheck_NUM()
    {
     inc = 4;
     return (short) (((msgData[inc++] & 0xFF) << 24) + ((msgData[inc++] & 0xFF) << 16) 
             + ((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
    }
    
    /** 
     * 判断检验码是否正确
     */
    public boolean ckeckCheck_NUM()
    { 
    if(checkMSG_HEAD()) 
    {
        check_NUM += ComandConfig.msg_head;
    }
    if(checkMSG_END()) 
    {
        check_NUM += ComandConfig.msg_end;
    }
    if(checkMSG_LEN()){
        check_NUM += getMSG_LEN();
    }
    for(int i = 8;i<getMSG_LEN()-2;i++){
        if(msgData[i]<0){
             check_NUM += 256;
         }
         check_NUM += msgData[i];
    }
    if(getCheck_NUM() != check_NUM) return false;
        return true;
    }
    
    public void putBoolean(boolean vol) {
        msgData[inc++] = (byte) (vol ? 1 : 0);// true == 1;false == 0;
    }

    public boolean getBoolean() {
        return msgData[inc++] == 1;// true == 1;false == 0;
    }

    public void putByte(byte vol) {
        msgData[inc++] = vol;
    }

    public byte getByte() {
        return msgData[inc++];
    }

    public void putShort(short vol) {
        msgData[inc++] = (byte) ((vol >>> 8) & 0xFF);
        msgData[inc++] = (byte) (vol & 0xFF);
    }

    public short getShort() {
        return (short) (((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
    }

    public void putInt(int vol) {
        msgData[inc++] = (byte) ((vol >>> 24) & 0xFF);
        msgData[inc++] = (byte) ((vol >>> 16) & 0xFF);
        msgData[inc++] = (byte) ((vol >>> 8) & 0xFF);
        msgData[inc++] = (byte) (vol & 0xFF);
    }

    public int getInt() {
        return ((msgData[inc++] & 0xFF) << 24)
                + ((msgData[inc++] & 0xFF) << 16)
                + ((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF);
    }
    
    /**
     * 用3个字节代表长度
     * @param vol 长度
     */
    public void putLen(int vol) {
        msgData[inc++] = (byte) ((vol >>> 16) & 0xFF);
        msgData[inc++] = (byte) ((vol >>> 8) & 0xFF);
        msgData[inc++] = (byte) (vol & 0xFF);
    }

    /**
     * 用3个字节代表长度
     * @return 长度
     */
    public int getLen() {
        return ((msgData[inc++] & 0xFF) << 16)
                + ((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF);
    }
    
    /**
     * 用2个字节代表协议类型
     * @return 协议类型
     */
    public void putType(short type) {
        this.type = type; 
        setInc(8); 
        msgData[inc++] = (byte) ((type >>> 8) & 0xFF);
        msgData[inc++] = (byte) (type & 0xFF);
    }
    
    /**
     * 用2个字节代表协议类型
     * @return 协议类型
     */
    public void putAgreeID(short type) {
        setInc(4); 
        msgData[inc++] = (byte) ((type >>> 8) & 0xFF);
        msgData[inc++] = (byte) (type & 0xFF);
    }

    public short getType() {
        setInc(8);
        type =(short) (((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
        return type;
    }
    
    /**
     * 用4个字节代表玩家ID
     * @return 玩家ID
     */
    public void putRoleID(int role_ID)
    {
        this.role_ID = role_ID;
        msgData[inc++] = (byte) ((role_ID >>> 24) & 0xFF);
        msgData[inc++] = (byte) ((role_ID >>> 16) & 0xFF);
        msgData[inc++] = (byte) ((role_ID >>> 8) & 0xFF);
        msgData[inc++] = (byte) (role_ID & 0xFF);
    }
    
    public int getRoleID()
    {
        setInc(16);
        role_ID= ((msgData[inc++] & 0xFF) << 24)
                + ((msgData[inc++] & 0xFF) << 16)
                + ((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF);
        return role_ID;
    }
    
    /**
     * 协议ID
     * @param i 协议个数
     * @return 协议ID
     */
    public short getAgreeID()
    {
       inc = 4; 
       agree_ID = (short)(((msgData[inc++] & 0xFF) << 8) + (msgData[inc++] & 0xFF));
         
       return agree_ID;
    }
    

    
    public String getString(int index){
        try {
            agree = new String(msgData,inc, index,"utf-8");
            System.out.println("agree = " + agree);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inc += index;
        return agree;
    }
    
    public byte[] getBytes(int index){
        byte[] buf = new byte[index];
        for(int i=0;i<index;i++){
            buf[i] = msgData[inc];
            inc++;
        }
        return buf;
    }
    
    
    public void putMsg(short agreeID, int com,short size)
    {
        agreeNUM++;
        this.agree_ID = agreeID;
        if(agreeNUM<2){
            putShort(agreeNUM);
            putShort(agree_ID);
            putShort(size);
            putInt(com);
        }else{
            msgData[14] = (byte) ((agreeNUM >>> 8) & 0xFF);
            msgData[15] = (byte) (agreeNUM & 0xFF);
            putShort(agree_ID);
            putShort(size);
            putInt(com);
        }
    }
    
    public int getInc() {
        return inc;
    }

    public void setInc(int inc) {
        this.inc = inc;
    }
    
    public byte[] getMsgData() {
        return msgData;
    }

    public void setMsgData(byte[] msgData) {
        this.msgData = msgData;
    }

    public void putString(String str) throws Exception{
        byte[] b = str.getBytes("GBK");
        for(int i=0;i<b.length;i++){
            msgData[inc++] = b[i];
        }
    }
    
    
    public String getUTF(){
        short len = getShort();
        String str = null;
        try {
             str = new String(msgData,inc, len,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        inc += len;
        return str;
    }
    
    public void putUTF(String str) {
        try {
        byte[] b = str.getBytes("utf-8");
        putShort((short)b.length);
        for(int i=0;i<b.length;i++){
            msgData[inc++] = b[i];
        }
        
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    

}
