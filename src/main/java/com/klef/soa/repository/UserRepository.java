
package com.klef.soa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.klef.soa.entity.Role;
import com.klef.soa.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);

    Optional<User> findByContact(String contact);

    List<User> findByRole(Role role);
}
