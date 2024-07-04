//package com.bookdream.sbb.admin;
//
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.bookdream.sbb.DataNotFoundException;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class AdminService {
//<<<<<<< HEAD
//	private final AdminRepository userRepository;
//	private final PasswordEncoder passwordEncoder;
//	
//	public Admin create(Map<String, String> map) {
//		Admin admin = new Admin();
//		admin.setAdminname(map.get("adminname"));
//		admin.setEmail(map.get("email"));
//		admin.setPassword(passwordEncoder.encode(map.get("password")));
//		this.userRepository.save(admin);
//		return admin;
//	}
//	
//	public Admin getAdmin(String adminname) {
//		Optional<Admin> admin = this.adminRepository.findByadmin(adminname);
//		if(admin.isPresent()) {
//			return Admin()) {
//		} else {
//			throw new DataNotFoundException("admin not found!");
//		}
//	}
//=======
//   private final UserRepository userRepository;
//   private final PasswordEncoder passwordEncoder;
//   
//   public Admin create(Map<String, String> map) {
//      Admin admin = new Admin();
//      admin.setAdminname(map.get("adminname"));
//      admin.setEmail(map.get("email"));
//      admin.setPassword(passwordEncoder.encode(map.get("password")));
//      this.userRepository.save(admin);
//      return admin;
//   }
//   
//   public Admin getAdmin(String adminname) {
//      Optional<Admin> admin = this.adminRepository.findByadmin(adminname);
//      if(admin.isPresent()) {
//         return Admin()) {
//      } else {
//         throw new DataNotFoundException("admin not found!");
//      }
//   }
//>>>>>>> branch 'main' of https://github.com/yoki06161/Book-Dream.git
//}

