package com.sandesh.libraryservice.model;

import com.sandesh.libraryservice.event.VehicleNameModifiedEvent;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle extends AbstractAggregateRoot<Vehicle> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "vehicle_id")
    private List<Owner> owners;

    public void setNameExplicitly(String name) {
        this.name = name;
        registerEvent(new VehicleNameModifiedEvent(name));
    }

}
