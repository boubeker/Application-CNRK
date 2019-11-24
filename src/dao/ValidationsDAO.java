package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLType;

import metier.Personne;
import metier.Validations;

public class ValidationsDAO {

	private Connect myConnect;
	private ResultSet rs;
	
	public boolean ajouterModifierValidations(Validations validation) {
		try {
			PreparedStatement pStmt = myConnect.getInstance().getConnection().prepareStatement("INSERT INTO validations VALUES (null,?,?,?,?)");
			pStmt.setInt(1, validation.getPersonne().getId());
			pStmt.setInt(2, validation.getCycle().getId());
			pStmt.setInt(3, validation.getInstrument().getId());
			pStmt.setDate(4, (Date) validation.getDateObtention());
			
			if (pStmt.executeUpdate() > 0 ) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean supprimerValidation(Validations validation) {
		try {
			PreparedStatement pStmt = myConnect.getInstance().getConnection().prepareStatement("DELETE from validations where validation_id = ?");
			pStmt.setInt(1, validation.getId());

			if (pStmt.executeUpdate() > 0 ) {
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
