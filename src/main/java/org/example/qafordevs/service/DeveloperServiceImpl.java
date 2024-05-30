package org.example.qafordevs.service;

import lombok.RequiredArgsConstructor;
import org.example.qafordevs.entity.DeveloperEntity;
import org.example.qafordevs.entity.Status;
import org.example.qafordevs.exception.DeveloperDuplicateEmailException;
import org.example.qafordevs.exception.DeveloperNotFoundException;
import org.example.qafordevs.repository.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;

    @Override
    public DeveloperEntity saveDeveloper(DeveloperEntity developerEntity) {
        DeveloperEntity duplicateCandidate = developerRepository.findByEmail(developerEntity.getEmail());

        if (Objects.nonNull(duplicateCandidate)) {
            throw new DeveloperDuplicateEmailException(String.format("Developer with email %s already exists", developerEntity.getEmail()));
        }

        return developerRepository.save(developerEntity);
    }

    @Override
    public DeveloperEntity updateDeveloper(DeveloperEntity developerEntity) {
        boolean exists = developerRepository.existsById(developerEntity.getId());

        if (!exists) {
            throw new DeveloperNotFoundException(String.format("Developer with id %s not found", developerEntity.getId()));
        }

        return developerRepository.save(developerEntity);
    }

    @Override
    public DeveloperEntity getDeveloperById(Integer id) {
        return developerRepository.findById(id).orElseThrow(
            () -> new DeveloperNotFoundException(String.format("Developer with id %s not found", id))
        );
    }

    @Override
    public DeveloperEntity getDeveloperByEmail(String name) {
        DeveloperEntity developer = developerRepository.findByEmail(name);

        if (Objects.isNull(developer)) {
            throw new DeveloperNotFoundException(String.format("Developer with email %s not found", name));
        }

        return developer;
    }

    @Override
    public List<DeveloperEntity> getAllDevelopers() {
        return developerRepository.findAll().
            stream()
            .filter(developer -> developer.getStatus().equals(Status.ACTIVE))
            .toList();
    }

    @Override
    public List<DeveloperEntity> getAllActiveBySpeciality(String speciality) {
        return developerRepository.findAllActiveBySpeciality(speciality);
    }

    @Override
    public void deactivateDeveloperById(Integer id) {
        DeveloperEntity developer = developerRepository.findById(id)
            .orElseThrow(
                () -> new DeveloperNotFoundException(String.format("Developer with id %s not found", id))
            );
        developer.setStatus(Status.DELETED);
        developerRepository.save(developer);
    }

    @Override
    public void deleteDeveloperById(Integer id) {
        DeveloperEntity developer = developerRepository.findById(id)
            .orElseThrow(
                () -> new DeveloperNotFoundException(String.format("Developer with id %s not found", id))
            );
        developerRepository.deleteById(developer.getId());
    }

}
