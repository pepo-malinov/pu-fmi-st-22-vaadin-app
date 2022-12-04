package uni.pu.fmi.st.data.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Data
public class Farm extends AbstractEntity
{
    @ManyToOne
    private Owner owner;
    private String name;
    private String address;
    @ManyToOne
    private User manager;
}
