package lv.tele2ssc.bookshelf;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/mybooks", method = RequestMethod.GET)
    public String page(Model model) {
        Book b1 = new Book();
        b1.setId(1L);
        b1.setTitle("Book One");
        b1.setAuthor("John Smith");
        b1.setDescription("blah blah blah");
        b1.setYear(2035);

        List<Book> available = Collections.nCopies(3, b1);
        List<Book> queued = Collections.nCopies(5, b1);
        List<Book> owned = Collections.nCopies(2, b1);

        model.addAttribute("availableBooks", available);
        model.addAttribute("queuedBooks", queued);
        model.addAttribute("ownedBooks", owned);
        return "mybooks";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid User user, BindingResult bindingResult, Model model) {

        // some additional validation
        validateEmail(user, bindingResult);
        validatePassword(user, bindingResult);
        
        // validation isn't passed return back to registration form
        if (bindingResult.hasErrors()) {
            return "register";
        }

        boolean creating = user.getId() == null;
        userService.save(user);
        
        if (creating) {
            model.addAttribute("successMessage", "You are successfuly registered");
        } else {
            model.addAttribute("successMessage", "Profile is updated");
        }
        
        return "register";
    }

    private void validateEmail(User user, BindingResult bindingResult) {
        if (user == null) {
            return;
        }
        String email = user.getEmail();
        if (email == null) {
            return;
        }
        User existing = userService.findByEmail(email);
        if (existing != null && !Objects.equals(existing.getId(), user.getId())) {
            bindingResult.rejectValue("email", "already-exists", "User with this email already exists");
        }
    }

    private void validatePassword(User user, BindingResult bindingResult) {
        if (user == null) {
            return;
        }
        String p1 = user.getPassword();
        String p2 = user.getPassword2();
        if (p1 != null && !p1.equals(p2)) {
            bindingResult.rejectValue("password2", "pwd-not-match",  "Passwords don't match");
        }
    }

}
