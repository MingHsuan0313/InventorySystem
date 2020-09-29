package ntu.csie.selab.inventorysystem.service;

import manager.DatabaseManager;
import model.DatabaseObject;
import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnauthorizedException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.csie.selab.inventorysystem.model.Token;
import ntu.csie.selab.inventorysystem.model.User;
import ntu.csie.selab.inventorysystem.repository.TokenRepository;
import ntu.csie.selab.inventorysystem.repository.UserRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    /*@Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;*/

    /*public User authenticate(String username, String password) {
        List<User> list = userRepository.findByUsernameAndPassword(username, password);
        if (list.isEmpty())
            throw new NotFoundException("Username or password not matched.");
        return list.get(0);
    }*/
    public DatabaseObject authenticate(String username, String password) {
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

    /*public void expireToken(Token token) {
        if (token.getExpire().compareTo(new Date()) > 0) {
            token.setExpire(new Date());
            tokenRepository.save(token);
        }
    }*/
    public void expireToken(DatabaseObject user) {
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

    /*public Token assignNewToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString().replace("-", ""));
        token.setExpire(DateUtils.addDays(new Date(), 1));
        return tokenRepository.save(token);
    }*/
    public DatabaseObject assignNewToken(DatabaseObject user) {
        //System.out.println(user.get("__id").toString()+"                   QQQQQQQQ");
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

    /*public void isLogin(Integer uid, String token) {
        List<Token> list = tokenRepository.CheckAvailableToken(new User(uid), token);
        if (list.isEmpty())
            throw new UnauthorizedException("User not logged in.");
    }*/
    public void isLogin(Integer uid, String token) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<model.Restriction> restrict = new ArrayList<>();
        List<model.Restriction> and_restrict = new ArrayList<>();

        model.Restriction r1 = model.Restriction.equal("uid",String.valueOf(uid));
        model.Restriction r2 = model.Restriction.equal("token",token);
        model.Restriction r3 = model.Restriction.greaterThan("expire",timestamp);

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
