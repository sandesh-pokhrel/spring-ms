package com.sandesh.libraryservice.controller;

import com.sandesh.libraryservice.config.KafkaConfig;
import com.sandesh.libraryservice.model.Course;
import com.sandesh.libraryservice.model.Lecturer;
import com.sandesh.libraryservice.repository.CourseRepository;
import com.sandesh.libraryservice.repository.LecturerRepository;
import com.sandesh.libraryservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.StringTemplate.STR;

@Slf4j
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseService courseService;
    private final KafkaTemplate<Long, Lecturer> lecturerKafkaTemplate;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAllCustomFetch();
    }

    @GetMapping("/bulk")
    public void getAllCoursesBulk() {
        List<Course> courses = courseRepository.findAll();
        log.info("Fetch course completed");
    }

    /**
     * Spec is great to create dynamic query with reusable components
     * Query builder is also good to create dynamic queries
     * @param title Course title
     */
    @GetMapping("/specs/{title}")
    public void getAllCoursesByTitleUsingSpecs(@PathVariable String title) {
        Specification<Course> spec = (root, _, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), STR."%\{title}%");
        Specification<Course> specSocialDefault = (root, _, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "social");
        List<Course> courses = courseRepository.findAll(spec.or(specSocialDefault));
        courses.forEach(course -> log.info(course.getTitle()));
    }

    /**
     * Example matcher isn't much great
     * @param title Course title
     */
    @GetMapping("/example/{title}")
    public void getAllCoursesByTitleUsingExample(@PathVariable String title) {
        ExampleMatcher titleMatcher = ExampleMatcher.matching()
//                .withIgnorePaths("id", "createdAt", "updatedAt", "createdBy", "updatedBy") // if null and .withIncludeNullValues() is enabled then ignore those given list
//                .withIncludeNullValues() // all null values in Course will be included in where  condition
//                .withStringMatcher(ExampleMatcher.StringMatcher.ENDING);
                .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<Course> courseExample = Example.of(Course.builder().title(title).build(), titleMatcher);
        List<Course> courses = courseRepository.findAll(courseExample);
        courses.forEach(course -> log.info(course.getTitle()));
    }

    @GetMapping("/title/{title}")
    public void getAllCoursesTitle(@PathVariable String title) {
        List<Course> courses = courseService.getCourseByTitle(title);
        courses.forEach(c -> log.info(c.getTitle()));
    }

    @PostMapping
    public void saveCourses() {
        List<Course> courses = List.of(
                Course.builder().title("Title 1").build(),
                Course.builder().title("Title 2").build(),
                Course.builder().title("Title 3").build(),
                Course.builder().title("Title 4").build(),
                Course.builder().title("Title 5").build(),
                Course.builder().title("Title 6").build()
        );
        courseService.saveCourses(courses);
    }

    @PostMapping("/bulk")
    public void saveCoursesBulk() {
        List<Course> courses = IntStream.range(1,100)
                .mapToObj(val -> Course.builder().id((long)val).title(STR."Title \{val}").build())
                .collect(Collectors.toList());
        courseService.saveCoursesBulk(courses);
    }

    @GetMapping("/publish-lecturers")
    public void publishLecturers() {
        lecturerRepository.findAll()
                .forEach(lecturer -> lecturerKafkaTemplate.send(KafkaConfig.LECTURER_TOPIC, lecturer)
                        .whenComplete((result, ex) -> {
                            if (ex != null) {
                                log.error("Some error occurred while sending lecturer {}", ex.getMessage());
                            } else {
                                log.info("Sent the stuff {} ", result.getProducerRecord().value());
                            }
                        }));
    }
}
