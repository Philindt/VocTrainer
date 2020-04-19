package model;

import java.io.Serializable;

/**
 * @author Christian Pollok
 * @author Lukas Maier
 * @author Philipp Lindt
 * 
 */
public class Wort implements Serializable {

	private String german;
	private String english;
	
	public Wort(String german, String english) {
		this.german = german;
		this.english = english;
	}

	public String getGerman() {
		return german;
	}

	public String getEnglish() {
		return english;
	}
	
	public boolean equals(Wort wort) {
		if (this.getGerman().equals(wort.getGerman()) && this.getEnglish().equals(wort.getEnglish())) {
			return true;
		} else {
			return false;
		}
	}

}
