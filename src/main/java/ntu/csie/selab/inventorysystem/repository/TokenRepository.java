package ntu.csie.selab.inventorysystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntu.csie.selab.inventorysystem.model.Token;
import ntu.csie.selab.inventorysystem.model.User;

import java.util.List;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {
    @Query(value = "SELECT t FROM Token t WHERE t.user = :user AND t.token = :token AND t.expire > CURRENT_TIMESTAMP")
    List<Token> CheckAvailableToken(@Param("user") User user, @Param("token") String token);

    @Query(value = "SELECT t FROM Token t")
    List<Token> GetAllToken();
}
