package com.kentonmurray.bevara;

import com.kentonmurray.bevara.pics.InteractPicsActivity;
import com.kentonmurray.bevara.voice.InteractVoiceActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.kentonmurray.bevara.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    
    /** Takes the user to the part of the app to deal with Pictures */
    public void goToPics(View view)  {
    	Intent intent = new Intent(this, InteractPicsActivity.class);
    	//EditText editText = (EditText) findViewById(R.id.edit_message);
    	//String message = editText.getText().toString();
    	//intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    
    /** Main Menu Actions */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_pictures:
            	intent = new Intent(this, InteractPicsActivity.class);
            	startActivity(intent);
                return true;
            case R.id.menu_send_data:
            	intent = new Intent(this, InteractPicsActivity.class);
            	startActivity(intent);
                return true;
            case R.id.menu_voice_recordings:
            	intent = new Intent(this, InteractVoiceActivity.class);
            	startActivity(intent);
            case R.id.menu_videos:
            	intent = new Intent(this, InteractPicsActivity.class);
            	startActivity(intent);
            case R.id.menu_consent:
            	intent = new Intent(this, GetConsentActivity.class);
            	startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
