package org.example.qafordevs.tests.service;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

}
