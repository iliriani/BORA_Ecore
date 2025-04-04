package boraproj.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import RefClasses.NLModeler;
import boraproj.services.BotService;
import boraproj.services.PersistModel;

public class Importer {
	
	@Autowired
	PersistModel persist_models;
	
	
	public Importer() {
		
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="persistModels")
	public void persist(@RequestParam String name, @RequestParam String body) {
		persist_models.persist(name, body);
	}

}
