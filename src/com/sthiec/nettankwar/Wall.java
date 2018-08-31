package com.sthiec.nettankwar;

import java.awt.*;

public class Wall extends GameObj {
	
	private Rectangle rect;
	private int width, height;

	public Wall(TankClient tc, int x, int y, int width, int height) {
		super(tc, x, y);
		this.width = width;
		this.height = height;
		rect = new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	@Override
	public void draw(Graphics g) {
		drawRect(g, width, height, Color.darkGray);
	}

	@Override
	public Rectangle getRect() {
		return rect;
	}
	
}
