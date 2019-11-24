package metier;

import java.util.Date;

public class Validations {
	
	private int id;
	private Date dateObtention;
	private Instrument instrument;
	private Cycles cycle;
	private Personne personne;
	
	public Validations(int id, Date dateObtention) {
		this.id = id;
		this.dateObtention = dateObtention;
		personne = new Personne(0, "", "", 0);
	}
	
	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
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


	public int getId() {
		return id;
	}

	public Date getDateObtention() {
		return dateObtention;
	}

	
	@Override
	public String toString() {
		return "Validations [ " + id + ", " + dateObtention + ", " + instrument;
	}
}