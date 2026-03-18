import java.util.*;

// Room Domain Model
class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
}

// Inventory (State Holder)
class Inventory {
    private Map<String, Integer> availability = new HashMap<>();

    public Inventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Set<String> getRoomTypes() {
        return availability.keySet();
    }
}

// Search Service (Read-only logic)
class SearchService {
    private Inventory inventory;
    private Map<String, Room> roomCatalog;

    public SearchService(Inventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public void searchRooms() {
        System.out.println("Room Search\n");

        for (String type : inventory.getRoomTypes()) {
            int available = inventory.getAvailability(type);

            // Validation: only show available rooms
            if (available > 0) {
                Room room = roomCatalog.get(type);

                System.out.println(type + " Room:");
                System.out.println("Beds: " + room.getBeds());
                System.out.println("Size: " + room.getSize() + " sqft");
                System.out.println("Price per night: " + room.getPrice());
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

// Main Class
public class bookmystayapp {
    public static void main(String[] args) {

        // Create Room Catalog (Domain Data)
        Map<String, Room> roomCatalog = new HashMap<>();
        roomCatalog.put("Single", new Room("Single", 1, 250, 1500.0));
        roomCatalog.put("Double", new Room("Double", 2, 400, 2500.0));
        roomCatalog.put("Suite", new Room("Suite", 3, 750, 5000.0));

        // Inventory (State)
        Inventory inventory = new Inventory();

        // Search Service (Read-only)
        SearchService searchService = new SearchService(inventory, roomCatalog);

        // Guest initiates search
        searchService.searchRooms();
    }
}