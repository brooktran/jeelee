/* @(#)AllSample.java 1.0 2009-11-18
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.jtool.sample;

import java.awt.EventQueue;

import org.jtool.app.IApplication;
import org.jtool.app.IModel;
import org.jtool.sample.job.JLApplication;
import org.jtool.sample.job.JLModel;
import org.jtool.utils.ResourceUtil;

/**
 * <B>AllSample</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-18 created
 * @since JTool Ver 1.0
 * 
 */
public class AllSample {

	/** The Constant Labels. */
	private static final String Labels = "org.jtool.sample.Labels";

	/** Default application object name. */
	private static final String DEFAULT_APP_NAME = "org.jtool.app.DefaultApplication";

	/** Default application model name. */
	private static final String DEFAULT_MODEL_NAME = "org.jtool.app.DefaultModel";

	/** Default view class reference. */
	private static final String DEFAULT_VIEW_NAME="org.jtool.job.JLView";

	/** The application. */
	private IApplication application;

	/** The model. */
	private IModel model;

	/**
	 * The Constructor.
	 * 
	 * @since JobLister
	 */
	public AllSample() {
	}

	/**
	 * Start.
	 */
	private void start(){
		initApp();
		application.launch();
	}
	/**
	 * Inits the app.
	 */
	private void initApp() {
		ResourceUtil resource = ResourceUtil
		.getResourceBundleUtil(Labels);

		// inits model
		String modelName = resource.getString("model.className");
		if (modelName == null) {
			modelName = DEFAULT_MODEL_NAME;
		}
		model = (IModel) getObject(modelName, model);
		if(model == null) {
			model =new JLModel();
		}
		model.setCopyright("2009 all right reserve");
		model.setName("JTool");
		model.setVersion("1.0.1");

		// inits the view
		String viewName = resource.getString("view.className");
		if (viewName == null) {
			viewName = DEFAULT_VIEW_NAME;
		}
		model.setViewName(viewName);

		// inits the application
		String appName = resource.getString("app.className");
		if (appName == null) {
			appName = DEFAULT_APP_NAME;
		}
		application = (IApplication) getObject(appName, application);
		if(application == null) {
			application =new JLApplication();
		}
		application.setModel(model);

	}

	/**
	 * Gets the object reference by the specified class name.
	 * 
	 * if the obj is not null, then it do nothing. so, you have to set the obj
	 * to null before invoke this method.
	 * 
	 * @param className the class name
	 * @param obj the obj
	 * 
	 * @return the object
	 */
	private Object getObject(String className, Object obj) {
		if (obj == null) {
			if (className == null) {
				return null; // er .. maybe it can try to do something else.
			}

			try {
				return Class.forName(className).newInstance();
			} catch (ClassNotFoundException e) {
				System.out.println("Cann't find class name: "+className);
				System.err.println("can't find the "+className+" class. try it for a monent");
				System.exit(-1);
				//				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(className);
				e.printStackTrace();
			}catch(ExceptionInInitializerError e){
				System.out.println("Cann't find class name: "+className);
				System.err.println("can't find the "+className+" class. try it for a monent");
				System.exit(-1);
			}
		}
		return null; //
	}


	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					new AllSample().start();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
