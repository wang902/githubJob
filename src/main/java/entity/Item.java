package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	private String itemId;
	private String name;
	private String address;
	private Set<String> keywords;
	private String imageUrl;
	private String url;
	private String type;
	private String time;
	
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.type = builder.type;
		this.name = builder.name;
		this.address = builder.address;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
		this.keywords = builder.keywords;
		this.time = builder.time;
	}

	public String getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public String getType() {
		return type;
	}
	
	public String getTime() {
		return time;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId);
			obj.put("type", type);
			obj.put("name", name);
			obj.put("address", address);
			obj.put("keywords", new JSONArray(keywords));
			obj.put("image_url", imageUrl);
			obj.put("url", url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static class ItemBuilder {
		private String itemId;
		private String type;
		private String time;
		private String name;
		private String address;
		private String imageUrl;
		private String url;
		private Set<String> keywords;

		public void setType(String type) {
			this.type = type;
		}
		
		public void setTime(String time) {
			this.time = time;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
	
		public void setName(String name) {
			this.name = name;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setKeywords(Set<String> keywords) {
			this.keywords = keywords;
		}
		
		public Item build() {
			return new Item(this);
		}
	}
}
