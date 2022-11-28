package com.ozenero.webparse.service;

import com.ozenero.webparse.constants.Constants;
import com.ozenero.webparse.dto.WebText;
import com.ozenero.webparse.utils.LoadingWebPageUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.ozenero.webparse.dto.WebContents;
import com.ozenero.webparse.dto.WebUrls;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ParsingServiceImpl implements ParsingService{

	@Override
	public WebContents parse(WebUrls urlsRequest) throws Exception {
		List<WebText> webs = new ArrayList<WebText>();
		String content = Constants.EMPTY;
		for(String url: urlsRequest.getUrls()) {
			try {
				String html = LoadingWebPageUtil.webLoading(url);
				content = Jsoup.parse(html).text();
			} catch (Exception e) {
				log.error("Error = " + e);
			}

			webs.add(WebText.builder().url(url)
					.content(content)
					.build());
		}

		return WebContents.builder()
						.webs(webs)
						.build();
	}
}
