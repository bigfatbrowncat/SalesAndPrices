package com.example.salesandprices;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Long> {


	public ProductAdapter(Context context, int resource,
			int textViewResourceId, List<Long> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int resource,
			int textViewResourceId, Long[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public ProductAdapter(Context context, int textViewResourceId,
			List<Long> objects) {
		super(context, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int textViewResourceId,
			Long[] objects) {
		super(context, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.price_list_row_layout, parent, false);
		
		TextView nameTextView = (TextView)rowView.findViewById(R.id.product_name);
		TextView descriptionTextView = (TextView)rowView.findViewById(R.id.product_description);
		TextView priceTextView = (TextView)rowView.findViewById(R.id.product_price_text_view);

		ImageView crownImageView = (ImageView)rowView.findViewById(R.id.crown_image_view);
		ImageView starImageView = (ImageView)rowView.findViewById(R.id.this_is_new_image_view);
		
		try
		{
			PricesDataManager pricesDataManager = new PricesDataManager(getContext());
			Product currentProduct = pricesDataManager.getProductById(getItem(position));
			
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
			
			int rub = currentProduct.getPrice() / 100;
			int kop = currentProduct.getPrice() - rub * 100;
			
			priceTextView.setText(rub + "ð. " + kop + "ê.");
		
			pricesDataManager.close();
		}
		catch (PricesDatabaseException e)
		{
			e.printStackTrace();
		}

		return rowView;
	}
}
