package com.sthiec.nettankwar;

public enum Direction {
	Z, N, P;

	private static Direction[] directions = Direction.values();
	
	public static Direction getRandomDirection() {
		return directions[MyRandom.nextInt(0, directions.length)];
	}
}