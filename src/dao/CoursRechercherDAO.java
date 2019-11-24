package dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import metier.Adresse;
import metier.Caracteristique;
import metier.Cours;
import metier.Cycles;
import metier.Instrument;
import metier.Site;
import metier.Ville;
import service.CoursRechercher;


public class CoursRechercherDAO {
	
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Cours> getAll() {
		ArrayList<Cours> liste = new ArrayList<>();
		
		try {
			CallableStatement stmt =  myConnect.getInstance().getConnection().prepareCall("{ call rechercher_cours(0, 0, 0, 0, 0)}");

			
			rs = stmt.executeQuery();
	
			
			Cours monCour;
			
			while(rs.next()) {
				Site newSite = new Site(rs.getInt(7), rs.getString(8), rs.getString(13));
				Adresse newAdresse = new Adresse(rs.getString(8),rs.getString(10), rs.getString(11), rs.getString(9), rs.getString(12), "");
				newAdresse.setLocalisation(new Ville(rs.getString(14), rs.getString(15), rs.getString(16), 0, 0));
				newSite.setAdresse(newAdresse);
				monCour = new Cours(new Caracteristique(rs.getInt(1), rs.getString(2)), new Instrument(rs.getInt(3), rs.getString(4)), new Cycles(rs.getInt(5), rs.getString(6), false), newSite);
				liste.add(monCour);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return liste;
	}
	
	public ArrayList<Cours> getRecherche(CoursRechercher courRechercher) {
		ArrayList<Cours> liste = new ArrayList<>();
		try {
			CallableStatement stmt =  myConnect.getInstance().getConnection().prepareCall("{ call rechercher_cours(?, ?, ?, ?, ?)}");
            stmt.setInt(1,courRechercher.getInstrument().getId());
            stmt.setInt(2,courRechercher.getSpeficite().getId());
            stmt.setInt(3,courRechercher.getCycle().getId());
            stmt.setInt(4, courRechercher.getSite().getId());
            stmt.setDouble(5, courRechercher.getDistance());

			rs = stmt.executeQuery();
	
			Cours monCour;
			
			while(rs.next()) {
				Site newSite = new Site(rs.getInt(7), rs.getString(8), rs.getString(13));
				Adresse newAdresse = new Adresse(rs.getString(8),rs.getString(10), rs.getString(11), rs.getString(9), rs.getString(12), "");
				newAdresse.setLocalisation(new Ville(rs.getString(14), rs.getString(15), rs.getString(16), 0, 0));
				newSite.setAdresse(newAdresse);
				monCour = new Cours(new Caracteristique(rs.getInt(1), rs.getString(2)), new Instrument(rs.getInt(3), rs.getString(4)), new Cycles(rs.getInt(5), rs.getString(6), false), newSite);
				liste.add(monCour);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return liste;
	}

}
