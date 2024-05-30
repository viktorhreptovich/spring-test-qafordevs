package org.example.qafordevs.service;

import org.example.qafordevs.entity.DeveloperEntity;

import java.util.List;

public interface DeveloperService {

    DeveloperEntity saveDeveloper(DeveloperEntity developerEntity);

    DeveloperEntity updateDeveloper(DeveloperEntity developerEntity);

    DeveloperEntity getDeveloperById(Integer id);

    DeveloperEntity getDeveloperByEmail(String name);

    List<DeveloperEntity> getAllDevelopers();

    List<DeveloperEntity> getAllActiveBySpeciality(String speciality);

    void deactivateDeveloperById(Integer id);

    void deleteDeveloperById(Integer id);

}
