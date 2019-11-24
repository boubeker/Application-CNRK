package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Departement;


public class DepartementDAO {
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Departement> getAll() {
		ArrayList<Departement> liste = new ArrayList<>();
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			
			String strCmd = "select * from departements order by nom_departement";
			
			rs= stmt.executeQuery(strCmd);
			
			while(rs.next()) {
				liste.add(new Departement(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return liste;
	}
}
