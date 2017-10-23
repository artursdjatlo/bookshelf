package lv.tele2ssc.bookshelf;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // marking class as an entity (mapped to DB table)
@Table(name = "role") // table should have specifiec name
public class Role implements Serializable {
    
    /**
     * id of the entity
     */
    @Id // required to mark a field as primary key
    @GeneratedValue // primary key is autoincrement
    @Column(name = "id") // column in the table should have specified name
    private Long id;
    
    @Column(name = "role_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
