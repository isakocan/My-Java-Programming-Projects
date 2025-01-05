package clinicReservationSystem;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateSerFile {

    public static void main(String[] args) {
        CRS crs = new CRS();

        // 1. Hasta, Hastane, Bölüm ve Doktor Oluşturma

        // Hastalar
        Patient patient1 = new Patient("Ahmet Yılmaz", 12345678901L);
        Patient patient2 = new Patient("Elif Kaya", 98765432109L);
        Patient patient3 = new Patient("Can Demir", 45678901234L);
        Patient patient4 = new Patient("Zeynep Güneş", 67890123456L);
        crs.addPatient(patient1);
        crs.addPatient(patient2);
        crs.addPatient(patient3);
        crs.addPatient(patient4);


        // Hastaneler
        Hospital hospital1 = new Hospital(1, "Merkez Hastanesi");
        Hospital hospital2 = new Hospital(2, "Şehir Hastanesi");
        crs.addHospital(hospital1);
        crs.addHospital(hospital2);



        // Bölümler
        Section section1 = new Section(101, "Kardiyoloji", 2);
        Section section2 = new Section(201, "Dahiliye", 3);
        Section section3 = new Section(301, "Göz Hastalıkları", 1);
        Section section4 = new Section(401, "Ortopedi", 2);
        hospital1.addSection(section1);
        hospital1.addSection(section3);
        hospital2.addSection(section2);
        hospital2.addSection(section4);

        // Doktorlar
        Doctor doctor1 = new Doctor("Ayşe Kaya", 11223344556L, 777);
        Doctor doctor2 = new Doctor("Mehmet Demir", 99887766554L, 888);
        Doctor doctor3 = new Doctor("Zeynep Yılmaz", 55667788990L, 999);
        Doctor doctor4 = new Doctor("Ali Veli", 33445566778L, 111);
         Doctor doctor5 = new Doctor("Fatma Türk", 44556677889L, 222);

        section1.addDoctor(doctor1);
        section2.addDoctor(doctor2);
        section3.addDoctor(doctor3);
         section4.addDoctor(doctor4);
         section1.addDoctor(doctor5);

        // 2. Randevu Alma
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Başarılı randevular
           crs.makeRandezvous(12345678901L, 1, 101, 777, dateFormat.parse("2024-12-10"));
            crs.makeRandezvous(98765432109L, 2, 201, 888, dateFormat.parse("2024-12-11"));
            crs.makeRandezvous(45678901234L, 1, 301, 999, dateFormat.parse("2024-12-12"));
            crs.makeRandezvous(67890123456L, 2, 401, 111, dateFormat.parse("2024-12-12"));

            // Aynı güne randevu alma denemeleri
            crs.makeRandezvous(12345678901L, 1, 101, 777, dateFormat.parse("2024-12-10"));
             crs.makeRandezvous(98765432109L, 2, 201, 888, dateFormat.parse("2024-12-11"));
             crs.makeRandezvous(45678901234L, 1, 301, 999, dateFormat.parse("2024-12-12"));
             crs.makeRandezvous(67890123456L, 2, 401, 111, dateFormat.parse("2024-12-12"));

            // Aynı doktor farklı tarihlere randevu
            crs.makeRandezvous(12345678901L, 1, 101, 777, dateFormat.parse("2024-12-11"));
            crs.makeRandezvous(98765432109L, 2, 201, 888, dateFormat.parse("2024-12-13"));
            crs.makeRandezvous(45678901234L, 1, 301, 999, dateFormat.parse("2024-12-14"));

        } catch (ParseException e) {
            System.err.println("Tarih formatı hatası: " + e.getMessage());
        }
        catch (IDException e){
            System.err.println("Randevu oluşturulamadı: " + e.getMessage());
        }


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clinic_data.ser"))) {
            oos.writeObject(crs);
            System.out.println("clinic_data.ser dosyası oluşturuldu.");
        } catch (IOException e) {
            System.err.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }
}