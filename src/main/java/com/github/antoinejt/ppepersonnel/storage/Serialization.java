package com.github.antoinejt.ppepersonnel.storage;

import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import com.github.antoinejt.ppepersonnel.personnel.SauvegardeImpossible;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization implements Passerelle {
	private static final String FILE_NAME = "GestionPersonnel.srz";

	@Override
	public GestionPersonnel getGestionPersonnel() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			return (GestionPersonnel) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Sauvegarde le gestionnaire pour qu'il soit ouvert automatiquement
	 * lors d'une exécution ultérieure du programme.
	 *
	 * @throws SauvegardeImpossible Si le support de sauvegarde est inaccessible.
	 */
	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeObject(gestionPersonnel);
		} catch (IOException e) {
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible {
		return -1;
	}

	@Override
	public void remove(Ligue ligue) throws SauvegardeImpossible {
		// does nothing
	}
}
