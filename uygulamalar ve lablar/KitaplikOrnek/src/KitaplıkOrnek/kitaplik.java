package KitaplıkOrnek;

public class kitaplik {
	
	raf raflar[];
	int toplamRaf;
	int toplamSira;
	
	public kitaplik(int toplamRaf, int toplamSira) {
		this.toplamRaf = toplamRaf;
		this.toplamSira = toplamSira;
		raflar = new raf[toplamRaf];
		for(int i=0; i<toplamRaf ; i++) {
			raflar[i] = new raf(toplamSira);
		}
	}
	
	
	public void kitapEkle(int rafNo, int siraNo, kitap kitap) {
		kitap.setRafNo(rafNo);
		kitap.setSiraNo(siraNo);
		raflar[rafNo-1].rafaKitapEkle(siraNo, kitap);
	}
	
	public void kitapSil(int rafNo, int siraNo) {
		raflar[rafNo-1].raftanKitapSil(siraNo);
	}
	
	public void kitapAra(String kitapAdi) {
		int i=0;
		int j=0;
		int sonuc = 0; 
		while(i<toplamRaf) {
			j=0;
			while(j<toplamSira) {
				if(!(raflar[i].kitapSirasi[j].equals(null))  && raflar[i].kitapSirasi[j].getKitapAdi().equals(kitapAdi)) {
					System.out.println("Aradiginiz kitap " + (i+1) + ". rafta " + (j+1) + ". sirada bulunuyor.!");
					System.out.println("Kitabin bilgileri\nKitap Adi: "+ raflar[i].kitapSirasi[j].getKitapAdi());
					System.out.println("Yazar Adi: "+ raflar[i].kitapSirasi[j].getYazarAdi());
					System.out.println("Raf No: "+ raflar[i].kitapSirasi[j].getRafNo());
					System.out.println("Sira No: "+ raflar[i].kitapSirasi[j].getSiraNo());
					j = toplamSira;
					i = toplamRaf;
					sonuc = 1;
				}
				j++;
			}
			i++;
		}
		
		if(sonuc == 0) {
			System.out.println("Aradiginiz kitap kitaplikta bulunmamaktadir! ");
		}
	}
	
	
	public void rafGoster(int rafNo) {
		System.out.println(rafNo + ". rafta bulunan kitaplar:");
		System.out.println("   Kitap Adi\t\tYazarAdi");
		for(int i=0; i<toplamSira; i++) {
			if(raflar[rafNo-1].kitapSirasi[i].getKitapAdi() == null) {
				System.out.println(i+1 + ". bos\t\t\tbos");
			}
			else {
				System.out.println(i+1 + ". " + 
						   raflar[rafNo-1].kitapSirasi[i].getKitapAdi() + "\t\t" + 
						   raflar[rafNo-1].kitapSirasi[i].getYazarAdi());
			}
			
		}
	}
	
	
}
