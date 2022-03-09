package server.database;

import org.springframework.data.jpa.repository.JpaRepository;

import commons.Entry1;

public interface EntryRepository extends JpaRepository<Entry1, Long> {}
