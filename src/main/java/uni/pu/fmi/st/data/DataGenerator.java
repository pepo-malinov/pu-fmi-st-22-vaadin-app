package uni.pu.fmi.st.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uni.pu.fmi.st.data.entity.User;
import uni.pu.fmi.st.data.service.UserService;

import javax.annotation.PostConstruct;


@Component
public class DataGenerator
{
    @Autowired
    private UserService userService;

    @PostConstruct
    public void generate(){
        if(userService.count()==0){
            final User user = new User();
            user.setName("Иван");
            user.setUsername("ivan");
            userService.update(user);
        }
    }
}
