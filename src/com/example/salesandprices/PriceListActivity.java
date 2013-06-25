package com.example.salesandprices;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.LinearLayout;
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
		
		PricesAllFragment pricesAllFragment = PricesAllFragment.newInstance();
		PricesNewFragment pricesNewFragment = PricesNewFragment.newInstance();
		PricesExclusiveFragment pricesExclusiveFragment = PricesExclusiveFragment.newInstance();
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.tab_all, pricesAllFragment);
		ft.add(R.id.tab_new, pricesNewFragment);
		ft.add(R.id.tab_exclusive, pricesExclusiveFragment);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.price_list, menu);
		return true;
	}

}
