import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class TestUtils {
    public static GestionPersonnel personnel = GestionPersonnel.getGestionPersonnel();
    
    public static Ligue createLigue(String nom) 
    {
        try {
            return personnel.addLigue(nom);
        } catch (SauvegardeImpossible e) {
            e.printStackTrace();
        }
        return null;
    }
}
