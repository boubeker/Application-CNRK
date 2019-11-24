package vue;

import java.util.HashSet;
import java.util.Set;

import application.Main;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import metier.Adresse;
import metier.Instrument;
import metier.Personne;
import metier.Site;
import metier.Validations;
import metier.Ville;
import service.AdministrateurBean;

public class AjouterEnseignantController {

	@FXML
	private TextField textFieldPrenom;
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
	private TextField textFieldRayon;
	@FXML
	private TableView<Validations> tableViewValidations;
	@FXML
	private ComboBox<Ville> comboBoxVille;
	@FXML
	private TableColumn<Validations, String> tabColonneCycle;
	@FXML
	private TableColumn<Validations, String> tabColonneInstrument;
	@FXML
	private TableColumn<Validations, String> tabColonneDate;
	@FXML
	private Label labelCodePostal;
	@FXML
	private Button btnSupprimerDiplome;

	private Stage dialogStage;
	private AdministrateurBean adminBean;
	private boolean isModification;
	private Main menuApp;
	private SuggestionProvider<Ville> providerVille;

	@FXML
	private void initialize() {
		tabColonneCycle.setCellValueFactory((CellDataFeatures<Validations, String> feature) -> new SimpleStringProperty(
				feature.getValue().getCycle().getNom()));
		tabColonneInstrument
				.setCellValueFactory((CellDataFeatures<Validations, String> feature) -> new SimpleStringProperty(
						feature.getValue().getInstrument().getNom()));
		tabColonneDate.setCellValueFactory((CellDataFeatures<Validations, String> feature) -> new SimpleStringProperty(
				(feature.getValue().getDateObtention() == null) ? ""
						: feature.getValue().getDateObtention().toString()));

		tableViewValidations.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selectionValidation(newValue));

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

	private void selectionValidation(Validations newValue) {
		if (newValue != null) {

			btnSupprimerDiplome.setDisable(false);
		} else {
			btnSupprimerDiplome.setDisable(true);
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setMenuApp(Main menuApp) {
		this.menuApp = menuApp;
		adminBean = menuApp.getAdminBean();
		comboBoxVille.setItems(adminBean.getListeVille());
		comboBoxVille.valueProperty().addListener((observable, oldValue, newValue) -> selectVille(newValue));
		
		Set<Ville> autoCompletions = new HashSet<>(adminBean.getListeVille());
		providerVille = SuggestionProvider.create(autoCompletions);
		new AutoCompletionTextFieldBinding<>(comboBoxVille.getEditor(), providerVille);
		
		afficheDetailPersonne();
		if (adminBean.getPersonneSelected().getId() == 0) {
			isModification = false;
		} else {
			isModification = true;
		}

	}

	public void afficheDetailPersonne() {

		textFieldPrenom.setText(adminBean.getPersonneSelected().getPrenom());
		textFieldNom.setText(adminBean.getPersonneSelected().getNom());
		textFieldAdresse1.setText(adminBean.getPersonneSelected().getAdresse().getNumLibelle());
		textFieldAdresse2.setText(adminBean.getPersonneSelected().getAdresse().getComplementInterieur());
		textFieldAdresse3.setText(adminBean.getPersonneSelected().getAdresse().getComplementExterieur());
		textFieldAdresse4.setText(adminBean.getPersonneSelected().getAdresse().getLieuParticulier());
		textFieldRayon.setText(Integer.toString(adminBean.getPersonneSelected().getRayon()));
		tableViewValidations.setItems(adminBean.getPersonneSelected().getListeDiplome());
		comboBoxVille.setValue(adminBean.getPersonneSelected().getAdresse().getLocalisation());

	}

	private void selectVille(Ville villeSelect) {
		if (villeSelect != null) {
			labelCodePostal.setText(comboBoxVille.getSelectionModel().getSelectedItem().getCodePostale());
		}
	}

	@FXML
	public void valider() {
		if (isModification) {
			// update
			adminBean.getPersonneSelected().setPrenom(textFieldPrenom.getText());
			adminBean.getPersonneSelected().setNom(textFieldNom.getText());
			adminBean.getPersonneSelected().setRayon(Integer.parseInt(textFieldRayon.getText()));
			Adresse newAdresse = new Adresse("", textFieldAdresse2.getText(), textFieldAdresse3.getText(),
					textFieldAdresse1.getText(), textFieldAdresse4.getText(), "");
			newAdresse.setLocalisation(new Ville(comboBoxVille.getValue().getInsee(), "", "", 0, 0));
			adminBean.getPersonneSelected().setAdresse(newAdresse);
			if (adminBean.modifierPersonne(adminBean.getPersonneSelected())) {
				System.out.println("Modification OK");
				menuApp.fermerFenetreAjouterEnseignant();
				menuApp.refreshAdministrer();
			} else {
				System.out.println("Erreur Modification");
			}

		} else {
			// insert
			adminBean.getPersonneSelected().setPrenom(textFieldPrenom.getText());
			adminBean.getPersonneSelected().setNom(textFieldNom.getText());
			adminBean.getPersonneSelected().setRayon(Integer.parseInt(textFieldRayon.getText()));
			Adresse newAdresse = new Adresse("", textFieldAdresse2.getText(), textFieldAdresse3.getText(),
					textFieldAdresse1.getText(), textFieldAdresse4.getText(), "");
			newAdresse.setLocalisation(new Ville(comboBoxVille.getValue().getInsee(), "", "", 0, 0));
			adminBean.getPersonneSelected().setAdresse(newAdresse);
			if (adminBean.ajouterPersonne(adminBean.getPersonneSelected())) {
				System.out.println("Insertion OK");
				menuApp.fermerFenetreAjouterEnseignant();
				menuApp.refreshAdministrer();
			} else {
				System.out.println("Erreur insertion");
			}
		}
	}

	@FXML
	public void annuler() {
		menuApp.fermerFenetreAjouterEnseignant();
		adminBean.getPersonneSelected().getListeDiplome().addAll(adminBean.getListeValidationAsupprimer());
		adminBean.getListeValidationAsupprimer().clear();
		adminBean.getPersonneSelected().getListeDiplome().removeAll(adminBean.getListeValidationAajouter());
		adminBean.getListeValidationAajouter().clear();
	}

	@FXML
	public void supprimerDiplome() {
		adminBean.addValidationAsupprimer(tableViewValidations.getSelectionModel().getSelectedItem());
		adminBean.getPersonneSelected().getListeDiplome()
				.remove(tableViewValidations.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void showAjouterValidations() {
		menuApp.showAjouterValidations();
		
	}

}
