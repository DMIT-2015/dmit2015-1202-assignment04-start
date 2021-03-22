package dmit2015.view;

import dmit2015.entity.BillPayment;
import dmit2015.repository.BillPaymentRepository;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentBillPaymentListController")
@ViewScoped
public class BillPaymentListController implements Serializable {

    @Inject
    private BillPaymentRepository _billpaymentRepository;

    @Getter
    private List<BillPayment> billpaymentList;

    @Inject
    @ManagedProperty("#{param.billId}")
    @Getter
    @Setter
    private Long billId;

    @PostConstruct  // After @Inject is complete
    public void init() {
        if (!Faces.isPostback()) {
            try {
                if (billId == null) {
                    billpaymentList = _billpaymentRepository.findAll();
                } else {
                    billpaymentList = _billpaymentRepository.findAllByBillId(billId);
                }
            } catch (Exception ex) {
                Messages.addGlobalError(ex.getMessage());
            }
        }
    }
}