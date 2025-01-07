package KitaplıkOrnek;

public class kitap {

	private String kitapAdi;
	private String yazarAdi;
	private int rafNo;
	private int siraNo;
	
	public kitap() {
		
	}
	
	public kitap(String kitapAdi, String yazarAdi) {
		this.kitapAdi = kitapAdi;
		this.yazarAdi = yazarAdi;
	}
	
	public kitap(String kitapAdi, String yazarAdi, int rafNo, int siraNo) {
		this.kitapAdi = kitapAdi;
		this.rafNo = rafNo;
		this.siraNo = siraNo;
		this.yazarAdi = yazarAdi;
	}
	
	
	
	
	
	
	
	public String getKitapAdi() {
		return kitapAdi;
	}
	
	public void setKitapAdi(String kitapAdi) {
		this.kitapAdi = kitapAdi;
	}
	
	public String getYazarAdi() {
		return yazarAdi;
	}

	public void setYazarAdi(String yazarAdi) {
		this.yazarAdi = yazarAdi;
	}

	public int getRafNo() {
		return rafNo;
	}

	public void setRafNo(int rafNo) {
		this.rafNo = rafNo;
	}

	public int getSiraNo() {
		return siraNo;
	}

	public void setSiraNo(int siraNo) {
		this.siraNo = siraNo;
	}

	
}
