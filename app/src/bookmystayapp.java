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

// Inventory Service (State Holder)
class InventoryService {
    private Map<String, Integer> availability = new HashMap<>();

    public InventoryService() {
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}

// Booking Service (Allocation Logic)
class BookingService {
    private Queue<Reservation> bookingQueue;

    // Track allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService(Queue<Reservation> bookingQueue) {
        this.bookingQueue = bookingQueue;
    }

    public void processBookings(InventoryService inventory) {
        System.out.println("Room Allocation Processing\n");

        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.poll();
            String type = res.getRoomType();

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Initialize set if not present
                allocatedRooms.putIfAbsent(type, new HashSet<>());

                Set<String> roomSet = allocatedRooms.get(type);

                // Generate unique room ID
                int roomNumber = roomSet.size() + 1;
                String roomId = type + "-" + roomNumber;

                // Ensure uniqueness using Set
                if (!roomSet.contains(roomId)) {
                    roomSet.add(roomId);

                    // Update inventory immediately (atomic step)
                    inventory.decrement(type);

                    // Confirm booking
                    System.out.println("Booking confirmed for Guest: "
                            + res.getGuestName()
                            + ", Room ID: "
                            + roomId);
                }

            } else {
                System.out.println("Booking failed for Guest: "
                        + res.getGuestName()
                        + " (No rooms available)");
            }
        }
    }
}

// Main Class
public class bookmystayapp {
    public static void main(String[] args) {

        // Step 1: Prepare booking queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.offer(new Reservation("Abhi", "Single"));
        bookingQueue.offer(new Reservation("Subha", "Single"));
        bookingQueue.offer(new Reservation("Vanmathi", "Suite"));

        // Step 2: Inventory
        InventoryService inventory = new InventoryService();

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(bookingQueue);

        // Step 4: Process allocations
        bookingService.processBookings(inventory);
    }
}