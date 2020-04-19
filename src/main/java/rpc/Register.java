package rpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;
import db.UserConnection;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		String password = input.getString("password");
		String firstname = input.getString("first_name");
		String lastname = input.getString("last_name");	
		UserConnection connection = new UserConnection();
		JSONObject obj = new JSONObject();
		JSONObject person = new JSONObject();
		person.put("userId", userId);
		person.put("password", password);
		person.put("firstname", firstname);
		person.put("lastname", lastname);
		if (connection.addUser(userId, password, firstname, lastname)) {
			obj.put("status", "OK");
			obj.put("response", person);
		} else {
			obj.put("status", "User Already Exists");
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}


}
