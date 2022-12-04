package uni.pu.fmi.st.data.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uni.pu.fmi.st.data.entity.Owner;

import java.util.UUID;

@Service
public class OwnerService extends BaseService<Owner>
{
    public OwnerService(OwnerRepo repository)
    {
        super(repository);
    }
}
