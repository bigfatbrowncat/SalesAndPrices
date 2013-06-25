package com.example.salesandprices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PricesNewFragment extends Fragment {

	private PricesDataManager pricesDataManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pricesDataManager = new PricesDataManager(getActivity());
	}
	
	@Override
	public void onDestroy() {
		pricesDataManager.close();
		super.onDestroy();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.fragment_prices_new, container, false);
	
		ListView priceList = (ListView)root.findViewById(R.id.prices_list_view);
		
		try {
			pricesDataManager.updateDatabaseFromDataFile();
			Product[] products = pricesDataManager.getNewProductsListWithShortDescriptions();

			ProductAdapter productAdapter = new ProductAdapter(this.getActivity(), R.layout.price_list_row_layout, R.id.product_name, products);
			
			priceList.setAdapter(productAdapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return root;
	}
	
	public PricesNewFragment() {
		// Should be empty
	}
	
	/**
	 * Constructs the new instance of <code>PricesAllFragment</code>
	 * @return
	 */
	public static PricesNewFragment newInstance() {
		PricesNewFragment res = new PricesNewFragment();
		
		Bundle b = new Bundle();
		res.setArguments(b);
		
		return res;
	}
	
}
