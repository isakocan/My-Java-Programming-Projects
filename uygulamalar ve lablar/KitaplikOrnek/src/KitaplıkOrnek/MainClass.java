package KitaplıkOrnek;



public class MainClass {

	public static void main(String[] args) {
		
		kitap kitap1 = new kitap("İsa'nin son sirri ", "Dos Santos", 2, 1);
		kitap kitap2 = new kitap("Suc ve Ceza", "Dostoyevski", 1, 2);
		kitap kitap3 = new kitap("Ask ve Gurur", "Jane Austen");
		kitap kitap4 = new kitap("Aydaki İnsanlar", "H.G.Wells", 1,1);
		kitap kitap5 = new kitap("Kürk Mantolu Madonna", "Sabahattin Ali", 2,3);
		
		kitaplik kitaplik = new kitaplik(2, 5);
		
		kitaplik.kitapEkle(kitap1.getRafNo(), kitap1.getSiraNo(), kitap1);
		kitaplik.kitapEkle(kitap2.getRafNo(), kitap2.getSiraNo(), kitap2);
		kitaplik.kitapEkle(1, 4, kitap3);
		kitaplik.kitapEkle(kitap4.getRafNo(), kitap4.getSiraNo(), kitap4);
		kitaplik.kitapEkle(kitap5.getRafNo(), kitap5.getSiraNo(), kitap5);
		kitaplik.rafGoster(1);
		kitaplik.rafGoster(2);
		
		//kitaplik.kitapAra("Kürk Mantolu Madonna");
		
		kitaplik.kitapSil(kitap3.getRafNo(), kitap3.getSiraNo());
		kitaplik.kitapEkle(2, 5, kitap3);
	
		
	
	}

}
