package clinicReservationSystem;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class CRSTerminal {
	private CRS crs;
	private String filePath;

	public CRSTerminal() {
		this.crs = new CRS();
		terminalMode();
	}

	public void terminalMode() {
		Scanner scanner = new Scanner(System.in);
		String choice;
		do {

			System.out.println("\n--- Klinik Randevu Sistemi ---");
			System.out.println("1. Hasta Ekle");
			System.out.println("2. Hastane Ekle");
			System.out.println("3. Bölüm Ekle");
			System.out.println("4. Doktor Ekle");
			System.out.println("5. Randevu Al");
			System.out.println("6. Sistem Bilgilerini Görüntüle");
			System.out.println("7. Verileri Kaydet");
			System.out.println("8. Verileri Yükle");
			System.out.println("0. Çıkış");
			System.out.print("\nSeçiminiz: ");
			choice = scanner.nextLine();

			try {
				switch (choice) {
				case "1":
					addPatient(scanner);
					break;
				case "2":
					addHospital(scanner);
					break;
				case "3":
					addSection(scanner);
					break;
				case "4":
					addDoctor(scanner);
					break;
				case "5":
					makeAppointment(scanner);
					break;
				case "6":
					printSystemInfo();
					break;
				case "7":
					saveTablesToDisk(scanner);
					break;
				case "8":
					loadTablesFromDisk(scanner);
					break;
				case "0":
					scanner.close();
					System.out.println("Program kapatılıyor..");
					break;
				default:
					System.out.println("Geçersiz sayı girildi.");
					break;
				}

			} catch (NumberFormatException e) {
				System.err.println("Hata: Geçersiz bir sayısal giriş yapıldı. Lütfen tekrar deneyin");
			} catch (DuplicateInfoException e) {
				System.err.println("Hata:" + e.getMessage());
			} catch (IDException e) {
				System.err.println("Hata:" + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.err.println("Sınıf bulunamadı hatası: " + e.getMessage());
			} catch (IOException e) {
				System.err.println("Dosyadan okuma hatası: " + e.getMessage());
			} catch (InterruptedException e) {
				System.err.println("Hata : Teknik bir hata oluştu.\n");
			} catch (Exception e) {
				System.err.println("Hata: Bilinmeyen bir hata oluştu.");
			} finally {
				if (!choice.equals("0")) {
					System.out.println("\n[i]-> Menüye dönmek için enter tuşuna basın: ");
					choice = scanner.nextLine();
				}
			}

		} while (!choice.equals("0"));
	}

	private void addPatient(Scanner scanner) throws DuplicateInfoException {
		System.out.print("Hasta Adı: ");
		String patientName = scanner.nextLine();
		System.out.print("Hasta ID: ");
		long patientId = Long.parseLong(scanner.nextLine());
		Patient patient = new Patient(patientName, patientId);
		crs.addPatient(patient);
		System.out.println("Hasta başarıyla eklendi: " + patient);
	}

	private void addHospital(Scanner scanner) throws DuplicateInfoException {
		System.out.print("Hastane ID: ");
		int hospitalId = Integer.parseInt(scanner.nextLine());
		System.out.print("Hastane Adı: ");
		String hospitalName = scanner.nextLine();
		Hospital hospital = new Hospital(hospitalId, hospitalName);
		crs.addHospital(hospital);
		System.out.println("Hastane başarıyla eklendi: " + hospital);
	}

	private void addSection(Scanner scanner) throws DuplicateInfoException, IDException {
		System.out.println("Kayıtlı Hastaneler:");
		for (Hospital h : crs.getHospitals().values()) {
			System.out.println("\t" + h);
		}
		System.out.print("Hangi hastaneye bölüm eklemek istiyorsunuz? (Hastane ID): ");
		int hospitalId = Integer.parseInt(scanner.nextLine());
		Hospital hospital = crs.getHospitals().get(hospitalId);
		if (hospital == null) {
			throw new IDException("Bu id'ye sahip bir hastane bulunamadı: " + hospitalId);
		}
		System.out.print("Bölüm ID: ");
		int sectionId = Integer.parseInt(scanner.nextLine());
		System.out.print("Bölüm Adı: ");
		String sectionName = scanner.nextLine();
		System.out.print("Bölüm Max Hasta Sayısı: ");
		int maxPatientPerDay = Integer.parseInt(scanner.nextLine());
		Section section = new Section(sectionId, sectionName, maxPatientPerDay);
		hospital.addSection(section);
		System.out.println("Bölüm başarıyla eklendi: " + section);
	}

	private void addDoctor(Scanner scanner) throws DuplicateInfoException, IDException {
		System.out.println("Kayıtlı Hastaneler:");
		for (Hospital h : crs.getHospitals().values()) {
			System.out.println("\t" + h);
			for (Section s : h.listSections()) {
				System.out.println("\t\t" + s);
			}
		}

		System.out.print("Hangi hastaneye doktor eklemek istiyorsunuz? (Hastane ID): ");
		int hospitalId = Integer.parseInt(scanner.nextLine());
		Hospital hospital = crs.getHospitals().get(hospitalId);
		if (hospital == null) {
			throw new IDException("Bu id'ye sahip bir hastane bulunamadı: " + hospitalId);
		}
		System.out.print("Hangi bölüme doktor eklemek istiyorsunuz? (Bölüm ID): ");
		int sectionId = Integer.parseInt(scanner.nextLine());
		Section section = hospital.getSection(sectionId);
		if (section == null) {
			throw new IDException("Bu id'ye sahip bir bölüm bulunamadı: " + sectionId);
		}
		System.out.print("Doktor Adı: ");
		String doctorName = scanner.nextLine();
		System.out.print("Doktor National ID: ");
		long doctorNationalId = Long.parseLong(scanner.nextLine());
		System.out.print("Doktor Diploma ID: ");
		int doctorDiplomaId = Integer.parseInt(scanner.nextLine());
		Doctor doctor = new Doctor(doctorName, doctorNationalId, doctorDiplomaId);
		section.addDoctor(doctor);
		System.out.println("Doktor başarıyla eklendi: " + doctor);

	}

	private void makeAppointment(Scanner scanner) throws InterruptedException {
		// THREAD CREATION
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (crs) {
						System.out.println("Kayıtlı Hastalar:");
						for (Patient p : crs.getPatients().values()) {
							System.out.println("\t" + p);
						}
						System.out.print("Hangi hasta için randevu oluşturmak istiyorsunuz? (Hasta ID): ");
						long patientID = Long.parseLong(scanner.nextLine());
						Patient patient = crs.getPatients().get(patientID);
						if (patient == null) {
							throw new IDException("Bu id'ye sahip bir hasta bulunamadı: " + patientID);
						}
						System.out.println("Kayıtlı Hastaneler:");
						for (Hospital h : crs.getHospitals().values()) {
							System.out.println("\t" + h);
						}
						System.out.print("Hangi hastaneye randevu oluşturmak istiyorsunuz? (Hastane ID): ");
						int hospitalID = Integer.parseInt(scanner.nextLine());
						Hospital hospital = crs.getHospitals().get(hospitalID);
						if (hospital == null) {
							throw new IDException("Bu id'ye sahip bir hastane bulunamadı: " + hospitalID);
						}
						System.out.println("Kayıtlı Bölümler:");
						for (Section s : hospital.listSections()) {
							System.out.println("\t\t" + s);
						}
						System.out.print("Hangi bölüme randevu oluşturmak istiyorsunuz? (Bölüm ID): ");
						int sectionID = Integer.parseInt(scanner.nextLine());
						Section section = hospital.getSection(sectionID);
						if (section == null) {
							throw new IDException("Bu id'ye sahip bir bölüm bulunamadı: " + sectionID);
						}
						System.out.println("Kayıtlı Doktorlar:");
						for (Doctor d : section.listDoctors()) {
							System.out.println("\t\t\t" + d);
						}
						System.out.print("Hangi doktora randevu oluşturmak istiyorsunuz? (Doktor Diploma ID): ");
						int diplomaID = Integer.parseInt(scanner.nextLine());
						Doctor doctor = section.getDoctor(diplomaID);
						if (doctor == null) {
							throw new IDException("Bu diploma id'sine sahip bir doktor bulunamadı: " + diplomaID);
						}
						System.out.print("Randevu Tarihi (dd-MM-yyyy): ");
						String dateString = scanner.nextLine();
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						Date desiredDate = dateFormat.parse(dateString);
						if (crs.makeRandezvous(patientID, hospitalID, sectionID, diplomaID, desiredDate)) {
							System.out.println("Randevu başarıyla oluşturuldu.");
						} else
							System.err.println("Randevu oluşturulamadı. Doktor o gün dolu.");
					}
				} catch (IDException e) {
					System.err.println("Hata: " + e.getMessage());
				} catch (ParseException e) {
					System.err.println("Hata: Lütfen tarih formatını doğru girin (GG-AA-YYYY)");
				} catch (Exception e) {
					System.err.println("Hata: Bilinmeyen bir hata oluştu. Tekrar deneyin.");
				}
			}
		});
		thread.start();
		thread.join();
	}

	public void printSystemInfo() throws InterruptedException {
		// THREAD CREATION
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (crs) {
						System.out.println("\nHastalar:");
						for (Patient p : crs.getPatients().values()) {
							System.out.println("\t" + p);
						}
						System.out.println("\nHastaneler:");
						for (Hospital hospital : crs.getHospitals().values()) {
							System.out.println("\t" + hospital);
							for (Section section : hospital.listSections()) {
								System.out.println("\t\t" + section);
								for (Doctor doctor : section.listDoctors()) {
									System.out.println("\t\t\t" + doctor);
								}
							}
						}
						System.out.println("\nRandevular:");
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						for (Rendezvous rendezvous : crs.getRendezvous()) {
							Date date = rendezvous.getDateTime();
							String formattedDate = dateFormat.format(date);
							Doctor doctor = rendezvous.getDoctor();
							Hospital hospital = null;
							Section section = null;

							boolean found = false;
							Iterator<Hospital> hospitalIterator = crs.getHospitals().values().iterator();
							while (hospitalIterator.hasNext() && !found) {
								Hospital h = hospitalIterator.next();
								Iterator<Section> sectionIterator = h.listSections().iterator();
								while (sectionIterator.hasNext() && !found) {
									Section s = sectionIterator.next();
									if (s.getDoctor(doctor.getDiplomaId()) != null) {
										hospital = h;
										section = s;
										found = true;
									}
								}
							}
							System.out.println("\tRandevu tarihi: " + formattedDate + ", \thasta adı: "
									+ rendezvous.getPatient().getName() + ", \tdoktor adı: " + doctor.getName()
									+ ", \thastane adı: " + hospital.getName() + ", \tbölüm adı: " + section.getName());
						}
					}
				} catch (Exception e) {
					System.err.println("Hata: Bilinmeyen bir hata oluştu. Tekrar deneyin.");
				}
			}
		});
		// THREAD CREATION
		thread.start();
		thread.join();
	}

	private void saveTablesToDisk(Scanner scanner) throws IOException {
		System.out.print("Kaydedilecek dosya adını giriniz. (varsayılan için enter): ");
		String filePathInput = scanner.nextLine();
		if (filePathInput.isEmpty()) {
			filePath = "clinic_data.ser";
		} else {
			filePath = filePathInput;
		}

		crs.saveTablesToDisk(filePath);
		System.out.println("Veriler kaydedildi.");

	}

	private void loadTablesFromDisk(Scanner scanner) throws ClassNotFoundException, IOException {
		System.out.print("Yüklenecek dosyanın adını giriniz.(varsayılan için enter) ");
		String filePathInput = scanner.nextLine();
		if (filePathInput.isEmpty()) {
			filePath = "clinic_data.ser";
		} else {
			filePath = filePathInput;
		}

		crs.loadTablesToDisk(filePath);
		System.out.println("Veriler yüklendi.");

	}
}