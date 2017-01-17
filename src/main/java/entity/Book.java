package entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Book {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    private String author;

    private String title;

    public Book() {
    }

    @Override
    public String toString() {
        return author + " \"" + title + "\"";
    }
}
