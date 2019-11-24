package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Departement {
	
	private int id;
	private String code;
	private String nom;
	private ObservableList<Ville> listeVille;
	
	public Departement( int id, String nom, String codeDepartement) {
		this.id = id;
		this.code = codeDepartement;
		this.nom = nom ;
		listeVille = FXCollections.observableArrayList();
	}

	public ObservableList<Ville> getListeVille() {
		return listeVille;
	}

	public void addVille(Ville newVille) {
		this.listeVille.add(newVille);
	}
	

	public String getCode() {
		return code;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return nom;
	}
	
	
}
