package dmit2015.view;

import dmit2015.entity.Bill;
import dmit2015.repository.BillRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentBillEditController")
@ViewScoped
public class BillEditController implements Serializable {

    @Inject
    private BillRepository _billRepository;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private Bill existingBill;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            Optional<Bill> optionalEntity = _billRepository.findOneById(editId);
            optionalEntity.ifPresent(entity -> existingBill = entity);
        }
    }

    public String onUpdate() {
        String nextPage = "";
        try {
            _billRepository.update(existingBill);
            Messages.addFlashGlobalInfo("Update was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Update was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _billRepository.delete(existingBill.getId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful. {0}", e.getMessage());
            ;
        }
        return nextPage;
    }
}