package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Caracteristique {
	
	private int id;
	private String nom;
	private ObservableList<Instrument> listeInstrument;
	
	public Caracteristique(int id, String nom) {
		this.id = id;
		this.nom = nom;
		this.listeInstrument = FXCollections.observableArrayList();
	}

	
	
	public ObservableList<Instrument> getListeInstrument() {
		return listeInstrument;
	}



	public void addInstrument(Instrument newInstrument) {
		listeInstrument.add(newInstrument);
	}



	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caracteristique other = (Caracteristique) obj;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return nom;
	}
}