package lv.tele2ssc.bookshelf;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository works directly with a table.
 * It has common operation (like selection by id, saving, 
 * counting etc.) defined in CrudRepository already
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    
    @Override
    List<Book> findAll();
    
    @Query("SELECT b FROM Book b WHERE"
            + " LOWER(b.title) LIKE %?1%"
            + " OR LOWER(b.description) LIKE %?1%"
            + " OR LOWER(b.author) LIKE %?1%")
    List<Book> findByTerm(String term);
}
