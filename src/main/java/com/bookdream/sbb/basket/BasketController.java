package com.bookdream.sbb.basket;

import java.lang.reflect.Type;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bookdream.sbb.prod.Prod_Service;
import com.bookdream.sbb.user.UserService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.bookdream.sbb.prod_repo.*;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
	@Autowired
	private final Prod_Service prodService;
	private final BasketService basketService;
	
	@GetMapping("")
	public String list() {      
		return "basket/list"; 
	}
}