package testingSpring.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @PostMapping(value = "/login")
    public void loginCheck(Model model,
                           @RequestParam(required = false,value = "surname") String surname,
                           HttpServletRequest request,
                           HttpServletResponse response){

    }
}
