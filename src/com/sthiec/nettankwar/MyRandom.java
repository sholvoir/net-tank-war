package com.sthiec.nettankwar;

import java.util.*;

public class MyRandom {
	private static Random random = new Random();

	public static int nextInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}
}
