package com.dmi.cloud.datastore;


/**
 * @author Sophea <a href='mailto:smak@dminc.com'> sophea </a>
 * @version $id$ - $Revision$
 * @date 2017
 */
public abstract class AbstractLongEntity extends AbstractCreatedUpdatedEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -91940750750388101L;
    private Long id;

    public AbstractLongEntity() {
    }

    public AbstractLongEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String subString() {
        return String.format("id:%d", id);
    }


}
