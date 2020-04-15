package personnel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class TestGestionPersonnel {
	private GestionPersonnel personnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void testGetLigue()
	{
		Ligue ligue = new Ligue("Batteur de saumon en Alaska");
		Employe employe = ligue.addEmploye("Grolask", "Jean-Charles", "jc.grolask@alaska.us", "jellybelly");
		
		assertNull(personnel.getLigue(employe));
		
		ligue.setAdministrateur(employe);
		assertEquals(ligue, personnel.getLigue(employe));
	}
	
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
	
	/*
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
