package server.database;


import commons.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import commons.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}