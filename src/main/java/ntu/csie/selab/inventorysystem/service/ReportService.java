package ntu.csie.selab.inventorysystem.service;

import manager.DatabaseManager;
import model.DatabaseObject;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.repository.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportService {
    /*@Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ItemRepository itemRepository;*/



    /*public String gerReportString() throws JSONException {
        JSONObject object_2D = new JSONObject();
        JSONArray jsonA = new JSONArray();

        for(Department department : departmentRepository.findAll()) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", department.getId());
            jsonO.put("name", department.getName());
            jsonO.put("description", department.getDescription());
            jsonO.put("POSCode", department.getCode());
            jsonO.put("tag", department.getTag());
            jsonA.put(jsonO);
        }

        //System.out.println(jsonA);
        object_2D.put("DEPARTMENT",jsonA);

        jsonA = new JSONArray();
        for(Category category : categoryRepository.findAll()) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", category.getId());
            jsonO.put("name", category.getName());
            jsonO.put("description", category.getDescription());
            jsonO.put("tag", category.getTag());
            jsonO.put("sub", category.getSub());
            jsonA.put(jsonO);
        }
        //System.out.println(jsonA);
        object_2D.put("CATEGORY",jsonA);

        jsonA = new JSONArray();
        for(Item item: itemRepository.findAll()) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", item.getId());
            jsonO.put("description", item.getDescription());
            jsonO.put("quantity", item.getQuantity());
            jsonO.put("condition", item.getCondition());
            jsonO.put("price", item.getPrice());
            jsonA.put(jsonO);
        }
        object_2D.put("ITEM",jsonA);
        return object_2D.toString();
    }*/
    public String gerReportString() throws JSONException {
        JSONObject object_2D = new JSONObject();
        JSONArray jsonA = new JSONArray();

        for(DatabaseObject department : DatabaseManager.retrieveAll("Department")) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", department.get("__id"));
            jsonO.put("name", department.get("name"));
            jsonO.put("description", department.get("description"));
            jsonO.put("POSCode", department.get("POSCode"));
            jsonO.put("tag", department.get("tag"));
            jsonA.put(jsonO);
        }

        //System.out.println(jsonA);
        object_2D.put("DEPARTMENT",jsonA);

        jsonA = new JSONArray();
        for(DatabaseObject category : DatabaseManager.retrieveAll("Category")) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", category.get("__id"));
            jsonO.put("name", category.get("name"));
            jsonO.put("description", category.get("description"));
            jsonO.put("tag", category.get("tag"));
            jsonO.put("sub", category.get("sub"));
            jsonA.put(jsonO);
        }
        //System.out.println(jsonA);
        object_2D.put("CATEGORY",jsonA);

        jsonA = new JSONArray();
        for(DatabaseObject item : DatabaseManager.retrieveAll("Item")) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", item.get("__id"));
            jsonO.put("description", item.get("description"));
            jsonO.put("quantity", item.get("quantity"));
            jsonO.put("condition", item.get("condition"));
            jsonO.put("price", item.get("price"));
            jsonA.put(jsonO);
        }
        object_2D.put("ITEM",jsonA);
        return object_2D.toString();
    }

}
