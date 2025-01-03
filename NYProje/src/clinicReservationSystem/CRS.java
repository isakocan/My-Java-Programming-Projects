package clinicReservationSystem;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Date;

public class CRS implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private HashMap<Long, Patient> patients;
    private LinkedList<Rendezvous> rendezvous;
    private HashMap<Integer, Hospital> hospitals;

    public CRS() {
        this.patients = new HashMap<>();
        this.rendezvous = new LinkedList<>();
        this.hospitals = new HashMap<>();
    }

    public boolean makeRandezvous(long patientID, int hospitalID, int sectionID, int diplomaID, Date desiredDate) throws IDException {
    	// 1. Veri Yapılarında Gerekli ID Kontrolleri
    	if (!patients.containsKey(patientID)) {
            throw new IDException("Belirtilen hasta ID'si bulunamadı: " + patientID);
        }

    	Patient patient = patients.get(patientID);
    	
        if (!hospitals.containsKey(hospitalID)) {
            throw new IDException("Belirtilen hastane ID'si bulunamadı: " + hospitalID);
        }

        Hospital hospital = hospitals.get(hospitalID);
        Section section = hospital.getSection(sectionID);

        if (section == null){
        	throw new IDException("Belirtilen bölüm ID'si bulunamadı: " + sectionID);
        }

        Doctor doctor = section.getDoctor(diplomaID);

        if (doctor == null){
           throw new IDException("Belirtilen doktor diploma ID'si bulunamadı: " + diplomaID);
        }
        
        Schedule schedule = doctor.getSchedule();

        // 2. Randevu Alma İşlemleri
        if (schedule.addRendezvous(patient, desiredDate)) {
            rendezvous.add(new Rendezvous(patient, desiredDate, doctor)); // Randevu listesine ekle
            return true;
        }
        else
            return false; // Randevu eklenemedi
        
    }

    public void saveTablesToDisk(String filePath) throws IOException {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filePath))) {
        	writer.writeObject(this);
        } 
        catch (IOException e) {
            System.err.println("Dosyaya yazma hatası: " + e.getMessage());
        } 
        
    }

    public void loadTablesToDisk(String filePath) throws IOException, ClassNotFoundException {
         try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filePath))) {
        	 CRS loadedCRS = (CRS) reader.readObject();          
             this.patients = loadedCRS.patients;
             this.rendezvous = loadedCRS.rendezvous;
             this.hospitals = loadedCRS.hospitals;
         } 
         catch (IOException e) {
             System.err.println("Dosyadan okuma hatası: " + e.getMessage());
             
         } 
         catch (ClassNotFoundException e) {
             System.err.println("Sınıf bulunamadı hatası: " + e.getMessage());
             
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

    public void addPatient(Patient patient){
         this.patients.put(patient.getNationalId(), patient);
    }

    public void addHospital(Hospital hospital){
        this.hospitals.put(hospital.getId(), hospital);
    }

}
