package com.sthiec.nettankwar;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TankClient extends Frame {

	private static final long serialVersionUID = -8861635531672475352L;

	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	private static final int TITLE_HEIGHT = 30;
	private static final int REFRESH_INTERVAL = 50;

	public static final Rectangle rect = new Rectangle(0, TITLE_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT - TITLE_HEIGHT);

	private boolean isrunning = false;
	private Thread repaintThread = null;
	private NetClient nc;
	private Image backScreen = null;
	private Gamer myTank;
	private BloodClot clot = new BloodClot(this);
	private java.util.List<Missile> missiles = new LinkedList<Missile>();
	private java.util.List<Tank> tanks = new LinkedList<Tank>();
	private java.util.List<Explode> explodes = new LinkedList<Explode>();
	private java.util.List<Wall> walls = new LinkedList<Wall>();

	public static void main(String[] args) {
		new TankClient().launchFrame();
	}
	
	public void launchFrame() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				myTank.keyPressed(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				myTank.keyReleased(e.getKeyCode());
			}
		});

		nc = NetClient.getInstance(this);
		nc.connect("127.0.0.1", TankServer.TCP_PORT);
		
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setResizable(false);
		setTitle("Tank War");
		setVisible(true);

		walls.add(new Wall(this, 80, 300, 20, 300));
		walls.add(new Wall(this, 500, 400, 400, 20));

		myTank = new Gamer(this, nc.getID());
		while (overBorder(myTank)) {
			myTank.setRandomPosition();
		}
		tanks.add(myTank);
		
		explodes.add(new Explode(this, -100, -100));

		isrunning = true;
		backScreen = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		(repaintThread = new Thread(new Runnable() {
			@Override
			public void run() {
				autoRepaint();
			}
		})).start();
	}
	
	public boolean overBorder(GameObj obj) {
		Rectangle r = obj.getRect();
		if (!rect.contains(r)) return true;
		for (Iterator<Wall> it = walls.iterator(); it.hasNext();) {
			if (it.next().getRect().intersects(r)) return true;
		}
		return false;
	}

	public boolean bumpTanks(Tank obj) {
		Rectangle r = obj.getRect();
		for (Iterator<Tank> it = tanks.iterator(); it.hasNext();) {
			Tank tank = it.next();
			if (tank == obj) continue;
			if (tank.getRect().intersects(r)) return true;
		}
		return false;
	}

	public boolean eatBloodClot(Tank tank) {
		if (!clot.isLive()) return false;
		if (!clot.getRect().intersects(tank.getRect())) return false;
		clot.dead();
		return true;
	}

	private void close() {
		setVisible(false);

		isrunning = false;

		if (repaintThread != null && repaintThread.isAlive()) try {
			repaintThread.join();
		} catch (InterruptedException e) {
		}

		System.exit(0);
	}

	private void move() {
		clot.move();
		
		for (Iterator<Explode> it = explodes.iterator(); it.hasNext();) {
			it.next().move();
		}
		
		for (Iterator<Tank> it = tanks.iterator(); it.hasNext();) {
			it.next().move();
		}

		for (Iterator<Missile> itm = missiles.iterator(); itm.hasNext();) {
			Missile m = itm.next();
			m.move();
			if (m.isLive()) m.hit(tanks);
		}
		/*
		if (tanks.size() <= 1) for (int i = 0; i < initTankCount; i++) {
			Tank t = new Robot(this);
			while (overBorder(t) || bumpTanks(t)) t.setRandomPosition();
			tanks.add(t);
		}
		*/
	}

	@Override
	public void paint(Graphics g) {
		move();

		for (Iterator<Wall> it = walls.iterator(); it.hasNext();) {
			it.next().draw(g);
		}
		
		for (Iterator<Missile> it = missiles.iterator(); it.hasNext();) {
			Missile m = it.next();
			if (m.isLive()) m.draw(g);
			else it.remove();
		}

		for (Iterator<Tank> it = tanks.iterator(); it.hasNext();) {
			Tank tank = it.next();
			if (tank.isLive()) tank.draw(g);
			else it.remove();
		}

		for (Iterator<Explode> it = explodes.iterator(); it.hasNext();) {
			Explode e = it.next();
			e.draw(g);
			if (!e.isLive()) it.remove();
		}

		if (clot.isLive()) clot.draw(g);
	}

	@Override
	public void update(Graphics g) {
		Graphics goff = backScreen.getGraphics();
		Color c = goff.getColor();
		goff.setColor(Color.black);
		goff.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		goff.setColor(c);
		paint(goff);
		g.drawImage(backScreen, 0, 0, null);
	}

	private void autoRepaint() {
		try {
			while (isrunning) {
				repaint();
				Thread.sleep(REFRESH_INTERVAL);
			}

		} catch (InterruptedException e) {
		}
	}

	public void add(Missile missile) {
		missiles.add(missile);
	}

	public void add(Tank tank) {
		tanks.add(tank);
	}

	public void add(Explode explode) {
		explodes.add(explode);
	}

	public void deal(byte[] buf, int length) {
		
	}
}
