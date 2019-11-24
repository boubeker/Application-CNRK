package service;

import dao.CaracteristiqueDAO;
import dao.CyclesDAO;
import dao.InstrumentDAO;
import dao.PersonneDAO;
import dao.SiteDAO;
import dao.ValidationsDAO;
import dao.VilleDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metier.Caracteristique;
import metier.Cycles;
import metier.Instrument;
import metier.Personne;
import metier.Site;
import metier.Validations;
import metier.Ville;

public class AdministrateurBean {

	private ObservableList<Personne> listePersonne;
	private ObservableList<Cycles> listeCycle;
	private ObservableList<Instrument> listeInstrument;
	private ObservableList<Site> listeSite;
	private ObservableList<Caracteristique> listeSpecificite;
	private ObservableList<Ville> listeVille;

	private PersonneRechercher personneRechercher;
	private int copieHashCodePersonne;
	private Personne personneSelected;

	private ObservableList<Validations> listeValidationAsupprimer;
	private ObservableList<Validations> listeValidationAajouter;

	private Site siteSelected;

	public AdministrateurBean() {
		listePersonne = FXCollections.observableArrayList(new PersonneDAO().getAll());

		listeCycle = FXCollections.observableArrayList(new CyclesDAO().getAll());

		listeInstrument = FXCollections.observableArrayList(new InstrumentDAO().getAll());

		listeSite = FXCollections.observableArrayList(new SiteDAO().getAll());

		listeSpecificite = FXCollections.observableArrayList(new CaracteristiqueDAO().getAll());
		listeSpecificite.add(0, new Caracteristique(0, "Famille d'instruments"));

		listeVille = FXCollections.observableArrayList(new VilleDAO().getAll());

		personneRechercher = new PersonneRechercher(0, "", "", 0);
		copieHashCodePersonne = personneRechercher.hashCode();

		listeValidationAsupprimer = FXCollections.observableArrayList();
		listeValidationAajouter = FXCollections.observableArrayList();
	}

	public ObservableList<Personne> getListePersonne() {
		return listePersonne;
	}

	public ObservableList<Cycles> getListeCycle() {
		return listeCycle;
	}

	public ObservableList<Instrument> getListeInstrument() {
		return listeInstrument;
	}

	public ObservableList<Site> getListeSite() {
		return listeSite;
	}

	public ObservableList<Caracteristique> getListeSpecificite() {
		return listeSpecificite;
	}

	public PersonneRechercher getPersonneRechercher() {
		return personneRechercher;
	}

	public Personne getPersonneSelected() {
		return personneSelected;
	}

	public void setPersonneSelected(Personne personneSelected) {
		this.personneSelected = personneSelected;
	}

	public ObservableList<Ville> getListeVille() {
		return listeVille;
	}

	public ObservableList<Validations> getListeValidationAsupprimer() {
		return listeValidationAsupprimer;
	}

	public void setSiteSelected(Site site) {
		this.siteSelected = site;
	}
	
	public Site getSiteSelected() {
		return siteSelected;
	}
	
	public void getSitePersonneSelected()
	{
	    personneSelected.setSiteAffecte(new SiteDAO().getSiteAffected(personneSelected));
	}

	public boolean supprimerPersonne() {
		if (new PersonneDAO().supprimerPersonne(personneSelected)) {
			listePersonne.remove(personneSelected);
			return true;
		} else {
			return false;
		}
	}

	public boolean ajouterPersonne(Personne personne) {
		int insertedId = new PersonneDAO().ajouterPersonne(personne);
		if (insertedId != -1) {
			personne.setId(insertedId);

			System.out.println(insertedId);
			// ajouter diplome
			for (Validations validation : personneSelected.getListeDiplome()) {
				validation.setPersonne(personne);
				new ValidationsDAO().ajouterModifierValidations(validation);
			}
			listePersonne = FXCollections.observableArrayList(new PersonneDAO().getRecherche(personneRechercher));
			return true;
		} else {
			return false;
		}

	}

	public boolean modifierPersonne(Personne personne) {
		if (new PersonneDAO().modifierPersonne(personne)) {
			for (Validations validation : listeValidationAajouter) {
				new ValidationsDAO().ajouterModifierValidations(validation);
			}
			for (Validations validation : listeValidationAsupprimer) {
				new ValidationsDAO().supprimerValidation(validation);
			}
			listeValidationAsupprimer = FXCollections.observableArrayList();
			listePersonne = FXCollections.observableArrayList(new PersonneDAO().getRecherche(personneRechercher));
			listeValidationAajouter.clear();
			return true;
		}
		else {
			return false;

		}
	}
	
	public boolean ajouterSite() {
		if (new SiteDAO().ajouterSite(siteSelected)) {
			listeSite = FXCollections.observableArrayList(new SiteDAO().getAll());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean modifierSite() {
		if (new SiteDAO().modifierSite(siteSelected)) {
			listeSite = FXCollections.observableArrayList(new SiteDAO().getAll());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean supprimerSite() {
		if (new SiteDAO().supprimerSite(siteSelected)) {
			listeSite.remove(siteSelected);
			return true;
		} else {
			return false;
		}
	}

	public ObservableList<Validations> getListeValidationAajouter() {
		return listeValidationAajouter;
	}

	public void addListeValidationAajouter(Validations validationAajouter) {
		this.listeValidationAajouter.add(validationAajouter);
	}

	public void addValidationAsupprimer(Validations validationAsupprimer) {
		this.listeValidationAsupprimer.add(validationAsupprimer);
	}

	public ObservableList<Personne> rechercher() {
		if (personneRechercher.hashCode() != copieHashCodePersonne) {
			listePersonne = FXCollections.observableArrayList(new PersonneDAO().getRecherche(personneRechercher));
			copieHashCodePersonne = personneRechercher.hashCode();
		}
		return listePersonne;
	}

	public Instrument getInstrumentByName(String recherche) {
		for (Instrument i : listeInstrument) {
			if (i.getNom().equals(recherche)) {
				return i;
			}
		}
		return null;
	}

	public Site getSiteByName(String recherche) {
		for (Site i : listeSite) {
			if (i.getNom().equals(recherche)) {

				return i;
			}
		}
		return null;
	}

	public Ville getVilleByName(String recherche) {
		for (Ville i : listeVille) {
			if (i.getNomVille().equals(recherche)) {
				return i;
			}
		}
		return null;
	}

}
