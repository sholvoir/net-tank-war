package com.sthiec.nettankwar;

import java.io.*;
import java.net.*;
import java.util.*;

public class TankServer implements Runnable {
	
	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	
	private List<Client> clients = new LinkedList<Client>();
	private ServerSocket ss = null;
	private static int id = 100;

	public static void main(String[] args) {
		new TankServer().start();
	}
	
	private void start() {
		try {
			ss = new ServerSocket(TCP_PORT);
			System.out.println("Server is started!");
			accept();
		} catch (IOException e) {
			System.out.println("Server start failed! Please check the port " + TCP_PORT);
		} finally {
			try {
				if ( ss != null) {
					ss.close();
					ss = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void accept() {
		while (true) {
			Socket s = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			try {
				s = ss.accept();
				System.out.println("A client connected! Address- " + s.getInetAddress() + ":" + s.getPort());
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				int udpPort = dis.readInt();
				dos.writeInt(id++);
				dos.flush();
				clients.add(new Client(s.getInetAddress().getHostAddress(), udpPort));
			}
			catch (IOException e) {
				System.out.println("Connenced failed, please report this bug!");
				continue;
			} finally {
				try {
					if (dis != null) {
						dis.close();
						dis = null;
					}
					if (dos != null) {
						dos.close();
						dos = null;
					}
					if (s != null) {
						s.close();
						s = null;
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	private class Client{
		String ip;
		int port;
		
		private Client(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}
	}

}
