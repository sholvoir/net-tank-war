package com.sthiec.nettankwar;

public class Robot extends Tank {

	private int index;
	
	public Robot(TankClient tc, int id) {
		super(tc, true, id);
	}

	@Override
	public void beHitted() {
		dead();
	}

	@Override
	public void move() {
		int x = MyRandom.nextInt(0, 100);
		if (x < 10) fire();
		if (x < 2) superFire();
		
		super.move();
		
		if (getTC().overBorder(this) || getTC().bumpTanks(this)) {
			moveBack();
			adjustDirection();
		}
		else if (--index == 0) {
			adjustDirection();
		}
	}
	
	private void adjustDirection() {
		setRandomDirection();
		syncGund();
		index = MyRandom.nextInt(3, 20);
	}
}
