package com.sandesh.libraryservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VehicleNameModifiedEvent {
    private String name;
}
