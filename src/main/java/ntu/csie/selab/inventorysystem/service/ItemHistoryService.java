package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.*;
import ntu.csie.selab.inventorysystem.repository.ItemHistoryAdjustReasonRepository;
import ntu.csie.selab.inventorysystem.repository.ItemHistoryEventRepository;
import ntu.csie.selab.inventorysystem.repository.ItemHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import model.DatabaseObject;
import manager.DatabaseManager;

@Service
public class ItemHistoryService {
    // @Autowired ItemHistoryRepository itemHistoryRepository;
    // @Autowired ItemHistoryAdjustReasonRepository itemHistoryAdjustReasonRepository;
    // @Autowired ItemHistoryEventRepository itemHistoryEventRepository;
    // private static Map<String, ItemHistoryEvent> eventMap = null;
    // private static Map<String, ItemHistoryAdjustReason> reasonMap = null;
    private static Map<String, String> eventMap = null;
    private static Map<String, String> reasonMap = null;

    // private void readAllEvent() {
    //     if (eventMap != null)
    //         return;
    //     eventMap = new HashMap<>();
    //     for (ItemHistoryEvent event : itemHistoryEventRepository.findAll())
    //         eventMap.put(event.getEvent(), event);
    // }

    private void readAllEvent() {
        if (eventMap != null)
            return;
        eventMap = new HashMap<>();
        for(model.DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("ItemHistoryEvent")){
            eventMap.put(databaseObject.get("event").toString(), databaseObject.get("__id").toString());
        }
    }

    // private void readAllReason() {
    //     if (reasonMap != null)
    //         return;
    //     reasonMap = new HashMap<>();
    //     for (ItemHistoryAdjustReason reason : itemHistoryAdjustReasonRepository.findAll())
    //         reasonMap.put(reason.getReason(), reason);
    // }

    private void readAllReason() {
        if (reasonMap != null)
            return;
        reasonMap = new HashMap<>();
        for(model.DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("ItemHistoryAdjustReason")){
            reasonMap.put(databaseObject.get("reason").toString(), databaseObject.get("__id").toString());
        }
    }

    // public void itemHistoryAdd(Item item, User user) {
    //     readAllEvent();
    //     readAllReason();
    //     ItemHistory itemHistory = new ItemHistory(item, user);
    //     itemHistory.setEvent(eventMap.get("Add to inventory"));
    //     itemHistory.setAdjust(item.getQuantity());
    //     itemHistoryRepository.save(itemHistory);
    // }

    public void itemHistoryAdd(String iid, User user) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(null, user);
        DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
        itemHistoryDatabaseObject.putString("iid", iid);
        itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
        itemHistoryDatabaseObject.putString("event", eventMap.get("Add to inventory"));
        int quantity = 0;
        for(DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("Item")){
            if(databaseObject.get("__id").toString().equals(iid)){
                quantity = (int) databaseObject.get("quantity");
            }
        }
        itemHistory.setAdjust(quantity);
        // itemHistoryDatabaseObject.putDouble("price", null);
        itemHistoryDatabaseObject.putInteger("adjust", itemHistory.getAdjust());
        itemHistoryDatabaseObject.putString("reason", "");
        itemHistoryDatabaseObject.putString("uid", user.getId().toString());
        itemHistoryDatabaseObject.putString("comment", "");

