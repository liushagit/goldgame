package com.orange.http;

import java.io.IOException;
import java.util.Map;

import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.IncompatibleDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;

import com.orange.core.monitor.HttpMessage;
import com.orange.util.HttpClientUtil;

public class HttpDefaultHandler extends HttpHandler {

	@Override
	public void writeResponse(Map<String,Object> requestParams) {
		MessageEvent e = (MessageEvent) requestParams.get("e");
		String msg = (String) requestParams.get("msg");
		
		HttpClientUtil.writeReponse(e, msg);
	}

	@Override
	public void pare(HttpRequest request, Map<String, Object> requestParams,
			HttpMessage msg, MessageEvent e) throws ErrorDataDecoderException,
			IncompatibleDataDecoderException, NotEnoughDataDecoderException,
			IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPostParamByName(HttpPostRequestDecoder decoder, String name)
			throws NotEnoughDataDecoderException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
