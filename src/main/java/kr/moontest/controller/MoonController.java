package kr.moontest.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Controller
public class MoonController {

	private final String serviceKey = "e8eZUMOFe2kXFaYaiTcjiG%2B4IJPnI9BNp5heQfWMYJES06yVG9L9h7fAO%2FKEQtCMH%2F0bsx%2FufeaFAC3Y5tyfyQ%3D%3D";

	@GetMapping("/")
	public String home() throws IOException, SAXException, ParserConfigurationException {

		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/AstroEventInfoService/getAstroEventInfo"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode("2022", "UTF-8")); /*연*/
		urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode("05", "UTF-8")); /*월*/
		urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());

		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(sb.toString())));

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("item");
//		System.out.println("----------------------------");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
//			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode.getChildNodes();
				System.out.println("Astro Event : " + eElement.getElementsByTagName("astroEvent").item(0).getTextContent());
				System.out.println("Astro Time : " + eElement.getElementsByTagName("astroTime").item(0).getTextContent());
				System.out.println("Astro Title : " + eElement.getElementsByTagName("astroTitle").item(0).getTextContent());
				System.out.println("Locdate : " + eElement.getElementsByTagName("locdate").item(0).getTextContent());
				System.out.println("Seq : " + eElement.getElementsByTagName("seq").item(0).getTextContent());
			}
		}

		return "index";
	}


	@GetMapping("/moon")
	public String moon() throws IOException, ParserConfigurationException, SAXException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/LunPhInfoService/getLunPhInfo"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode("2022", "UTF-8")); /*연*/
		urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode("05", "UTF-8")); /*월*/
		urlBuilder.append("&" + URLEncoder.encode("solDay","UTF-8") + "=" + URLEncoder.encode("22", "UTF-8")); /*일*/
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());

		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(sb.toString())));

		// root tag
		doc.getDocumentElement().normalize();
//		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("item");
//		System.out.println("----------------------------");
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			//			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode.getChildNodes();
				System.out.println("Lunar Age : " + eElement.getElementsByTagName("lunAge").item(0).getTextContent());
				System.out.println("Solar Day : " + eElement.getElementsByTagName("solDay").item(0).getTextContent());
				System.out.println("Solar Month : " + eElement.getElementsByTagName("solMonth").item(0).getTextContent());
				System.out.println("Solar Week : " + eElement.getElementsByTagName("solWeek").item(0).getTextContent());
				System.out.println("Solar Year : " + eElement.getElementsByTagName("solYear").item(0).getTextContent());
			}
		}

		return "index";
	}


}
