package com.sandesh.libraryservice.config;

import com.sandesh.libraryservice.event.VehicleNameModifiedEvent;
import com.sandesh.libraryservice.model.Course;
import com.sandesh.libraryservice.model.Lecturer;
import com.sandesh.libraryservice.model.Owner;
import com.sandesh.libraryservice.model.Vehicle;
import com.sandesh.libraryservice.repository.CourseRepository;
import com.sandesh.libraryservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CommonConfig {

    @Bean
    public CommandLineRunner commandLineRunner(CourseRepository courseRepository) {
        System.out.println("Config running");
        return args -> {
            Course course = Course.builder().title("Social").build();
            Lecturer lecturer = Lecturer.builder().name("John Doe").build();
            Lecturer lecturer1 = Lecturer.builder().name("Jack Ma").build();
            course.setLecturers(List.of(lecturer, lecturer1));
            courseRepository.save(course);
        };

    }

    @Bean
    public CommandLineRunner commandLineRunnerVehicle(VehicleRepository vehicleRepository) {
        System.out.println("Creating vehicles");
        return args -> {
            Vehicle truck = Vehicle.builder().type("Truck").build();
            List<Owner> ow = List.of(
                    Owner.builder().name("Jack").address("Chicago").build(),
                    Owner.builder().name("John").address("Washington").build(),
                    Owner.builder().name("Mike").address("Texas").build(),
                    Owner.builder().name("Russell").address("Michigan").build()
            );
            truck.setNameExplicitly("Johnson");
            truck.setOwners(ow);
            vehicleRepository.save(truck);
        };

    }

    @EventListener(VehicleNameModifiedEvent.class)
    public void vehicleModifiedEventListener(VehicleNameModifiedEvent vehicleNameModifiedEvent) {
        log.info("Vehicle name set as : {} ", vehicleNameModifiedEvent.getName());
    }
}
