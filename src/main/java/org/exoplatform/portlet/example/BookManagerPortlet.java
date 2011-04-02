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

import java.util.Calendar;
import java.util.List;

import javax.portlet.PortletMode;

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

	public static class SearchBookActionListener extends EventListener<BookManagerPortlet> {
		public void execute(Event<BookManagerPortlet> event) throws Exception {
			BookManagerPortlet basicConfig = event.getSource();
		    PortletRequestContext context = (PortletRequestContext) event.getRequestContext();
			context.setApplicationMode(PortletMode.VIEW);
		}

	}

	public static class AddBookActionListener extends EventListener<BookManagerPortlet> {
		public void execute(Event<BookManagerPortlet> event) throws Exception {
			BookManagerPortlet basicConfig = event.getSource();
		    PortletRequestContext context = (PortletRequestContext) event.getRequestContext();
			context.setApplicationMode(PortletMode.VIEW);	  
		}
	}

	public BookManagerPortlet() throws Exception {
		super();
//		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
//		javax.portlet.PortletPreferences preferences = portletRequestContext.getRequest().getPreferences();
		// String text = preferences.getValue(UIBookSearchForm.TEXT_PREFERENCE, null);
//		String text = "please input search value";
//
//		addChild(new UIFormStringInput("Search", text));
	}

	public void processRender(WebuiApplication app, WebuiRequestContext context) throws Exception {
		getChildren().clear();
		if (this.getChild(UISearchBookForm.class) == null) this.addChild(UISearchBookForm.class, null, null);
		super.processRender(app, context);
	}
	
	public List<Book> getAllBook(){
		PortalContainer portalContainer = PortalContainer.getInstance();
		BookManager bookManager = (BookManager) portalContainer.getComponentInstanceOfType(BookManager.class);
		
		return bookManager.search(new Book());
	}
}
