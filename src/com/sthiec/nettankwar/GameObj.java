package com.sthiec.nettankwar;

import java.awt.*;

public abstract class GameObj {
	
	protected static final Toolkit tk = Toolkit.getDefaultToolkit();
	
	private TankClient tc;
	private int x, y;
	
	public GameObj(TankClient tc) {
		this.tc = tc;
		setRandomPosition();
	}
	
	public GameObj(TankClient tc, int x, int y) {
		this.tc = tc;
		setPosition(x, y);
	}
	
	public TankClient getTC() {
		return tc;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setRandomPosition() {
		Rectangle r = TankClient.rect;
		x = MyRandom.nextInt(r.x, r.x + r.width);
		y = MyRandom.nextInt(r.y, r.y + r.height);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, 1, 1);
	}
	
	public void drawRect(Graphics g, int width, int height, Color color) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillRect(x - width / 2, y - height / 2, width, height);
		g.setColor(c);
	}
	
	public void drawRect(Graphics g, int x, int y, int width, int height, Color color) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillRect(x - width / 2, y - height / 2, width, height);
		g.setColor(c);
	}
	
	public void drawImage(Graphics g, Image image) {
		g.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
	}
	
	public abstract void draw(Graphics g);
	
}
