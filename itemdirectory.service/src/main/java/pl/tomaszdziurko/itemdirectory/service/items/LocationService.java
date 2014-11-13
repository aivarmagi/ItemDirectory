package pl.tomaszdziurko.itemdirectory.service.items;

import pl.tomaszdziurko.itemdirectory.domain.entities.items.Location;

import java.util.List;


public interface LocationService {

    Location findById(Long id);

    void save(Location location);

    Location update(Location location);

    void remove(Location location);

    long size();

    Location findByName(String locationName);

    List<Location> findAll();
}