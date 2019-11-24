package metier;

public class Utilisateur {
	
	private String identifiant;
	private String hashMotDePasse;
	
	public Utilisateur(String identifiant, String hashMotDePasse) {
		this.identifiant = identifiant;
		this.hashMotDePasse = hashMotDePasse;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public String getHashMotDePasse() {
		return hashMotDePasse;
	}
	
	
}
