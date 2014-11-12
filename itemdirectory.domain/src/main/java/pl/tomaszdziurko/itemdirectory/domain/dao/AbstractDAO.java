package pl.tomaszdziurko.itemdirectory.domain.dao;

import org.apache.log4j.Logger;

import pl.tomaszdziurko.itemdirectory.domain.entities.AbstractEntity;
import pl.tomaszdziurko.itemdirectory.domain.entities.IEntity;

import javax.persistence.Query;
import java.util.List;

public abstract class AbstractDAO<EntityClass extends AbstractEntity>
        implements DAO<EntityClass> {

    private static Logger LOG = Logger.getLogger(AbstractDAO.class);

    @SuppressWarnings("unchecked")
    public List<EntityClass> findAll() {
        Query query = getFindAllQuery();
        return query.getResultList();
    }

    protected Query getFindAllQuery() {
        return getEntityManager().createQuery(getBaseQueryBuilder().toString());
    }

    private StringBuilder getBaseQueryBuilder() {
        StringBuilder queryBuilder = new StringBuilder("select entity from ");
        queryBuilder.append(getClazz().getName());
        queryBuilder.append(" entity");
        return queryBuilder;
    }

    public EntityClass findById(Long id) {
        return getEntityManager().find(getClazz(), id);
    }

    public void persist(EntityClass entity) {
        getEntityManager().persist(entity);
    }

    public void remove(EntityClass entity) {
        getEntityManager().remove(entity);
    }

    public void removeSafely(EntityClass entity) {
        getEntityManager().remove(
                getEntityManager().getReference(getClazz(), entity.getId()));
    }

    public EntityClass getReference(EntityClass entity) {

        if (entity.isNew()) {
            return entity;
        } else {
            return getEntityManager().getReference(getClazz(), entity.getId());
        }
    }

    public long size() {
        StringBuilder query = new StringBuilder("select count(entity) from ");
        query.append(getClazz().getName());
        query.append(" entity");

        Long result = (Long) getEntityManager().createQuery(query.toString())
                .getSingleResult();
        return result.longValue();
    }

    public EntityClass merge(EntityClass entity) {
        return getEntityManager().merge(entity);
    }

}
