package com.sthiec.nettankwar;

import java.io.*;
import java.net.*;

public class NetClient implements Runnable {
	private static NetClient nc;
	private static final int BUFLENTH = 1024;
	
	public static NetClient getInstance(TankClient tc) {
		if (nc == null) nc = new NetClient(tc);
		return nc;
	}
	
	private TankClient tc;
	private int id;
	private byte[] buf = new byte[BUFLENTH];
	private DatagramSocket ds;
	private DatagramPacket dp = new DatagramPacket(buf, BUFLENTH);
	
	private NetClient(TankClient tc) {
		this.tc = tc;
	}
	
	public int getID() {
		return id;
	}
	
	public void connect(String ip, int port) {
		try {
			ds = new DatagramSocket();
			new Thread(this).start();
		} catch (SocketException e) {
			System.out.println("UDP listen exception!");
			System.exit(-1);
		}
		
		Socket s = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			s = new Socket(ip, port);
			System.out.println("Connected! Port:" + s.getLocalPort());
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(ds.getLocalPort());
			dos.flush();
			id = dis.readInt();
			System.out.println("GetID:" + id);
		} catch (UnknownHostException e) {
			System.out.println("Can't find TankServer!");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("System IO exception!");
			System.exit(-1);
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				ds.receive(dp);
				tc.deal(buf, dp.getLength());
			} catch (IOException e) {
				System.out.println("Data Exception! discard it!");
			}
		}
		
	}
	
}
