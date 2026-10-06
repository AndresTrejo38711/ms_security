package com.uc.ms_security.repository;

import com.uc.ms_security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByUrlAndMethod(String url, String method);

    boolean existsByUrlAndMethodAndIdNot(String url, String method, Long id);
}
