package cl.inacap.SistemaMonito.controllers;

import cl.inacap.SistemaMonito.models.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
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
