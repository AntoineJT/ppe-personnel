package com.github.antoinejt.ppepersonnel.commandline;

import com.github.antoinejt.ppepersonnel.Utils;
import com.github.antoinejt.ppepersonnel.personnel.Employe;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import java.text.ParseException;
import java.util.Date;

import static commandLineMenus.rendering.examples.util.InOut.getString;

public class EmployeConsole {
	private Option afficher(final Employe employe) {
		return new Option("Afficher l'employé", "l", () -> System.out.println(employe));
	}

	ListOption<Employe> editerEmploye() {
		return this::editerEmploye;
	}

	Option editerEmploye(Employe employe) {
		Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
		menu.add(afficher(employe));
		menu.add(changerNom(employe));
		menu.add(changerPrenom(employe));
		menu.add(changerMail(employe));
		menu.add(changerPassword(employe));
		menu.add(changerDateArrivee(employe));
		menu.add(changerDateDepart(employe));
		menu.add(supprimer(employe));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Employe employe) {
		return new Option("Changer le nom", "n", () -> employe.setNom(getString("Nouveau nom : ")));
	}

	private Option changerPrenom(final Employe employe) {
		return new Option("Changer le prénom", "p", () -> employe.setPrenom(getString("Nouveau prénom : ")));
	}

	private Option changerMail(final Employe employe) {
		return new Option("Changer le mail", "e", () -> employe.setMail(getString("Nouveau mail : ")));
	}

	private Option changerPassword(final Employe employe) {
		return new Option("Changer le password", "x", () -> employe.setPassword(getString("Nouveau mot de passe : ")));
	}

	private void printDateFormat() {
		System.out.println("La date est au format JJ/MM/YYYY");
	}

	private Date getDate(String type) {
		Date date = null;
		do {
			try {
				date = Utils.dateFormatter.parse(getString(String.format("Date %s au format JJ/MM/YYYY : ", type)));
			} catch (ParseException e) {
				System.err.println("Date invalide !");
			}
		} while (date == null);
		return date;
	}

	private Option changerDateArrivee(final Employe employe) {
		return new Option("Changer la date d'arrivée", "a", () -> {
			printDateFormat();
			employe.setDateArrivee(getDate("d'arrivée"));
		});
	}

	private Option changerDateDepart(final Employe employe) {
		return new Option("Changer la date de départ", "d", () -> {
			printDateFormat();
			employe.setDateDepart(getDate("de départ"));
		});
	}

	private Option supprimer(final Employe employe) {
		return new Option("Supprimer", "s", employe::remove);
	}
}
