package com.example.salesandprices;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ProductContextMenuDialogFragment extends DialogFragment {

	private static final int SHOWCASE_ITEM_INDEX = 0;
	private static final int ADD_TO_CART_ITEM_INDEX = 1;

	public interface OnShowcaseSelectedListener {
		void onShowcaseSelected(long productId);
	}

	public interface OnAddToCartSelectedListener {
		void onAddToCart(long productId);
	}

	private OnShowcaseSelectedListener showcaseSelectedListener;
	private OnAddToCartSelectedListener addToCartListener;

	public void setOnShowcaseSelectedListener(
			OnShowcaseSelectedListener listener) {
		this.showcaseSelectedListener = listener;
	}

	public void setOnAddToCartListener(OnAddToCartSelectedListener listener) {
		this.addToCartListener = listener;
	}

	public static ProductContextMenuDialogFragment newInstance(long productId) {
		ProductContextMenuDialogFragment frag = new ProductContextMenuDialogFragment();
		Bundle args = new Bundle();
		args.putLong("productId", productId);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final long productId = getArguments().getLong("productId");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		return builder.setItems(R.array.product_context_menu_items,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == SHOWCASE_ITEM_INDEX) {
							if (showcaseSelectedListener != null)
								showcaseSelectedListener
										.onShowcaseSelected(productId);
						} else if (which == ADD_TO_CART_ITEM_INDEX) {
							if (addToCartListener != null)
								addToCartListener.onAddToCart(productId);
						}
					}

				}).create();
	}

}
