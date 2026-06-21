package testingSpring.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import testingSpring.dto.UserLoginDto;
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

    @GetMapping(value = "/sign-in")
    public String signInPage(@ModelAttribute("userLoginDto") UserLoginDto userLoginDto) {
        return "sign-in";
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@ModelAttribute("userLoginDto") @Valid UserLoginDto userLoginDto,
                         BindingResult result,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        if (result.hasErrors()){
            return "sign-in-with-errors";
        }
        service.createSession(userLoginDto);
        System.out.println( userLoginDto);

        return "index";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("userRegisterDto")  UserRegisterDto userRegisterDto) {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute("userRegisterDto") @Valid UserRegisterDto userRegisterDto , BindingResult result) {
        if(result.hasErrors()){
            return "sign-up-with-errors";
        }
        System.out.println(userRegisterDto);
        service.registerUser(userRegisterDto);
        return "redirect:/auth/sign-in";
    }
}
