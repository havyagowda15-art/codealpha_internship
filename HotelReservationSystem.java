import java.io.*;
import java.util.*;

class Room {
    int roomId;
    String category;
    double price;
    boolean available;

    public Room(int roomId, String category, double price) {
        this.roomId = roomId;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class BookingManager {
    private ArrayList<Room> rooms = new ArrayList<>();

    public BookingManager() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(201, "Deluxe", 4000));
        rooms.add(new Room(202, "Deluxe", 4000));
        rooms.add(new Room(301, "Suite", 7000));
    }

    public void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.available) {
                System.out.println(
                        "Room ID: " + room.roomId +
                        " | Category: " + room.category +
                        " | Price: ₹" + room.price);
            }
        }
    }

    public void bookRoom(String name, int roomId) {
        for (Room room : rooms) {
            if (room.roomId == roomId && room.available) {

                System.out.println("\nProcessing Payment...");
                System.out.println("Payment Successful!");

                room.available = false;

                try {
                    FileWriter fw = new FileWriter("bookings.txt", true);
                    fw.write(name + "," + room.roomId + "," +
                            room.category + "," + room.price + "\n");
                    fw.close();
                } catch (IOException e) {
                    System.out.println("Error saving booking.");
                }

                System.out.println("\nBooking Confirmed!");
                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    public void cancelReservation(int roomId) {

        for (Room room : rooms) {
            if (room.roomId == roomId && !room.available) {

                room.available = true;

                System.out.println("Reservation Cancelled Successfully!");
                return;
            }
        }

        System.out.println("Reservation Not Found!");
    }

    public void viewBookings() {

        try {
            BufferedReader br =
                    new BufferedReader(new FileReader("bookings.txt"));

            String line;

            System.out.println("\nBooking Details:");

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                System.out.println("---------------------");
                System.out.println("Customer : " + data[0]);
                System.out.println("Room ID  : " + data[1]);
                System.out.println("Category : " + data[2]);
                System.out.println("Amount   : ₹" + data[3]);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("No Bookings Found!");
        }
    }
}

public class HotelReservationSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingManager manager = new BookingManager();

        while (true) {

            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    manager.viewRooms();
                    break;

                case 2:
                    sc.nextLine();

                    System.out.print("Enter Customer Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room ID: ");
                    int roomId = sc.nextInt();

                    manager.bookRoom(name, roomId);
                    break;

                case 3:
                    System.out.print("Enter Room ID to Cancel: ");
                    int cancelId = sc.nextInt();

                    manager.cancelReservation(cancelId);
                    break;

                case 4:
                    manager.viewBookings();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}