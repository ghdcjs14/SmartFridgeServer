package ac.kaist.smartfridge.ui.itemlist.controller;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.WriteResult;

import ac.kaist.smartfridge.ui.itemlist.service.ItemListService;
import ac.kaist.smartfridge.ui.itemlist.vo.ItemListVO;


@Controller
@RequestMapping(value="/Item")
public class ItemListController {
	
	@Autowired
	private ItemListService itemListService;
	
	@RequestMapping(value="/itemList", method=RequestMethod.GET)
	public ModelAndView getItemListView(HttpServletRequest request,
			HttpServletResponse response, Model model) {
        
		ModelAndView view = null;
		
		try {
			view = new ModelAndView();

			view.setViewName("itemList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	
	@RequestMapping(value = "/getItemList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getItemList(HttpServletResponse response, HttpServletRequest request) {
		
		ModelAndView mv = null;
		List<ItemListVO> dataList = null;
		
		try {
			mv = new ModelAndView();
			mv.setViewName("itemList");
			
			dataList = itemListService.selectItemList();
			
			if(dataList != null) {
		    	for (ItemListVO item : dataList) {
		    		
		    		System.out.print(item.getId());
			        System.out.print(item.getFullCode());
			        System.out.println(item.getItemName());
			    }
		    }
			
			HttpSession session = request.getSession();
			session.setAttribute("dataList", dataList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	@RequestMapping(value="/itemDetail", method=RequestMethod.GET)
	public ModelAndView getItemDetailView(HttpServletRequest request,
			HttpServletResponse response, Model model, @RequestParam String id) {
        
		ModelAndView view = null;
		ItemListVO data = null;
		
		try {
			view = new ModelAndView();
			view.setViewName("itemDetail");
			
			ObjectId oid = new ObjectId(id);
			data = itemListService.selectItem("_id", oid);
		    		
		    System.out.print(data.getId());
			System.out.print(data.getFullCode());
			System.out.println(data.getItemName());
			
			HttpSession session = request.getSession();
			session.setAttribute("data", data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="/itemUpdate", method=RequestMethod.POST)
	public ModelAndView updateItemDetail(HttpServletRequest request,
			HttpServletResponse response, Model model, @RequestParam String id, 
			@RequestParam Double count, @RequestParam String remark) {
        
		ModelAndView view = null;
		WriteResult wr = null;
		
		try {
			view = new ModelAndView();
			view.setViewName("redirect:getItemList");
			
			ObjectId oid = new ObjectId(id);
			ItemListVO vo = new ItemListVO();
			vo.setRemark(remark);
			vo.setCount(count);
			
			wr = itemListService.updateItem("_id", oid, vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="/deleteItem", method=RequestMethod.GET)
	public ModelAndView deleteItem(HttpServletRequest request,
			HttpServletResponse response, Model model, @RequestParam String id) {
        
		ModelAndView view = null;
		WriteResult wr = null;
		
		try {
			view = new ModelAndView();
			view.setViewName("redirect:getItemList");
			
			ObjectId oid = new ObjectId(id);
			
			wr = itemListService.deleteItem("_id", oid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="/insertItem", method=RequestMethod.POST)
	public void insertItem(HttpServletRequest request, HttpServletResponse response, Model model, 
			@RequestParam String fullCode) {
        
		System.out.println("start isertItem");
		System.out.println("fullCode: " + fullCode);
		
		WriteResult wr = null;
		ItemListVO vo = null;
		
		try {
			// fullCode parsing
			if(fullCode != null) {
				vo = new ItemListVO();
				vo.setFullCode(fullCode); // fullCode 넣고 
				
				// GS1 Matrix
				if(fullCode.length() > 13) {		
					StringBuffer ai = new StringBuffer();	// GS1 Application Identifiers
					
					for(int i=0; i<fullCode.length(); i++) {
						
						if("(".equals(fullCode.substring(i,i+1))) {
							for(; i<fullCode.length(); i++) {
								ai.append(fullCode.substring(i,i+1));
								if(")".equals(fullCode.substring(i,i+1))) {
									i++;
									break;
								}
							}
						}
						
						if("(01)".equals(ai.toString())) {
							
							vo.setIndicator(fullCode.substring(i,i+1));
							vo.setMemberOrganization(fullCode.substring(i+1,i+4));
							vo.setCompanyNumber(fullCode.substring(i+4,i+10));
							vo.setItemReference(fullCode.substring(i+10,i+13));
							vo.setVerificationNumber(fullCode.substring(i+13,i+14));
							ai.delete(0, ai.length());
							i += 13;
						} else if("(11)".equals(ai.toString())) {
							
							vo.setManufacturedDate(fullCode.substring(i,i+6));
							ai.delete(0, ai.length());
							i += 5;
							
						} else if("(17)".equals(ai.toString())) {
							vo.setExpirationDate(fullCode.substring(i,i+6));
							ai.delete(0, ai.length());
							i += 5;
						}
					}
					
				// 기존 바코드가 13자리(한국)
				} else if(fullCode.length() == 13) {
					if(!(checkDigit(fullCode).equals(fullCode.substring(12)))) {
						System.out.println("[!!!바코드 판독오류!!!] 바코드의 체크 디지트가 일치하지 않습니다.");
						return;
					}
					
					// 국가코드번호(2~3자리) + 국내기업체 코드번호(3~6자리) + 상품등록번호(3~6자리) + 체크디지트(1자리)
					// 처음 3자리 : 우리나라 880
					vo.setMemberOrganization(fullCode.substring(0, 3));
					// 숫자 1~5,7~8로 시작되는 경우 업체코드 4자리, 6인 경우 4~5자리, 9인 경우 6자리   
					if(fullCode.substring(3,4).equals("9")) {
						vo.setCompanyNumber(fullCode.substring(3,9));		// 업체코드: 6자리
						vo.setItemReference(fullCode.substring(9,12));		// 상품코드: 3자리
						vo.setVerificationNumber(fullCode.substring(12));	// 체크디지트: 1자리
						
					} else if(fullCode.substring(3,4).equals("6")) {
//						String comNum4 = fullCode.substring(3,7);			// 업체코드: 4자리
//						String itemRef5 = fullCode.substring(7,12);			// 상품코드: 5자리
						
						// 일단 업체코드 5자리라고 처
						String comNum5 = fullCode.substring(3,8);			// 업체코드: 5자리
						String itemRef4 = fullCode.substring(8,12);			// 상품코드: 4자리
						
						vo.setVerificationNumber(fullCode.substring(12));	// 체크디지트: 1자리
						
					} else {
						vo.setCompanyNumber(fullCode.substring(3,7));		// 업체코드: 4자리
						vo.setItemReference(fullCode.substring(7,12));		// 상품코드: 5자리
						vo.setVerificationNumber(fullCode.substring(12));	// 체크디지트: 1자리
						
					}
					
					
				} else if(fullCode.length() == 8) {
					if(!(checkDigit(fullCode).equals(fullCode.substring(7)))) {
						System.out.println("[!!!바코드 판독오류!!!] 바코드의 체크 디지트가 일치하지 않습니다.");
						return;
					}
					
					// 국가코드번호(3자리) + 국내기업체 코드번호(3자리) + 상품등록번호(1자리) + 체크디지트(1자리)
					vo.setMemberOrganization(fullCode.substring(0, 3));	// 처음 3자리 : 우리나라 880
					vo.setCompanyNumber(fullCode.substring(3,6));		// 업체코드: 3자리
					vo.setItemReference(fullCode.substring(6,7));		// 상품코드: 1자리
					vo.setVerificationNumber(fullCode.substring(7,8));	// 체크디지트: 1자리
				}
				
				System.out.println(vo.toString());
			}
			
			itemListService.insertItem(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String checkDigit(String fullCode) {
		
		int checkSum = 0;
		int checkDigit = 0;
		int remainder = 0;
		
		//13자리, 8자리 
		// 1단계 : 짝수인 애들 합
		for(int i=0; i<fullCode.length(); i++) {
			if(i%2 == 1) {	
				checkSum += Integer.parseInt(fullCode.substring(i, i+1));
			}
		}
		// 2단계 : 1단계 결과에 * 3
		checkSum *= 3;
		
		// 3단계 : 홀수인 애들 합
		for(int i=0; i<fullCode.length()-1; i++) {
			if(i%2 != 1) {	
				checkSum += Integer.parseInt(fullCode.substring(i, i+1));
			}
		}
		
		// 4단계 :3단계 결과에 10의 배수가 되도록 더해진 최소수치(0이상의 양수)가 체크 디지트
		// ex) checkSum = 127, checkDigit = 3
		remainder = checkSum % 10;
		checkDigit = (10 - remainder) % 10;
		
		System.out.println("checkDigit = " + checkDigit);
		
		return Integer.toString(checkDigit);
	}
}
