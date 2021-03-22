package dmit2015.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected Long id;

    @NotBlank(message = "Please enter a payee name")
    protected String payeeName;

    @NotNull(message = "Please enter/select the due date")
    @FutureOrPresent(message = "Payment due date must be in the future or present day")
    protected LocalDate dueDate;

    @NotNull(message = "Please enter the amount that is due.")
    protected BigDecimal amountDue;

    protected BigDecimal amountBalance;

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
