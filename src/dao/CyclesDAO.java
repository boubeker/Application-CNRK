package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Cycles;

public class CyclesDAO {
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Cycles> getAll() {
		ArrayList<Cycles> liste = new ArrayList<>();
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			
			String strCmd = "select * from cycles";
			
			rs= stmt.executeQuery(strCmd);
			
			while(rs.next()) {
				liste.add(new Cycles(rs.getInt(1), rs.getString(2), rs.getBoolean(3)));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return liste;
	}
}
