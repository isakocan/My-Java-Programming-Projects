package clinicReservationSystem;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Date;

public class CRS implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<Long, Patient> patients;
	private LinkedList<Rendezvous> rendezvous;
	private HashMap<Integer, Hospital> hospitals;

	public CRS() {
		this.patients = new HashMap<Long, Patient>();
		this.rendezvous = new LinkedList<Rendezvous>();
		this.hospitals = new HashMap<Integer, Hospital>();
	}

	public static void main(String[] args) {
		CRS crs = new CRS();
		new CRSGUI(new CRS());
		
		// 1. Hasta, Hastane, Bölüm ve Doktor Oluşturma

		// Hastalar
		Patient patient1 = new Patient("Ahmet Yılmaz", 12345678901L);
		Patient patient2 = new Patient("Elif Kaya", 98765432109L);
		Patient patient3 = new Patient("Can Demir", 45678901234L);
		crs.addPatient(patient1);
		crs.addPatient(patient2);
		crs.addPatient(patient3);
		Patient patient4 = new Patient("Ahmet Yılmaz", 12345678901L);
		crs.addPatient(patient4);

		// Hastaneler
		Hospital hospital1 = new Hospital(1, "Merkez Hastanesi");
		Hospital hospital2 = new Hospital(2, "Şehir Hastanesi");
		crs.addHospital(hospital1);
		crs.addHospital(hospital2);
		Hospital hospital3 = new Hospital(1, "Merkez Hastanesi");
		crs.addHospital(hospital3);

		// Bölümler
		Section section1 = new Section(101, "Kardiyoloji", 1);
		Section section2 = new Section(201, "Dahiliye", 2);
		Section section3 = new Section(301, "Göz Hastalıkları", 1);

		hospital1.addSection(section1);
		hospital1.addSection(section3);
		hospital2.addSection(section2);

		// Doktorlar
		Doctor doctor1 = new Doctor("Ayşe Kaya", 11223344556L, 777);
		Doctor doctor2 = new Doctor("Mehmet Demir", 99887766554L, 888);
		Doctor doctor3 = new Doctor("Zeynep Yılmaz", 55667788990L, 999);

		section1.addDoctor(doctor1);
		section2.addDoctor(doctor2);
		section3.addDoctor(doctor3);

		Doctor doctor4 = new Doctor("Ali Veli", 11223344556L, 777);
		section1.addDoctor(doctor4);

		// 2. Randevu Alma
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			// Başarılı randevular
			makeAppointment(crs, 12345678901L, 1, 101, 777, dateFormat.parse("2024-12-10")); // Başarılı olmalı (Ayşe
																								// Kaya)
			makeAppointment(crs, 98765432109L, 2, 201, 888, dateFormat.parse("2024-12-11")); // Başarılı olmalı (Mehmet
																								// Demir)
			makeAppointment(crs, 45678901234L, 1, 301, 999, dateFormat.parse("2024-12-12")); // Başarılı olmalı (Zeynep
																								// Yılmaz)

			// Aynı gün için ikinci randevular (limit kontrolü)
			makeAppointment(crs, 12345678901L, 1, 101, 777, dateFormat.parse("2024-12-10")); // Başarısız olmalı (aynı
																								// gün) (Ayşe Kaya)
			makeAppointment(crs, 98765432109L, 2, 201, 888, dateFormat.parse("2024-12-11"));// Başarılı olmalı (aynı
																							// gün) (Mehmet Demir)
			makeAppointment(crs, 45678901234L, 1, 301, 999, dateFormat.parse("2024-12-12"));// Başarısız olmalı (aynı
																							// gün) (Zeynep Yılmaz)

			// Aynı doktor farklı tarihler (başarılı)
			makeAppointment(crs, 12345678901L, 1, 101, 777, dateFormat.parse("2024-12-11")); // Başarılı olmalı (farklı
																								// gün) (Ayşe Kaya)
			makeAppointment(crs, 98765432109L, 2, 201, 888, dateFormat.parse("2024-12-12")); // Başarılı olmalı (farklı
																								// gün) (Mehmet Demir)

			// Geçersiz hasta, hastane, bölüm ve doktor için randevu alma denemeleri
			// (istisna fırlatacak)
			makeAppointment(crs, 11111111111L, 1, 101, 777, dateFormat.parse("2024-12-13")); // Geçersiz hasta
			makeAppointment(crs, 12345678901L, 3, 101, 777, dateFormat.parse("2024-12-13")); // Geçersiz hastane
			makeAppointment(crs, 12345678901L, 1, 401, 777, dateFormat.parse("2024-12-13")); // Geçersiz bölüm
			makeAppointment(crs, 12345678901L, 1, 101, 666, dateFormat.parse("2024-12-13")); // Geçersiz doktor
		} catch (ParseException e) {
			System.err.println("Tarih formatı hatası: " + e.getMessage());
		}

		// 3. Bilgileri Yazdırma
		System.out.println("\n--- Sistemdeki Bilgiler ---");
		printSystemInfo(crs);
		
	}

	public boolean makeRandezvous(long patientID, int hospitalID, int sectionID, int diplomaID, Date desiredDate)
			throws IDException {
		// 1. Veri Yapılarında Gerekli ID Kontrolleri
		if (!patients.containsKey(patientID)) {
			throw new IDException("Bu ID'ye sahip bir hasta bulunamadı: " + patientID);
		}

		Patient patient = patients.get(patientID);

		if (!hospitals.containsKey(hospitalID)) {
			throw new IDException("Bu ID'ye sahip bir hastane bulunamadı: " + hospitalID);
		}

		Hospital hospital = hospitals.get(hospitalID);
		Section section = hospital.getSection(sectionID);

		if (section == null) {
			throw new IDException("Bu ID'ye sahip bir bölüm bulunamadı: " + sectionID);
		}

		Doctor doctor = section.getDoctor(diplomaID);

		if (doctor == null) {
			throw new IDException("Bu diploma ID'sine sahip bir doktor bulunamadı: " + diplomaID);
		}

		Schedule schedule = doctor.getSchedule();

		// 2. Randevu Alma İşlemleri
		if (schedule.addRendezvous(patient, desiredDate)) {
			rendezvous.add(new Rendezvous(patient, desiredDate, doctor)); // Randevu listesine ekle
			return true;
		} else
			return false; // Randevu eklenemedi

	}

	public void saveTablesToDisk(String filePath) throws IOException {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filePath))) {
			writer.writeObject(this);
		} catch (IOException e) {
			System.err.println("Dosyaya yazma hatası: " + e.getMessage());
			throw new IOException();
		}

	}

	public void loadTablesToDisk(String filePath) throws IOException, ClassNotFoundException {
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filePath))) {
			CRS loadedCRS = (CRS) reader.readObject();
			this.patients = loadedCRS.patients;
			this.rendezvous = loadedCRS.rendezvous;
			this.hospitals = loadedCRS.hospitals;
		} catch (IOException e) {
			System.err.println("Dosyadan okuma hatası: " + e.getMessage());
			throw new IOException();

		} catch (ClassNotFoundException e) {
			System.err.println("Sınıf bulunamadı hatası: " + e.getMessage());
			throw new ClassNotFoundException();
		}

	}

	public HashMap<Long, Patient> getPatients() {
		return patients;
	}

	public LinkedList<Rendezvous> getRendezvous() {
		return rendezvous;
	}

	public HashMap<Integer, Hospital> getHospitals() {
		return hospitals;
	}

	public void addPatient(Patient patient) {
		try {
			if (this.patients.containsKey(patient.getNationalId())) {
				throw new DuplicateInfoException("Bu ID'ye sahip bir hasta zaten mevcut: " + patient.getNationalId());
			}
			this.patients.put(patient.getNationalId(), patient);
		} catch (DuplicateInfoException e) {
			System.err.println("Hasta eklenemedi:" + e.getMessage());
		}
	}

	public void addHospital(Hospital hospital) {
		try {
			if (this.hospitals.containsKey(hospital.getId())) {
				throw new DuplicateInfoException("Bu ID'ye sahip bir hastane zaten mevcut: " + hospital.getId());
			}
			this.hospitals.put(hospital.getId(), hospital);
		} catch (DuplicateInfoException e) {
			System.err.println("Hastane Eklenemedi:" + e.getMessage());
		}
	}

	public static void makeAppointment(CRS crs, long patientID, int hospitalID, int sectionID, int diplomaID,
			Date desiredDate) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = dateFormat.format(desiredDate);
			if (crs.makeRandezvous(patientID, hospitalID, sectionID, diplomaID, desiredDate)) {
				System.out.println("Randevu başarıyla oluşturuldu. Hasta ID: " + patientID + ", Hastane ID: "
						+ hospitalID + ", Bölüm ID: " + sectionID + ", Doktor Diploma ID: " + diplomaID + ", Tarih: "
						+ formattedDate);
			} else {
				System.err.println("Randevu oluşturulamadı: Doktor o gün dolu. Doktor: " + diplomaID + ", Tarih: "
						+ formattedDate);
			}
		} catch (IDException e) {
			System.err.println("Randevu oluşturulamadı: " + e.getMessage());
		}
	}

	public static void printSystemInfo(CRS crs) {
		System.out.println("\nHastalar:");
		for (Patient p : crs.getPatients().values()) {
			System.out.println(p);
		}
		System.out.println("\nRandevular:");
		for (Rendezvous rendezvous : crs.getRendezvous()) {
			System.out.println(rendezvous);
		}
		System.out.println("\nHastaneler:");
		for (Hospital hospital : crs.getHospitals().values()) {
			System.out.println(hospital);
		}
	}

}