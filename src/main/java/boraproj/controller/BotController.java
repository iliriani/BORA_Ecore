package boraproj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import RefClasses.NLModeler;
import boraproj.services.BotService;

@RestController
public class BotController {
	
	@Autowired
	BotService botService;
	
	
	public BotController() {
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="getNLString")
//	public LinkedList<String> getModels(@RequestParam String nlString) {
	public List<List<NLModeler>> getModels(@RequestParam String nlString) {
		
		return Arrays.asList(botService.getMatchingModels(nlString));
	}
	

}
