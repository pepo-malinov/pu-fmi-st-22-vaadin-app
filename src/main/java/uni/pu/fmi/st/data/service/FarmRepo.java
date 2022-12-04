package uni.pu.fmi.st.data.service;


import org.springframework.data.jpa.repository.JpaRepository;
import uni.pu.fmi.st.data.entity.Farm;
import uni.pu.fmi.st.data.entity.Owner;

import java.util.List;
import java.util.UUID;


public interface FarmRepo extends JpaRepository<Farm, UUID>
{
    List<Farm> findByOwner(Owner owner);
}
