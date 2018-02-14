package Assignment2;

import java.io.IOException;
import java.util.ArrayList;

class Passenger
{
    String id;
    String name;
    ArrayList<Flight> my_flights;
    
    public Passenger(String pass_id, String pass_name)
    {
        id = pass_id;
        name = pass_name;
        my_flights = new ArrayList<Flight>();
    }
}

class Flight
{
    String flight_number;
    ArrayList<Passenger> my_passengers;
    
    public Flight(String fl_no)
    {
        flight_number = fl_no;
        my_passengers  = new ArrayList<Passenger>();
    }
}


public class Assignment2 {
    
    public static ArrayList<Flight> All_Flights = new ArrayList<Flight>();
    
    public void Reserve(Flight F, Passenger P)
    {
        F.my_passengers.add(P);
        P.my_flights.add(F);
    }
    
    public void Cancel(Flight F, Passenger P)
    {
        F.my_passengers.remove(P);
        P.my_flights.remove(F);
    }
    
    public void My_Flights(Passenger P)
    {
        for(int i = 0;i<P.my_flights.size();i++)
        {
            System.out.println("Flight Number : "+P.my_flights.get(i).flight_number);
        }
    }
    
    public void Transfer (Flight F1, Flight F2, Passenger P)
    {
        F1.my_passengers.remove(P);
        F2.my_passengers.add(P);
        P.my_flights.remove(F1);
        P.my_flights.add(F2);
    }
    
    public void Total_Reservations()
    {
        int total = 0;
        for(int i = 0;i<All_Flights.size();i++)
        {
            total = total+All_Flights.get(i).my_passengers.size();
        }
        System.out.println("Total Number of Reservations : " +  total);
    }
    
    public static void main(String[] args)throws IOException
    {
        
        Flight F1 = new Flight("F1");
        Flight F2 = new Flight("F2");
        Flight F3 = new Flight("F3");
        Flight F4 = new Flight("F4");
        Flight F5 = new Flight("F5");
        
        Passenger P1 = new Passenger("P1","Akash");
        Passenger P2 = new Passenger("P2", "Rahul");
        Passenger P3 = new Passenger("P3", "Raj");
        Passenger P4 = new Passenger("P4", "Rakesh");
        Passenger P5 = new Passenger("P5", "Om");
        
        P1.my_flights.add(F1);
        P2.my_flights.add(F2);
        P3.my_flights.add(F3);
        P4.my_flights.add(F4);
        P5.my_flights.add(F5);
        
        F1.my_passengers.add(P1);
        F2.my_passengers.add(P2);
        F3.my_passengers.add(P3);
        F4.my_passengers.add(P4);
        F5.my_passengers.add(P5);
        
        
        All_Flights.add(F1);
        All_Flights.add(F2);
        All_Flights.add(F3);
        All_Flights.add(F4);
        All_Flights.add(F5);
        
        
    }

}