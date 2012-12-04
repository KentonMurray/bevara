package com.kentonmurray.bevara.pics;

//import com.kentonmurray.bevara.DisplayMessageActivity;
import com.kentonmurray.bevara.R;
import com.kentonmurray.bevara.R.layout;
import com.kentonmurray.bevara.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class InsertTextActivity extends Activity {
	public final static String EXTRA_MESSAGE2 = "com.kentonmurray.bevara.pics.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_insert_text, menu);
		return true;
	}
	
	/** Called when the user clicks the source_lang button */
    public void saveSourceLang(View view) {
        Intent intent = new Intent(this, DisplaySourceLangActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_src);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE2, message);
        startActivity(intent);
    }

}
