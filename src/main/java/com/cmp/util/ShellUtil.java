package com.cmp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.cmp.ehcache.AbstractDao;
import com.cmp.entity.ShellMessage;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class ShellUtil extends  AbstractDao<ShellMessage, Long>{
	private Connection conn;  
    private String ipAddr;  
    private String charset = Charset.defaultCharset().toString();  
    private String userName;  
    private String password;  
    private static Map<String, Map<Integer, String>> shellMsgMap = new HashMap<String, Map<Integer, String>>();
    int port;
    
  
 
    public ShellUtil(String ipAddr, int port, String userName, String password,  
            String charset) {  
        this.ipAddr = ipAddr;  
        this.userName = userName;  
        this.password = password;
        this.port = port;
        if (charset != null) {  
            this.charset = charset;  
        }  
    }  
  
    public boolean login() throws IOException {  
        conn = new Connection(ipAddr, port);  
        conn.connect(); // 连接  
        return conn.authenticateWithPassword(userName, password); // 认证  
    }  
  
    public String exec(String cmds, String logIndex) {  
        InputStream in = null;  
        String result = "";  
        try {  
            if (this.login()) {  
                Session session = conn.openSession(); // 打开一个会话  
                session.execCommand(cmds);  
                  
                in = session.getStdout();  
                result = this.processStdoutBuffLine(in, this.charset, logIndex);  
                session.close();  
                conn.close();  
            }  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
        return result;  
    }  
  
    public String processStdout(InputStream in, String charset) {  
      
        byte[] buf = new byte[1024];  
        StringBuffer sb = new StringBuffer();  
        try {  
            while (in.read(buf) != -1) {  
                sb.append(new String(buf, charset));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return sb.toString();  
    }  
    
    
    public String processStdoutBuffLine(InputStream in, String charset, String logIndex) throws IOException {  
    	try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
			while (true) { 
			   String line = br.readLine();
			   Map currentMsgMap = shellMsgMap.get(logIndex);
			   if (line == null) {
				currentMsgMap.put(currentMsgMap.size() + 1, "-1");			//脚本执行完毕， -1作为结束标志
			    break;
			   }
			   if (currentMsgMap == null) {
				   Map<Integer, String> message = new HashMap<Integer, String>();
				   message.put(1, line);
				   shellMsgMap.put(logIndex, message);
			   }else {
				   currentMsgMap.put(currentMsgMap.size() + 1, line);
			   }
			}
			return "OK";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "OK"; 
    	
    }
    
	@Override
	public String getMybatisQryFunc() {
		// TODO Auto-generated method stub
		return null;
	} 
	
	public static Map getShellMsgMap() {
		return shellMsgMap;
	}
	
    
    public static void main(String[] args) {  
  
    	ShellUtil tool = new ShellUtil("118.242.40.216", 7001, "root",  
                "r00t0neio", "utf-8");  
  
        String result = tool.exec("./test.sh", "231321321");  
        System.out.print(result);  
  
    }

 
}
