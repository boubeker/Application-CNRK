package metier;

public class Site {
	
	private int id;
	private String nom;
	private String numTelephone;
	private Adresse adresse;
	
	public Site(int id, String nom , String numTelephone) {
		this.id = id;
		this.nom = nom;
		this.numTelephone = numTelephone;
		this.adresse = new Adresse("", "", "", "", "", "");
	}

	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getNumTelephone() {
		return numTelephone;
	}

	public int getId() {
		return id;
	}

	public Adresse getAdresse() {
		return adresse;
	}
	
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((numTelephone == null) ? 0 : numTelephone.hashCode());
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
		Site other = (Site) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (numTelephone == null) {
			if (other.numTelephone != null)
				return false;
		} else if (!numTelephone.equals(other.numTelephone))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return  nom;
	}
}