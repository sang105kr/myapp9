package com.kh.app3.domain.covid.svc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@Service
public class CovidSVCImpl2 implements CovidSVC{
  @Override
  public Response getCovidInfo(String sdate, String edate) {

    Response res = null;

    try {

      StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson"); /*URL*/
      urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=bJ0AcEWnYARdHMe24EsPd77ralP%2BiRWLuhIeWgoIBgM%2F4dqlAgbS%2FilwgSiZkbkL9ojCBQHuEZI2TtoMqYzRhA%3D%3D"); /*Service Key*/
      urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
      urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
      urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode(sdate, "UTF-8")); /*검색할 생성일 범위의 시작*/
      urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode(edate, "UTF-8")); /*검색할 생성일 범위의 종료*/


      RestTemplate rt = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-type", "application/json");
      MultiValueMap<String, String> pararms = new LinkedMultiValueMap<>();
      pararms.add("serviceKey","=bJ0AcEWnYARdHMe24EsPd77ralP%2BiRWLuhIeWgoIBgM%2F4dqlAgbS%2FilwgSiZkbkL9ojCBQHuEZI2TtoMqYzRhA%3D%3D");
      pararms.add("pageNo", "1");
      pararms.add("numOfRows", "10");
      pararms.add("startCreateDt", sdate);
      pararms.add("endCreateDt", edate);
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(pararms, headers);

      ResponseEntity<String> response = rt.exchange(
          urlBuilder.toString(),
          HttpMethod.GET,
          request,
          String.class
      );

      res = parser(response.getBody());
      log.info("res={}",res);
      log.info("totalCount={}",res.getBody());
    }catch (Exception e){
      e.printStackTrace();
    }

    return res;
  }

  private Response parser(String xmlStr) {
    ObjectMapper xmlMapper = new XmlMapper();
    Response response = null;
    try {
      response = xmlMapper.readValue(xmlStr,Response.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return response;
  }
}
