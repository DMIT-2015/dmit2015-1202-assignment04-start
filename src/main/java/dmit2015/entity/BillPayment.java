package dmit2015.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill billToPay;

    @Column(nullable = false)
    @NotNull(message = "Please enter the payment amount")
    @DecimalMin(value = "0.01", message = "Please enter a payment amount greater or equal to ${value}")
    private BigDecimal paymentAmount;

    @FutureOrPresent(message = "Payment due date must be in the future or present day", groups = {NewBillChecks.class})
    @Column(nullable = false)
    private LocalDate paymentDate = LocalDate.now();

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime lastModified;

    @Version
    private Integer version;

    @PrePersist
    private void beforePersist() {
        created = LocalDateTime.now();
        lastModified = created;
    }

    @PreUpdate
    private void beforeUpdate() {
        lastModified = LocalDateTime.now();
    }

}
