package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import tickets.TicketManager;

public class TestBase {
    private TicketManager manager;

    @BeforeEach
    void setUp() {
        manager = TicketManager.getInstance();
    }

    @AfterEach
    void tearDown() {
        manager.clear();
    }
}
