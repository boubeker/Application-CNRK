package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import metier.Departement;
import metier.Region;

public class RegionDAO {
	private Connect myConnect;
	private ResultSet rs;
	
	public ArrayList<Region> getAll() {
		ArrayList<Region> liste = new ArrayList<>();
		try {

			Statement stmt = myConnect.getInstance().getConnection().createStatement();
			// Determine the column set column

			String strCmd = "select r.region_id, nom_region, departement_id, nom_departement, code_departement from regions as r join departements as d on d.region_id = r.region_id order by nom_region  ";
			rs = stmt.executeQuery(strCmd);

			Region regionLu = new Region(0, "");

			while (rs.next()) {
				if (regionLu.getId() != rs.getInt(1)) {
					regionLu = new Region(rs.getInt(1), rs.getString(2));
					liste.add(regionLu);
				}
				regionLu.addDepartement(new Departement(rs.getInt(3), rs.getString(4), rs.getString(5)));
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
