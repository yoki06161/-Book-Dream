package com.bookdream.sbb.basket;

import java.lang.reflect.Type;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("")
	public String list(HttpSession session, Model model) {
		 // formData 배열 가져오기
	    String formDataListJson = (String) session.getAttribute("formDataList");
	    if (formDataListJson != null && !formDataListJson.isEmpty()) {
	        Gson gson = new Gson();
	        Type formDataListType = new TypeToken<List<FormData>>() {}.getType();
	        List<FormData> formDataList = gson.fromJson(formDataListJson, formDataListType);

	        // 모델에 formDataList 추가
	        model.addAttribute("formDataList", formDataList);
	    }

	    // book_id 배열 가져오기
	    String bookIdListJson = (String) session.getAttribute("bookIdList");
	    if (bookIdListJson != null && !bookIdListJson.isEmpty()) {
	        Gson gson = new Gson();
	        Type bookIdListType = new TypeToken<List<String>>() {}.getType();
	        List<String> bookIdList = gson.fromJson(bookIdListJson, bookIdListType);

	        // 모델에 bookIdList 추가
	        model.addAttribute("bookIdList", bookIdList);
	    }

		return "basket/list";
	}
}