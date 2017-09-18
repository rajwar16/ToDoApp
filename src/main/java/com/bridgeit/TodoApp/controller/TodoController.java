package com.bridgeit.TodoApp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.TodoApp.JSONResponse.ErrorResponse;
import com.bridgeit.TodoApp.JSONResponse.Response;
import com.bridgeit.TodoApp.JSONResponse.ToDoNotesResponse;

import com.bridgeit.TodoApp.model.Collaborator;
import com.bridgeit.TodoApp.model.PageScrapedata;
import com.bridgeit.TodoApp.model.ToDoNotes;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.services.ToDoServices;
import com.bridgeit.TodoApp.services.TokenServices;
import com.bridgeit.TodoApp.services.UserServices;

/**
 * @author bridgeit
 *
 */
@RestController
@RequestMapping(value="/")
public class TodoController {

	@Autowired
	ToDoServices toDoServices; 
	@Autowired
	UserServices userServices;
	@Autowired
	TokenServices tokenservices;

	/*---------------it create the ToDoNotes------------*/
	/**
	 * 1) this method create TodoNotes and store inside database `ToDoGoogleKeep` of table `ToDo_Notes` 
	 * @param toDoNotes
	 * @param bindingResult
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	@RequestMapping(value="CreateToDoNote", method=RequestMethod.POST)
	public ResponseEntity<Response> CreateToDoNotes(@RequestBody ToDoNotes toDoNotes,BindingResult bindingResult,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException, URISyntaxException
	{
		boolean createNote=false;
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();

		if(bindingResult.hasErrors())
		{
			List<FieldError> list = bindingResult.getFieldErrors();
			errorResponse.setMessage("Some Binding error has occure...");
			errorResponse.setStatus(-1);
			errorResponse.setList(list);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		User user=(User) httpServletRequest.getAttribute("user");
		System.out.println("user get by httpservletRequest :: "+user);
		
		String description=toDoNotes.getDescription();
		
		List<String> containedUrls = new ArrayList<String>();
	    String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
	    Matcher urlMatcher = pattern.matcher(description);

	    while (urlMatcher.find())
	    {
	        containedUrls.add(description.substring(urlMatcher.start(0),
	                urlMatcher.end(0)));
	    }
	  
		System.out.println(containedUrls);
		
		if(containedUrls!=null)
		{
			 getAndCreateScrap(containedUrls, toDoNotes);
		}
		
		Date currentDate=new Date();
                                
		toDoNotes.setNoteCreatedDate(currentDate);
		toDoNotes.setUser(user);//store whole object inside the table

		try {
			createNote=(Boolean) toDoServices.CreateAndUpdateToDoNotes(toDoNotes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!createNote) {
			toDoNotesResponse.setStatus(400);
			toDoNotesResponse.setMessage("Todo Note not created successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		
	/*	
		todoNoteslist=toDoServices.getNotesList(user.getId());
		toDoNotesResponse.setList(todoNoteslist);*/
		
		toDoNotesResponse.setLinks(containedUrls);
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Item created successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	private void getAndCreateScrap(List<String> containedUrls,ToDoNotes toDoNotes) throws URISyntaxException, IOException {
		
		 for(int i=0;i<containedUrls.size();i++)
		 {
			 PageScrapedata pageScrapedata=new PageScrapedata();
			 Connection con = Jsoup.connect(containedUrls.get(i)).timeout(40*1000);
			 URI stringUri=new URI(containedUrls.get(i));
			 String hostName=stringUri.getHost();
			 Document doc = con.get();
			 String title = null;
			 Elements metaOgTitle = doc.select("meta[property=og:title]");
			 if (metaOgTitle!=null) {
		    	title = metaOgTitle.attr("content");
			 }
			 else {
		    	title = doc.title();
		    }
		    
		    String imageUrl = null;
		    Elements metaOgImage = doc.select("meta[property=og:image]");
		    if (metaOgImage!=null) {
		        imageUrl = metaOgImage.attr("content");
		    }
		    else {
		    }
		    pageScrapedata.setHostName(hostName);
		    pageScrapedata.setUrlTitle(title);
		    pageScrapedata.setUrlImage(imageUrl);
		    pageScrapedata.setRedirectUrl(containedUrls.get(0));
		    pageScrapedata.setNoteid(toDoNotes);
		    
		    boolean createScrape=(Boolean) toDoServices.createScrape(pageScrapedata);
		 }
		}

