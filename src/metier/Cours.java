package metier;

public class Cours {

	private Caracteristique speficite;
	private Instrument instrument;
	private Cycles cycle;
	private Site site;
	
	
	public Cours(Caracteristique speficite, Instrument instrument, Cycles cycle, Site site) {
		this.speficite = speficite;
		this.instrument = instrument;
		this.cycle = cycle;
		this.site = site;
	}

	public void setSpeficite(Caracteristique speficite) {
		this.speficite = speficite;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public void setCycle(Cycles cycle) {
		this.cycle = cycle;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Caracteristique getSpeficite() {
		return speficite;
	}


	public Instrument getInstrument() {
		return instrument;
	}


	public Cycles getCycle() {
		return cycle;
	}


	public Site getSite() {
		return site;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cycle == null) ? 0 : cycle.hashCode());
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((speficite == null) ? 0 : speficite.hashCode());
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
		Cours other = (Cours) obj;
		if (cycle == null) {
			if (other.cycle != null)
				return false;
		} else if (!cycle.equals(other.cycle))
			return false;
		if (instrument == null) {
			if (other.instrument != null)
				return false;
		} else if (!instrument.equals(other.instrument))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (speficite == null) {
			if (other.speficite != null)
				return false;
		} else if (!speficite.equals(other.speficite))
			return false;
		return true;
	}
	
	
	
}
