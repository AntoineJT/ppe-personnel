package com.github.antoinejt.ppepersonnel.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.antoinejt.ppepersonnel.config.Config;
import com.github.antoinejt.ppepersonnel.personnel.GestionPersonnel;
import com.github.antoinejt.ppepersonnel.personnel.Ligue;
import com.github.antoinejt.ppepersonnel.personnel.Passerelle;
import com.github.antoinejt.ppepersonnel.personnel.SauvegardeImpossible;

public class JDBC implements Passerelle {
	Connection connection;

	public JDBC() {
		try {
			Class.forName(Config.DB_DRIVER_CLASSNAME.toString());
			connection = DriverManager.getConnection(getUrl(),
					Config.DB_USER.toString(), Config.DB_PASSWORD.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("Pilote JDBC non installé.");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private static String getUrl() {
		return String.format("jdbc:%s://%s:%s/%s", Config.DB_DRIVER, Config.DB_HOST, Config.DB_PORT, Config.DB_NAME);
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() {
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try (ResultSet ligues = connection.createStatement().executeQuery("SELECT * FROM ligue")) {
			while (ligues.next()) {
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible {
		close();
	}
	
	public void close() throws SauvegardeImpossible {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible {
		ResultSet id = null;
		try (PreparedStatement instruction = connection.prepareStatement(
				"INSERT INTO ligue (nom) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
			instruction.setString(1, ligue.getNom());
			instruction.executeUpdate();
			id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		} finally {
			if (id != null) {
				try {
					id.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
