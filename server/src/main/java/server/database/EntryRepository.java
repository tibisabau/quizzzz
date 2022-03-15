package server.database;

import org.springframework.data.jpa.repository.JpaRepository;

import commons.Activity;

public interface EntryRepository extends JpaRepository<Activity, Long> {}
