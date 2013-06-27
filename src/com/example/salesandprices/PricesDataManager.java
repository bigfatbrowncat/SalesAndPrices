package com.example.salesandprices;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;

public class PricesDataManager {
	private static final String dataFileName = "dataset.xml";

	private Context context;
	private PricesDatabaseHelper helper;

	protected Product[] readProductsFromDataFile() throws IOException,
			XmlPullParserException, DataFileException {
		// Opening the data file
		AssetManager assetManager = context.getAssets();
		InputStream datasetInputStream = assetManager.open(dataFileName);

		boolean insideProducts = false;

		// Parsing the file

		try {
			ArrayList<Product> res = new ArrayList<Product>();

			XmlPullParser xmlParser = Xml.newPullParser();
			xmlParser.setInput(datasetInputStream, null);

			int eventType = xmlParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {

					if (!insideProducts) {
						if (xmlParser.getName().equals("products")) {
							insideProducts = true;
						} else {
							throw new DataFileException(
									"Only products root tag allowed");
						}
					} else {
						if (xmlParser.getName().equals("product")) {
							Product newProd = new Product();
							boolean idSet = false;
							boolean nameSet = false;
							boolean priceSet = false;

							for (int i = 0; i < xmlParser.getAttributeCount(); i++) {
								if (xmlParser.getAttributeName(i).equals("id")) {
									newProd.setId(Long.parseLong(xmlParser
											.getAttributeValue(i)));
									idSet = true;
								} else if (xmlParser.getAttributeName(i)
										.equals("name")) {
									newProd.setName(xmlParser
											.getAttributeValue(i));
									nameSet = true;
								} else if (xmlParser.getAttributeName(i)
										.equals("description")) {
									newProd.setDescription(xmlParser
											.getAttributeValue(i));
								} else if (xmlParser.getAttributeName(i)
										.equals("descrcut")) {
									newProd.setDescrCut(xmlParser
											.getAttributeValue(i));
								} else if (xmlParser.getAttributeName(i)
										.equals("price")) {
									newProd.setPrice(new Price(Integer.parseInt(xmlParser
											.getAttributeValue(i))));
									priceSet = true;
								} else if (xmlParser.getAttributeName(i)
										.equals("exclusive")) {
									newProd.setExclusive(Integer
											.parseInt(xmlParser
													.getAttributeValue(i)) > 0);
									priceSet = true;
								} else {
									throw new DataFileException(
											"Invalid product attribute");
								}
							}

							if (!idSet)
								throw new DataFileException(
										"A product should have an id");
							if (!nameSet)
								throw new DataFileException(
										"A product should have a name");
							if (!priceSet)
								throw new DataFileException(
										"A product should have a price");

							res.add(newProd);
						} else {
							throw new DataFileException(
									"Only product tag allowed inside products tag");
						}
					}

				} else if (eventType == XmlPullParser.END_TAG) {

					if (xmlParser.getName().equals("products")) {
						insideProducts = false;
					}

				} else if (eventType == XmlPullParser.TEXT) {
					// Do nothing
				}

				eventType = xmlParser.next();
			}

			return res.toArray(new Product[] {});
		} finally {
			datasetInputStream.close();
		}
	}

	public PricesDataManager(Context context) {
		this.context = context;
		helper = new PricesDatabaseHelper(context);
	}

	public void updateDatabaseFromDataFile() throws IOException,
			XmlPullParserException, DataFileException, PricesDatabaseException {
		Product[] productsFromDataFile = readProductsFromDataFile();

		for (int i = 0; i < productsFromDataFile.length; i++) {
			if (helper.checkProductExists(productsFromDataFile[i].getId())) {
				helper.updateProduct(productsFromDataFile[i]);
			} else {

				productsFromDataFile[i].setCreationDate(new Date());
				helper.insertProduct(productsFromDataFile[i]);
			}
		}
	}

	public void close()
	{
		helper.close();
	}
	
	public Long[] getAllProductIds() throws PricesDatabaseException
	{
		return helper.getAllProductIds();
	}
	public Long[] getExclusiveProductIds() throws PricesDatabaseException
	{
		return helper.getExclusiveProductIds();
	}

	public Long[] getNewProductIds() throws PricesDatabaseException {
		Date olderDate = new Date((new Date()).getTime() - GlobalOptions.NEW_PRODUCT_TIMESPAN); 
		return helper.getProductIdsNewerThan(olderDate);
	}

	public Long[] getProductIdsInCart() throws PricesDatabaseException
	{
		return helper.getProductIdsInCart();
	}
	
	public Product getProductById(long id) throws PricesDatabaseException {
		return helper.getProductById(id);
	}
	
	public Integer getProductQuantityFromCart(long id) throws PricesDatabaseException {
		return helper.getProductQuantityFromCart(id);
	}

	public void deliverCart() {
		helper.deliverCart();
	}
	
	public void recreateDatabase() {
		helper.recreateDatabase();
	}

	public void addToCart(long productId, int quantity) throws PricesDatabaseException {
		helper.addToCart(productId, quantity);
		
	}
}
