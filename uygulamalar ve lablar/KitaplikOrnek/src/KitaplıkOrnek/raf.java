package KitaplıkOrnek;

public class raf {
	
	kitap kitapSirasi[];
	
	public raf(int rafTopSira) {
		kitapSirasi = new kitap[rafTopSira];
		for(int i=0; i<rafTopSira ; i++) {
			kitapSirasi[i] = new kitap();
		}
	}
	
	public void rafaKitapEkle(int siraSayisi, kitap kitap) {
		kitapSirasi[siraSayisi-1] = kitap;
	}
	
	public void raftanKitapSil(int siraSayisi) {
		kitapSirasi[siraSayisi-1] = new kitap();
	}
	
	
	
}
