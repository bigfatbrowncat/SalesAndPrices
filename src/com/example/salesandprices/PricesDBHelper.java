package com.example.salesandprices;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PricesDBHelper extends SQLiteOpenHelper {

	// Database
	private static final String DATABASE_NAME = "Prices.db";
	private static final int DATABASE_VERSION = 5;

	// Tables
	private static final String TABLE_PRICES = "prices";
	private static final String TABLE_CART = "cart";

	// table columns
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_CREATION_DATETIME = "creation_datetime";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_DESCR_CUT = "descrcut";
	private static final String COLUMN_PRICE = "price";
	private static final String COLUMN_EXCLUSIVE = "exclusive";
	private static final String COLUMN_QUANTITY = "quantity";

	private static final String QUERY_CREATE_TABLE_PRICES = "create table if not exists "
			+ TABLE_PRICES
			+ " "
			+ "("
			+ COLUMN_ID
			+ " integer primary key, "
			+ COLUMN_CREATION_DATETIME
			+ " integer, "
			+ COLUMN_NAME
			+ " text, "
			+ COLUMN_DESCRIPTION
			+ " text, "
			+ COLUMN_DESCR_CUT
			+ " text, "
			+ COLUMN_PRICE + " integer," + COLUMN_EXCLUSIVE + " integer" + ")";

	private static final String QUERY_CREATE_TABLE_CART = "create table if not exists "
			+ TABLE_PRICES
			+ " "
			+ "("
			+ COLUMN_ID
			+ " integer primary key, "
			+ COLUMN_QUANTITY + " integer" + ")";

	private static final String QUERY_UPDATE = "drop table if exists "
			+ TABLE_PRICES + "; " + "drop table if exists " + TABLE_CART;

	protected void createTables(SQLiteDatabase db) {
		db.execSQL(QUERY_CREATE_TABLE_PRICES);
		db.execSQL(QUERY_CREATE_TABLE_CART);
	}

	public PricesDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(QUERY_UPDATE);
		createTables(db);
	}

	public void insertProduct(Product product) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ID, product.getId());
		cv.put(COLUMN_CREATION_DATETIME, product.getCreationDateTime()
				.getTime());
		cv.put(COLUMN_NAME, product.getName());
		cv.put(COLUMN_DESCRIPTION, product.getDescription());
		cv.put(COLUMN_DESCR_CUT, product.getDescrCut());
		cv.put(COLUMN_PRICE, product.getPrice());
		cv.put(COLUMN_EXCLUSIVE, product.isExclusive() ? 1 : 0);

		db.insert(TABLE_PRICES, null, cv);
		db.close();
	}

	public void updateProduct(Product product) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ID, product.getId());
		// cv.put(COLUMN_CREATION_DATETIME, product.getCreationDateTime().getTime()); // We don't upgrade creation time
		cv.put(COLUMN_NAME, product.getName());
		cv.put(COLUMN_DESCRIPTION, product.getDescription());
		cv.put(COLUMN_DESCR_CUT, product.getDescrCut());
		cv.put(COLUMN_PRICE, product.getPrice());
		cv.put(COLUMN_EXCLUSIVE, product.isExclusive());

		db.update(TABLE_PRICES, cv, COLUMN_ID + " = ?",
				new String[] { String.valueOf(product.getId()) });
		db.close();
	}

	public void addToCart(Long productId, int quantity) throws PricesDatabaseException {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.beginTransaction();
			try {
				Cursor cur = db.query(TABLE_CART, new String[] { COLUMN_ID,
						COLUMN_QUANTITY }, COLUMN_ID + " = ?",
						new String[] { String.valueOf(productId) }, null, null,
						null);
				if (cur != null) {
					int quantityAlreadySelected = 0;
					if (cur.getCount() > 0) {
						cur.moveToFirst();
						quantityAlreadySelected = cur.getInt(1);
					}

					ContentValues cv = new ContentValues();
					cv.put(COLUMN_ID, productId);
					cv.put(COLUMN_QUANTITY, quantity + quantityAlreadySelected);

					if (cur.getCount() == 0) {
						db.insert(TABLE_CART, null, cv);
					} else {
						db.update(TABLE_CART, cv, COLUMN_ID + " = ?",
								new String[] { String.valueOf(productId) });
					}

					db.setTransactionSuccessful();
				} else {
					throw new PricesDatabaseException("Can't create a cursor");
				}
			} finally {
				db.endTransaction();
			}
		} finally {
			if (db != null)
				db.close();
		}

	}

	public boolean checkProductExists(long id) throws PricesDatabaseException {
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cur = db.query(TABLE_PRICES, new String[] { COLUMN_ID },
					COLUMN_ID + " = ?", new String[] { String.valueOf(id) },
					null, null, null);

			try {
				if (cur != null) {
					return cur.getCount() > 0;
				} else {
					throw new PricesDatabaseException("Can't create a cursor");
				}
			} finally {
				if (cur != null)
					cur.close();
			}
		} finally {
			db.close();
		}
	}

	public Product getProductById(long id) throws PricesDatabaseException {
		SQLiteDatabase db = getReadableDatabase();
		try {

			Cursor cur = db.query(TABLE_PRICES, new String[] { COLUMN_ID,
					COLUMN_CREATION_DATETIME, COLUMN_NAME, COLUMN_DESCRIPTION,
					COLUMN_DESCR_CUT, COLUMN_PRICE, COLUMN_EXCLUSIVE },
					COLUMN_ID + " = ?", new String[] { String.valueOf(id) },
					null, null, null);
			try {
				if (cur != null) {
					cur.moveToFirst();
					return new Product(cur.getLong(0),
							new Date(cur.getLong(1)), cur.getString(2),
							cur.getString(3), cur.getString(4), cur.getInt(5),
							cur.getInt(6) > 0);
				} else {
					throw new PricesDatabaseException("Can't create a cursor");
				}
			} finally {
				if (cur != null)
					cur.close();
			}

		} finally {
			db.close();
		}
	}

	public Long[] getProductIdsBy(String selection)
			throws PricesDatabaseException {
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cur = db.query(TABLE_PRICES, new String[] { COLUMN_ID },
					selection, null, null, null, null);
			try {
				if (cur != null) {
					Long[] res = new Long[cur.getCount()];

					cur.moveToFirst();

					int i = 0;
					while (!cur.isAfterLast()) {
						res[i] = cur.getLong(0);
						cur.moveToNext();
						i++;
					}
					cur.close();

					return res;
				} else {
					throw new PricesDatabaseException("Can't create a cursor");
				}
			} finally {
				if (cur != null)
					cur.close();
			}
		} finally {
			db.close();
		}
	}

	public Long[] getAllProductIds() throws PricesDatabaseException {
		return getProductIdsBy(null);
	}

	public Long[] getExclusiveProductIds() throws PricesDatabaseException {
		return getProductIdsBy(COLUMN_EXCLUSIVE + " > 0");
	}

	public Long[] getProductIdsNewerThan(Date moment)
			throws PricesDatabaseException {
		return getProductIdsBy(COLUMN_CREATION_DATETIME + " > "
				+ moment.getTime());
	}
}
