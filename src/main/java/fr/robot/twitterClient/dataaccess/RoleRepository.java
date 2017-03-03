package fr.robot.twitterClient.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.robot.twitterClient.dataaccess.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    void delete(Role role);

}
