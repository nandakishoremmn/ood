import entities.Slot;
import entities.User;

public class ParkingApplicationImpl implements ParkingApplication {
    private final BillingManager billingManager;

    private final SlotRepository slotRepository;

    private final SlotSelector slotSelector;

    public ParkingApplicationImpl(BillingManager billingManager, SlotRepository slotRepository, SlotSelector slotSelector) {
        this.billingManager = billingManager;
        this.slotRepository = slotRepository;
        this.slotSelector = slotSelector;
    }

    @Override
    public void park(User user) {
        try {
            Slot slot = slotSelector.allocate(user);
            billingManager.startBilling(user, slot);

        } catch (NoSlotAvailableException e) {
            System.out.println("No slot Available");
        }

    }

    @Override
    public void unpark(User user) {
        try {
            Slot slot = slotRepository.getUserSlot(user);
            billingManager.stopBilling(slot);
        } catch (UseNotParkedException e) {
            System.out.println("entities.User not parked");
        }
    }
}
