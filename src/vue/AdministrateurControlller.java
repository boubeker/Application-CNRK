package vue;

import java.util.HashSet;
import java.util.Set;

import org.controlsfx.control.action.Action;
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.utils.JFXHighlighter;
import com.jfoenix.utils.JFXNodeUtils;

import application.Main;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Duration;
import javafx.util.StringConverter;
import metier.Caracteristique;
import metier.Instrument;
import metier.Personne;
import metier.Site;
import metier.Validations;
import metier.Ville;
import service.AdministrateurBean;

public class AdministrateurControlller {

	@FXML
	private TableView<Personne> tableViewPersonne;

	@FXML
	private TableColumn<Personne, String> tabColonnePrenom;

	@FXML
	private TableColumn<Personne, String> tabColonneNom;

	@FXML
	private TableColumn<Personne, String> tabColonneAdresse;

	@FXML
	private TableColumn<Personne, String> tabColonneVille;

	@FXML
	private TableColumn<Personne, String> tabColonneCodePostal;

	@FXML
	private Label labelPrenom;

	@FXML
	private Label labelNom;

	@FXML
	private Label labelAdresse;

	@FXML
	private Label labelVille;

	@FXML
	private Label labelCodePostal;

	@FXML
	private Label labelSite;

	@FXML
	private Label labelRayon;

	@FXML
	private TableView<Validations> tableViewValidation;

	@FXML
	private TableColumn<Validations, String> tabColonneCycle;

	@FXML
	private TableColumn<Validations, String> tabColonneInstrument;

	@FXML
	private TableColumn<Validations, String> tabColonneDate;

	@FXML
	private ComboBox<Caracteristique> comboBoxFamilleInstru;

	@FXML
	private ComboBox<Instrument> comboBoxInstrument;

	@FXML
	private ComboBox<Site> comboBoxSite;

	@FXML
	private TextField barreDeRecherche;

	@FXML
	private ButtonBar buttonBarModifSupp;

	@FXML
	private TableView<Site> tableViewSite;

	@FXML
	private TableColumn<Site, String> tabSiteColonneCodePostal;

	@FXML
	private TableColumn<Site, String> tabSiteColonneNom;

	@FXML
	private TableColumn<Site, String> tabSiteColonneNomVille;

	@FXML
	TableColumn<Site, String> tabSiteColonneTelephone;

	@FXML
	private Label labelSiteNom;

	@FXML
	private Label labelSiteVille;

	@FXML
	private Label labelSiteAdresse;

	@FXML
	private Label labelSiteCodePostal;

	@FXML
	private Label labelSiteTelephone;

	@FXML
	private TabPane onglet;

	private AdministrateurBean adminBean;
	private Main menuApp;

	private SuggestionProvider<Instrument> providerInstrument;
	private SuggestionProvider<Ville> providerVille;
	private SuggestionProvider<Site> providerSite;

	@FXML
	private JFXSpinner chargement;
	@FXML
	private Label labelChargement;

	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private void initialize() {
		tableViewValidation.setSelectionModel(null);

		comboBoxInstrument.setEditable(true);
		/*
		 * permet l'utilisation du comboBoxInstrument avec un textField
		 */
		comboBoxInstrument.setConverter(new StringConverter<Instrument>() {
			@Override
			public String toString(Instrument object) {

				System.out.print("converting object: ");
				if (object == null) {
					System.out.println("null");
					return "";
				}
				System.out.println(object.toString());
				return object.toString();

			}

			@Override
			public Instrument fromString(String string) {
				return adminBean.getInstrumentByName(string);
			}

		});

		comboBoxSite.setEditable(true);
		comboBoxSite.setConverter(new StringConverter<Site>() {
			@Override
			public String toString(Site object) {

				System.out.print("converting object: ");
				if (object == null) {
					System.out.println("null");
					return "";
				}
				System.out.println(object.toString());
				return object.toString();

			}

			@Override
			public Site fromString(String string) {
				return adminBean.getSiteByName(string);
			}

		});
	}

	public void setMenuApp(Main menuApp) {
		this.menuApp = menuApp;
		adminBean = menuApp.getAdminBean();

		tableViewPersonne.setItems(adminBean.getListePersonne());

		comboBoxFamilleInstru.setItems(adminBean.getListeSpecificite());
		comboBoxFamilleInstru.setValue(adminBean.getListeSpecificite().get(0));

		comboBoxInstrument.setItems(adminBean.getListeInstrument());

		comboBoxSite.setItems(adminBean.getListeSite());

		// Met en place l'auto completion dans la comboBoxInstrument
		Set<Instrument> autoCompletions = new HashSet<>(adminBean.getListeInstrument());
		providerInstrument = SuggestionProvider.create(autoCompletions);
		new AutoCompletionTextFieldBinding<>(comboBoxInstrument.getEditor(), providerInstrument);

		Set<Site> autoCompletionsSite = new HashSet<>(adminBean.getListeSite());
		providerSite = SuggestionProvider.create(autoCompletionsSite);
		new AutoCompletionTextFieldBinding<>(comboBoxSite.getEditor(), providerSite);

		initListener();
		initTableView();
		initSite();
	}

