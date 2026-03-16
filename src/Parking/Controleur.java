package Parking;
import java.awt.List;
import java.util.ArrayList;

import Parking.metier.*;
import Parking.ihm.*;

public class Controleur 
{
    private Parking parking;
    private ArrayList<Vehicule> vehicules;
    private FrameParking frame;
    
    public Controleur()
    {		
        parking = new Parking(this);
        vehicules = new ArrayList<>();
        
        // Créer l'IHM
        frame = new FrameParking(this);
        
        int nbVehicules = 300;
        
        System.out.println("=== Simulation avec " + nbVehicules + " véhicules ===\n");
        
        for (int i = 0; i < nbVehicules; i++) 
        {
            Vehicule vehicule = new Vehicule(parking, i + 1);
            this.vehicules.add(vehicule);
            vehicule.start();
            
            // Délai entre chaque arrivée de véhicule (500-3500ms)
            try 
            {
                Thread.sleep((int)(Math.random() * 3000) + 500);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }
    public void retirerVehicule(int idVehicule) 
    { 
        for (Vehicule vehicule : vehicules) 
        {
            if (vehicule.getIdVehicule()==idVehicule) 
            {
                vehicules.remove(vehicule); break; 
            
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
        return vehicules.size();
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
    
    /**
     * Met à jour l'affichage du parking
     */
    public void majAffichage() 
    {
        if (frame != null) 
        {
            frame.majAffichage();
        }
    }
    
    public static void main(String[] args) 
    {
        new Controleur();
    }
}