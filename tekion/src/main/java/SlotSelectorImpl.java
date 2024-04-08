import entities.Slot;
import entities.User;

import java.util.List;

public abstract class SlotSelectorImpl implements SlotSelector {
    private SlotRepository slotRepository;

    @Override
    public Slot allocate(User user) {
        List<Slot> freeSlots = slotRepository.getFreeSlots(user.getVehicleType());
        if(freeSlots.size() == 0) {
            throw new NoSlotAvailableException();
        }
        Slot selectedSlot = select(freeSlots);

        selectedSlot.setUser(user);

        return selectedSlot;
    }

    abstract Slot select(List<Slot> freeSlots);
}
