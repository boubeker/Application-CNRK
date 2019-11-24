package metier;

public class Adresse {
	
	private String identite;
	private String complementInterieur;
	private String complementExterieur;
	private String numLibelle;
	private String lieuParticulier;
	private Ville localisation;
	private String pays;
	
	public Adresse(String identite, String complementInterieur, String complementExterieur, String numLibelle, String lieuParticulier,  String pays) {
		this.identite = identite;
		this.complementInterieur = (complementInterieur == null ) ? "" : complementInterieur;
		this.complementExterieur = (complementExterieur == null ) ? "" : complementExterieur;
		this.numLibelle = numLibelle;
		this.lieuParticulier = (lieuParticulier == null ) ? "" : lieuParticulier.toUpperCase();
		this.localisation = new Ville("00000", "", "", 0, 0);
		this.pays = "Kaco";
		
	}
	

	public void setLocalisation(Ville localisation) {
		this.localisation = localisation;
	}


	public Ville getLocalisation() {
		return localisation;
	}

	public String getNumLibelle() {
		return numLibelle;
	}
	
	


	public String getComplementInterieur() {
		return complementInterieur;
	}


	public String getComplementExterieur() {
		return complementExterieur;
	}


	public String getLieuParticulier() {
		return lieuParticulier;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complementExterieur == null) ? 0 : complementExterieur.hashCode());
		result = prime * result + ((complementInterieur == null) ? 0 : complementInterieur.hashCode());
		result = prime * result + ((identite == null) ? 0 : identite.hashCode());
		result = prime * result + ((lieuParticulier == null) ? 0 : lieuParticulier.hashCode());
		result = prime * result + ((localisation == null) ? 0 : localisation.hashCode());
		result = prime * result + ((numLibelle == null) ? 0 : numLibelle.hashCode());
		result = prime * result + ((pays == null) ? 0 : pays.hashCode());
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
		Adresse other = (Adresse) obj;
		if (complementExterieur == null) {
			if (other.complementExterieur != null)
				return false;
		} else if (!complementExterieur.equals(other.complementExterieur))
			return false;
		if (complementInterieur == null) {
			if (other.complementInterieur != null)
				return false;
		} else if (!complementInterieur.equals(other.complementInterieur))
			return false;
		if (identite == null) {
			if (other.identite != null)
				return false;
		} else if (!identite.equals(other.identite))
			return false;
		if (lieuParticulier == null) {
			if (other.lieuParticulier != null)
				return false;
		} else if (!lieuParticulier.equals(other.lieuParticulier))
			return false;
		if (localisation == null) {
			if (other.localisation != null)
				return false;
		} else if (!localisation.equals(other.localisation))
			return false;
		if (numLibelle == null) {
			if (other.numLibelle != null)
				return false;
		} else if (!numLibelle.equals(other.numLibelle))
			return false;
		if (pays == null) {
			if (other.pays != null)
				return false;
		} else if (!pays.equals(other.pays))
			return false;
		return true;
	}


	@Override
	public String toString() {
		if (complementInterieur.equals("") && complementExterieur.equals("") && lieuParticulier.equals("")) {
			return numLibelle + " " + complementInterieur + " " + complementExterieur + " " + lieuParticulier;
		} else {
			return numLibelle + "\n" + complementInterieur + " " + complementExterieur + " " + lieuParticulier;
		}
	}
	
	
}
