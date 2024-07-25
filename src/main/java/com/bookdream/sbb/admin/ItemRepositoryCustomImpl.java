//package com.bookdream.sbb.admin;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.jsoup.internal.StringUtil;
//import org.springframework.data.domain.Pageable;
//import org.thymeleaf.util.StringUtils;
//
//import com.mysql.cj.QueryResult;
//
//import jakarta.persistence.EntityManager;
//import ognl.BooleanExpression;
//
//// ItemRepositoryCustom 상속받기
//public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
//	
//	private JPAQueryFactory queryFactory;
//	
//	public ItemRepositoryCustomImpl(EntityManager em) {
//		this.queryFactory = new JPAQueryFactory(em);
//	}
//	
//	private BooleanExperssion searchSellStatusEq(ItemSellStatus searchSellStatus) {
//		LocalDateTime deteTime = LocalDateTime.now();
//		
//		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
//			return null;
//		} else if(StringUtils.equals("id", searchDateType)) {
//			dateTime = dateTime.minusDays(1);
//		} else if(StringUtils.equals("1w", searchDateType)) {
//			dateTime = deteTime.minusWeeks(1);
//		} else if(StringUtils.equals("6m", searchDateType)) {
//			dateTime = dateTime.minusMonths(6);
//		}
//		
//		return QItem.item.regTime.after(dateTime);
//	}
//	
//	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
//		
//		if(StringUtil.equals("itemNm", searchBy)) {
//			return QItem.item.itemNm.like("%" + searchQuery + "%");
//		} else if(StringUtils.equals("createBy", serachBy)) {
//			return QItem.item.createdBy.like("%" + searchQuery + "%");
//		}
//		
//		return null;
//	}
//	
//	@Override
//	public Page<Item> getAdminItemPage(ItemSearchDto, Pageable pageable) {
//		QueryResult<Item> results = queryFactory
//				.selectFrom(QItem.item)
//				.where(regDtsAfter(itemSearchDto.getSearchDateType())),
//					searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//					searchByLike(itemSearchDto.getSearchSellStatus()),
//							itemSearchDto.getSearchQuery()))
//				.orderBy(QItem.item.id.desc())
//				.offset(pageable.getOffset())
//				.limit(pageable.getPageSize())
//				.fetchResults();
//				
//		List<Item> content = results.getResults();
//		long total = results.getTotal();
//		return new PageImpl<>(content, pageable, total);
//	}
//}
//