	private void initTableView() {
		tabColonneNom.setCellValueFactory(
				(CellDataFeatures<Personne, String> feature) -> new SimpleStringProperty(feature.getValue().getNom()));
		tabColonnePrenom.setCellValueFactory((CellDataFeatures<Personne, String> feature) -> new SimpleStringProperty(
				feature.getValue().getPrenom()));
		tabColonneAdresse.setCellValueFactory((CellDataFeatures<Personne, String> feature) -> new SimpleStringProperty(
				feature.getValue().getAdresse().getNumLibelle()));
		tabColonneVille.setCellValueFactory((CellDataFeatures<Personne, String> feature) -> new SimpleStringProperty(
				feature.getValue().getAdresse().getLocalisation().getNomVille()));
		tabColonneCodePostal
				.setCellValueFactory((CellDataFeatures<Personne, String> feature) -> new SimpleStringProperty(
						feature.getValue().getAdresse().getLocalisation().getCodePostale()));

		tabColonneCycle.setCellValueFactory((CellDataFeatures<Validations, String> feature) -> new SimpleStringProperty(
				feature.getValue().getCycle().getNom()));
		tabColonneInstrument
				.setCellValueFactory((CellDataFeatures<Validations, String> feature) -> new SimpleStringProperty(
						feature.getValue().getInstrument().getNom()));
		tabColonneDate.setCellValueFactory((CellDataFeatures<Validations, String> feature) -> new SimpleStringProperty(
				(feature.getValue().getDateObtention() == null) ? ""
						: feature.getValue().getDateObtention().toString()));
	}
	
