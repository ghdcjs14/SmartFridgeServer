package ac.kaist.smartfridge.ui.statistics.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mongodb.WriteResult;

import ac.kaist.smartfridge.ui.itemlist.service.ItemListService;
import ac.kaist.smartfridge.ui.itemlist.vo.ItemListVO;
import ac.kaist.smartfridge.ui.statistics.service.StatisticsService;


@Controller
@RequestMapping(value="/Statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping(value="/epcisEvent", method=RequestMethod.GET)
	public ModelAndView getItemListView(HttpServletRequest request,
			HttpServletResponse response, Model model) {
        
		ModelAndView view = null;
		
		try {
			view = new ModelAndView();

			view.setViewName("statistics");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value = "/getOpenCloseEPCISEvent", method = RequestMethod.GET)
	@ResponseBody
	public void getOpenCloseEPCISEvent(HttpServletResponse response, HttpServletRequest request) {
		
		List<ItemListVO> dataList = null;
		
		try {
			
			dataList = statisticsService.selectItemList();
			
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
	}
	
}
