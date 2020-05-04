package com.github.antoinejt.ppepersonnel.personnel;

import java.io.Serializable;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Représente une ligue. Chaque ligue est reliée à une liste
 * d'employés dont un administrateur. Comme il n'est pas possible
 * de créer un employé sans l'affecter à une ligue, le root est
 * l'administrateur de la ligue jusqu'à ce qu'un administrateur
 * lui ait été affecté avec la fonction {@link #setAdministrateur}.
 */
public class Ligue implements Serializable, Comparable<Ligue> {
	private static final long serialVersionUID = 1L;

	private final int id;
	private final SortedSet<Employe> employes = new TreeSet<>();

	private String nom;
	private Employe administrateur;

	/**
	 * Crée une ligue.
	 *
	 * @param nom le nom de la ligue.
	 */
	Ligue(String nom) throws SauvegardeImpossible {
		construct(nom);
		this.id = GestionPersonnel.getGestionPersonnel().insert(this);
	}

	Ligue(int id, String nom) {
		construct(nom);
		this.id = id;
	}

	// this is a little workaround
	private void construct(String nom) {
		this.nom = nom;
		administrateur = GestionPersonnel.getGestionPersonnel().getRoot();
	}

	/**
	 * Retourne le nom de la ligue.
	 *
	 * @return le nom de la ligue.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Change le nom.
	 *
	 * @param nom le nouveau nom de la ligue.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne l'id de la ligue.
	 *
	 * @return l'id de la ligue.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retourne l'administrateur de la ligue.
	 *
	 * @return l'administrateur de la ligue.
	 */
	public Employe getAdministrateur() {
		return administrateur;
	}

	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Lève DroitsInsuffisants si l'administrateur n'est pas
	 * un employé de la ligue ou le root. Révoque les droits de l'ancien
	 * administrateur.
	 *
	 * @param administrateur le nouvel administrateur de la ligue.
	 */
	public void setAdministrateur(Employe administrateur) {
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		if (administrateur != root && administrateur.getLigue() != this) {
			throw new DroitsInsuffisants();
		}
		this.administrateur = administrateur;
	}

	/**
	 * Retourne les employés de la ligue.
	 *
	 * @return les employés de la ligue dans l'ordre alphabétique.
	 */
	public SortedSet<Employe> getEmployes() {
		return Collections.unmodifiableSortedSet(employes);
	}

	/**
	 * Ajoute un employé dans la ligue. Cette méthode
	 * est le seul moyen de créer un employé.
	 *
	 * @param nom      le nom de l'employé.
	 * @param prenom   le prénom de l'employé.
	 * @param mail     l'adresse mail de l'employé.
	 * @param password le password de l'employé.
	 * @return l'employé créé.
	 */
	public Employe addEmploye(String nom, String prenom, String mail, String password) {
		Employe employe = new Employe(nom, prenom, mail, password, this);
		employes.add(employe);
		return employe;
	}

	void remove(Employe employe) {
		employes.remove(employe);
	}

	/**
	 * Supprime la ligue, entraîne la suppression de tous les employés
	 * de la ligue.
	 */
	public void remove() {
		GestionPersonnel.getGestionPersonnel().remove(this);
	}

	@Override
	public int compareTo(Ligue autre) {
		return nom.compareTo(autre.nom);
	}

	@Override
	public String toString() {
		return nom;
	}
}
