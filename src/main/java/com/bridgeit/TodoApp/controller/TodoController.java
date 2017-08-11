package com.bridgeit.TodoApp.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.bridgeit.TodoApp.JSONResponse.UserResponse;
import com.bridgeit.TodoApp.model.ToDoNotes;
import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.model.UserRegistration;
import com.bridgeit.TodoApp.services.ToDoServices;

/**
 * @author bridgeit
 *
 */
@RestController
@RequestMapping(value="/")
public class TodoController {

	@Autowired
	ToDoServices toDoServices; 

	/*---------------it create the ToDoNotes------------*/
	/**
	 * 1) this method create TodoNotes and store inside database `ToDoGoogleKeep` of table `ToDo_Notes` 
	 * @param toDoNotes
	 * @param bindingResult
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value="CreateToDoNote", method=RequestMethod.POST)
	public ResponseEntity<Response> CreateToDoNotes(@RequestBody ToDoNotes toDoNotes,BindingResult bindingResult,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		
		boolean createNote=false;
		List<ToDoNotes> todoNoteslist;
		System.out.println("isdelted :::::: :::::::: ::::::"+toDoNotes.getIsDelete());
		System.out.println("ispin :::::: :::::::: ::::::"+toDoNotes.getPin());
		System.out.println("pooja");
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
		System.out.println("updatede id  after set all value************:::::"+toDoNotes.getNoteid());
		HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");
		System.out.println("user data :: "+user);
		Date currentDate=new Date();

		System.out.println("after set all value :: "+toDoNotes);
                                
		toDoNotes.setNoteCreatedDate(currentDate);
		toDoNotes.setUser(user);//store whole object inside the table

		try {
			createNote=(Boolean) toDoServices.CreateAndUpdateToDoNotes(toDoNotes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!createNote) {
			toDoNotesResponse.setStatus(-1);
			toDoNotesResponse.setMessage("Todo Note not created successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		
		todoNoteslist=toDoServices.getNotesList(user.getId());
		System.out.println("all Notes list  :: "+toDoNotes);
		
		toDoNotesResponse.setList(todoNoteslist);
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Item created successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*------get ToDoNotes List------------*/
	/**
	 *2) this method get TodoNotes list by Title from database `ToDoGoogleKeep` of table `ToDo_Notes`
	 * @param httpServletResponse
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="ToDoNoteList", method = RequestMethod.GET)
	public ResponseEntity<ToDoNotesResponse> getNotesList(HttpServletResponse httpServletResponse,HttpServletRequest httpServletRequest )
	{
		System.out.println("--------todolistID---------");
		List<ToDoNotes> toDoNotesList=null;
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");
		System.out.println("user data :: "+user);
		
		long userId=user.getId();

		System.out.println("user Id :: "+userId);
		
		try {
			toDoNotesList=toDoServices.getNotesList(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		toDoNotesResponse.setList(toDoNotesList);
		System.out.println("TodoList :: "+toDoNotesList);
		return new ResponseEntity<ToDoNotesResponse>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*-------------------ToDoNote Update------------------*/
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
		System.out.println("is pin valuesssssss :::::   ");
		boolean updateNote=false;
		List<ToDoNotes> toDoNotesList=null;
		System.out.println("comming from services :: "+toDoNotes);
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

		HttpSession httpSession=httpServletRequest.getSession();
		User user=(User) httpSession.getAttribute("user");
		System.out.println("user data :: "+user);
		System.out.println("updated note id *****:::::"+toDoNotes);
		Date noteEditedDate=new Date();
		toDoNotes.setNoteEditedDate(noteEditedDate);
		toDoNotes.setUser(user);//store whole object inside the table
		
		System.out.println("after set all value :: "+toDoNotes);

