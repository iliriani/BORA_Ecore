package boraproj.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import RefClasses.FormClass;
import RefClasses.NLModeler;
import boraproj.services.FormService;

@RestController
public class FormController {

	@Autowired
	FormService formService;
	
	public FormController() {}
	
	@RequestMapping(method=RequestMethod.POST, value="getFormInfo")
	public List<FormClass> getModels(@RequestParam String cl) {
		
		return Arrays.asList(formService.initialiForm(cl));
	}
	
}
