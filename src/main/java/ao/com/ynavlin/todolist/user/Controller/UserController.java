package ao.com.ynavlin.todolist.user.Controller;

import ao.com.ynavlin.todolist.user.Model.UserModel;
import ao.com.ynavlin.todolist.user.Repository.IUserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/users")
public class UserController {

    public final IUserRepository iUserRepository;
    @Autowired
    public UserController(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){

        var user = iUserRepository.findByUsername(userModel.getUsername());

        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        var passwordHashed = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);

        var userCreated = iUserRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
