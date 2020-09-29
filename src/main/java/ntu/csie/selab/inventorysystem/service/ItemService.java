package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.*;
import ntu.csie.selab.inventorysystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.DatabaseObject;
import manager.DatabaseManager;

import java.util.*;


@Service
public class ItemService {
    // @Autowired
    // ItemRepository itemRepository;
    // @Autowired
    // ItemConditionRepository itemConditionRepository;
    // @Autowired
    // InventoryRepository inventoryRepository;
    @Autowired
    ItemHistoryService itemHistoryService = new ItemHistoryService();
    // private static Map<String, ItemCondition> conditionMap = null;
    private static Map<String, String> conditionMap = null;

    // public List<Map<String, Object>> itemList() {
    //     List<Map<String, Object>> list = new ArrayList<>();
    //     for (Item item : itemRepository.findAll())
    //         list.add(itemReturnMap(inventoryRepository.findByItem(item)));
    //     return list;
    // }    

    public List<Map<String, Object>> itemList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (model.DatabaseObject item : manager.DatabaseManager.retrieveAll("Item")) {
            list.add(itemReturnMap(item));
        }
        return list;
    }

    // public List<Map<String, Object>> itemHierarchyLeafList(Integer did, Integer cid, Integer scid) {
    //     List<Map<String, Object>> list = new ArrayList<>();
    //     List<Inventory> query;
    //     if (scid == null && cid == null)
    //         query = inventoryRepository.findLeafByHierarchy(new Department(did));
    //     else if (scid == null)
    //         query = inventoryRepository.findLeafByHierarchy(new Department(did), new Category(cid));
    //     else
    //         query = inventoryRepository.findLeafByHierarchy(new Department(did), new Category(cid), new Category(scid));
    //     for (Inventory inventory : query)
    //         list.add(itemReturnMap(inventory));
    //     return list;
    // }

    public List<Map<String, Object>> itemHierarchyLeafList(Integer did, Integer cid, Integer scid) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (model.DatabaseObject inventory : manager.DatabaseManager.retrieveAll("Inventory")) {
            if((did == null ? "" : did.toString()).equals(inventory.get("did").toString()) && 
                (cid == null ? "" : cid.toString()).equals(inventory.get("cid").toString()) && 
                (scid == null ? "" : scid.toString()).equals(inventory.get("scid").toString()) 
            ){
                list.add(inventory.get("iid").toString());
            }
        }
        for(String iid : list){
            for (model.DatabaseObject item : manager.DatabaseManager.retrieveAll("Item")) {
                if(iid.equals(item.get("__id").toString()))
                    mapList.add(itemReturnMap(item));
            }
        }
        return mapList;
    }

    // public List<Map<String, Object>> itemHierarchySubtreeList(Integer did, Integer cid, Integer scid) {
    //     List<Map<String, Object>> list = new ArrayList<>();
    //     List<Inventory> query;
    //     if (scid == null && cid == null)
    //         query = inventoryRepository.findSubtreeByHierarchy(new Department(did));
    //     else if (scid == null)
    //         query = inventoryRepository.findSubtreeByHierarchy(new Department(did), new Category(cid));
    //     else
    //         query = inventoryRepository.findLeafByHierarchy(new Department(did), new Category(cid), new Category(scid));
    //     for (Inventory inventory : query)
    //         list.add(itemReturnMap(inventory));
    //     return list;
    // }

    public List<Map<String, Object>> itemHierarchySubtreeList(Integer did, Integer cid, Integer scid) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        if(cid == null){
            for (model.DatabaseObject inventory : manager.DatabaseManager.retrieveAll("Inventory")) {
                if(did.toString().equals(inventory.get("did").toString())){
                    list.add(inventory.get("iid").toString());
                }
            }
        }else if(scid == null){
            for (model.DatabaseObject inventory : manager.DatabaseManager.retrieveAll("Inventory")) {
                if(cid.toString().equals(inventory.get("cid").toString())){
                    list.add(inventory.get("iid").toString());
                }
            }
        }else{
            for (model.DatabaseObject inventory : manager.DatabaseManager.retrieveAll("Inventory")) {
                if(scid.toString().equals(inventory.get("scid").toString())){
                    list.add(inventory.get("iid").toString());
                }
            }
        }
        for(String iid : list){
            for (model.DatabaseObject item : manager.DatabaseManager.retrieveAll("Item")) {
                if(iid.toString().equals(item.get("__id").toString()))
                    mapList.add(itemReturnMap(item));
            }
        }
        return mapList;
    }

    // public Item itemById(Integer id) {
    //     Optional<Item> result = itemRepository.findById(id);
    //     if (!result.isPresent())
    //         throw new NotFoundException(String.format("No item found with id: %d.", id));
    //     return result.get();
    // }

    public Map<String, Object> itemById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        for (model.DatabaseObject item : manager.DatabaseManager.retrieveAll("Item")) {
            if(id.toString().equals(item.get("__id").toString())){
                map = itemReturnMap(item);
            }
        }
        if (map.size() == 0)
            throw new NotFoundException(String.format("No item found with id: %d.", id));
        return map;
    }

    // public Item itemInsert(Item.ItemValidation itemValidation, Integer uid) {
    //     readAllCondition();
    //     Item item = itemValidation.toItem();
    //     if (!conditionMap.containsKey(itemValidation.condition))
    //         throw new UnprocessableEntityException("Invalid value in field: 'condition'.");
    //     item.setCondition(conditionMap.get(itemValidation.condition));
    //     item = itemRepository.save(item);

    //     Inventory inventory = new Inventory();
    //     inventory.setItem(item);
    //     inventory.setDepartment(itemValidation.did == null ? null : new Department(itemValidation.did));
    //     inventory.setCategory(itemValidation.cid == null ? null : new Category(itemValidation.cid));
    //     inventory.setSubcategory(itemValidation.scid == null ? null : new Category(itemValidation.scid));
    //     inventoryRepository.save(inventory);

    //     itemHistoryService.itemHistoryAdd(item, new User(uid));
    //     if (item.getPrice() > 0)
    //         itemHistoryService.itemHistoryPriceSet(item, new User(uid));
    //     return item;
    // }

    public DatabaseObject itemInsert(Item.ItemValidation itemValidation, Integer uid) {
        readAllCondition();
        Item item = itemValidation.toItem();
        if (!conditionMap.containsKey(itemValidation.condition))
            throw new UnprocessableEntityException("Invalid value in field: 'condition'.");
        DatabaseObject itemDatabaseObject = DatabaseObject.initMethod("Item");
        itemDatabaseObject.putString("description", item.getDescription());
        itemDatabaseObject.putInteger("quantity", item.getQuantity());
        itemDatabaseObject.putDouble("price", item.getPrice());
        itemDatabaseObject.putInteger("condition", Integer.valueOf(conditionMap.get(itemValidation.condition)));
        itemDatabaseObject.putString("aid", item.getAcquisition() == null ? null : item.getAcquisition().getId().toString());
        DatabaseManager.save(itemDatabaseObject);

        DatabaseObject inventoryDatabaseObject = DatabaseObject.initMethod("Inventory");

        // for(DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("Item")){
        //     if(databaseObject.get("description").toString().equals(itemValidation.description)){
        //         itemDatabaseObject = databaseObject;
        //         break;
        //     }
        // }

        String iid = itemDatabaseObject.getDatabaseObject().getID().toString();
        
        inventoryDatabaseObject.putString("iid", iid);
        inventoryDatabaseObject.putString("did", itemValidation.did == null? "" : itemValidation.did.toString());
        inventoryDatabaseObject.putString("cid", itemValidation.cid == null? "" : itemValidation.cid.toString());
        inventoryDatabaseObject.putString("scid", itemValidation.scid == null? "" : itemValidation.scid.toString());

        DatabaseManager.save(inventoryDatabaseObject);

        itemHistoryService.itemHistoryAdd(iid, new User(uid));
        if (item.getPrice() > 0)
            itemHistoryService.itemHistoryPriceSet(iid, new User(uid));

        return inventoryDatabaseObject;
    }

    // public Item itemUpdate(Item.ItemValidation itemValidation, Integer id, Integer uid) {
    //     readAllCondition();
    //     Item origin = itemById(id);
    //     Item item = itemValidation.toItem();
    //     item.setId(id);
    //     if (!conditionMap.containsKey(itemValidation.condition))
    //         throw new UnprocessableEntityException("Invalid value in field: 'condition'.");
    //     item.setCondition(conditionMap.get(itemValidation.condition));
    //     if (!origin.getQuantity().equals(item.getQuantity()))
    //         itemHistoryService.itemHistoryAdjust(item, new User(uid),
    //                 item.getQuantity() - origin.getQuantity(), itemValidation.reason, itemValidation.comment);
    //     if (!origin.getPrice().equals(item.getPrice()))
    //         itemHistoryService.itemHistoryPriceChanged(item, new User(uid));
    //     itemRepository.save(item);
    //     return item;
    // }

    public DatabaseObject itemUpdate(Item.ItemValidation itemValidation, Integer id, Integer uid) {
        readAllCondition();

        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction r = model.Restriction.equal("__id", id.toString());
        restrict.add(r);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Item",restrict);
        DatabaseObject itemDatabaseObject = list.get(0);

        Item item = itemValidation.toItem();
        itemDatabaseObject.putString("description",item.getDescription());
        if (!conditionMap.containsKey(itemValidation.condition))
            throw new UnprocessableEntityException("Invalid value in field: 'condition'.");
        itemDatabaseObject.putInteger("condition", Integer.valueOf(conditionMap.get(itemValidation.condition)));
        int originQuantity = (int)itemDatabaseObject.get("quantity");
        if (!item.getQuantity().equals(originQuantity)){
            itemDatabaseObject.putInteger("quantity", item.getQuantity());
            itemHistoryService.itemHistoryAdjust(id.toString(), new User(uid),
                    item.getQuantity() - originQuantity, itemValidation.reason, itemValidation.comment);
        }
        Double originPrice = (Double)itemDatabaseObject.get("price");
        if (!item.getPrice().equals(originPrice)){
            itemDatabaseObject.putDouble("price", item.getPrice());
            itemHistoryService.itemHistoryPriceChanged(id.toString(), new User(uid), item.getPrice());
        }
        DatabaseManager.update(itemDatabaseObject);
        return itemDatabaseObject;
    }

    public Map<String, Object> itemReturnMap(model.DatabaseObject itemDatabaseObject){
        Map<String, Object> map = new HashMap<>();
        model.DatabaseObject inventoryDatabaseObject = model.DatabaseObject.initMethod("");
        for(model.DatabaseObject databaseObject : DatabaseManager.retrieveAll("Inventory")){
            if(itemDatabaseObject.get("__id").toString().equals(databaseObject.get("iid").toString())){
                inventoryDatabaseObject = databaseObject;
                break;
            }
        }
        map.put("id", itemDatabaseObject.get("__id"));
        map.put("description", itemDatabaseObject.get("description"));
        map.put("quantity", itemDatabaseObject.get("quantity"));
        map.put("price", itemDatabaseObject.get("price"));
        map.put("condition", itemDatabaseObject.get("condition"));

	Object department = null;
        for(model.DatabaseObject databaseObject : DatabaseManager.retrieveAll("Department")){
            if(inventoryDatabaseObject.get("did").toString().equals(databaseObject.get("__id").toString())){
                department = databaseObject.get("name");
            }
        }
        map.put("department", department == null? "" : department.toString());

        Object category = null;
        Object subcategory = null;
        for(model.DatabaseObject databaseObject : DatabaseManager.retrieveAll("Category")){
            if(inventoryDatabaseObject.get("cid").toString().equals(databaseObject.get("__id").toString())){
                category = databaseObject.get("name");
            }
            if(inventoryDatabaseObject.get("scid").toString().equals(databaseObject.get("__id").toString())){
                subcategory = databaseObject.get("name");
            }
        }
        map.put("category", String.format("%s%s",
            category == null? "" : category.toString(),
            subcategory == null? "" : ", " + subcategory.toString()));
        Object donor = null;
        for(model.DatabaseObject databaseObject : DatabaseManager.retrieveAll("Acquisition")){
            String aid = itemDatabaseObject.get("aid")==null? "" : itemDatabaseObject.get("aid").toString();
            if(aid.equals(databaseObject.get("__id").toString())){
                donor = databaseObject.get("donor");
            }
        }
        map.put("donor", donor == null? "" : donor.toString());
        return map;
    }

    // private Map<String, Object> itemReturnMap(Inventory inventory) {
    //     Map<String, Object> map = new HashMap<>();
    //     map.put("id", inventory.getItem().getId());
    //     map.put("description", inventory.getItem().getDescription());
    //     map.put("quantity", inventory.getItem().getQuantity());
    //     map.put("price", inventory.getItem().getPrice());
    //     map.put("condition", inventory.getItem().getCondition().toString());
    //     map.put("department", inventory.getDepartment().getName());
    //     map.put("category", String.format("%s%s",
    //             inventory.getCategory() == null ? "" : inventory.getCategory().getName(),
    //             inventory.getSubcategory() == null ? "" : ", " + inventory.getSubcategory().getName()));
    //     map.put("donor", inventory.getItem().getAcquisition() == null ? "" : inventory.getItem().getAcquisition().getDonor());
    //     return map;
    // }

    // private void readAllCondition() {
    //     if (conditionMap != null)
    //         return;
    //     conditionMap = new HashMap<>();
    //     for (ItemCondition condition : itemConditionRepository.findAll())
    //         conditionMap.put(condition.getCondition(), condition);
    // }

    private void readAllCondition() {
        if (conditionMap != null)
            return;
        conditionMap = new HashMap<>();
        for(model.DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("ItemCondition")){
            conditionMap.put(databaseObject.get("condition").toString(), databaseObject.get("__id").toString());
        }
    }
}
