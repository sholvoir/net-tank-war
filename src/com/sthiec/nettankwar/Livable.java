package com.sthiec.nettankwar;

public abstract class Livable extends GameObj {
	
	private boolean live = true;
	
	public Livable(TankClient tc) {
		super(tc);
	}

	public Livable(TankClient tc, int x, int y) {
		super(tc, x, y);
	}

	public boolean isLive() {
		return live;
	}

	public void dead() {
		live = false;
	}
	
	public void revival() {
		live = true;
	}
	
	public abstract void move();
}
