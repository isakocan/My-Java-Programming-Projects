package clinicReservationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CRSGUI {

    private CRS crs;
    private JFrame frame;
    private JTextField patientNameField;
    private JTextField patientIdField;
    private JTextField hospitalIdField;
    private JTextField sectionIdField;
    private JTextField doctorDiplomaIdField;
    private JTextField desiredDateField;
    private JLabel resultLabel;

    public CRSGUI(CRS crs) {
        this.crs = crs;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Klinik Randevu Sistemi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        // Etiketler ve metin alanları oluşturma
        JLabel patientNameLabel = new JLabel("Hasta Adı:");
        patientNameField = new JTextField(20);
        JLabel patientIdLabel = new JLabel("Hasta ID:");
        patientIdField = new JTextField(20);
        JLabel hospitalIdLabel = new JLabel("Hastane ID:");
        hospitalIdField = new JTextField(20);
        JLabel sectionIdLabel = new JLabel("Bölüm ID:");
        sectionIdField = new JTextField(20);
        JLabel doctorDiplomaIdLabel = new JLabel("Doktor Diploma ID:");
        doctorDiplomaIdField = new JTextField(20);
        JLabel desiredDateLabel = new JLabel("Randevu Tarihi (yyyy-MM-dd):");
        desiredDateField = new JTextField(20);
        resultLabel = new JLabel("");

        JButton makeAppointmentButton = new JButton("Randevu Al");
        makeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientName = patientNameField.getText();
                    long patientId = Long.parseLong(patientIdField.getText());
                    int hospitalId = Integer.parseInt(hospitalIdField.getText());
                    int sectionId = Integer.parseInt(sectionIdField.getText());
                    int doctorDiplomaId = Integer.parseInt(doctorDiplomaIdField.getText());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date desiredDate = dateFormat.parse(desiredDateField.getText());

                    Patient patient = new Patient(patientName, patientId);
                    crs.addPatient(patient);
                    boolean appointmentSuccessful = crs.makeRandezvous(patientId, hospitalId, sectionId, doctorDiplomaId, desiredDate);
                    if (appointmentSuccessful)
                         resultLabel.setText("Randevu oluşturuldu.");
                    else
                         resultLabel.setText("Randevu oluşturulamadı. Doktor o gün dolu");
                } catch (NumberFormatException ex) {
                  resultLabel.setText("Hata: Lütfen sayısal değerleri kontrol edin.");
                  System.err.println("Hata: Lütfen sayısal değerleri kontrol edin. " + ex.getMessage());
               } catch (ParseException ex) {
                    resultLabel.setText("Hata: Lütfen tarih formatını doğru girin (yyyy-MM-dd).");
                     System.err.println("Hata: Lütfen tarih formatını doğru girin (yyyy-MM-dd). " + ex.getMessage());
               } catch (DuplicateInfoException ex) {
                   resultLabel.setText("Hata: " + ex.getMessage());
                   System.err.println("Hata: " + ex.getMessage());
               } catch (IDException ex){
                 resultLabel.setText("Hata: " + ex.getMessage());
                 System.err.println("Hata: " + ex.getMessage());
               }
                catch (Exception ex) {
                   resultLabel.setText("Hata: Bilinmeyen bir hata oluştu.");
                    System.err.println("Hata: Bilinmeyen bir hata oluştu. " + ex.getMessage());
                }
            }
        });

        // Panoya ekleme
        panel.add(patientNameLabel);
        panel.add(patientNameField);
        panel.add(patientIdLabel);
        panel.add(patientIdField);
        panel.add(hospitalIdLabel);
        panel.add(hospitalIdField);
        panel.add(sectionIdLabel);
        panel.add(sectionIdField);
        panel.add(doctorDiplomaIdLabel);
        panel.add(doctorDiplomaIdField);
        panel.add(desiredDateLabel);
        panel.add(desiredDateField);
        panel.add(resultLabel);
        panel.add(makeAppointmentButton);

        frame.add(panel);

        frame.setVisible(true);
    }
}