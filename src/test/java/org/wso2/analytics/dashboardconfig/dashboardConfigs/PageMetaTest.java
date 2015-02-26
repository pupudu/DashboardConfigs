package org.wso2.analytics.dashboardconfig.dashboardConfigs;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wso2.analytics.dashboardconfig.PageMetaBean;

import sun.print.resources.serviceui;

public class PageMetaTest {

	private static String endpointUrl;

	@BeforeClass
	public static void beforeClass() {
		endpointUrl = "http://localhost:9763/dashboardConfigs/dashBoardConfigs";
	}
	
	@Test
	public void getPageMetaTestForNoContent() throws Exception{
		WebClient client=WebClient.create(endpointUrl+"/metadata/1");
		Response response=client.accept(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus()); //error status 204
	}

	@Test
	public void setPageMetaTest() throws Exception {
		List<Object> providers = new ArrayList<Object>();
	    providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
	    
	    PageMetaBean page=new PageMetaBean();
	    page.setListElement(0, "Dodan");
	    page.setListElement(1, "Nands");
	    
	    WebClient client = WebClient.create(endpointUrl + "/metadata/0", providers);
	    Response response = client.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(page);
	    
	    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	    
	    MappingJsonFactory factory = new MappingJsonFactory();
		JsonParser parser = factory.createJsonParser((InputStream)response.getEntity());
		PageMetaBean output = parser.readValueAs(PageMetaBean.class);
		assertEquals("Dodan", output.getElementTitle(0));
		assertEquals("Nands", output.getElementTitle(1));
		
	}
	
	@Test
	public void getPageMetaTest() throws Exception{
		WebClient client = WebClient.create(endpointUrl+"/metadata/0");
		Response response=client.accept(MediaType.APPLICATION_JSON).get();
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		MappingJsonFactory factory= new MappingJsonFactory();
		JsonParser parser = factory.createJsonParser((InputStream)response.getEntity());
		PageMetaBean output =parser.readValueAs(PageMetaBean.class);
		
		assertEquals("Dodan", output.getElementTitle(0));
		assertEquals("Nands", output.getElementTitle(1));
	}
	
	@Test
	public void deletePageMetaTest() throws Exception{
		WebClient client= WebClient.create(endpointUrl+"/metadata/0");
		Response response= client.delete();
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		WebClient client2= WebClient.create(endpointUrl+"/metadata/1");
		Response response2 = client2.delete();
		
		assertEquals(Response.Status.NOT_MODIFIED.getStatusCode(), response2.getStatus());
	}

}
