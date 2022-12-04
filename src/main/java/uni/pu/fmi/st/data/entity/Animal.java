package uni.pu.fmi.st.data.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;


@Entity
@Data
public class Animal extends AbstractEntity
{
    @ManyToOne
    private Farm farm;
    private LocalDate birthDate;
    private String name;
    private String animalId;
    @ManyToOne
    private Animal father;
    @ManyToOne
    private Animal mother;
    private String breed;
}
