package demo;


import static junit.framework.TestCase.assertNotNull;

import junit.framework.Assert;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestTemplateTest {
  @Test
  public void getTest(){
    String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";
    RestTemplate restTemplate= new RestTemplate();
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    String result = restTemplate.getForObject(url, String.class, "SpringSource");
    assertNotNull(result);
  }

  @Test
  public void getProcessInstances(){
    String url = "http://localhost:9000/activiti/service/runtime/process-instances/";
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(
        new AuthScope(null, -1),
        new UsernamePasswordCredentials("kermit", "kermit"));
    HttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
    RestTemplate restTemplate=new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

    String result = restTemplate.getForObject(url, String.class);
    Assert.assertNotNull(result);
  }
  @Test
  public void postProcessInstance(){
    String url = "http://localhost:9000/activiti/service/runtime/process-instances/";
    CredentialsProvider credsProvider = new BasicCredentialsProvider();
    credsProvider.setCredentials(
        new AuthScope(null, -1),
        new UsernamePasswordCredentials("kermit", "kermit"));
    HttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
    RestTemplate restTemplate=new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("username", "test");
    map.add("password", "test123");
    map.add("id", "1234");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

//    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//    messageConverters.add(new MappingJacksonHttpMessageConverter());
//    messageConverters.add(new FormHttpMessageConverter());
//    restTemplate.setMessageConverters(messageConverters);

    String result = restTemplate.postForObject(url, request, String.class);
    Assert.assertNotNull(result);
  }
}
