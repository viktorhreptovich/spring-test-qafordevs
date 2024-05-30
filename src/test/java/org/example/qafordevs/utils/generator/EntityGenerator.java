package org.example.qafordevs.utils.generator;

import io.qameta.allure.Step;
import org.example.qafordevs.entity.DeveloperEntity;
import org.example.qafordevs.entity.Status;

public class EntityGenerator {

    public static DeveloperEntity getDeveloperJohnDoeTransient() {
        return DeveloperEntity.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@mail.com")
            .speciality("Java")
            .status(Status.ACTIVE)
            .build();
    }

    public static DeveloperEntity getDeveloperMikeSmithTransient() {
        return DeveloperEntity.builder()
            .firstName("Mike")
            .lastName("Smith")
            .email("mike.smith@mail.com")
            .speciality("Java")
            .status(Status.ACTIVE)
            .build();
    }

    public static DeveloperEntity getDeveloperFrankJonesTransient() {
        return DeveloperEntity.builder()
            .firstName("Frank")
            .lastName("Jones")
            .email("frank.jones@mail.com")
            .speciality("Java")
            .status(Status.DELETED)
            .build();
    }

    public static DeveloperEntity getDeveloperJohnDoePersisted() {
        return DeveloperEntity.builder()
            .id(1)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@mail.com")
            .speciality("Java")
            .status(Status.ACTIVE)
            .build();
    }

    public static DeveloperEntity getDeveloperMikeSmithPersisted() {
        return DeveloperEntity.builder()
            .id(2)
            .firstName("Mike")
            .lastName("Smith")
            .email("mike.smith@mail.com")
            .speciality("Java")
            .status(Status.ACTIVE)
            .build();
    }

    public static DeveloperEntity getDeveloperFrankJonesPersisted() {
        return DeveloperEntity.builder()
            .id(3)
            .firstName("Frank")
            .lastName("Jones")
            .email("frank.jones@mail.com")
            .speciality("Java")
            .status(Status.DELETED)
            .build();
    }

}
