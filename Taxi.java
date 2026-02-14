import java.util.ArrayList;
import java.util.List;

public class Taxi {

    int taxiId;
    char currentPoint = 'A';
    int totalEarnings;
    List<Booking> bookings;

    Taxi(int taxiId) {
        this.taxiId = taxiId;
        this.bookings = new ArrayList<>();
    }

    public boolean isAvailable(int requestTime) {
        if(bookings.isEmpty()) return true;
        Booking lastBooking = bookings.getLast();
        return lastBooking.dropTime <= requestTime;
    }

    public int calculateEarnings(char from, char to) {
        int distance = Math.abs(to - from) * 15;
        return 100 + Math.max(0, (distance - 5) * 10);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        totalEarnings += booking.amount; //updating the state
        currentPoint = booking.to; //updating current point
    }

}