	private void initListener() {
		comboBoxFamilleInstru.valueProperty()
				.addListener((observable, oldValue, newValue) -> selectCaracteristique(newValue));
		comboBoxInstrument.valueProperty().addListener((observable, oldValue, newValue) -> selectInstrument(newValue));
		comboBoxSite.valueProperty().addListener((observable, oldValue, newValue) -> selectSite(newValue));

		barreDeRecherche.textProperty().addListener((observable, odlValue, newValue) -> personneRechercher(newValue));

		tableViewPersonne.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> afficherDetailPersonne(newValue));
		onglet.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> changementOnglet(newValue));
	}

	private void initSite() {
		tableViewSite.setItems(adminBean.getListeSite());

		tabSiteColonneNom.setCellValueFactory(
				(CellDataFeatures<Site, String> feature) -> new SimpleStringProperty(feature.getValue().getNom()));

		tabSiteColonneNomVille.setCellValueFactory((CellDataFeatures<Site, String> feature) -> new SimpleStringProperty(
				feature.getValue().getAdresse().getLocalisation().getNomVille()));

		tabSiteColonneCodePostal
				.setCellValueFactory((CellDataFeatures<Site, String> feature) -> new SimpleStringProperty(
						feature.getValue().getAdresse().getLocalisation().getCodePostale()));

		tabSiteColonneTelephone
				.setCellValueFactory((CellDataFeatures<Site, String> feature) -> new SimpleStringProperty(
						feature.getValue().getNumTelephone()));

		tableViewSite.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> afficherDetailSite(newValue));

	}
	
	private void changementOnglet(Tab newValue) {
		buttonBarModifSupp.setDisable(true);
	}

	private void afficherDetailSite(Site site) {
		adminBean.setSiteSelected(site);

		if (site != null) {
			labelSiteNom.setText(site.getNom());
			labelSiteTelephone.setText(site.getNumTelephone());
			labelSiteAdresse.setText(site.getAdresse().toString());
			labelSiteCodePostal.setText(site.getAdresse().getLocalisation().getCodePostale());
			labelSiteVille.setText(site.getAdresse().getLocalisation().getNomVille());
			buttonBarModifSupp.setDisable(false);
		} else {
			labelSiteNom.setText("");
			labelSiteTelephone.setText("");
			labelSiteAdresse.setText("");
			labelSiteCodePostal.setText("");
			labelSiteVille.setText("");
		}
	}

	private void afficherDetailPersonne(Personne personneSelect) {
		if (personneSelect != null) {
			adminBean.setPersonneSelected(personneSelect);
			adminBean.getSitePersonneSelected();
			labelPrenom.setText(personneSelect.getPrenom());
			labelNom.setText(personneSelect.getNom());
			labelAdresse.setText(personneSelect.getAdresse().toString());
			labelVille.setText(personneSelect.getAdresse().getLocalisation().getNomVille());
			labelCodePostal.setText(personneSelect.getAdresse().getLocalisation().getCodePostale());
			labelRayon.setText(Integer.toString(personneSelect.getRayon()));
			tableViewValidation.setItems(personneSelect.getListeDiplome());
			adminBean.setPersonneSelected(personneSelect);
			labelSite.setText(personneSelect.getSiteAffecte().getNom());
			buttonBarModifSupp.setDisable(false);
		} else {
			labelPrenom.setText("");
			labelNom.setText("");
			labelAdresse.setText("");
			labelVille.setText("");
			labelCodePostal.setText("");
			labelRayon.setText("");
			tableViewValidation.setItems(null);
			adminBean.setPersonneSelected(null);
			buttonBarModifSupp.setDisable(true);
		}
	}

	@FXML
	private void showCours() {
		menuApp.showCours();
	}

	private void chargementActif() {
		labelChargement.setVisible(true);
		chargement.setVisible(true);
		mainAnchorPane.setDisable(true);
	}

	@FXML
	private void ajouter() {
		if (onglet.getSelectionModel().getSelectedItem().getText().equals("Enseignants")) {
			tableViewPersonne.getSelectionModel().clearSelection();
			adminBean.setPersonneSelected(new Personne(0, "", "", 0));
			menuApp.showAjouterEnseignant();

		} else if (onglet.getSelectionModel().getSelectedItem().getText().equals("Sites")) {
			tableViewSite.getSelectionModel().clearSelection();
			adminBean.setSiteSelected(new Site(0, "", ""));
			menuApp.showEditSite();
		}
	}

	@FXML
	private void modifier() {
		if (onglet.getSelectionModel().getSelectedItem().getText().equals("Enseignants")) {
			menuApp.showAjouterEnseignant();

		} else if (onglet.getSelectionModel().getSelectedItem().getText().equals("Sites")) {
			menuApp.showEditSite();
		}
	}

	@FXML
	private void supprimer() {

		if (onglet.getSelectionModel().getSelectedItem().getText().equals("Enseignants")) {
			supprimerEnseigant();

		} else if (onglet.getSelectionModel().getSelectedItem().getText().equals("Sites")) {
			supprimerSite();
		}

	}

	private void supprimerEnseigant() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Suppression");
		alert.setHeaderText(
				"Voulez-vous vraiment supprimer l'enseignant(e) : " + adminBean.getPersonneSelected().toString());

		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			if (adminBean.supprimerPersonne()) {
				Alert alertReussite = new Alert(AlertType.INFORMATION);
				alertReussite.setHeaderText("Personne supprimer");
				alertReussite.show();
				tableViewPersonne.setItems(adminBean.getListePersonne());
			} else {
				Alert alertEchec = new Alert(AlertType.ERROR);
				alertEchec.setHeaderText("Personne non supprimer");
				alertEchec.show();
			}
		}
	}

	private void supprimerSite() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Suppression");
		alert.setHeaderText("Voulez-vous vraiment supprimer le site : " + adminBean.getSiteSelected().getNom());

		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			if (adminBean.supprimerSite()) {
				Alert alertReussite = new Alert(AlertType.INFORMATION);
				alertReussite.setHeaderText("Site supprimer");
				alertReussite.show();
				tableViewSite.setItems(adminBean.getListeSite());
			} else {
				Alert alertEchec = new Alert(AlertType.ERROR);
				alertEchec.setHeaderText("Site non supprimer");
				alertEchec.show();
			}
		}
	}

	private void selectCaracteristique(Caracteristique specificiteSelect) {
		if (specificiteSelect != null) {
			if (specificiteSelect.getId() == 0) {
				comboBoxInstrument.setItems(adminBean.getListeInstrument());
				comboBoxInstrument.setPromptText("Instruments");
				comboBoxInstrument.setValue(null);
			} else {
				ObservableList<Instrument> instrumentListe = FXCollections
						.observableArrayList(specificiteSelect.getListeInstrument());
				comboBoxInstrument.setItems(instrumentListe);
				comboBoxInstrument.setPromptText("Sélectionner instruments");
			}
			// Met à jour l'auto completion
			if (providerInstrument != null) {
				Set<Instrument> filteredAutoCompletions;
				if (specificiteSelect.getId() == 0) {
					filteredAutoCompletions = new HashSet<>(adminBean.getListeInstrument());
				} else {
					filteredAutoCompletions = new HashSet<>(specificiteSelect.getListeInstrument());
				}

				providerInstrument.clearSuggestions();
				providerInstrument.addPossibleSuggestions(filteredAutoCompletions);
			}
		}
	}

	private void selectInstrument(Instrument instrumentSelect) {
		System.out.println("Changement instrument");
		if (instrumentSelect != null) {
			adminBean.getPersonneRechercher().setInstrument(instrumentSelect);
		} else {
			adminBean.getPersonneRechercher().setInstrument(new Instrument(0, ""));
		}

		tableViewPersonne.setItems(adminBean.rechercher());
	}

	private void personneRechercher(String nomSelect) {
		if (nomSelect != null) {
			adminBean.getPersonneRechercher().setNom(nomSelect);
			tableViewPersonne.setItems(adminBean.rechercher());
		}
	}

	private void selectSite(Site siteSelect) {
		if (siteSelect != null) {
			adminBean.getPersonneRechercher().setSite(siteSelect);
		} else {
			adminBean.getPersonneRechercher().setSite(new Site(0, "", ""));
		}

		tableViewPersonne.setItems(adminBean.rechercher());
	}

	public void refresh() {
		tableViewPersonne.setItems(adminBean.getListePersonne());
		tableViewSite.setItems(adminBean.getListeSite());
	}

}
