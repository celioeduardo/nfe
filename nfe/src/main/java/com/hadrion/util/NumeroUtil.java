package com.hadrion.util;

import java.util.Random;

public class NumeroUtil {
	private static Random random;
	
	private static Random random(){
		if (random == null)
			random = new Random();
		return random;
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = random().nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
