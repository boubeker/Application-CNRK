package vue;


import java.sql.Date;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import metier.Cycles;
import metier.Instrument;
import metier.Validations;
import service.AdministrateurBean;

public class AjouterValidationsController {

	@FXML
	private ComboBox<Cycles> comboBoxCycles;

	@FXML
	private ComboBox<Instrument> comboBoxInstruments;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Button btnValider;

	private Main menuApp;
	private AdministrateurBean adminBean;

	public void setMenuApp(Main menuApp) {
		this.menuApp = menuApp;
		adminBean = menuApp.getAdminBean();
		comboBoxCycles.setItems(adminBean.getListeCycle());
		comboBoxInstruments.setItems(adminBean.getListeInstrument());
	}

	@FXML
	private void valider() {
		Validations validation = new Validations(0, Date.valueOf(datePicker.getValue()));
		validation.setPersonne(adminBean.getPersonneSelected());
		validation.setCycle(comboBoxCycles.getValue());
		validation.setInstrument(comboBoxInstruments.getValue());
		adminBean.getPersonneSelected().getListeDiplome().add(validation);
		adminBean.addListeValidationAajouter(validation);
		menuApp.fermerFenetreAjouterValidations();
	}

	@FXML
	private void annuler() {

		menuApp.fermerFenetreAjouterValidations();
	}
}
