import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Problem Assumptions:

Taxi Count: Assume 4 taxis for simplicity, but it should scale to any number of taxis.
Points on Route: A, B, C, D, E, F (each 15 km apart)
Travel Time Between Points: 60 minutes

Charges:
Minimum Rs.100 for the first 5 km
Rs.10 per km thereafter

Initial Taxi Position: All taxis are stationed at A.

Booking Rules:

When a customer books a taxi:
A free taxi at the pickup point is allocated.
If no free taxi is available, the nearest taxi is allocated.
If two taxis are free at the same point, the one with lower earnings is allocated.
Taxis only charge from the pickup point to the drop point.
If no taxi is available, the booking is rejected.

Modules:
Call Taxi Booking

Sample Inputs and Outputs:

Input 1:

Customer ID: 1
Pickup Point: A
Drop Point: B
Pickup Time: 9

Output 1:

Taxi can be allotted.
Taxi-1 is allotted.

 */
public class TaxiBookingSystem {
    private static int customerId = 0;
    private static List<Taxi> taxis = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter the number of taxis : ");
        int taxi = sc.nextInt();
        for(int i=1;i<=taxi;i++) {
            taxis.add(new Taxi(i));
        }

        boolean isValid = true;
        while(isValid) {
            System.out.println("1.Book Taxi\n2.Display Taxi Details\n3.Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 :
                    bookTaxi();
                    break;
                case 2 :
                    getListOfTaxi();
                    break;
                case 3 :
                    isValid = false;
                    break;
                default:
                    System.out.println("Invalid choice, Try again.");
            }
        }
    }

    private static void bookTaxi() {
        System.out.println("Customer ID : " + ++customerId);
        System.out.println("Enter Pickup Point (A-F): ");
        char pickUp = sc.next().toUpperCase().charAt(0);
        System.out.println("Enter Drop Point (A-F): ");
        char drop = sc.next().toUpperCase().charAt(0);
        System.out.println("Enter PickUp Time (in hours): ");
        int pickUpTime = sc.nextInt();

        Taxi selectedTaxi = null;
        int minDistance = Integer.MAX_VALUE;

        for (Taxi taxi : taxis) {
            //check taxi available at req pickup time
            if (taxi.isAvailable(pickUpTime)) {
                //distance between current location and pickup point
                int distance = Math.abs(taxi.currentPoint - pickUp);
                //select taxi with min distance or low earnings if distance are equal
                if (distance < minDistance || (distance == minDistance && taxi.totalEarnings < selectedTaxi.totalEarnings)) {
                    selectedTaxi = taxi;
                    minDistance = distance;
                }
            }
        }

        if(selectedTaxi == null) {
            System.out.println("Booking rejected. No taxis available.");
        }

        int dropTime = pickUpTime + Math.abs(pickUp - drop);
        int amount = selectedTaxi.calculateEarnings(pickUp,drop);
        int bookingId = selectedTaxi.bookings.size()+1;

        Booking booking = new Booking(bookingId,customerId,pickUpTime,dropTime,amount,pickUp,drop);
        //adds the new booking to the selected taxi
        selectedTaxi.addBooking(booking);
        System.out.println("Taxi- " + selectedTaxi.taxiId + " is allocated.");
    }

    private static void getListOfTaxi() {
        for (Taxi taxi : taxis) {
            System.out.println("Taxi- " + taxi.taxiId + " Total Earnings: Rs." + taxi.totalEarnings);
            System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6s%n",
                    "BookingID", "CustomerID", "From", "To", "PickupTime", "DropTime", "Amount");

            for (Booking booking : taxi.bookings) {
                System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6s%n",
                        booking.bookingId, booking.customerId, booking.from, booking.to,
                        booking.pickupTime, booking.dropTime, booking.amount);
            }
        }
    }

}
