package com.james.LMS.repository;

import com.james.LMS.entity.UserGiven;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGivenRepository extends JpaRepository<UserGiven, Long> {}
