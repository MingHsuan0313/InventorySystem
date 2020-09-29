package ntu.csie.selab.inventorysystem.controller;

import jdk.nashorn.internal.runtime.ListAdapter;
import manager.DatabaseManager;
import model.DatabaseObject;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
//import ntu.csie.selab.inventorysystem.model.Token;
import ntu.csie.selab.inventorysystem.model.User;
import ntu.csie.selab.inventorysystem.service.HierarchyService;
import ntu.csie.selab.inventorysystem.service.TryService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import selab.service.database.pojo.Token;

import ntu.csie.selab.inventorysystem.service.AcquisitionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import manager.*;

@RestController
@RequestMapping("/marks")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost"}, allowCredentials = "true")
public class TryController {
    // @Autowired
    TryService tryService = new TryService();
    // @Autowired
    UserService userService = new UserService();
    // Token t = new Token();

    @PostMapping(value = "/login2", produces = "application/json")
    public Map<String, Object> login2(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        // Login Process
        DatabaseObject user = tryService.authenticate2(
                (String) map.getOrDefault("username", ""),
                (String) map.getOrDefault("password", "")
        );

        tryService.expireToken2(user);
        DatabaseObject token = tryService.assignNewToken2(user);

        Cookie cookie;
        cookie = new Cookie("uid", token.get("uid").toString());
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie = new Cookie("token", token.get("token").toString());
        cookie.setPath("/");
        response.addCookie(cookie);

        // Response Body
        Map<String, Object> body = new HashMap<>();
        body.put("privilege", 1);
        return body;
    }
    @GetMapping(value = "/apple", produces = "application/json")
    public String createTry(){
        DatabaseManager.createDatabase("SQLite");
        return "APPLE";
    };

    // API: Add Department
    @PostMapping(value = "/departments", produces = "application/json")
    public Department addDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @Valid @RequestBody Department department,
            BindingResult validation
    ) {
        //tryService.isLogin2(new Integer(uid), token);
        /*if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));*/
        return tryService.departmentInsert(department);
    }
    @PostMapping(value = "/departments/{id}/categories", produces = "application/json")
    public Category addCategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String did,
            @Valid @RequestBody Category category,
            BindingResult validation
    ) {
        //userService.isLogin(new Integer(uid), token);
        /*if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));*/
        return tryService.categoryInsert(did, category);
    }
    //set Table "Department"
    @GetMapping(value = "/createTable/{tableName}", produces = "application/json")
    public String crT(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "tableName") String tableName
    ) {
        //userService.isLogin(new Integer(uid), token);
        //tryService.isLogin2(new Integer(uid), token);
        return tryService.createTable(tableName);
    }
    //delete Table "Department"
    @GetMapping(value = "/deleteTable/{tableName}", produces = "application/json")
    public String deT(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "tableName") String tableName
    ) {
        //userService.isLogin(new Integer(uid), token);
        return tryService.deleteTable(tableName);
    }

    //set Table "category"
    /*@GetMapping(value = "/createTableCategory", produces = "application/json")
    public String crTcategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        userService.isLogin(new Integer(uid), token);
        return TryService.createTable("Category");
    }
    //delete Table "category"
    @GetMapping(value = "/deleteTableCategory", produces = "application/json")
    public String deTcategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        userService.isLogin(new Integer(uid), token);
        return TryService.deleteTable("Category");
    }*/

    //get department
    @GetMapping(value = "/getDepartmentList", produces = "application/json")
    public List<Map<String, Object>> getDepartL(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        //tryService.isLogin2(new Integer(uid), token);
        return tryService.departmentList();
    }

    @GetMapping(value = "/TryANY", produces = "application/json")
    public String TTQ(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        //userService.isLogin(new Integer(uid), token);
        return tryService.gerTry();
    }

    @GetMapping(value = "/addUser/{username}/{password}/{priviledge}", produces = "application/json")
    public String addUser(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "username") String username,
            @PathVariable(name = "password") String password,
            @PathVariable(name = "priviledge") int priviledge
    ) {
        //userService.isLogin(new Integer(uid), token);
        return tryService.addUser(username,password,priviledge);
    }

    @GetMapping(value = "/onlyCookie", produces = "application/json")
    public String onlyCookie(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        return "OnlyCookie";
    }

    @GetMapping(value = "/nothing", produces = "application/json")
    public String nothing() {

        return "Nothing";
    }
}
