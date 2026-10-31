package org.sopt;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;
public interface PostRepository extends JpaRepository<Post,Long>{
 @EntityGraph(attributePaths = "author") Page<Post> findAll(Pageable pageable);
 @EntityGraph(attributePaths = "author") Page<Post> findByCategory(Category category,Pageable pageable);
 @Override @EntityGraph(attributePaths = "author") Optional<Post> findById(Long id);
}
