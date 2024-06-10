package com.informix.ecommerce.repository;

import com.informix.ecommerce.entity.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {
}
