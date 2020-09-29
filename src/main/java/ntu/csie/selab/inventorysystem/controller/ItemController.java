package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.model.ItemHistory;
import ntu.csie.selab.inventorysystem.service.ItemService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import model.DatabaseObject;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ItemController {
    // @Autowired
    ItemService itemService = new ItemService();
    // @Autowired
    UserService userService = new UserService();

    // get all items
    @GetMapping(value = "/", produces = "application/json")
    public List<Map<String, Object>> viewItemList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        // userService.isLogin(new Integer(uid), token);
        return itemService.itemList();
    }

    // get leaf items under specific department
    @GetMapping(value = "/hierarchy/leaf/{did}", produces = "application/json")
    public List<Map<String, Object>> viewItemHierarchyLeafList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "did") Integer did
    ) {
        //userService.isLogin(new Integer(uid), token);
        return itemService.itemHierarchyLeafList(did, null, null);
    }

    // get subtree items under specific department
    @GetMapping(value = "/hierarchy/subtree/{did}", produces = "application/json")
    public List<Map<String, Object>> viewItemHierarchySubtreeList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "did") Integer did
    ) {
        //userService.isLogin(new Integer(uid), token);
        return itemService.itemHierarchySubtreeList(did, null, null);
    }

    // get leaf items under specific category
    @GetMapping(value = "/hierarchy/leaf/{did}/{cid}", produces = "application/json")
    public List<Map<String, Object>> viewItemHierarchyLeafList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "did") Integer did,
            @PathVariable(name = "cid") Integer cid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return itemService.itemHierarchyLeafList(did, cid, null);
    }

    // get subtree items under specific category
    @GetMapping(value = "/hierarchy/subtree/{did}/{cid}", produces = "application/json")
    public List<Map<String, Object>> viewItemHierarchySubtreeList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "did") Integer did,
            @PathVariable(name = "cid") Integer cid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return itemService.itemHierarchySubtreeList(did, cid, null);
    }

    // get leaf items under specific subcategory
    @GetMapping(value = "/hierarchy/leaf/{did}/{cid}/{scid}", produces = "application/json")
    public List<Map<String, Object>> viewItemHierarchyLeafList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "did") Integer did,
            @PathVariable(name = "cid") Integer cid,
            @PathVariable(name = "scid") Integer scid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return itemService.itemHierarchyLeafList(did, cid, scid);
    }

    // get subtree items under specific subcategory
    @GetMapping(value = "/hierarchy/subtree/{did}/{cid}/{scid}", produces = "application/json")
    public List<Map<String, Object>> viewItemHierarchySubtreeList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "did") Integer did,
            @PathVariable(name = "cid") Integer cid,
            @PathVariable(name = "scid") Integer scid
    ) {
        //userService.isLogin(new Integer(uid), token);
        return itemService.itemHierarchySubtreeList(did, cid, scid);
    }

    // @GetMapping(value = "/{id}", produces = "application/json")
    // public Item viewItem(
    //         @CookieValue(name = "uid") String uid,
    //         @CookieValue(name = "token") String token,
    //         @PathVariable(name = "id") Integer iid
    // ) {
    //     userService.isLogin(new Integer(uid), token);
    //     return itemService.itemById(iid);
    // }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Map<String, Object> viewItem(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer iid
    ) {
        // userService.isLogin(new Integer(uid), token);
        return itemService.itemById(iid);
    }

    @PostMapping(value = "/", produces = "application/json")
    public DatabaseObject addItem(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @Valid @RequestBody Item.ItemValidation item,
            BindingResult validation
    ) {
        // userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return itemService.itemInsert(item, new Integer(uid));
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public DatabaseObject editItem(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer iid,
            @Valid @RequestBody Item.ItemValidation item,
            BindingResult validation
    ) {
        // userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return itemService.itemUpdate(item, iid, new Integer(uid));
    }
/*
    @GetMapping(value = "/history/{id}", produces = "application/json")
    public List<ItemHistory> viewItemHistory(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer iid
    ) {
        userService.isLogin(new Integer(uid), token);
        return itemService.historyByItem(iid);
    }

 */
}
