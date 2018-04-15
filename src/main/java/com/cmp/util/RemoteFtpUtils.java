package com.cmp.util;

import java.io.IOException;
import java.io.InputStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;

public class RemoteFtpUtils {
	
	
	public Connection getConn(String ip, int port, String username, String pwd) throws IOException {
		Connection con = new Connection(ip, port);
		con.connect();
		boolean result = con.authenticateWithPassword(username, pwd);
		if (!result) {
			return null;
		}
		return con;
	}
	
	public Session getSession(Connection con) throws IOException {
		return con.openSession();
	}
	
	/**
	 * 创建远程目录
	 * @param con
	 * @param dirPath
	 * @throws IOException
	 */
	public void mkdir(Connection con, String dirPath) throws IOException {
		SFTPv3Client sftpClient = new SFTPv3Client(con);
		sftpClient.mkdir(dirPath, 6);
		sftpClient.close();
	}
	
	
	/**
	 * 删除远程目录
	 * @param con
	 * @param dirPath
	 * @throws IOException
	 */
	public void rmDir(Connection con, String dirPath) throws IOException {
		SFTPv3Client sftpClient = new SFTPv3Client(con);
		sftpClient.rmdir(dirPath);
	}
	
	
	
	
	public Session connect(Connection con) throws IOException{
		//建立会话
		Session session = null;
		session = con.openSession();
		return session;
	}
	
	/**
	 * 执行远程命令
	 * @param session
	 * @param cmd
	 * @throws IOException
	 */
	public String execCommand(Session session, String cmd, String charset) throws IOException {
		session.execCommand(cmd);
		InputStream is = session.getStdout();
		byte[] buf = new byte[1024];  
        StringBuffer sb = new StringBuffer();  
        try {  
            while (is.read(buf) != -1) {  
                sb.append(new String(buf, charset));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		return sb.toString();
	}
	
	public void closeConn( Connection con) {
		con.close();
	}
	
	public void closeSession(Session session) {
		session.close();
	}
	
	public void close(Connection con, Session session) {
		con.close();
		session.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		RemoteFtpUtils fptUtils = new RemoteFtpUtils();
		Connection con = fptUtils.getConn("192.168.0.130", 22, "root",  
			      "r00t0neio");
		System.out.println(fptUtils.execCommand(fptUtils.getSession(con), "ls", "utf-8"));
		System.exit(0);
	}
}
