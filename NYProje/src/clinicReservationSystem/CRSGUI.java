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
import java.util.Iterator;

public class CRSGUI {

	private CRS crs;
	private JFrame frame;
	private JTextArea resultArea;
	private JFileChooser fileChooser;
	private String filePath;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JScrollPane scrollPane;

	private Font labelFont = new Font("Arial", Font.PLAIN, 14);
	private Font buttonFont = new Font("Arial", Font.BOLD, 14);

	public CRSGUI() {
		this.crs = new CRS();
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
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);

		mainPanel = new JPanel(new BorderLayout());
		resultArea = new JTextArea(10, 50);
		resultArea.setFont(labelFont);
		resultArea.setEditable(false);
		scrollPane = new JScrollPane(resultArea);

		// Dosya Yükleme Bölümü
		JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton loadFileButton = new JButton("Veri Dosyası Yükle");
		loadFileButton.setFont(buttonFont);
		loadFileButton.setBackground(new Color(0x007BFF));
		loadFileButton.setForeground(Color.WHITE);
		loadFileButton.setPreferredSize(new Dimension(150, 30));
		loadFileButton.addActionListener(e -> {
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
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

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// Ana Ekran Butonları
		JButton addPatientButton = new JButton("Hasta Ekle");
		JButton addHospitalButton = new JButton("Hastane Ekle");
		JButton addSectionButton = new JButton("Bölüm Ekle");
		JButton addDoctorButton = new JButton("Doktor Ekle");
		JButton makeAppointmentButton = new JButton("Randevu Al");
		JButton viewSystemButton = new JButton("Sistem Bilgilerini Görüntüle");

		addPatientButton.setFont(buttonFont);
		addHospitalButton.setFont(buttonFont);
		addSectionButton.setFont(buttonFont);
		addDoctorButton.setFont(buttonFont);
		makeAppointmentButton.setFont(buttonFont);
		viewSystemButton.setFont(buttonFont);
		addPatientButton.setBackground(new Color(0x28A745));
		addHospitalButton.setBackground(new Color(0x28A745));
		addSectionButton.setBackground(new Color(0x28A745));
		addDoctorButton.setBackground(new Color(0x28A745));
		makeAppointmentButton.setBackground(new Color(0x007BFF));
		viewSystemButton.setBackground(new Color(0x007BFF));
		addPatientButton.setForeground(Color.WHITE);
		addHospitalButton.setForeground(Color.WHITE);
		addSectionButton.setForeground(Color.WHITE);
		addDoctorButton.setForeground(Color.WHITE);
		makeAppointmentButton.setForeground(Color.WHITE);
		viewSystemButton.setForeground(Color.WHITE);

		addPatientButton.setPreferredSize(new Dimension(200, 50));
		addHospitalButton.setPreferredSize(new Dimension(200, 50));
		addSectionButton.setPreferredSize(new Dimension(200, 50));
		addDoctorButton.setPreferredSize(new Dimension(200, 50));
		makeAppointmentButton.setPreferredSize(new Dimension(200, 50));
		viewSystemButton.setPreferredSize(new Dimension(200, 50));
		addPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addHospitalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addSectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		makeAppointmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewSystemButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		addPatientButton.addActionListener(e -> showPatientPanel());
		addHospitalButton.addActionListener(e -> showHospitalPanel());
		addSectionButton.addActionListener(e -> showSectionPanel());
		addDoctorButton.addActionListener(e -> showDoctorPanel());
		makeAppointmentButton.addActionListener(e -> showAppointmentPanel());
		viewSystemButton.addActionListener(e -> showSystemInfoPanel());

		buttonPanel.add(addPatientButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(addHospitalButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(addSectionButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(addDoctorButton);
		buttonPanel.add(Box.createVerticalStrut(10));
		buttonPanel.add(makeAppointmentButton);
		buttonPanel.add(Box.createVerticalStrut(10));
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
					} catch (IOException ex) {
						resultArea.append("Veriler kaydedilemedi.\n");
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
		JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton loadFileButton = new JButton("Veri Dosyası Yükle");
		loadFileButton.setFont(buttonFont);
		loadFileButton.setBackground(new Color(0x007BFF));
		loadFileButton.setForeground(Color.WHITE);
		loadFileButton.setPreferredSize(new Dimension(150, 30));
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
		mainPanel.removeAll();
		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel patientNameLabel = new JLabel("Hasta Adı:");
		patientNameLabel.setFont(labelFont);
		JTextField patientNameField = new JTextField(20);
		JLabel patientIdLabel = new JLabel("Hasta ID:");
		patientIdLabel.setFont(labelFont);
		JTextField patientIdField = new JTextField(20);
		JButton addPatientButton = new JButton("Hasta Ekle");
		addPatientButton.setFont(buttonFont);
		addPatientButton.setBackground(new Color(0x28A745));
		addPatientButton.setForeground(Color.WHITE);
		JButton backButton = new JButton("Geri");
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color(0x007BFF));
		backButton.setForeground(Color.WHITE);

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
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
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
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JLabel hospitalIdLabel = new JLabel("Hastane ID:");
		hospitalIdLabel.setFont(labelFont);
		JTextField hospitalIdField = new JTextField(20);
		JLabel hospitalNameLabel = new JLabel("Hastane Adı:");
		hospitalNameLabel.setFont(labelFont);
		JTextField hospitalNameField = new JTextField(20);
		JButton addHospitalButton = new JButton("Hastane Ekle");
		addHospitalButton.setFont(buttonFont);
		addHospitalButton.setBackground(new Color(0x28A745));
		addHospitalButton.setForeground(Color.WHITE);
		JButton backButton = new JButton("Geri");
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color(0x007BFF));
		backButton.setForeground(Color.WHITE);

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
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
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
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel hospitalLabel = new JLabel("Hastane Seç:");
		hospitalLabel.setFont(labelFont);
		JComboBox<Hospital> hospitalComboBox = new JComboBox<>(crs.getHospitals().values().toArray(new Hospital[0]));
		JLabel sectionIdLabel = new JLabel("Bölüm ID:");
		sectionIdLabel.setFont(labelFont);
		JTextField sectionIdField = new JTextField(20);
		JLabel sectionNameLabel = new JLabel("Bölüm Adı:");
		sectionNameLabel.setFont(labelFont);
		JTextField sectionNameField = new JTextField(20);
		JLabel sectionMaxPatientPerDayLabel = new JLabel("Bölüm Max Hasta Sayısı:");
		sectionMaxPatientPerDayLabel.setFont(labelFont);
		JTextField sectionMaxPatientPerDayField = new JTextField(20);
		JButton addSectionButton = new JButton("Bölüm Ekle");
		addSectionButton.setFont(buttonFont);
		addSectionButton.setBackground(new Color(0x28A745));
		addSectionButton.setForeground(Color.WHITE);
		JButton backButton = new JButton("Geri");
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color(0x007BFF));
		backButton.setForeground(Color.WHITE);

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
				}
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen geçerli bir Bölüm ID veya Hasta Sayısı girin.\n");
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
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
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel hospitalLabel = new JLabel("Hastane Seç:");
		hospitalLabel.setFont(labelFont);
		JComboBox<Hospital> hospitalComboBox = new JComboBox<>(crs.getHospitals().values().toArray(new Hospital[0]));
		JLabel sectionLabel = new JLabel("Bölüm Seç:");
		sectionLabel.setFont(labelFont);
		JComboBox<Section> sectionComboBox = new JComboBox<>();
		JLabel doctorNameLabel = new JLabel("Doktor Adı:");
		doctorNameLabel.setFont(labelFont);
		JTextField doctorNameField = new JTextField(20);
		JLabel doctorNationalIdLabel = new JLabel("Doktor National ID:");
		doctorNationalIdLabel.setFont(labelFont);
		JTextField doctorNationalIdField = new JTextField(20);
		JLabel doctorDiplomaIdLabel = new JLabel("Doktor Diploma ID:");
		doctorDiplomaIdLabel.setFont(labelFont);
		JTextField doctorDiplomaIdField = new JTextField(20);
		JButton addDoctorButton = new JButton("Doktor Ekle");
		addDoctorButton.setFont(buttonFont);
		addDoctorButton.setBackground(new Color(0x28A745));
		addDoctorButton.setForeground(Color.WHITE);
		JButton backButton = new JButton("Geri");
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color(0x007BFF));
		backButton.setForeground(Color.WHITE);

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
				}
				showMainPanel();
			} catch (NumberFormatException ex) {
				resultArea.append("Hata: Lütfen geçerli bir doktor id veya diploma id girin.\n");
			} catch (DuplicateInfoException ex) {
				resultArea.append("Hata: " + ex.getMessage() + "\n");
			} catch (Exception ex) {
				resultArea.append("Bilinmeyen bir hata oluştu.\n");
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
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel patientLabel = new JLabel("Hasta Seç:");
		patientLabel.setFont(labelFont);
		JComboBox<Patient> patientComboBox = new JComboBox<>(crs.getPatients().values().toArray(new Patient[0]));
		JLabel hospitalLabel = new JLabel("Hastane Seç:");
		hospitalLabel.setFont(labelFont);
		JComboBox<Hospital> hospitalComboBox = new JComboBox<>(crs.getHospitals().values().toArray(new Hospital[0]));
		JLabel sectionLabel = new JLabel("Bölüm Seç:");
		sectionLabel.setFont(labelFont);
		JComboBox<Section> sectionComboBox = new JComboBox<>();
		JLabel doctorLabel = new JLabel("Doktor Seç:");
		doctorLabel.setFont(labelFont);
		JComboBox<Doctor> doctorComboBox = new JComboBox<>();
		JLabel desiredDateLabel = new JLabel("Randevu Tarihi (GG-AA-YYYY):");
		desiredDateLabel.setFont(labelFont);
		JTextField desiredDateField = new JTextField(20);
		JButton makeAppointmentButton = new JButton("Randevu Al");
		makeAppointmentButton.setFont(buttonFont);
		makeAppointmentButton.setBackground(new Color(0x007BFF));
		makeAppointmentButton.setForeground(Color.WHITE);
		JButton backButton = new JButton("Geri");
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color(0x007BFF));
		backButton.setForeground(Color.WHITE);

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
			// THREAD CREATION
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
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
					} catch (ParseException ex) {
						resultArea.append("Hata: Lütfen tarih formatını doğru girin (GG-AA-YYYY).\n");
					} catch (IDException ex) {
						resultArea.append("Hata: " + ex.getMessage() + "\n");
					} catch (Exception ex) {
						resultArea.append("Hata: Bilinmeyen bir hata oluştu.\n");
					}
				}
			}); // THREAD CREATION
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException ex) {
				resultArea.append("Hata : Teknik bir hata oluştu.\n");
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

	private void showSystemInfoPanel() {
		mainPanel.remove(buttonPanel);
		mainPanel.remove(scrollPane);
		JPanel panel = new JPanel(new BorderLayout());
		JButton backButton = new JButton("Geri");
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color(0x007BFF));
		backButton.setForeground(Color.WHITE);
		JTextArea systemInfoTextArea = new JTextArea(20, 70);
		systemInfoTextArea.setEditable(false);
		systemInfoTextArea.setFont(labelFont);
		JScrollPane systemInfoScrollPane = new JScrollPane(systemInfoTextArea);

		Thread systemInfoThread = new Thread(new Runnable() { // THREAD CREATION

			@Override
			public void run() {
				try {
					StringBuilder sb = new StringBuilder();
					sb.append("\nHastalar:\n");
					for (Patient p : crs.getPatients().values()) {
						sb.append("\t").append(p).append("\n");
					}

					sb.append("\nHastaneler:\n");
					for (Hospital hospital : crs.getHospitals().values()) {
						sb.append("\t").append(hospital).append("\n");
						for (Section section : hospital.listSections()) {
							sb.append("\t\t").append(section).append("\n");
							for (Doctor doctor : section.listDoctors()) {
								sb.append("\t\t\t").append(doctor).append("\n");
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

						sb.append("\tRandevu tarihi: ").append(formattedDate).append(",  hasta adı: ")
								.append(rendezvous.getPatient().getName()).append(",\t doktor adı: ")
								.append(doctor.getName()).append(",\t   hastane adı: ").append(hospital.getName())
								.append(",\t bölüm adı: ")
								.append(section != null ? section.getName() : "Bölüm bilgisi bulunamadı").append("\n");
					}
					systemInfoTextArea.setText(sb.toString());
				} catch (Exception e) {
					resultArea.append("Hata : Bilinmeyen bir hata oluştu. Tekrar deneyin.\n");
				}
			}
		}); // THREAD CREATION
		systemInfoThread.start();
		try {
			systemInfoThread.join();
		} catch (InterruptedException ex) {
			resultArea.append("Hata : Teknik bir hata oluştu.\n");
		}
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();
			}
		});
		panel.add(systemInfoScrollPane, BorderLayout.CENTER);
		panel.add(backButton, BorderLayout.SOUTH);
		mainPanel.add(panel, BorderLayout.CENTER);
		frame.setContentPane(mainPanel);
		frame.revalidate();
		frame.repaint();
	}
}