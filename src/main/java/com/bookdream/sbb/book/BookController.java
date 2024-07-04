//<<<<<<< HEAD
////package com.bookdream.sbb.book;
////
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestParam;
////
////import com.bookdream.sbb.admin.AdminService;
////
////import lombok.RequiredArgsConstructor;
////
////@RequiredArgsConstructor
////@RequestMapping
////@Controller
////public class BookController {
////	
////	private final BookService bookService;
////	private final AdminService adminService; 
////	
////	@GetMapping("/list")
////	public String list(Model model. @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value="kw", defaultValue = "") String kw) {
////		
////		Page<Review>
////	}
////}
//=======
//package com.bookdream.sbb.book;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.bookdream.sbb.admin.AdminService;
//import com.bookdream.sbb.admin.Review;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RequestMapping("/book")
//@Controller
//public class BookController {
//	
//	private final ReviewService reviewService;
//	private final BookService bookService;
//	private final AdminService adminService; 
//	
//	@PreAuthorixe("isAuthenticate()")
//	@PostMapping("/create/{id}")
//	public String create(Model model)
//	
//}
//>>>>>>> branch 'main' of https://github.com/yoki06161/Book-Dream.git
