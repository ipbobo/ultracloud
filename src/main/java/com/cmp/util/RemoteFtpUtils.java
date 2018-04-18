package com.cmp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.ssh2.Connection;
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
		Connection con = fptUtils.getConn("192.168.0.130", 22, "root",  
			      "r00t0neio");
		List li = fptUtils.execCommand(con, "ls", "utf-8");
		System.out.println(li.size());
		//System.out.println(fptUtils.checkFileExist(con, "/root/test.sh"));
		System.exit(0);
	}
}
