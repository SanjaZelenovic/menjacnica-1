package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {

	private static MenjacnicaGUI menjacnicaGUI;
	private static MenjacnicaInterface menjacnica;
	
	private static JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica = new Menjacnica();
					menjacnicaGUI = new MenjacnicaGUI();
					menjacnicaGUI.setVisible(true);
					menjacnicaGUI.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
//	public GUIKontroler() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(contentPane,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaGUI.prikaziSveValute(menjacnica.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(contentPane);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	static public void prikaziIzvrsiZamenuGUI(Valuta valuta) {
		if (valuta != null) {
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(valuta);
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}

	public static void prikaziObrisiKursGUI(Valuta valuta) {
		if (valuta != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(valuta);
			prozor.setLocationRelativeTo(contentPane);
			prozor.setVisible(true);
		}
	}

	static public void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(contentPane);
		prozor.setVisible(true);
	}

	static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(contentPane,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, String prodajni, String kupovni, String srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			menjacnicaGUI.prikaziSveValute(menjacnica.vratiKursnuListu());
			
			//Zatvaranje DodajValutuGUI prozora
			//dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			menjacnicaGUI.prikaziSveValute(menjacnica.vratiKursnuListu());
			// dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(contentPane, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
}
