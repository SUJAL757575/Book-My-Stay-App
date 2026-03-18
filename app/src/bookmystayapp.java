/**
 * ====================================================
 * MAIN CLASS - UseCase2RoomInitialization
 * ====================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * This class models room types for the Hotel Booking System.
 * It demonstrates inheritance, abstraction, and static availability.
 *
 * Concrete room classes extend an abstract Room class,
 * encapsulating common room attributes.
 *
 * @author Developer
 * @version 2.1
 */
abstract class Room {
    protected int beds;
    protected int size; // in sqft
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

public class bookmystayapp {

    public static void main(String[] args) {
        System.out.println("Hotel Room Initialization\n");

        SingleRoom single = new SingleRoom();
        int singleAvailable = 5;
        single.printDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        DoubleRoom doubleRoom = new DoubleRoom();
        int doubleAvailable = 3;
        doubleRoom.printDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        SuiteRoom suite = new SuiteRoom();
        int suiteAvailable = 2;
        suite.printDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}