package Parking.metier;

public class Vehicule extends Thread
{
    private Parking parking;
    private int id;
    private int dureeStationnement;
    private boolean hasRemorque;
    
    public Vehicule(Parking parking, int id)
    {
        this.parking = parking;
        this.id = id;
        this.dureeStationnement = (int)(Math.random() * 10000) + 5000; // entre 5 et 15 secondes
        this.hasRemorque = Math.random() < 0.3; // 30% de chance d'avoir une remorque
    }
    
    public int getIdVehicule()
    {
        return id;
    }

    public boolean hasRemorque()
    {
        return hasRemorque;
    }
    
    public void run() 
    {
        try 
        {
            parking.entrer(id, hasRemorque);
            Thread.sleep(dureeStationnement);
            parking.sortir(id, dureeStationnement, hasRemorque);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
}
