package com.example.salesandprices;

public class Price {
	private int value;

	public Price(int value) {
		this.value = value;
	}
	
	public Price(int rub, int kop)
	{
		value = rub * 100 + kop;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		int rub = value / 100;
		int kop = value - rub * 100;

		return rub + "ð. " + kop + "ê.";
	}
}
