package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.exception.UnauthorizedException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.service.AcquisitionService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import model.DatabaseObject;

import java.util.*;

@RestController
@RequestMapping("/acquisitions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AcquisitionController {
    // @Autowired
    AcquisitionService acquisitionService = new AcquisitionService();
    // @Autowired
    UserService userService = new UserService();

    @GetMapping(value = "/", produces = "application/json")
    public List<Map<String, Object>> viewAcquisitionList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        //userService.isLogin(new Integer(uid), token);
        return acquisitionService.acquisitionList();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Map<String, Object> viewAcquisition(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer aid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return acquisitionService.acquisitionById(aid);
    }


    @GetMapping(value = "/{id}/items", produces = "application/json")
    public List<Map<String, Object>> getItemsByAid(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer aid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return acquisitionService.getAcquisitionItemsById(aid);
    }
}
