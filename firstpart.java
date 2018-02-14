package Assignment2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Passenger
{
    private int id;
    private String name;
    private ArrayList<Flight> my_flights;

    public Passenger(int pass_id, String pass_name)
    {
        id = pass_id;
        name = pass_name;
        my_flights = new ArrayList<Flight>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Flight> getMy_flights() {
        return my_flights;
    }

    public void setMy_flights(ArrayList<Flight> my_flights) {
        this.my_flights = my_flights;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addFlight(Flight f){
        this.my_flights.add(f);
    }

    public void removeFlight(Flight f) {
        this.my_flights.remove(f);
    }

    public void myFlights(int pid) {
        for(Passenger p: firstpart.allPassengers) {
            if(p.id == pid) {
                for (Flight f : my_flights) {
                    System.out.println(f);
                }
                break;
            }
        }
    }

}

class Flight
{

    private String flight_number;
    private int maxSeats;
    private int vacantSeats;
    private ArrayList<Passenger> my_passengers;

    public Flight(String fl_no, int seats) {
        flight_number = fl_no;
        maxSeats = seats;
        vacantSeats = maxSeats;
        my_passengers = new ArrayList<Passenger>();
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getVacantSeats() {
        return vacantSeats;
    }

    public void setVacantSeats(int vacantSeats) {
        this.vacantSeats = vacantSeats;
    }

    public ArrayList<Passenger> getMy_passengers() {
        return my_passengers;
    }

    public void setMy_passengers(ArrayList<Passenger> my_passengers) {
        this.my_passengers = my_passengers;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flight_number='" + flight_number + '\'' +
                ", maxSeats=" + maxSeats +
                ", vacantSeats=" + vacantSeats +
                '}';
    }

    public void addPassenger(Passenger p){
        this.my_passengers.add(p);
        vacantSeats--;
    }

    public void reserve(Flight f, int pid) {
        for (Passenger p : firstpart.allPassengers) {
            if(p.getId() == pid) {
                if(vacantSeats != 0) {
                    vacantSeats--;
                    f.my_passengers.add(p);
                    p.addFlight(f);
                    break;
                }
            }
        }
    }

    public void cancel(Flight f, int pid) {
//        Passenger p = null;
        for(Passenger p : f.my_passengers) {
            if(p.getId() == pid) {
                f.vacantSeats++;
                p.removeFlight(f);
                f.my_passengers.remove(p);
                break;
            }
        }

    }

    public int totalReservation() {
        int no = 0;
        for(Flight f : firstpart.All_Flights) {
            no += f.my_passengers.size();
        }
        return no;
    }

    public void transfer(Flight a, Flight b, int pid) {
        for(Passenger p: a.my_passengers) {
            if(p.getId() == pid) {
                if(b.vacantSeats == 0) {
                    a.my_passengers.remove(p);
                    a.vacantSeats++;
                    b.my_passengers.add(p);
                    b.vacantSeats--;
                    p.addFlight(b);
                    p.removeFlight(a);
                }
                break;
            }
        }
    }

}


class transaction implements Runnable{

    @Override
    public void run() {
        Random r = new Random();
        int choice = r.nextInt(5) + 1;
        System.out.println("Choice == "+ choice);
//        int choice = 1;
        switch(choice) {
            case 1: {
                    System.out.println("reserve");
                    int flightNo = r.nextInt(firstpart.All_Flights.size());
                    int passNo = r.nextInt(firstpart.allPassengers.size());

                    final Lock lock1 = new ReentrantLock();

                    final Lock lock1_P = new ReentrantLock();

                    for(int i = 0;i<firstpart.All_Flights.size();i++)
                    {
                        lock1.lock();

                    }

                    for(int i = 0;i<firstpart.allPassengers.size();i++)
                    {
                        lock1_P.lock();
                    }


                    Flight f = firstpart.All_Flights.get(flightNo);
                    f.reserve(f, passNo);

                    for(int i = 0;i<firstpart.All_Flights.size();i++)
                    {
                        lock1.unlock();

                    }

                    for(int i = 0;i<firstpart.allPassengers.size();i++)
                    {
                        lock1_P.unlock();
                    }

                break;
                }
            case 2: {
                System.out.println("cancel");
                int flightNo = r.nextInt(firstpart.All_Flights.size());
                int passNo = r.nextInt(firstpart.allPassengers.size());

                Lock lock2 = new ReentrantLock();

                Lock lock2_P = new ReentrantLock();

                    for(int i = 0;i<firstpart.All_Flights.size();i++)
                    {
                        lock2.lock();

                    }

                    for(int i = 0;i<firstpart.allPassengers.size();i++)
                    {
                        lock2_P.lock();
                    }
                Flight f = firstpart.All_Flights.get(flightNo);
                f.cancel(f, passNo);

                for(int i = 0;i<firstpart.All_Flights.size();i++)
                    {
                        lock2.unlock();

                    }

                    for(int i = 0;i<firstpart.allPassengers.size();i++)
                    {
                        lock2_P.unlock();
                    }
                break;
            }
            case 3: {
                System.out.println("my flight");
                int passNo = r.nextInt(firstpart.allPassengers.size());
                
                
                Lock lock3 = new ReentrantLock();
                Lock lock3_P = new ReentrantLock();
                for(int i = 0;i<firstpart.All_Flights.size();i++)
                {
                   lock3.lock();
                }
                for(int i = 0;i<firstpart.allPassengers.size();i++)
                {
                   lock3_P.lock();
                }
                    
                Passenger p = firstpart.allPassengers.get(passNo);
                p.myFlights(passNo);

                for(int i = 0;i<firstpart.All_Flights.size();i++)
                {
                   lock3.unlock();
                }
                for(int i = 0;i<firstpart.allPassengers.size();i++)
                {
                   lock3_P.unlock();
                }
                break;
            }
            case 4:{
                System.out.println("total reservation");
                int flightNo = r.nextInt(firstpart.All_Flights.size());
                //int passNo = r.nextInt(firstpart.allPassengers.size() + 1);
                Lock lock3 = new ReentrantLock();
                Lock lock3_P = new ReentrantLock();
                for(int i = 0;i<firstpart.All_Flights.size();i++)
                {
                   lock3.lock();
                }
                for(int i = 0;i<firstpart.allPassengers.size();i++)
                {
                   lock3_P.lock();
                }
                
                Flight f = firstpart.All_Flights.get(flightNo);
                f.totalReservation();
                
                for(int i = 0;i<firstpart.All_Flights.size();i++)
                {
                   lock3.unlock();
                }
                for(int i = 0;i<firstpart.allPassengers.size();i++)
                {
                   lock3_P.unlock();
                }                
                break;
            }
            case 5:{
                System.out.println("transfer");
                int flightNo1 = r.nextInt(firstpart.All_Flights.size());
                int flightNo2;
                do{
                    flightNo2 = r.nextInt(firstpart.All_Flights.size());
                }while(flightNo1 != flightNo2);
                int passNo = r.nextInt(firstpart.allPassengers.size());
                Lock lock3 = new ReentrantLock();
                Lock lock3_P = new ReentrantLock();
                for(int i = 0;i<firstpart.All_Flights.size();i++)
                {
                   lock3.lock();
                }
                for(int i = 0;i<firstpart.allPassengers.size();i++)
                {
                   lock3_P.lock();
                }
                
                Flight f1 = firstpart.All_Flights.get(flightNo1);
                Flight f2 = firstpart.All_Flights.get(flightNo2);
                f1.transfer(f1, f2, passNo);
                
                for(int i = 0;i<firstpart.All_Flights.size();i++)
                {
                   lock3.unlock();
                }
                for(int i = 0;i<firstpart.allPassengers.size();i++)
                {
                   lock3_P.unlock();
                }
                break;
            }
            default:
                System.out.println("Wrong choice");
        }
        firstpart.count++;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    
    }
}

public class firstpart {

    public static  ArrayList<Flight> All_Flights = new ArrayList<Flight>();
    public static  ArrayList<Passenger> allPassengers = new ArrayList<Passenger>();
    volatile static int count = 0;

    public static void main(String args[]) {
        Flight F1 = new Flight("F1", 100);
        Flight F2 = new Flight("F2", 60);
        Flight F3 = new Flight("F3", 50);
        Flight F4 = new Flight("F4", 150);
        Flight F5 = new Flight("F5", 200);

        Passenger P1 = new Passenger(1, "Akash");
        Passenger P2 = new Passenger(2, "Rahul");
        Passenger P3 = new Passenger(3, "Raj");
        Passenger P4 = new Passenger(4, "Rakesh");
        Passenger P5 = new Passenger(5, "Om");

//        P1.addFlight(F1);
//        P2.addFlight(F2);
//        P3.addFlight(F3);
//        P4.addFlight(F4);
//        P5.addFlight(F5);
//
//        F1.addPassenger(P1);
//        F2.addPassenger(P2);
//        F3.addPassenger(P3);
//        F4.addPassenger(P4);
//        F5.addPassenger(P5);


        All_Flights.add(F1);
        All_Flights.add(F2);
        All_Flights.add(F3);
        All_Flights.add(F4);
        All_Flights.add(F5);

        allPassengers.add(P1);
        allPassengers.add(P2);
        allPassengers.add(P3);
        allPassengers.add(P4);
        allPassengers.add(P5);

        ExecutorService tpool = Executors.newFixedThreadPool(40 );
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() < startTime + 10000) {
//        int i = 10;
//        while (i-- != 0){
            Runnable t = new transaction();
            tpool.execute(t);
        }
        tpool.shutdownNow();
//        while (!tpool.isTerminated()) {
//        }

        for(Flight f : All_Flights)
            System.out.println(f);
        System.out.println(count);
        //System.out.println(P5.getMy_flights());
    }
}