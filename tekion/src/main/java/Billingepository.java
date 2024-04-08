import entities.Invoice;
import entities.Slot;

public interface Billingepository {
    void add(Invoice invoice);

    Invoice getInvoice(Slot slot);

    void update(Invoice invoice);
}
