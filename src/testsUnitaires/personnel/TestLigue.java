package personnel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TestLigue 
{	
	private Ligue createLigueFlechette()
	{
		return new Ligue("Fléchettes");
	}
	
	private Employe createBouchard(Ligue ligue)
	{
		return ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
	}
	
	@Test
	void testCreateLigue() 
	{
		assertEquals("Fléchettes", createLigueFlechette().getNom());
	}

	@Test
	void testAddEmploye() 
	{ 
		Ligue ligue = createLigueFlechette();
		assertEquals(createBouchard(ligue), ligue.getEmployes().first());
	}
	
	@Test
	void testSetAdministrateur()
	{
		Ligue ligue = createLigueFlechette();
		Employe employe = createBouchard(ligue);
		
		ligue.setAdministrateur(employe);
		assertEquals(ligue.getAdministrateur(), employe);
		
		assertThrows(DroitsInsuffisants.class, () -> new Ligue("Escrime").setAdministrateur(employe));
	}
	
	@Test
	void testSetNom()
	{
		Ligue ligue = createLigueFlechette();
		ligue.setNom("Koala");
		assertEquals("Koala", ligue.getNom());
	}
}
