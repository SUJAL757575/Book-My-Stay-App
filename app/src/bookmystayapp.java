import java.util.*;

// Reservation Entity
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Read-only access
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {
        System.out.println("Booking History and Reporting\n");
        System.out.println("Booking History Report\n");

        for (Reservation res : reservations) {
            System.out.println("Guest: " + res.getGuestName()
                    + ", Room Type: " + res.getRoomType());
        }
    }
}

// Main Class
public class bookmystayapp {
    public static void main(String[] args) {

        // Booking History
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from previous use cases)
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Reporting
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllReservations());
    }
}