package com.sthiec.nettankwar;

public abstract class Flowable extends Livable {
	
	private Direction xd, yd;
	private int ox, oy;
	
	public Flowable(TankClient tc) {
		super(tc);
		setRandomDirection();
	}

	public Flowable(TankClient tc, int x, int y) {
		super(tc, x, y);
		setRandomDirection();
	}
	
	public Flowable(TankClient tc, int x, int y, Direction xd, Direction yd) {
		super(tc, x, y);
		setDirection(xd, yd);
	}

	public Direction getXd() {
		return xd;
	}

	public void setXd(Direction xd) {
		this.xd = xd;
	}
	
	public Direction getYd() {
		return yd;
	}
	
	public void setYd(Direction yd) {
		this.yd = yd;
	}
	
	public String getDirection() {
		return xd.toString() + yd.toString();
	}
	
	public void setDirection(Direction xd, Direction yd) {
		this.xd = xd;
		this.yd = yd;
	}
	
	public void setRandomDirection() {
		xd = Direction.getRandomDirection();
		yd = Direction.getRandomDirection();
	}
	
	public void move(int xstep, int ystep) {
		if (xd == Direction.Z && yd == Direction.Z) return;
		
		ox = getX();
		oy = getY();
		
		if (xd == Direction.N) setX(ox - xstep);
		else if (xd == Direction.P) setX(ox + xstep);
		if (yd == Direction.N) setY(oy - ystep);
		else if (yd == Direction.P) setY(oy + ystep);
	}
	
	public void moveBack() {
		setX(ox);
		setY(oy);
	}

}
