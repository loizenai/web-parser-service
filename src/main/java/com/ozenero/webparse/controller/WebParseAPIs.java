package com.ozenero.webparse.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozenero.webparse.dto.WebContents;
import com.ozenero.webparse.dto.WebUrls;
import com.ozenero.webparse.service.ParsingService;

@RestController
@RequestMapping("/api")
public class WebParseAPIs{

	ParsingService parsingService;

	public WebParseAPIs(ParsingService parsingService) {
		this.parsingService = parsingService;
	}
	
	@PostMapping("/parse")
	public WebContents parseWeb(@RequestBody WebUrls request) throws Exception {
		return parsingService.parse(request);
	}
}