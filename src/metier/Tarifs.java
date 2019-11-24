package metier;

public class Tarifs {
	
	private int id;
	private int valeur;
	private Instrument instrument;
	private Cycles cycle;
	private Annee annee;
	
	public Tarifs(int id, int valeur) {
		this.id = id;
		this.valeur = valeur;
		this.instrument = null;
		this.cycle = null;
		this.annee = null;
	}

	
	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Cycles getCycle() {
		return cycle;
	}

	public void setCycle(Cycles cycle) {
		this.cycle = cycle;
	}

	public Annee getAnnee() {
		return annee;
	}

	public void setAnnee(Annee annee) {
		this.annee = annee;
	}

	public int getId() {
		return id;
	}

	public int getValeur() {
		return valeur;
	}

	
	@Override
	public String toString() {
		return "Tarifs [ " + id + ", " + valeur + ", " + instrument + ", " + cycle
				+ "," + annee + "]";
	}
}