package com.github.antoinejt.ppepersonnel.personnel;

import com.github.antoinejt.ppepersonnel.config.Config;
import com.github.antoinejt.ppepersonnel.storage.JDBC;
import com.github.antoinejt.ppepersonnel.storage.Passerelle;
import com.github.antoinejt.ppepersonnel.storage.Serialization;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Gestion du personnel. Un seul objet de cette classe existe.
 * Il n'est pas possible d'instancier directement cette classe,
 * la méthode {@link #getGestionPersonnel getGestionPersonnel}
 * le fait automatiquement et retourne toujours le même objet.
 * Dans le cas où {@link #sauvegarder()} a été appelé lors
 * d'une exécution précédente, c'est l'objet sauvegardé qui est
 * retourné.
 */
public class GestionPersonnel implements Serializable {
	private static final long serialVersionUID = -105283113987886425L;
	private static final Passerelle passerelle = Config.TYPE_PASSERELLE.toString().equals("JDBC")
			? new JDBC() : new Serialization();
	private static GestionPersonnel gestionPersonnel = null;

	private SortedSet<Ligue> ligues;
	private Employe root = new Employe("root", "", "", "toor", null);

	public GestionPersonnel() {
		if (gestionPersonnel != null)
			throw new IllegalStateException("Vous ne pouvez créer qu'une seule instance de GestionPersonnel.");
		ligues = new TreeSet<>();
	}

	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà.
	 *
	 * @return l'unique objet de type {@link GestionPersonnel}.
	 */
	public static GestionPersonnel getGestionPersonnel() {
		if (gestionPersonnel == null) {
			gestionPersonnel = passerelle.getGestionPersonnel();
		}
		if (gestionPersonnel == null) {
			gestionPersonnel = new GestionPersonnel();
		}
		return gestionPersonnel;
	}

	public void sauvegarder() throws SauvegardeImpossible {
		passerelle.sauvegarderGestionPersonnel(this);
	}

	/**
	 * Retourne la ligue dont administrateur est l'administrateur,
	 * null s'il n'est pas un administrateur.
	 *
	 * @param administrateur l'administrateur de la ligue recherchée.
	 * @return la ligue dont administrateur est l'administrateur.
	 */
	public Ligue getLigue(Employe administrateur) {
		return administrateur.estAdmin(administrateur.getLigue())
				? administrateur.getLigue()
				: null;
	}

	/**
	 * Retourne toutes les ligues enregistrées.
	 *
	 * @return toutes les ligues enregistrées.
	 */

	public SortedSet<Ligue> getLigues() {
		return Collections.unmodifiableSortedSet(ligues);
	}

	public Ligue addLigue(String nom) throws SauvegardeImpossible {
		Ligue ligue = new Ligue(nom);
		ligues.add(ligue);
		return ligue;
	}

	public Ligue addLigue(int id, String nom) {
		Ligue ligue = new Ligue(id, nom);
		ligues.add(ligue);
		return ligue;
	}

	void remove(Ligue ligue) {
		ligues.remove(ligue);
	}

	int insert(Ligue ligue) throws SauvegardeImpossible {
		return passerelle.insert(ligue);
	}

	/**
	 * Retourne le root (super-utilisateur).
	 *
	 * @return le root.
	 */
	public Employe getRoot() {
		return root;
	}
}
