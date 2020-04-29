import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import com.github.antoinejt.ppepersonnel.personnel.SauvegardeImpossible;

public class TestUtils {
	public static GestionPersonnel personnel = GestionPersonnel.getGestionPersonnel();

	public static Ligue createLigue(String nom) {
		try {
			return personnel.addLigue(nom);
		} catch (SauvegardeImpossible e) {
			e.printStackTrace();
		}
		return null;
	}
}
