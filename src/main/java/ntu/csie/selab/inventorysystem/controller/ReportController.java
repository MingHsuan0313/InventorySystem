package ntu.csie.selab.inventorysystem.controller;


import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.repository.*;
import ntu.csie.selab.inventorysystem.service.ReportService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class ReportController {

    // @Autowired
    ReportService reportService = new ReportService();

    // @Autowired
    UserService userService = new UserService();

    @GetMapping(value = "/report", produces = "application/json")
    public String getReport(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) throws JSONException {

        //userService.isLogin(new Integer(uid), token);
        return reportService.gerReportString();
    }

}
