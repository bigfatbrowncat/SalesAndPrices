package com.example.salesandprices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PricesFragment extends Fragment {

	private Long[] productIds;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
			if (getArguments().containsKey("productIds"))
			{
				long[] pids = getArguments().getLongArray("productIds");
				
				productIds = new Long[pids.length];
				for (int i = 0; i < pids.length; i++)
				{
					productIds[i] = pids[i];
				}
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.fragment_prices_list, container, false);
	
		PricesListView priceList = (PricesListView)root.findViewById(R.id.prices_list_view);
		
		try {
			if (productIds != null)
			{
				ProductAdapter productAdapter = new ProductAdapter(this.getActivity(), R.layout.price_list_row_layout, R.id.product_name, productIds);
				priceList.setAdapter(productAdapter);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return root;
	}
	
	public PricesFragment() {
		// Should be empty
	}
	
	/**
	 * Constructs the new instance of <code>PricesAllFragment</code>
	 * @return
	 */
	public static PricesFragment newInstance(Long[] productIds) {
		PricesFragment res = new PricesFragment();
		
		Bundle b = new Bundle();
		long[] pids = new long[productIds.length];
		for (int i = 0; i < pids.length; i++)
		{
			pids[i] = productIds[i];
		}
		
		b.putLongArray("productIds", pids);
		res.setArguments(b);
		
		return res;
	}
	
	
}
