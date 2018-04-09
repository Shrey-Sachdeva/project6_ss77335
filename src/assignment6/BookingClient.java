// Insert header here
package assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Thread;

public class BookingClient {
    // I added this. ok??
    private Map<String, Integer> office;
    private Theater theater;

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
        int numOffices = office.size();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < numOffices; i++) {
            Thread thread = new Thread();
            threads.add(thread);
            thread.start();
        }
        return threads;
	}
}
