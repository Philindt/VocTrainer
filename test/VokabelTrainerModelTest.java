import static org.junit.Assert.*;

import org.junit.Test;

import model.WoerterBuch;
import model.Wort;

/**
 * @author Christian Pollok
 * @author Lukas Maier
 * @author Philipp Lindt
 * 
 */
public class VokabelTrainerModelTest {

	WoerterBuch buch = new WoerterBuch();
	Wort wort = new Wort("Katze", "Cat");

	@Test
	public void testWortHinzufuegen() {
		try {
			assertFalse(buch.wortHinzufuegen("", "Flasche"));
			assertTrue(buch.wortHinzufuegen("Fenster", "window"));
			assertFalse(buch.wortHinzufuegen("Hund", "Dog"));
		} catch (Exception e) {
			fail("Shouldn't have thrown " + e);
		}
	}

	@Test
	public void testNeuesWort() {
		try {
			assertEquals("Katze", buch.neuesWort(wort, 0));
			assertEquals("Cat", buch.neuesWort(wort, 1));
		} catch (Exception e) {
			fail("Shouldn't have thrown " + e);
		}
	}

	@Test
	public void testUebersetze() {
		try {
			assertTrue(buch.uebersetze(wort, "Katze"));
			assertTrue(buch.uebersetze(wort, "Cat"));
			assertFalse(buch.uebersetze(wort, "Dog"));
		} catch (Exception e) {
			fail("Shouldn't have thrown " + e);
		}
	}

}
