package org.exoplatform.portlet.example;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

import javassist.expr.Instanceof;

import javax.portlet.PortletSession;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.jcr.example.pojo.Book;
import org.exoplatform.jcr.example.service.BookManager;
import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;

@ComponentConfig(
					lifecycle = UIApplicationLifecycle.class,
					template = "app:/groovy/webui/TestPortlet/BookListPortlet.gtmpl",
					events = {
						@EventConfig(listeners = BookManagerPortlet.AddBookActionListener.class),
						@EventConfig(listeners = BookManagerPortlet.SearchBookActionListener.class)
					}					
				)
public class BookManagerPortlet extends UIPortletApplication {
	private BookManagerPortletStatus bookManagerPortletStatus = null;
	
	public static class SearchBookActionListener extends EventListener<BookManagerPortlet> {
		public void execute(Event<BookManagerPortlet> event) throws Exception {
		    PortletRequestContext context = (PortletRequestContext) event.getRequestContext();
		    
		    PortletSession portletSession = context.getRequest().getPortletSession();
		    BookManagerPortletStatus bookManagerPortletStatus = (BookManagerPortletStatus) portletSession.getAttribute("status");
		    
		    if(bookManagerPortletStatus != null &&  bookManagerPortletStatus instanceof BookManagerPortletStatus){
		    	bookManagerPortletStatus.setStatus(BookManagerPortletStatus.SEARCH);
		    } else {
				bookManagerPortletStatus = new BookManagerPortletStatus();
				portletSession.setAttribute("status", bookManagerPortletStatus);
		    }
		}

	}

	public static class AddBookActionListener extends EventListener<BookManagerPortlet> {
		public void execute(Event<BookManagerPortlet> event) throws Exception {
		    PortletRequestContext context = (PortletRequestContext) event.getRequestContext();
		    PortletSession portletSession = context.getRequest().getPortletSession();
		    BookManagerPortletStatus bookManagerPortletStatus = (BookManagerPortletStatus) portletSession.getAttribute("status");
		    
		    if(bookManagerPortletStatus != null &&  bookManagerPortletStatus instanceof BookManagerPortletStatus){
		    	bookManagerPortletStatus.setStatus(BookManagerPortletStatus.ADD);
		    } else {
				bookManagerPortletStatus = new BookManagerPortletStatus();
				portletSession.setAttribute("status", bookManagerPortletStatus);
		    }
		    
		}
	}

	public BookManagerPortlet() throws Exception {
		super();
		if (this.getChildById("UISearchForm") == null) this.addChild(UISearchBookForm.class, null, "UISearchForm");
		if (this.getChildById("UIListBook") == null) this.addChild(UIListBook.class, null, "UIListBook");
		if (this.getChildById("UIAddBookForm") == null) this.addChild(UIAddBookForm.class, null, "UIAddBookForm");
	}

	public void processRender(WebuiApplication app, WebuiRequestContext context) throws Exception {
		PortletRequestContext  portletRequestContext = WebuiRequestContext.getCurrentInstance();
		PortletSession portletSession =  portletRequestContext.getRequest().getPortletSession();
		bookManagerPortletStatus = (BookManagerPortletStatus) portletSession.getAttribute("status");
		
		if(bookManagerPortletStatus != null &&  bookManagerPortletStatus instanceof BookManagerPortletStatus){

		} else {
			bookManagerPortletStatus = new BookManagerPortletStatus();
			portletSession.setAttribute("status", bookManagerPortletStatus);
		}
		
		String currentPage = bookManagerPortletStatus.getStatus();
		if( BookManagerPortletStatus.SEARCH == bookManagerPortletStatus.getStatus()){
			if (this.getChildById("UISearchForm") != null) this.getChildById("UISearchForm").setRendered(true);
			if (this.getChildById("UIListBook") != null) this.getChildById("UIListBook").setRendered(true);
			if (this.getChildById("UIAddBookForm") != null) this.getChildById("UIAddBookForm").setRendered(false);
		} else if (BookManagerPortletStatus.ADD == bookManagerPortletStatus.getStatus()){
			if (this.getChildById("UISearchForm") != null) this.getChildById("UISearchForm").setRendered(false);
			if (this.getChildById("UIListBook") != null) this.getChildById("UIListBook").setRendered(false);
			if (this.getChildById("UIAddBookForm") != null) this.getChildById("UIAddBookForm").setRendered(true);			
		} else {
			if (this.getChildById("UISearchForm") != null) this.getChildById("UISearchForm").setRendered(false);
			if (this.getChildById("UIListBook") != null) this.getChildById("UIListBook").setRendered(true);
			if (this.getChildById("UIAddBookForm") != null) this.getChildById("UIAddBookForm").setRendered(false);

		}
		
		super.processRender(app, context);
	}

	public BookManagerPortletStatus getBookManagerPortletStatus() {
		return bookManagerPortletStatus;
	}

}
