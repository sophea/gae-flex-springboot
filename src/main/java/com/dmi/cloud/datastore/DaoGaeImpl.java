package com.dmi.cloud.datastore;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmi.domain.ResponseList;
import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.FullEntity.Builder;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;

public abstract class DaoGaeImpl<D extends AbstractLongEntity> {
    
    private Datastore datastore;
    private KeyFactory keyFactory;
    
    protected Class<D> clazzOfDomain;
    protected static final Logger LOG = LoggerFactory.getLogger(DaoGaeImpl.class);
    
    public DaoGaeImpl(Class<D> clazz) {
        this.clazzOfDomain = clazz;
        // Authorized Datastore servic
        datastore = DatastoreOptions.getDefaultInstance().getService(); 
        // Is used for creating keys later
        keyFactory = datastore.newKeyFactory().setKind(getTableName());
        
        System.out.println("==========key========" +keyFactory != null);
    }
    
    
    public String getTableName() {
        return clazzOfDomain.getSimpleName();
    }
    
    public D add(final D domain) {
        IncompleteKey key = keyFactory.newKey();   
        if (domain.getId() == null) {
            key = keyFactory.newKey(); 
        } else {
            key = keyFactory.newKey(domain.getId());
        }
        
        Builder builder = FullEntity.newBuilder(key);
        
        //convert domain to Entity
        copyDomainToEntity(domain, builder);
        
        //persistance with databasel
        Entity entity = datastore.put(builder.build());
        
        domain.setId(entity.getKey().getId());
        return domain;
    }
    
    public void update(final D domain) {
        FullEntity e = toEntity(domain);
        datastore.put(e);
        System.out.println("updated "  + e.getKey());
    }
    public void delete(Long id) {
        datastore.delete(keyFactory.newKey(id));
    }
    public D findById(Long id) {
        Entity from = datastore.get(keyFactory.newKey(id)); // Load an Entity for Key(id)
        if (from == null) {
            return null;
        }
        
        final D domain = createDomain();
        domain.setId(id);
        copyEntityToDomain(from, domain);
        
        return domain;
    }
    
    public D createDomain() {
        try {
            final D domain = clazzOfDomain.newInstance();
            return domain;
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Access", ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException("Access", ex);
        }
    }

    public Collection<D> entitiesToDomains(QueryResults<Entity> resultList) {
        Collection<D> resultBooks = new ArrayList<>();
        while (resultList.hasNext()) {  // We still have data
          resultBooks.add(toDomain(resultList.next())); // Add the Domain to the List
        }
        return resultBooks;
      }
    
    public ResponseList<D> findByPage(String startCursorString, int pagesize) {
        Cursor startCursor = null;
        if (startCursorString != null && !startCursorString.equals("")) {
          startCursor = Cursor.fromUrlSafe(startCursorString);    // Where we left off
        }
        Query<Entity> query = Query.newEntityQueryBuilder()       // Build the Query
            .setKind(getTableName())                                     // We only care about Books
            .setLimit(pagesize)                                         // Only show 10 at a time
            .setStartCursor(startCursor)                          // Where we left off
            //.setOrderBy(OrderBy.asc(Book.TITLE))                  // Use default Index "title"
            .build();
        QueryResults<Entity> resultList = datastore.run(query);   // Run the query
        final Collection<D> resultDomains = entitiesToDomains(resultList);     // Retrieve and convert Entities
        Cursor cursor = resultList.getCursorAfter();              // Where to start next time
        if (cursor != null && resultDomains.size() == 10) {         // Are we paging? Save Cursor
          String cursorString = cursor.toUrlSafe();               // Cursors are WebSafe
          return new ResponseList<D>(resultDomains, cursorString);
        } else {
          return new ResponseList<D>(resultDomains);
        }
      }
    
    public Collection<D> findByFields(D domain) {
        final CompositeFilter filter = convertToFilter (domain);
        Query<Entity> query = null;
        //no filter
        if (filter == null) {
            query = Query.newEntityQueryBuilder()       // Build the Query
                .setKind(getTableName())
                .build();
        } else {
            query = Query.newEntityQueryBuilder()       // Build the Query
                    .setKind(getTableName())
                    .setFilter(filter)
                    .build();
        }
        LOG.info("query {}", query.toString());
        final QueryResults<Entity> resultList = datastore.run(query); 
        
        return entitiesToDomains(resultList);
    }
    
    public  CompositeFilter convertToFilter(D domain) {
        return null;
    }
    
    public D toDomain(Entity from) {
        final D domain = createDomain();
        domain.setId(from.getKey().getId());
        copyEntityToDomain(from, domain);
        return domain;
    }
    public FullEntity toEntity(D from) {
        final IncompleteKey key = keyFactory.newKey(from.getId());
         Builder<IncompleteKey> builder = FullEntity.newBuilder(key);
        
         
        //convert domain to EntityE
        copyDomainToEntity(from, builder);
      
        return builder.build();
    }
    public abstract void copyDomainToEntity(D from, Builder<IncompleteKey> to); 
    public abstract void copyEntityToDomain(com.google.cloud.datastore.Entity from, D to);
}

