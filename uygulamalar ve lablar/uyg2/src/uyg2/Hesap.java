package uyg2;

public abstract class Hesap {
	
	protected int hesapNo;
	protected double bakiye;
	
	public Hesap(int hesapNo, double bakiye) {
		this.hesapNo = hesapNo;
		this.bakiye = bakiye;
	}

	public Hesap(int hesapNo) {
		this.hesapNo = hesapNo;
		bakiye = 0.0;
	}
	
	public void paraYatir(double miktar) {
		if(miktar>0) {
			bakiye += miktar;
			System.out.println("Bakiyenize "+ miktar + " tutarında para yatırılmıştır. Yeni bakiyeniz: "+ bakiye);
		}
		else {
			System.out.println("Geçersiz Tutar: "+ miktar);
		}
	}	
	
	public abstract void paraCek(double miktar);
	
	
	public void bilgiGoster() {
		System.out.println("Hesap bilgileri:\nHesap no: " + hesapNo + "\tBakiye: " + bakiye);
	}
	
}
