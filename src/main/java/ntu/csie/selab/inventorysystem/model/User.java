package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "privilege")
    private Integer privilege;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Token> tokens;

    public User() {}
    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Integer getPrivilege() {
        return privilege;
    }
    public List<Token> getTokens() {
        return tokens;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    @Override
    public String toString() {
        return String.format("User[id=%d, username='%s', password='%s', privilege=%d]",
                id, username, password, privilege);
    }
}
