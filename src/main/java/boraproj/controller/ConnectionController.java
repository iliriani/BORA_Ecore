package boraproj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boraproj.services.ConnectionService;

@RestController
public class ConnectionController {
	
	@Autowired
	ConnectionService conn_service;
	
	public ConnectionController() {
		conn_service = new ConnectionService();
	}
	
	public ConnectionController(String repository) {
		conn_service = new ConnectionService(repository);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="getMultiplicity")
	public String[] getConnectionMultiplicity(@RequestParam String cl1, @RequestParam String cl2){
		return conn_service.getConnectionMultiplicity(cl1, cl2);
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="getConnectionDetails")
	public String[][] getConnectionDetails(@RequestParam String cl1, @RequestParam String cl2){
		return conn_service.getConnectionDetails(cl1, cl2);
	}

}
