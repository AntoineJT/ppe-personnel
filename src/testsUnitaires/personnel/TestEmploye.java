package personnel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestEmploye {
	private Ligue createLigueTirArc() 
	{
		return new Ligue("Tir à l'arc");
	}
	
	private Employe createKarlo(Ligue ligue)
	{
		return ligue.addEmploye("Karlo", "Jean-Eude", "jeaneude.karlo@karlo.com", "KarloIsTheBest");
	}
	
	@Test
	void testEstRoot() 
	{
		Employe employe = createKarlo(createLigueTirArc());
		
		assertFalse(employe.estRoot());
		assertTrue(GestionPersonnel.getGestionPersonnel().getRoot().estRoot());
	}
	
	@Test
	void testEstAdmin()
	{
		Ligue ligue = createLigueTirArc();
		Employe employe = createKarlo(ligue);
		Ligue ligueEscrime = new Ligue("Escrime");
		
		ligue.setAdministrateur(employe);
		assertTrue(employe.estAdmin(ligue));
		
		assertFalse(employe.estAdmin(ligueEscrime));
	}
	
	@Test
	void testCheckPassword()
	{
		Employe employe = createKarlo(createLigueTirArc());
		
		assertTrue(employe.checkPassword("KarloIsTheBest"));
		assertFalse(employe.checkPassword("lulz"));
	}
	
	@Test
	void testMailGetter()
	{
		Employe employe = createKarlo(createLigueTirArc());
		assertEquals(employe.getMail(), "jeaneude.karlo@karlo.com");
	}
	
	@Test
	void testSetters()
	{
		Employe employe = new Employe(createLigueTirArc(), "Abandonné", "Déchet", "poubelle@trash.org", "ptdr");
		
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
		Employe employe = createKarlo(createLigueTirArc());
		
		assertEquals(employe.toString(), "Karlo Jean-Eude jeaneude.karlo@karlo.com (Tir à l'arc)");
		assertEquals(root.toString(), root.getNom() + " " + root.getPrenom() + " " + root.getMail() + " (super-utilisateur)");
	}
	
	@Test
	void testRemove()
	{
		Ligue ligue = createLigueTirArc();
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
	
	@Test
	void testCompareTo()
	{
		Ligue ligue = createLigueTirArc();
		Employe employe = createKarlo(ligue);
		Employe employe2 = ligue.addEmploye("Javell", "Francis", "francisj@wanadoo.fr", "S3cur3");
		
		assertTrue(employe.compareTo(employe) == 0);
		assertFalse(employe.compareTo(employe2) == 0);
	}
}
