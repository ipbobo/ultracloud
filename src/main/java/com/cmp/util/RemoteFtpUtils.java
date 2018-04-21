package com.cmp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3FileAttributes;
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
	
	/**
	 * 尝试是否可以连接
	 * @param ip
	 * @param port
	 * @param username
	 * @param pwd
	 * @return
	 * @throws IOException
	 */
	public boolean canConn(String ip, int port, int timeout) {
		ConnectionInfo ci = null;
		Connection con = new Connection(ip, port);
		try {
			ci = con.connect(null, timeout, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if (ci == null) {
			return false;
		}else {
			con.close();
			return true;
		}
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
	
	/**
	 * 查询远程服务器上是否存在文件
	 * @param con
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean checkFileExist(Connection con, String path){
		boolean fileStatus = false;
		SFTPv3FileAttributes fileAttr;
		try {
			SFTPv3Client sftpClient = new SFTPv3Client(con);
			fileAttr = sftpClient.lstat(path);
			if (fileAttr != null) {
				fileStatus = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileStatus;
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
	public List<String> execCommand(Connection con, String cmd, String charset) throws IOException {
		LinkedList<String> resList = new LinkedList<String>();
		Session session = connect(con);
		session.execCommand(cmd);
		try {
			InputStream is = session.getStdout();
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
			while (true) {
			   String line = br.readLine();
			   if (line == null) {
			   break;
			   }
			   resList.add(line);
			}
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {
        	closeSession(session);
        	closeConn(con);
        }  
		return resList;
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
		boolean f = fptUtils.canConn("192.168.0.131", 22, 3000);
		System.out.println(f);
		//System.out.println(fptUtils.checkFileExist(con, "/root/test.sh"));
		System.exit(0);
	}
}
