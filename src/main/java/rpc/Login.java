package rpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import db.MySQLConnection;
import db.UserConnection;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject obj = new JSONObject();
		JSONObject person = new JSONObject();
		if (session != null) {
			MySQLConnection connection = new MySQLConnection();
			String userId = session.getAttribute("user_id").toString();
			person.put("userId", userId);
			person.put("name", connection.getFullname(userId));
			obj.put("status", "OK");
			obj.put("response", person);
			connection.close();
		} else {
			obj.put("status", "Invalid Session");
			response.setStatus(403);
		}
		RpcHelper.writeJsonObject(response, obj);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		String password = input.getString("password");

		UserConnection connection = new UserConnection();
		JSONObject obj = new JSONObject();
		JSONObject person = new JSONObject();
		if (connection.verifyLogin(userId, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", userId);
			session.setMaxInactiveInterval(600);
			person.put("userId", userId);
			person.put("name", connection.getFullname(userId));
			obj.put("status", "OK").put("response", person);
		} else {
			obj.put("status", "User Doesn't Exist");
			response.setStatus(401);
		}
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}


}
