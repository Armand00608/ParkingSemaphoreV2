package Parking.metier;

import java.util.concurrent.Semaphore;
import Parking.Controleur;

public class Parking
{
    private Controleur ctrl;
    private int nbPlaces;
    private int nbColonnes;
    private int nbLignes;
    private Semaphore semaphore;
    private boolean[] placesOccupees;
    private int[] vehiculeSurPlace;
    private boolean[] placeEstRemorque;

    public Parking(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.nbColonnes = 6;
        this.nbLignes = 3;
        this.nbPlaces = nbColonnes * nbLignes;
        this.semaphore = new Semaphore(nbPlaces, true);
        this.placesOccupees = new boolean[nbPlaces];
        this.vehiculeSurPlace = new int[nbPlaces];
        this.placeEstRemorque = new boolean[nbPlaces];
    }

    public void entrer(int idVehicule, boolean avecRemorque) throws InterruptedException
    {
        if (avecRemorque)
        {
            while (true)
            {
                semaphore.acquire(2);
                int[] places = trouverDeuxPlacesContigues();
                if (places != null)
                {
                    placesOccupees[places[0]] = true;
                    placesOccupees[places[1]] = true;
                    vehiculeSurPlace[places[0]] = idVehicule;
                    vehiculeSurPlace[places[1]] = idVehicule;
                    placeEstRemorque[places[0]] = true;
                    placeEstRemorque[places[1]] = true;
                    System.out.println("Vehicule " + idVehicule + " (remorque) entre -> places " + (places[0] + 1) + " et " + (places[1] + 1)
                        + " | Permits: " + semaphore.availablePermits() + " | En attente: " + semaphore.getQueueLength());
                    ctrl.incrementerVehiculeEntre();
                    ctrl.majAffichage();
                    return;
                }
                else
                {
                    semaphore.release(2);
                    Thread.sleep(100);
                }
            }
        }
        else
        {
            semaphore.acquire();
            int place = trouverPlaceLibre();
            if (place >= 0)
            {
                placesOccupees[place] = true;
                vehiculeSurPlace[place] = idVehicule;
                placeEstRemorque[place] = false;
                System.out.println("Vehicule " + idVehicule + " entre -> place " + (place + 1)
                    + " | Permits: " + semaphore.availablePermits() + " | En attente: " + semaphore.getQueueLength());
                ctrl.incrementerVehiculeEntre();
                ctrl.majAffichage();
            }
        }
    }

    public void sortir(int idVehicule, int duree, boolean avecRemorque) throws InterruptedException
    {
        int placesLiberees = 0;
        for (int i = 0; i < nbPlaces; i++)
        {
            if (vehiculeSurPlace[i] == idVehicule)
            {
                placesOccupees[i] = false;
                vehiculeSurPlace[i] = 0;
                placeEstRemorque[i] = false;
                placesLiberees++;
            }
        }
        System.out.println("Vehicule " + idVehicule + (avecRemorque ? " (remorque)" : "") + " sort (" + placesLiberees + " place(s), duree: " + duree + "ms)"
            + " | Permits: " + (semaphore.availablePermits() + placesLiberees) + " | En attente: " + semaphore.getQueueLength());
        ctrl.majAffichage();
        semaphore.release(placesLiberees);
    }

    private int[] trouverDeuxPlacesContigues()
    {
        for (int col = 0; col < nbColonnes; col++)
        {
            for (int lig = 0; lig < nbLignes - 1; lig++)
            {
                int place1 = lig * nbColonnes + col;
                int place2 = (lig + 1) * nbColonnes + col;
                if (!placesOccupees[place1] && !placesOccupees[place2])
                {
                    return new int[]{place1, place2};
                }
            }
        }
        return null;
    }

    private int trouverPlaceLibre()
    {
        for (int i = 0; i < nbPlaces; i++)
        {
            if (!placesOccupees[i]) return i;
        }
        return -1;
    }

    public int getNbPlaces()             { return nbPlaces; }
    public int getNbColonnes()           { return nbColonnes; }
    public int getNbLignes()             { return nbLignes; }
    public int getAvailablePermits()     { return semaphore.availablePermits(); }
    public int getQueueLength()          { return semaphore.getQueueLength(); }

    public int getNbPlacesOccupees()
    {
        int count = 0;
        for (boolean b : placesOccupees) if (b) count++;
        return count;
    }

    public boolean[] getPlacesOccupees()    { return placesOccupees; }
    public int[] getVehiculeSurPlace()      { return vehiculeSurPlace; }
    public boolean[] getPlaceEstRemorque()  { return placeEstRemorque; }
}