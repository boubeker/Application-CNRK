package vue;

import java.util.HashSet;
import java.util.Set;

import application.Main;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import metier.Adresse;
import metier.Ville;
import service.AdministrateurBean;
import service.ControleSaisie;

public class EditSiteController {

	@FXML
	private TextField textFieldNom;
	@FXML
	private TextField textFieldAdresse1;
	@FXML
	private TextField textFieldAdresse2;
	@FXML
	private TextField textFieldAdresse3;
	@FXML
	private TextField textFieldAdresse4;
	@FXML
	private TextField textFieldTelephone;

	@FXML
	private ComboBox<Ville> comboBoxVille;

	@FXML
	private Label labelCodePostal;

	private Main menuApp;
	private AdministrateurBean adminBean;
	private SuggestionProvider<Ville> providerVille;
	private boolean isModification;
	private ControleSaisie controleSaisie;

	@FXML
	private void initialize() {
		comboBoxVille.setEditable(true);
		comboBoxVille.setConverter(new StringConverter<Ville>() {
			@Override
			public String toString(Ville object) {

				System.out.print("converting object: ");
				if (object == null) {
					System.out.println("null");
					return "";
				}
				System.out.println(object.toString());
				return object.toString();

			}

			@Override
			public Ville fromString(String string) {
				return adminBean.getVilleByName(string);
			}

		});
	}

	public void setMenuApp(Main menuApp) {
		this.menuApp = menuApp;
		adminBean = menuApp.getAdminBean();
		comboBoxVille.setItems(adminBean.getListeVille());
		comboBoxVille.valueProperty().addListener((observable, oldValue, newValue) -> selectVille(newValue));

		Set<Ville> autoCompletions = new HashSet<>(adminBean.getListeVille());
		providerVille = SuggestionProvider.create(autoCompletions);
		new AutoCompletionTextFieldBinding<>(comboBoxVille.getEditor(), providerVille);

		afficheDetailSite();
		if (adminBean.getSiteSelected().getId() == 0) {
			isModification = false;
		} else {
			isModification = true;
		}
	}

	public void afficheDetailSite() {

		textFieldNom.setText(adminBean.getSiteSelected().getNom());
		textFieldAdresse1.setText(adminBean.getSiteSelected().getAdresse().getNumLibelle());
		textFieldAdresse2.setText(adminBean.getSiteSelected().getAdresse().getComplementInterieur());
		textFieldAdresse3.setText(adminBean.getSiteSelected().getAdresse().getComplementExterieur());
		textFieldAdresse4.setText(adminBean.getSiteSelected().getAdresse().getLieuParticulier());
		textFieldTelephone.setText(adminBean.getSiteSelected().getNumTelephone());
		comboBoxVille.setValue(adminBean.getSiteSelected().getAdresse().getLocalisation());

	}

	private void selectVille(Ville villeSelect) {
		if (villeSelect != null) {
			labelCodePostal.setText(comboBoxVille.getSelectionModel().getSelectedItem().getCodePostale());
		}
	}

	@FXML
	private void valider() {
		if (isModification) {
			adminBean.getSiteSelected().setNom(textFieldNom.getText());
			adminBean.getSiteSelected().setNumTelephone(textFieldTelephone.getText());
			Adresse newAdresse = new Adresse("", textFieldAdresse2.getText(), textFieldAdresse3.getText(),
					textFieldAdresse1.getText(), textFieldAdresse4.getText(), "");
			newAdresse.setLocalisation(new Ville(comboBoxVille.getValue().getInsee(), "", "", 0, 0));
			adminBean.getSiteSelected().setAdresse(newAdresse);

			if (adminBean.modifierSite()) {
				System.out.println("Modification OK");
				menuApp.fermerFenetreEditSite();
				menuApp.refreshAdministrer();
			} else {
				System.out.println("Erreur Modification");
			}
		} else {
			adminBean.getSiteSelected().setNom(textFieldNom.getText());
			adminBean.getSiteSelected().setNumTelephone(textFieldTelephone.getText());
			Adresse newAdresse = new Adresse("", textFieldAdresse2.getText(), textFieldAdresse3.getText(),
					textFieldAdresse1.getText(), textFieldAdresse4.getText(), "");
			newAdresse.setLocalisation(new Ville(comboBoxVille.getValue().getInsee(), "", "", 0, 0));
			adminBean.getSiteSelected().setAdresse(newAdresse);

			if (adminBean.ajouterSite()) {
				System.out.println("Insertion OK");
				menuApp.fermerFenetreEditSite();
				menuApp.refreshAdministrer();
			} else {
				System.out.println("Erreur Insertion");
			}
		}
	}

	@FXML
	private void annuler() {
//		if (controleSaisie.isString(textFieldNom.getText())) {
//			System.out.println("C'est une chaine de carartère");
//		} else {
//			System.out.println("Erreur not String");
//		}
//
//		if (controleSaisie.isNull(textFieldNom.getText())) {
//			System.out.println("C'est vide");
//		} else {
//			System.out.println("ok bien rempli");
//		}
//		
//		if (controleSaisie.isTel(textFieldNom.getText())) {
//			System.out.println("numero de telephone");
//		} else {
//			System.out.println("erreur num");
//		}
//		
//		if (controleSaisie.adresse(textFieldNom.getText())) {
//			System.out.println("c'est une adresse");
//		} else {
//			System.out.println("erreur adresse");
//		}

	menuApp.fermerFenetreEditSite();
	}
}
