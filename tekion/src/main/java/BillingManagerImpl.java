import entities.Invoice;
import entities.Slot;
import entities.User;

import java.util.Date;

public class BillingManagerImpl implements BillingManager {
    private final Billingepository billingepository;

    private final BillCalculator billCalculator;

    public BillingManagerImpl(Billingepository billingepository, BillCalculator billCalculator) {
        this.billingepository = billingepository;
        this.billCalculator = billCalculator;
    }

    @Override
    public void startBilling(User user, Slot slot) {
        Date date = new Date();
        Invoice invoice = null;
        billingepository.add(invoice);
    }

    @Override
    public double stopBilling(Slot slot) {
        Invoice invoice = billingepository.getInvoice(slot);
        invoice.setEndTime(new Date());
        billingepository.update(invoice);
        return billCalculator.calculateAmount(invoice);

    }
}
