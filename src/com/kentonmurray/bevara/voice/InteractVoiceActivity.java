package com.kentonmurray.bevara.voice;

import com.kentonmurray.bevara.R;
import com.kentonmurray.bevara.R.layout;
import com.kentonmurray.bevara.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class InteractVoiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interact_voice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_interact_voice, menu);
		return true;
	}

	public void goToRecordVoice(View view)  {
    	//Intent intent = new Intent(this, RecordVoiceActivity.class);// InteractPicsActivity.class);
    	//startActivity(intent);
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText("Implement Voice Recordings");
		setContentView(textView);
    }
	
}
