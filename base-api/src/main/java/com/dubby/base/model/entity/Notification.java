package com.dubby.base.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BASE_NOTIFICATION")
public class Notification implements HasSerializableEntity, HasCreateEntity {
	
	private static final long serialVersionUID = 1L;

	private Long id;

    private Boolean isArchived = false;

    private Boolean isRead = false;

    private String message;
	
    private String receiver;

    private String createBy;
	
	private Date createDate;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

    @Column(name="IS_ARCHIVED")
    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    @Column(name="IS_READ")
    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    @Column(name="MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name="RECEIVER")
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

    @Override
    @Column(name="CREATE_BY")
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;

    }

    @Override
    @Column(name="CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Notification rhs = (Notification) obj;

        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(isArchived, rhs.isArchived)
                .append(isRead, rhs.isRead)
                .append(message, rhs.message)
                .append(receiver, rhs.receiver)
                .append(createBy, rhs.createBy)
                .append(createDate, rhs.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isArchived != null ? isArchived.hashCode() : 0);
        result = 31 * result + (isRead != null ? isRead.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
