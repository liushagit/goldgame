package com.orange.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.IncompatibleDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import org.jboss.netty.util.CharsetUtil;

import com.orange.core.Domain;
import com.orange.core.DomainManager;
import com.orange.core.Message;
import com.orange.core.ServerManager;
import com.orange.core.monitor.HttpMessage;
import com.orange.core.proxy.InputMessage;
import com.orange.goldgame.util.XmlUtil;
import com.orange.log.LoggerFactory;
import com.orange.server.ProxyServer;
import com.orange.util.Util;

/**
 * 微米支付处理
 * @author guojiang
 *
 */
public class HttpWeiMiPayHandler extends HttpHandler{
	private static Logger logger = LoggerFactory
			.getLogger(HttpWeiMiPayHandler.class);
	private static ServerManager serverManager = ServerManager.getInstance();
	private static final String WEIMI_SERVER_TYPE = "other";
	String key = "con";
	@Override
	public void writeResponse(Map<String, Object> requestParams) {
		String content = (String) requestParams.get(key);
		int channelId = (Integer) requestParams.get("channelId");
		logger.info("weimi info:" + content);
		try {
			content = URLDecoder.decode(content, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document document = XmlUtil.strToXml(content);
		Element root = document.getRootElement();
		String notify = root.elementText("ExData");
		if (notify == null) {
			notify = root.elementText("extData");
		}
		if (notify == null) {
			return;
		}
		logger.info("weimi notify:" + notify);
		
		
		List<Domain> domainList = DomainManager.getInstance().getDomainMap4Server(WEIMI_SERVER_TYPE);
		ProxyServer server = null;
		for(Domain domain : domainList){
			server = serverManager.getProxyServerByDomainID(domain.getDomainID(), Message.WEIMI_PROTOCOL);
			break;
		}
		
		if(server == null){
			return;
		}
		
		try {
			byte[] notifyDatas = URLEncoder.encode(notify,"UTF-8").getBytes("UTF-8");
			
			byte[] datas = new byte[notifyDatas.length + 2 + 2];
			int index = 0;
			index = Util.putShort(datas, index, (short)notifyDatas.length);
			System.arraycopy(notifyDatas, 0, datas, index, notifyDatas.length);
			index += notifyDatas.length;
			
			InputMessage msg = new InputMessage(Message.WEIMI_PROTOCOL, datas);
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
		ChannelBuffer buffer = request.getContent();
		content = buffer.toString(CharsetUtil.UTF_8);
		if(request.getMethod().equals(HttpMethod.POST) && content.length() == 0){
			decoder = new HttpPostRequestDecoder(
					new DefaultHttpDataFactory(false), request, CharsetUtil.UTF_8);
			requestParams.put(key, getPostParam(decoder));
		}else {
			requestParams.put(key, content.toString());
		} 
		
		requestParams.put("channelId", e.getChannel().getId());
	}

}
