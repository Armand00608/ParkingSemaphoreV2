package Parking.metier;

import java.util.concurrent.Semaphore;
import Parking.Controleur;

public class Parking 
{
    Controleur ctrl;
    private Semaphore portail;
    private Semaphore mutex; // Pour protéger l'accès au compteur
    private int nbPlaces;
    private int nbPlacesOccupees;
    
    public Parking(Controleur ctrl) 
    {
        this.nbPlaces = 18;
        this.portail = new Semaphore(nbPlaces);
        this.mutex = new Semaphore(1); // Mutex binaire
        this.nbPlacesOccupees = 0;
        System.out.println("=== Parking créé avec " + nbPlaces + " places ===");
        this.ctrl = ctrl;
    }
    
    public int getNbPlaces() 
    {
        return nbPlaces;
    }
    
    public int getNbPlacesOccupees() 
    {
        return nbPlacesOccupees;
    }
    
    public void entrer(int idVehicule) 
    {
        try 
        {
            System.out.println("[Véhicule " + idVehicule + "] Attend à l'entrée...");
            
            portail.acquire(); // Bloque si parking plein
            
            mutex.acquire();
            nbPlacesOccupees++;
            System.out.println("[Véhicule " + idVehicule + "] ENTRE (" + nbPlacesOccupees + "/" + nbPlaces + ")");
            mutex.release();
            
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void sortir(int idVehicule, int dureeStationnement) 
    {
        try 
        {
            mutex.acquire();
            nbPlacesOccupees--;
            System.out.println("[Véhicule " + idVehicule + "] SORT (" + nbPlacesOccupees + "/" + nbPlaces + ") - " + dureeStationnement / 1000 + "s");
            mutex.release();
            
            portail.release();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        this.ctrl.retirerVehicule(idVehicule);
    }
}
