package com.example.salesandprices;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

public class UpdateDataActivity extends Activity {

	private PricesDataManager pricesDataManager;
	
	private class UpdateDatabaseTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				pricesDataManager.updateDatabaseFromDataFile();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} 
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			UpdateDataActivity.this.finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_update_data);
		pricesDataManager = new PricesDataManager(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		new UpdateDatabaseTask().execute();
	}

	@Override
	public void onDestroy() {
		pricesDataManager.close();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// Don't do anything.
	}
}
