package com.sandesh.libraryservice.repository;

import com.sandesh.libraryservice.model.Course;
import com.sandesh.libraryservice.model.Lecturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(value = "classpath:application.properties")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setup() {
        System.out.println("Initializing");
        Course course = Course.builder().title("Social").build();
        Lecturer lecturer = Lecturer.builder().name("John Doe").build();
        Lecturer lecturer1 = Lecturer.builder().name("Jack Ma").build();
        course.setLecturers(List.of(lecturer, lecturer1));
        courseRepository.save(course);
    }

    @Test
    void testGetTitles() {
        System.out.println("Printing all titles");
        List<String> titles = courseRepository.getAllTitles();
        assertEquals(1, titles.size());
        assertEquals("Social", titles.getFirst());
    }

    @Test
    void testGetTitles_query() {
        System.out.println("Printing all titles via query");
        List<String> titles = courseRepository.getTitlesQuery();
        assertEquals(1, titles.size());
        assertEquals("Social", titles.getFirst());
    }

    @Test
    void testGetAll() {
        List<Course> courses = courseRepository.findAll();
        assertEquals(1, courses.size());
        assertEquals(2, courses.getFirst().getLecturers().size());
    }

    @Test
    void testGetAllCustom() {
        List<Course> courses = courseRepository.findAllCustom();
        assertEquals(1, courses.size());
        assertEquals(2, courses.getFirst().getLecturers().size());
    }
}