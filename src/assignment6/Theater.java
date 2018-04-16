/* BOX OFFICE Theater.java
 * EE422C Project 6 submission by
 * Shrey Sachdeva
 * ss77335
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

package assignment6;

import java.util.ArrayList;
import java.util.List;

public class Theater {
	/**
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

        /**
         * Seat Constructor
         * @param rowNum is the row number
         * @param seatNum is the seat number in the row
         */
		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

        /**
         * Returns the seat number
         * @return the seat number
         */
		public int getSeatNum() {
			return seatNum;
		}

        /**
         * Returns the row number
         * @return the row number
         */
		public int getRowNum() {
			return rowNum;
		}

        /**
         * Returns a String representation of the Seat
         * @return a String representing the seat
         */
		@Override
		public String toString() {
            int intValue = rowNum;
            String row = "";
            // Convert the row number to its corresponding letter representation
            while(intValue > 0) {
                int i = (intValue - 1) % 26;
                row = (char) (i + 'A') + row;
                intValue = (intValue - 1) / 26;
            }
            // Append the seat number
            return(row + seatNum);
		}
	}

    /**
     * Represents a ticket purchased by a client
     */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
		private int client;

        /**
         * Ticket Constructor
         * @param show is the show name
         * @param boxOfficeId is the box office issuing the ticket
         * @param seat is the seat being sold
         * @param client is the client number
         */
		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

        /**
         * Returns the Seat
         * @return the Seat
         */
		public Seat getSeat() {
			return seat;
		}

        /**
         * Returns the show
         * @return the show
         */
		public String getShow() {
			return show;
		}

        /**
         * Returns the box office ID
         * @return the box office ID
         */
		public String getBoxOfficeId() {
			return boxOfficeId;
		}

        /**
         * Returns the client number
         * @return the client number
         */
		public int getClient() {
			return client;
		}

        /**
         * Returns a String representation of the Ticket
         * @return the String representation
         */
		@Override
		public String toString() {
			String ticket = "-------------------------------\n";
			String s = "| Show: " + show;
			ticket = ticket + addPipe(s);
			s = "| Box Office ID: " + boxOfficeId;
			ticket = ticket + addPipe(s);
			s = "| Seat: " + seat.toString();
			ticket = ticket + addPipe(s);
			s = "| Client: " + client;
			ticket = ticket + addPipe(s);
			ticket = ticket + "-------------------------------\n";
			return ticket;
		}

        /**
         * Adds a pipe aligned with the end of the ticket
         * @param s is the String without the pipe
         * @return the String with the pipe
         */
		private String addPipe(String s) {
		    int padding  = 30 - s.length();
		    for(int i = 0; i < padding; i++) {
		        s += ' ';
            }
            s += "|\n";
		    return s;
        }
	}

	private int numRows;
	private int seatsPerRow;
	private String show;
	private int numTicketsSold = 0;
	private List<Ticket> ticketsSold = new ArrayList<>();
	private boolean soldOut = false;

    /**
     * Returns the remaining number of tickets
     * @return the number of tickets remaining
     */
	public int getNumTicketsLeft() {
	    return (numRows * seatsPerRow - numTicketsSold);
    }

    /**
     * Returns the show
     * @return the show
     */
    public String getShow() {
	    return show;
    }

    /**
     * Theater Constructor
     * @param numRows is the number of rows in the theater
     * @param seatsPerRow is the number of seats per row
     * @param show is the name of the show
     */
	public Theater(int numRows, int seatsPerRow, String show) {
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.show = show;
	}

    /**
     * Calculates the best seat not yet reserved
     * @return the best seat or null if theater is full
     */
	public Seat bestAvailableSeat() {
	    // Determine if there are tickets remaining
        if(numTicketsSold == (numRows * seatsPerRow)) {
            if(!soldOut) {
                soldOut = true;
                System.out.println("Sorry, we are sold out!");
            }
            return null;
        }
        // Determine the next best seat
        else {
            int rowNum = (numTicketsSold / seatsPerRow) + 1;
            int seatNum = numTicketsSold - (rowNum - 1) * seatsPerRow + 1;
            return(new Seat(rowNum, seatNum));
        }
	}

    /**
     * Prints a ticket for the client after they reserve a seat (also prints to the console)
     * @param boxOfficeId if the box office ID
     * @param seat is a particular seat in the theater
     * @param client is the client number
     * @return a ticket or null if a box office failed to reserve the seat
     */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		try {
		    // Create and print the ticket
            Ticket ticket = new Ticket(show, boxOfficeId, seat, client);
            ticketsSold.add(ticket);
            numTicketsSold++;
            System.out.println(ticket);
            Thread.sleep(50);
            return ticket;
        } catch (Exception e) {
		    numTicketsSold++;
		    return null;
        }
	}

    /**
     * Lists all tickets sold for this theater in order of purchase
     * @return a list of tickets sold
     */
	public List<Ticket> getTransactionLog() {
        return ticketsSold;
	}
}