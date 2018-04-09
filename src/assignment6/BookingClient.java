// Insert header here
package assignment6;

import java.util.*;
import java.lang.Thread;

public class BookingClient {
    // I added this. ok??
    private Map<String, Integer> office;
    private static Theater theater;

    /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
    public BookingClient(Map<String, Integer> office, Theater theater) {
    // TODO: Implement this constructor
        this.office = office;
        this.theater = theater;
    }

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
	public List<Thread> simulate() {
		//TODO: Implement this method (NOT DONE!!!)
        //int numOffices = office.size();
        List<Thread> threads = new ArrayList<>();
        //for(int i = 0; i < numOffices; i++) {
        for(Map.Entry<String, Integer> entry : office.entrySet()) {
            /*Thread thread = new Thread();
            threads.add(thread);
            thread.start();*/
            /*Thread thread = new Thread(new Runnable() {
                private String name;

                @Override
                public void run() {

                }
            }).start();*/
            Thread thread = new Thread(new BoxOffice(entry.getKey(), entry.getValue()));
            //thread.start();
            threads.add(thread);
        }
        for(int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
        return threads;
	}

	static class BoxOffice implements Runnable {
	    private String name;
	    private int numClients;

	    public BoxOffice(String name, int numClients) {
	        this.name = name;
	        this.numClients = numClients;
        }

	    public synchronized void run() {
            while(numClients > 0 && theater.getNumTicketsLeft() > 0) {
                Theater.Seat seat = theater.bestAvailableSeat();
                int client = theater.getTransactionLog().size() + 1;
                theater.printTicket(name, seat, client);
                numClients--;
            }
        }
    }

    public static void main(String args[]) {
	    Map<String, Integer> office = new HashMap<>();
	    office.put("BX1", 3);
        office.put("BX3", 3);
        office.put("BX2", 4);
        office.put("BX5", 3);
        office.put("BX4", 3);
        Theater theater = new Theater(3, 5, "Ouija");
        BookingClient bookingClient = new BookingClient(office, theater);
        while(theater.getNumTicketsLeft() > 0) {
            Theater.Seat seat = theater.bestAvailableSeat();
            System.out.println(seat);
            theater.printTicket("BX1", seat, theater.getTransactionLog().size() + 1);
        }
        //bookingClient.simulate();
    }
}
