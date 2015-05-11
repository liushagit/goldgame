package com.orange.core.monitor;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.IncompatibleDataDecoderException;
import org.jboss.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import com.orange.core.ServerManager;
import com.orange.http.HttpHandler;
import com.orange.log.LoggerFactory;

/**
 * 通过 http 方式返回目标服务器状态
 * 
 * @author hqch
 * 
 */
public class MonitorServerHandler extends SimpleChannelHandler {

	private static Logger logger = LoggerFactory
			.getLogger(MonitorServerHandler.class);
	private static ServerManager serverManager = ServerManager.getInstance();
	private HttpRequest request;
	private boolean readingChunks;
	
	private Map<Integer,HttpMessage> msgMap = new ConcurrentHashMap<Integer, HttpMessage>();
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Map<String,Object> requestParams = new HashMap<String, Object>();
		String uri = null;
		int channalId = e.getChannel().getId();
		if (!readingChunks) {
			HttpRequest request = this.request = (HttpRequest) e.getMessage();
			uri = request.getUri();
			uri = uri.substring(1);
			if(uri.indexOf("?") != -1){
				uri = uri.substring(0,uri.indexOf("?"));
			}
			if(uri.equals("favicon.ico")){
				return;
			}
			
			logger.debug("-------------------------------------");
			logger.info("uri:" + uri);
			logger.debug("-------------------------------------");

			HttpMessage msg = msgMap.get(channalId);
			if(msg == null){
				msg = new HttpMessage(uri);
				msgMap.put(channalId, msg);
			}
			
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(e);
			}
			
			if(request.getMethod().equals(HttpMethod.GET)){
				// 解析请求参数
				QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
				Map<String, List<String>> params = queryStringDecoder.getParameters();
				if (!params.isEmpty()) {
					for (Entry<String, List<String>> p : params.entrySet()) {
						String key = p.getKey();
						List<String> vals = p.getValue();
						for (String val : vals) {
							requestParams.put(key, val);
						}
					}
				}
			}
			
			msg.setRequestParams(requestParams);
			
			if (request.isChunked()) {
				readingChunks = true;
			}
		} else {// 为分块编码时
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) {
				readingChunks = false;
			} else {
				HttpMessage msg = msgMap.get(channalId);
				msg.getContent().append(chunk.getContent().toString(CharsetUtil.UTF_8));
			}
		}
		
		if(!readingChunks){
			HttpMessage msg = msgMap.get(channalId);
			msgMap.remove(channalId);
			messagelReceivedFinish(e,msg);
		}

	}

	
	private void messagelReceivedFinish(MessageEvent e,HttpMessage msg) throws ErrorDataDecoderException, IncompatibleDataDecoderException, NotEnoughDataDecoderException, IOException{
		String url = msg.getUrl();
		HttpHandler handler = serverManager.getHandlerByName(url);
		Map<String,Object> requestParams = msg.getRequestParams();
		if(handler != null){
			handler.pare(request, requestParams, msg, e);
			serverManager.addChannel(e);
			handler.writeResponse(requestParams);
		} else {
			handler = serverManager.getHandlerByName("default");
			boolean isExec = serverManager.execTask(url);
			requestParams.put("e", e);
			if(isExec){
				requestParams.put("msg", "执行 " + url + " task成功");
			} else {
				requestParams.put("msg", serverManager.realTimeStatus());
			}
			
			handler.writeResponse(requestParams);
		}
	}
	
	private void send100Continue(MessageEvent e) {
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		e.getChannel().write(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		if (e.getCause() instanceof ClosedChannelException) {
			e.getChannel().close();
		} else if (e.getCause() instanceof IOException) {
			e.getChannel().close();
		} else {
			logger.error("MonitorServerHandler",e.getCause());
		}
	}
}
