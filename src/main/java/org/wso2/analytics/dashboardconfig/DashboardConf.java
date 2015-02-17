package org.wso2.analytics.dashboardconfig;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.wso2.carbon.authenticator.stub.AuthenticationAdminStub;

import org.wso2.carbon.user.api.*;
import org.wso2.carbon.context.*;

// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
@Path("/dashBoardConfigs")
public class DashboardConf {

	PageMetaBean metaData = new PageMetaBean();
	WidgetBean widget = new WidgetBean();
	DashboardBean dashboard = new DashboardBean();
	ContentBean content = new ContentBean();

	@GET
	@Path("/metadata/")
	@Produces(MediaType.APPLICATION_JSON)
	public PageMetaBean getPageMetaData() {

		// PageMetaBean metaData = new PageMetaBean();
		// metaData.setListElement(0, "tab1");
		// metaData.setListElement(1, "tab2");
		// metaData.setListElement(2, "tab3");
		// metaData.setListElement(3, "tab6");
		// this.metaData = metaData;
		return this.metaData; // TODO- add response type return
	}

	@PUT
	@Path("/metadata/")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes("application/json")
	public Response setPageMetaData(PageMetaBean beans) { // TODO- edit
														  // thismethod

		System.out.println("Method Accessed from outside url");

		if (beans != null) {
			// this.metaData=null;
			this.metaData = beans;
		}
		return Response.ok(metaData).type(MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Path("/widget/")
	@Produces(MediaType.APPLICATION_JSON)
	public WidgetBean getWidget() {
		return this.widget;
	}

	@POST
	@Path("widget")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setWidget(WidgetBean widget) {

		if (metaData != null) {
			this.widget = widget;
		}
		return Response.ok(widget).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	@GET
	@Path("/dashboard/")
	@Produces(MediaType.APPLICATION_JSON)
	public DashboardBean getDashboard() {
		return this.dashboard;
	}

	@POST
	@Path("/dashboard/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setDashboard(DashboardBean dashboard) {

		if (dashboard != null) {
			this.dashboard = dashboard;
		}
		return Response.ok(dashboard).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ContentBean getContent() {
		return this.content;
	}

	@POST
	@Path("/content/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setContent(ContentBean content) {

		if (dashboard != null) {
			this.content = content;
		}
		return Response.ok(content).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	@POST
	@Path("/login/")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response authenticateUser(String input) {

		/**
		 * Server url of the WSO2 Carbon Server
		 */
		String SEVER_URL = "https://localhost:9443/services/";

		/**
		 * User Name to access WSO2 Carbon Server
		 */
		String USER_NAME = "admin";

		/**
		 * Password of the User who access the WSO2 Carbon Server
		 */
		String PASSWORD = "admin";

		/**
		 * trust store path. this must contains server's certificate.
		 */
		String trustStore =
		                    System.getProperty("user.dir") + File.separator + "repository" + File.separator + "resources" +
		                            File.separator + "security"+ File.separator+"wso2carbon.jks";
		
//		trustStore="/home/dodan/Desktop/svn/user-admin-client/src/main/resources/wso2carbon.jks";

		System.out.println(trustStore);
		/**
		 * Call to https://localhost:9443/services/ uses HTTPS protocol.
		 * Therefore we to validate the server certificate. The server
		 * certificate is looked up in the
		 * trust store. Following code sets what trust-store to look for and its
		 * JKs password.
		 * Note : The trust store should have server's certificate.
		 */

		System.setProperty("javax.net.ssl.trustStore", trustStore);

		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

		/**
		 * Axis2 configuration context
		 */
		ConfigurationContext configContext;

		try {

			/**
			 * Create a configuration context. A configuration context contains
			 * information for
			 * axis2 environment. This is needed to create an axis2 client
			 */
			configContext =
			                ConfigurationContextFactory.createConfigurationContextFromFileSystem(null,
			                                                                                     null);

			/**
			 * creates UserAdminClient instance
			 */
			UserAdminClient sampleUserAdminClient = new UserAdminClient(configContext, SEVER_URL);

			/**
			 * User is authenticate with the WSO2 Carbon Server
			 */
			if (sampleUserAdminClient.authenticate(USER_NAME, PASSWORD)) {
				/**
				 * When authentication is succeed.
				 */
				System.out.println("User is successfully authenticated");

				/**
				 * Do any thing with user admin API. you can list user. add
				 * users, roles, assignments...
				 * Here as an example just have implemented list user operation.
				 */
				String[] users = sampleUserAdminClient.getAllUserNames();

				if (users != null) {
					System.out.println("Listing user names of Carbon server...... ");
					for (String user : users) {
						System.out.println("User Name : " + user);
					}
				}

			} else {
				/**
				 * When authentication is failed log error
				 */
				System.out.println("User can not be authenticated");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(input).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}
