package crud.backend;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "Title is required")
    @Size(min = 3, max = 50, message = "title must be longer than 3 and less than 40 characters")
    private String title;
    
    @Size(min = 3, max = 50, message = "author must be longer than 3 and less than 40 characters")
    private String author;
    
    @Size(min = 3, max = 50, message = "publisher must be longer than 3 and less than 40 characters")
    private String publisher;

	@NotNull(message = "Date is required")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
	private String code;
	private String location;

    
    public Book() {
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
