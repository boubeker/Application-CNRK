package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cycles {
	
	private int id;
	private String nom;
	private Cycles cyclePrecedent;
	private Cycles cycleSuivant;
	private boolean examen;
	private ObservableList<Cycles> cycleEnseigner;
	
	public Cycles(int id, String nom, boolean examen) {
		this.id = id;
		this.cyclePrecedent = null;
		this.cycleSuivant = null;
		this.nom = nom;
		this.examen = examen;
		cycleEnseigner = FXCollections.observableArrayList();
	}


	public void setCycleSuivant(Cycles cycleSuivant) {
		this.cycleSuivant = cycleSuivant;
	}

	public void setCyclePrecedent(Cycles cyclePrecedent) {
		this.cyclePrecedent = cyclePrecedent;
	}

	public ObservableList<Cycles> getCycleEnseigner() {
		return cycleEnseigner;
	}

	public void setCycleEnseigner(Cycles cycleEnseigner) {
		this.cycleEnseigner.add(cycleEnseigner);
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public Cycles getCycleSuivant() {
		return cycleSuivant;
	}

	public Cycles getCyclePrecedent() {
		return cyclePrecedent;
	}

	public boolean isExamen() {
		return examen;
	}


	@Override
	public String toString() {
		return nom;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (examen ? 1231 : 1237);
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
		Cycles other = (Cycles) obj;
		if (examen != other.examen)
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	
	
}