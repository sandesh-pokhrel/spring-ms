package com.sandesh.libraryservice.repository;

import com.sandesh.libraryservice.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @Query(name = "course.titles")
    List<String> getAllTitles();

    @Query(value = "select c.title from Course c")
    List<String> getTitlesQuery();

    @Transactional
    @Query(value = "select c from Course c")
    List<Course> findAllCustom();

    @Query(value = "select c from Course c left join fetch c.lecturers")
    List<Course> findAllCustomFetch();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "course.lecturers.nested")
    @Query(value = "select c from Course c")
    List<Course> findAllGraphFetch();
}
