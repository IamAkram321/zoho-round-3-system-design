public class Booking {

    int bookingId, customerId, pickupTime, dropTime, amount;
    char from, to;

    public Booking(int bookingId, int customerId, int pickupTime, int dropTime, int amount, char from, char to) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.amount = amount;
        this.from = from;
        this.to = to;
    }
}