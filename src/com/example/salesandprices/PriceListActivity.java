package com.example.salesandprices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class PriceListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_list);
		
		TabHost tabs = (TabHost)findViewById(R.id.price_list_tabs);
		tabs.setup();
		
		tabs.addTab(tabs.newTabSpec("all").setIndicator("All").setContent(R.id.tab_all));
		tabs.addTab(tabs.newTabSpec("new").setIndicator("New").setContent(R.id.tab_new));
		tabs.addTab(tabs.newTabSpec("exclusive").setIndicator("Exclusive").setContent(R.id.tab_exclusive));

		try
		{
			PricesDataManager pricesDataManager = new PricesDataManager(this);
			
			PricesFragment pricesAllFragment = PricesFragment.newInstance(pricesDataManager.getAllProductIds());
			PricesFragment pricesNewFragment = PricesFragment.newInstance(pricesDataManager.getNewProductIds());
			PricesFragment pricesExclusiveFragment = PricesFragment.newInstance(pricesDataManager.getExclusiveProductIds());
			
			pricesDataManager.close();
			
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(R.id.tab_all, pricesAllFragment);
			ft.add(R.id.tab_new, pricesNewFragment);
			ft.add(R.id.tab_exclusive, pricesExclusiveFragment);
			ft.commit();
		}
		catch (PricesDatabaseException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.price_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_update_data)
		{
			Intent updateDataIntent = new Intent(this, UpdateDataActivity.class);
			startActivity(updateDataIntent);

		}
		return super.onOptionsItemSelected(item);
	}

}
