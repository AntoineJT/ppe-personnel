import com.github.antoinejt.ppepersonnel.personnel.DroitsInsuffisants;
import com.github.antoinejt.ppepersonnel.personnel.Employe;
import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLigue {
	private static GestionPersonnel personnel = TestUtils.personnel;

	private Ligue createLigueFlechette() {
		return TestUtils.createLigue("Fléchettes");
	}

	private Employe createBouchard(Ligue ligue) {
		return ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
	}

	@Test
	void testCreateLigue() {
		assertEquals("Fléchettes", createLigueFlechette().getNom());
	}

	@Test
	void testAddEmploye() {
		Ligue ligue = createLigueFlechette();
		assertEquals(createBouchard(ligue), ligue.getEmployes().first());
	}

	@Test
	void testSetAdministrateur() {
		Ligue ligue = createLigueFlechette();
		Employe employe = createBouchard(ligue);
		Employe employe2 = ligue.addEmploye("Toto", "Toto", "fzarf@test.com", "azerty1234");

		assertEquals(GestionPersonnel.getGestionPersonnel().getRoot(), ligue.getAdministrateur());

		ligue.setAdministrateur(employe);
		assertEquals(ligue.getAdministrateur(), employe);
		assertTrue(employe.estAdmin(ligue));

		ligue.setAdministrateur(employe2);
		assertEquals(ligue.getAdministrateur(), employe2);
		assertTrue(employe2.estAdmin(ligue));
		assertFalse(employe.estAdmin(ligue));

		assertThrows(DroitsInsuffisants.class, () -> TestUtils.createLigue("Escrime").setAdministrateur(employe));
	}

	@Test
	void testSetNom() {
		Ligue ligue = createLigueFlechette();
		ligue.setNom("Koala");
		assertEquals("Koala", ligue.getNom());
	}

	@Test
	void testRemove() {
		Ligue ligue = createLigueFlechette();
		ligue.remove();
		assertFalse(personnel.getLigues().contains(ligue));
	}
}
