package cl.inacap.SistemaMonito.controllers;


import cl.inacap.SistemaMonito.models.UserEntity;
import cl.inacap.SistemaMonito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public List<UserEntity> index(){
        return userService.allUsers();
    }

    @PostMapping("/user/create")
    public UserEntity create(@RequestBody UserEntity user) {
        userService.registrarUsuarioApi(user);
        return user;
    }


}
