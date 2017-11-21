package com.xd.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weixin4j.pay.JsApiTicket;

import com.alibaba.fastjson.JSONObject;

public class WeixinHttpsUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinHttpsUtil.class);  
	
	/**
	 * 请求类型行post
	 */
    public final static String METHOD_POST = "POST";
	/**
	 * 请求类型get
	 */
	public final static String METHOD_GET = "GET";
	
	
	public final static String JSON_SUCCESS = "{\"errcode\":0,\"mesg\":\"success\"}";
	public final static String JSON_FAIL = "{\"errcode\":-1,\"mesg\":\"fail\"}";
	
	
	
	

	
	

	/**

	     * 发起https请求并获取结果

	     * 

	     * @param requestUrl 请求地址

	     * @param requestMethod 请求方式（GET、POST）

	     * @param params 提交的数据

	     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)

	     */
	
	public final static JSONObject httpRequest(String requestUrl, String requestMethod, String params) {  

        JSONObject jsonObject = new JSONObject();  
        StringBuffer buffer = new StringBuffer();  
        jsonObject.put("errcode", "-1");
        try {  

            HttpsURLConnection httpUrlConn = getHttpsConnection(requestUrl);  

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);  
            if ("GET".equalsIgnoreCase(requestMethod))  
            	
                httpUrlConn.connect();  
            // 当有数据需要提交时
            if (null != params) {  

                OutputStream outputStream = httpUrlConn.getOutputStream();  
                
                // 注意编码格式，防止中文乱码
                outputStream.write(params.getBytes("UTF-8"));  
                outputStream.close();  

            }  

             // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  

            while ((str = bufferedReader.readLine()) != null) {  

                buffer.append(str);  

            }  

            bufferedReader.close();  
            inputStreamReader.close();  

            // 释放资源
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  

            jsonObject = JSONObject.parseObject(buffer.toString());  
            	           

        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
            return jsonObject;
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
            return jsonObject;
        }  
        
        return jsonObject;  
	}  
	
	
	private final static HttpsURLConnection  getHttpsConnection(String requestUrl) 
			throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, IOException{
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化

        TrustManager[] tm = { new MyX509TrustManager() };  
        SSLContext sslContext;		
		sslContext = SSLContext.getInstance("SSL", "SunJSSE");		  		            
		sslContext.init(null, tm, new java.security.SecureRandom());		

        // 从上述SSLContext对象中得到SSLSocketFactory对象

        SSLSocketFactory ssf = sslContext.getSocketFactory();  

        URL url = new URL(requestUrl);  
        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
        httpUrlConn.setSSLSocketFactory(ssf);  
        httpUrlConn.setDoOutput(true);  
        httpUrlConn.setDoInput(true);  
        httpUrlConn.setUseCaches(false);
        
		return httpUrlConn;  
		
	}
	
	/**
	 * 上传文件到微信平台上（素材上传）, 此接口数据上传媒体的base接口，也支持除视频外的永久素材上传
	 * @param uploadMediaUrl   请求url
	 * @param file             要上传的文件
	 * @return
	 */
	public final static JSONObject httpsUpload(String url, File file) {
				   
		        	
		JSONObject result = null;
        try {  
              
            HttpsURLConnection httpUrlConn = getHttpsConn(url);
            
            // 设置边界,这里的boundary是http协议里面的分割符，(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行  
            String BOUNDARY = "----------" + System.currentTimeMillis();  
            httpUrlConn.setRequestProperty("Content-Type",  "multipart/form-data; boundary=" + BOUNDARY);  
            
            // 请求正文信息               
            StringBuilder sb = new StringBuilder();                
            sb.append("--"); // 必须多两道线  
            sb.append(BOUNDARY);  
            sb.append("\r\n");  
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\""+file.length()+"\";filename=\""  
                    + file.getName() + "\"\r\n");  
            sb.append("Content-Type:application/octet-stream\r\n\r\n");  
           
            byte[] head = sb.toString().getBytes("utf-8");  
            
            // 获得输出流  
            OutputStream out = new DataOutputStream(httpUrlConn.getOutputStream());  
            
            // 输出表头  
            out.write(head);  
            
            // 文件正文部分  
            // 把文件已流文件的方式 推入到url中  
            DataInputStream in = new DataInputStream(new FileInputStream(file));  
            
            int bytes = 0;  
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }  
            in.close();  
            
            // 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定  
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线              
            out.write(foot);  
            
            
            out.flush();  
            out.close();  
            
            // 定义BufferedReader输入流来读取URL的响应  
            BufferedReader  reader = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream()));                		
            StringBuffer buffer = new StringBuffer(); 
            String line = null;             		            
            while ((line = reader.readLine()) != null) {  
                buffer.append(line);  
            }  
           		         
            reader.close();
  
            result = JSONObject.parseObject(buffer.toString());  
            
            if (result.containsKey("media_id")) {  
                System.out.println("media_id:"+result.getString("media_id"));  
                log.debug("media_id:{}", result.getString("media_id"));
               
            } else {  
                System.out.println(result.toString());  
                log.debug("result:{}", result.toString());
            }  
            
            
            
        } catch (IOException e) {  
			log.error("上传多媒体文件失败！");
			log.error(e.getMessage());
        } finally {  
  
        }  
		
		return result;
	}
	
	
	public static HttpsURLConnection  getHttpsConn(String url) throws IOException{
		URL url1 = new URL(url); 
		HttpsURLConnection conn = (HttpsURLConnection) url1.openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(30000);  
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        conn.setUseCaches(false);  
        conn.setRequestMethod("POST"); 
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Cache-Control", "no-cache");
        
        return conn;
	}
	
	/**
	 * 永久视频素材上传（永久视频素材比较特殊，所以单独封装一个方法）
	 * @param url              url
	 * @param file             视频文件
	 * @param title            上传视频（永久）时用到  视频素材的标题
	 * @param introduction     上传视频（永久）时用到   视频素材的描述
	 * @return
	 */
	public static JSONObject uploadVideoMaterial(String url, File file,	String title, String introduction)  {
			
		if(!file.exists())
		return null;
		String result = null;
		try{
			URL url1 = new URL(url); 
			HttpsURLConnection conn =  getHttpsConn(url);
	        String boundary = "-----------------------------"+System.currentTimeMillis();
	        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
	         
			OutputStream output = conn.getOutputStream();
			output.write(("--" + boundary + "\r\n").getBytes());
			output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName()).getBytes());  
			output.write("Content-Type: application/octet-stream \r\n\r\n".getBytes());
	        byte[] data = new byte[1024];
	        int len =0;
	        FileInputStream input = new FileInputStream(file);
			while((len=input.read(data))>-1){
			output.write(data, 0, len);
			}
			output.write(("--" + boundary + "\r\n").getBytes());
			output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
			output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}",title,introduction).getBytes());
			output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
			output.flush();
			output.close();
			input.close();
			InputStream resp = conn.getInputStream();
			StringBuffer sb = new StringBuffer();
			while((len= resp.read(data))>-1)
			sb.append(new String(data,0,len,"utf-8"));
			resp.close();
			result = sb.toString();
				System.out.println(result);
		} catch (IOException e) {  
			log.error("上传多媒体文件失败！");
			log.error(e.getMessage());
	    } finally {  
	
	    }  
			
		return JSONObject.parseObject(result);
		}
	
	/**
	 * 下载文件
	 * @param httpUrl 请求url
	 * @param path    文件存放路径   ‘/’结尾
	 * @return
	 */
	public final static JSONObject httpDownload(String httpUrl, String path){  	
		
        // 下载网络文件  
        int bytesum = 0;  
        int byteread = 0;  
  
        URL url = null;  
	    try {  
	        url = new URL(httpUrl);  
	    } catch (MalformedURLException e1) {  
	        // TODO Auto-generated catch block  
	        e1.printStackTrace();  
	        
	    }  
	        	  
       try {  
           URLConnection conn = url.openConnection();  
          
           InputStream inStream = conn.getInputStream();  
           String contentType =  conn.getHeaderField("Content-Type");
           
           //响应类型为 附件
           if(!("text/plain".equals(contentType) || "application/json; encoding=utf-8".equals(contentType))){
        	   
        	   
        	   String disposition = conn.getHeaderField("Content-disposition");
        	   disposition = disposition.replace("attachment; filename=", "");
        	   String fileName = disposition.replace("\"", "");
        	   File file = new File(path);
        	   if(!file.exists()){
        		   file.mkdirs();
        	   }
        	   
        	   
        	   FileOutputStream fs = new FileOutputStream(path + fileName);  	  
	           byte[] buffer = new byte[1204];
	           while ((byteread = inStream.read(buffer)) != -1) {  
	               bytesum += byteread;  
//	               System.out.println(bytesum);  
	               fs.write(buffer, 0, byteread);  
	           }
	           log.info("下载文件大小为{}字节", bytesum);;
	           fs.close();
	           inStream.close();
	           
	           JSONObject result = JSONObject.parseObject(JSON_SUCCESS);
	           result.put("filename", fileName);
	           return result;
	           
           }else{
        	   
        	   //响应类型 为文本（json）
        	   StringBuffer buffer = new StringBuffer();  
        	   InputStreamReader inputStreamReader = new InputStreamReader(inStream, "utf-8");  

	           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  

	           String str = null;  

	           while ((str = bufferedReader.readLine()) != null) {  

	               buffer.append(str);  

	           }  

	           bufferedReader.close();  
	           inputStreamReader.close();  
	           inStream.close();
	           
	           JSONObject result = JSONObject.parseObject(buffer.toString());
	           
	           //如果下载的是video类型（视频），第一返回的是视频的下载路径，而不是视频本身， 所以需要再次请求下载	           
        	   if(result.containsKey("video_url")){
        		   return httpDownload(result.getString("video_url"), path);
        	   }
	        	    
	           
	           
	           return JSONObject.parseObject(buffer.toString());  
        	   
           }
           
          
          
       } catch (FileNotFoundException e) {  
           e.printStackTrace();  
            
       } catch (IOException e) {  
           e.printStackTrace();  
             
       }  
       
       
       return JSONObject.parseObject(JSON_FAIL);
	}  

	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.addRequestProperty("reqMsg", param);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

}
