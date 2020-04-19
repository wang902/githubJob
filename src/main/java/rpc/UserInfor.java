package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import db.UserConnection;

/**
 * Servlet implementation class UserInfor
 */
public class UserInfor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfor() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject input = RpcHelper.readJSONObject(request);
		JSONObject obj = new JSONObject();
		JSONObject person = new JSONObject();
		
		String userId = input.getString("user_id");
		String intro = input.getString("intro");
		String education = input.getString("education");
		String experience = input.getString("experience");
		String skills = input.getString("skills");
		String services = input.getString("services");
		
		UserConnection connection = new UserConnection();
		connection.update(userId, intro, education, experience, skills, services);

		obj.put("status", "OK");
		connection.close();
		RpcHelper.writeJsonObject(response, obj);
	}

}
