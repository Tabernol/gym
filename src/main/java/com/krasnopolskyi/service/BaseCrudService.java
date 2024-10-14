package com.krasnopolskyi.service;

import com.krasnopolskyi.exception.EntityException;
import com.krasnopolskyi.exception.GymException;

/**
 * Interface unions base crud operations
 *
 * @param <R> represents response to user
 * @param <P> represents parameter as request
 */
public interface BaseCrudService <R, P>{
    R save(P p) throws GymException;
    R findById(Long id) throws EntityException;
    R update(P p) throws GymException;

}
