package org.exoplatform.portlet.example;

import javax.portlet.PortletMode;

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
		@EventConfig(listeners = UISearchBookForm.CancelActionListener.class) })
public class UISearchBookForm extends UIForm {
	String searchTitle = "";

	public UISearchBookForm() {
		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
	}

	public static class SearchActionListener extends
			EventListener<UISearchBookForm> {
		@Override
		public void execute(Event<UISearchBookForm> event) throws Exception {
			UISearchBookForm bookForm = event.getSource();
		    UIFormStringInput UIsearchTitleInput = bookForm.getUIStringInput("search_title");
		    if(UIsearchTitleInput!=null){
		    	bookForm.setSearchTitle(UIsearchTitleInput.getValue());
		    }
		}
	}

	public static class CancelActionListener extends
			EventListener<UISearchBookForm> {
		@Override
		public void execute(Event<UISearchBookForm> event) throws Exception {

		}

	}

	public void processRender(WebuiRequestContext context)
			throws Exception {
		getChildren().clear();

		// if(PortletMode.VIEW.equals(currentMode)) {
		// if (this.getChild(UITestForm.class) == null)
		// this.addChild(UITestForm.class, null, null);
		// } else {
		// if (this.getChild(UITestConfig.class) == null)
		// this.addChild(UITestConfig.class, null, null);
		// }

		addChild(new UIFormStringInput("search_title", searchTitle));
		super.processRender(context);
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

}
