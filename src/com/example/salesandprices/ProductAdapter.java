package com.example.salesandprices;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {


	public ProductAdapter(Context context, int resource,
			int textViewResourceId, List<Product> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int resource,
			int textViewResourceId, Product[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public ProductAdapter(Context context, int textViewResourceId,
			List<Product> objects) {
		super(context, textViewResourceId, objects);
	}

	public ProductAdapter(Context context, int textViewResourceId,
			Product[] objects) {
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
		
		nameTextView.setText(getItem(position).getName());
		descriptionTextView.setText(getItem(position).getDescription());
		if (getItem(position).isExclusive())
		{
			crownImageView.setImageResource(R.drawable.crown);
		}
		else
		{
			crownImageView.setImageResource(R.drawable.crown_gray);
		}
		
		if (getItem(position).isNew())
		{
			starImageView.setImageResource(R.drawable.star);
		}
		else
		{
			starImageView.setImageResource(R.drawable.star_gray);
		}
		
		int rub = getItem(position).getPrice() / 100;
		int kop = getItem(position).getPrice() - rub * 100;
		
		priceTextView.setText(rub + "ð. " + kop + "ê.");
		
		return rowView;
	}
}
