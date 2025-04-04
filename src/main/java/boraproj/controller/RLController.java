package boraproj.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boraproj.services.AddWeights;

@RestController
public class RLController {
	
	@Autowired
	AddWeights weights;
	
	public RLController() {
		weights = new AddWeights();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="getSelectedClasses")
	public void getSelectedClasses(@RequestParam String cl, @RequestParam ArrayList<String> selected_classes) {
		
		weights.updateWeight(cl, selected_classes);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="undoUpdateWeights")
	public void undoUpdateWeights(@RequestParam String cl, @RequestParam ArrayList<String> selected_classes) {
		
		weights.undoUpdateWeight(cl, selected_classes);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="getSelectedAttributes")
	public void getSelectedAttributes(@RequestParam ArrayList<String> attributes) {
		
	}

}
