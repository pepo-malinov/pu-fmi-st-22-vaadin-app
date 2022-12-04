package uni.pu.fmi.st.data.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Entity
public class Owner extends AbstractEntity
{
    @NotBlank(message = "Въведете идентификатор на собственика!")
    private String ownerId;
    @Email
    private String email;
    private String name;
    private String gsm;
}
