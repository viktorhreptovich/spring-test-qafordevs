package org.example.qafordevs.repository;

import org.example.qafordevs.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Integer> {

    DeveloperEntity findByEmail(String email);

    @Query("SELECT developers FROM DeveloperEntity developers " +
        "WHERE developers.status = 'ACTIVE' AND developers.speciality = ?1")
    List<DeveloperEntity> findAllActiveBySpeciality(String speciality);

}
