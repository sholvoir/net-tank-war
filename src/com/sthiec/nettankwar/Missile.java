package com.sthiec.nettankwar;

import java.awt.*;
import java.util.*;

public class Missile extends Arm {
	
	private static final int XSTEP = 10;
	private static final int YSTEP = 10;
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	private static final Map<String, Image> images = new HashMap<String, Image>();
	
	static {
		images.put("NN", tk.getImage(Missile.class.getClassLoader().getResource("images/missileNN.gif")));
		images.put("NZ", tk.getImage(Missile.class.getClassLoader().getResource("images/missileNZ.gif")));
		images.put("NP", tk.getImage(Missile.class.getClassLoader().getResource("images/missileNP.gif")));
		images.put("ZN", tk.getImage(Missile.class.getClassLoader().getResource("images/missileZN.gif")));
		images.put("ZP", tk.getImage(Missile.class.getClassLoader().getResource("images/missileZP.gif")));
		images.put("PN", tk.getImage(Missile.class.getClassLoader().getResource("images/missilePN.gif")));
		images.put("PZ", tk.getImage(Missile.class.getClassLoader().getResource("images/missilePZ.gif")));
		images.put("PP", tk.getImage(Missile.class.getClassLoader().getResource("images/missilePP.gif")));
	}
	
	private Image image;
	
	public Missile(TankClient tc, boolean robot, int x, int y, Direction xd, Direction yd) {
		super(tc, robot, 0, x, y, xd, yd);
		image = images.get(getDirection());
	}
	
	@Override
	public void move() {
		move(XSTEP, YSTEP);
		if (getTC().overBorder(this)) dead();
	}
	
	@Override
	public void draw(Graphics g) {
		drawImage(g, image);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(getX() - WIDTH / 2, getY() - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
	public boolean hit(java.util.List<Tank> tanks) {
		Rectangle r = getRect();
		for (Iterator<Tank> it = tanks.iterator(); it.hasNext();) {
			Tank tank = it.next();
			if (!tank.isLive()) continue;
			if (tank.isRobot() == isRobot()) continue;
			if (tank.getRect().intersects(r)) {
				tank.beHitted();
				dead();
				getTC().add(new Explode(getTC(), getX(), getY()));
				return true;
			}
		}
		return false;
	}
	
}
