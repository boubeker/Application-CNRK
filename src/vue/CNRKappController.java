package vue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import application.Main;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.StringConverter;
import metier.Adresse;
import metier.Caracteristique;
import metier.Cours;
import metier.Cycles;
import metier.Departement;
import metier.Instrument;
import metier.Region;
import metier.Site;
import metier.Ville;
import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXSpinner;

import service.CNRKBean;

public class CNRKappController {

	@FXML
	private ComboBox<Caracteristique> comboBoxFamilleInstru;

	@FXML
	private ComboBox<Instrument> comboBoxInstrument;

	@FXML
	private ComboBox<Cycles> comboBoxCycles;

	@FXML
	private ComboBox<Region> comboBoxRegion;

	@FXML
	private ComboBox<Departement> comboBoxDepartement;

	@FXML
	private ComboBox<Site> comboBoxSite;

	@FXML
	private TextField rayonRecherche;

	@FXML
	private Button boutonRechercher;

	@FXML
	private TableView<Cours> tableViewCours;

	@FXML
	private TableColumn<Cours, String> tabColonneFamille;

	@FXML
	private TableColumn<Cours, String> tabColonneInstrument;

	@FXML
	private TableColumn<Cours, String> tabColonneCycle;

	@FXML
	private TableColumn<Cours, String> tabColonneVille;


	@FXML
	private Label nomSite;

	@FXML
	private Label adresse;

	@FXML
	private Label ville;

	@FXML
	private Label codePostale;

	@FXML
	private Label instrument;

	@FXML
	private Label familleInstrument;

	@FXML
	private Label cycle;

	@FXML
	private JFXSpinner chargement;
	@FXML
	private Label labelChargement;
	
	@FXML
	private AnchorPane mainAnchorPane;
	

	private Main menuApp;
	private CNRKBean repertoire;

	private SuggestionProvider<Instrument> providerInstrument;

