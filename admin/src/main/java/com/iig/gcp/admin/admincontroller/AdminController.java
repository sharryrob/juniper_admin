package com.iig.gcp.admin.admincontroller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.iig.gcp.CustomAuthenticationProvider;
import com.iig.gcp.admin.admincontroller.dto.CountryMaster;
import com.iig.gcp.admin.admincontroller.dto.Group;
import com.iig.gcp.admin.admincontroller.dto.Project;
import com.iig.gcp.admin.admincontroller.dto.UserAccount;
import com.iig.gcp.admin.adminservice.AdminService;



@Controller
@SessionAttributes(value= {"user_name","project_name","jwt"})
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	
	@Value( "${parent.front.micro.services}" )
	private String parent_micro_services;
	
	public String usr = "";
	public String proj = "";
	
	@RequestMapping(value = { "/", "/login"}, method = RequestMethod.GET)
	public ModelAndView homePage(@Valid @ModelAttribute("jsonObject") final String jsonObject,final ModelMap modelMap,final HttpServletRequest request) throws JSONException {
		if(jsonObject == null || jsonObject.equals("")){
			//Redirecting to Access Denied page
			return new ModelAndView("/login");
		}
		JSONObject jobj = new JSONObject(jsonObject);
		this.usr = jobj.getString("userId");
		this.proj = jobj.getString("project");
		//Validate the token at the first place
		try {
		JSONObject jsonModelObject = null;
		if(modelMap.get("jsonObject")== null || modelMap.get("jsonObject").equals("")) {
			//TODO: Redirect to Access Denied Page
			return new ModelAndView("/login");
		}
		jsonModelObject = new JSONObject( modelMap.get("jsonObject").toString());
		authenticationByJWT(this.usr+":"+this.proj, jsonModelObject.get("jwt").toString());
		
		//Set session variable
		request.getSession().setAttribute("user_name", jsonModelObject.getString("userId"));
		request.getSession().setAttribute("project_name", jsonModelObject.getString("project"));
		
		//Rest of the method continues here...
		
		modelMap.addAttribute("user_id", jsonModelObject.getString("userId"));
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ModelAndView("/login");
			//redirect to Login Page
		}
		return new ModelAndView("/index");
	}
	
	private void authenticationByJWT(final String name,final String token) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, token);
        Authentication authenticate = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
	}
	
	@RequestMapping(value = {"/parent"}, method = RequestMethod.GET)
	public ModelAndView parentHome(ModelMap modelMap,HttpServletRequest request, Authentication auth) throws JSONException {
		CustomAuthenticationProvider.MyUser m = (CustomAuthenticationProvider.MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("userId", m.getName());
		jsonObject.put("project", m.getProject());
		jsonObject.put("jwt", m.getJwt());
		
		modelMap.addAttribute("jsonObject",jsonObject.toString());
		return new ModelAndView("redirect:" + "//"+parent_micro_services+"/fromChild", modelMap);
		
		
	}
	
	@RequestMapping(value = { "/admin/onboardProject"}, method = RequestMethod.GET)
	public ModelAndView onBoardProject(final ModelMap modelMap,final HttpServletRequest request) {
		List<String> projects=null;
		try {
			projects = adminService.getProjects();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		modelMap.addAttribute("proj_val", projects);
		return new ModelAndView("/admin/onboardProject");
	}
	
	@RequestMapping(value = { "/admin/SystemOnboard"}, method = RequestMethod.GET)
	public ModelAndView sysOnboarding(final ModelMap modelMap) throws ClassNotFoundException, SQLException {
		return new ModelAndView("/admin/SystemOnboard");
	}
	
	@RequestMapping(value = { "/admin/userOnboarding"}, method = RequestMethod.GET)
	public ModelAndView userOnboarding(final ModelMap modelMap) throws ClassNotFoundException, SQLException {
		
		List<String> userId=null;
		try {
			userId = adminService.fetchUserIds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelMap.addAttribute("user_val",userId);
		return new ModelAndView("admin/userOnboarding");
	}
	
	@RequestMapping(value = { "/admin/usertogrplink"}, method = RequestMethod.GET)
    public ModelAndView onBoardUser(final ModelMap modelMap) throws Exception {
		
		ArrayList<String> usersid=	adminService.getAllUsers();
		modelMap.addAttribute("user_val",usersid);
		return new ModelAndView("/admin/usertogrouplink");
	}
	

	@RequestMapping(value = { "/admin/UserEdit"}, method = RequestMethod.POST)
	public ModelAndView updateUserDetails(@ModelAttribute("user_val") final String userId,final ModelMap modelMap) throws ClassNotFoundException, SQLException {
		UserAccount user=null;
		try {
			user = adminService.fetchUserDetails(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelMap.addAttribute("username",user.getUser_id());
		modelMap.addAttribute("domain",user.getUser_domain());
		modelMap.addAttribute("name",user.getUser_fullname());
		modelMap.addAttribute("email",user.getUser_email());
		return new ModelAndView("admin/userOnboarding2");
	}

	@RequestMapping(value = { "/admin/userOnboarding1"}, method = RequestMethod.POST)
	public ModelAndView userOnboarding(@Valid @RequestParam("username") String userId,
			@RequestParam("domain") String domain, 
			@RequestParam("email") String userEmail,@RequestParam("name") final String onboardedUserFullName, @ModelAttribute("button_type") final String button_type,
			final HttpServletRequest request,final ModelMap modelMap) throws Exception {
		String message = null;
		
		UserAccount loggedInUser = (UserAccount)adminService.fetchUserDetails((String)request.getSession().getAttribute("user_name"));
		if(loggedInUser!=null) {
			try {
				if (button_type.equalsIgnoreCase("add")) {
					message = adminService.registerUserInSystem(userId,domain,onboardedUserFullName, userEmail,loggedInUser.getUser_id());
					if(message.equals("Success")) {
						modelMap.addAttribute("successString",""+userId+" user registration successful");
					}else {
						modelMap.addAttribute("errorString",""+userId+" user registration failed");
					}
				} else {
					message = adminService.updateUser(userId,domain,onboardedUserFullName,userEmail,loggedInUser.getUser_id());
					if(message.equals("Success")) {
						modelMap.addAttribute("successString",""+userId+" user updated successfully");
					}else {
						modelMap.addAttribute("errorString",""+userId+" user update failed");
					}
				} 
			} catch (Exception e) {
				modelMap.addAttribute("errorStatus", message);
				e.printStackTrace();
			}
		}
		ArrayList<String> usersid=	adminService.getAllUsers();
		modelMap.addAttribute("user_val",usersid);
		return new ModelAndView("admin/userOnboarding");
	}
	
	
	
	/**
	 * This method validate if PSID already exists.
	 * @param system_eim
	 * @param model
	 * @return 
	 * @throws UnsupportedOperationException
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/PSIDValidation", method = RequestMethod.POST)
	public ModelAndView psidValidation(@Valid @RequestParam(value = "psid", required = true) String psid, ModelMap model) throws UnsupportedOperationException, Exception {
		int stat = adminService.checkPSID(psid.trim());
		model.addAttribute("stat", stat);
		return new ModelAndView("/admin/userOnboarding3");
	}
	

	@RequestMapping(value = { "/admin/selectuser"}, method = RequestMethod.POST)
	public ModelAndView selectUser(@Valid @RequestParam(value = "user", required = true) final String user,final ModelMap modelMap,final HttpServletRequest request) {
		try {
			String statuserid=	adminService.getUser(user);	
			String stat=statuserid.substring(0, 1);
			String userid=statuserid.substring(1, statuserid.length());
			ArrayList<Group> group = adminService.getGroups(user,(String)request.getSession().getAttribute("project_name"));
			ArrayList<Group> groupp = adminService.getGroupsAlready(user,(String)request.getSession().getAttribute("project_name"));

			modelMap.addAttribute("stat",Integer.parseInt(stat));
			modelMap.addAttribute("userid",userid);
			modelMap.addAttribute("group", group);
			modelMap.addAttribute("groupp", groupp);

		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();
		}
		return new ModelAndView("admin/userdiv");
	}

	@RequestMapping(value = { "/admin/onboarduser"}, method = RequestMethod.POST)
    public ModelAndView submitUser(@Valid final String x,@Valid final boolean project_admin,final ModelMap modelMap,final HttpServletRequest request) throws Exception {
		
		try {
			String message=adminService.onBoardUser(x, project_admin, (String)request.getSession().getAttribute("project_name"), request);
			modelMap.addAttribute("successString",message);

		}catch(Exception e) {
			modelMap.addAttribute("errorString",e.getMessage());

			e.printStackTrace();

		}
		ArrayList<String> usersid=	adminService.getAllUsers();
		modelMap.addAttribute("user_val",usersid);
		return new ModelAndView("admin/usertogrouplink");
	}


	
	@RequestMapping(value = { "/admin/saveProjectDetailsForm"}, method = RequestMethod.POST)
	public ModelAndView saveProjectDetails(final ModelMap modelMap) {
		return new ModelAndView("/index");
	}

	@RequestMapping(value = { "/admin/saveSystemDetailsForm"}, method = RequestMethod.POST)
	public ModelAndView saveSystemDetails(final ModelMap modelMap) {
		return new ModelAndView("/index");
	}



	@RequestMapping(value = { "/admin/addProjectDetails" }, method = RequestMethod.POST)
	public ModelAndView registerProject(@Valid @RequestParam("project_id") final String projectId,
			@RequestParam("project_name") final String projectName, @RequestParam("project_owner") final String projectOwner,
			@RequestParam("project_description") final String projectDescription, 
			@RequestParam("gsd_group") final String gsdGroup,@RequestParam("gsd_key") final String gsdKey,@ModelAttribute("button_type") final String button_type,
			final HttpServletRequest request,final ModelMap modelMap) throws Exception{
		String message = null;
		String message1 = null;
		UserAccount user = (UserAccount)adminService.fetchUserDetails((String)request.getSession().getAttribute("user_name"));

		try {
			if (button_type.equalsIgnoreCase("add")) {
				message = adminService.registerProject(projectId, projectName, projectOwner, projectDescription,gsdGroup,gsdKey,(int)adminService.getUserSequence((String)request.getSession().getAttribute("user_name")));
				if(message.equals("Success")) {
					Project project = adminService.getProject(projectId);
					adminService.linkUserGroupsToNewProject((int)adminService.getUserSequence((String)request.getSession().getAttribute("user_name")),project);
					fetchProjectDetailsForUser(modelMap, message, user);
					modelMap.addAttribute("successString",""+projectId+"  project created successfully");
				}else {
					modelMap.addAttribute("errorString",""+projectId+" project creation failed");
				}
			} else {
				message = adminService.updateProject(projectId, projectName, projectOwner, projectDescription,gsdGroup,gsdKey,user.getUser_id());
				if(message.equals("Success")) {
					fetchProjectDetailsForUser(modelMap, message, user);
					modelMap.addAttribute("successString",""+projectId+" updated successfully");
				}else {
					modelMap.addAttribute("errorString",""+projectId+" update failed");
				}
			}
			
		} catch (Exception e) {
			modelMap.addAttribute("errorStatus", message);
			e.printStackTrace();
		}
		
		return onBoardProject(modelMap,request);
	}


	/**
	 * This method fetch projects related to user and create map with project id and project sequence.
	 * @param modelMap
	 * @param message
	 * @param user
	 * @throws Exception
	 */
	private void fetchProjectDetailsForUser(final ModelMap modelMap,final String message,final UserAccount user) throws Exception {
		modelMap.addAttribute("successString", message);
		ArrayList<Project> arrProject = adminService.getProjects1(user.getUser_id());
		modelMap.addAttribute("arrProject", arrProject);
		HashMap<String, Integer> hsmap = new HashMap<String, Integer>();
		for (Project project : arrProject) {

			hsmap.put(project.getProject_id(), project.getProject_sequence());
		}
		modelMap.addAttribute("projectFeatureMap", hsmap);
	}

	/**
	 * This method fetch project details related to project id.
	 * @param projId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/getProjectDetails", method = RequestMethod.POST)
	public ModelAndView fetchProjectDetails(@Valid final String projId, final ModelMap model) {
		Project project=null;
		try {
			project = adminService.fetchProjectDetails(projId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("project_id", project.getProject_id());
		model.addAttribute("project_name", project.getProject_name());
		model.addAttribute("project_owner", project.getProject_owner());
		model.addAttribute("project_description", project.getProject_description());
		model.addAttribute("gsd_group", project.getGsd_group());
		model.addAttribute("gsd_key", project.getGsd_key());
		return new ModelAndView("admin/ProjectDetailsEdit");
	}
	
	@RequestMapping(value = { "/admin/FetchCountriesForRegion"}, method = RequestMethod.POST)
	public ModelAndView fetchCountriesForRegion(@Valid @RequestParam("region") final String region,final ModelMap modelMap) throws Exception {
		List<CountryMaster> countries = adminService.fetchCountries(region);
		modelMap.addAttribute("countries", countries);
		return new ModelAndView("/admin/SystemOnboard1");
	}


	/**
	 * This method validated EIM, if duplicate.
	 * @param system_eim
	 * @param model
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/EIMValidation", method = RequestMethod.POST)
	public ModelAndView eimValidation(@Valid @RequestParam(value = "eim", required = true)final String system_eim,final ModelMap model) throws UnsupportedOperationException, Exception {
		int stat = adminService.checkEIM(system_eim);
		model.addAttribute("stat", stat);
		return new ModelAndView("/admin/SystemOnboard2");
	}





	/**
	 * This method register new system in db.
	 * @param feed_id
	 * @param job_id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "/admin/register"}, method = RequestMethod.POST)
	public ModelAndView registerSystem(@Valid @RequestParam("system_eim") final String systemEIM,@RequestParam("system_name") final String systemName,
			@RequestParam("system_region") final String region,@RequestParam("system_country") final String country,
			@RequestParam("environment_type") final String environmentType,final HttpServletRequest request,final ModelMap modelMap) {
		try {
			UserAccount user = (UserAccount)adminService.fetchUserDetails((String)request.getSession().getAttribute("user_name"));
				if(user==null) {
					return new ModelAndView("/error");
				}
				String message = adminService.registerSystem(systemEIM,systemName,region,country,environmentType,user.getUser_id());
				if (message.equals("Success")) {
					modelMap.addAttribute("successString", ""+systemName+" system registration successful");
				} else {
					modelMap.addAttribute("errorString", ""+systemName+" system registration failed");
				}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("errorString", e.getMessage());
		}
		return new ModelAndView("/admin/SystemOnboard");
	}

	@RequestMapping(value = { "/admin/error"}, method = RequestMethod.GET)
	public ModelAndView error(final ModelMap modelMap,final HttpServletRequest request) {
		
		return new ModelAndView("/index");
	}
	
}
