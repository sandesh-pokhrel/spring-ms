package com.sandesh.libraryservice.service;

import com.sandesh.libraryservice.model.Course;
import com.sandesh.libraryservice.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCourses(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    @BatchSize(size = 20)
    @Transactional
    public void saveCoursesBulk(List<Course> courses) {
        courseRepository.saveAll(courses);
    }

    public List<Course> getCourseByTitle(String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);

        Expression<Boolean> titlePredicate = criteriaBuilder.like(root.get("title"), STR."%\{title}%");
        query.where(titlePredicate);
        TypedQuery<Course> courseByTitleQuery = entityManager.createQuery(query);
        return courseByTitleQuery.getResultList();
    }
}
