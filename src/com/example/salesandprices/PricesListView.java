package com.example.salesandprices;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import com.example.salesandprices.ProductContextMenuDialogFragment.OnAddToCartSelectedListener;
import com.example.salesandprices.ProductContextMenuDialogFragment.OnShowcaseSelectedListener;

public class PricesListView extends ListView {

	private OnAddToCartSelectedListener addToCartListener;
	private OnShowcaseSelectedListener showcaseSelectedListener;
	
	public void setOnAddToCartSelectedListener(OnAddToCartSelectedListener listener)
	{
		addToCartListener = listener;
	}
	
	public void setOnShowcaseSelectedListener(OnShowcaseSelectedListener listener)
	{
		showcaseSelectedListener = listener;
	}
	
	private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(android.widget.AdapterView<?> arg0, android.view.View view, int position, long id)
		{
			Log.w("PricesListView", "Item at " + position + " long clicked");

			final Long productId = (Long) getItemAtPosition(position);
			
			ProductContextMenuDialogFragment menuDialogFragment = ProductContextMenuDialogFragment.newInstance(productId);
			menuDialogFragment.setOnShowcaseSelectedListener(showcaseSelectedListener);
			menuDialogFragment.setOnAddToCartListener(addToCartListener);
			
			FragmentManager fragmentManager = ((FragmentActivity)PricesListView.this.getContext()).getSupportFragmentManager();
			menuDialogFragment.show(fragmentManager, "priceListContextMenu");
			
			return true;
		}
	};
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(android.widget.AdapterView<?> arg0, android.view.View arg1, int position, long id)
		{
			final Long productId = (Long) getItemAtPosition(position);
			if (productId != null)
			{
				showcaseSelectedListener.onShowcaseSelected(productId);
			}
		}
	};
	
	public PricesListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PricesListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PricesListView(Context context) {
		super(context);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		
		setOnItemLongClickListener(itemLongClickListener);
		setOnItemClickListener(itemClickListener);
	}
	
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}
	
}
