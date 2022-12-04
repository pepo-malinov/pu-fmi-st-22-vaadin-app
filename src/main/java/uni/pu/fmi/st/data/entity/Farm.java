package uni.pu.fmi.st.data.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
@Data
public class Farm extends AbstractEntity
{
    @ManyToOne
    private Owner owner;
    private String name;
    private String address;
    @ManyToOne
    @NotNull(message = "Изберете управител!")
    private User manager;
}
