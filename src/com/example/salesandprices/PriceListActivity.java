package com.example.salesandprices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.salesandprices.CartFragment.OnDeliverListener;

public class PriceListActivity extends FragmentActivity {

	TabHost tabs;
	
	OnDeliverListener deliverListener = new OnDeliverListener() {
		
		@Override
		public void onDeliver() {
			PricesDataManager pricesDataManager = new PricesDataManager(PriceListActivity.this);
			try
			{
				pricesDataManager.deliverCart();
				updateData();
			}
			finally
			{
				if (pricesDataManager != null) pricesDataManager.close();
			}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_list);
		
		tabs = (TabHost)findViewById(R.id.price_list_tabs);
		tabs.setup();
		
		tabs.addTab(tabs.newTabSpec("all").setIndicator("All").setContent(R.id.tab_all));
		tabs.addTab(tabs.newTabSpec("new").setIndicator("New").setContent(R.id.tab_new));
		tabs.addTab(tabs.newTabSpec("exclusive").setIndicator("Exclusive").setContent(R.id.tab_exclusive));
		tabs.addTab(tabs.newTabSpec("cart").setIndicator("Cart").setContent(R.id.tab_cart));

		if (savedInstanceState != null)
		{
			if (savedInstanceState.containsKey("currentTab"))
			{
				tabs.setCurrentTab(savedInstanceState.getInt("currentTab"));
			}
		}

	}

	protected void updateData()
	{
		try
		{
			PricesDataManager pricesDataManager = new PricesDataManager(this);
			
			try
			{
				PricesFragment pricesAllFragment = PricesFragment.newInstance(pricesDataManager.getAllProductIds());
				PricesFragment pricesNewFragment = PricesFragment.newInstance(pricesDataManager.getNewProductIds());
				PricesFragment pricesExclusiveFragment = PricesFragment.newInstance(pricesDataManager.getExclusiveProductIds());
 
				CartFragment cartFragment = CartFragment.newInstance(pricesDataManager.getProductIdsInCart());
				cartFragment.setDeliverListener(deliverListener);

				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.tab_all, pricesAllFragment);
				ft.replace(R.id.tab_new, pricesNewFragment);
				ft.replace(R.id.tab_exclusive, pricesExclusiveFragment);
				ft.replace(R.id.tab_cart, cartFragment);
				ft.commit();
			}
			finally
			{
				if (pricesDataManager != null) pricesDataManager.close();
			}
		}
		catch (PricesDatabaseException e)
		{
			e.printStackTrace();
		}		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateData();
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
			return true;
		}
		else if (item.getItemId() == R.id.action_recreate_database)
		{
			PricesDataManager pricesDataManager = new PricesDataManager(this);
			try
			{
				pricesDataManager.recreateDatabase();
				PriceListActivity.this.updateData();
			}
			finally
			{
				pricesDataManager.close();
			}
		}
			
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentTab", tabs.getCurrentTab());
	}

}
