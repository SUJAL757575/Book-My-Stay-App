import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class Hotel {
    private Map<String, Integer> inventory = new HashMap<>();

    public Hotel() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Critical section: synchronized method ensures thread safety
    public synchronized void bookRoom(BookingRequest request, int roomNumber) {
        int available = inventory.getOrDefault(request.roomType, 0);

        if (available > 0) {
            inventory.put(request.roomType, available - 1);
            System.out.println("Booking confirmed for Guest: " + request.guestName +
                    ", Room ID: " + request.roomType + "-" + roomNumber);
        } else {
            System.out.println("Booking failed for Guest: " + request.guestName +
                    " (No " + request.roomType + " rooms available)");
        }
    }

    public void printInventory() {
        System.out.println("\nRemaining Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class BookingProcessor implements Runnable {
    private Queue<BookingRequest> queue;
    private Hotel hotel;
    private static int roomCounter = 1;

    public BookingProcessor(Queue<BookingRequest> queue, Hotel hotel) {
        this.queue = queue;
        this.hotel = hotel;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) { // Protect shared queue
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }

            // Simulate room allocation
            hotel.bookRoom(request, roomCounter++);

            try {
                Thread.sleep(100); // Simulate processing delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class bookmystayapp {
    public static void main(String[] args) {
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Simulate concurrent booking requests
        bookingQueue.add(new BookingRequest("Abhi", "Single"));
        bookingQueue.add(new BookingRequest("Vamshi", "Double"));
        bookingQueue.add(new BookingRequest("Kural", "Suite"));
        bookingQueue.add(new BookingRequest("Subha", "Single"));

        Hotel hotel = new Hotel();

        // Create multiple threads
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, hotel));
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, hotel));
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, hotel));

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Print final inventory
        hotel.printInventory();
    }
}