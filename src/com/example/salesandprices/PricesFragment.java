package com.example.salesandprices;

import com.example.salesandprices.ProductContextMenuDialogFragment.OnAddToCartSelectedListener;
import com.example.salesandprices.ProductContextMenuDialogFragment.OnShowcaseSelectedListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PricesFragment extends Fragment {

	private Long[] productIds;
	
	private void showcase(Long productId)
	{
		Intent showcaseIntent = new Intent(getActivity(), ProductShowcaseActivity.class);
		showcaseIntent.putExtra("productId", productId);
		startActivity(showcaseIntent);
	}
	
	private void addToCart(Long productId)
	{
		PricesDataManager pricesDataManager = new PricesDataManager(getActivity());
		try {
			
			pricesDataManager.addToCart(productId, 1);
			((PriceListActivity)getActivity()).updateData();
			
		} catch (PricesDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
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

	protected void updateData()
	{
		PricesListView priceList = (PricesListView)getView().findViewById(R.id.prices_list_view);
		
		try {
			if (productIds != null)
			{
				ProductPriceAdapter productAdapter = new ProductPriceAdapter(this.getActivity(), R.layout.price_list_row_layout, R.id.product_name, productIds);
				priceList.setAdapter(productAdapter);
				
				priceList.setOnShowcaseSelectedListener(new OnShowcaseSelectedListener() {
					
					@Override
					public void onShowcaseSelected(long productId) {
						showcase(productId);
					}
				});
				
				priceList.setOnAddToCartSelectedListener(new OnAddToCartSelectedListener() {
					
					@Override
					public void onAddToCart(long productId) {
						addToCart(productId);
						
					}
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.fragment_price_list, container, false);
	
		//updateData();
		
		return root;
	}
	
	@Override
	public void onResume() {
		Log.w(this.getClass().getName(), "onResume");
		super.onResume();
		updateData();
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
