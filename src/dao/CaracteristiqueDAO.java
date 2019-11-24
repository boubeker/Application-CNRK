package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Caracteristique;
import metier.Instrument;


public class CaracteristiqueDAO {
	
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Caracteristique> getAll() {
		ArrayList<Caracteristique> liste = new ArrayList<>();
		try {

			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			// Determine the column set column

			String strCmd = "select c.caracteristique_id, nom_type, instrument_id, nom_instrument from Caracteristiques as c join Instruments as i on c.caracteristique_id = i.caracteristique_id";
			rs = stmt.executeQuery(strCmd);

			Caracteristique specificiteLu = new Caracteristique(0, "");

			while (rs.next()) {
				if (specificiteLu.getId() != rs.getInt(1)) {
					specificiteLu = new Caracteristique(rs.getInt(1), rs.getString(2));
					liste.add(specificiteLu);
				}
				specificiteLu.addInstrument(new Instrument(rs.getInt(3), rs.getString(4)));
			}
			rs.close();

		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		return liste;
	}
}
