package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;
    @Column(name = "token")
    private String token;
    @Column(name = "expire")
    private Date expire;

    public Integer getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getToken() {
        return token;
    }
    public Date getExpire() {
        return expire;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setExpire(Date expire) {
        this.expire = expire;
    }
    @Override
    public String toString() {
        return String.format("Token[id=%d, uid=%d, token='%s', expire='%s']",
                id, user.getId(), token, expire.toString());
    }
}
