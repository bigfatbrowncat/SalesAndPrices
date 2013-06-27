package com.example.salesandprices;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductShowcaseActivity extends Activity {
	
	private long productId;
	
	private TextView productNameTextView;
	private TextView productDescriptionTextView;
	private TextView productExclusiveTextView;
	private TextView productNewTextView;
	
	private ImageView productExclusiveImageView;
	private ImageView productNewImageView;
	
	private EditText quantityEditText;
	private Button addToCartButton;
	
	private OnClickListener addToCartClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			int quantityEntered;
			try
			{
				quantityEntered = Integer.parseInt(quantityEditText.getText().toString());
			}
			catch (NumberFormatException e) {
				quantityEntered = 0;
			}
				
			if (quantityEntered > 0)
			{
				PricesDataManager pricesDataManager = new PricesDataManager(ProductShowcaseActivity.this);
				try
				{
					pricesDataManager.addToCart(productId, quantityEntered);
					finish();
				} catch (PricesDatabaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if (pricesDataManager != null) pricesDataManager.close();
				}
			}
		}
	};
	
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
		
		quantityEditText = (EditText)findViewById(R.id.quantity_edit_text);
		quantityEditText.setText("1");
		
		addToCartButton = (Button)findViewById(R.id.add_to_cart_button);
		addToCartButton.setOnClickListener(addToCartClickListener);
		
		productId = getIntent().getLongExtra("productId", -1);
		if (productId > -1)
		{
			PricesDatabaseHelper pricesDBHelper = new PricesDatabaseHelper(this);
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
		else
		{
			finish();
		}
	}
}
