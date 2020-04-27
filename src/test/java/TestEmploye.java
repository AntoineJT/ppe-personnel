import static org.junit.jupiter.api.Assertions.*;

import com.github.antoinejt.ppepersonnel.Utils;
import org.junit.jupiter.api.Test;

import com.github.antoinejt.ppepersonnel.personnel.Employe;
import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.ImpossibleDeSupprimerRoot;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;

import java.text.ParseException;
import java.util.Date;

class TestEmploye {
	private Ligue createLigueTirArc() 
	{
        return TestUtils.createLigue("Tir à l'arc");
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
		Ligue ligueEscrime = TestUtils.createLigue("Escrime");
		assertNotNull(ligueEscrime);
		
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
		assertEquals("jeaneude.karlo@karlo.com", employe.getMail());
	}
	
	@Test
	void testSetters()
	{
	    Employe employe = createLigueTirArc().addEmploye("Abandonné", "Déchet", "poubelle@trash.org", "ptdr");
		
		employe.setMail("hello@world.org");
		assertEquals("hello@world.org", employe.getMail());
		
		employe.setNom("Vilo");
		assertEquals("Vilo", employe.getNom());
		
		employe.setPrenom("Lucien");
		assertEquals("Lucien", employe.getPrenom());
		
		employe.setPassword("MotDePasse");
		assertTrue(employe.checkPassword("MotDePasse"));
	}
	
	@Test
	void testToString()
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		Employe employe = createKarlo(createLigueTirArc());
		
		assertEquals("Karlo Jean-Eude jeaneude.karlo@karlo.com (Tir à l'arc)", employe.toString());
		assertEquals(root.getNom() + " " + root.getPrenom() + " " + root.getMail() + " (super-utilisateur)", root.toString());
	}
	
	@Test
	void testRemove()
	{
		Ligue ligue = createLigueTirArc();
		Employe employe = ligue.addEmploye("Paskal", "Lucien", "letesteurunitaire@codeur.org", "TesterCEstDouterCorrigerCEstAbdiquer");
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		Employe admin = ligue.addEmploye("Charles", "Guy", "guycharles@cookie.org", "VivLéKouki");
		
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
		
		ligue.setAdministrateur(admin);
		admin.remove();
		assertFalse(ligue.getEmployes().contains(admin));
		
		assertEquals(root, ligue.getAdministrateur());
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> root.remove());
	}
	
	@Test
	void testCompareTo()
	{
		Ligue ligue = createLigueTirArc();
		Employe employe = createKarlo(ligue);
		Employe employe2 = ligue.addEmploye("Javell", "Francis", "francisj@wanadoo.fr", "S3cur3");
		
		assertEquals(0, employe.compareTo(employe));
		assertNotEquals(0, employe.compareTo(employe2));
	}

	@Test
	void testDates()
	{
		// TODO test getter, setter + toString

		Ligue ligue = createLigueTirArc();
		Employe employe = createKarlo(ligue);

		Date date = null;

		try {
			date = Utils.dateFormatter.parse("12/12/2003");
		} catch (ParseException e) {
			fail("La date spécifiée est invalide !");
		}

		employe.setDateArrivee(date);
		assertEquals(employe.getDateArrivee(), date);

		assertEquals(employe.toString(), "Karlo Jean-Eude jeaneude.karlo@karlo.com (Tir à l'arc) 12/12/2003 - ?");

		employe.setDateDepart(date);
		assertEquals(employe.getDateDepart(), date);

		assertEquals(employe.toString(), "Karlo Jean-Eude jeaneude.karlo@karlo.com (Tir à l'arc) 12/12/2003 - 12/12/2003");

		employe.setDateArrivee(null);
		assertNull(employe.getDateArrivee());

		assertEquals(employe.toString(), "Karlo Jean-Eude jeaneude.karlo@karlo.com (Tir à l'arc)");
	}
}
