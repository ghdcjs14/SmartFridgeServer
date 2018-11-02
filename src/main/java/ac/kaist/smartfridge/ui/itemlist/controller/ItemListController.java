package ac.kaist.smartfridge.ui.itemlist.controller;

import java.util.List;
import java.util.Map;

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
        
		WriteResult wr = null;
		ItemListVO vo = null;
		try {
			
			// 개발 해야함  
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
