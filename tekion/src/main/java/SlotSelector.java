import entities.Slot;
import entities.User;

public interface SlotSelector {
    Slot allocate(User user);
}
