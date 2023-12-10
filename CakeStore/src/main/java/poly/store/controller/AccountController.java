package poly.store.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.store.entity.Account;
import poly.store.entity.Product;
import poly.store.service.AccountService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;

//    @RequestMapping("/account/create")
//    public ResponseEntity<Account> createAccount(
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam String fullname,
//            @RequestParam String email,
//            @RequestParam String phone,
//            @RequestParam String address) throws NotFoundException {
//        // Create an Account object from the form data and proceed with account creation
//        Account account = new Account(username, password, fullname, email, phone, address);
//        Account createdAccount = accountService.create(account);
//
//        // Redirect to the login form
//        return ResponseEntity.status(HttpStatus.SEE_OTHER)
//                .header(HttpHeaders.LOCATION, "/security/login/form")
//                .body(createdAccount);
//    }
@RequestMapping("/account/create")
public ResponseEntity<Account> createAccount(Account accounts,
        Model model) throws NotFoundException {
    // If there are no validation errors, proceed with account creation
    Account createdAccount = accountService.create(accounts);

    // Redirect to the login form
    return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .header(HttpHeaders.LOCATION, "/security/login/form")
            .body(createdAccount);
}

    @RequestMapping("/account/detail/{username}")
    public String detail(Model model, @PathVariable("username") String username) {
        Account item = accountService.findById(username);
        model.addAttribute("item", item);
        return "security/information";
    }

    @RequestMapping("account/update")
    public String updateAccount(Principal p,
            @RequestParam String email,
            @RequestParam String fullname,
            @RequestParam String phone,
            @RequestParam String address,
            RedirectAttributes redirectAttributes) throws NotFoundException {
        Account existingAccount = accountService.findById(p.getName());

        if (existingAccount == null) {
            throw new NotFoundException("Account not found with username: " + p.getName());
        }
        existingAccount.setEmail(email);
        existingAccount.setFullname(fullname);
        existingAccount.setPhone(phone);
        existingAccount.setAddress(address);
        accountService.update(existingAccount);
        redirectAttributes.addFlashAttribute("message", "Account information updated successfully");
        return "redirect:/account/detail/"+p.getName();
    }

    @RequestMapping("account/change-password/form")
    public String index() {
        return "security/changePassword";
    }

    @PostMapping("account/change-password")
    public String change(Model model,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String newPassword,
                         @RequestParam String confirmPassword) throws NotFoundException {
        // Retrieve the existing account based on the provided username
        Account existingAccount = accountService.findById(username);
        if (existingAccount == null) {
            throw new NotFoundException("Account not found with username: " + username);
        }
        if (!passwordEncoder.matches(password, existingAccount.getPassword())) {
            // Handle incorrect password
            model.addAttribute("error", "Incorrect password");
            return "security/changePassword"; // Replace with the appropriate error page
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match");
            return "error-page"; // Replace with the appropriate error page
        }
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        existingAccount.setPassword(hashedNewPassword);
        accountService.save(existingAccount);
        return "redirect:/account/detail/" + username;
    }

}
