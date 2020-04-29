import com.github.antoinejt.ppepersonnel.personnel.Employe;
import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestGestionPersonnel {
	private static GestionPersonnel personnel = TestUtils.personnel;

	@Test
	void testGetLigue() {
		Ligue ligue = TestUtils.createLigue("Batteur de saumon en Alaska");
		Employe employe = ligue.addEmploye("Grolask", "Jean-Charles", "jc.grolask@alaska.us", "jellybelly");

		assertNull(personnel.getLigue(employe));

		ligue.setAdministrateur(employe);
		assertEquals(ligue, personnel.getLigue(employe));
	}
	
	/*
	@Test
	void testConstructor()
	{
		try {
			Constructor<GestionPersonnel> constructor = GestionPersonnel.class.getDeclaredConstructor(new Class[] {});
			constructor.setAccessible(true);
			GestionPersonnel gestionPersonnel = constructor.newInstance((Object[]) null);
			assertTrue(gestionPersonnel.getLigues().isEmpty());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("The specified constructor can't be found!");
		}
	}
	
	@Test
	void testSauvegarder()
	{
		try {
			personnel.sauvegarder();
		} catch (SauvegardeImpossible e) {
			fail();
		}
		
		try {
			Field serializedFile = Serialization.class.getDeclaredField("FILE_NAME");
			serializedFile.setAccessible(true);
			String value = (String) serializedFile.get(serializedFile);
			
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(serializedFile, serializedFile.getModifiers() & ~Modifier.FINAL);
			
			serializedFile.set(null, "THISFILEDOESNTEXISTS");
			
			assertThrows(SauvegardeImpossible.class, () -> personnel.sauvegarder());
			
			serializedFile.set(serializedFile, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			fail();
		}
	}
	*/
}
