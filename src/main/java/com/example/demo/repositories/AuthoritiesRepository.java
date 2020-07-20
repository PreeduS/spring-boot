package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;
import com.example.demo.models.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer>{
    List<Authorities> findAllByUserUsername(String username);
}