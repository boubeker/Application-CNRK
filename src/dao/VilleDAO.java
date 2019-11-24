package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Ville;

public class VilleDAO {
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Ville> getAll() {
		ArrayList<Ville> liste = new ArrayList<>();
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			
			String strCmd = "select * from villes order by nom_ville";
			
			rs= stmt.executeQuery(strCmd);
			
			while(rs.next()) {
				liste.add(new Ville(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return liste;
	}
}
