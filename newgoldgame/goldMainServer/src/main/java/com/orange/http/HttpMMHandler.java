package com.orange.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.IncompatibleDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;

import com.orange.core.Domain;
import com.orange.core.DomainManager;
import com.orange.core.Message;
import com.orange.core.ServerManager;
import com.orange.core.monitor.HttpMessage;
import com.orange.core.proxy.InputMessage;
import com.orange.log.LoggerFactory;
import com.orange.server.ProxyServer;
import com.orange.util.Util;

public class HttpMMHandler extends HttpHandler{

	private static Logger logger = LoggerFactory
			.getLogger(HttpAlipayHandler.class);
	private static final String ALIPAY_SERVER_TYPE = "other";
	private static ServerManager serverManager = ServerManager.getInstance();
	@Override
	public void writeResponse(Map<String, Object> requestParams) {
		String mminfo = (String) requestParams.get("mminfo");
		int channelId = (Integer) requestParams.get("channelId");
		logger.info("mminfo clinet messgae:" + mminfo );
		
		List<Domain> domainList = DomainManager.getInstance().getDomainMap4Server(ALIPAY_SERVER_TYPE);
		ProxyServer server = null;
		for(Domain domain : domainList){
			server = serverManager.getProxyServerByDomainID(domain.getDomainID(), Message.MOBELLMM_PROTOCOL);
			break;
		}
		
		if(server == null){
			return;
		}
		
		try {
			byte[] notifyDatas = URLEncoder.encode(mminfo,"UTF-8").getBytes("UTF-8");
			
			byte[] datas = new byte[notifyDatas.length + 2];
			int index = 0;
			index = Util.putShort(datas, index, (short)notifyDatas.length);
			System.arraycopy(notifyDatas, 0, datas, index, notifyDatas.length);
			
			InputMessage msg = new InputMessage(Message.MOBELLMM_PROTOCOL, datas);
			msg.setSessionID(String.valueOf(channelId));
			server.getMsgManager().addSocketMessage(msg);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
	}

	@Override
	public void pare(HttpRequest request, Map<String, Object> requestParams,
			HttpMessage msg, MessageEvent e) throws ErrorDataDecoderException,
			IncompatibleDataDecoderException, NotEnoughDataDecoderException,
			IOException {
		
		HttpPostRequestDecoder decoder = null;
		String content = msg.getContent().toString();
		if(request.getMethod().equals(HttpMethod.POST) && content.length() == 0){
			decoder = new HttpPostRequestDecoder(
					new DefaultHttpDataFactory(false), request);
			
			requestParams.put("mminfo", getPostParamByName(decoder, "mminfo"));
		} else {
			String[] params = content.split("&");
			String[] tmp = null;
			for(String param : params){
				tmp = param.split("=");
				if( tmp == null || tmp.length <= 1){
					continue;
				}
				requestParams.put(tmp[0], tmp[1]);
			}
		}
		requestParams.put("channelId", e.getChannel().getId());		
	}

}
