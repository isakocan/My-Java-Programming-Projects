package uyg2;

public class Main {

	public static void main(String[] args) {
		
		BireyselHesap b1 = new BireyselHesap(1001, "1234", 1000);
		b1.paraYatir(2000);
		b1.paraCek(1000, "1234");
		
		TicariHesap t1 = new TicariHesap(2001, 5000);
		t1.paraYatir(10000);
		t1.paraCek(12000);
		t1.paraCek(5000);
		
		
	}

}
