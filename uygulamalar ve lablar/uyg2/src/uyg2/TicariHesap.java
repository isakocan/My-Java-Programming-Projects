package uyg2;

public class TicariHesap extends Hesap{

	private double krediLimiti;
	
	public TicariHesap(int hesapNo, double krediLimiti) {
		super(hesapNo);
		this.krediLimiti = krediLimiti;
	}
	
	public void paraCek(double miktar) {
		if(miktar>0 && bakiye+krediLimiti >= miktar) {
			bakiye -= miktar;
			System.out.println("Çekilen tutar: "+miktar+" Yeni bakiye: "+bakiye);
		}
		else {
			System.out.println("Bakiye yetersiz! İşlem reddedildi. Bakiye: "+bakiye);
		}
	}
	
	
	
}
