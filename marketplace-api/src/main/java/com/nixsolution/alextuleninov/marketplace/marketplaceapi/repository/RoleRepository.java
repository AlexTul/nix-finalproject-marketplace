/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.repository;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for working with the repository of Role.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
