package org.exoplatform.portlet.example;

import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletSession;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.jcr.example.pojo.Book;
import org.exoplatform.jcr.example.service.BookManager;
import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormStringInput;

@ComponentConfig(lifecycle = UIFormLifecycle.class, template = "app:/groovy/webui/TestPortlet/UIListBook.gtmpl")
public class UIListBook extends UIComponent {
	public String getSearchTitle() {
		return searchTitle;
	}
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	private String searchTitle = "";

	public UIListBook() {

	}
	public List<Book> getAllBook(){
		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
		String searchTitleObject = (String) portletRequestContext.getRequest().getPortletSession().getAttribute("search_title");			
		if (searchTitleObject != null) {
			 this.searchTitle = (String) searchTitleObject;
		}
		
		PortalContainer portalContainer = PortalContainer.getInstance();
		BookManager bookManager = (BookManager) portalContainer.getComponentInstanceOfType(BookManager.class);
		Book bookToSearch = new Book();
		bookToSearch.setTitle(searchTitle);
		return bookManager.search(bookToSearch);
	}
}
