package personnel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TestLigue 
{
	private static Ligue ligue = new Ligue("Fléchettes");
	private static Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
	
	@Test
	void createLigue() 
	{
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() 
	{ 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void setAdministrateur()
	{
		// Employe employe = new Employe(ligueFlechettes, "Karlo", "Jean-Eude", "jeaneude.karlo@karlo.com", "KarloIsTheBest");
		ligue.setAdministrateur(employe);
		assertEquals(ligue.getAdministrateur(), employe);
		
		assertThrows(DroitsInsuffisants.class, () -> new Ligue("Escrime").setAdministrateur(employe));
	}
	
	// TODO Fix it!
	@Test
	void setNom()
	{
		Ligue ligueEscrime = new Ligue("Escrime");
		ligueEscrime.setNom("Koala");
		assertEquals("Koala", ligueEscrime.getNom());
	}
	
	
	
}
