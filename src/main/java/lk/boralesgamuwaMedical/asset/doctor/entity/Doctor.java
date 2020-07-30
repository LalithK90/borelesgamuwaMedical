package lk.boralesgamuwaMedical.asset.doctor.entity;

import lk.boralesgamuwaMedical.asset.commonAsset.model.Enum.Gender;
import lk.boralesgamuwaMedical.asset.commonAsset.model.Enum.Title;
import lk.boralesgamuwaMedical.asset.consultation.entity.Consultation;
import lk.boralesgamuwaMedical.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends AuditEntity {

    @Column(length = 45)
    private String name;

    private String code;

    @Column(unique = true)
    private Integer slmcNumber;

    private String mobile;

    private String land;

    @Email(message = "Please provide valid email")
    @Column(length = 45)
    private String email;

    @Column(length = 10)
    private String description;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private Consultation consultation;

    //@OneToMany
    //@ManyToOne
    /*    @OneToMany(mappedBy = "doctor")
        private List<Invoice> invoices = new ArrayList<>();*/

}
