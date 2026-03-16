package Parking;
import java.util.ArrayList;

import Parking.metier.*;
import Parking.ihm.*;

public class Controleur 
{
    private static final int NB_VEHICULES = 300;

    private Parking parking;
    private ArrayList<Vehicule> vehicules;
    private FrameParking frame;
    private int nbVehiculeEntre = 0;
    
    public Controleur()
    {		
        parking = new Parking(this);
        vehicules = new ArrayList<>();
        
        // Créer l'IHM
        frame = new FrameParking(this);
        
        System.out.println("=== Simulation avec " + NB_VEHICULES + " véhicules ===\n");
        
        for (int i = 0; i < NB_VEHICULES; i++) 
        {
            Vehicule vehicule = new Vehicule(parking, i + 1);
            this.vehicules.add(vehicule);
            vehicule.start();
            
            try 
            {
                this.majAffichage();
                Thread.sleep((int)(Math.random() * 300) + 100);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
        this.majAffichage();
    }
    public void retirerVehicule(int idVehicule) 
    { 
        for (Vehicule vehicule : vehicules) 
        {
            if (vehicule.getIdVehicule()==idVehicule) 
            {
                vehicules.remove(vehicule);  
                this.majAffichage(); break;
            }
        }
    }
    
    // Getters pour l'IHM
    public int getNbPlaces() 
    {
        return parking.getNbPlaces();
    }
    
    public int getNbPlacesOccupees() 
    {
        return parking.getNbPlacesOccupees();
    }

    public int getNbVehiculesDehors()
    {
        return NB_VEHICULES - this.getNbVehiculeEntre();
    }
    public ArrayList<String> getVehiculesDehors() 
    { 
        ArrayList<String> liste = new ArrayList<>(); 
        for (Vehicule v : vehicules) 
        { 
                liste.add(String.valueOf(v.getIdVehicule())); 
        } 
            return liste; 
    }
    
    public boolean[] getPlacesOccupees()
    {
        return parking.getPlacesOccupees();
    }
    
    public int[] getVehiculeSurPlace()
    {
        return parking.getVehiculeSurPlace();
    }

    public boolean[] getPlaceRemorque()
    {
        return parking.getPlaceRemorque();
    }
    
    /**
     * Met à jour l'affichage du parking
     */
    public void majAffichage() 
    {
        if (this.frame != null)
            this.frame.maj();
    }
    
    public static void main(String[] args) 
    {
        new Controleur();
    }
    public void incrementerVehiculeEntre() {
        nbVehiculeEntre++;
    }
    public int getNbVehiculeEntre() {
        return nbVehiculeEntre;
    }
}