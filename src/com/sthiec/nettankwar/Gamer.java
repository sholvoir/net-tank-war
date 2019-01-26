package com.sthiec.nettankwar;

import java.awt.*;
import java.awt.event.*;

public class Gamer extends Tank {
	private static final int FULL_BLOOD = 100;
	private static final int STEP_BLOOD = 25;
	private static final int BLOOD_BAR_POSITION = 30;
	private static final int BLOOD_BAR_WIDTH = 40;
	private static final int BLOOD_BAR_HEIGHT = 10;
	
	private int blood = FULL_BLOOD;

	public Gamer(TankClient tc, int id) {
		super(tc, false, id);
	}

	@Override
	public void beHitted() {
		if ((blood -= STEP_BLOOD) <= 0) dead();
	}
	
	@Override
	public void move() {
		super.move();
		if (getTC().overBorder(this) || getTC().bumpTanks(this)) moveBack();
		if (getTC().eatBloodClot(this)) blood = FULL_BLOOD;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		int x = getX() - BLOOD_BAR_WIDTH / 2;
		int y = getY() - BLOOD_BAR_POSITION - BLOOD_BAR_HEIGHT / 2;
		
		Color c = g.getColor();
		g.setColor(Color.red);
		g.drawRect(x, y, BLOOD_BAR_WIDTH, BLOOD_BAR_HEIGHT);
		g.fillRect(x, y, BLOOD_BAR_WIDTH * blood / FULL_BLOOD, BLOOD_BAR_HEIGHT);
		g.setColor(c);
	}

	public void keyPressed(int keyCode) {
		if (!isLive() && keyCode != KeyEvent.VK_F2) return;

		switch (keyCode) {
		case KeyEvent.VK_F2:
			if (isLive()) return;
			revival();
			blood = FULL_BLOOD;
			getTC().add(this);
			return;
		case KeyEvent.VK_LEFT:
			setXd(Direction.N);
			break;
		case KeyEvent.VK_UP:
			setYd(Direction.N);
			break;
		case KeyEvent.VK_RIGHT:
			setXd(Direction.P);
			break;
		case KeyEvent.VK_DOWN:
			setYd(Direction.P);
			break;
		default:
			return;
		}
		
		syncGund();
	}

	public void keyReleased(int keyCode) {
		if (!isLive()) return;
		switch (keyCode) {
		case KeyEvent.VK_CONTROL:
			fire();
			return;
		case KeyEvent.VK_SPACE:
			superFire();
			return;
		case KeyEvent.VK_LEFT:
			setXd(Direction.Z);
			break;
		case KeyEvent.VK_UP:
			setYd(Direction.Z);
			break;
		case KeyEvent.VK_RIGHT:
			setXd(Direction.Z);
			break;
		case KeyEvent.VK_DOWN:
			setYd(Direction.Z);
			break;
		default:
			return;
		}
		
		syncGund();
	}
}
