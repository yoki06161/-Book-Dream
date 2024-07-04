package com.bookdream.sbb.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.bind.Name;

import com.bookdream.sbb.admin.Admin;
import com.bookdream.sbb.admin.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 300)
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
	private List<Review> reviewList;
	
	@ManyToOne
	private Admin author;
	
	@ManyToMany
	Set<Admin> voter;
}
