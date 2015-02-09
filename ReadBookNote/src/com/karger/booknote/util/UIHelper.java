package com.karger.booknote.util;

import android.content.Context;
import android.content.Intent;

public class UIHelper {
	public static void gotoNewActivity(Context start,Class target){
		Intent intent = new Intent(start,target);
		start.startActivity(intent);
	}
	
}
