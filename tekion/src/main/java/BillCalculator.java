import entities.Invoice;

public interface BillCalculator {
    double calculateAmount(Invoice invoice);
}
