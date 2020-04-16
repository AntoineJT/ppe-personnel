package personnel;

public interface Passerelle 
{
	GestionPersonnel getGestionPersonnel();
	void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	int insert(Ligue ligue) throws SauvegardeImpossible;
}
