package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Adresse;
import metier.Personne;
import metier.Site;
import metier.Ville;

public class SiteDAO {

	private Connect myConnect;
	private ResultSet rs;

	public ArrayList<Site> getAll() {
		ArrayList<Site> liste = new ArrayList<>();
		try {

			Statement stmt = myConnect.getInstance().getConnection().createStatement();

			String strCmd = "select site_id,  nom_site, s.insee, adresse1, adresse2, adresse3,adresse4, telephone, nom_ville, code_postal from sites as s join villes as v on s.insee = v.insee order by nom_site";

			rs = stmt.executeQuery(strCmd);

			while (rs.next()) {
				Site newSite = new Site(rs.getInt(1), rs.getString(2), rs.getString(8));
				Adresse newAdresse = new Adresse(rs.getString(2), rs.getString(5), rs.getString(6), rs.getString(4),
						rs.getString(7), "");
				newAdresse.setLocalisation(new Ville(rs.getString(3), rs.getString(9), rs.getString(10), 0, 0));
				newSite.setAdresse(newAdresse);
				liste.add(newSite);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return liste;
	}

	public Site getSiteAffected(Personne personne) {

		Site siteAffecte = null;

		try {

			CallableStatement stmt = myConnect.getInstance().getConnection()
					.prepareCall("{ call chercher_site_affecte(?)}");
			stmt.setInt(1, personne.getId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				siteAffecte = new Site(rs.getInt(1), rs.getString(2), rs.getString(3));
				Adresse newAdresse = new Adresse("", rs.getString(5), rs.getString(6), rs.getString(4), rs.getString(7),
						"");
				newAdresse.setLocalisation(new Ville(rs.getString(8), rs.getString(9), rs.getString(10), 0, 0));
				siteAffecte.setAdresse(newAdresse);

			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return siteAffecte;
	}

	public boolean supprimerSite(Site site) {
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			String strCmd = "DELETE FROM SITES WHERE SITE_ID = " + site.getId();
			stmt.execute(strCmd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean ajouterSite(Site site) {
		try {
			PreparedStatement pStmt = myConnect.getInstance().getConnection()
					.prepareStatement("INSERT INTO SITES VALUES (null,?,?,?,?,?,?,?)");
			pStmt.setString(1, site.getNom());
			pStmt.setString(2, site.getAdresse().getLocalisation().getInsee());
			pStmt.setString(3, site.getAdresse().getNumLibelle());
			pStmt.setString(4, site.getAdresse().getComplementInterieur());
			pStmt.setString(5, site.getAdresse().getComplementExterieur());
			pStmt.setString(6, site.getAdresse().getLieuParticulier());
			pStmt.setString(7, site.getNumTelephone());

			if (pStmt.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public boolean modifierSite(Site site) {
		try {
			PreparedStatement pStmt = myConnect.getInstance().getConnection().prepareStatement(
					"UPDATE sites SET nom_site = ?, insee = ?, adresse1 = ?, adresse2 = ?, adresse3 = ?, adresse4 = ?, telephone = ? where site_id = ?");
			pStmt.setString(1, site.getNom());
			pStmt.setString(2, site.getAdresse().getLocalisation().getInsee());
			pStmt.setString(3, site.getAdresse().getNumLibelle());
			pStmt.setString(4, site.getAdresse().getComplementInterieur());
			pStmt.setString(5, site.getAdresse().getComplementExterieur());
			pStmt.setString(6, site.getAdresse().getLieuParticulier());
			pStmt.setString(7, site.getNumTelephone());
			pStmt.setInt(8, site.getId());

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
