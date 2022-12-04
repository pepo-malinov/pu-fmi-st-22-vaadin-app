package uni.pu.fmi.st.data.service;


import org.springframework.stereotype.Service;
import uni.pu.fmi.st.data.entity.Farm;
import uni.pu.fmi.st.data.entity.Owner;

import java.util.ArrayList;
import java.util.List;


@Service
public class FarmService extends BaseService<Farm>
{
    public FarmService(FarmRepo repository)
    {
        super(repository);
    }


    public List<Farm> findByOwner(Owner owner)
    {
        return owner == null ? new ArrayList<>() : ((FarmRepo)getRepo()).findByOwner(owner);
    }
}
