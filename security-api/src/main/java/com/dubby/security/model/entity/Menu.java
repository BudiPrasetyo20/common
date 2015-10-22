package com.dubby.security.model.entity;

import com.dubby.base.model.entity.HasSerializableEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="SEC_MENU")
@ForeignKey(name="none")
@JsonAutoDetect
public class Menu implements HasSerializableEntity{

	private static final long serialVersionUID = 1L;

    protected Long id;
    protected String menuName;
    protected String partialPath;
    protected String rights;
    protected Menu parent;
    protected String type;
    protected Long menuOrder;
    private List<Menu> child= new ArrayList<>();

    @Id
    @Column(name="ID", length = 20)
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "MENU_NAME", length = 100)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name = "PARTIAL_PATH",length = 200)
    public String getPartialPath() {
        return partialPath;
    }

    public void setPartialPath(String partialPath) {
        this.partialPath = partialPath;
    }

    @Column(name = "RIGHTS", length = 200)
    public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT")
    @ForeignKey(name = "none")
    @JsonBackReference("menu-subMenu")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "MENU_ORDER")
    public Long getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Long menuOrder) {
		this.menuOrder = menuOrder;
	}

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent")
    @JsonManagedReference("menu-suMenu")
    public List<Menu> getChild() {
        return child;
    }

    public void setChild(List<Menu> child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }

        Menu rhs = (Menu) obj;

        return new EqualsBuilder()
                .append(id, rhs.id)
                .append(menuName, rhs.menuName)
                .append(partialPath, rhs.partialPath)
                .append(rights, rhs.rights)
                .append(parent, rhs.parent)
                .append(menuOrder, rhs.menuOrder)
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).
                append(id).
                append(menuName).
                append(partialPath).
                append(rights).
                append(parent).
                append(menuOrder).
                toHashCode();
    }
}
