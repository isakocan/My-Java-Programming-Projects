package clinicReservationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CRSGUI {

	private CRS crs;
	private JFrame frame;
	private JTextArea resultArea;
	private JFileChooser fileChooser;
	private String filePath;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JScrollPane scrollPane;

	public CRSGUI(CRS crs) {
		this.crs = crs;
		createAndShowGUI();

	}

	private void loadData(String filePath) {
        try {
            crs.loadTablesToDisk(filePath);
            resultArea.append("Veriler yüklendi.\n");
        } catch (IOException e) {
            resultArea.append("Veri yüklenirken bir sorun oluştu. Dosya okuma hatası.\n");
        } catch (ClassNotFoundException e) {
            resultArea.append("Hatalı dosya formatı. Lütfen .ser uzantılı bir dosya seçin.\n");
        }
    }

	private void createAndShowGUI() {
		frame = new JFrame("Klinik Randevu Sistemi");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(800, 600);

		mainPanel = new JPanel(new BorderLayout());
		resultArea = new JTextArea(10, 50);
		resultArea.setEditable(false);
		scrollPane = new JScrollPane(resultArea);

		// Dosya Yükleme Bölümü
		JPanel filePanel = new JPanel();
		JButton loadFileButton = new JButton("Veri Dosyası Yükle");

		loadFileButton.addActionListener(e -> {
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(".")); // Uygulama klasörünü varsayılan yap
			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				filePath = selectedFile.getAbsolutePath();
				loadData(filePath);
			} else {
				resultArea.append("Veri dosyası seçilmedi.\n");
			}
		});

		filePanel.add(loadFileButton);
		mainPanel.add(filePanel, BorderLayout.NORTH);

		buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		// Ana Ekran Butonları
		JButton addPatientButton = new JButton("Hasta Ekle");
		JButton addHospitalButton = new JButton("Hastane Ekle");
		JButton addSectionButton = new JButton("Bölüm Ekle");
		JButton addDoctorButton = new JButton("Doktor Ekle");
		JButton makeAppointmentButton = new JButton("Randevu Al");
		JButton viewSystemButton = new JButton("Sistem Bilgilerini Görüntüle");

		addPatientButton.addActionListener(e -> showPatientPanel());
		addHospitalButton.addActionListener(e -> showHospitalPanel());
		addSectionButton.addActionListener(e -> showSectionPanel());
		addDoctorButton.addActionListener(e -> showDoctorPanel());
		makeAppointmentButton.addActionListener(e -> showAppointmentPanel());
		viewSystemButton.addActionListener(e -> showSystemInfo());

		buttonPanel.add(addPatientButton);
		buttonPanel.add(addHospitalButton);
		buttonPanel.add(addSectionButton);
		buttonPanel.add(addDoctorButton);
		buttonPanel.add(makeAppointmentButton);
		buttonPanel.add(viewSystemButton);

		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);

		frame.add(mainPanel);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int choice = JOptionPane.showConfirmDialog(frame, "Verileri kaydetmek istiyor musunuz?",
						"Kapatma Onayı", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("."));
					int result = fileChooser.showSaveDialog(frame);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						filePath = selectedFile.getAbsolutePath();
					}

					try {
						crs.saveTablesToDisk(filePath);
						resultArea.append("Veriler kaydedildi.\n");
						System.out.println("Veriler kaydedildi.");
					} catch (IOException ex) {
						resultArea.append("Veriler kaydedilemedi.\n");
						System.err.println("Veriler kaydedilemedi: " + ex.getMessage());
					}
				}
				frame.dispose();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
		});

		frame.setVisible(true);
	}

	private void showMainPanel() {
		mainPanel.removeAll();
		JPanel filePanel = new JPanel();
		JButton loadFileButton = new JButton("Veri Dosyası Yükle");

		loadFileButton.addActionListener(e -> {
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(".")); // Uygulama klasörünü varsayılan yap
			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				filePath = selectedFile.getAbsolutePath();
				loadData(filePath);
			} else {
				resultArea.append("Veri dosyası seçilmedi.\n");
			}
		});
		filePanel.add(loadFileButton);
		mainPanel.add(filePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	private void showPatientPanel() {
		mainPanel.remove(buttonPanel);
		mainPanel.remove(scrollPane);

		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

		JLabel patientNameLabel = new JLabel("Hasta Adı:");
		JTextField patientNameField = new JTextField(20);
		JLabel patientIdLabel = new JLabel("Hasta ID:");
		JTextField patientIdField = new JTextField(20);
		JButton addPatientButton = new JButton("Hasta Ekle");
		JButton backButton = new JButton("Geri");

		addPatientButton.addActionListener(e -> {
			try {
				String patientName = patientNameField.getText();
				long patientId = Long.parseLong(patientIdField.getText());
				Patient patient = new Patient(patientName, patientId);
				crs.addPatient(patient);
				resultArea.append("Hasta başarıyla eklendi. " + patient + "\n");
				patientNameField.setText("");
				patientIdField.setText("");
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen geçerli bir Hasta ID girin.\n");
				System.err.println("Hata: Lütfen geçerli bir Hasta ID girin. " + ex.getMessage());
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
				System.err.println("Hata: " + ex.getMessage());
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
				System.err.println("Bilinmeyen bir hata oluştu." + ex.getMessage());
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();
			}
		});

		panel.add(patientNameLabel);
		panel.add(patientNameField);
		panel.add(patientIdLabel);
		panel.add(patientIdField);
		panel.add(addPatientButton);
		panel.add(backButton);
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();

	}

	private void showHospitalPanel() {
		mainPanel.remove(buttonPanel);
		mainPanel.remove(scrollPane);

		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

		JLabel hospitalIdLabel = new JLabel("Hastane ID:");
		JTextField hospitalIdField = new JTextField(20);
		JLabel hospitalNameLabel = new JLabel("Hastane Adı:");
		JTextField hospitalNameField = new JTextField(20);
		JButton addHospitalButton = new JButton("Hastane Ekle");
		JButton backButton = new JButton("Geri");

		addHospitalButton.addActionListener(e -> {
			try {
				int hospitalId = Integer.parseInt(hospitalIdField.getText());
				String hospitalName = hospitalNameField.getText();
				Hospital hospital = new Hospital(hospitalId, hospitalName);
				crs.addHospital(hospital);
				resultArea.append("Hastane başarıyla eklendi. " + hospital + "\n");
				hospitalIdField.setText("");
				hospitalNameField.setText("");
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen geçerli bir Hastane ID girin.\n");
				System.err.println("Hata: Lütfen geçerli bir Hastane ID girin. " + ex.getMessage());
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
				System.err.println("Hata: " + ex.getMessage());
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
				System.err.println("Bilinmeyen bir hata oluştu." + ex.getMessage());
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();
			}
		});

		panel.add(hospitalIdLabel);
		panel.add(hospitalIdField);
		panel.add(hospitalNameLabel);
		panel.add(hospitalNameField);
		panel.add(addHospitalButton);
		panel.add(backButton);
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	private void showSectionPanel() {
		mainPanel.remove(buttonPanel);
		mainPanel.remove(scrollPane);
		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
		JLabel hospitalLabel = new JLabel("Hastane Seç:");
		JComboBox<Hospital> hospitalComboBox = new JComboBox<>(crs.getHospitals().values().toArray(new Hospital[0]));
		JLabel sectionIdLabel = new JLabel("Bölüm ID:");
		JTextField sectionIdField = new JTextField(20);
		JLabel sectionNameLabel = new JLabel("Bölüm Adı:");
		JTextField sectionNameField = new JTextField(20);
		JLabel sectionMaxPatientPerDayLabel = new JLabel("Bölüm Max Hasta Sayısı:");
		JTextField sectionMaxPatientPerDayField = new JTextField(20);
		JButton addSectionButton = new JButton("Bölüm Ekle");
		JButton backButton = new JButton("Geri");

		addSectionButton.addActionListener(e -> {
			try {
				int sectionId = Integer.parseInt(sectionIdField.getText());
				String sectionName = sectionNameField.getText();
				int maxPatientPerDay = Integer.parseInt(sectionMaxPatientPerDayField.getText());
				Hospital selectedHospital = (Hospital) hospitalComboBox.getSelectedItem();

				if (selectedHospital != null) {
					Section section = new Section(sectionId, sectionName, maxPatientPerDay);
					selectedHospital.addSection(section);
					resultArea.append("Bölüm başarıyla eklendi. " + section + "\n");
					sectionIdField.setText("");
					sectionNameField.setText("");
					sectionMaxPatientPerDayField.setText("");

				} else {
					resultArea.append("Hata: Lütfen geçerli bir hastane seçin.\n");
					System.err.println("Hata: Lütfen geçerli bir hastane seçin.");
				}
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen geçerli bir Bölüm ID veya Hasta Sayısı girin.\n");
				System.err.println("Hata: Lütfen geçerli bir Bölüm ID veya Hasta Sayısı girin. " + ex.getMessage());
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
				System.err.println("Hata: " + ex.getMessage());
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
				System.err.println("Bilinmeyen bir hata oluştu." + ex.getMessage());
			}

		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();
			}
		});
		panel.add(hospitalLabel);
		panel.add(hospitalComboBox);
		panel.add(sectionIdLabel);
		panel.add(sectionIdField);
		panel.add(sectionNameLabel);
		panel.add(sectionNameField);
		panel.add(sectionMaxPatientPerDayLabel);
		panel.add(sectionMaxPatientPerDayField);
		panel.add(addSectionButton);
		panel.add(backButton);
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	private void showDoctorPanel() {
		mainPanel.remove(buttonPanel);
		mainPanel.remove(scrollPane);
		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
		JLabel hospitalLabel = new JLabel("Hastane Seç:");
		JComboBox<Hospital> hospitalComboBox = new JComboBox<>(crs.getHospitals().values().toArray(new Hospital[0]));
		JLabel sectionLabel = new JLabel("Bölüm Seç:");
		JComboBox<Section> sectionComboBox = new JComboBox<>();
		JLabel doctorNameLabel = new JLabel("Doktor Adı:");
		JTextField doctorNameField = new JTextField(20);
		JLabel doctorNationalIdLabel = new JLabel("Doktor National ID:");
		JTextField doctorNationalIdField = new JTextField(20);
		JLabel doctorDiplomaIdLabel = new JLabel("Doktor Diploma ID:");
		JTextField doctorDiplomaIdField = new JTextField(20);
		JButton addDoctorButton = new JButton("Doktor Ekle");
		JButton backButton = new JButton("Geri");

		hospitalComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sectionComboBox.removeAllItems();
				Hospital selectedHospital = (Hospital) hospitalComboBox.getSelectedItem();
				if (selectedHospital != null) {
					for (Section s : selectedHospital.listSections()) {
						sectionComboBox.addItem(s);
					}
				}
			}
		});

		addDoctorButton.addActionListener(e -> {
			try {
				String doctorName = doctorNameField.getText();
				long doctorNationalId = Long.parseLong(doctorNationalIdField.getText());
				int doctorDiplomaId = Integer.parseInt(doctorDiplomaIdField.getText());
				Section selectedSection = (Section) sectionComboBox.getSelectedItem();
				if (selectedSection != null) {
					Doctor doctor = new Doctor(doctorName, doctorNationalId, doctorDiplomaId);
					selectedSection.addDoctor(doctor);
					resultArea.append("Doktor başarıyla eklendi. " + doctor + "\n");
					doctorNameField.setText("");
					doctorNationalIdField.setText("");
					doctorDiplomaIdField.setText("");
				} else {
					resultArea.append("Hata: Lütfen geçerli bir hastane ve bölüm seçin.\n");
					System.err.println("Hata: Lütfen geçerli bir hastane ve bölüm seçin.");
				}
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen geçerli bir doktor id veya diploma id girin.\n");
				System.err.println("Hata: Lütfen geçerli bir doktor id veya diploma id girin. " + ex.getMessage());
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
				System.err.println("Hata: " + ex.getMessage());
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
				System.err.println("Bilinmeyen bir hata oluştu." + ex.getMessage());
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();
			}
		});
		panel.add(hospitalLabel);
		panel.add(hospitalComboBox);
		panel.add(sectionLabel);
		panel.add(sectionComboBox);
		panel.add(doctorNameLabel);
		panel.add(doctorNameField);
		panel.add(doctorNationalIdLabel);
		panel.add(doctorNationalIdField);
		panel.add(doctorDiplomaIdLabel);
		panel.add(doctorDiplomaIdField);
		panel.add(addDoctorButton);
		panel.add(backButton);
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	private void showAppointmentPanel() {
		mainPanel.remove(buttonPanel);
		mainPanel.remove(scrollPane);
		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

		JLabel patientLabel = new JLabel("Hasta Seç:");
		JComboBox<Patient> patientComboBox = new JComboBox<>(crs.getPatients().values().toArray(new Patient[0]));
		JLabel hospitalLabel = new JLabel("Hastane Seç:");
		JComboBox<Hospital> hospitalComboBox = new JComboBox<>(crs.getHospitals().values().toArray(new Hospital[0]));
		JLabel sectionLabel = new JLabel("Bölüm Seç:");
		JComboBox<Section> sectionComboBox = new JComboBox<>();
		JLabel doctorLabel = new JLabel("Doktor Seç:");
		JComboBox<Doctor> doctorComboBox = new JComboBox<>();
		JLabel desiredDateLabel = new JLabel("Randevu Tarihi (GG-AA-YYYY):");
		JTextField desiredDateField = new JTextField(20);
		JButton makeAppointmentButton = new JButton("Randevu Al");
		JButton backButton = new JButton("Geri");

		hospitalComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sectionComboBox.removeAllItems();
				doctorComboBox.removeAllItems();
				Hospital selectedHospital = (Hospital) hospitalComboBox.getSelectedItem();
				if (selectedHospital != null) {
					for (Section s : selectedHospital.listSections()) {
						sectionComboBox.addItem(s);
					}
				}
			}
		});
		sectionComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doctorComboBox.removeAllItems();
				Section selectedSection = (Section) sectionComboBox.getSelectedItem();
				if (selectedSection != null) {
					for (Doctor d : selectedSection.listDoctors()) {
						doctorComboBox.addItem(d);
					}
				}
			}
		});
		makeAppointmentButton.addActionListener(e -> {
			try {
				Patient selectedPatient = (Patient) patientComboBox.getSelectedItem();
				Hospital selectedHospital = (Hospital) hospitalComboBox.getSelectedItem();
				Section selectedSection = (Section) sectionComboBox.getSelectedItem();
				Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date desiredDate = dateFormat.parse(desiredDateField.getText());

				if (selectedHospital != null && selectedSection != null && selectedDoctor != null
						&& selectedPatient != null) {
					boolean appointmentSuccessful = crs.makeRandezvous(selectedPatient.getNationalId(),
							selectedHospital.getId(), selectedSection.getId(), selectedDoctor.getDiplomaId(),
							desiredDate);
					if (appointmentSuccessful)
						resultArea.append("Randevu oluşturuldu.\n");
					else
						resultArea.append("Randevu oluşturulamadı. Doktor o gün dolu.\n");
					desiredDateField.setText("");

				} else {
					resultArea.append("Hata: Lütfen geçerli bir seçim yapınız.\n");
				}
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen sayısal değerleri kontrol edin.\n");
				System.err.println("Hata: Lütfen sayısal değerleri kontrol edin. " + ex.getMessage());
			} catch (ParseException ex) {
				resultArea.append("Hata: Lütfen tarih formatını doğru girin (GG-AA-YYYY).\n");
				System.err.println("Hata: Lütfen tarih formatını doğru girin (GG-AA-YYYY). " + ex.getMessage());
			} catch (IDException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
				System.err.println("Hata: " + ex.getMessage());
			} catch (Exception ex) {
				resultArea.append("Hata: Bilinmeyen bir hata oluştu.\n");
				System.err.println("Hata: Bilinmeyen bir hata oluştu." + ex.getMessage());
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();
			}
		});

		panel.add(patientLabel);
		panel.add(patientComboBox);
		panel.add(hospitalLabel);
		panel.add(hospitalComboBox);
		panel.add(sectionLabel);
		panel.add(sectionComboBox);
		panel.add(doctorLabel);
		panel.add(doctorComboBox);
		panel.add(desiredDateLabel);
		panel.add(desiredDateField);
		panel.add(makeAppointmentButton);
		panel.add(backButton);
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	private void showSystemInfo() {
		resultArea.setText("");
		StringBuilder sb = new StringBuilder();
		sb.append("\nHastalar:\n");
		for (Patient p : crs.getPatients().values()) {
			sb.append(p).append("\n");
		}
		sb.append("\nHastaneler:\n");
		for (Hospital hospital : crs.getHospitals().values()) {
			sb.append(hospital).append("\n");
			for (Section section : hospital.listSections()) {
				sb.append("\t").append(section).append("\n");
				for (Doctor doctor : section.listDoctors()) {
					sb.append("\t\t").append(doctor).append("\n");
				}
			}
		}
		sb.append("\nRandevular:\n");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (Rendezvous rendezvous : crs.getRendezvous()) {
			Date date = rendezvous.getDateTime();
			String formattedDate = dateFormat.format(date);
			Doctor doctor = rendezvous.getDoctor();
			Hospital hospital = null;
			Section section = null;

			for (Hospital h : crs.getHospitals().values()) {
				for (Section s : h.listSections()) {
					if (s.getDoctor(doctor.getDiplomaId()) != null) {
						hospital = h;
						section = s;
						break;
					}
				}
				if (hospital != null)
					break;
			}

			sb.append("Randevu tarihi: ").append(formattedDate).append(", hasta adı: ")
					.append(rendezvous.getPatient().getName()).append(", doktor adı: ").append(doctor.getName())
					.append(", hastane adı: ").append(hospital.getName()).append(", bölüm adı: ")
					.append(section != null ? section.getName() : "Bölüm bilgisi bulunamadı").append("\n");
		}
		resultArea.append(sb.toString());
		showMainPanel();
	}

}