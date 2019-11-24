package metier;

public class AnneesEtudes {
	
	private int id;
	private int annee;
	private AnneesEtudes anneeSuivante;
	private AnneesEtudes anneePrecedente;
	private Cycles cycleInstru;
	
	public AnneesEtudes (int id, int annee) {
		this.id = id;
		this.annee = annee;
		this.anneeSuivante = null;
		this.anneePrecedente = null;
		this.cycleInstru = null;
	}

	
	public void setAnneeSuivante(AnneesEtudes anneeSuivante) {
		this.anneeSuivante = anneeSuivante;
	}

	public void setAnneePrecedente(AnneesEtudes anneePrecedente) {
		this.anneePrecedente = anneePrecedente;
	}

	public Cycles getCycleInstru() {
		return cycleInstru;
	}

	public void setCycleInstru(Cycles cycleInstru) {
		this.cycleInstru = cycleInstru;
	}

	public int getId() {
		return id;
	}

	public int getAnnee() {
		return annee;
	}

	public AnneesEtudes getAnneeSuivante() {
		return anneeSuivante;
	}

	public AnneesEtudes getAnneePrecedente() {
		return anneePrecedente;
	}

	@Override
	public String toString() {
		return "AnneesEtudes [ " + id + ", " + annee + ", " + anneeSuivante + ", "
				+ anneePrecedente + ", " + cycleInstru + "]";
	}
}