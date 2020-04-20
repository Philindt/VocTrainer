package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Christian Pollok
 * @author Lukas Maier
 * @author Philipp Lindt
 * 
 */
public class WoerterBuch implements Serializable{

	private List<Wort> buch;

	public WoerterBuch() {
		buch = new LinkedList<>();
		vokabelnGenerieren();
	}

	/**
	 * F�gt Wort der Liste hinzu, pr�ft davor, ob das Wort bereits vorhanden ist
	 * @param german
	 * @param english
	 * @return true falls Wort hinzugef�gt wurde, ansonsten false
	 */
	public boolean wortHinzufuegen(String german, String english) {
		if (german.length() <= 1 || english.length() <= 1) {
			return false;
		} else {
			Wort tempWort = new Wort(german, english);
			for (Wort wort : buch) {
				if (wort.equals(tempWort)) {
					return false;
				}
			}
			buch.add(tempWort);
			return true;
		}
	}

	/**
	 * Zuf�lliges Objekt der Klasse Wort wird f�r die �bersetzung erzeugt
	 * @return Wort
	 */
	public Wort zufaelligesWort() {
		Random random = new Random();
		Wort wort = buch.get(random.nextInt(buch.size()));
		return wort;
	}
	
	/**
	 * gibt zur �bersetzung entweder das englische oder das deutsche Wort zur�ck
	 * @param wort
	 * @param index
	 * @return String deutsches oder englisches Wort
	 */
	public String neuesWort(Wort wort, int index) {
		if(index==0) {
			return wort.getGerman();
		} else {
			return wort.getEnglish();
		}
	}

	/**
	 * Pr�ft ob die �bersetzung richtig oder falsch ist
	 * @param wort
	 * @param s
	 * @return true oder false
	 */
	public boolean uebersetze(Wort wort, String s) {
		if (s.equals(wort.getEnglish())) {
			return true;
		} else if (s.equals(wort.getGerman())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Generiert ein Standardvokabular
	 */
	private void vokabelnGenerieren() {
		this.wortHinzufuegen("Katze", "Cat");
		this.wortHinzufuegen("Hund", "Dog");
		this.wortHinzufuegen("Tier", "Animal");
		this.wortHinzufuegen("Vogel", "Bird");
		this.wortHinzufuegen("Biene", "Bee");
		this.wortHinzufuegen("Haus", "House");
		this.wortHinzufuegen("Wasser", "Water");
		this.wortHinzufuegen("Lampe", "Lamp");
		this.wortHinzufuegen("Bleistift", "Pencil");
	}
	
}
