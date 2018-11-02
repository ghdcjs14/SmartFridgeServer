package ac.kaist.smartfridge.ui.fridgecam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/FridgeCam")
public class FridgeCamController {
	
	@RequestMapping(value="/showImage", method=RequestMethod.GET)
	public ModelAndView getItemListView(HttpServletRequest request,
			HttpServletResponse response, Model model) {
        
		ModelAndView view = null;
		
		try {
			view = new ModelAndView();

			view.setViewName("fridgeCam");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
		
}
