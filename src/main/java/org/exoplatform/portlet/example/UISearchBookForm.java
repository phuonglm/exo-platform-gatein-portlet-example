package org.exoplatform.portlet.example;

import javax.portlet.PortletMode;
import javax.portlet.PortletSession;

import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormStringInput;

@ComponentConfig(lifecycle = UIFormLifecycle.class, template = "system:/groovy/webui/form/UIForm.gtmpl", events = {
		@EventConfig(listeners = UISearchBookForm.SearchActionListener.class),
		@EventConfig(listeners = UISearchBookForm.CancelActionListener.class)})
public class UISearchBookForm extends UIForm {
	private String searchTitle = "";

	public UISearchBookForm() {
		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
		
		String searchTitleObject = (String) portletRequestContext.getRequest().getPortletSession().getAttribute("search_title");
		
		if (searchTitleObject != null) {
			 this.searchTitle = (String) searchTitleObject;
		}
		addChild(new UIFormStringInput("search_title", searchTitle));
	}

	public static class SearchActionListener extends
			EventListener<UISearchBookForm> {
		@Override
		public void execute(Event<UISearchBookForm> event) throws Exception {
			UISearchBookForm bookForm = event.getSource();
			PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();

			UIFormStringInput UIsearchTitleInput = bookForm.getUIStringInput("search_title");
			if (UIsearchTitleInput != null) {
				portletRequestContext.getRequest().getPortletSession().setAttribute("search_title", UIsearchTitleInput.getValue());
			} 
		}
	}

	public static class CancelActionListener extends
			EventListener<UISearchBookForm> {
		@Override
		public void execute(Event<UISearchBookForm> event) throws Exception {
			PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
			PortletSession portletSession = portletRequestContext.getRequest().getPortletSession();
			
			BookManagerPortletStatus bookManagerPortletStatus = (BookManagerPortletStatus) portletSession.getAttribute("status");
			
			if(bookManagerPortletStatus != null) {
				portletRequestContext.getRequest().getPortletSession().setAttribute("search_title", "");
				bookManagerPortletStatus.setStatus(BookManagerPortletStatus.VIEWLIST);
			}
		}

	}

	public void processRender(WebuiRequestContext context) throws Exception {
		
		super.processRender(context);
	}
 
	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
}
