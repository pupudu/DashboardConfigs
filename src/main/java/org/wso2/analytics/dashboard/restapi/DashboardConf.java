package org.wso2.analytics.dashboard.restapi;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.json.JSONString;
import org.springframework.util.SerializationUtils;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.context.RegistryType;
import org.wso2.carbon.registry.api.Registry;
import org.wso2.carbon.registry.api.RegistryException;
import org.wso2.carbon.registry.api.Resource;

import sun.security.jca.GetInstance.Instance;

@Path("/dashBoardConfigs")
public class DashboardConf implements Serializable {

	/** The logger. */
	private Log logger = LogFactory.getLog(DashboardConf.class);

	Map<Integer, PageMetaBean> pageMeta = new HashMap<Integer, PageMetaBean>();
	Map<String, WidgetBean> widgets = new HashMap<String, WidgetBean>();
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
			PageMetaBean metaData = pageMeta.get(dashBoardID);
			if (metaData == null) {
				return Response.noContent().build();// 204
			} else {
				return Response.ok(metaData).type(MediaType.APPLICATION_JSON).build(); // 200
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
			return Response.notModified().build();
		}

		if (pageMeta.get(dashBoardID) == null) {
			logger.debug("MetaData added to Map with key: " + dashBoardID);
		} else {
			logger.debug("MetaData on Map with key: " + dashBoardID + " Replaced");
		}
		pageMeta.put(dashBoardID, metaData);
		writeToRegistry("Dashboards/metadata/" + dashBoardID, pageMeta); 

		return Response.ok(metaData).type(MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	// TODO- Get superior Advice on this method structure
	@Path("/metadata/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePageMetaElement(@PathParam("id") int dashboardId) {

		if (pageMeta.get(dashboardId) == null) {
			return Response.notModified().build();
		} else {
			pageMeta.remove(dashboardId);
			return Response.ok().build();
		}
	}

	/* Returns the Widget ID List as a Json with a String Array */
	@GET
	@Path("/widgetList/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWidget() {
		logger.debug("getWidgetIDList Method called");
		try {
			String[] widgetsIDList = widgets.keySet().toArray(new String[(widgets.size())]);

			if (widgetsIDList != null) {
				logger.debug("Widget ID List returned");
				return Response.ok(widgetsIDList).type(MediaType.APPLICATION_JSON).build();
			} else {
				logger.debug("Widget ID List is empty");
				return Response.noContent().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/widget/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWidget(@PathParam("id") String id) {
		logger.debug("getWidget Method Accessed");
		try {

			WidgetBean widget = widgets.get(id);

			if (widget != null) {
				logger.debug("Widget Successfully Resturned");
				return Response.ok(widget).type(MediaType.APPLICATION_JSON).build();
			} else {
				logger.debug("Widget Content is empty");
				return Response.noContent().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@POST
	@Path("widget/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putWidget(@PathParam("id") String id, WidgetBean widget) {
		logger.debug("putWidget method accessed");

		try {
			if (widgets.get(widget.getId()) == widget) {
				logger.debug("unmodified widget object");
				return Response.notModified().build();
			} else {
				widgets.remove(id);
				widgets.put(widget.getId(), widget);
				logger.debug("widget with id:" + widget.getId() + " replaced");
				return Response.ok(widget).type(MediaType.APPLICATION_JSON).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/dashboard/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDashboard() {
		if (logger.isDebugEnabled()) {
			logger.debug("Dashboard dataset Requested");
		}
		try {
			if (this.dashboard != null) {
				return Response.ok(this.dashboard).type(MediaType.APPLICATION_JSON).build();
			} else {
				return Response.noContent().build();
			}
		} catch (Exception e) {
			logger.error(e);
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/dashboard/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setDashboard(DashboardBean dashboard) {
		if (logger.isDebugEnabled()) {
			logger.debug("Dashboard dataset Recieved");
		}
		try {
			if (dashboard == null) {
				logger.debug("Null object recieved");
				return Response.notModified().build();
			} else {
				logger.debug("dashboard data replaced");
				this.dashboard = dashboard;
				return Response.ok(this.dashboard).type(MediaType.APPLICATION_JSON).build();
			}
		} catch (Exception e) {
			logger.error(e);
			return Response.serverError().build();
		}
	}

	@DELETE
	@Path("/dashboard/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteDashboard() {

		if (this.dashboard == null) {
			return Response.notModified().build();
		} else {
			this.dashboard = null;
			return Response.ok().build();
		}
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

	public void writeToRegistry(RegistryType registryType, String url, Object content) {
		CarbonContext cctx = CarbonContext.getThreadLocalCarbonContext();
		Registry registry = cctx.getRegistry(registryType);

		try {
			Resource resource = registry.newResource();
			byte[] data = SerializationUtils.serialize(content); // Object to
																 // byte[]
																 // conversion
			resource.setContent(data); // Accepts String, byte[] and inputStream
									   // only
			registry.put(url, resource);
			logger.debug("Object saved on " + registryType.toString() + " at " + url);
		} catch (RegistryException re) {
			System.err.println("Registry Exception");
			re.printStackTrace();
		}
	}

	public void writeToRegistry(String url, Object Content) {
		writeToRegistry(RegistryType.SYSTEM_GOVERNANCE, url, Content);
	}

	public Object readFromRegistry(RegistryType registryType, String url) {
		Object content=null;
		CarbonContext cctx = CarbonContext.getThreadLocalCarbonContext();
		Registry registry = cctx.getRegistry(registryType);

		try {
			Resource resource = registry.get(url);
			byte[] data = (byte[]) resource.getContent(); // read contents of the resource as a byte[]
			content = SerializationUtils.deserialize(data); // deserialize byte[]
		} catch (RegistryException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public Object readFromRegistry(String url){
		return readFromRegistry(RegistryType.SYSTEM_GOVERNANCE,url);
	}
}
