package com.sandesh.libraryservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "course.titles", query = "select c.title from Course c")
@NamedEntityGraph(name = "course.lecturers", attributeNodes = {
    @NamedAttributeNode(value = "lecturers")
})
@NamedEntityGraph(name = "course.lecturers.nested", attributeNodes = {
        @NamedAttributeNode(value = "lecturers", subgraph = "course.lecturers.id")
}, subgraphs = {
        @NamedSubgraph(name = "course.lecturers.id", attributeNodes = {
                @NamedAttributeNode(value = "id")
        })
})
@EntityListeners(AuditingEntityListener.class)
public class Course extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "course_lecturer",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    private List<Lecturer> lecturers;
}
