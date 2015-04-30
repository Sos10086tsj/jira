/**
 * 
 */
package com.chinesedreamer.jira.base.logic.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.base.model.AbstractEntity;
import com.chinesedreamer.jira.base.repository.BaseRepository;

/**
 * @author dev-laiting
 *  
 */
public class BaseLogicImpl<M extends AbstractEntity<?>, ID extends Serializable>
		implements BaseLogic<M, ID> {
 
	protected BaseRepository<M, ID> baseRepository;

	@Autowired
    public void setBaseRepository(BaseRepository<M, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public M save(M m) {
        return baseRepository.save(m);
    }

    @Override
    public M update(M m) {
        return baseRepository.save(m);
    }

    @Override
    public void delete(ID id) {
        baseRepository.delete(id);
    }

    @Override
    public void delete(M m) {
        baseRepository.delete(m);
    }

    @Override
    public M findOne(ID id) {
        return baseRepository.findOne(id);
    }

    @Override
    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    @Override
    public long count() {
        return baseRepository.count();
    }

	@Override
	public Specification<M> getQuerySpecification(M entity, Root<M> root,CriteriaBuilder cb) {
		Specification<M> condition = new Specification<M>() {
			@Override
			public Predicate toPredicate(Root<M> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> pds = new ArrayList<Predicate>();
				return cb.and(pds.toArray(new Predicate[] {}));
			}
		};
		return condition;
	}

}
