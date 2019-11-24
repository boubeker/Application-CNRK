package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Instrument {
	
	private int id;
	private String nom;
	private Caracteristique specificite;
	private ObservableList<Instrument> instrumentLier;
	
	public Instrument(int id, String nom) {
		this.id = id;
		this.nom = nom;
		specificite = null;
		instrumentLier = FXCollections.observableArrayList();
	}
	
	public void setInstrumentLier(Instrument instruCompatible) {
		instrumentLier.add(instruCompatible);
	}

	public ObservableList<Instrument> getInstrumentLier() {
		return instrumentLier;
	}

	public void setSpecificite(Caracteristique newSpecificite) {
		this.specificite = newSpecificite;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public Caracteristique getSpecificite() {
		return specificite;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrument other = (Instrument) obj;
		return id == other.id && nom.equals(other.nom);
	}
	
	
	@Override
	public String toString() {
		return nom;
	}


}