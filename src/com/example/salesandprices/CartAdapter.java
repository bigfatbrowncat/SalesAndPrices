package com.example.salesandprices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CartAdapter extends ArrayAdapter<Long> {

	public CartAdapter(Context context, Long[] productIds) {
		super(context, R.layout.cart_row_layout, R.id.product_name, productIds);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.cart_row_layout, parent, false);
		
		TextView nameTextView = (TextView)rowView.findViewById(R.id.product_name);
		TextView descriptionTextView = (TextView)rowView.findViewById(R.id.product_description);
		TextView quantityTextView = (TextView)rowView.findViewById(R.id.quantity_text_view);

		ImageView crownImageView = (ImageView)rowView.findViewById(R.id.crown_image_view);
		ImageView starImageView = (ImageView)rowView.findViewById(R.id.this_is_new_image_view);
		
		try
		{
			PricesDataManager pricesDataManager = new PricesDataManager(getContext());
			Product currentProduct = pricesDataManager.getProductById(getItem(position));
			Integer quantity = pricesDataManager.getProductQuantityFromCart(getItem(position));
			
			nameTextView.setText(currentProduct.getName());
			descriptionTextView.setText(currentProduct.getDescription());
			if (currentProduct.isExclusive())
			{
				crownImageView.setImageResource(R.drawable.crown);
			}
			else
			{
				crownImageView.setImageResource(R.drawable.crown_gray);
			}
			
			if (currentProduct.isNew())
			{
				starImageView.setImageResource(R.drawable.star);
			}
			else
			{
				starImageView.setImageResource(R.drawable.star_gray);
			}
			
			quantityTextView.setText(quantity.toString());
		
			pricesDataManager.close();
		}
		catch (PricesDatabaseException e)
		{
			e.printStackTrace();
		}

		return rowView;
	}
}
