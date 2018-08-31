package com.sthiec.nettankwar;

import java.awt.*;

public class BloodClot extends Livable {
	
	private static final int WIDTH = 20;
	private static final int HEIGHT = 10;
	
	private int index = 1;

	public BloodClot(TankClient tc) {
		super(tc);
	}
	
	@Override
	public void move() {
		if (--index <= 0) {
			if (isLive()) {
				dead();
				index = MyRandom.nextInt(300, 500);
			}
			else {
				revival();
				do {
					setRandomPosition();
				} while (getTC().overBorder(this));
				index = MyRandom.nextInt(100, 200);
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		drawRect(g, WIDTH, HEIGHT, Color.magenta);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(getX() - WIDTH / 2, getY() - HEIGHT / 2, WIDTH, HEIGHT);
	}
	
}
