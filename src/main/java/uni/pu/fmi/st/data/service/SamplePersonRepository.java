package uni.pu.fmi.st.data.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.pu.fmi.st.data.entity.SamplePerson;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}