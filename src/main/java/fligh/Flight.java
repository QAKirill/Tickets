package fligh;

import tickets.Ticket;
import tickets.TicketManager;
import utils.MyException;

public class Flight {
    private final String flightNumber, destination;
    private int price;
    private final TicketManager manager = TicketManager.getInstance();

    public Flight(String flightNumber, String destination) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        releaseTickets();
    }

    private void releaseTickets(){
        price = (int) (Math.random() * 100) + 10;
        manager.releaseTickets(flightNumber, price);
    }

    public int[] getAvailablePlaces(){
        return manager.getAvailableTickets(flightNumber).stream().mapToInt(Ticket::getPlace).toArray();
    }

    public Ticket getTicket(int place){
        return manager.getTicket(flightNumber, place);
    }

    public String bookTicket(int place) throws MyException {
        Ticket ticket = manager.getTicket(flightNumber, place);
        boolean isSucceed = false;
        if (ticket != null) {
            if (!ticket.isBooked()) {
                isSucceed = manager.bookTicket(ticket.getId());
            } else {
                return "Билет недоступен для бронирования!";
            }
        }

        if (isSucceed)
            return "Забронирован! К оплате: " + ticket.getPrice() + "p.";
        else throw new MyException("Не удалось забронировать!");
    }

    public String buyTicket(int place) throws MyException {
        Ticket ticket = manager.getTicket(flightNumber, place);
        if (ticket == null)
            throw new MyException("Нет такого билета!");
        boolean isSucceed = manager.sellTicket(ticket.getId());
        if (isSucceed) {
            return "Поздравляем с покупкой!\r\n" +
                    "Ваш билет: #" + ticket.getId() + "\r\n" +
                    "Рейс " + flightNumber +
                    " до " + destination + "\r\n" +
                    "место " + place;

        } else {
            return "Ticket was not bought";
        }
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getPrice() {
        return price;
    }
}
