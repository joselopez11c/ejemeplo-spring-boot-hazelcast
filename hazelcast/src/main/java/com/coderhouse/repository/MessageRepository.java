package com.coderhouse.repository;

import com.coderhouse.model.Message;
import com.coderhouse.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

}
