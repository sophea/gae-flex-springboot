package com.dmi.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.dmi.cloud.datastore.DaoGaeImpl;
import com.dmi.domain.DCategory;
import com.google.cloud.datastore.FullEntity.Builder;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
/**
 * @author Sophea <a href='mailto:sopheamak@gmail.com'> sophea </a>
 * @version $id$ - $Revision$
 * @date 2017
 */
@Repository("categoryDao")
public class CategoryDaoImpl extends DaoGaeImpl<DCategory> {

    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public CategoryDaoImpl() {
        super(DCategory.class);
    }


    @Override
    public void copyEntityToDomain(com.google.cloud.datastore.Entity from, DCategory to) {
        if (from.contains(COLUMN_NAME_NAME)) {
            to.setName(from.getString(COLUMN_NAME_NAME));
        }
        if (from.contains(COLUMN_NAME_DESCRIPTION)) {
            to.setDescription(from.getString(COLUMN_NAME_DESCRIPTION));
        }
        if (from.contains(DCategory.COLUMN_NAME_CREATEDDATE)) {
            to.setCreatedDate(from.getDateTime(DCategory.COLUMN_NAME_CREATEDDATE).toDate());
        }
    }

    @Override
    public void copyDomainToEntity(DCategory from, Builder<IncompleteKey> to) {
        to.set(COLUMN_NAME_NAME, from.getName());
        to.set(COLUMN_NAME_DESCRIPTION, from.getDescription());
        to.set(DCategory.COLUMN_NAME_CREATEDDATE, com.google.cloud.datastore.DateTime.copyFrom(new Date()));
    }

    @Override
    public CompositeFilter convertToFilter(DCategory cat) {
        if (cat == null) {
            return null;
        }
        CompositeFilter compositeFilter = null;

        PropertyFilter f = null;
        if (cat.getName() != null) {
            f = PropertyFilter.eq(COLUMN_NAME_NAME, cat.getName());
            compositeFilter = CompositeFilter.and(f);
        }
        if (cat.getDescription() !=null) {
            compositeFilter = CompositeFilter.and(f, PropertyFilter.eq(COLUMN_NAME_DESCRIPTION, cat.getDescription()));

        }
        LOG.debug("filter {}" ,compositeFilter);

        return compositeFilter;
    }

}
