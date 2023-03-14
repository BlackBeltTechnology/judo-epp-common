/****************************************************************************
* Copyright (c) 2017 Red Hat Inc. and others
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package hu.blackbelt.judo.eclipse.epp.common;

/*-
 * #%L
 * hu.blackbelt.judo.eclipse.epp.package.common
 * %%
 * Copyright (C) 2018 - 2023 BlackBelt Technology
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.handlers.HandlerUtil;

public class ContributeHandler extends AbstractHandler {

	private static final String CONTRIBUTE_URL = "https://www.eclipse.org/contribute/";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		if (activeWorkbenchWindow == null) {
			return new Status(IStatus.ERROR, "hu.blackbelt.judo.eclipse.epp.common", "No active workbench window");
		}
		try {
			IWebBrowser browser = activeWorkbenchWindow.getWorkbench().getBrowserSupport().createBrowser(getClass().getName());
			browser.openURL(new URL(CONTRIBUTE_URL));
			return Status.OK_STATUS;
		} catch (PartInitException e) {
			Program.launch(CONTRIBUTE_URL);
			return new Status(IStatus.OK, "hu.blackbelt.judo.eclipse.epp.common", e.getMessage(), e);
		} catch (MalformedURLException e) {
			return new Status(IStatus.ERROR, "hu.blackbelt.judo.eclipse.epp.common", e.getMessage(), e);
		}
	}

}
