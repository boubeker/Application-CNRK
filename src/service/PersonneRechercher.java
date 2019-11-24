package service;

import metier.Cycles;
import metier.Instrument;
import metier.Site;

public class PersonneRechercher {
    private int id;
    private String prenom;
    private String nom;
    private int rayon;
    private Instrument instrument;
    private Cycles cycle;
    private Site site;

    public PersonneRechercher(int id, String prenom, String nom, int rayon) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom.toUpperCase();
        this.rayon = rayon;
        instrument = new Instrument(0, "");
        cycle =  new Cycles(0, "", false);
        site = new Site(0, "", "");
    }


    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getRayon() {
        return rayon;
    }

    public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}


	public Cycles getCycle() { return cycle; }

    public Instrument getInstrument() { return instrument; }

    public void setPrenom(String prenom) { this.prenom = prenom; }

    public void setNom(String nom) { this.nom = nom; }

    public void setRayon(int rayon) { this.rayon = rayon; }

    public void setInstrument(Instrument instrument) { this.instrument = instrument; }

    public void setCycle(Cycles cycle) { this.cycle = cycle; }

    
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cycle == null) ? 0 : cycle.hashCode());
		result = prime * result + id;
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + rayon;
		result = prime * result + ((site == null) ? 0 : site.hashCode());
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
		PersonneRechercher other = (PersonneRechercher) obj;
		if (cycle == null) {
			if (other.cycle != null)
				return false;
		} else if (!cycle.equals(other.cycle))
			return false;
		if (id != other.id)
			return false;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (rayon != other.rayon)
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		return true;
	}


	@Override
    public String toString() {
        return prenom + nom;
    }
}
