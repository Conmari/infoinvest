package scari.corp.infoinvest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import scari.corp.infoinvest.model.User;

// @Repository
// public interface UserRepository extends CrudRepository<User, Long> {
// }

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}