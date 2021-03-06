package com.github.antoinejt.ppepersonnel.personnel;

import com.github.antoinejt.ppepersonnel.Utils;

import java.io.Serializable;
import java.util.Date;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé,
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */
public class Employe implements Serializable, Comparable<Employe> {
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom;
	private String prenom;
	private String password;
	private String mail;
	private Ligue ligue;
	private Date arrivee;
	private Date depart;

	Employe(String nom, String prenom, String mail, String password, Date arrivee, Ligue ligue) {
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.arrivee = arrivee;
		this.depart = null;
		this.ligue = ligue;
	}

	Employe(String nom, String prenom, String mail, String password, Ligue ligue) {
		this(nom, prenom, mail, password, null, ligue);
	}

	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue
	 * passée en paramètre.
	 *
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this
	 *              est l'admininstrateur.
	 * @return vrai ssi l'employé est administrateur de la ligue
	 * passée en paramètre.
	 */
	public boolean estAdmin(Ligue ligue) {
		return ligue.getAdministrateur() == this;
	}

	/**
	 * Retourne vrai ssi l'employé est le root.
	 *
	 * @return vrai ssi l'employé est le root.
	 */
	public boolean estRoot() {
		return GestionPersonnel.getGestionPersonnel().getRoot() == this;
	}

	/**
	 * Retourne le nom de l'employé.
	 *
	 * @return le nom de l'employé.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 *
	 * @param nom le nouveau nom.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'employé.
	 *
	 * @return le prénom de l'employé.
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Change le prénom de l'employé.
	 *
	 * @param prenom le nouveau prénom de l'employé.
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Retourne le mail de l'employé.
	 *
	 * @return le mail de l'employé.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Change le mail de l'employé.
	 *
	 * @param mail le nouveau mail de l'employé.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Retourne la date d'arrivée de l'employé.
	 *
	 * @return la date d'arrivée de l'employé.
	 */
	public Date getDateArrivee() {
		return arrivee;
	}

	/**
	 * Change la date d'arrivée de l'employé.
	 *
	 * @param arrivee la nouvelle date d'arrivée de l'employé.
	 */
	public void setDateArrivee(Date arrivee) {
		this.arrivee = arrivee;
	}

	/**
	 * Retourne la date de départ de l'employé.
	 *
	 * @return la date de départ de l'employé.
	 */
	public Date getDateDepart() {
		return depart;
	}

	/**
	 * Change la date de départ de l'employé.
	 *
	 * @param depart la nouvelle date de départ de l'employé.
	 */
	public void setDateDepart(Date depart) {
		this.depart = depart;
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 *
	 * @param password le password auquel comparer celui de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 */
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 *
	 * @param password le nouveau password de l'employé.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 *
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	public Ligue getLigue() {
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 */
	public void remove() {
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		if (this == root) {
			throw new ImpossibleDeSupprimerRoot();
		}
		if (estAdmin(ligue)) {
			ligue.setAdministrateur(root);
		}
		ligue.remove(this);
	}

	@Override
	public int compareTo(Employe autre) {
		int cmp = getNom().compareTo(autre.getNom());
		return (cmp != 0)
				? cmp
				: getPrenom().compareTo(autre.getPrenom());
	}

	@Override
	public String toString() {
		String ligueStr = estRoot() ? "super-utilisateur" : ligue.toString();
		String res = String.format("%s %s %s (%s)", nom, prenom, mail, ligueStr);

		if (arrivee != null) {
			String arriveeStr = Utils.dateFormatter.format(arrivee);
			String departStr = (depart != null) ? Utils.dateFormatter.format(depart) : "?";

			res += String.format(" %s - %s", arriveeStr, departStr);
		}
		return res;
	}
}
