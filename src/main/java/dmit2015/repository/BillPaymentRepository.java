package dmit2015.repository;

import dmit2015.entity.Bill;
import dmit2015.entity.BillPayment;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class BillPaymentRepository {

    @PersistenceContext(unitName = "h2database-jpa-pu")
    private EntityManager _entityManager;

    public Optional<BillPayment> findOneById(Long id) {
        Optional<BillPayment> optionalBillPayment = Optional.empty();
        try {
            BillPayment querySingleResult = _entityManager.find(BillPayment.class, id);
            if (querySingleResult != null) {
                optionalBillPayment = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalBillPayment;
    }

    public List<BillPayment> findAll() {
        return _entityManager.createQuery(
                "SELECT bp FROM BillPayment bp ORDER BY bp.paymentDate DESC "
                , BillPayment.class)
                .getResultList();
    }

    public List<BillPayment> findAllByBillId(Long billId) {
        return _entityManager.createQuery(
                "SELECT bp "
                        + " FROM BillPayment bp "
                        + " WHERE bp.billToPay.id = :billId "
                        + " ORDER BY bp.paymentDate DESC "
                , BillPayment.class)
                .setParameter("billId", billId)
                .getResultList();
    }

    public void create(BillPayment newBillPayment) {
        // Subtract the BillPayment paymentAmount from the Bill paymentBalance
        Bill existingBill = newBillPayment.getBillToPay();
        existingBill.setAmountBalance(existingBill.getAmountBalance().subtract(newBillPayment.getPaymentAmount()));
        // Set the payment to the current date
        newBillPayment.setPaymentDate(LocalDate.now());
        // Save the newBillPayment
        _entityManager.persist(newBillPayment);
        // Update the existingBill
        _entityManager.merge(existingBill);
    }

    public void update(BillPayment updatedBillPayment) {
        Optional<BillPayment> optionalBillPayment = findOneById(updatedBillPayment.getId());
        if (optionalBillPayment.isPresent()) {
            BillPayment existingBillPayment = optionalBillPayment.get();

            // Update the amountBalance on the Bill by adding the previous paymentAmount
            // and subtract the new paymentAmount
            Bill existingBill = existingBillPayment.getBillToPay();
            BigDecimal previousPaymentAmount = existingBillPayment.getPaymentAmount();
            BigDecimal newPaymentAmount = updatedBillPayment.getPaymentAmount();
            BigDecimal paymentAmountChange = newPaymentAmount.subtract(previousPaymentAmount);
            BigDecimal newAmountBalance = existingBill.getAmountBalance().subtract(paymentAmountChange);
            existingBill.setAmountBalance(newAmountBalance);
            _entityManager.merge(existingBill);

            existingBillPayment.setPaymentAmount(updatedBillPayment.getPaymentAmount());
            existingBillPayment.setPaymentDate(updatedBillPayment.getPaymentDate());
            existingBillPayment.setVersion(updatedBillPayment.getVersion());

            // Update the existingBillPayment
            _entityManager.merge(existingBillPayment);
            _entityManager.flush();
        }
    }

    public void delete(Long id) {
        Optional<BillPayment> optionalBillPayment = findOneById(id);
        if (optionalBillPayment.isPresent()) {
            BillPayment existingBillPayment = optionalBillPayment.get();

            // Add the paymentAmount from the Bill
            Bill existingBill = existingBillPayment.getBillToPay();
            existingBill.setAmountBalance(existingBill.getAmountBalance().add(existingBillPayment.getPaymentAmount()));
            _entityManager.merge(existingBill);
            // Remove the Bill
            _entityManager.remove(existingBillPayment);
        }
    }
}