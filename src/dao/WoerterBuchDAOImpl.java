package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.WoerterBuch;

/**
 * @author Christian Pollok
 * @author Lukas Maier
 * @author Philipp Lindt
 * 
 */
public class WoerterBuchDAOImpl implements WoerterBuchDAO {

	private File file;
	
	public WoerterBuchDAOImpl(String fileName) {
		this.file = new File(fileName);
	}

	@Override
	public WoerterBuch createBuch() throws IOException {
		if (!file.exists()) {
			FileOutputStream out = new FileOutputStream(file);
			ObjectOutputStream obout = new ObjectOutputStream(out);
			WoerterBuch buch = new WoerterBuch();
			obout.writeObject(buch);
			obout.close();
			return buch;
		} else {
			throw new IOException("Woerterbuch existiert bereits");
		}
	}

	@Override
	public WoerterBuch readBuch() throws IOException {
		if (!file.exists()) {
			throw new IOException("W�rterbuch existiert nicht");
		} else {
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream obin = new ObjectInputStream(in);
			WoerterBuch buch = null;

			try {
				buch = (WoerterBuch) obin.readObject();
			} catch (ClassNotFoundException e) {
				throw new IOException("File contains no Woerterbuch");
			}
			if (buch == null)
				throw new IOException("File is empty");
			return buch;
		}
	}

	@Override
	public void updateBuch(WoerterBuch buch) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream obout = new ObjectOutputStream(out);
		obout.writeObject(buch);
		obout.close();
	}

}
