package service;

import dao.CaracteristiqueDAO;
import dao.CoursRechercherDAO;
import dao.CyclesDAO;
import dao.DepartementDAO;
import dao.InstrumentDAO;
import dao.RegionDAO;
import dao.SiteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metier.Caracteristique;
import metier.Cours;
import metier.Cycles;
import metier.Departement;
import metier.Instrument;
import metier.Region;
import metier.Site;

public class CNRKBean {
	
	private ObservableList<Caracteristique> listeSpecificite;
	private ObservableList<Instrument> listeInstrument;
	private ObservableList<Cycles> listeCycle;
	private ObservableList<Site> listeSite;
	private ObservableList<Cours> listeCoursQBE;
	private ObservableList<Region> listeRegion;
	private ObservableList<Departement> listeDepartement;
	
	private int copieHashCodeQBE;	// permet de faire la vérification de modification de coursQBE
	private CoursRechercher coursQBE;
	
	public CNRKBean() {
		listeSpecificite = FXCollections.observableArrayList(new CaracteristiqueDAO().getAll());
		listeSpecificite.add(0, new Caracteristique(0, "Famille d'instruments"));
		
		listeInstrument = FXCollections.observableArrayList(new InstrumentDAO().getAll());
		
		listeCycle = FXCollections.observableArrayList(new CyclesDAO().getAll());
		listeCycle.add(0, new Cycles(0, "Cycles", false));
		listeCycle.remove(listeCycle.size()-1);
		
		listeRegion = FXCollections.observableArrayList(new RegionDAO().getAll());
		listeRegion.add(0,new Region(0, "Choisir Region"));
		
		listeDepartement = FXCollections.observableArrayList(new DepartementDAO().getAll());
		listeDepartement.add(0, new Departement(0, "Choisir Departement", ""));
		
		listeSite = FXCollections.observableArrayList(new SiteDAO().getAll());
		listeSite.add(0, new Site(0, "Site", ""));
		
		listeCoursQBE = FXCollections.observableArrayList(new CoursRechercherDAO().getAll());
		
		coursQBE = new CoursRechercher(new Caracteristique(0, ""), new Instrument(0, ""), new Cycles(0, "", false), new Site(0, "", ""), 0);
		
		copieHashCodeQBE = coursQBE.hashCode();
	}
	
	

	public ObservableList<Caracteristique> getListeSpecificite() {
		return listeSpecificite;
	}

	public ObservableList<Instrument> getListeInstrument() {
		return listeInstrument;
	}

	public ObservableList<Cycles> getListeCycle() {
		return listeCycle;
	}

	public ObservableList<Site> getListeSite() {
		return listeSite;
	}
	
	public ObservableList<Cours> getListeCoursQBE() {
		return listeCoursQBE;
	}

	public CoursRechercher getCoursQBE() {
		return coursQBE;
	}

	public ObservableList<Region> getListeRegion() {
		return listeRegion;
	}

	public ObservableList<Departement> getListeDepartement() {
		return listeDepartement;
	}
	
	/*
	 * Méthode qui lance la recherche dans la dao
	 */
	public ObservableList<Cours> rechercher() {
		if(coursQBE.hashCode() != copieHashCodeQBE)
		{
			listeCoursQBE = FXCollections.observableArrayList(new CoursRechercherDAO().getRecherche(coursQBE));
			copieHashCodeQBE = coursQBE.hashCode();
		}
		return listeCoursQBE;
	}

	public Instrument getInstrumentByName(String recherche)
	{
		for(Instrument i : listeInstrument)
		{
			if (i.getNom().equals(recherche)
			) {

				return i;
			}
		}
		return null;
	}
	
}
