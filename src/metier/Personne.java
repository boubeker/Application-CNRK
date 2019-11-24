package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Personne {
	
	private int id;
	private String prenom;
	private String nom;
	private int rayon;
	private Adresse adresse;
	private ObservableList<Validations> listeDiplome;
	private Site siteAffecte;
	
	public Personne(int id, String prenom, String nom, int rayon) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom.toUpperCase();
		this.rayon = rayon;
		this.adresse = new Adresse("", "", "", "", "", "");
		this.listeDiplome = FXCollections.observableArrayList();
	}

	
	
	public Site getSiteAffecte() {
		return siteAffecte;
	}



	public void setSiteAffecte(Site siteAffecte) {
		this.siteAffecte = siteAffecte;
	}



	public void setId(int id) {
		this.id = id;
	}



	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setRayon(int rayon) {
		this.rayon = rayon;
	}


	public void setListeDiplome(ObservableList<Validations> listeDiplome) {
		this.listeDiplome = listeDiplome;
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

	public Adresse getAdresse() {
		return adresse;
	}

	public int getRayon() {
		return rayon;
	}

	public ObservableList<Validations> getListeDiplome() {
		return listeDiplome;
	}

	public void addDiplome(Validations newDiplome) {
		this.listeDiplome.add(newDiplome);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + id;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + rayon;
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
		Personne other = (Personne) obj;
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
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (rayon != other.rayon)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return prenom + " " + nom;
	}
}