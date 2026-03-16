package Parking.metier;

public class Vehicule extends Thread
{
    private Parking parking;
    private int id;
    private int dureeStationnement;
    
    public Vehicule(Parking parking, int id)
    {
        this.parking = parking;
        this.id = id;
        this.dureeStationnement = (int)(Math.random() * 8000) + 3000; // 3 à 11 secondes
    }
    
    public int getIdVehicule()
    {
        return id;
    }
    
    public void run() 
    {
        try 
        {
            parking.entrer(id);
            Thread.sleep(dureeStationnement);
            parking.sortir(id, dureeStationnement);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
}
