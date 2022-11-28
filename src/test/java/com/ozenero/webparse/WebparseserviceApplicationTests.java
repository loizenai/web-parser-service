package com.ozenero.webparse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozenero.webparse.constants.Constants;
import com.ozenero.webparse.dto.WebUrls;
import com.ozenero.webparse.service.ParsingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class WebparseserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ParsingService parsingService;

	@Test
	void testcase_1() throws Exception{
		doTestCase_1("https://www.starwars.com/news/everything-we-know-about-the-mandalorian",
				"STAR WARS",
				"Star Wars categorized");
	}

	@Test
	void testcase_2() throws Exception{
		doTestCases("https://www.msn.com/en-nz",
				"MSN New Zealand | latest news, Hotmail");
	}

	@Test
	void testcase_3() throws Exception{
		doTestCases("https://www.newstalkzb.co.nz/news/sport",
				"Sports News");
	}

	@Test
	void testcase_4() throws Exception{
		doTestCases("https://www.glamour.de/artikel/jennifer-lopez-weihnachtspulli-zum-naked-rock",
				"Jennifer Lopez hat gerade einen");
	}

	@Test
	void testcase_5() throws Exception{
		//doTestCases("https://www.bbc.com/",
		doTestCases("https://edition.cnn.com/2022/11/28/china/china-lockdown-protests-covid-explainer-intl-hnk/index.html",
				"China's lockdown protests: What you need to know about the rare mass demonstrations");
	}

	@Test
	void testcase_6() throws Exception{
		doTestCases("https://www3.forbes.com/business/2020-upcoming-hottest-new-vehicles/13/?nowelcome",
				"2020 Upcoming Hottest New Vehicles");
	}

	@Test
	void testcase_7() throws Exception{
		doTestCases("https://www.tvblog.it/post/1681999/valerio-fabrizio-salvatori-gli-inseparabili-chi-sono-pechino-express-2020",
				"Pechino Express 2020");
	}

	@Test
	void testcase_8() throws Exception{
		doTestCases("https://edition.cnn.com/",
				"CNN International - Breaking News");
	}

	private void doTestCase_2(String url, String categorized, String message) throws Exception {
		List<String> urls = Arrays.asList(url);
		WebUrls webUrls = WebUrls.builder()
				.urls(urls)
				.build();

		ResultActions response = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/parse")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(webUrls)));

		Assert.isTrue(response.andReturn()
				.getResponse()
				.getContentAsString().toLowerCase()
				.contains(categorized.toLowerCase()), message);
	}

	private void doTestCase_1(String url, String categorized, String message) throws Exception {
		List<String> urls = Arrays.asList(url);
		WebUrls webUrls = WebUrls.builder()
				.urls(urls)
				.build();

		ResultActions response = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/parse")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(webUrls)));

		Assert.isTrue(response.andReturn()
						.getResponse()
						.getContentAsString().toLowerCase()
						.contains(categorized.toLowerCase()), message);
	}

	private void doTestCases(String url, String text) throws Exception {
		List<String> urls = Arrays.asList(url);
		WebUrls webUrls = WebUrls.builder()
				.urls(urls)
				.build();

		ResultActions response = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/parse")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(webUrls)));

		// then - verify the output
		response.andExpect(status().isOk());

		String strResponse = response.andReturn()
				.getResponse()
				.getContentAsString();

		Assert.isTrue(strResponse.contains(text), "Response contains the text = " + text);
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
