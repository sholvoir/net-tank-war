package com.sthiec.nettankwar;

public abstract class Arm extends Flowable {
	
	private boolean robot;
	private int id;
	
	public Arm(TankClient tc, boolean robot, int id) {
		super(tc);
		init(robot, id);
	}

	public Arm(TankClient tc, boolean robot, int id, int x, int y) {
		super(tc, x, y);
		init(robot, id);
	}
	
	public Arm(TankClient tc, boolean robot, int id, int x, int y, Direction xd, Direction yd) {
		super(tc, x, y, xd, yd);
		init(robot, id);
	}
	
	private void init(boolean robot, int id) {
		this.robot = robot;
		this.id = id;
	}

	public boolean isRobot() {
		return robot;
	}
	
	public int getID() {
		return id;
	}
}
