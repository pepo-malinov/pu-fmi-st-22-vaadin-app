package uni.pu.fmi.st.data.service;


import org.springframework.data.jpa.repository.JpaRepository;
import uni.pu.fmi.st.data.entity.Owner;

import java.util.UUID;


public interface OwnerRepo extends JpaRepository<Owner, UUID>
{
}
