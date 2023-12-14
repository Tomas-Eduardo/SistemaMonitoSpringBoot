package cl.inacap.SistemaMonito.controllers;

import cl.inacap.SistemaMonito.models.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }
    @GetMapping("/jefe")
    public String jefeRedirect(Model model) {
        model.addAttribute("user", new UserEntity());
        return "jefe/index";
    }

    @GetMapping("/vendedor")
    public String userRedirect(Model model) {
        model.addAttribute("user", new UserEntity());
        return "vendedor/index";
    }
}
