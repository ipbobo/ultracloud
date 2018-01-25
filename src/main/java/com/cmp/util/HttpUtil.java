package com.cmp.util;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	private static CloseableHttpClient httpClient = HttpClients.createDefault();//创建默认的httpClient实例
	private static final String CONTENTTYPE = "application/json";
	private static final String CHARSETNAME = "UTF-8";
	private static final String PARAM_CONTENTTYPE = "text/json";
	
    //发送post请求(JSON)
    public static String sendHttpPost(String url, String jsonStr, boolean isZip){
    	logger.debug("请求地址："+url+"、请求参数: "+jsonStr);
        CloseableHttpResponse httpResponse=null;
        HttpEntity httpEntity=null;
        try{
        	HttpPost httpPost=new HttpPost(url);//创建http请求(post方式)：httpPost.getRequestLine() & httpPost.getURI()
        	httpPost.addHeader(HTTP.CONTENT_TYPE, CONTENTTYPE);
        	httpPost.setConfig(RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(5000).build());//设置连接超时和传输超时时间
        	
        	//请求参数
        	StringEntity se = new StringEntity(jsonStr, CHARSETNAME);//URLEncoder.encode(jsonStr, CHARSETNAME)
            se.setContentType(PARAM_CONTENTTYPE);//设置类型
            httpPost.setEntity(se);//设置表单实体
            
            //执行请求
            httpResponse=httpClient.execute(httpPost);
            StatusLine sl=httpResponse.getStatusLine();
    		if(sl==null || sl.getStatusCode()!=200){
    			logger.error("Http request error: "+sl);
    			return null;
    		}
            
            //获取数据
            httpEntity=httpResponse.getEntity();
            if(httpEntity==null){
            	return null;
            }
            
            String retJsonStr=isZip?getZipStr(httpEntity.getContent(), CHARSETNAME):EntityUtils.toString(httpEntity, "UTF-8");//响应内容
            logger.debug("返回JSON字符串: "+retJsonStr);
            return retJsonStr;
        }catch(Exception e){
        	logger.error("Http request error: "+e.getMessage());
            return null;
        }finally{
            try{
                if(httpEntity!=null){
                	EntityUtils.consume(httpEntity);
                	httpEntity=null;
	            }
                
                if(httpResponse!=null){
                	httpResponse.close();
                	httpResponse=null;
                }
            }catch(Exception e){
            }
        }
    }
    
    //发送get请求(JSON)：isEncode,是否加密
    public static String sendHttpGet(String url, String paramStr, boolean isEncode){
    	logger.debug("请求地址："+url+"、请求参数: "+paramStr);
        CloseableHttpResponse httpResponse=null;
        HttpEntity httpEntity=null;
        try{
        	HttpGet httpGet=new HttpGet(url+(isEncode?URLEncoder.encode(paramStr, CHARSETNAME):(paramStr==null?"":paramStr)));//创建http请求(get方式)
        	httpGet.addHeader(HTTP.CONTENT_TYPE, CONTENTTYPE);
        	httpGet.setConfig(RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(5000).build());//设置连接超时和传输超时时间
        	
            //执行请求
            httpResponse=httpClient.execute(httpGet);
            StatusLine sl=httpResponse.getStatusLine();
    		if(sl==null || sl.getStatusCode()!=200){
    			logger.error("Http request error: "+sl);
    			return null;
    		}
            
            //获取数据
            httpEntity=httpResponse.getEntity();
            if(httpEntity==null){
            	return null;
            }
            
            String retJsonStr=EntityUtils.toString(httpEntity, "UTF-8");//响应内容
            logger.debug("返回JSON字符串: "+retJsonStr);
            return retJsonStr;
        }catch(Exception e){
        	logger.error("Http request error: "+e);
            return null;
        }finally{
            try{
                if(httpEntity!=null){
                	EntityUtils.consume(httpEntity);
                	httpEntity=null;
	            }
                
                if(httpResponse!=null){
                	httpResponse.close();
                	httpResponse=null;
                }
            }catch(Exception e){
            }
        }
    }
    
	private static String getZipStr(InputStream is, String charset) throws IOException {
		InputStream gzis = new GZIPInputStream(is);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i=gzis.read())!=-1) {
			baos.write(i);
		}
		
		return baos.toString(charset);
	}
}