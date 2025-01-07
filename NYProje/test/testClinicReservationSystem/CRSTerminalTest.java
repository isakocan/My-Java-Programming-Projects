package testClinicReservationSystem;

import clinicReservationSystem.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class CRSTerminalTest {
	private final CRS crs;
	private final StringBuilder output;
	private String filePath;

	public CRSTerminalTest(CRS crs, StringBuilder output) {
		this.crs = crs;
		this.output = output;
	}

	public CRSTerminalTest(CRS crs) {
		this(crs, new StringBuilder());
	}

	// Kullanıcı arayüzü methodları kaldırılıp sadece iş methodları kaldı

	public void addPatient(String patientName, long patientId) throws DuplicateInfoException {
		Patient patient = new Patient(patientName, patientId);
		crs.addPatient(patient);
		output.append("Hasta başarıyla eklendi: " + patient + "\n");
	}

	public void addHospital(int hospitalId, String hospitalName) throws DuplicateInfoException {
		Hospital hospital = new Hospital(hospitalId, hospitalName);
		crs.addHospital(hospital);
		output.append("Hastane başarıyla eklendi: " + hospital + "\n");
	}

	public void addSection(int hospitalId, int sectionId, String sectionName, int maxPatientPerDay)
			throws DuplicateInfoException, IDException {
		Hospital hospital = crs.getHospitals().get(hospitalId);
		if (hospital == null) {
			throw new IDException("Bu id'ye sahip bir hastane bulunamadı: " + hospitalId);
		}
		Section section = new Section(sectionId, sectionName, maxPatientPerDay);
		hospital.addSection(section);
		output.append("Bölüm başarıyla eklendi: " + section + "\n");
	}

	public void addDoctor(int hospitalId, int sectionId, String doctorName, long doctorNationalId, int doctorDiplomaId)
			throws DuplicateInfoException, IDException {
		Hospital hospital = crs.getHospitals().get(hospitalId);
		if (hospital == null) {
			throw new IDException("Bu id'ye sahip bir hastane bulunamadı: " + hospitalId);
		}
		Section section = hospital.getSection(sectionId);
		if (section == null) {
			throw new IDException("Bu id'ye sahip bir bölüm bulunamadı: " + sectionId);
		}
		Doctor doctor = new Doctor(doctorName, doctorNationalId, doctorDiplomaId);
		section.addDoctor(doctor);
		output.append("Doktor başarıyla eklendi: " + doctor + "\n");
	}

	public boolean makeAppointment(long patientId, int hospitalId, int sectionId, int diplomaId, String dateString)
			throws InterruptedException {
		// THREAD CREATION
		final boolean[] success = { false };

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (crs) {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						Date desiredDate = dateFormat.parse(dateString);
						if (crs.makeRandezvous(patientId, hospitalId, sectionId, diplomaId, desiredDate)) {
							output.append("Randevu başarıyla oluşturuldu.\n");
							success[0] = true;
						} else {
							output.append("Randevu oluşturulamadı. Doktor o gün dolu.\n");
							success[0] = false;
						}
					} catch (ParseException e) {
						output.append("Hata: Lütfen tarih formatını doğru girin (GG-AA-YYYY)\n");
					} catch (Exception e) {
						output.append("Hata: Bilinmeyen bir hata oluştu. Tekrar deneyin.\n");
					}
				}
			}
		});
		thread.start();
		thread.join();
		return success[0];
	}

	public void printSystemInfo() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (crs) {
					try {
						output.append("\nHastalar:\n");
						for (Patient p : crs.getPatients().values()) {
							output.append("\t" + p + "\n");
						}
						output.append("\nHastaneler:\n");
						for (Hospital hospital : crs.getHospitals().values()) {
							output.append("\t" + hospital + "\n");
							for (Section section : hospital.listSections()) {
								output.append("\t\t" + section + "\n");
								for (Doctor doctor : section.listDoctors()) {
									output.append("\t\t\t" + doctor + "\n");
								}
							}
						}
						output.append("\nRandevular:\n");
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
							output.append("\tRandevu tarihi: " + formattedDate + ", \thasta adı: "
									+ rendezvous.getPatient().getName() + ", \tdoktor adı: " + doctor.getName()
									+ ", \thastane adı: " + hospital.getName() + ", \tbölüm adı: " + section.getName()
									+ "\n");
						}
					} catch (Exception e) {
						output.append("Hata: Bilinmeyen bir hata oluştu. Tekrar deneyin.\n");
					}
				}
			}
		});
		thread.start();
		thread.join();
	}

	public void saveTablesToDisk(String filePathInput) throws IOException {
		if (filePathInput.isEmpty()) {
			filePath = "clinic_data.ser";
		} else {
			filePath = filePathInput;
		}
		crs.saveTablesToDisk(filePath);
		output.append("Veriler kaydedildi.\n");
	}

	public void loadTablesFromDisk(String filePathInput) throws ClassNotFoundException, IOException {
		if (filePathInput.isEmpty()) {
			filePath = "clinic_data.ser";
		} else {
			filePath = filePathInput;
		}
		crs.loadTablesToDisk(filePath);
		output.append("Veriler yüklendi.\n");
	}

	public CRS getCrs() {
		return crs;
	}

	public String getOutput() {
		return output.toString();
	}
}