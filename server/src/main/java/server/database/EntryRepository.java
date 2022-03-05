package server.database;

import org.springframework.data.jpa.repository.JpaRepository;

import commons.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {}
