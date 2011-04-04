package org.exoplatform.portlet.example;

public class BookManagerPortletStatus {
	public static final String VIEWLIST = "viewlist";
	public static final String SEARCH = "search";
	public static final String ADD = "search";
	
	private String status = VIEWLIST;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