	/*-------------------------get ToDoNotes List-------------------------------------------------------------*/
	/**
	 *2) this method get TodoNotes list by Title from database `ToDoGoogleKeep` of table `ToDo_Notes`
	 * @param httpServletResponse
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="ToDoNoteList", method = RequestMethod.GET)
	public ResponseEntity<Response> getNotesList(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest )
	{
		List<ToDoNotes> toDoNotesList=null;
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		ErrorResponse errorResponse=new ErrorResponse();

		User user=(User) httpServletRequest.getAttribute("user");
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user=null;
		
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		long userId1=user.getId();
		try {
			toDoNotesList=toDoServices.getNotesList(userId1);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		for(int i=0;i<toDoNotesList.size();i++)
		{
			ToDoNotes toDoNotes=toDoNotesList.get(i);
			List<PageScrapedata> scrapedataList=toDoServices.getPageScrapeData(toDoNotes.getNoteid());
			if(!scrapedataList.isEmpty())
			{
				for(int i1=0;i1<scrapedataList.size();i1++)
				{
					scrapedataList.get(i1).getNoteid().getUser().setPassword(null);
				}
				toDoNotes.setPageScrapedata(scrapedataList);	
			}
			
		}
		
		user.setPassword(null);
		toDoNotesResponse.setList(toDoNotesList);
		toDoNotesResponse.setUser(user);
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("all todo list");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*------------------------------------ToDoNote Update-------------------------------------------------------*/
	/**
	 * 3) this controller method update the todoNote inside the `ToDoGoogleKeep` database of `User` table
	 * @param toDoNotes {@link ToDoNotes}
	 * @param bindingResult {@link BindingResult}
	 * @param httpServletRequest {@link HttpServletRequest}
	 * @param httpServletResponse {@link HttpServletResponse}
	 * @return
	 */
	@RequestMapping(value="ToDoNoteUpdate", method=RequestMethod.PUT)
	public ResponseEntity<Response> updateToDoNotes(@RequestBody ToDoNotes toDoNotes,BindingResult bindingResult,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		boolean updateNote=false;
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();

		if(bindingResult.hasErrors())
		{
			List<FieldError> list = bindingResult.getFieldErrors();
			errorResponse.setMessage("Some Binding error has occure...");
			errorResponse.setStatus(400);
			errorResponse.setList(list);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		Date noteEditedDate=new Date();
		toDoNotes.setNoteEditedDate(noteEditedDate);
		toDoNotes.setUser(user);

		try {
			updateNote=(Boolean) toDoServices.UpdateToDoNotes(toDoNotes);
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!updateNote) {
			toDoNotesResponse.setStatus(400);
			toDoNotesResponse.setMessage("Todo Note not updated successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		/*
		try {
			toDoNotesList=toDoServices.getNotesList(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		toDoNotesResponse.setList(toDoNotesList);*/
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Item updated successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*----------------------------------Delete todoNote---------------------------------------------------------*/
	/**
	 * 4) this method controller Delete the user data from the `ToDoGoogleKeep` database of `User_Registration` table
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="TodoNoteDelete",method=RequestMethod.POST)
	public ResponseEntity<Response> todoNoteDelete(@RequestBody long noteId,HttpServletRequest httpServletRequest)
	{
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user = (User) httpSession.getAttribute("user");*/
		
		boolean deleteNote;
		try {
			deleteNote = toDoServices.deleteNoteById(noteId);
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!deleteNote) {
			toDoNotesResponse.setStatus(400);
			toDoNotesResponse.setMessage("TodoNote not deleted successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		
		/*todoNoteslist=toDoServices.getNotesList(user.getId());
		toDoNotesResponse.setList(todoNoteslist);*/
		
		toDoNotesResponse.setMessage("TodoNote deleted successfully...");
		toDoNotesResponse.setStatus(200);
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	
	/*----------------------------------TodoNoteEmptyTrash todoNote---------------------------------------------------------*/
	/**
	 * 5) this method controller Delete the user data from the `ToDoGoogleKeep` database of `User_Registration` table
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="TodoNoteEmptyTrash", method = RequestMethod.DELETE)
	public ResponseEntity<Response> TodoNoteEmptyTrash(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest )
	{
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*
		String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
	/* HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		long userId1=user.getId();
		
		try {
				toDoServices.emptyTrash(userId1);
				toDoNotesResponse.setStatus(200);
				toDoNotesResponse.setMessage("empty trash .....");
				return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			toDoNotesResponse.setStatus(500);
			toDoNotesResponse.setMessage("Exception....");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
	}
	
	/*-----------------------------------get ToDoNotes by Id-------------------------------------*/
	/**
	 * 6) this method get TodoNotes by id from database `ToDoGoogleKeep` of table `ToDo_Notes` 
	 * @param noteId
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value="ToDoNote/{id}",method = RequestMethod.GET)
	public ResponseEntity<Response> getNotesById(@PathVariable("id") long noteId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ErrorResponse errorResponse=new ErrorResponse();
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
	/*	HttpSession httpSession=httpServletRequest.getSession();
		UserRegistration user=(UserRegistration) httpSession.getAttribute("user");*/
		ToDoNotes toDoNotes = null;
		try {
			toDoNotes = toDoServices.getNotesById(noteId,user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Notes get successfully By Id...");
		toDoNotesResponse.setToDoNotes(toDoNotes);
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/* -------------------get ToDoNotes by Title------------------------*/
	/**
	 *7) this method get TodoNotes by Title from database `ToDoGoogleKeep` of table `ToDo_Notes`
	 * @param noteTitle
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value="ToDoNoteTitle/{Title}",method = RequestMethod.GET)
	public ResponseEntity<Response> getNotesByTitle(@PathVariable("Title") String noteTitle,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		
		ErrorResponse errorResponse=new ErrorResponse();
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
	/*	HttpSession httpSession=httpServletRequest.getSession();
		UserRegistration user=(UserRegistration) httpSession.getAttribute("user");*/
		
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		long userId1=user.getId(); 
		
		List<ToDoNotes> toDoNotes = null;
		try {
			toDoNotes = toDoServices.getNotesByTitle(noteTitle,userId1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		if(toDoNotes==null)
		{
			toDoNotesResponse.setStatus(400);
			toDoNotesResponse.setMessage("Todo Notes of "+noteTitle+" title not found.....");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.NOT_FOUND);
		}
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Notes get by title successfully...");
		toDoNotesResponse.setList(toDoNotes);
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	
	/*-------------------ToDoNote Archive------------------------------------------------------------------------*/
	/**
	 * 8) this controller method update the todoNote inside the `ToDoGoogleKeep` database of `User` table
	 * @param toDoNotes {@link ToDoNotes}
	 * @param bindingResult {@link BindingResult}
	 * @param httpServletRequest {@link HttpServletRequest}
	 * @param httpServletResponse {@link HttpServletResponse}
	 * @return
	 */
	@RequestMapping(value="ToDoNoteArchive", method=RequestMethod.PUT)
	public ResponseEntity<Response> toDoNoteArchive(@RequestBody Map<String, Object> archiveMap,BindingResult bindingResult,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		boolean updateNote=false;
		ToDoNotes toDoNotes = null;
		
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		Date noteEditedDate=new Date();
		String archive=(String) archiveMap.get("archive");
		int noteIdint = (Integer)archiveMap.get("noteId");
		long noteId = noteIdint;
		System.out.println("note id archive :: "+noteId);
		
		if(bindingResult.hasErrors())
		{
			List<FieldError> list = bindingResult.getFieldErrors();
			errorResponse.setMessage("Some Binding error has occure...");
			errorResponse.setStatus(400);
			errorResponse.setList(list);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}

		try {
			toDoNotes = toDoServices.getNotesById(noteId,user.getId());
			if(toDoNotes!=null)
				{
					if(archive.equals("true"))
					{
						toDoNotes.setPin("false");
					}
					toDoNotes.setIsArchive(archive);
					toDoNotes.setNoteEditedDate(noteEditedDate);
					toDoNotes.setUser(user);//store whole object inside the table
					try {
						updateNote=(Boolean) toDoServices.UpdateToDoNotes(toDoNotes);
					} catch (Exception e) {
						e.printStackTrace();
						errorResponse.setMessage("Exception occure....");
						errorResponse.setStatus(500);
						return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
					}
				}
			} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!updateNote) {
			toDoNotesResponse.setStatus(400);
			toDoNotesResponse.setMessage("Todo Note not updated successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		/*try {
			toDoNotesList=toDoServices.getNotesList(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		toDoNotesResponse.setList(toDoNotesList);*/
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Item archive value updated successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*---------------------------------------ToDoNote Reminder-----------------------------------------*/
	/**
	 * 9) this controller method update the todoNote inside the `ToDoGoogleKeep` database of `User` table
	 * @param toDoNotes {@link ToDoNotes}
	 * @param bindingResult {@link BindingResult}
	 * @param httpServletRequest {@link HttpServletRequest}
	 * @param httpServletResponse {@link HttpServletResponse}
	 * @return
	 */
	@RequestMapping(value="ToDoNoteReminder", method=RequestMethod.PUT)
	public ResponseEntity<Response> ToDoNoteReminder(@RequestBody Map<String, Object> remindMap,BindingResult bindingResult,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		boolean updateNote=false;
		List<ToDoNotes> toDoNotesList=null;
		ToDoNotes toDoNotes = null;
		
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken =  httpServletRequest.getHeader("accessToken");
		Token token = tokenservices.getToken(accessToken);
		long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		Date noteEditedDate=new Date();
		String reminder=(String) remindMap.get("reminder");
		int noteIdint = (Integer)remindMap.get("noteId");
		long noteId = noteIdint;
		
		if(bindingResult.hasErrors())
		{
			List<FieldError> list = bindingResult.getFieldErrors();
			errorResponse.setMessage("Some Binding error has occure...");
			errorResponse.setStatus(400);
			errorResponse.setList(list);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}

		try {
			toDoNotes = toDoServices.getNotesById(noteId,user.getId());
			if(toDoNotes!=null)
				{
					toDoNotes.setIsReminder(reminder);
					toDoNotes.setNoteEditedDate(noteEditedDate);
					toDoNotes.setUser(user);//store whole object inside the table
					try {
						updateNote=(Boolean) toDoServices.UpdateToDoNotes(toDoNotes);
					} catch (Exception e) {
						e.printStackTrace();
						errorResponse.setMessage("Exception occure....");
						errorResponse.setStatus(500);
						return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
					}
				}
			} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!updateNote) {
			toDoNotesResponse.setStatus(400);
			toDoNotesResponse.setMessage("Todo Note not updated successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		try {
			toDoNotesList=toDoServices.getNotesList(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		toDoNotesResponse.setList(toDoNotesList);
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Item updated successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	
	/*---------------------------------------ToDoNote collaborator-----------------------------------------*/
	/**
	 * 10) this controller method update the todoNote inside the `ToDoGoogleKeep` database of `User` table
	 * @param toDoNotes {@link ToDoNotes}
	 * @param bindingResult {@link BindingResult}
	 * @param httpServletRequest {@link HttpServletRequest}
	 * @param httpServletResponse {@link HttpServletResponse}
	 * @return
	 */
	@RequestMapping(value="collaborator", method=RequestMethod.PUT)
	public ResponseEntity<Response> collaborator(@RequestBody Map<String, Object> collaboratorMap,BindingResult bindingResult,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ToDoNotes toDoNotes = null;
		Collaborator collaborator = new Collaborator();
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		User user=(User) httpServletRequest.getAttribute("user");
		
		/*String accessToken = httpServletRequest.getHeader("accessToken");
		
		Token token=null;
		
		try {
			token = tokenservices.getToken(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/
		
		/*long userId=token.getUserId();
		User user;
		try {
			user=userServices.getUserById(userId);	
		} catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}*/

		/*HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");*/
		
		int noteIdint = (Integer)collaboratorMap.get("noteId");
		long sharednoteId = noteIdint;
		String sharedEmailId=(String) collaboratorMap.get("sharedEmailId");
		User sharedUser=null;
		boolean collaboratorNote=false;
		try {
				toDoNotes = toDoServices.getNotesById(sharednoteId,user.getId());
				if(toDoNotes!=null)
				{
					try {
						sharedUser = userServices.getUserByEmail(sharedEmailId);
						if(sharedUser!=null)
						{
							collaborator.setSharedId(sharedUser.getId());
							collaborator.setOwnerId(user.getId());
							collaborator.setNoteid(toDoNotes);
							try {
								collaboratorNote=(Boolean) toDoServices.collaboratorNoteCreate(collaborator);
							} catch (Exception e) {
								e.printStackTrace();
								errorResponse.setMessage("Exception occure....");
								errorResponse.setStatus(500);
								return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
							}	
						}
					}catch (Exception e) {
					e.printStackTrace();
					errorResponse.setMessage("Exception occure....");
					errorResponse.setStatus(500);
					return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
				}
				}
			}catch (Exception e) {
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(500);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setUser(user);
		toDoNotesResponse.setSharedUser(sharedUser);
		toDoNotesResponse.setMessage("Todo collaborator successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
}
