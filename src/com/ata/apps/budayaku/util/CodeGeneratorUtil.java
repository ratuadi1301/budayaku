package com.ata.apps.budayaku.util;

import java.util.Random;

public class CodeGeneratorUtil {

	public static String RandomAlphaNumericString(int size){
//	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//	    String chars = "abcdefhijkmnopqrtuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ2346789";
	    String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ2346789";
	    String ret = "";
	    int length = chars.length();
	    Random rnd = new Random();
	    
	    for (int i = 0; i < size; i ++){
//	        ret += chars.split("")[ (int) (Math.random() * (length - 1)) ];
	        ret += chars.split("")[ (int) (rnd.nextInt(length - 1)) ];
	    }
	    
	    return ret;
	}	
}
