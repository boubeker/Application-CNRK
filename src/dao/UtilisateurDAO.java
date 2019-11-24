package dao;

import java.sql.ResultSet;
import java.sql.Statement;

import metier.Utilisateur;

public class UtilisateurDAO {
	
	private Connect myConnect;
	private ResultSet rs;
	
	public boolean rechercherUtilisateur(Utilisateur utilisateurRecherche) {
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			String strCmd = "SELECT count(*) from utilisateurs where identifiant = '"+utilisateurRecherche.getIdentifiant()+"' and hash = SHA2('"+utilisateurRecherche.getHashMotDePasse()+"',256)";
			
			
			rs = stmt.executeQuery(strCmd);
			
			if(rs.next() && rs.getInt(1) == 1) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
	}
}
