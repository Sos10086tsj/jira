/**
 * 
 */
package com.chinesedreamer.jira.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ID id;

	@Version
	@Column(name = "optlock")
	private Long version = 0l;

	@Override
	public ID getId() {
		return id;
	}

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
	public void setId(ID id) {
		this.id = id;
	}
    
}
