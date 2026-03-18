import java.util.*;

// Reservation Entity (represents booking intent)
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

// Booking Request Queue (FIFO structure)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    // Process requests in FIFO order (read-only simulation)
    public void processRequests() {
        System.out.println("Booking Request Queue\n");

        while (!queue.isEmpty()) {
            Reservation res = queue.poll(); // FIFO removal
            System.out.println("Processing booking for Guest: "
                    + res.getGuestName()
                    + ", Room Type: "
                    + res.getRoomType());
        }
    }
}

// Main Class
public class bookmystayapp {
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating guest booking requests (arrival order matters)
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Double"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process requests in FIFO order
        bookingQueue.processRequests();
    }
}