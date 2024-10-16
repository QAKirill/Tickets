package tests;

import fligh.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tickets.Ticket;
import utils.MyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

//TODO: дописать тесты
public class UnitTests extends TestBase {

    @Test
    @DisplayName("При создании рейса создаются билеты с номерами мест от 1 до 30")
    void allPlacesAvailable() {
        Flight flight = new Flight("QWE123", "London");
        Flight flight2 = new Flight("QWE12345", "Париж");

        int expLength = 30;
        int[] expected = new int[expLength];
        for (int i = 0; i < expLength; i++){
            expected[i] = i+1;
        }

        int[] ap = flight.getAvailablePlaces();
        int[] ap2 = flight2.getAvailablePlaces();

        assertAll("Grouped Assertions",
                () -> assertThat(ap.length).isEqualTo(expLength),
                () -> assertThat(ap).contains(expected),
                () -> assertThat(ap2.length).isEqualTo(expLength),
                () -> assertThat(ap2).contains(expected));
    }

    @Test
    @DisplayName("Проверка успешного бронирования")
    void successBookingTest() {
        Flight flight = new Flight("QWE123", "London");

        String bookingResult = null;
        try {
            bookingResult = flight.bookTicket(15);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(bookingResult);

        Ticket ticket = flight.getTicket(15);

        assertThat(ticket.isBooked()).isTrue();
    }

    @Test
    @DisplayName("Проверка сообщения при успешном бронировании")
    void successBookingMessageTest() {
        Flight flight = new Flight("QWE123", "London");

        String bookingResult = null;
        try {
            bookingResult = flight.bookTicket(15);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(bookingResult);

        final String finalBookingResult = bookingResult;
        assertAll("Grouped Assertions",
                () -> assertThat(finalBookingResult).contains(String.valueOf(flight.getPrice())),
                () -> assertThat(finalBookingResult).contains("Забронирован"));
    }

    @Test
    @DisplayName("При попытке забронировать уже забронированный билет появляется ошибка")
    void cantBookBookedTicket() {
        Flight flight = new Flight("QWE123", "London");

        String bookingResult = null;
        try {
            bookingResult = flight.bookTicket(15);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(bookingResult);

        try {
            bookingResult = flight.bookTicket(15);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(bookingResult);

        assertThat(bookingResult).contains("Билет недоступен для бронирования!");
    }

    @Test
    @DisplayName("Проверка ошибки при попытки бронирования несуществующего билета")
    void errorBookingMessageTest() {
        Flight flight = new Flight("QWE123", "London");
        var ref = new Object() {
            String bookingResult;
        };

        MyException thrown = Assertions.assertThrows(MyException.class, () ->
                ref.bookingResult = flight.bookTicket(35));


        assertAll("Grouped Assertions",
                () -> assertThat(ref.bookingResult).isNull(),
                () -> assertThat("Не удалось забронировать!").isEqualTo(thrown.getMessage()));
    }
}
