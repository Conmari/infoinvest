package scari.corp.infoinvest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import scari.corp.infoinvest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
