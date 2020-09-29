package ntu.csie.selab.inventorysystem.controller;

import manager.DatabaseManager;
import model.DatabaseObject;
import model.Restriction;
import ntu.csie.selab.inventorysystem.service.TryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.model.Token;
import ntu.csie.selab.inventorysystem.model.User;
import ntu.csie.selab.inventorysystem.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    // @Autowired
    UserService userService = new UserService();

    @PostMapping(value = "/login", produces = "application/json")
    public Map<String, Object> login(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        // Login Process
        /*User user = userService.authenticate(
                (String) map.getOrDefault("username", ""),
                (String) map.getOrDefault("password", "")
        );
        for (Token token : user.getTokens())
            userService.expireToken(token);
        Token token = userService.assignNewToken(user);*/

        DatabaseObject user = userService.authenticate(
                (String) map.getOrDefault("username", ""),
                (String) map.getOrDefault("password", "")
        );
        userService.expireToken(user);
        DatabaseObject token = userService.assignNewToken(user);


        // Set Cookie
        /*Cookie cookie;
        cookie = new Cookie("uid", token.getUser().getId().toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("token", token.getToken());
        cookie.setPath("/");
        response.addCookie(cookie);*/
        Cookie cookie;
        cookie = new Cookie("uid", token.get("uid").toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("token", token.get("token").toString());
        cookie.setPath("/");
        response.addCookie(cookie);

        // Response Body
        Map<String, Object> body = new HashMap<>();

        List<Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("__id",token.get("uid").toString());
        restrict.add(r1);
        List<DatabaseObject> list_user = DatabaseManager.retrieveWithRestriction("User",restrict);
        body.put("privilege", list_user.get(0).get("privilege"));
        return body;
    }
}
