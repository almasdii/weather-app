package testingSpring.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testingSpring.dto.UserRegisterDto;
import testingSpring.serivce.UserService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService service;

    @Autowired
    public AuthController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping(value = "/login")
    public void login(Model model,
                      @RequestParam(required = false,value = "surname") String surname,
                      HttpServletRequest request,
                      HttpServletResponse response){
    }

    @GetMapping(value = "/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(Model model,HttpServletRequest request){
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repassword");
        if(login == null || password == null || repeatPassword == null){
            System.out.println("Enter login and password");
        }
        UserRegisterDto userRegisterDto = new UserRegisterDto(login,password);
        model.addAttribute("userRegisterDto",userRegisterDto);
        service.registerUser(userRegisterDto);
        return "login";
    }
}
