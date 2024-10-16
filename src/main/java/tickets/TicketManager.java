package tickets;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketManager {
    private static volatile TicketManager instance;
    private final Set<Ticket> availableTickets;
    private final Set<Ticket> soldTickets;
    private int currentId = 0;

    private TicketManager() {
        availableTickets = new HashSet<>();
        soldTickets = new HashSet<>();
    }

    public static synchronized TicketManager getInstance() {
        if (instance == null) {
            synchronized (TicketManager.class) {
                if (instance == null) {
                    instance = new TicketManager();
                }
            }
        }
        return instance;
    }

    public void releaseTickets(String flight, int price) {
        synchronized (availableTickets) {
            for (int i = 0; i < 30; i++) {
                if(i < 10)
                    price = price*2;

                availableTickets.add(new Ticket(currentId++, i+1, price, flight));
            }
        }
    }

    public Ticket getTicket(String flight, int place) {
        synchronized (availableTickets) {
            return availableTickets.stream().filter(t -> t.getFlight().equals(flight) && t.getPlace() == place).findFirst()
                    .orElse(null);
        }
    }

    public boolean bookTicket(int id){
        Ticket ticket = availableTickets.stream().filter(t -> t.getId() == id).findFirst().orElse(null);

        if (ticket != null){
            ticket.setBooked(true);
            return true;
        } else
            return false;
    }

    public Ticket getTicket(int id) {
        synchronized (availableTickets) {
            return availableTickets.stream().filter(t -> t.getId() == id).findFirst()
                    .orElse(null);
        }
    }

    public Set<Ticket> getAvailableTickets(String flight){
        return availableTickets.stream().filter(t -> t.getFlight().equals(flight)).collect(Collectors.toSet());
    }

    public boolean sellTicket(int id){
        Ticket ticket = availableTickets.stream().filter(t -> t.getId() == id).findFirst().orElse(null);

        if(ticket != null) {
            availableTickets.remove(ticket);
            soldTickets.add(ticket);
            return true;
        } else
            return false;
    }

    public void clear(){
        instance = null;
    }
}