	@FXML
	private void initialize() {
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
				return repertoire.getInstrumentByName(string);
			}

		});

	}

	public void setMenuApp(Main menuApp) {
		this.menuApp = menuApp;
		repertoire = menuApp.getRepertoire();

		comboBoxFamilleInstru.setItems(repertoire.getListeSpecificite());
		comboBoxFamilleInstru.setValue(repertoire.getListeSpecificite().get(0));

		comboBoxInstrument.setItems(repertoire.getListeInstrument());
		TextFields.bindAutoCompletion(comboBoxInstrument.getEditor(),repertoire.getListeInstrument());

		comboBoxCycles.setItems(repertoire.getListeCycle());
		comboBoxCycles.setValue(repertoire.getListeCycle().get(0));

		comboBoxRegion.setItems(repertoire.getListeRegion());
		comboBoxRegion.setValue(repertoire.getListeRegion().get(0));

		comboBoxDepartement.setItems(repertoire.getListeDepartement());
		comboBoxDepartement.setValue(repertoire.getListeDepartement().get(0));

		comboBoxSite.setItems(repertoire.getListeSite());
		comboBoxSite.setValue(repertoire.getListeSite().get(0));

		tableViewCours.setItems(repertoire.getListeCoursQBE());

		// Met en place l'auto completion dans la comboBoxInstrument
		Set<Instrument> autoCompletions = new HashSet<>(repertoire.getListeInstrument());
		providerInstrument = SuggestionProvider.create(autoCompletions);
		new AutoCompletionTextFieldBinding<>(comboBoxInstrument.getEditor(), providerInstrument);
		initTableView();
		initListener();
	}

	
	private void initTableView() {
		tabColonneFamille.setCellValueFactory((CellDataFeatures<Cours, String> feature) -> new SimpleStringProperty(
				feature.getValue().getSpeficite().getNom()));
		tabColonneInstrument.setCellValueFactory((CellDataFeatures<Cours, String> feature) -> new SimpleStringProperty(
				feature.getValue().getInstrument().getNom()));
		tabColonneCycle.setCellValueFactory((CellDataFeatures<Cours, String> feature) -> new SimpleStringProperty(
				feature.getValue().getCycle().getNom()));
		tabColonneVille.setCellValueFactory((CellDataFeatures<Cours, String> feature) -> new SimpleStringProperty(
				feature.getValue().getSite().getAdresse().getLocalisation().getNomVille()));	
	}
	
	private void initListener() {
		comboBoxFamilleInstru.valueProperty()
				.addListener((observable, oldValue, newValue) -> selectCaracteristique(newValue));
		comboBoxInstrument.valueProperty().addListener((observable, oldValue, newValue) -> selectInstrument(newValue));
		comboBoxCycles.valueProperty().addListener((observable, oldValue, newValue) -> selectCycle(newValue));
		comboBoxRegion.valueProperty().addListener((observable, oldValue, newValue) -> selectRegion(newValue));
		comboBoxDepartement.valueProperty()
				.addListener((observable, oldValue, newValue) -> selectDepartement(newValue));
		comboBoxSite.valueProperty().addListener((observable, oldValue, newValue) -> selectSite(newValue));

		tableViewCours.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> afficherDetailCours(newValue));
	}
	

	@FXML
	public void showAuthentification() {
		menuApp.showAuthentification();
	}

	private void afficherDetailCours(Cours courSelect) {
		if (courSelect != null) {
			nomSite.setText(courSelect.getSite().getNom());
			adresse.setText(courSelect.getSite().getAdresse().toString());
			ville.setText(courSelect.getSite().getAdresse().getLocalisation().getNomVille());
			codePostale.setText(courSelect.getSite().getAdresse().getLocalisation().getCodePostale());
			instrument.setText(courSelect.getInstrument().getNom());
			familleInstrument.setText(courSelect.getSpeficite().getNom());
			cycle.setText(courSelect.getCycle().getNom());
		} else {
			nomSite.setText("");
			adresse.setText("");
			ville.setText("");
			codePostale.setText("");
			instrument.setText("");
			familleInstrument.setText("");
			cycle.setText("");
		}
	}

	private void selectCaracteristique(Caracteristique specificiteSelect) {
		mainAnchorPane.setDisable(true);
		labelChargement.setVisible(true);
		chargement.setVisible(true);
		
		if (specificiteSelect != null) {

			Task task = new Task<Void>() {
				@Override
				public Void call() {
					if (specificiteSelect.getId() == 0) {
						comboBoxInstrument.setItems(repertoire.getListeInstrument());
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
							filteredAutoCompletions = new HashSet<>(repertoire.getListeInstrument());
						} else {
							filteredAutoCompletions = new HashSet<>(specificiteSelect.getListeInstrument());
						}

						providerInstrument.clearSuggestions();
						providerInstrument.addPossibleSuggestions(filteredAutoCompletions);
					}

					repertoire.getCoursQBE().setSpeficite(specificiteSelect);
					tableViewCours.setItems(repertoire.rechercher());

					return null;
				}
			};
			task.setOnSucceeded(taskFinishEvent -> {
				labelChargement.setVisible(false);
				chargement.setVisible(false);
				mainAnchorPane.setDisable(false);
			});
			new Thread(task).start();

		}
	}

	private void selectInstrument(Instrument instrumentSelect) {
		System.out.println("Changement instrument 1");
		
		mainAnchorPane.setDisable(true);
		labelChargement.setVisible(true);
		chargement.setVisible(true);

		Task task = new Task<Void>() {
			@Override
			public Void call() {
				if (instrumentSelect != null) {
					repertoire.getCoursQBE().setInstrument(instrumentSelect);
					
				}
				else {
					repertoire.getCoursQBE().setInstrument(new Instrument(0, ""));
				}
				tableViewCours.setItems(repertoire.rechercher());
				return null;
			}
		};
		task.setOnSucceeded(taskFinishEvent -> {
			labelChargement.setVisible(false);
			chargement.setVisible(false);
			mainAnchorPane.setDisable(false);
		});
		new Thread(task).start();

	}

	private void selectCycle(Cycles cycleSelect) {
		if (cycleSelect != null) {
			repertoire.getCoursQBE().setCycle(cycleSelect);
			tableViewCours.setItems(repertoire.rechercher());
		}
	}

	private void selectRegion(Region regionSelect) {
		if (regionSelect != null) {
			if (regionSelect.getId() == 0) {
				comboBoxDepartement.setItems(repertoire.getListeDepartement());
				comboBoxDepartement.setValue(repertoire.getListeDepartement().get(0));
				System.out.println(regionSelect);
			} else {
				ObservableList<Departement> departementListe = FXCollections
						.observableArrayList(regionSelect.getListeDepartement());
				departementListe.add(0, new Departement(0, "Sélectionner departement", ""));
				comboBoxDepartement.setItems(departementListe);
				comboBoxDepartement.setValue(departementListe.get(0));
			}
		}
	}

	private void selectDepartement(Departement selectDepartement) {
		if (selectDepartement != null) {
			if (selectDepartement.getId() == 0) {
				comboBoxSite.setItems(repertoire.getListeSite());
				comboBoxSite.setValue(repertoire.getListeSite().get(0));
			} else {
				// Récupère la liste des villes du département sélectionner
				ObservableList<Site> listeSite = FXCollections
						.observableArrayList(repertoire
								.getListeSite().stream().filter(line -> line.getAdresse().getLocalisation().getInsee()
										.substring(0, 2).equals(selectDepartement.getCode()))
								.collect(Collectors.toList()));
				Site newSite = new Site(0, "Sélectionner Site", "");
//				Adresse newAdresse = new Adresse("", "", "", "", "", "");
//				newAdresse.setLocalisation(new Ville("00000", "", "", 0, 0));
//				newSite.setAdresse(newAdresse);
				listeSite.add(0, newSite);
				comboBoxSite.setItems(listeSite);
				comboBoxSite.setValue(listeSite.get(0));
			}
		}
	}

	private void selectSite(Site siteSelect) {
		if (siteSelect != null) {
			repertoire.getCoursQBE().setSite(siteSelect);
			tableViewCours.setItems(repertoire.rechercher());
		}
	}

	@FXML
	public void onEnterDistance(ActionEvent ae) {
		try {
			int value = Integer.parseInt(rayonRecherche.getText());
			repertoire.getCoursQBE().setDistance(value);
			tableViewCours.setItems(repertoire.rechercher());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
