package org.example.qafordevs.tests.repository;

import org.example.qafordevs.entity.DeveloperEntity;
import org.example.qafordevs.repository.DeveloperRepository;
import org.example.qafordevs.utils.generator.EntityGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Developer repository tests")
public class DeveloperRepositoryTests {

    @Autowired
    private DeveloperRepository developerRepository;

    @BeforeEach
    public void setUp() {
        developerRepository.deleteAll();

    }

    @Test
    @DisplayName("Test save developer functionality")
    public void givenDeveloperObject_whenSave_thenDeveloperIsCreated() {
        //given
        DeveloperEntity developerToCreated = EntityGenerator.getDeveloperJohnDoeTransient();
        //when
        DeveloperEntity savedDeveloper = developerRepository.save(developerToCreated);
        //then
        assertThat(savedDeveloper).isNotNull();
        assertThat(savedDeveloper.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test get developer by id functionality")
    public void givenDeveloperCreated_whenGetById_thenDeveloperIsReturned() {
        //given
        DeveloperEntity developerToCreated = EntityGenerator.getDeveloperJohnDoeTransient();
        developerRepository.save(developerToCreated);
        //when
        DeveloperEntity obtainedDeveloper = developerRepository.findById(developerToCreated.getId())
            .orElse(null);
        //then
        assertThat(obtainedDeveloper).isNotNull();
        assertThat(obtainedDeveloper.getEmail()).isEqualTo("john.doe@mail.com");
    }

    @Test
    @DisplayName("Test developer not found functionality")
    public void givenDeveloperIsNotCreated_whenGetById_thenOptionalIsEmpty() {
        //given
        //when
        DeveloperEntity obtainedDeveloper = developerRepository.findById(1).orElse(null);
        //then
        assertThat(obtainedDeveloper).isNull();
    }

    @Test
    @DisplayName("Test update developer functionality")
    public void givenDeveloperToUpdate_whenSave_thenEmailIsChanged() {
        //given
        String updatedEmail = "updated@mail.com";
        DeveloperEntity developerToCreated = EntityGenerator.getDeveloperJohnDoeTransient();
        developerRepository.save(developerToCreated);
        //when
        DeveloperEntity developerToUpdate = developerRepository.findById(developerToCreated.getId())
            .orElse(null);
        developerToUpdate.setEmail(updatedEmail);
        DeveloperEntity updatedDeveloper = developerRepository.save(developerToUpdate);
        //then
        assertThat(updatedDeveloper).isNotNull();
        assertThat(updatedDeveloper.getEmail()).isEqualTo(updatedEmail);
    }


    @Test
    @DisplayName("Test get all developers functionality")
    public void givenThreeDevelopersAreStored_whenFindAll_thenAllDevelopersAreReturned() {
        //given
        DeveloperEntity developer1 = EntityGenerator.getDeveloperJohnDoeTransient();
        DeveloperEntity developer2 = EntityGenerator.getDeveloperMikeSmithTransient();
        DeveloperEntity developer3 = EntityGenerator.getDeveloperFrankJonesTransient();
        developerRepository.saveAll(List.of(developer1, developer2, developer3));
        //when
        List<DeveloperEntity> obtainedDevelopers = developerRepository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(obtainedDevelopers)).isFalse();
        assertThat(obtainedDevelopers.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test get developer by email functionality")
    public void givenDeveloperSaved_whenGetByEmail_thenDeveloperIsReturned() {
        //given
        DeveloperEntity developer = EntityGenerator.getDeveloperJohnDoeTransient();
        developerRepository.save(developer);
        //when
        DeveloperEntity obtainedDeveloper = developerRepository.findByEmail(developer.getEmail());
        //then
        assertThat(obtainedDeveloper).isNotNull();
        assertThat(obtainedDeveloper.getEmail()).isEqualTo(developer.getEmail());
    }

    @Test
    @DisplayName("Test get all active developers by speciality functionality")
    public void givenThreeDevelopersAndTwoActive_whenFindAllActiveBySpeciality_thenTwoDevelopersAreReturned() {
        //given
        DeveloperEntity developer1 = EntityGenerator.getDeveloperJohnDoeTransient();
        DeveloperEntity developer2 = EntityGenerator.getDeveloperMikeSmithTransient();
        DeveloperEntity developer3 = EntityGenerator.getDeveloperFrankJonesTransient();
        developerRepository.saveAll(List.of(developer1, developer2, developer3));
        //when
        List<DeveloperEntity> obtainedDevelopers = developerRepository.findAllActiveBySpeciality("Java");
        //then
        assertThat(CollectionUtils.isEmpty(obtainedDevelopers)).isFalse();
        assertThat(obtainedDevelopers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test delete developer by id functionality")
    public void givenDeveloperIsSaved_whenDeleteById_thenDeveloperIsDeleted() {
        //given
        DeveloperEntity developer = EntityGenerator.getDeveloperJohnDoeTransient();
        developerRepository.save(developer);
        //when
        developerRepository.deleteById(developer.getId());
        //then
        DeveloperEntity obtainedDeveloper = developerRepository.findById(developer.getId())
            .orElse(null);
        assertThat(obtainedDeveloper).isNull();
    }

}
