package lv.tele2ssc.bookshelf;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity // marking class as an entity (mapped to DB table)
@Table(name = "user") // table should have specifiec name
public class User implements Serializable {
    
    /**
     * id of the entity
     */
    @Id // required to mark a field as primary key
    @GeneratedValue // primary key is autoincrement
    @Column(name = "id") // column in the table should have specified name
    private Long id;
    
    @Column(name = "full_name")
    @NotEmpty
    private String fullName;
    
    @Email // validation column's value should be a vaild email
    @NotEmpty
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    @Length(min = 5)
    private String password;
    
    @Transient // this field will not be mapped to database (but will be used in controller)
    private String password2;
    
    @ManyToMany // many-to-many relationship
    @JoinTable(name="user_role", // middle table's name  
            joinColumns = @JoinColumn(name = "user_id"), // column in middle table for User id
            inverseJoinColumns = @JoinColumn(name = "role_id")) // column in middle table for Role id
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    
    
}