        manager.DatabaseManager.save(itemHistoryDatabaseObject);
    }

    // public void itemHistoryPriceSet(Item item, User user) {
    //     readAllEvent();
    //     readAllReason();
    //     ItemHistory itemHistory = new ItemHistory(item, user);
    //     itemHistory.setEvent(eventMap.get("Price set"));
    //     itemHistory.setPrice(item.getPrice());
    //     itemHistoryRepository.save(itemHistory);
    // }
    
    public void itemHistoryPriceSet(String iid, User user) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(null, user);
        DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
        itemHistoryDatabaseObject.putString("iid", iid);
        itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
        itemHistoryDatabaseObject.putString("event", eventMap.get("Price set"));
        Double price = 0.0;
        for(DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("Item")){
            if(databaseObject.get("__id").toString().equals(iid)){
                price = (Double)databaseObject.get("price");
            }
        }
        itemHistoryDatabaseObject.putDouble("price", price);
        itemHistoryDatabaseObject.putInteger("adjust", 0);
        itemHistoryDatabaseObject.putString("reason", "");
        itemHistoryDatabaseObject.putString("uid", user.getId().toString());
        itemHistoryDatabaseObject.putString("comment", "");

        manager.DatabaseManager.save(itemHistoryDatabaseObject);
    }

    // public void itemHistoryPriceChanged(Item item, User user) {
    //     readAllEvent();
    //     readAllReason();
    //     ItemHistory itemHistory = new ItemHistory(item, user);
    //     itemHistory.setEvent(eventMap.get("Price changed"));
    //     itemHistory.setPrice(item.getPrice());
    //     itemHistoryRepository.save(itemHistory);
    // }

    public void itemHistoryPriceChanged(String iid, User user, Double price) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(null, user);
        DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
        itemHistoryDatabaseObject.putString("iid", iid);
        itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
        itemHistoryDatabaseObject.putString("event", eventMap.get("Price changed"));
        itemHistoryDatabaseObject.putDouble("price", price);
        itemHistoryDatabaseObject.putInteger("adjust", 0);
        itemHistoryDatabaseObject.putString("reason", "");
        itemHistoryDatabaseObject.putString("uid", user.getId().toString());
        itemHistoryDatabaseObject.putString("comment", "");

        manager.DatabaseManager.save(itemHistoryDatabaseObject);
    }

    // public void itemHistorySplit(Item item, User user, Integer split) {
    //     readAllEvent();
    //     readAllReason();
    //     ItemHistory itemHistory = new ItemHistory(item, user);
    //     itemHistory.setEvent(eventMap.get("Item split"));
    //     itemHistory.setAdjust(split);
    //     itemHistoryRepository.save(itemHistory);
    // }

    public void itemHistorySplit(String iid, User user, Integer split) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(null, user);
        itemHistory.setAdjust(split);
        DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
        itemHistoryDatabaseObject.putString("iid", iid);
        itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
        itemHistoryDatabaseObject.putString("event", eventMap.get("Item split"));
        // itemHistoryDatabaseObject.putDouble("price", "");
        itemHistoryDatabaseObject.putInteger("adjust", itemHistory.getAdjust());
        itemHistoryDatabaseObject.putString("reason", "");
        itemHistoryDatabaseObject.putString("uid", user.getId().toString());
        itemHistoryDatabaseObject.putString("comment", "");

        manager.DatabaseManager.save(itemHistoryDatabaseObject);
    }

    // public void itemHistorySold(Item item, Integer sold, Double price) {
    //     readAllEvent();
    //     readAllReason();
    //     ItemHistory itemHistory = new ItemHistory(item, null);
    //     itemHistory.setEvent(eventMap.get("Item sold"));
    //     itemHistory.setAdjust(sold);
    //     itemHistory.setPrice(price);
    //     itemHistoryRepository.save(itemHistory);
    // }

    public void itemHistorySold(String iid, Integer sold, Double price) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(null, null);
        itemHistory.setAdjust(sold);
        itemHistory.setPrice(price);
        DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
        itemHistoryDatabaseObject.putString("iid", iid);
        itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
        itemHistoryDatabaseObject.putString("event", eventMap.get("Item sold"));
        itemHistoryDatabaseObject.putDouble("price", price);
        itemHistoryDatabaseObject.putInteger("adjust", itemHistory.getAdjust());
        itemHistoryDatabaseObject.putString("reason", "");
        itemHistoryDatabaseObject.putString("uid", "");
        itemHistoryDatabaseObject.putString("comment", "");

        manager.DatabaseManager.save(itemHistoryDatabaseObject);
    }

    // public void itemHistoryAdjust(Item item, User user, Integer adjust, String reason, String comment) {
    //     readAllEvent();
    //     readAllReason();
    //     ItemHistory itemHistory = new ItemHistory(item, user);
    //     itemHistory.setEvent(eventMap.get("Quantity adjusted"));
    //     itemHistory.setAdjust(adjust);
    //     if (!reasonMap.containsKey(reason))
    //         throw new UnprocessableEntityException("Invalid value in field: 'reason'.");
    //     itemHistory.setReason(reasonMap.get(reason));
    //     itemHistory.setComment(comment);
    //     itemHistoryRepository.save(itemHistory);
    // }

    public void itemHistoryAdjust(String iid, User user, Integer adjust, String reason, String comment) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(null, user);
        itemHistory.setAdjust(adjust);
        if (!reasonMap.containsKey(reason))
            throw new UnprocessableEntityException("Invalid value in field: 'reason'.");
        itemHistory.setComment(comment);
        DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
        itemHistoryDatabaseObject.putString("iid", iid);
        itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
        itemHistoryDatabaseObject.putString("event", eventMap.get("Quantity adjusted"));
        // itemHistoryDatabaseObject.putDouble("price", "");
        itemHistoryDatabaseObject.putInteger("adjust", itemHistory.getAdjust());
        itemHistoryDatabaseObject.putString("reason", reason);
        itemHistoryDatabaseObject.putString("uid", user.getId().toString());
        itemHistoryDatabaseObject.putString("comment", itemHistory.getComment());

        manager.DatabaseManager.save(itemHistoryDatabaseObject);
    }
}
