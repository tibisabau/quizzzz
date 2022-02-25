package server.database;


import org.springframework.data.jpa.repository.JpaRepository;

import commons.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}