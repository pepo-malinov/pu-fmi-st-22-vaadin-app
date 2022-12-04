package uni.pu.fmi.st.data.service;


import org.springframework.data.jpa.repository.JpaRepository;
import uni.pu.fmi.st.data.entity.Animal;

import java.util.UUID;


public interface AnimalRepo extends JpaRepository<Animal, UUID>
{
}
