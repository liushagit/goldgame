package com.orange.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.multipart.Attribute;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.IncompatibleDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import com.orange.core.monitor.HttpMessage;

public abstract class HttpHandler {
	
	public abstract void writeResponse(Map<String,Object> requestParams);
	public abstract void pare(HttpRequest request,Map<String,Object> requestParams,HttpMessage msg,MessageEvent e)throws ErrorDataDecoderException, IncompatibleDataDecoderException, NotEnoughDataDecoderException, IOException;
	
	
	public String getPostParamByName(HttpPostRequestDecoder decoder,String name)throws NotEnoughDataDecoderException, IOException{
		InterfaceHttpData data = decoder.getBodyHttpData(name);
		if (data.getHttpDataType() == HttpDataType.Attribute) {
			Attribute attribute = (Attribute) data;
			String value = attribute.getValue();
			if(value == null){
				value = "";
			}
			return value;
		}
		return null;
	}
	
	public String getPostParam(HttpPostRequestDecoder decoder)throws NotEnoughDataDecoderException, IOException{
		List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
		if (datas == null || datas.size() <= 0) {
			return null;
		}
		InterfaceHttpData data = datas.get(0);
		if (data.getHttpDataType() == HttpDataType.Attribute) {
			Attribute attribute = (Attribute) data;
			String value = attribute.getValue();
			if(value == null){
				value = "";
			}
			return value;
		}
		return null;
	}
	
}
