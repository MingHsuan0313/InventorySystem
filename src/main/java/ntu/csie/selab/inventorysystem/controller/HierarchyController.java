package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.service.HierarchyService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hierarchy")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class HierarchyController {
    // @Autowired
    HierarchyService hierarchyService = new HierarchyService();
    // @Autowired
    UserService userService = new UserService();

    // API: View Department List
    @GetMapping(value = "/departments", produces = "application/json")
    public List<Map<String, Object>> viewDepartmentList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        // userService.isLogin(new Integer(uid), token);
        return hierarchyService.departmentList();
    }
    // API: View Department
    @GetMapping(value = "/departments/{id}", produces = "application/json")
    public Map<String, Object> viewDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String did
    ) {
        // userService.isLogin(new Integer(uid), token);
        return hierarchyService.departmentById(did);
    }

    // API: Add Department
    @PostMapping(value = "/departments", produces = "application/json")
    public String addDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @Valid @RequestBody Department department,
            BindingResult validation
    ) {
        // userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return hierarchyService.departmentInsert(department);
    }
    /*@PostMapping(value = "/departments2", produces = "application/json")
    public String addDepartment2(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @Valid @RequestBody Department department,
            BindingResult validation
    ) {

        userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return hierarchyService.departmentInsert(department);
    }*/


    // API: Edit Department
    @PatchMapping(value = "/departments/{id}", produces = "application/json")
    public String editDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer did,
            @Valid @RequestBody Department department,
            BindingResult validation
    ) {
        // userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        department.setId(did);
        return hierarchyService.departmentUpdate(department);
    }

    // API: Delete Department
    @DeleteMapping(value = "/departments/{id}", produces = "application/json")
    public void deleteDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String did
    ) {
        // userService.isLogin(new Integer(uid), token);
        hierarchyService.departmentDelete(did);
    }

    // API: View Category List
    @GetMapping(value = "/departments/{id}/categories", produces = "application/json")
    public List<Map<String, Object>> viewCategoryList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String did
    ) {
        //userService.isLogin(new Integer(uid), token);
        return hierarchyService.categoryList(did);
    }

    // API: View Category
    @GetMapping(value = "/categories/{id}", produces = "application/json")
    public Map<String, Object> viewCategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String cid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return hierarchyService.categoryById(cid);
    }

    // API: Add Category
    @PostMapping(value = "/departments/{id}/categories", produces = "application/json")
    public String addCategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String did,
            @Valid @RequestBody Category category,
            BindingResult validation
    ) {
        //userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return hierarchyService.categoryInsert(did, category);
    }

    // API: Edit Category
    @PatchMapping(value = "/categories/{id}", produces = "application/json")
    public String editCategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer cid,
            @Valid @RequestBody Category category,
            BindingResult validation
    ) {
        //userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        category.setId(cid);
        return hierarchyService.categoryUpdate(category);
    }

    // API: Delete Category
    @DeleteMapping(value = "/categories/{id}", produces = "application/json")
    public void deleteCategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String cid
    ) {
        //userService.isLogin(new Integer(uid), token);
        hierarchyService.categoryDelete(cid);
    }

    // API: View Subcategory List
    @GetMapping(value = "/categories/{id}/subcategories", produces = "application/json")
    public List<Map<String, Object>> viewSubcategoryList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String cid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return hierarchyService.subcategoryList(cid);
    }

    // API: Add Subcategory
    @PostMapping(value = "/categories/{id}/subcategories", produces = "application/json")
    public String addSubcategory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") String cid,
            @Valid @RequestBody Category category,
            BindingResult validation
    ) {
        //userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return hierarchyService.subcategoryInsert(cid, category);
    }
}
