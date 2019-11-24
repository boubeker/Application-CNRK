package metier;

public class Ville {
	
	private String nomVille;
	private String insee;
	private String codePostale;
	private double longitude;
	private double latitude;
	
	public Ville(String insee, String nomVille, String codePostale, double longitude, double latitude) {
		this.insee = insee;
		this.codePostale = codePostale;
		this.nomVille = nomVille;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	


	public String getNomVille() {
		return nomVille;
	}

	public String getInsee() {
		return insee;
	}

	public String getCodePostale() {
		return codePostale;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codePostale == null) ? 0 : codePostale.hashCode());
		result = prime * result + ((insee == null) ? 0 : insee.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((nomVille == null) ? 0 : nomVille.hashCode());
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
		Ville other = (Ville) obj;
		if (codePostale == null) {
			if (other.codePostale != null)
				return false;
		} else if (!codePostale.equals(other.codePostale))
			return false;
		if (insee == null) {
			if (other.insee != null)
				return false;
		} else if (!insee.equals(other.insee))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (nomVille == null) {
			if (other.nomVille != null)
				return false;
		} else if (!nomVille.equals(other.nomVille))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return nomVille;
	}
}