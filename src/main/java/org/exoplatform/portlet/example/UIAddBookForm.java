package org.exoplatform.portlet.example;

import java.util.Date;

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
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormDateTimeInput;
import org.exoplatform.webui.form.UIFormInputInfo;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.ext.UIFormComboBox;

@ComponentConfig(lifecycle = UIFormLifecycle.class, template = "system:/groovy/webui/form/UIForm.gtmpl", events = {
		@EventConfig(listeners = UIAddBookForm.AddActionListener.class),
		@EventConfig(listeners = UIAddBookForm.CancelActionListener.class)})
public class UIAddBookForm extends UIForm {
	private String searchTitle = "";

	public UIAddBookForm() {
		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
		
		addChild(new UIFormStringInput("book_title", "book title"));
		addChild(new UIFormStringInput("book_price", "book price"));
		addChild(new UIFormDateTimeInput("book_publishDay", "book publishDay", new Date(), false));		
	}

	public static class AddActionListener extends
			EventListener<UIAddBookForm> {
		@Override
		public void execute(Event<UIAddBookForm> event) throws Exception {
			UIAddBookForm uiAddBookForm = event.getSource();
			Book bookToAdd = new Book();
			UIFormStringInput UITitleInput = uiAddBookForm.getUIStringInput("book_title");
			if (UITitleInput != null) {
				bookToAdd.setTitle( UITitleInput.getValue());
			}
			UIFormStringInput UIPriceInput = uiAddBookForm.getUIStringInput("book_price");
			if (UIPriceInput != null) {
				bookToAdd.setPrice(Integer.parseInt(UIPriceInput.getValue()));
			}
			UIFormDateTimeInput uiFormDateTimeInput = uiAddBookForm.getUIFormDateTimeInput("book_publishDay");
			if (uiFormDateTimeInput != null) {
				bookToAdd.setPublishDay(uiFormDateTimeInput.getCalendar());
			}
			
			PortalContainer portalContainer = PortalContainer.getInstance();
			BookManager bookManager = (BookManager) portalContainer.getComponentInstanceOfType(BookManager.class);
			bookManager.save(bookToAdd);
			
			PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
			PortletSession portletSession = portletRequestContext.getRequest().getPortletSession();
			
			BookManagerPortletStatus bookManagerPortletStatus = (BookManagerPortletStatus) portletSession.getAttribute("status");
			
			if(bookManagerPortletStatus != null) {
				portletRequestContext.getRequest().getPortletSession().setAttribute("search_title", "");
				bookManagerPortletStatus.setStatus(BookManagerPortletStatus.VIEWLIST);
			}
		}
	}

	public static class CancelActionListener extends
			EventListener<UIAddBookForm> {
		@Override
		public void execute(Event<UIAddBookForm> event) throws Exception {
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
 
}
