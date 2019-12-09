package personnel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestEmploye {
	private static Ligue ligue = new Ligue("Tir à l'arc");
	private static Employe employe = ligue.addEmploye("Karlo", "Jean-Eude", "jeaneude.karlo@karlo.com", "KarloIsTheBest");
	
	@Test
	void testEstRoot() 
	{
		assertFalse(employe.estRoot());
		assertTrue(GestionPersonnel.getGestionPersonnel().getRoot().estRoot());
	}
	
	@Test
	void testEstAdmin()
	{
		ligue.setAdministrateur(employe);
		Ligue ligueEscrime = new Ligue("Escrime");
		assertTrue(employe.estAdmin(ligue));
		assertFalse(employe.estAdmin(ligueEscrime));
	}
	
	@Test
	void testCheckPassword()
	{
		assertTrue(employe.checkPassword("KarloIsTheBest"));
		assertFalse(employe.checkPassword("lulz"));
	}
	
	@Test
	void testMailGetter()
	{
		assertEquals(employe.getMail(), "jeaneude.karlo@karlo.com");
	}
	
	@Test
	void testSetters()
	{
		Employe employe = new Employe(ligue, "Abandonné", "Déchet", "poubelle@trash.org", "ptdr");
		
		employe.setMail("hello@world.org");
		assertEquals(employe.getMail(), "hello@world.org");
		
		employe.setNom("Vilo");
		assertEquals(employe.getNom(), "Vilo");
		
		employe.setPrenom("Lucien");
		assertEquals(employe.getPrenom(), "Lucien");
		
		employe.setPassword("MotDePasse");
		assertTrue(employe.checkPassword("MotDePasse"));
	}
	
	@Test
	void testToString()
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		assertEquals(employe.toString(), "Karlo Jean-Eude jeaneude.karlo@karlo.com (Tir à l'arc)");
		assertEquals(root.toString(), root.getNom() + " " + root.getPrenom() + " " + root.getMail() + " (super-utilisateur)");
	}
	
	// TODO Virer les effets de bords!!
	@Test
	void testRemove()
	{
		Employe employe = new Employe(ligue, "Paskal", "Lucien", "letesteurunitaire@codeur.org", "TesterCEstDouterCorrigerCEstAbdiquer");
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
		
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		
		Employe admin = ligue.addEmploye("Charles", "Guy", "guycharles@cookie.org", "VivLéKouki");
		ligue.setAdministrateur(admin);
		admin.remove();
		assertFalse(ligue.getEmployes().contains(admin));
		assertEquals(ligue.getAdministrateur(), root);
		
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> { root.remove(); });
	}
}
