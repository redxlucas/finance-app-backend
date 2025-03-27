package com.ifrs.financeapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
