package ntu.csie.selab.inventorysystem.service;

//import database.hibernate.HibernateManager;
import database.model.restriction_component.composite.And;
import database.model.restriction_component.single.Equal;
import manager.DatabaseManager;
import database.hibernate.HibernateManager;
import model.DatabaseObject;
//import database.DatabaseManager;
//import model.TableObject;
import database.model.TableObject;
import database.model.restriction_component.*;

import java.sql.Timestamp;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnauthorizedException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.*;
import ntu.csie.selab.inventorysystem.repository.*;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TryService {
    //@Autowired
    //UserRepository userRepository;
    //@Autowired
    //TokenRepository tokenRepository;

    public String createTable(String tableName){

        //t.setSchema("id","int");
        if(tableName.equals("Department")){
            TableObject t = TableObject.newInstance(tableName);
            t.setSchema("name","string");
            t.setSchema("description","string");
            t.setSchema("POSCode","string");
            t.setSchema("tag","string");

            DatabaseManager.createTable(model.TableObject.toTableObject(t));
        }
        else if(tableName.equals("Category")){
            TableObject t = TableObject.newInstance(tableName);
            t.setSchema("name","string");
            t.setSchema("description","string");
            t.setSchema("tag","string");
            t.setSchema("sub","boolean");
            DatabaseManager.createTable(model.TableObject.toTableObject(t));
        }
        else if(tableName.equals("User")){
            TableObject t = TableObject.newInstance(tableName);
            t.setSchema("username","string");
            t.setSchema("password","string");
            t.setSchema("priviledge","int");
            t.setSchema("token","List", "Token");
            DatabaseManager.createTable(model.TableObject.toTableObject(t));
        }
        else if(tableName.equals("Token")){
            TableObject t = TableObject.newInstance(tableName);
            t.setSchema("uid","int");
            t.setSchema("token","string");
            t.setSchema("expire","Date");
            t.setSchema("user","User");
            DatabaseManager.createTable(model.TableObject.toTableObject(t));
        }
        else if(tableName.equals("User_Token")){
            TableObject t = TableObject.newInstance("User");

            t.setSchema("username","string");
            t.setSchema("password","string");
            t.setSchema("privilege","int");
            t.setSchema("token","List", "Token");

            TableObject t2 = TableObject.newInstance("Token");
            t2.setSchema("uid","string");
            t2.setSchema("token","string");
            t2.setSchema("expire","Date");
            t2.setSchema("user","User");
            //t2.setSchema("uid","User");
            //System.out.println("QWQWQWQWQ          "+model.TableObject.toTableObject(t2).printSchema());

            List<TableObject> tableObjects = new ArrayList<>();

            tableObjects.add(t2);
            tableObjects.add(t);
            DatabaseManager.createTables(tableObjects);

        }
        else if(tableName.equals("Bad_QQ")){
            TableObject t = TableObject.newInstance(tableName);
            t.setSchema("name","string");
            t.setSchema("sub","boolean");
            DatabaseManager.createTable(model.TableObject.toTableObject(t));
        }
        else if(tableName.equals("All_table")){
            TableObject t = TableObject.newInstance("Department");

            t.setSchema("name","string");
            t.setSchema("description","string");
            t.setSchema("POSCode","string");
            t.setSchema("tag","string");

            TableObject t2 = TableObject.newInstance("Category");
            t2.setSchema("name","string");
            t2.setSchema("description","string");
            t2.setSchema("tag","string");
            t2.setSchema("sub","boolean");

            TableObject t3 = TableObject.newInstance("Department_Category");
            t3.setSchema("did","string");
            t3.setSchema("cid","string");

            TableObject t4 = TableObject.newInstance("Category_Subcategory");

            t4.setSchema("cid","string");
            t4.setSchema("scid","string");

            TableObject t5 = TableObject.newInstance("Inventory");
            t5.setSchema("iid","string");
            t5.setSchema("scid","string");
            t5.setSchema("did","string");
            t5.setSchema("cid","string");

            TableObject t6 = TableObject.newInstance("Item");
            t6.setSchema("description","string");
            t6.setSchema("quantity","int");
            t6.setSchema("condition","int");
            t6.setSchema("price","double");
            t6.setSchema("aid","string");
            //t6.setSchema("acquisition","Acquisition");
            //t6.setSchema("itemHistory","List","ItemHistory");

            TableObject t7 = TableObject.newInstance("Acquisition");
            t7.setSchema("type","string");
            t7.setSchema("donor","string");
            t7.setSchema("contact","string");
            t7.setSchema("phone","string");
            t7.setSchema("date","Date");
            t7.setSchema("status","string");
            //t7.setSchema("acquisitionStatus","AcquisitionStatus");
            //t7.setSchema("acquisitionType","AcquisitionType");
            //t7.setSchema("item","List","Item");


            TableObject t8 = TableObject.newInstance("AcquisitionStatus");
            //t8.setSchema("acquisition","List","Acquisition");
            t8.setSchema("status","string");

            TableObject t9 = TableObject.newInstance("AcquisitionType");
            //t9.setSchema("acquisition","List","Acquisition");
            t9.setSchema("type","string");

            TableObject t10 = TableObject.newInstance("ItemHistory");
            t10.setSchema("iid","string");
            t10.setSchema("date","Date");
            t10.setSchema("event","string");
            t10.setSchema("price","double");
            t10.setSchema("adjust","int");

            t10.setSchema("reason","string");
            t10.setSchema("uid","string");
            t10.setSchema("command","string");

            //t10.setSchema("itemHistoryAdjustReason","ItemHistoryAdjustReason");
            //t10.setSchema("itemHistoryEvent","ItemHistoryEvent");
            //t10.setSchema("itemCondition","ItemCondition");
            //t10.setSchema("item","Item");



            TableObject t11 = TableObject.newInstance("ItemCondition");
            //t11.setSchema("itemHistory","List","ItemHistory");
            t11.setSchema("condition","string");

            TableObject t12 = TableObject.newInstance("ItemHistoryEvent");
            //t12.setSchema("itemHistory","List","ItemHistory");
            t12.setSchema("event","string");


            TableObject t13 = TableObject.newInstance("ItemHistoryAdjustReason");
            //t13.setSchema("itemHistory","List","ItemHistory");
            t13.setSchema("reason","string");


            TableObject t14 = TableObject.newInstance("User");
            t14.setSchema("username","string");
            t14.setSchema("password","string");
            t14.setSchema("privilege","int");
            t14.setSchema("token","List", "Token");

            TableObject t15 = TableObject.newInstance("Token");
            t15.setSchema("uid","string");
            t15.setSchema("token","string");
            t15.setSchema("expire","Date");
            t15.setSchema("user","User");
            //t2.setSchema("uid","User");
            //System.out.println("QWQWQWQWQ          "+model.TableObject.toTableObject(t2).printSchema());
            
            //List<database.model.TableObject> tableObjects  = new ArrayList<>();
            List<TableObject> tableObjects = new ArrayList<>();
            tableObjects.add(t15);
            tableObjects.add(t14);
            tableObjects.add(t13);
            tableObjects.add(t12);
            tableObjects.add(t11);
            tableObjects.add(t10);
            tableObjects.add(t9);
            tableObjects.add(t8);
            tableObjects.add(t7);
            tableObjects.add(t6);
            tableObjects.add(t5);
            tableObjects.add(t4);
            tableObjects.add(t3);
            tableObjects.add(t2);
            tableObjects.add(t);
            DatabaseManager.createTables(tableObjects);
        }
	else if(tableName.equals("FFF")){
	    return "WHY?";
	}
        else{
            return "No this table";
        }

        return tableName + "  ok  ";// + t.printSchema();
    }
    public String deleteTable(String tableName){
        DatabaseManager.deleteAll(tableName);
        return tableName + "  delete";
    }


    public List<Map<String, Object>> departmentList() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (DatabaseObject department : DatabaseManager.retrieveAll("Department")) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", department.get("id"));
            map.put("name", department.get("name"));
            list.add(map);
        }
        return list;
    }



    public Department departmentInsert(Department department) {

        if (department.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: 'id'.");
        //if (departmentRepository.CountByName(department.getName()) > 0)
        //    throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        //if (departmentRepository.CountByTag(department.getTag()) > 0)
        //    throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");
        DatabaseObject d = DatabaseObject.initMethod("Department");
        d.putString("POSCode",department.getCode());
        d.putString("name",department.getName());
        d.putString("description",department.getDescription());
        d.putString("tag",department.getTag());
        d.putInteger("id",department.getId());
        DatabaseManager.save(d);
        //System.out.println(d.get("__id").toString());
        return department;
    }
    public Category categoryInsert(String did, Category category) {

        if (category.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: 'id'.");
        //if (departmentRepository.CountByName(department.getName()) > 0)
        //    throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        //if (departmentRepository.CountByTag(department.getTag()) > 0)
        //    throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");



        DatabaseObject c = DatabaseObject.initMethod("Category");
        c.putBoolean("sub",category.getSub());
        c.putString("name",category.getName());
        c.putString("description",category.getDescription());
        c.putString("tag",category.getTag());
        c.putInteger("id",category.getId());
        DatabaseManager.save(c);

        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction isCorrectTag = model.Restriction.equal("tag",category.getTag());
        restrict.add(isCorrectTag);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Category",restrict);
        c = list.get(0);

        DatabaseObject d_c = DatabaseObject.initMethod("Department_Category");
        d_c.putString("cid",c.get("__id").toString());
        d_c.putString("did",did);
        DatabaseManager.save(d_c);
        return category;
    }

    public String gerTry() throws JSONException {
        int i=0;
        JSONArray jsonA = new JSONArray();
        /*for(Token t : tokenRepository.GetAllToken()){
            JSONObject jsonO = new JSONObject();
            jsonO.put("id",t.getId());
            jsonO.put("expire",t.getExpire());
            jsonO.put("uid",t.getUser().getId());
            jsonO.put("token",t.getToken());

            jsonA.put(i,jsonO);
            i+=1;
        }*/

        return jsonA.toString();
    }

    //add user marks 1234 1
    public String addUser(String username, String password, int pri){
        DatabaseObject d = DatabaseObject.initMethod("User");
        d.putString("username",username);
        d.putString("password",password);
        d.putInteger("priviledge",pri);
        DatabaseManager.save(d);
        return d.get(username) + "  " + d.get(password);
    }



    public DatabaseObject authenticate2(String username, String password) {
        List<model.Restriction> restrict = new ArrayList<>();
        List<model.Restriction> and_restrict = new ArrayList<>();
        model.Restriction isCorrectUsername = model.Restriction.equal("username",username);
        model.Restriction isCorrectPassword = model.Restriction.equal("password",password);
        and_restrict.add(isCorrectUsername);
        and_restrict.add(isCorrectPassword);
        model.Restriction and = model.Restriction.and(and_restrict);

        restrict.add(and);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("User",restrict);
        //List<User> list = userRepository.findByUsernameAndPassword(username, password);
        if (list.isEmpty())
            throw new NotFoundException("Username or password not matched.");
        return list.get(0);
    }

    public void expireToken2(DatabaseObject user) {
        List<model.Restriction> restrict = new ArrayList<>();
        model.Restriction tokenToUser = model.Restriction.equal("uid",user.get("__id").toString());
        restrict.add(tokenToUser);
        List<DatabaseObject> tokenList = DatabaseManager.retrieveWithRestriction("Token",restrict);

        for(DatabaseObject token : tokenList){
            Date d = (Date)token.get("expire");
            if(d.compareTo(new Date())>0){
                token.putDate("expire",new Date());
                DatabaseManager.save(token);
            }
        }
        /*if (token.getExpire().compareTo(new Date()) > 0) {
            token.setExpire(new Date());
            tokenRepository.save(token);
        }*/
    }


    public DatabaseObject assignNewToken2(DatabaseObject user) {
        System.out.println(user.get("__id").toString()+"                   QQQQQQQQ");
        DatabaseObject token = DatabaseObject.initMethod("Token");
        token.putString("uid",user.get("__id").toString());
        token.putDate("expire",DateUtils.addDays(new Date(), 1));
        token.putString("token",UUID.randomUUID().toString().replace("-", ""));
        DatabaseManager.save(token);

        //token.setUser(user);
        //token.setToken(UUID.randomUUID().toString().replace("-", ""));
        //token.setExpire(DateUtils.addDays(new Date(), 1));
        //return tokenRepository.save(token);
        return token;
    }


    public void isLogin2(Integer uid, String token) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<model.Restriction> restrict = new ArrayList<>();
        List<model.Restriction> and_restrict = new ArrayList<>();

        /*List<model.Restriction> restrict1 = new ArrayList<>();
        List<model.Restriction> and_restrict1 = new ArrayList<>();*/
        model.Restriction r1 = model.Restriction.equal("uid",String.valueOf(uid));
        model.Restriction r2 = model.Restriction.equal("token",token);
        model.Restriction r3 = model.Restriction.greaterThan("expire",timestamp);

        /*System.out.println("QQQQQ " + timestamp);
        and_restrict1.add(r1);
        and_restrict1.add(r2);
        model.Restriction and1 = model.Restriction.and(and_restrict1);
        restrict1.add(and1);*/

        /*List<DatabaseObject> list2 = DatabaseManager.retrieveAll("Token");
        System.out.println("See all Token");
        for(DatabaseObject d : list2){
            System.out.println(d.get("uid").toString() + " =? " + uid);
            System.out.println(d.get("token").toString() + " =? " + token);
            System.out.println(d.get("expire").toString() + " =? " + timestamp);
        }
        System.out.println(list2.size());

        List<DatabaseObject> list1 = DatabaseManager.retrieveWithRestriction("Token",restrict1);
        System.out.println("See timestamp");
        for(DatabaseObject d : list1){
            System.out.println(d.get("uid").toString() + " =? " + uid);
            System.out.println(d.get("token").toString() + " =? " + token);
            System.out.println(d.get("expire").toString() + " =? " + timestamp);
        }
        System.out.println(list1.size());*/


        and_restrict.add(r1);
        and_restrict.add(r2);
        and_restrict.add(r3);
        model.Restriction and = model.Restriction.and(and_restrict);

        restrict.add(and);
        List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Token",restrict);
        System.out.println(list.size());

        if (list.isEmpty())
            throw new UnauthorizedException("User not logged in.");
    }


}
