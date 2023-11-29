package mardini.project.kaskos.repository;

import mardini.project.kaskos.dto.UserResponse;
import mardini.project.kaskos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);

    public UserResponse findTop1ByEmail(String email);

    public boolean existsByEmail(String email);

    List<UserResponse> findAllProjectedBy();

}
