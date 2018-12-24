package ac.kaist.smartfridge.ui.itemlist.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
			        System.out.print(item.getExpirationDate());
			        System.out.println(item.getItemName());
			        int diffDays = getDiffOfDate(item.getExpirationDate());
			        if(diffDays >= 0) { 
			        	item.setRemainingDate(Integer.toString(diffDays));
			        } else {
			        	item.setRemainingDate("유통기한 만료");
			        }
			        
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
			System.out.println(data.getImageLinkURL());
			
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
			@RequestParam String itemName, @RequestParam Double count, 
			@RequestParam String expirationDate, @RequestParam String remark) {
        
		ModelAndView view = null;
		WriteResult wr = null;
		
		try {
			view = new ModelAndView();
			view.setViewName("redirect:getItemList");
			
			ObjectId oid = new ObjectId(id);
			ItemListVO vo = new ItemListVO();
			
			vo.setItemName(itemName);
			vo.setExpirationDate(expirationDate);
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
	
	@RequestMapping(value="/insertVoiceItem", method=RequestMethod.POST)
	public void insertVoiceItem(HttpServletRequest request, HttpServletResponse response, Model model, 
	      @RequestParam String voiceItemName) {
	        
	   System.out.println("start insertItem");
	   System.out.println("voiceItemName: " + voiceItemName);
	      
	   ItemListVO vo = null;
	      
	   try {
	      if(voiceItemName != null) {
	    	  String date = new SimpleDateFormat("yyyy-MM-dd").format(
		        		 new Date(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000)));
	    	 
	         vo = new ItemListVO();
	         vo.setItemName(voiceItemName);
	         vo.setExpirationDate(date);	//	15일 뒤로 세팅
	         vo.setManufacturedDate(date);	//	15일 뒤로 세팅
	         
	         itemListService.insertItem(vo);
	         
	      }
	      
	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	}
	
	@RequestMapping(value="/insertLocalGS1SourceItem", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void insertLocalGS1SourceItem(HttpServletRequest request, HttpServletResponse response, Model model, 
	      @RequestBody JSONObject jsonLocalGS1SourceItemObj) {
	        
	   System.out.println("start insertLocalGS1SourceItem");
	   if(jsonLocalGS1SourceItemObj != null) {
		   System.out.println("jsonObj: " + jsonLocalGS1SourceItemObj.toString());
	   }

	   ItemListVO vo = null;
	   JSONObject jsonObject = null;
	      
	   try {
	      if(jsonLocalGS1SourceItemObj != null) {
	    	  jsonObject = jsonLocalGS1SourceItemObj;
	    	  vo = new ItemListVO();
	    	  
	    	  setLocalGS1ProductInfo(jsonObject, vo);
	    	  
	    	  itemListService.insertLocalGS1SourceItem(vo);
	      }
	      
	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	}
	
	@RequestMapping(value="/deleteVoiceItem", method=RequestMethod.POST)
	public void deleteVoiceItem(HttpServletRequest request, HttpServletResponse response, Model model, 
	      @RequestParam String voiceItemName) {
	        
	   System.out.println("start deleteItem");
	   System.out.println("voiceItemName: " + voiceItemName);
	      
	   WriteResult wr = null;
	   
	   try {
	      if(voiceItemName != null) {
	         wr = itemListService.deleteVoiceItem("itemName", voiceItemName);
	      }
	      
	      // vo가 없어서 Service에서 처리
//	      if(wr.wasAcknowledged()) {
//	    	  itemListService.captureEvent("pull", vo);
//	      }
	      
	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	}
	
	@RequestMapping(value="/insertItem", method=RequestMethod.POST)
	public void insertItem(HttpServletRequest request, HttpServletResponse response, Model model, 
			@RequestParam String fullCode) {
        
		System.out.println("start isertItem");
		System.out.println("fullCode: " + fullCode);
		
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
							vo.setGtin(fullCode.substring(i,i+14));
							vo.setIndicator(fullCode.substring(i,i+1));
							vo.setMemberOrganization(fullCode.substring(i+1,i+4));
							vo.setCompanyNumber(fullCode.substring(i+4,i+10));
							vo.setItemReference(fullCode.substring(i+10,i+13));
							vo.setVerificationNumber(fullCode.substring(i+13,i+14));
							ai.delete(0, ai.length());
							
							// Search GS1 Source
							System.out.println(fullCode.substring(i,i+14));
							Boolean bResponseState = requestGS1Source(fullCode.substring(i,i+14), vo);
							if(!bResponseState) {
								searchLocalGS1Source(fullCode.substring(i,i+14), vo);
							}
							
							i += 13;
						} else if("(11)".equals(ai.toString())) {
							vo.setManufacturedDate(changeDateFormat(fullCode.substring(i,i+6)));
							ai.delete(0, ai.length());
							i += 5;
							
						} else if("(17)".equals(ai.toString())) {
							vo.setExpirationDate(changeDateFormat(fullCode.substring(i,i+6)));
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
					vo.setGtin(fullCode);
					
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
					
					// 13자리로 검색
					searchLocalGS1Source(fullCode, vo);
					
					
				} else if(fullCode.length() == 8) {
					if(!(checkDigit(fullCode).equals(fullCode.substring(7)))) {
						System.out.println("[!!!바코드 판독오류!!!] 바코드의 체크 디지트가 일치하지 않습니다.");
						return;
					}
					
					vo.setGtin(fullCode);
					
					// 국가코드번호(3자리) + 국내기업체 코드번호(3자리) + 상품등록번호(1자리) + 체크디지트(1자리)
					vo.setMemberOrganization(fullCode.substring(0, 3));	// 처음 3자리 : 우리나라 880
					vo.setCompanyNumber(fullCode.substring(3,6));		// 업체코드: 3자리
					vo.setItemReference(fullCode.substring(6,7));		// 상품코드: 1자리
					vo.setVerificationNumber(fullCode.substring(7,8));	// 체크디지트: 1자리
					
					// 8자리로 검색
					searchLocalGS1Source(fullCode, vo);
				}
				
				System.out.println(vo.toString());
			}
			
			// 날짜 데이터 없으면 15일 뒤로 세팅 
			if(vo.getManufacturedDate() == null) {
				String date = new SimpleDateFormat("yyyy-MM-dd").format(
		        		 new Date(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000)));
				
				vo.setManufacturedDate(date);
			}
			if(vo.getExpirationDate() == null) {
				String date = new SimpleDateFormat("yyyy-MM-dd").format(
		        		 new Date(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000)));
				
				vo.setExpirationDate(date);
			}
			
			itemListService.insertItem(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getDiffOfDate(String expirationDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long diff = 0;
		long diffDays = 0;
		try {
			Date beginDate = new Date();
			Date endDate = sdf.parse(expirationDate);
			
			diff = endDate.getTime() - beginDate.getTime();
			diffDays = diff / (24 * 60 * 60 * 1000) + 1;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return (int)diffDays;
	}
	
	private String changeDateFormat(String strDate) {
		SimpleDateFormat sdf = null;
		Date date = null;
		
		try {
			sdf = new SimpleDateFormat("yyMMdd");
			date = sdf.parse(strDate);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sdf.format(date); 
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
	
	public boolean requestGS1Source(String gtin, ItemListVO vo) {
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
				
		try {
			url = new URL("https://oliot-tsd.herokuapp.com/product/"+ gtin + "/BasicProductInformation");
			System.out.println("https://oliot-tsd.herokuapp.com/product/"+ gtin +"/BasicProductInformation");
			
			// request
			conn = (HttpURLConnection)url.openConnection();	
			conn.setRequestMethod("GET");
			
			// Response가 다 정상으로 날라옴...
//			responseMessge = conn.getResponseMessage();
//			responseCode = conn.getResponseCode();
//			System.out.println("ResponseCode: " + responseCode);
//			System.out.println("Error: " + conn.getErrorStream());
//			System.out.println("ResponseMessge: " + responseMessge);
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			JSONObject jsonObject = parseJson(br.readLine());
			System.out.println("jsonObject: " + jsonObject);
			
			if(jsonObject != null) {
				setGS1ProductInfo(jsonObject, vo);
				
				return true;
				
			} else {
				return false;
			}
			
		
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		return false;
	}
	
	public JSONObject parseJson(String jsonStr) {
		
		if(jsonStr == null) {
			return null;
		}
		
		JSONParser jsonParser = null;
		JSONObject jsonObject = null;
		
		try {
			jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(jsonStr);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public void setGS1ProductInfo(JSONObject jsonObj, ItemListVO vo) {
		
		JSONArray tempArr = null;
		JSONObject tempObj = null;
		
		try {
			// ProductName Array
			tempArr  = (JSONArray) jsonObj.get("productName");
			tempObj = (JSONObject) tempArr.get(0);
			vo.setItemName(tempObj.get("value").toString());
			
			// BrandNameInformation
			vo.setBrandName(((JSONObject)jsonObj.get("brandNameInformation"))
					.get("brandName").toString());
			
			// productInformationLink Array
			tempArr  = (JSONArray) jsonObj.get("productInformationLink");
			tempObj = (JSONObject) tempArr.get(0);
			vo.setProductInformationLinkURL(tempObj.get("url").toString());
			
			// imageLink Array
			tempArr  = (JSONArray) jsonObj.get("imageLink");
			tempObj = (JSONObject) tempArr.get(0);
			vo.setImageLinkURL(tempObj.get("url").toString());
			
			// PackagingSignatureLine Array
			tempArr  = (JSONArray) jsonObj.get("packagingSignatureLine");
			for(int i=0; i<tempArr.size(); i++) {
 				tempObj = (JSONObject) tempArr.get(i);
				if("DISTRIBUTOR".equals(((JSONObject)tempObj.get("partyContactRoleCode")).get("value").toString())) {
					vo.setPartyContactName(tempObj.get("partyContactName").toString());
					vo.setPartyContactAddress(tempObj.get("partyContactAddress").toString());
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void searchLocalGS1Source(String gtin, ItemListVO vo) {
		
		ItemListVO temp = null;
		try {
			
			temp = itemListService.selectLocalGS1SourceProduct("gtin", gtin);
		    		
		    if(temp != null) {
		    	System.out.print(temp.toString());
		    	
		    	vo.setItemName(temp.getProductName());
		    	vo.setBrandName(temp.getBrandName());
		    	vo.setProductInformationLinkURL(temp.getProductInformationLinkURL());
		    	vo.setImageLinkURL(temp.getImageLinkURL());
		    	vo.setPartyContactName(temp.getPartyContactName());
		    	vo.setPartyContactRoleCode(temp.getPartyContactRoleCode());
		    	vo.setPartyContactAddress(temp.getPartyContactAddress());
		    } else {
		    	vo.setItemName("No Name");
		    }
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLocalGS1ProductInfo(JSONObject jsonObj, ItemListVO vo) {
		
		try {
			vo.setGtin(jsonObj.get("gtin").toString());
			vo.setProductName(jsonObj.get("productName").toString());
			vo.setBrandName(jsonObj.get("brandName").toString());
			vo.setProductInformationLinkURL(jsonObj.get("productInformationLinkURL").toString());
			vo.setImageLinkURL(jsonObj.get("imageLinkURL").toString());
			vo.setPartyContactRoleCode(jsonObj.get("partyContactRoleCode").toString());
			vo.setPartyContactName(jsonObj.get("partyContactName").toString());
			vo.setPartyContactAddress(jsonObj.get("partyContactAddress").toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
