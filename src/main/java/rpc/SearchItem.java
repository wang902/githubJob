package rpc;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;
import external.GitHubClient;

/**
 * Servlet implementation class SearchItem
 */
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
    	JSONObject obj = new JSONObject();
    	JSONObject infor = new JSONObject();
    	String userId = request.getParameter("user_id");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));

		infor.put("userId", userId);
		infor.put("lat", lat);
		infor.put("lon", lon);
		
		GitHubClient client = new GitHubClient();
		List<Item> items = client.search(lat, lon, null);

		MySQLConnection connection = new MySQLConnection();
		Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
		connection.close();

		JSONArray array = new JSONArray();
		for (Item item : items) {
			JSONObject itemObj = item.toJSONObject();
			itemObj.put("favorite", favoritedItemIds.contains(item.getItemId()));
			array.put(itemObj);
		}
		infor.put("job", array);
		obj.put("status", "OK");
		obj.put("response", infor);
		
		
		RpcHelper.writeJsonObject(response, obj);


	}






	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
