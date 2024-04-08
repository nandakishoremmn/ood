import entities.Slot;
import entities.User;

public interface BillingManager {
    void startBilling(User user, Slot slot);

    double stopBilling(Slot slot);
}
