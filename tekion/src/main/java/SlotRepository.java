import entities.Slot;
import entities.User;
import entities.VehicleType;

import java.util.List;

public interface SlotRepository {
    List<Slot> getFreeSlots(VehicleType vehicleType);

    Slot getUserSlot(User user);
}
