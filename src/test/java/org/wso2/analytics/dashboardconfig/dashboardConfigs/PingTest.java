package org.wso2.analytics.dashboardconfig.dashboardConfigs;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.swing.JOptionPane;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wso2.analytics.dashboardconfig.DashboardConf;

public class PingTest {

private static String endpointUrl;
	
	@BeforeClass
	public static void beforeClass() {
		endpointUrl = "http://localhost:9763/dashboardConfigs/dashBoardConfigs";
	}
	
	@Test
	public void testPing() throws Exception {
		WebClient client = WebClient.create(endpointUrl + "/metadata/1");
		Response response = client.accept(MediaType.APPLICATION_JSON).get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//		String value = IOUtils.toString((InputStream)response.getEntity());
//		JOptionPane.showMessageDialog(null, "Val: "+value);
	}

}
