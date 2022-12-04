package uni.pu.fmi.st.data.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uni.pu.fmi.st.data.entity.SamplePerson;

@Service
public class SamplePersonService extends BaseService<SamplePerson>
{

    @Autowired
    public SamplePersonService(SamplePersonRepository repository)
    {
        super(repository);
    }

}
