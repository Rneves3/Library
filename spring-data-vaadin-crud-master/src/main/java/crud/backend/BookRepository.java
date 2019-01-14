
package crud.backend;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
    
    /* A version to fetch List instead of Page to avoid extra count query. */
    List<Book> findAllBy(Pageable pageable);
    List<Book> findByTitleLikeIgnoreCase(String titleFilter);
    
    // For lazy loading and filtering
    List<Book> findByTitleLikeIgnoreCase(String titleFilter, Pageable pageable);
    
    long countByTitleLike(String titleFilter);

}
