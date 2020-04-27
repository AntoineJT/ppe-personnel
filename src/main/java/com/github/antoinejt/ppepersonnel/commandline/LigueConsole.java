package com.github.antoinejt.ppepersonnel.commandline;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import com.github.antoinejt.ppepersonnel.personnel.Employe;
import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import com.github.antoinejt.ppepersonnel.personnel.SauvegardeImpossible;
import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> System.out.println(gestionPersonnel.getLigues()));
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", () -> System.out.println(
				ligue + "\n" +
				"administrée par " + ligue.getAdministrateur())
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> System.out.println(ligue.getEmployes()));
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () ->
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> ligue.setNom(getString("Nouveau nom : ")));
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<>("Sélectionner une ligue", "e",
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				this::editerLigue
		);
	}

	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<>("Sélectionner un employé", "e",
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye());
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("Ajouter un employé", "a",
				() ->
					ligue.addEmploye(getString("nom : "), 
						getString("prenom : "),
						getString("mail : "),
						getString("password : "))
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}

	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return new List<>("Changer l'administrateur", "c",
				() -> {
					java.util.List<Employe> list = new ArrayList<>(ligue.getEmployes());
					list.add(gestionPersonnel.getRoot());
					return list;
				},
				(index, element) -> ligue.setAdministrateur(element));
	}
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", ligue::remove);
	}
}
