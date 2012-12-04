package com.kentonmurray.bevara.pics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.kentonmurray.bevara.R;
import com.kentonmurray.bevara.R.layout;
import com.kentonmurray.bevara.R.menu;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TakePictureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_picture);
		Location gps_loc;
		String gps_string = "";
		
		CheckEnableGPS();

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		try {
			boolean gps_enabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (gps_enabled) {
				LocationProvider provider = locationManager
						.getProvider(LocationManager.GPS_PROVIDER);
				//Toast.makeText(TakePictureActivity.this,"GPS Enabled: " + provider, Toast.LENGTH_LONG).show();
				gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				gps_string = gps_loc.toString();
				//Display it on the screen
				TextView textView = new TextView(this);
				textView.setTextSize(40);
				textView.setText(gps_string);
				setContentView(textView);
				Thread.sleep(2000);
			}
		} catch (Exception ex) {
		}
		
		/*
		 * boolean gpsEnabled =
		 * locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); if
		 * (!gpsEnabled) { TextView textView = new TextView(this);
		 * textView.setTextSize(40); textView.setText("No GPS!");
		 * setContentView(textView); }
		 */
	}

	private void CheckEnableGPS() {
		String provider = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.equals("")) {
			// Is this actually GPS?
			// GPS Enabled
			Toast.makeText(TakePictureActivity.this,
					"Location Tagging Enabled: " + provider, Toast.LENGTH_LONG)
					.show();
		} else {
			Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivity(intent);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_take_picture, menu);
		return true;
	}

	/*
	 * @Override protected void onStart() { super.onStart();
	 * 
	 * // This verification should be done during onStart() because the system
	 * calls // this method when the user returns to the activity, which ensures
	 * the desired // location provider is enabled each time the activity
	 * resumes from the stopped state. locationManager = (LocationManager)
	 * getSystemService(Context.LOCATION_SERVICE); final boolean gpsEnabled =
	 * locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	 * 
	 * if (!gpsEnabled) { // Build an alert dialog here that requests that the
	 * user enable // the location services, then when the user clicks the "OK"
	 * button, new AlertDialog.Builder(this).setTitle("No GPS!").setMessage(
	 * "So you alone (not tracking) will know where this was collected"
	 * ).setNegativeButton("Cancel", null).setPositiveButton("OK",
	 * null).setNeutralButton("No", null).show(); enableLocationSettings(); } }
	 * 
	 * private void enableLocationSettings() { Intent settingsIntent = new
	 * Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	 * startActivity(settingsIntent); }
	 */

	public void takePic(View view) {
		int actionCode = 3; // Noooo idea what that does
		dispatchTakePictureIntent(actionCode);
	}

	/** Actually take the picture */
	private void dispatchTakePictureIntent(int actionCode) {
		String action_string = MediaStore.ACTION_IMAGE_CAPTURE;
		boolean available = isIntentAvailable(this, action_string);
		if (available == true) {
			Intent takePictureIntent = new Intent(action_string);
			startActivityForResult(takePictureIntent, actionCode);
		} else {
			setContentView(R.layout.activity_take_picture);

			TextView textView = new TextView(this);
			textView.setTextSize(40);
			textView.setText(":( Sorry ... you don't have a camera app");
			setContentView(textView);

		}
	}

	/** Make sure that the device can take pictures */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// Debugging
		// TextView textView = new TextView(this);
		// textView.setTextSize(40);
		// textView.setText("Debugging: Return from Camera");
		// setContentView(textView);

		// Check which request we're responding to
		// Make sure the request was successful
		if (resultCode == RESULT_OK) {
			// Debugging
			// TextView textView = new TextView(this);
			// textView.setTextSize(40);
			// textView.setText("resultCode = OK");
			// setContentView(textView);

			// The user picked a contact.
			// The Intent's data Uri identifies which contact was selected.
			handleSmallCameraPhoto(data);

			// Save the picture
			try {
				File toSave = createImageFile();
				/* takePictureIntent */
				data.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(toSave));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Display for 2 seconds
			//try {
				//Thread.sleep(2000);
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			
			//Annotate it
			annotateCurrentPic();
		}

	}

	/** Get back the picture taken and show */
	private void handleSmallCameraPhoto(Intent intent) {
		Bundle extras = intent.getExtras();
		ImageView mImageView = new ImageView(this);
		Bitmap mImageBitmap = (Bitmap) extras.get("data");
		mImageView.setImageBitmap(mImageBitmap);
		setContentView(mImageView);
	}

	/** Save the file */
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = "BEVARA" + timeStamp + "_";
		File image = File.createTempFile(imageFileName, ".jpg", getAlbumDir());
		// mCurrentPhotoPath = image.getAbsolutePath();
		
		//TextView textView = new TextView(this);
		//textView.setTextSize(40);
		//textView.setText(imageFileName);
		//setContentView(textView);
		
		return image;
	}

	/** Album Directory */
	private File getAlbumDir() {
		File storageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),/*
																							 * getAlbumName
																							 * (
																							 * )
																							 */
				"Bevara");
		
		
		return storageDir;
	}
	
	public void annotateCurrentPic()  {
    	Intent intent = new Intent(this, InsertTextActivity.class);
    	startActivity(intent);
    }
}
