package com.cmp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.cmp.ehcache.AbstractDao;
import com.cmp.entity.ShellMessage;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class ShellUtil{
	public static final int DEF_PORT = 7001;
	public static final String DEF_CHARSET = "utf-8";
	public static final int CONN_TIME_OUT = 5000;
	public static final String LOCAL_SCRIPT_DIR = "/data/script";
	private Connection conn;  
    private String ipAddr;  
    private String charset = Charset.defaultCharset().toString();  
    private String userName;  
    private String password;  
   // private static Map<String, Map<Integer, String>> shellMsgMap = new HashMap<String, Map<Integer, String>>();
    private static Map<String, LinkedList<String>> shellMsgMap = new HashMap<String, LinkedList<String>>();
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
        return conn.authenticateWithPassword(userName, password); // 认证  
    }  
  
    
    /*
     * params:
     * cmds :命令
     * 日志queryKey :从0开始的日志记录，用于前台显示
     */
    public String exec(String cmds, String queryKey) {  
        InputStream in = null;  
        String result = "";  
        try {  
            if (this.login()) {  
                Session session = conn.openSession(); // 打开一个会话  
                session.execCommand(cmds);  
                  
                in = session.getStdout();  
                result = this.processStdoutBuffLine(in, this.charset, queryKey);  
                session.close();  
                conn.close();  
            }  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
        return result;  
    }  
    
    
    /*
     * params:
     * cmds :命令
     */
    public String exec(String cmds) {  
        InputStream in = null;  
        String result = "";  
        try {  
            if (this.login()) {  
                Session session = conn.openSession(); // 打开一个会话  
                session.execCommand(cmds);  
                  
                in = session.getStdout();  
                result = this.processStdout(in, this.charset);  
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
    
    
    public String processStdoutBuffLine(InputStream in, String charset, String queryKey) throws IOException {  
    	try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
			while (true) {
			   String line = br.readLine();
				   if (line == null) {
				   break;
			   }
			   LinkedList currentMsgList = shellMsgMap.get(queryKey);
			   
			   if (currentMsgList == null) {
				   LinkedList<String> newMsgList = new LinkedList<String>();
				   newMsgList.add(line);
				   shellMsgMap.put(queryKey, newMsgList);
			   }else {
				   currentMsgList.add(line);
			   }
			}
			return "OK";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "OK"; 
    	
    }
    
    public static void addMsgLog(String queryKey, String msg) {
    	   if (queryKey == null || msg == null) {
    	 	  return;
    	   }
    	   LinkedList currentMsgList = shellMsgMap.get(queryKey);
		   if (currentMsgList == null) {
			   LinkedList<String> newMsgList = new LinkedList<String>();
			   newMsgList.add(msg);
			   shellMsgMap.put(queryKey, newMsgList);
		   }else {
			   currentMsgList.add(msg);
		   }
    }
    
	
	
	public static Map getShellMsgMap() {
		return shellMsgMap;
	}
	
	
	public static void executeFinished(String queryKey) {
		//所有安装完毕设置结束标志
		Map currentMsgMap = (Map) getShellMsgMap().get(queryKey);
		if (currentMsgMap != null) {
			currentMsgMap.put(currentMsgMap.size() + 1,  "cmp:install finished");
		}
	}
	
	/**
     * ping方法，仅仅为了判断是否可连通。
     * 
     * @param host
     * @param port
     * @return
     */
    public static boolean ping(String host, int timeOut) {
        try {
        	return InetAddress.getByName(host).isReachable(timeOut);
        } catch (IOException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {  
  
//    	ShellUtil tool = new ShellUtil("180.169.225.158", 7001, "root",  
//                "r00t0neio", "utf-8");  
//  
//        String result = tool.exec("./test.sh", "231321321"); 
    	//ping
//    	boolean pingRes = ShellUtil.ping("192.168.1.130",  3000);
//        System.out.print(pingRes);  
    	//ftp 命令下载测试
    	ShellUtil tool = new ShellUtil("192.168.0.130", 22, "root",  
      "r00t0neio", "utf-8");  
    	String result = tool.exec("ls"); 
    	System.out.println(result);
    }

 
}
