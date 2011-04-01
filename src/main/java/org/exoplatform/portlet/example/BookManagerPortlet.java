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

import org.exoplatform.container.PortalContainer;
import org.exoplatform.jcr.example.pojo.Book;
import org.exoplatform.jcr.example.service.BookManager;
import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;
import org.exoplatform.webui.form.UIFormStringInput;

@ComponentConfig(
					lifecycle = UIApplicationLifecycle.class,
					template = "app:/groovy/webui/TestPortlet/BookListPortlet.gtmpl"
				)
public class BookManagerPortlet extends UIPortletApplication {

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
		super.processRender(app, context);
	}
	
	public List<Book> getAllBook(){
		PortalContainer portalContainer = PortalContainer.getInstance();
		BookManager bookManager = (BookManager) portalContainer.getComponentInstanceOfType(BookManager.class);
		
		Book book = new Book();
		book.setTitle("Hello World");
		book.setPrice(10);
		book.setPublishDay(Calendar.getInstance());
		bookManager.save(book);
		
		return bookManager.search(new Book());
	}
}
