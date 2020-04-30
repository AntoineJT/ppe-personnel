package com.github.antoinejt.ppepersonnel.storage;

import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import com.github.antoinejt.ppepersonnel.personnel.SauvegardeImpossible;

public interface Passerelle {
	GestionPersonnel getGestionPersonnel();

	void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible;

	int insert(Ligue ligue) throws SauvegardeImpossible;
}
