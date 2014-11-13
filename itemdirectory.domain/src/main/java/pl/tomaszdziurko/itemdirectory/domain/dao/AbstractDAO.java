package pl.tomaszdziurko.itemdirectory.domain.dao;

import org.apache.log4j.Logger;

import pl.tomaszdziurko.itemdirectory.domain.entities.AbstractEntity;
import pl.tomaszdziurko.itemdirectory.domain.entities.IEntity;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public EntityClass findByProperty(String propertyName, Object propertyValue) {

        if (propertyName == null || propertyValue == null){
            throw new IllegalArgumentException("Property name and value cannot be null!"
            + " Values: propertyName" + propertyName + ", propertyValue: "+ propertyValue);
        }

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();     // (1)
        CriteriaQuery<EntityClass> findEntityQuery = cb.createQuery(getClazz());     // (2)
        Root<EntityClass> entity = findEntityQuery.from(getClazz());               // (3)

        Predicate propertyPredicate = cb.equal(entity.get(propertyName), propertyValue);   //  (4)

        findEntityQuery.where(propertyPredicate);       // (5)

        //return getEntityManager().createQuery(findEntityQuery).getSingleResult();       // (6)
        TypedQuery<EntityClass> query = getEntityManager().createQuery(findEntityQuery);
            List<EntityClass> entities = query.setMaxResults(1).getResultList();

        if (entities.isEmpty()) return null; else return entities.get(0);

        /*
            1. CritieriaBuilder is a factory for almost every method and object in Critieria API, so we use one here.
            2. CritieriaQuery specifies what we want to get from the database. In this case, we need object of class
            returned by getClazz() object.  getClazz() is a method from DAO interface which will be implemented in
            concrete DAO classes such as LocationDAOImpl.
            3. Root represents SQL ‘from’ clause.
            4. Predicates represent elements after SQL ‘where’.  We need only one, similar to
            ‘’where entity.propertyName = :propertyValue’ so we create it with CriteriaBuilder.
            5. Predicate to do its job, must be added to query.
            6. Finally, we can return found results (or null).
         */
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
