package com.editorassistant.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.editorassistant.entity.Event;

public interface EventRepository extends JpaRepository<Event, BigInteger> {

}
