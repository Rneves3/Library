
package crud.backend;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JournalRepository extends JpaRepository<Journal, Long> {
    
    /* A version to fetch List instead of Page to avoid extra count query. */
    List<Journal> findAllBy(Pageable pageable);
    
    List<Journal> findByTitleLikeIgnoreCase(String titleFilter);
    
    // For lazy loading and filtering
    List<Journal> findByTitleLikeIgnoreCase(String titleFilter, Pageable pageable);
    
    long countByTitleLike(String titleFilter);

}
