package fr.robot.twitterClient.service;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.robot.twitterClient.service.exceptions.BusinessException;


/**
 * It is Generic interface for all he services to have CURD operations
 *
 * @author Sparkle
 *
 * @param <T>
 */
public interface GenericService<T> extends Serializable{
	
	public Collection<T> getAll();
	public T saveOrUpdate(T object) throws BusinessException;
	public T get(final Serializable id) throws BusinessException;
	public void delete(final Serializable id) throws BusinessException;
	public Page<T> getAll(Pageable pageable) throws BusinessException;
}
 