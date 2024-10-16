package org.example;

import fligh.Flight;
import utils.MyException;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Flight flight = new Flight("QWE123", "London");

        int[] ap = flight.getAvailablePlaces();
        System.out.println(Arrays.toString(ap));

        int rndPlace = new Random().nextInt(ap.length);
        String bookingResult = null;
        try {
            bookingResult = flight.bookTicket(rndPlace);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(bookingResult);

        int rndPlace2 = new Random().nextInt(ap.length);
        String buyResult = null;
        try {
            buyResult = flight.buyTicket(rndPlace2);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(buyResult);

        int[] ap2 = flight.getAvailablePlaces();
        System.out.println(Arrays.toString(ap2));
    }
}