package personnel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestEmploye {

	@Test
	void testEstRoot() 
	{
		Ligue ligue = new Ligue("Tir à l'arc");
		Employe employe = ligue.addEmploye( "Karlo", "Jean-Eude", "jeaneude.karlo@karlo.com", "KarloIsTheBest");
		assertFalse(employe.estRoot());
		assertTrue(GestionPersonnel.getGestionPersonnel().getRoot().estRoot());
	}
	
	@Test
	void testEstAdmin()
	{
		
	}
}
