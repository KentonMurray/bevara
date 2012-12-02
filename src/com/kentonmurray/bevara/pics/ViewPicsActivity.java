package com.kentonmurray.bevara.pics;

import java.io.File;

import com.kentonmurray.bevara.R;
import com.kentonmurray.bevara.R.layout;
import com.kentonmurray.bevara.R.menu;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ViewPicsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pics);

		// Get the pics directory
		File directory = getAlbumDir();
		String sDir = directory.toString();
		sDir = sDir + "/"; //For some reason it wasn't adding that
		File fileDir = new File(sDir);

		File file[] = fileDir.listFiles();
		int numFiles = file.length;
		// Log.d("Files", "Size: "+ file.length);
		//if (numFiles != "") {
			//for (int i = 0; i < file.length; i++) {
				//sDir = sDir + " " + file[i].getName();
				// Log.d("Files", "FileName:" + file[i].getName());
			//}
		//}

		TextView textView = new TextView(this);
		textView.setTextSize(10);
		textView.setText(sDir);
		setContentView(textView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_pics, menu);
		return true;
	}

	/** Album Directory */
	private File getAlbumDir() {
		File storageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Bevara");
		return storageDir;
	}
}
