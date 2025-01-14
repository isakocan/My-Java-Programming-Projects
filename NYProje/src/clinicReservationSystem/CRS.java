package clinicReservationSystem;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
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
		Scanner scanner = new Scanner(System.in);
		String choice;
		do {
			System.out.println("Hangi modda çalıştırmak istersiniz:\n");
			System.out.println("1. Terminal modu");
			System.out.println("2. GUI modu");
			System.out.print("Seçiminiz: ");
			choice = scanner.nextLine();
			switch (choice) {
			case "1": {
				new CRSTerminal();
				break;
			}
			case "2":
				new CRSGUI();
				break;
			default:
				System.out.println("Lütfen geçerli bir sayı girin.");
				break;
			}

		} while (!choice.equals("1") && !choice.equals("2"));
		scanner.close();
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

	@SuppressWarnings("resource")
	public void saveTablesToDisk(String filePath) throws IOException {
		try {
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filePath));
			writer.writeObject(this);
		} catch (IOException e) {
			throw new IOException("Veri kaydedilemedi. Dosya yazma hatası.");
		}
	}

	@SuppressWarnings("resource")
	public void loadTablesToDisk(String filePath) throws IOException, ClassNotFoundException {
		try {
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filePath));
			CRS loadedCRS = (CRS) reader.readObject();
			this.patients = loadedCRS.patients;
			this.rendezvous = loadedCRS.rendezvous;
			this.hospitals = loadedCRS.hospitals;
		} catch (IOException e) {
			throw new IOException("Veri yüklenirken bir sorun oluştu. Dosya okuma hatası.");
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
		if (this.patients.containsKey(patient.getNationalId()))
			throw new DuplicateInfoException("Bu ID'ye sahip bir hasta zaten mevcut: " + patient.getNationalId());

		this.patients.put(patient.getNationalId(), patient);
	}

	public void addHospital(Hospital hospital) {
		if (this.hospitals.containsKey(hospital.getId()))
			throw new DuplicateInfoException("Bu ID'ye sahip bir hastane zaten mevcut: " + hospital.getId());

		this.hospitals.put(hospital.getId(), hospital);
	}

}