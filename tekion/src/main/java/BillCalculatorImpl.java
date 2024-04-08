import entities.Invoice;

public class BillCalculatorImpl implements BillCalculator {
    @Override
    public double calculateAmount(Invoice invoice) {
        return 10;
    }
}
