package com.cmp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class ShellUtil {
	private Connection conn;  
    private String ipAddr;  
    private String charset = Charset.defaultCharset().toString();  
    private String userName;  
    private String password;  
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
  
    public String exec(String cmds) {  
        InputStream in = null;  
        String result = "";  
        try {  
            if (this.login()) {  
                Session session = conn.openSession(); // 打开一个会话  
                session.execCommand(cmds);  
                  
                in = session.getStdout();  
                result = this.processStdout2(in, this.charset);  
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
    
    
    public String processStdout2(InputStream in, String charset) throws IOException {  
    	try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
			while (true) { 
			   String line = br.readLine(); 
			   if (line == null) 
			    break; 
			   System.out.println(line);
			}
			return "OK";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "OK"; 
    	
    }
    
    
    
    public static void main(String[] args) {  
  
    	ShellUtil tool = new ShellUtil("118.242.40.216", 7001, "root",  
                "r00t0neio", "utf-8");  
  
        String result = tool.exec("./test.sh");  
        System.out.print(result);  
  
    }  
}
