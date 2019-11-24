package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Region {
	
	private int id;
	private String nom;
	private ObservableList<Departement> listeDepartement;
	
	public Region(int id, String nom) {
		this.id = id;
		this.nom = nom;
		this.listeDepartement = FXCollections.observableArrayList();
	}

	public ObservableList<Departement> getListeDepartement() {
		return listeDepartement;
	}

	public void addDepartement(Departement newDepartement) {
		this.listeDepartement.add(newDepartement);
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return  nom ;
	}
	
	
}
