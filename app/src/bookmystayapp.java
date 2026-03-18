import java.io.*;
import java.util.*;

class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> rooms;

    public Inventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public void display() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class PersistenceService {
    private static final String FILE_NAME = "inventory.dat";

    public static void save(Inventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public static Inventory load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Inventory) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("System Recovery:");
            System.out.println("No valid inventory data found. Starting fresh.\n");
            return new Inventory();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("System Recovery:");
            System.out.println("Corrupted data. Starting fresh.\n");
            return new Inventory();
        }
    }
}

public class bookmystayapp {
    public static void main(String[] args) {

        // Step 1: Load previous state
        Inventory inventory = PersistenceService.load();

        // Step 2: Display current state
        inventory.display();

        // Step 3: Save state before shutdown
        PersistenceService.save(inventory);
    }
}