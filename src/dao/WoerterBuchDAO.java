package dao;

import java.io.IOException;
import model.WoerterBuch;

/**
 * The interface represents a DAO for a WörterBuch.
 * Implementations are able to create, read, update
 * (CRUD) exactly ONE store (in a file).
 */
public interface WoerterBuchDAO {
	/* 
	 * Create a new store in the file.
	 * If the store file already exists throw IOException,
	 * otherwise create file, persist a new store and return the store.
	 */
	WoerterBuch createBuch() throws IOException;
	
	/* 
	 * Read the persisted store from the file. 
	 * If no store can be loaded or if no store file exists throw IOException.
	 * Otherwise return persisted store.
	 */
	WoerterBuch readBuch() throws IOException;
	
	/*
	 * Save current store to file.
	 * Ignore existing content of the file and overwrite it.
	 * Throw IOException if there is no store file.
	 */
	void updateBuch(WoerterBuch buch) throws IOException;
	

}
