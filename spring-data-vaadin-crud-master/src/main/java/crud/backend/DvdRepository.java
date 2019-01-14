
package crud.backend;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DvdRepository extends JpaRepository<Dvd, Long> {

    /* A version to fetch List instead of Page to avoid extra count query. */
    List<Dvd> findAllBy(Pageable pageable);

    List<Dvd> findByTitleLikeIgnoreCase(String titleFilter);

    // For lazy loading and filtering
    List<Dvd> findByTitleLikeIgnoreCase(String titleFilter, Pageable pageable);

    long countByTitleLike(String titleFilter);

}
