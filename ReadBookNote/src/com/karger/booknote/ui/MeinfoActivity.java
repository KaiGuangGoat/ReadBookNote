package com.karger.booknote.ui;

import android.os.Bundle;
import android.view.Window;
import android.widget.SearchView;

public class MeinfoActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(app.getResLayout("me_info_layout"));
		
	}

}
