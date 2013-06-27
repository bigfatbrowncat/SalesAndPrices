package com.example.salesandprices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CartFragment extends Fragment {

	public interface OnDeliverListener {
		void onDeliver();
	}
	
	private Long[] productIds;
	
	private Button deliverButton;
	private TextView priceTextView;
	
	private OnDeliverListener deliverListener;
	private OnClickListener deliverButtonOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (deliverListener != null) {
				deliverListener.onDeliver();
			}
			
		}
	};
	
	public void setDeliverListener(OnDeliverListener listener)
	{
		deliverListener = listener;
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
		PricesListView cartList = (PricesListView)getView().findViewById(R.id.cart_list_view);
		
		try {
			if (productIds != null)
			{
				CartAdapter cartAdapter = new CartAdapter(this.getActivity(), productIds);
				cartList.setAdapter(cartAdapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void updateDeliverPane()
	{
		if (productIds.length > 0)
		{
			deliverButton.setEnabled(true);
		}
		else
		{
			deliverButton.setEnabled(false);
		}
		
		// Calculating the full price
		PricesDataManager pricesDataManager = new PricesDataManager(getActivity());
		try
		{
			int price = 0;
			for (int i = 0; i < productIds.length; i++)
			{
				Product currentProduct = pricesDataManager.getProductById(productIds[i]);
				Integer quantity = pricesDataManager.getProductQuantityFromCart(productIds[i]);
				price += currentProduct.getPrice().getValue() * quantity;
			}
			priceTextView.setText(new Price(price).toString());
			
		} catch (PricesDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			pricesDataManager.close();
		}
	}
	
	protected void updateView()
	{
		updateData();
		updateDeliverPane();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.fragment_cart, container, false);
		
		deliverButton = (Button)root.findViewById(R.id.deliver_button);
		deliverButton.setOnClickListener(deliverButtonOnClickListener);
		
		priceTextView = (TextView)root.findViewById(R.id.price_text_view);
		
		return root;
	}
	
	@Override
	public void onResume() {
		Log.w(this.getClass().getName(), "onResume");
		super.onResume();
		updateView();
	}
	
	public CartFragment() {
		// Should be empty
	}
	
	public static CartFragment newInstance(Long[] productIds) {
		CartFragment res = new CartFragment();
		
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
