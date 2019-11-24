package vue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import dao.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import metier.Utilisateur;

public class AuthentificationController {

	@FXML
	private JFXButton buttonConnexion;

	@FXML
	private JFXTextField textFieldUtilisateur;

	@FXML
	private JFXPasswordField textFieldMotDePasse;
	
	@FXML
	private ImageView imageBackground;

	private Main menuApp;
	private Image image;

	@FXML
	private void initialize() {
		textFieldUtilisateur.textProperty()
				.addListener((observable, oldValue, newValue) -> saisieIdentifiant(newValue));
		textFieldMotDePasse.textProperty().addListener((observable, oldValue, newValue) -> saisieMotDePasse(newValue));
		image = new Image(getClass().getResourceAsStream("../image/background.jpg"));
		imageBackground.setImage(image);
	}

	private void saisieMotDePasse(String password) {
		if (password.length() > 0) {
			textFieldMotDePasse.setUnFocusColor(Color.web("#00ff19"));
		} else {
			textFieldMotDePasse.setUnFocusColor(Color.web("#ff0000"));
		}
	}

	private void saisieIdentifiant(String identifiant) {
		if (identifiant.length() > 0) {
			textFieldUtilisateur.setUnFocusColor(Color.web("#00ff19"));
		} else {
			textFieldUtilisateur.setUnFocusColor(Color.web("#ff0000"));
		}
	}

	public void setMenuApp(Main menuApp) {
		this.menuApp = menuApp;
	}

	@FXML
	private void setConnexion() {
		if (new UtilisateurDAO().rechercherUtilisateur(
				new Utilisateur(textFieldUtilisateur.getText(), textFieldMotDePasse.getText()))) {
			menuApp.showAdministrer();
			menuApp.fermerFenetreAuthentification();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erreur");
			alert.setHeaderText("L'utilisateur est inconnu !");
			alert.showAndWait();
		}
	}
}
