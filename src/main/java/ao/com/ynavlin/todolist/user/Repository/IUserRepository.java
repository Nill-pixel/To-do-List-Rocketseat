package ao.com.ynavlin.todolist.user.Repository;

import ao.com.ynavlin.todolist.user.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
