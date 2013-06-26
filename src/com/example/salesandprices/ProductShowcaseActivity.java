package com.example.salesandprices;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductShowcaseActivity extends Activity {
	
	TextView productNameTextView;
	TextView productDescriptionTextView;
	TextView productExclusiveTextView;
	TextView productNewTextView;
	
	ImageView productExclusiveImageView;
	ImageView productNewImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_showcase);
		
		productNameTextView = (TextView)findViewById(R.id.product_name_text_view);
		productDescriptionTextView = (TextView)findViewById(R.id.product_description_text_view);
		productExclusiveTextView = (TextView)findViewById(R.id.product_exclusive_text_view);
		productNewTextView = (TextView)findViewById(R.id.product_new_text_view);

		productExclusiveImageView = (ImageView)findViewById(R.id.product_exclusive_image_view);
		productNewImageView = (ImageView)findViewById(R.id.product_new_image_view);
		
		long productId = getIntent().getLongExtra("productId", -1);
		if (productId > -1)
		{
			PricesDBHelper pricesDBHelper = new PricesDBHelper(this);
			try
			{
				Product product = pricesDBHelper.getProductById(productId);
				
				productNameTextView.setText(product.getName());
				productDescriptionTextView.setText(product.getDescription() + " " + product.getDescrCut());
				if (product.isExclusive())
				{
					productExclusiveTextView.setVisibility(View.VISIBLE);
					productExclusiveImageView.setVisibility(View.VISIBLE);
				}
				else
				{
					productExclusiveTextView.setVisibility(View.INVISIBLE);
					productExclusiveImageView.setVisibility(View.INVISIBLE);
				}
				
				if (product.isNew())
				{
					productNewTextView.setVisibility(View.VISIBLE);
					productNewImageView.setVisibility(View.VISIBLE);
				}
				else
				{
					productNewTextView.setVisibility(View.INVISIBLE);
					productNewImageView.setVisibility(View.INVISIBLE);
				}
					
			} catch (PricesDatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if (pricesDBHelper != null) pricesDBHelper.close();
			}
		}
	}
}
