package uni.pu.fmi.st.data.service;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uni.pu.fmi.st.data.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}