package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.iTopPerson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class DevelopersInfoController {

	public DevelopersInfoController() {
	}
	
	@GetMapping(value = "/developers")
	public String getiTopInfo(ModelMap model) {
		String url = "http://itop-g7-71-38ac57.appfleet.net//webservices/rest.php?version=1.3&json_data={ \"operation\": \"core/get\", \"class\": \"Person\", \"key\": \"SELECT `Person` FROM Person AS `Person` JOIN Organization AS `Organization` ON `Person`.org_id = `Organization`.id JOIN Organization AS `Organization1` ON `Organization`.parent_id BELOW `Organization1`.id WHERE (`Organization1`.`id` = '2')\", \"output_fields\": \"friendlyname, email, phone\" }";
				
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth("admin", "admin");
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		String developersInfo = response.getBody();
				
		List<iTopPerson> developersInfoList = new ArrayList<iTopPerson>();
		
		String infoSplitted[] = developersInfo.replace("{\"objects\":{", "").split("},");

		Integer numKeyPersona = 2;
		for (int i = 0; i < infoSplitted.length-1; i++) {
			infoSplitted[i]= infoSplitted[i]
					.replace("\"Person::" + numKeyPersona + "\":{\"code\":0,\"message\":\"\",\"class\":\"Person\",\"key\":\"" + numKeyPersona + "\",\"fields\":", "")
					.replace("{", "").replaceAll("}", "").replaceAll("\"", "").replace("friendlyname:", "").replace("email:", "").replace("phone:", "");
			
			String personInfo[] = infoSplitted[i].split(",");
			developersInfoList.add(new iTopPerson(personInfo[0], personInfo[1], personInfo[2]));
			
			numKeyPersona++;
		}
		
		model.put("developersInfoList", developersInfoList);
				
		return "developers/list";
	}
}