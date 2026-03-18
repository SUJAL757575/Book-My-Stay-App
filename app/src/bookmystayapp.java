import java.util.*;

// Inventory Service
class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();

    public InventoryService() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public void increment(String roomType) {
        availability.put(roomType, availability.getOrDefault(roomType, 0) + 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

// Booking History (stores active bookings)
class BookingHistory {
    private Set<String> activeReservations = new HashSet<>();

    public void addReservation(String reservationId) {
        activeReservations.add(reservationId);
    }

    public boolean exists(String reservationId) {
        return activeReservations.contains(reservationId);
    }

    public void removeReservation(String reservationId) {
        activeReservations.remove(reservationId);
    }
}

// Cancellation Service
class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              InventoryService inventory) {

        System.out.println("Booking Cancellation\n");

        // Validate reservation
        if (!history.exists(reservationId)) {
            System.out.println("Cancellation failed: Invalid reservation ID");
            return;
        }

        // Extract room type (e.g., Single-1 → Single)
        String roomType = reservationId.split("-")[0];

        // Push to rollback stack (LIFO tracking)
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increment(roomType);

        // Remove from active bookings
        history.removeReservation(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        // Display rollback history
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }

        // Show updated inventory
        System.out.println("\nUpdated " + roomType + " Room Availability: "
                + inventory.getAvailability(roomType));
    }
}

// Main Class
public class bookmystayapp {
    public static void main(String[] args) {

        // Setup inventory and booking history
        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulate an existing booking
        String reservationId = "Single-1";
        history.addReservation(reservationId);

        // Cancellation Service
        CancellationService cancellationService = new CancellationService();

        // Perform cancellation
        cancellationService.cancelBooking(reservationId, history, inventory);
    }
}