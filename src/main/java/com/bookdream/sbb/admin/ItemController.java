//package com.bookdream.sbb.admin;
//
//import java.util.List;
//
//import org.hibernate.query.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//public class ItemController {
//    private final ItemService itemService;
//    @GetMapping(value = "/admin/item/new")
//    public String itemForm(Model model){
//        model.addAttribute("itemFormDto", new ItemFormDto());
//        return "item/itemForm";
//    }
//
//    @PostMapping(value = "/admin/item/new")
//    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
//                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
//
//        if(bindingResult.hasErrors()){
//            return "item/itemForm";
//        }
//
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }
//
//        try {
//            itemService.saveItem(itemFormDto, itemImgFileList);
//        } catch (Exception e){
//            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
//            return "item/itemForm";
//        }
//
//        return "redirect:/";
//    }
//
//    @GetMapping(value = "/admin/item/{itemId}")
//    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
//
//        try {
//            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
//            model.addAttribute("itemFormDto", itemFormDto);
//        } catch(EntityNotFoundException e){
//            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
//            model.addAttribute("itemFormDto", new ItemFormDto());
//            return "item/itemForm";
//        }
//
//        return "item/itemForm";
//    }
//
//    @PostMapping(value = "/admin/item/{itemId}")
//    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
//                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
//        if(bindingResult.hasErrors()){
//            return "item/itemForm";
//        }
//
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "item/itemForm";
//        }
//
//        try {
//            itemService.updateItem(itemFormDto, itemImgFileList);
//        } catch (Exception e){
//            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
//            return "item/itemForm";
//        }
//
//        return "redirect:/";
//    }
//
//    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
//    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
//        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
//
//        // 조회한 상품 데이터 정보 뷰에 전달 
//        model.addAttribute("items", items);
//        model.addAttribute("itemSearchDto", itemSearchDto);
//        // 최대 5개 이동할 페이지 번호
//        model.addAttribute("maxPage", 5);
//
//        return "item/itemMng";
//    }
//
//}