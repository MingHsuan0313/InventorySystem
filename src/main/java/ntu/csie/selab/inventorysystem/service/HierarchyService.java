package ntu.csie.selab.inventorysystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import manager.DatabaseManager;
import model.DatabaseObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.csie.selab.inventorysystem.exception.ConflictException;
import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.HierarchyC2Sc;
import ntu.csie.selab.inventorysystem.model.HierarchyD2C;
import ntu.csie.selab.inventorysystem.repository.CategoryRepository;
import ntu.csie.selab.inventorysystem.repository.DepartmentRepository;
import ntu.csie.selab.inventorysystem.repository.HierarchyC2ScRepository;
import ntu.csie.selab.inventorysystem.repository.HierarchyD2CRepository;
import ntu.csie.selab.inventorysystem.repository.InventoryRepository;

@Service()
public class HierarchyService {

    public List<Map<String, Object>> departmentList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DatabaseObject department : DatabaseManager.retrieveAll("Department")) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", department.get("__id"));
            map.put("name", department.get("name"));
            list.add(map);
        }
        return list;
    }

    public Map<String, Object> departmentById(String id) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("__id", id);
        restrict.add(r1);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Department", restrict);
        if (list.size() == 0)
            throw new NotFoundException(String.format("No department found with id: %s.", id));
        Map<String, Object> return_map = new HashMap<>();
        return_map.put("id", list.get(0).get("__id"));
        return_map.put("name", list.get(0).get("name"));
        return_map.put("description", list.get(0).get("description"));
        return_map.put("Code", list.get(0).get("POSCode"));
        return_map.put("tag", list.get(0).get("tag"));
        return return_map;
    }

    public String departmentInsert(Department department) {
        List<model.Restriction> restrict = new ArrayList<>();
        List<model.Restriction> and_restrict_id = new ArrayList<>();
        List<model.Restriction> and_restrict_tag = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("name", department.getName());
        restrict.add(r1);
        List<DatabaseObject> list_name = DatabaseManager.retrieveWithRestriction("Department", restrict);
        model.Restriction r3 = model.Restriction.equal("tag", department.getTag());
        and_restrict_tag.add(r3);
        restrict.clear();
        restrict.add(r3);
        List<DatabaseObject> list_tag = DatabaseManager.retrieveWithRestriction("Department", restrict);
        if (department.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: \'id\'.");
        if (list_name.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'name\'.");
        if (list_tag.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'tag\'.");
        DatabaseObject d = DatabaseObject.initMethod("Department");
        d.putString("POSCode", department.getCode());
        d.putString("name", department.getName());
        d.putString("description", department.getDescription());
        d.putString("tag", department.getTag());
        d.putInteger("id", department.getId());
        DatabaseManager.save(d);
        return d.toString();
    }

    public String departmentUpdate(Department department) {
        List<model.Restriction> restrict = new ArrayList<>();
        List<model.Restriction> and_restrict_id = new ArrayList<>();
        List<model.Restriction> and_restrict_tag = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("name", department.getName());
        model.Restriction r2 = model.Restriction.equal("__id", department.getId().toString());
        model.Restriction not_r2 = model.Restriction.not(r2);
        and_restrict_id.add(r1);
        and_restrict_id.add(not_r2);
        model.Restriction and = model.Restriction.and(and_restrict_id);
        restrict.add(and);
        List<DatabaseObject> list_id = DatabaseManager.retrieveWithRestriction("Department", restrict);
        model.Restriction r3 = model.Restriction.equal("tag", department.getTag());
        and_restrict_tag.add(r3);
        model.Restriction r4 = model.Restriction.equal("__id", department.getId().toString());
        model.Restriction not_r4 = model.Restriction.not(r4);
        and_restrict_tag.add(not_r4);
        and = model.Restriction.and(and_restrict_tag);
        restrict.clear();
        restrict.add(and);
        List<DatabaseObject> list_tag = DatabaseManager.retrieveWithRestriction("Department", restrict);
        if (department.getId() == null)
            throw new UnprocessableEntityException("Should not contain database-generated field: \'id\'.");
        if (list_id.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'name\'.");
        if (list_tag.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'tag\'.");
        restrict.clear();
        model.Restriction r5 = model.Restriction.equal("__id", department.getId().toString());
        restrict.add(r5);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Department", restrict);
        DatabaseObject d = list.get(0);
        d.putString("POSCode", department.getCode());
        d.putString("name", department.getName());
        d.putString("description", department.getDescription());
        d.putString("tag", department.getTag());
        d.putInteger("id", department.getId());
        DatabaseManager.update(d);
        return d.toString();
    }

    public void departmentDelete(String id) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("did", id);
        restrict.add(r1);
        List<DatabaseObject> list_category = DatabaseManager.retrieveWithRestriction("Department_Category", restrict);
        model.Restriction r2 = model.Restriction.equal("did", id);
        restrict.clear();
        restrict.add(r2);
        List<DatabaseObject> list_item = DatabaseManager.retrieveWithRestriction("Inventory", restrict);
        if ((list_category.size() > 0) || (list_item.size() > 0))
            throw new ConflictException("Cannot delete a department which is not empty.");
        restrict.clear();
        r1 = model.Restriction.equal("__id", id);
        restrict.add(r1);
        DatabaseManager.deleteWithRestriction("Department", restrict);
    }

    public List<Map<String, Object>> categoryList(String did) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r3 = model.Restriction.equal("did", did);
        restrict.add(r3);
        List<DatabaseObject> list_d2c = DatabaseManager.retrieveWithRestriction("Department_Category", restrict);
        for (DatabaseObject databaseObject : list_d2c) {
            restrict.clear();
            model.Restriction r2 = model.Restriction.equal("__id", databaseObject.get("cid").toString());
            restrict.add(r2);
            List<DatabaseObject> list_category = DatabaseManager.retrieveWithRestriction("Category", restrict);
            if (list_category.size() == 0)
                continue;
            Map<String, Object> map = new HashMap<>();
            map.put("id", list_category.get(0).get("__id"));
            map.put("name", list_category.get(0).get("name"));
            list.add(map);
        }
        return list;
    }

    public Map<String, Object> categoryById(String id) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("__id", id);
        restrict.add(r1);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Category", restrict);
        if (list.size() == 0)
            throw new NotFoundException(String.format("No Category found with id: %s.", id));
        Map<String, Object> return_map = new HashMap<>();
        return_map.put("id", list.get(0).get("__id"));
        return_map.put("name", list.get(0).get("name"));
        return_map.put("description", list.get(0).get("description"));
        return_map.put("tag", list.get(0).get("tag"));
        return_map.put("sub", list.get(0).get("sub"));
        return return_map;
    }

    public String categoryInsert(String did, Category category) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("name", category.getName());
        restrict.add(r1);
        List<DatabaseObject> list_name = DatabaseManager.retrieveWithRestriction("Category", restrict);
        restrict.clear();
        model.Restriction r3 = model.Restriction.equal("tag", category.getTag());
        restrict.add(r3);
        List<DatabaseObject> list_tag = DatabaseManager.retrieveWithRestriction("Category", restrict);
        if (category.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: \'id\'.");
        if (list_name.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'name\'.");
        if (list_tag.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'tag\'.");
        DatabaseObject c = DatabaseObject.initMethod("Category");
        c.putBoolean("sub", false);
        c.putString("name", category.getName());
        c.putString("description", category.getDescription());
        c.putString("tag", category.getTag());
        c.putInteger("id", category.getId());
        DatabaseManager.save(c);
        restrict.clear();
        model.Restriction isCorrectTag = model.Restriction.equal("tag", category.getTag());
        restrict.add(isCorrectTag);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Category", restrict);
        c = list.get(0);
        DatabaseObject d_c = DatabaseObject.initMethod("Department_Category");
        d_c.putString("cid", c.get("__id").toString());
        d_c.putString("did", did);
        DatabaseManager.save(d_c);
        return c.toString();
    }

    public String categoryUpdate(Category category) {
        List<model.Restriction> restrict = new ArrayList<>();
        List<model.Restriction> and_restrict_id = new ArrayList<>();
        List<model.Restriction> and_restrict_tag = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("name", category.getName());
        model.Restriction r2 = model.Restriction.equal("__id", category.getId().toString());
        model.Restriction not_r2 = model.Restriction.not(r2);
        and_restrict_id.add(r1);
        and_restrict_id.add(not_r2);
        model.Restriction and = model.Restriction.and(and_restrict_id);
        restrict.add(and);
        List<DatabaseObject> list_id = DatabaseManager.retrieveWithRestriction("Category", restrict);
        model.Restriction r3 = model.Restriction.equal("tag", category.getTag());
        model.Restriction r4 = model.Restriction.equal("__id", category.getId().toString());
        model.Restriction not_r4 = model.Restriction.not(r4);
        and_restrict_tag.add(r3);
        and_restrict_tag.add(not_r4);
        and = model.Restriction.and(and_restrict_tag);
        restrict.clear();
        restrict.add(and);
        List<DatabaseObject> list_tag = DatabaseManager.retrieveWithRestriction("Category", restrict);
        if (category.getId() == null)
            throw new UnprocessableEntityException("Should not contain database-generated field: \'id\'.");
        if (list_id.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'name\'.");
        if (list_tag.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'tag\'.");
        restrict.clear();
        model.Restriction r5 = model.Restriction.equal("__id", category.getId().toString());
        restrict.add(r5);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Category", restrict);
        DatabaseObject c = list.get(0);
        c.putString("name", category.getName());
        c.putString("description", category.getDescription());
        c.putString("tag", category.getTag());
        c.putInteger("id", category.getId());
        DatabaseManager.update(c);
        return c.toString();
    }

    public void categoryDelete(String id) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("cid", id);
        restrict.add(r1);
        List<DatabaseObject> list_Subcategory = DatabaseManager.retrieveWithRestriction("Category_Subcategory",
                restrict);
        model.Restriction r2 = model.Restriction.equal("cid", id);
        restrict.clear();
        restrict.add(r2);
        List<DatabaseObject> list_item = DatabaseManager.retrieveWithRestriction("Inventory", restrict);
        if ((list_Subcategory.size() > 0) || (list_item.size() > 0))
            throw new ConflictException("Cannot delete a category which is not empty.");
        restrict.clear();
        r1 = model.Restriction.equal("__id", id);
        restrict.add(r1);
        List<DatabaseObject> list_category = DatabaseManager.retrieveWithRestriction("Category", restrict);
        if (list_category.size() == 0) {
            System.out.println("no this category : " + id);
        } else {
            DatabaseObject c = list_category.get(0);
            restrict.clear();
            if ((Boolean) c.get("sub")) {
                r1 = model.Restriction.equal("scid", id);
                restrict.add(r1);
                DatabaseManager.deleteWithRestriction("Category_Subcategory", restrict);
            }
        }
        restrict.clear();
        model.Restriction r3 = model.Restriction.equal("cid", id);
        restrict.add(r3);
        DatabaseManager.deleteWithRestriction("Department_Category", restrict);
        restrict.clear();
        r1 = model.Restriction.equal("__id", id);
        restrict.add(r1);
        DatabaseManager.deleteWithRestriction("Category", restrict);
    }

    public List<Map<String, Object>> subcategoryList(String cid) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("cid", cid);
        restrict.add(r1);
        List<DatabaseObject> list_c2sc = DatabaseManager.retrieveWithRestriction("Category_Subcategory", restrict);
        for (DatabaseObject databaseObject : list_c2sc) {
            restrict.clear();
            model.Restriction r2 = model.Restriction.equal("__id", databaseObject.get("scid").toString());
            restrict.add(r2);
            List<DatabaseObject> list_Subcategory = DatabaseManager.retrieveWithRestriction("Category", restrict);
            if (list_Subcategory.size() == 0)
                continue;
            Map<String, Object> map = new HashMap<>();
            map.put("id", list_Subcategory.get(0).get("__id"));
            map.put("name", list_Subcategory.get(0).get("name"));
            list.add(map);
        }
        return list;
    }

    public String subcategoryInsert(String cid, Category subcategory) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r1 = model.Restriction.equal("name", subcategory.getName());
        restrict.add(r1);
        List<DatabaseObject> list_name = DatabaseManager.retrieveWithRestriction("Category", restrict);
        restrict.clear();
        model.Restriction r3 = model.Restriction.equal("tag", subcategory.getTag());
        restrict.add(r3);
        List<DatabaseObject> list_tag = DatabaseManager.retrieveWithRestriction("Category", restrict);
        if (subcategory.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: \'id\'.");
        if (list_name.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'name\'.");
        if (list_tag.size() > 0)
            throw new UnprocessableEntityException("Duplicated unique field: \'tag\'.");
        DatabaseObject c = DatabaseObject.initMethod("Category");
        c.putBoolean("sub", true);
        c.putString("name", subcategory.getName());
        c.putString("description", subcategory.getDescription());
        c.putString("tag", subcategory.getTag());
        c.putInteger("id", subcategory.getId());
        DatabaseManager.save(c);
        restrict.clear();
        model.Restriction isCorrectTag = model.Restriction.equal("tag", subcategory.getTag());
        restrict.add(isCorrectTag);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Category", restrict);
        c = list.get(0);
        DatabaseObject c_sc = DatabaseObject.initMethod("Category_Subcategory");
        c_sc.putString("cid", cid);
        c_sc.putString("scid", c.get("__id").toString());
        DatabaseManager.save(c_sc);
        return c.toString();
    }
}