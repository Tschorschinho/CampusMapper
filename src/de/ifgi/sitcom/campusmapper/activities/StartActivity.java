package de.ifgi.sitcom.campusmapper.activities;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.ifgi.sitcom.campusmapper.R;
import de.ifgi.sitcom.campusmapper.dialogs.SettingsDialog;
import de.ifgi.sitcom.campusmapper.indoordata.FloorPlan;
import de.ifgi.sitcom.campusmapper.io.RDFReader;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;


/*
 * 
 * Launched when the app starts. Provides the user with a short introduction text, 
 * an introduction video and buttons to either create a new dataset 
 * (calling ChooseLocationActivity) or to select an already existing, 
 * locally stored dataset (calling Activity to be implemented...).
 */
public class StartActivity extends SherlockFragmentActivity {

	private Button mLocalProjectButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		mLocalProjectButton = (Button) findViewById(R.id.button_local_project);
		if (hasLocalFloorPlan()) mLocalProjectButton.setVisibility(View.VISIBLE);
	}
	
	private boolean hasLocalFloorPlan(){
		
		return new RDFReader().localDataAvailable();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		    switch (item.getItemId()) {
		    	
			case R.id.action_settings:			
				new SettingsDialog().show(getSupportFragmentManager(), "");
				return true;


		    default:
		      break;
		    }

		    return true;
	}

	/** Called when the user clicks new project button */
	public void newProject(View v){
		launchBuildingInformationActivity();	
	}
	
	/** Called when the user clicks local project button */
	public void localProject(View v){
		
		startMappingActivity(getLastLocation());
		
		
	}
	

	private FloorPlan getLastLocation(){
		
		FloorPlan floorPlan = new FloorPlan();

	       // Restore preferences
	       SharedPreferences prefs = getSharedPreferences(MappingActivity.PREFS_LAST_LOCATION, 0);
	       floorPlan.setBuildingName(prefs.getString(MappingActivity.PREFS_LAST_LOCATION_BUILDING_NAME, ""));
	       floorPlan.setBuildingURI(prefs.getString(MappingActivity.PREFS_LAST_LOCATION_BUILDING_URI, ""));
	       floorPlan.setFloorNumber(prefs.getInt(MappingActivity.PREFS_LAST_LOCATION_FLOOR_NUMBER, 0));
	       floorPlan.setSourceFloorPlanImageUri(Uri.parse(prefs.getString(MappingActivity.PREFS_LAST_LOCATION_SOURCE_URI, "")));
	       floorPlan.setCroppedFloorPlanImageUri(Uri.parse(prefs.getString(MappingActivity.PREFS_LAST_LOCATION_CROPPED_URI, "")));
	       floorPlan.setFromServer(prefs.getBoolean(MappingActivity.PREFS_LAST_LOCATION_FROM_SERVER, false));

		return floorPlan;
	}

	 private void startMappingActivity(FloorPlan floorPlan){

		 Intent intent = new Intent(this, MappingActivity.class);
			intent.putExtra(ImageSourceActivity.EXTRA_SOURCE_URI, floorPlan.getSourceFloorPlanImageUri().toString());
			intent.putExtra(ImageSourceActivity.EXTRA_CROPPED_URI, floorPlan.getCroppedFloorPlanImageUri().toString());
			intent.putExtra(ChooseLocationActivity.EXTRA_BUILDING_NAME, floorPlan.getBuildingName());
			intent.putExtra(ChooseLocationActivity.EXTRA_BUILDING_URI, floorPlan.getBuildingURI());
			intent.putExtra(ChooseLocationActivity.EXTRA_FLOOR_NUMBER, Integer.toString(floorPlan.getFloorNumber()));
			intent.putExtra(ChooseLocationActivity.EXTRA_LOAD_LOCAL_DATA, true);
			intent.putExtra(ChooseLocationActivity.EXTRA_LOAD_FROM_SERVER, floorPlan.isFromServer());
			
			startActivity(intent);
	 }
	
	public void launchBuildingInformationActivity() {

		Intent intent = new Intent(this, ChooseLocationActivity.class);
		startActivity(intent);
	}
}
