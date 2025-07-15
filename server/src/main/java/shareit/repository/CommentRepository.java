package shareit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shareit.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
