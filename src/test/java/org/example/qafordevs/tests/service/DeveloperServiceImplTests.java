package org.example.qafordevs.tests.service;

import org.example.qafordevs.entity.DeveloperEntity;
import org.example.qafordevs.exception.DeveloperDuplicateEmailException;
import org.example.qafordevs.exception.DeveloperNotFoundException;
import org.example.qafordevs.repository.DeveloperRepository;
import org.example.qafordevs.service.DeveloperServiceImpl;
import org.example.qafordevs.utils.generator.EntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Developer service implementation tests")
@ExtendWith(MockitoExtension.class)
public class DeveloperServiceImplTests {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperServiceImpl serviceUnderTest;

    @Test
    @DisplayName("Test save developer functionality")
    public void givenDeveloperToSave_whenSave_thenRepositoryIsCalled() {
        //given
        DeveloperEntity developerToCreated = EntityGenerator.getDeveloperJohnDoeTransient();
        BDDMockito
            .given(developerRepository.findByEmail(anyString()))
            .willReturn(null);
        BDDMockito
            .given(developerRepository.save(any(DeveloperEntity.class)))
            .willReturn(EntityGenerator.getDeveloperJohnDoePersisted());
        //when
        DeveloperEntity savedDeveloper = serviceUnderTest.saveDeveloper(developerToCreated);
        //then
        assertThat(savedDeveloper).isNotNull();
        verify(developerRepository, times(1)).save(developerToCreated);
    }

    @Test
    @DisplayName("Test save developer with duplicate email functionality")
    public void givenDeveloperToSaveWithDuplicateEmail_whenSave_thenExceptionIsThrown() {
        //given
        DeveloperEntity developerToCreated = EntityGenerator.getDeveloperJohnDoeTransient();
        BDDMockito
            .given(developerRepository.findByEmail(anyString()))
            .willReturn(EntityGenerator.getDeveloperJohnDoePersisted());
        //when
        assertThrows(
            DeveloperDuplicateEmailException.class,
            () -> serviceUnderTest.saveDeveloper(developerToCreated)
        );
        //then
        verify(developerRepository, never()).save(any(DeveloperEntity.class));
    }

    @Test
    @DisplayName("Test update developer functionality")
    public void givenDeveloperToUpdate_whenUpdateDeveloper_thenRepositoryIsCalled() {
        //given
        DeveloperEntity developerToUpdate = EntityGenerator.getDeveloperJohnDoePersisted();
        BDDMockito
            .given(developerRepository.existsById(anyInt()))
            .willReturn(true);
        BDDMockito
            .given(developerRepository.save(any(DeveloperEntity.class)))
            .willReturn(developerToUpdate);
        //when
        DeveloperEntity updatedDeveloper = serviceUnderTest.updateDeveloper(developerToUpdate);
        //then
        assertThat(updatedDeveloper).isNotNull();
        verify(developerRepository, times(1)).save(developerToUpdate);
    }

    @Test
    @DisplayName("Test update developer with incorrect id functionality")
    public void givenDeveloperToUpdateWithIncorrectId_whenUpdateDeveloper_thenExceptionIsThrown() {
        //given
        DeveloperEntity developerToUpdate = EntityGenerator.getDeveloperJohnDoePersisted();
        BDDMockito
            .given(developerRepository.existsById(anyInt()))
            .willReturn(false);
        //when
        assertThrows(
            DeveloperNotFoundException.class,
            () -> serviceUnderTest.updateDeveloper(developerToUpdate)
        );
        //then
        verify(developerRepository, never()).save(any(DeveloperEntity.class));
    }

    @Test
    @DisplayName("Test get developer by id functionality")
    public void givenId_whenGetDeveloperById_thenDeveloperIsReturned() {
        //given
        BDDMockito
            .given(developerRepository.findById(anyInt()))
            .willReturn(Optional.of(EntityGenerator.getDeveloperJohnDoePersisted()));
        //when
        DeveloperEntity obtainedDeveloper = serviceUnderTest.getDeveloperById(1);
        //then
        assertThat(obtainedDeveloper).isNotNull();
    }

    @Test
    @DisplayName("Test get developer by incorrect id functionality")
    public void givenIncorrectId_whenGetDeveloperById_thenExceptionIsThrown() {
        //given
        BDDMockito
            .given(developerRepository.findById(anyInt()))
            .willReturn(Optional.empty());
        //when
        assertThrows(
            DeveloperNotFoundException.class,
            () -> serviceUnderTest.getDeveloperById(1)
        );
        //then
    }

    @Test
    @DisplayName("Test get developer by email functionality")
    public void givenEmail_whenGetDeveloperByEmail_thenDeveloperIsReturned() {
        //given
        BDDMockito
            .given(developerRepository.findByEmail(anyString()))
            .willReturn(EntityGenerator.getDeveloperJohnDoePersisted());
        //when
        DeveloperEntity obtainedDeveloper = serviceUnderTest.getDeveloperByEmail("john.doe@mail.com");
        //then
        assertThat(obtainedDeveloper).isNotNull();
    }

    @Test
    @DisplayName("Test get developer by incorrect email functionality")
    public void givenIncorrectEmail_whenGetDeveloperByEmail_thenExceptionIsThrown() {
        //given
        BDDMockito
            .given(developerRepository.findByEmail(anyString()))
            .willReturn(null);
        //when
        assertThrows(
            DeveloperNotFoundException.class,
            () -> serviceUnderTest.getDeveloperByEmail("john.doe@mail.com")
        );
        //then
    }
}
