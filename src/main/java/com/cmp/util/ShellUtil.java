package com.cmp.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.ethz.ssh2.Connection;

public class ShellUtil{
	public static final int DEF_PORT = 22;
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
    
    public boolean login() {  
        if (conn != null) {
        	return true;
        }else {
        	try {
				conn = new RemoteFtpUtils().getConn(ipAddr, DEF_PORT, userName, password);
			} catch (IOException e) {
				e.printStackTrace();
			} 
        	if (conn != null) {
        		return true;
        	}else {
        		return false;
        	}
        }
        
    }  
  
    public boolean checkFileExist(String path) {
    	return new RemoteFtpUtils().checkFileExist(conn, path);
    }
    
    /*
     * params:
     * cmds :命令
     * 日志queryKey :从0开始的日志记录，用于前台显示
     */
    public void exec(String cmds, String queryKey) {  
        try {  
            if (this.login()) {  
            	LinkedList<String> outList = (LinkedList)new RemoteFtpUtils().execCommand(conn, cmds, DEF_CHARSET);
                this.processStdoutBuffLine(outList, queryKey);  
            }  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
    }  
    
    
    /*
     * params:
     * cmds :命令
     */
    public List<String> exec(String cmds) {  
         try {  
             if (this.login()) {  
             	LinkedList<String> outList = (LinkedList)new RemoteFtpUtils().execCommand(conn, cmds, DEF_CHARSET);
             	return outList;
             }  
         } catch (IOException e1) {  
             e1.printStackTrace();  
         }  
         return null;
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
    
    
    public void processStdoutBuffLine(LinkedList outList, String queryKey) throws IOException {  
    	try {
		   LinkedList currentMsgList = shellMsgMap.get(queryKey);
		   if (currentMsgList == null) {
			   LinkedList<String> newMsgList = new LinkedList<String>();
			   newMsgList.addAll(outList);
			   shellMsgMap.put(queryKey, newMsgList);
		   }else {
			   currentMsgList.addAll(outList);
		   }
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    	try {
			ShellUtil tool = new ShellUtil("192.168.0.130", 22, "root",  
     "r00t0neio", "utf-8");  
			List result = tool.exec("ls"); 
			System.out.println(result.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

 
}
