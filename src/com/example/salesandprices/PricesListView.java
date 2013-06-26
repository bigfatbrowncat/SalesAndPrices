package com.example.salesandprices;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class PricesListView extends ListView {

	private void showcase(Long productId)
	{
		Intent showcaseIntent = new Intent(getContext(), ProductShowcaseActivity.class);
		showcaseIntent.putExtra("productId", productId);
		getContext().startActivity(showcaseIntent);
	}

	private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(android.widget.AdapterView<?> arg0, android.view.View view, int position, long id)
		{
			Log.w("PricesListView", "Item at " + position + " long clicked");

			final Long productId = (Long) getItemAtPosition(position);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setItems(R.array.product_context_menu_items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0)
					{
						showcase(productId);
					}
				}
				

			});
			AlertDialog menu = builder.create();
			menu.show();
			
			return true;
		}
	};
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(android.widget.AdapterView<?> arg0, android.view.View arg1, int position, long id)
		{
			final Long productId = (Long) getItemAtPosition(position);
			showcase(productId);
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
