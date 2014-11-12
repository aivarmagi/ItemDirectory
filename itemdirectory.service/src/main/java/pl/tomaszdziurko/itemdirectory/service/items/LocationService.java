package pl.tomaszdziurko.itemdirectory.service.items;

import pl.tomaszdziurko.itemdirectory.domain.entities.items.Location;


public interface LocationService {

    Location findById(Long id);

    void save(Location location);

    Location update(Location location);

    void remove(Location location);

    long size();

}