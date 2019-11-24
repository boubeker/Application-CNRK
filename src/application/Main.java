package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.AdministrateurBean;
import service.CNRKBean;
import vue.AdministrateurControlller;
import vue.AjouterEnseignantController;
import vue.AjouterValidationsController;
import vue.AuthentificationController;
import vue.CNRKappController;
import vue.EditSiteController;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane layout;
	private CNRKBean repertoire;
	private AdministrateurBean adminBean;
	private Stage ajouterEnseignantStage;
	private AdministrateurControlller adminControlleur;
	private Stage ajouterValidationStage;
	private Stage authentificationStage;
	private Stage editSiteStage;

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		showCours();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void showCours() {
		try {
			repertoire = new CNRKBean();
			this.primaryStage.setTitle("CNRK Application");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../vue/CNRKapp.fxml"));
			layout = loader.load();
			Scene scene = new Scene(layout);
			primaryStage.setScene(scene);
			CNRKappController cnrkAppController = loader.getController();
			cnrkAppController.setMenuApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAuthentification() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../vue/Authentification.fxml"));
			layout = loader.load();
			
			authentificationStage = new Stage();
			authentificationStage.initModality(Modality.WINDOW_MODAL);
			authentificationStage.initOwner(primaryStage);
			authentificationStage.setResizable(false);
			
			Scene scene = new Scene(layout);
			authentificationStage.setScene(scene);
			
			AuthentificationController authentificationController = loader.getController();
			authentificationController.setMenuApp(this);
			
			authentificationStage.showAndWait();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAdministrer() {
		try {
			adminBean = new AdministrateurBean();
			this.primaryStage.setTitle("Administrer");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../vue/Administrateur.fxml"));
			layout = loader.load();
			Scene scene = new Scene(layout);
			primaryStage.setScene(scene);
			adminControlleur = loader.getController();
			adminControlleur.setMenuApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showAjouterEnseignant() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../vue/AjouterEnseignant.fxml"));
			layout = loader.load();

			ajouterEnseignantStage = new Stage();
			ajouterEnseignantStage.setTitle("Fiche enseignant");
			ajouterEnseignantStage.initModality(Modality.WINDOW_MODAL);
			ajouterEnseignantStage.initOwner(primaryStage);
			ajouterEnseignantStage.setResizable(false);
			
			Scene scene = new Scene(layout);
			ajouterEnseignantStage.setScene(scene);

			AjouterEnseignantController ajtEnsController = loader.getController();
			ajtEnsController.setDialogStage(ajouterEnseignantStage);
			ajtEnsController.setMenuApp(this);
			ajouterEnseignantStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					ajtEnsController.annuler();

				}
			});
			ajouterEnseignantStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showAjouterValidations() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../vue/AjouterValidation.fxml"));
			layout = loader.load();
			
			ajouterValidationStage = new Stage();
			ajouterValidationStage.initModality(Modality.WINDOW_MODAL);
			ajouterValidationStage.initOwner(primaryStage);
			ajouterValidationStage.setResizable(false);
			
			Scene scene = new Scene(layout);
			ajouterValidationStage.setScene(scene);
			
			AjouterValidationsController ajtValidationController = loader.getController();
			ajtValidationController.setMenuApp(this);
			
			ajouterValidationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					fermerFenetreAjouterValidations();

				}
			});
			
			ajouterValidationStage.showAndWait();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showEditSite() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../vue/EditSite.fxml"));
			layout = loader.load();
			
			editSiteStage = new Stage();
			editSiteStage.initModality(Modality.WINDOW_MODAL);
			editSiteStage.initOwner(primaryStage);
			editSiteStage.setResizable(false);
			
			Scene scene = new Scene(layout);
			editSiteStage.setScene(scene);
			
			EditSiteController editSiteController = loader.getController();
			editSiteController.setMenuApp(this);
			
			editSiteStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					fermerFenetreEditSite();

				}
			});
			
			editSiteStage.showAndWait();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public Stage getAuthentificationStage() {
		return authentificationStage;
	}

	public void refreshAdministrer() {
		adminControlleur.refresh();
	}

	public void fermerFenetreAjouterEnseignant() {
		ajouterEnseignantStage.close();
	}

	public void fermerFenetreAjouterValidations() {
		ajouterValidationStage.close();
	}
	
	public void fermerFenetreAuthentification() {
		authentificationStage.close();
	}
	
	public void fermerFenetreEditSite() {
		editSiteStage.close();
	}
	
	public AdministrateurBean getAdminBean() {
		return adminBean;
	}

	public CNRKBean getRepertoire() {
		return repertoire;
	}

}
