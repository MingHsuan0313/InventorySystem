package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.AcquisitionStatus;
import ntu.csie.selab.inventorysystem.model.AcquisitionType;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.repository.AcquisitionRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionStatusRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionTypeRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.DatabaseObject;
import manager.DatabaseManager;

import java.util.*;

@Service
public class AcquisitionService {
    // @Autowired
    // AcquisitionRepository acquisitionRepository;
    // @Autowired
    // AcquisitionTypeRepository acquisitionTypeRepository;
    // @Autowired
    // AcquisitionStatusRepository acquisitionStatusRepository;
    // private static Map<String, AcquisitionType> typeMap = null;
    // private static Map<String, AcquisitionStatus> statusMap = null;
    private static Map<String, String> typeMap = null;
    private static Map<String, String> statusMap = null;
    private ItemService itemService = new ItemService();

    // public Acquisition newAcquisition(Acquisition.AcquisitionValidation acquisitionValidation) {
    //     readAllTypes();
    //     readAllStatus();
    //     Acquisition acquisition = new Acquisition();
    //     if (!typeMap.containsKey(acquisitionValidation.type))
    //         throw new UnprocessableEntityException("Invalid value in field: 'type'.");
    //     acquisition.setType(typeMap.get(acquisitionValidation.type));
    //     acquisition.setDonor(acquisitionValidation.donor);
    //     acquisition.setContact(acquisitionValidation.contact);
    //     acquisition.setPhone(acquisitionValidation.phone);
    //     acquisition.setDate(acquisitionValidation.date);
    //     acquisition.setStatus(statusMap.get("Expected"));
    //     return acquisitionRepository.save(acquisition);
    // }

    public DatabaseObject newAcquisition(Acquisition.AcquisitionValidation acquisitionValidation) {
        readAllTypes();
        readAllStatus();

        if (!typeMap.containsKey(acquisitionValidation.type))
            throw new UnprocessableEntityException("Invalid value in field: 'type'.");

        DatabaseObject databaseObject = DatabaseObject.initMethod("Acquisition");
        databaseObject.putString("type", typeMap.get(acquisitionValidation.type));
        databaseObject.putString("donor", acquisitionValidation.donor);
        databaseObject.putString("contact", acquisitionValidation.contact);
        databaseObject.putString("phone", acquisitionValidation.phone);
        databaseObject.putDate("date", acquisitionValidation.date);
        databaseObject.putString("status", statusMap.get("Expected"));
        DatabaseManager.save(databaseObject);
        return databaseObject;
    }

    // public List<Acquisition> acquisitionList() {
    //     List<Acquisition> list = new ArrayList<>();
    //     for (Acquisition acquisition : acquisitionRepository.findAll())
    //         list.add(acquisition);
    //     return list;
    // }

    public List<Map<String, Object>> acquisitionList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (model.DatabaseObject acquisition : manager.DatabaseManager.retrieveAll("Acquisition")) {
            list.add(acquisitionReturnMap(acquisition));
        }
        return list;
    }

    private Map<String, Object> acquisitionReturnMap(model.DatabaseObject acquisitionDatabaseObject){
        Map<String, Object> map = new HashMap<>();
        map.put("id", acquisitionDatabaseObject.get("__id"));
        map.put("type", acquisitionDatabaseObject.get("type"));
        map.put("donor", acquisitionDatabaseObject.get("donor"));
        map.put("contact", acquisitionDatabaseObject.get("contact"));
        map.put("phone", acquisitionDatabaseObject.get("phone"));
        map.put("date", acquisitionDatabaseObject.get("date"));
        map.put("status", acquisitionDatabaseObject.get("status"));
        return map;
    }

    // public Acquisition acquisitionById(Integer id) {
    //     Optional<Acquisition> result = acquisitionRepository.findById(id);
    //     if (!result.isPresent())
    //         throw new NotFoundException(String.format("No acquisition found with id: %d.", id));
    //     return result.get();
    // }

    public Map<String, Object> acquisitionById(Integer aid) {
        Map<String, Object> map = new HashMap<>();
        for(model.DatabaseObject databaseObject : DatabaseManager.retrieveAll("Acquisition")){
            if(aid.toString().equals(databaseObject.get("__id").toString())){
                return acquisitionReturnMap(databaseObject);
            }
        }
        if (map.size() == 0)
            throw new NotFoundException(String.format("No acquisition found with id: %d.", aid));
        return map;
    }

    // public String getAcquisitionItemsById(Integer id) throws JSONException {

    //     List<Item> list = acquisitionRepository.getItemIdWithAcqusition(id);
    //     /*System.out.println(list.size());
    //     for(int j=0;j<list.size();j++){
    //         System.out.println(list.get(j));
    //     }*/
    //     JSONArray jsonA = new JSONArray();
    //     for(int i=0;i<list.size();i++){
    //         JSONObject jsonO = new JSONObject();
    //         jsonO.put("id",list.get(i).getId());
    //         jsonO.put("description",list.get(i).getDescription());
    //         jsonO.put("quantity",list.get(i).getQuantity());
    //         jsonO.put("price",list.get(i).getPrice());
    //         jsonO.put("condition",list.get(i).getCondition());
    //         jsonO.put("aid",id);
    //         jsonA.put(i,jsonO);
    //     }
    //     /*System.out.println(jsonA.length());
    //     System.out.println(jsonA);*/

    //     return jsonA.toString();
    // }

    public List<Map<String, Object>> getAcquisitionItemsById(Integer id) {

        List<Map<String, Object>> list = new ArrayList<>();
        for(model.DatabaseObject databaseObject : DatabaseManager.retrieveAll("Item")){
	    String itemAid = databaseObject.get("aid") == null? "" : databaseObject.get("aid").toString();
            if(id.toString().equals(itemAid)){
                list.add(itemService.itemReturnMap(databaseObject));
            }
        }
        return list;
    }

    // private void readAllTypes() {
    //     if (typeMap != null)
    //         return;
    //     typeMap = new HashMap<>();
    //     for (AcquisitionType type : acquisitionTypeRepository.findAll())
    //         typeMap.put(type.getType(), type);
    // }

    private void readAllTypes() {
        if (typeMap != null)
            return;
        typeMap = new HashMap<>();
        for(model.DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("AcquisitionType")){
            typeMap.put(databaseObject.get("type").toString(), databaseObject.get("__id").toString());
        }
    }

    // private void readAllStatus() {
    //     if (statusMap != null)
    //         return;
    //     statusMap = new HashMap<>();
    //     for (AcquisitionStatus status : acquisitionStatusRepository.findAll())
    //         statusMap.put(status.getStatus(), status);
    // }

    private void readAllStatus() {
        if (statusMap != null)
            return;
        statusMap = new HashMap<>();
        for(model.DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("AcquisitionStatus")){
            statusMap.put(databaseObject.get("status").toString(), databaseObject.get("__id").toString());
        }
    }
}
