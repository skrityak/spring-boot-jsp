package springbootjsp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity(name = "t_tasks")
public class TTasks implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Size(max = 45)
	private String name;
	@Size(max = 45)
	private String descriptor;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date date_created;
	private Boolean finished;

	public TTasks() {
	}

	public TTasks(String name, String descriptor, Date date_created, Boolean finished) {
		super();
		this.name = name;
		this.descriptor = descriptor;
		this.date_created = date_created;
		this.finished = finished;
	}
}
