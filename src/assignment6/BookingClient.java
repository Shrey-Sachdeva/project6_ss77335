/* BOX OFFICE BookingClient.java
 * EE422C Project 6 submission by
 * Shrey Sachdeva
 * ss77335
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.*;
import java.lang.Thread;

public class BookingClient {
    private Map<String, Integer> office;
    private Theater theater;

    /**
     * BookingClient constructor
     * @param office maps box office id to number of customers in line
     * @param theater the theater where the show is playing
     */
    public BookingClient(Map<String, Integer> office, Theater theater) {
        this.office = office;
        this.theater = theater;
    }

    /**
     * Starts the box office simulation by creating (and starting) threads for each box office to sell tickets for the given theater
     * @return list of threads used in the simulation
     */
	public List<Thread> simulate() {
        List<Thread> threads = new ArrayList<>();
        List<BoxOffice> boxOffices = new ArrayList<>();
        ArrayList<Integer> clientsAtBO = new ArrayList<>();
        int numClients = 0;
        // Create new BoxOffices and Threads
        for(Map.Entry<String, Integer> entry : office.entrySet()) {
            BoxOffice boxOffice = new BoxOffice(entry.getKey());
            Thread thread = new Thread(boxOffice);
            threads.add(thread);
            boxOffices.add(boxOffice);
            clientsAtBO.add(entry.getValue());
            numClients += entry.getValue();
        }
        // Assign client numbers to the clients in each BoxOffice line
        int clientNumber = 1;
        while(numClients > 0) {
            for(int i = 0; i < clientsAtBO.size(); i++) {
                if(clientsAtBO.get(i) > 0) {
                    boxOffices.get(i).addClient(clientNumber);
                    numClients--;
                    clientNumber++;
                    clientsAtBO.set(i, clientsAtBO.get(i) - 1);
                }
            }
        }
        // Run the threads
        synchronized(theater) {
            for (Thread thread : threads) {
                thread.start();
                try {
                    thread.sleep(50);
                } catch (Exception e) {
                    System.out.println("Error in pausing!");
                }
            }
            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (Exception e) {
                System.out.println("Error in joining!");
            }
        }
        return threads;
	}

    /**
     * Inner class to represent the box office threads
     */
	class BoxOffice extends Thread {
	    private String name;
	    private ArrayList<Integer> clients = new ArrayList<>();

        /**
         * Constructor
         * @param name is the box office name
         */
	    public BoxOffice(String name) {
	        this.name = name;
        }

        /**
         * Adds a client number to the clients in the box office's line
         * @param clientNumber is the client's number
         */
        public void addClient(int clientNumber) {
	        clients.add(clientNumber);
        }

        /**
         * Run method for the thread
         */
        @Override
        public synchronized void run() {
            // Process clients while there are clients in the box office's line and tickets left
            while(clients.size() != 0 && theater.getNumTicketsLeft() >= 0) {
                Theater.Seat seat = theater.bestAvailableSeat();
                theater.printTicket(name, seat, clients.remove(0));
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println("Error in run");
                }
            }
        }
    }

    /**
     * Main method to run the program
     * @param args are the args
     */
    public static void main(String args[]) {
	    Map<String, Integer> office = new HashMap<>();
	    /*office.put("BX1", 500);
	    office.put("BX3", 600);
        office.put("BX2", 700);
        office.put("BX5", 800);
        office.put("BX4", 900);
        Theater theater = new Theater(1000, 1, "Ouija");*/
	    office.put("BX1", 3);
        office.put("BX3", 3);
        office.put("BX2", 4);
        office.put("BX5", 3);
        office.put("BX4", 3);
        Theater theater = new Theater(3, 5, "Ouija");
        BookingClient bookingClient = new BookingClient(office, theater);
        bookingClient.simulate();
    }
}