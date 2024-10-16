package tickets;

import lombok.Data;

@Data
public class Ticket {
    private final int id, place, price;
    private final String flight;
    private boolean isBooked;

    public Ticket(int id, int place, int price, String flight) {
        this.id = id;
        this.place = place;
        this.price = price;
        this.flight = flight;
        isBooked = false;
    }
}
