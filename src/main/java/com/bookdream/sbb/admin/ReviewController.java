package com.bookdream.sbb.admin;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookdream.sbb.user.UserCreateForm;
import com.bookdream.sbb.user.UserService;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
	private final AdminService userService;
	
	@GetMapping("/admin")
	public String sinup() {
		return "/admin";
	}
}