		try {
			updateNote=(Boolean) toDoServices.CreateAndUpdateToDoNotes(toDoNotes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!updateNote) {
			toDoNotesResponse.setStatus(-1);
			toDoNotesResponse.setMessage("Todo Note not updated successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		
		try {
			toDoNotesList=toDoServices.getNotesList(user.getId());
			System.out.println("all Notes list after updated :: "+toDoNotes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		toDoNotesResponse.setList(toDoNotesList);
		toDoNotesResponse.setStatus(200);
		toDoNotesResponse.setMessage("Todo Item updated successfully...");
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*----------------Delete todoNote------------------------*/
	/**
	 * 4) this method controller Delete the user data from the `ToDoGoogleKeep` database of `User_Registration` table
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value="TodoNoteDelete",method=RequestMethod.POST)
	public ResponseEntity<Response> todoNoteDelete(@RequestBody long noteId,HttpServletRequest httpServletRequest)
	{
		System.out.println("Note iD :: "+noteId);
		HttpSession httpSession=httpServletRequest.getSession();
		User user = (User) httpSession.getAttribute("user");
		List<ToDoNotes> todoNoteslist;
		ErrorResponse errorResponse=new ErrorResponse();
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		boolean deleteuser;
		try {
				deleteuser = toDoServices.deleteNoteById(noteId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		if (!deleteuser) {
			toDoNotesResponse.setStatus(-1);
			toDoNotesResponse.setMessage("Todo Note not deleted successfully...");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
		}
		
		todoNoteslist=toDoServices.getNotesList(user.getId());
		toDoNotesResponse.setList(todoNoteslist);
		toDoNotesResponse.setMessage("user deleted successfully...");
		toDoNotesResponse.setStatus(200);
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/*------get ToDoNotes by Id------------*/
	/**
	 * 4) this method get TodoNotes by id from database `ToDoGoogleKeep` of table `ToDo_Notes` 
	 * @param noteId
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value="ToDoNote/{id}",method = RequestMethod.GET)
	public ResponseEntity<Response> getNotesById(@PathVariable("id") long noteId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		ToDoNotes toDoNotes = null;
		try {
			toDoNotes = toDoServices.getNotesById(noteId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		toDoNotesResponse.setStatus(1);
		toDoNotesResponse.setMessage("Todo Notes get successfully By Id...");
		toDoNotesResponse.setToDoNotes(toDoNotes);
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	/* -------------------get ToDoNotes by Title------------------------*/
	/**
	 *4) this method get TodoNotes by Title from database `ToDoGoogleKeep` of table `ToDo_Notes`
	 * @param noteTitle
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(value="ToDoNoteTitle/{Title}",method = RequestMethod.GET)
	public ResponseEntity<Response> getNotesByTitle(@PathVariable("Title") String noteTitle,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
	{
		HttpSession httpSession=httpServletRequest.getSession();
		UserRegistration user=(UserRegistration) httpSession.getAttribute("user");
		System.out.println("user data :: "+user);
		
		ToDoNotesResponse toDoNotesResponse=new ToDoNotesResponse();
		
		long userId=user.getId(); 
		
		List<ToDoNotes> toDoNotes = null;
		try {
			toDoNotes = toDoServices.getNotesByTitle(noteTitle,userId);
			System.out.println("todo list by title :: "+toDoNotes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorResponse errorResponse=new ErrorResponse();
			errorResponse.setMessage("Exception occure....");
			errorResponse.setStatus(-1);
			return new ResponseEntity<Response>(errorResponse,HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("todo not null or not :: "+toDoNotes);
		if(toDoNotes==null)
		{
			toDoNotesResponse.setStatus(-1);
			toDoNotesResponse.setMessage("Todo Notes of "+noteTitle+" title not found.....");
			return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.NOT_FOUND);
		}
		
		toDoNotesResponse.setStatus(1);
		toDoNotesResponse.setMessage("Todo Notes get by title successfully...");
		toDoNotesResponse.setList(toDoNotes);
		return new ResponseEntity<Response>(toDoNotesResponse,HttpStatus.OK);
	}
	
	
}
