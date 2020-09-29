package ntu.csie.selab.inventorysystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntu.csie.selab.inventorysystem.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u WHERE u.username = :username AND u.password = PASSWORD(:password)")
    List<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
