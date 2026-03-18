
import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected int beds;
    protected int size; // sqft
    protected double pricePerNight;

    public Room(int beds, int size, double pricePerNight) {
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    public abstract String getRoomType();

    public void printDetails() {
        System.out.println(getRoomType() + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

/**
 * RoomInventory manages availability of room types using a centralized HashMap.
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    /**
     * Registers a room type with its initial availability.
     *
     * @param roomType Room type name
     * @param count Number of rooms available
     */
    public void registerRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    /**
     * Returns availability count for a given room type.
     *
     * @param roomType Room type name
     * @return number of available rooms or 0 if none registered
     */
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    /**
     * Updates availability for a given room type.
     *
     * @param roomType Room type name
     * @param newCount Updated availability count
     */
    public void updateAvailability(String roomType, int newCount) {
        availabilityMap.put(roomType, newCount);
    }

    /**
     * Prints the inventory status of all registered room types.
     *
     * @param rooms array of Room objects to show room details
     */
    public void printInventoryStatus(Room[] rooms) {
        System.out.println("Hotel Room Inventory Status\n");
        for (Room room : rooms) {
            room.printDetails();
            System.out.println("Available Rooms: " + getAvailability(room.getRoomType()));
            System.out.println();
        }
    }
}

public class bookmystayapp {

    public static void main(String[] args) {
        // Create room objects
        SingleRoom singleRoom = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suiteRoom = new SuiteRoom();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types and their availability
        inventory.registerRoomType(singleRoom.getRoomType(), 5);
        inventory.registerRoomType(doubleRoom.getRoomType(), 3);
        inventory.registerRoomType(suiteRoom.getRoomType(), 2);

        // Print current inventory status
        inventory.printInventoryStatus(new Room[]{singleRoom, doubleRoom, suiteRoom});
    }
}