package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserServiceImp;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class RestControllers {

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/api/authenticate")
    public ResponseEntity<User> authenticate(Principal principal) {
        User authorizedUser = userServiceImp.findUserByEmail(principal.getName());
        return ResponseEntity.ok(authorizedUser);
    }

    @GetMapping("/admin/allusers")
    public ResponseEntity <List<User>> getAllUsers(){
        return ResponseEntity.ok(userServiceImp.getAllUsers());
    }

    @PostMapping("/admin/new")
    public void addUser(@RequestBody User user){
        Set<Role> roles = user.getRoles();
        if(user.getIsAdmin()){
            roles.add(Role.ADMIN);
        }
        if(user.getIsUser()){
            roles.add(Role.USER);
        }
        user.setRoles(roles);
        userServiceImp.addUser(user);
    }

    @PostMapping("/admin/updateuser")
    public void updateUser(@RequestBody User user){
        Set<Role> roles = user.getRoles();
        if(user.getIsAdmin()){
            roles.add(Role.ADMIN);
        }
        if(user.getIsUser()){
            roles.add(Role.USER);
        }
        user.setRoles(roles);
        userServiceImp.updateUser(user);
    }

    @DeleteMapping("/admin/deleteuser/{id}")
    public void deleteUser(@PathVariable Long id){
        User user = userServiceImp.findUserByID(id);
        userServiceImp.deleteUser(user);
    }
}
