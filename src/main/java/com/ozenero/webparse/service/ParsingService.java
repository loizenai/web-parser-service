package com.ozenero.webparse.service;

import com.ozenero.webparse.dto.WebContents;
import com.ozenero.webparse.dto.WebUrls;

public interface ParsingService {
	WebContents parse(WebUrls urls) throws Exception;
}
