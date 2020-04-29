package com.github.antoinejt.ppepersonnel.commandline;

import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.SauvegardeImpossible;
import commandLineMenus.*;
import static commandLineMenus.rendering.examples.util.InOut.*;

public class PersonnelConsole
{
	private GestionPersonnel gestionPersonnel;
	private LigueConsole ligueConsole;
	private EmployeConsole employeConsole;
	
	public PersonnelConsole(GestionPersonnel gestionPersonnel)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = new EmployeConsole();
		this.ligueConsole = new LigueConsole(gestionPersonnel, employeConsole);
	}
	
	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel des ligues");
		menu.add(employeConsole.editerEmploye(gestionPersonnel.getRoot()));
		menu.add(ligueConsole.menuLigues());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						gestionPersonnel.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (SauvegardeImpossible unused)
					{
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
			);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}
	
	private boolean verifiePassword()
	{
		boolean ok = gestionPersonnel.getRoot().checkPassword(getString("Mot de passe : "));
		if (!ok)
			System.out.println("Mot de passe incorrect.");
		return ok;
	}
	
	public static void main(String[] args)
	{
		PersonnelConsole personnelConsole = new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
		if (personnelConsole.verifiePassword())
			personnelConsole.start();
	}
}
