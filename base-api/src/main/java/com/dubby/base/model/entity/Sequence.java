/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubby.base.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BASE_SEQUENCE")
public class Sequence implements HasSerializableEntity {

	private static final long serialVersionUID = 1L;

    protected String id;

    @Id
    @Column(name = "ID", nullable = false, length = 25)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Long sequence;

    @Column(name = "SEQUENCE", nullable = false)
    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Sequence rhs = (Sequence) obj;

        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(sequence, rhs.sequence)
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).
                append(id).
                append(sequence).
                toHashCode();
    }
}
