package pl.tomaszdziurko.itemdirectory.domain.dao.items;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import pl.tomaszdziurko.itemdirectory.domain.dao.AbstractDAO;
import pl.tomaszdziurko.itemdirectory.domain.entities.items.Location;

@Repository(value = "locationDao")
public class LocationDaoImpl extends AbstractDAO<Location> implements LocationDao {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<Location> getClazz() {
        return Location.class;
    }


}