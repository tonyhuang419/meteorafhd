package tswaterking.waterking.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tswaterking.waterking.entity.Board;
import tswaterking.waterking.service.IBoardService;

@Controller
public class DemoController {

	@Autowired
	@Qualifier("boardService")
	private IBoardService boardService;
	
	@RequestMapping(value = "/waterking/demo.do", method = RequestMethod.POST)
	public String get(String name, Model model) {
		model.addAttribute("name", name);
		boardService.saveOrUpdate(new Board());
		return "demo";
	}
}
