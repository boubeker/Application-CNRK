package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Instrument;

public class InstrumentDAO {
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Instrument> getAll() {
		ArrayList<Instrument> liste = new ArrayList<>();
		try {
			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			
			String strCmd = "select * from Instruments order by nom_instrument";
			
			rs= stmt.executeQuery(strCmd);
			
			while(rs.next()) {
				liste.add(new Instrument(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return liste;
	}
}
