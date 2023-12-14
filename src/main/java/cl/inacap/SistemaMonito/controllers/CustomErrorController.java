package cl.inacap.SistemaMonito.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Obtener información sobre el error
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            // Personalizar mensajes de error según el código de estado
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error", "Página no encontrada");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("error", "Error interno del servidor");
            } else {
                model.addAttribute("error", "Error inesperado");
            }
        }

        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}