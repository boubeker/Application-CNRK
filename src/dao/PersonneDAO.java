package dao;

import metier.*;
import service.PersonneRechercher;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Boubeker EL IDRISSI - Nicolas PERNET
 *
 */
public class PersonneDAO {
	private Connect myConnect;
	private ResultSet rs;

	/**
	 * Se connecte à la base de données et exécute une procédure stockée
	 * @return la liste de tout les enseignants
	 */

	public ArrayList<Personne> getAll() {
		ArrayList<Personne> liste = new ArrayList<>();

		try {
			CallableStatement stmt = myConnect.getInstance().getConnection()
					.prepareCall("{ call chercher_ensseignant(0, 0, '', 0)}");

			rs = stmt.executeQuery();
			Personne personneLu = new Personne(0, "", "", 0);

			while (rs.next()) {
				if (personneLu.getId() != rs.getInt(1)) {
					personneLu = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(8));
					Adresse newAdresse = new Adresse("", rs.getString(5), rs.getString(6), rs.getString(4),
							rs.getString(7), "");
					newAdresse.setLocalisation(new Ville(rs.getString(9), rs.getString(10), rs.getString(11), 0, 0));
					personneLu.setAdresse(newAdresse);
					liste.add(personneLu);
				}
				if (rs.getInt(12) != 0) {
					Validations diplomeLu = new Validations(rs.getInt(12), rs.getDate(17));
					diplomeLu.setCycle(new Cycles(rs.getInt(13), rs.getString(14), false));
					diplomeLu.setInstrument(new Instrument(rs.getInt(15), rs.getString(16)));
					diplomeLu.setPersonne(personneLu);
					personneLu.addDiplome(diplomeLu);
				}
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return liste;
	}
	
	/**
	 * On récupere la liste des enseignants en affinant la liste
	 *  grâce aux critères de recherche de la procédure qui sont :
	 * (Instrument, Cycle, Nom, Site) 
	 * @param personneRechercher 
	 * @return liste des enseigants
	 */

	public ArrayList<Personne> getRecherche(PersonneRechercher personneRechercher) {
		ArrayList<Personne> liste = new ArrayList<>();
		try {
			CallableStatement stmt = myConnect.getInstance().getConnection()
					.prepareCall("{ call chercher_ensseignant(?, ?, ?, ?)}");
			stmt.setInt(1, personneRechercher.getInstrument().getId());
			stmt.setInt(2, personneRechercher.getCycle().getId());
			stmt.setString(3, personneRechercher.getNom());
			stmt.setInt(4, personneRechercher.getSite().getId());

			rs = stmt.executeQuery();
			Personne personneLu = new Personne(0, "", "", 0);

			while (rs.next()) {
				if (personneLu.getId() != rs.getInt(1)) {
					personneLu = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(8));
					Adresse newAdresse = new Adresse("", rs.getString(5), rs.getString(6), rs.getString(4),
							rs.getString(7), "");
					newAdresse.setLocalisation(new Ville(rs.getString(9), rs.getString(10), rs.getString(11), 0, 0));
					personneLu.setAdresse(newAdresse);
					liste.add(personneLu);
				}
				if (rs.getInt(12) != 0) {
					Validations diplomeLu = new Validations(rs.getInt(12), rs.getDate(17));
					diplomeLu.setCycle(new Cycles(rs.getInt(13), rs.getString(14), false));
					diplomeLu.setInstrument(new Instrument(rs.getInt(15), rs.getString(16)));
					diplomeLu.setPersonne(personneLu);
					personneLu.addDiplome(diplomeLu);
				}
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return liste;
	}

	public boolean supprimerPersonne(Personne personne) {
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			String strCmd = "DELETE FROM PERSONNES WHERE PERSONNE_ID = " + personne.getId();
			stmt.execute(strCmd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * On excute la requete qui permet d'ajouter une personne et on va récupéré son ID pour pouvoir lui ajouter ses diplômes
	 * @param personne qui sera ajouter
	 * @return l'ID en cas de succès ou -1 si il y a eu une erreur d'insertion
	 */

	public int ajouterPersonne(Personne personne) {
		try {
			PreparedStatement pStmt = myConnect.getInstance().getConnection().prepareStatement(
					"INSERT INTO PERSONNES VALUES (null,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, personne.getNom());
			pStmt.setString(2, personne.getPrenom());
			pStmt.setString(3, personne.getAdresse().getNumLibelle());
			pStmt.setString(4, personne.getAdresse().getComplementInterieur());
			pStmt.setString(5, personne.getAdresse().getComplementExterieur());
			pStmt.setString(6, personne.getAdresse().getLieuParticulier());
			pStmt.setInt(7, personne.getRayon());
			pStmt.setString(8, personne.getAdresse().getLocalisation().getInsee());
			if (pStmt.executeUpdate() == 1) {
				rs = pStmt.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			} else {
				return -1;
			}

		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 
	 * @param personne
	 * @return
	 */

	public boolean modifierPersonne(Personne personne) {
		try {
			PreparedStatement pStmt = myConnect.getInstance().getConnection().prepareStatement(
					"UPDATE personnes SET nom = ?, prenom = ?, adresse1 = ?, adresse2 = ?, adresse3 = ?, adresse4 = ?, distance_maximum = ?, insee = ? where personne_id = ?");
			pStmt.setString(1, personne.getNom());
			pStmt.setString(2, personne.getPrenom());
			pStmt.setString(3, personne.getAdresse().getNumLibelle());
			pStmt.setString(4, personne.getAdresse().getComplementInterieur());
			pStmt.setString(5, personne.getAdresse().getComplementExterieur());
			pStmt.setString(6, personne.getAdresse().getLieuParticulier());
			pStmt.setInt(7, personne.getRayon());
			pStmt.setString(8, personne.getAdresse().getLocalisation().getInsee());
			pStmt.setInt(9, personne.getId());

			if (pStmt.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
