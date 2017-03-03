package fr.robot.twitterClient.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.robot.twitterClient.dataaccess.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    void delete(Privilege privilege);

}
