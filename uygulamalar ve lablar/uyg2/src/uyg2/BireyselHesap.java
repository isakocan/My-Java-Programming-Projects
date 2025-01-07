package uyg2;

public class BireyselHesap extends Hesap{
	
	private String sifre;
	private double gunlukCekimLimiti;
	private double bugunCekilenMiktar;
	
	public BireyselHesap(int hesapNo, String sifre, double gunlukCekimLimiti) {
		super(hesapNo);
		this.sifre = sifre;
		this.gunlukCekimLimiti = gunlukCekimLimiti;
		bugunCekilenMiktar = 0.0;
	}
	

	public void paraCek(double miktar, String sifre) {
		if(this.sifre.equals(sifre)) {
			if(bakiye >= miktar) {
				if(bugunCekilenMiktar + miktar <= gunlukCekimLimiti) {
					bakiye -= miktar;
					bugunCekilenMiktar += miktar;
					System.out.println("Çekilen tutar: "+miktar+"\tKalan bakiye: "+bakiye
							+"\tBugun toplam çekilen tutar: "+bugunCekilenMiktar);
				}
				else {
					System.out.println("Gunlük para çekim limiti aşıldı!");
				}
				
			}
			else {
				System.out.println("Bakiye yetersiz! İşlem reddedildi. Bakiye: "+bakiye);
			}
		}
		else {
			System.out.println("Hatali şifre girildi! İşlem başarısız.");
		}
	}
	
	
	public void gunlukLimitSifirla() {
		gunlukCekimLimiti = 0.0;
	}
	
	public void bilgiGoster() {
		System.out.println("Hesap bilgileri:\nHesap no: " + hesapNo + "\tBakiye: " + bakiye);
		System.out.println("Şifre: "+sifre+"\tGünlük para çekim limiti: "+ gunlukCekimLimiti
				+"\t Bugün çekilen miktar: "+ bugunCekilenMiktar);
	}


	@Override
	public void paraCek(double miktar) {
		// TODO Auto-generated method stub
		
	}
	
	
}
