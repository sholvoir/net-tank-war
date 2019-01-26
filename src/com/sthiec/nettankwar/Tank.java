package com.sthiec.nettankwar;

import java.awt.*;
import java.util.*;

public abstract class Tank extends Arm {

	private static final int XSTEP = 4;
	private static final int YSTEP = 4;
	private static final int WIDTH = 36;
	private static final int HEIGHT = 36;
	
	private static final Map<String, Image> images = new HashMap<String, Image>();
	
	static {
		images.put("NN", tk.getImage(Tank.class.getClassLoader().getResource("images/tankNN.gif")));
		images.put("NZ", tk.getImage(Tank.class.getClassLoader().getResource("images/tankNZ.gif")));
		images.put("NP", tk.getImage(Tank.class.getClassLoader().getResource("images/tankNP.gif")));
		images.put("ZN", tk.getImage(Tank.class.getClassLoader().getResource("images/tankZN.gif")));
		images.put("ZP", tk.getImage(Tank.class.getClassLoader().getResource("images/tankZP.gif")));
		images.put("PN", tk.getImage(Tank.class.getClassLoader().getResource("images/tankPN.gif")));
		images.put("PZ", tk.getImage(Tank.class.getClassLoader().getResource("images/tankPZ.gif")));
		images.put("PP", tk.getImage(Tank.class.getClassLoader().getResource("images/tankPP.gif")));
	}

	private Direction gunxd, gunyd;
	private Image image;

	public Tank(TankClient tc, boolean robot, int id) {
		super(tc, robot, id);
		initGund();
	}

	public Tank(TankClient tc, boolean robot, int id, int x, int y) {
		super(tc, robot, id, x, y);
		initGund();
	}
	
	private void initGund() {
		if (!syncGund()) {
			setRandomGunDirection();
			image = images.get(getGunDirection());
		}
	}
	
	public boolean syncGund() {
		if (getXd() == Direction.Z && getYd() == Direction.Z) return false;
		gunxd = getXd();
		gunyd = getYd();
		image = images.get(getGunDirection());
		return true;
	}

	@Override
	public void move() {
		move(XSTEP, YSTEP);
	}

	@Override
	public void draw(Graphics g) {
		drawImage(g, image);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(getX() - WIDTH / 2, getY() - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public abstract void beHitted();
	
	public String getGunDirection() {
		return gunxd.toString() + gunyd.toString();
	}
	
	public void setRandomGunDirection() {
		do {
			gunxd = Direction.getRandomDirection();
			gunyd = Direction.getRandomDirection();
		} while (gunxd == Direction.Z && gunyd == Direction.Z);
	}

	public void fire() {
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), gunxd, gunyd));
	}
	
	public void superFire() {
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.Z, Direction.N));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.Z, Direction.P));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.N, Direction.Z));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.P, Direction.Z));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.N, Direction.N));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.N, Direction.P));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.P, Direction.N));
		getTC().add(new Missile(getTC(), isRobot(), getX(), getY(), Direction.P, Direction.P));
	}
}
