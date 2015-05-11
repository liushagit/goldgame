/**
 * 
 */
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

/**
 * @author guojiang
 * 
 */
public class HttpWheelRecoderHandler extends HttpHandler {
	private static Logger logger = LoggerFactory
			.getLogger(HttpWheelRecoderHandler.class);

	private static ServerManager serverManager = ServerManager.getInstance();
	private static final String WHEEL_SERVER_TYPE = "other";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.orange.http.HttpHandler#writeResponse(java.util.Map)
	 */
	@Override
	public void writeResponse(Map<String, Object> requestParams) {

		String start = (String) requestParams.get("start");
		String end = (String) requestParams.get("end");
		String id = (String) requestParams.get("id");
		String type = (String) requestParams.get("type");
		String status = (String) requestParams.get("status");
		int channelId = (Integer) requestParams.get("channelId");
		logger.info("wheel data:" + channelId + "--->" + "----->" + start + "|"
				+ end + "|" + id + "|" + type);

		List<Domain> domainList = DomainManager.getInstance()
				.getDomainMap4Server(WHEEL_SERVER_TYPE);
		ProxyServer server = null;
		for (Domain domain : domainList) {
			server = serverManager.getProxyServerByDomainID(
					domain.getDomainID(), Message.WHEEL_PROTOCOL);
			break;
		}

		if (server == null) {
			return;
		}

		try {
			byte[] startDatas = URLEncoder.encode(start, "UTF-8").getBytes("UTF-8");
			byte[] endDatas = URLEncoder.encode(end, "UTF-8").getBytes("UTF-8");
			byte[] idDatas = URLEncoder.encode(id, "UTF-8").getBytes("UTF-8");
			byte[] typeDatas = URLEncoder.encode(type, "UTF-8").getBytes("UTF-8");
			byte[] statusDatas = URLEncoder.encode(status, "UTF-8").getBytes("UTF-8");

			byte[] datas = new byte[startDatas.length + endDatas.length + idDatas.length + typeDatas.length + statusDatas.length + 2 + 2 + 2 + 2 + 2];
			int index = 0;
			index = Util.putShort(datas, index, (short) startDatas.length);
			System.arraycopy(startDatas, 0, datas, index, startDatas.length);
			
			index += startDatas.length;
			index = Util.putShort(datas, index, (short) endDatas.length);
			System.arraycopy(endDatas, 0, datas, index, endDatas.length);
			
			index += endDatas.length;
			index = Util.putShort(datas, index, (short) idDatas.length);
			System.arraycopy(idDatas, 0, datas, index, idDatas.length);
			
			index += idDatas.length;
			index = Util.putShort(datas, index, (short) typeDatas.length);
			System.arraycopy(typeDatas, 0, datas, index, typeDatas.length);
			
			index += typeDatas.length;
			index = Util.putShort(datas, index, (short) statusDatas.length);
			System.arraycopy(statusDatas, 0, datas, index, statusDatas.length);
			
			InputMessage msg = new InputMessage(Message.WHEEL_PROTOCOL, datas);
			msg.setSessionID(String.valueOf(channelId));
			server.getMsgManager().addSocketMessage(msg);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.orange.http.HttpHandler#pare(org.jboss.netty.handler.codec.http.
	 * HttpRequest, java.util.Map, com.orange.core.monitor.HttpMessage,
	 * org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void pare(HttpRequest request, Map<String, Object> requestParams,
			HttpMessage msg, MessageEvent e) throws ErrorDataDecoderException,
			IncompatibleDataDecoderException, NotEnoughDataDecoderException,
			IOException {
		HttpPostRequestDecoder decoder = null;
		String content = msg.getContent().toString();
		if (request.getMethod().equals(HttpMethod.POST)
				&& content.length() == 0) {
			decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(
					false), request);
			requestParams.put("start", getPostParamByName(decoder, "start"));
			requestParams.put("end", getPostParamByName(decoder, "end"));
			requestParams.put("id", getPostParamByName(decoder, "id"));
			requestParams.put("type", getPostParamByName(decoder, "type"));
			requestParams.put("status", getPostParamByName(decoder, "status"));
		} else {
			String[] params = content.split("&");
			String[] tmp = null;
			if (params.length > 0 && params[0].length() > 0) {
				for (String param : params) {
					tmp = param.split("=");
					requestParams.put(tmp[0], tmp[1]);
				}
			}
		}
		requestParams.put("channelId", e.getChannel().getId());
	}

}
