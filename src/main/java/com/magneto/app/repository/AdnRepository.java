package com.magneto.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magneto.app.domain.Adn;

@Repository
public interface AdnRepository extends JpaRepository<Adn, String[]> {}
