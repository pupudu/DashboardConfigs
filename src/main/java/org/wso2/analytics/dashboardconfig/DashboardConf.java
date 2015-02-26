package org.wso2.analytics.dashboardconfig;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.context.RegistryType;
import org.wso2.carbon.registry.api.Registry;
import org.wso2.carbon.registry.api.RegistryException;
import org.wso2.carbon.registry.api.Resource;

import com.sun.xml.bind.v2.runtime.Name;

@Path("/dashBoardConfigs")
public class DashboardConf {

	/** The logger. */
	private Log logger = LogFactory.getLog(DashboardConf.class);

	// PageMetaBean metaData = new PageMetaBean();
	Map<Integer, PageMetaBean> PageMeta = new HashMap<Integer, PageMetaBean>();
	WidgetBean widget = new WidgetBean();
	DashboardBean dashboard = new DashboardBean(); // List of
	                                               // Dashboards(Categories)
	ContentBean content = new ContentBean();

	/* Returns a Json with page meta data for the dashboard:id */
	@GET
	@Path("/metadata/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPageMetaData(@PathParam("id") int dashBoardID) {
		if (logger.isDebugEnabled()) {
			logger.debug("PageMetaData for Dashboard with ID: " + dashBoardID + " Requested");
		}

		try {
			PageMetaBean metaData = PageMeta.get(dashBoardID);
			if (metaData == null) {
				return Response.noContent().build();// 204
			} else {
				return Response.ok(metaData).type(MediaType.APPLICATION_JSON).build();	//200
			}
		} catch (Exception e) {
			logger.error(e);
			return Response.serverError().build();
		}

	}

	/* Replaces Page meta data of the dashboard:id */
	@POST
	@Path("/metadata/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setPageMetaData(@PathParam("id") int dashBoardID, PageMetaBean metaData) {

		if (logger.isDebugEnabled()) {
			logger.debug("PageMetaData for Dashboard with ID: " + dashBoardID + " Requested");
		}

		if (metaData == null) {
			logger.debug("Input Data null, content not added or replaced");
		}

		if (PageMeta.get(dashBoardID) == null) {
			logger.debug("MetaData added to Map with key: " + dashBoardID);
		} else {
			logger.debug("MetaData on Map with key: " + dashBoardID + " Replaced");
		}
		PageMeta.put(dashBoardID, metaData);

		return Response.ok(metaData).type(MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE   //TODO- Get superior Advice on this method structure
	@Path("/metadata/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePageMetaElement(@PathParam("id") int dashboardId){
		
		if(PageMeta.get(dashboardId)==null){
			return Response.notModified().build();
		}else{
			PageMeta.remove(dashboardId);
			return Response.ok().build();
		}
	}

	/**/
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
		return Response.ok(widget).type(MediaType.APPLICATION_JSON).build();
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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(CredentialsBean credentials) {

		boolean status = false; // Authentication status TODO: Replace with a
		                        // DTO, or an OAuth token

		/* Server url of the WSO2 Carbon Server */
		String SEVER_URL = "https://localhost:9443/services/";

		/* User Name to access WSO2 Carbon Server */
		String username = credentials.getUsername();

		/* Password of the User who access the WSO2 Carbon Server */
		String password = credentials.getPassword();

		try {

			/*
			 * configuration context contains information for axis2 environment.
			 * This is needed to create an axis2 client
			 */
			ConfigurationContext configContext =
			                                     ConfigurationContextFactory.createConfigurationContextFromFileSystem(null,
			                                                                                                          null);

			/* creates UserAdminClient instance */
			UserAdminClient sampleUserAdminClient = new UserAdminClient(configContext, SEVER_URL);

			/* User is authenticate with the WSO2 Carbon Server */
			if (status = sampleUserAdminClient.authenticate(username, password)) {

				System.out.println("User is successfully authenticated");

				/**
				 * Do any thing with user admin API. you can list user. add
				 * users, roles, assignments...
				 */

			} else {

				/* When authentication is failed log error */
				System.out.println("User can not be authenticated");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.ok(status).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	public static void registryAccess() {
		CarbonContext cctx = CarbonContext.getThreadLocalCarbonContext();
		Registry registry = cctx.getRegistry(RegistryType.LOCAL_REPOSITORY);

		try {
			Resource resource = registry.newResource();
			resource.setContent("Dashboard_Test");
			registry.put("RandomPath", resource);
			System.out.println("Operation Success");
		} catch (RegistryException re) {
			System.err.println("Registry Exception");
			re.printStackTrace();
		}
	}
}
