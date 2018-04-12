// insert header here
package assignment6;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    public static void main(String args[]) {
        Seat s = new Seat(3, 24);
        System.out.println(s);
        s = new Seat(27, 190);
        System.out.println(s);
        s = new Seat(52, 10);
        System.out.println(s);
        s = new Seat(53, 673);
        System.out.println(s);
        s = new Seat(77, 5);
        System.out.println(s);
        s = new Seat(703, 15);
        System.out.println(s);

        s = new Seat(4, 104);
        Ticket t = new Ticket("Ouija", "BX1", s, 4);
        System.out.println(t.toString());
    }

	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		@Override
		public String toString() {
			// TODO: Implement this method to return the full Seat location ex: A1
            int intValue = rowNum;
            String row = "";
            /*while(intValue > 26) {
                int i = (intValue - 1) / 26;
                row += (char) (i + 'A' - 1);
                intValue -= (26 * i);
            }
            row += ((char) (intValue + 'A' - 1));*/
            while(intValue > 0) {
                int i = (intValue - 1) % 26;
                row = (char) (i + 'A') + row;
                intValue = (intValue - 1) / 26;
            }
            //Integer i = seatNum;
            return(row + seatNum);
		}
	}

  /*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
		private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			// TODO: Implement this method to return a string that resembles a ticket
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

		private String addPipe(String s) {
		    int padding  = 30 - s.length();
		    for(int i = 0; i < padding; i++) {
		        s += ' ';
            }
            s += "|\n";
		    return s;
        }
	}

	// I added these. ok??
	private int numRows;
	private int seatsPerRow;
	private String show;
	private int numTicketsSold = 0;
	private List<Ticket> ticketsSold = new ArrayList<>();
	public int getNumTicketsLeft() {
	    return (numRows * seatsPerRow - numTicketsSold);
    }
    public String getShow() {
	    return show;
    }

	public Theater(int numRows, int seatsPerRow, String show) {
		// TODO: Implement this constructor
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.show = show;
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		//TODO: Implement this method
        if(numTicketsSold == (numRows * seatsPerRow)) {
            System.out.println("Sorry, we are sold out!");
            return null;
        }
        else {
            int rowNum = (numTicketsSold / seatsPerRow) + 1;
            //int seatNum = seatsPerRow - (numTicketsSold % seatsPerRow) + 1;
            int seatNum = numTicketsSold - (rowNum - 1) * seatsPerRow + 1;
            return(new Seat(rowNum, seatNum));
        }
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		//TODO: Implement this method
        try {
            Ticket ticket = new Ticket(show, boxOfficeId, seat, client);
            ticketsSold.add(ticket);
            numTicketsSold++;
            System.out.println(ticket);
            //Thread.sleep(50);
            return ticket;
        }
        catch(Exception e) {
            //System.out.println("Ticket printing failed!");
            numTicketsSold++;
            return null;
        }
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		//TODO: Implement this method
        return ticketsSold;
	}
}
