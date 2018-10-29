package org.uengine.admin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uengine.processmanager.ProcessDefinitionRemote;
import org.uengine.processmanager.ProcessManagerFactoryBean;
import org.uengine.processmanager.ProcessManagerRemote;

public class ImportProcessAliasValidateionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		PrintWriter out = arg1.getWriter();
		arg1.setContentType("text/xml");
		arg1.setHeader("Cache-Control", "no-cache");

		String mode = arg0.getParameter("mode");

		if ("single".equals(mode)) {
			String alias = arg0.getParameter("alias");
			boolean val = validateSingleData(alias);

			out.println("<response>");
			out.println("<val>" + Boolean.toString(val) + "</val>");
			out.println("</response>");

		} else if ("multi".equals(mode)) {
			String paras = arg0.getParameter("paras");

			boolean[] val = validateMultiData(paras);

			out.println("<response>");
			for (int i = 0; i < val.length; i++)
				out.println("<val>" + Boolean.toString(val[i]) + "</val>");
			out.println("</response>");
		}

		out.close();
	}

	private boolean validateSingleData(String alias) {
		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		ProcessDefinitionRemote[] pds = null;
		ProcessDefinitionRemote pdr = null;
		boolean val = true;
		try {
			pm = processManagerFactory.getProcessManagerForReadOnly();
			pds = pm.listProcessDefinitionRemotesLight();
			for (int i = 0; i < pds.length; i++) {
				pdr = pds[i];
				if (pdr.getAlias() != null && pdr.getAlias().equals(alias)) {
					val = false;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pm.remove();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (RemoveException e) {
				e.printStackTrace();
			}
		}
		return val;
	}

	private boolean[] validateMultiData(String paras) {
		ProcessManagerFactoryBean processManagerFactory = new ProcessManagerFactoryBean();
		ProcessManagerRemote pm = null;
		ProcessDefinitionRemote[] pds = null;
		ProcessDefinitionRemote pdr = null;

		String[] aliass = new String[paras.split("\\|").length];
		int count=0;
		StringTokenizer st = new StringTokenizer(paras, "|");
		while (st.hasMoreElements()) {
			aliass[count] = st.nextToken();
			count++;
		}

		boolean[] val = new boolean[aliass.length];

		try {
			pm = processManagerFactory.getProcessManagerForReadOnly();
			pds = pm.listProcessDefinitionRemotesLight();
			for (int j = 0; j < aliass.length; j++) {
				for (int i = 0; i < pds.length; i++) {
					pdr = pds[i];
					if (pdr.getAlias() != null && pdr.getAlias().equals(aliass[j])) {
						val[j] = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pm.remove();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (RemoveException e) {
				e.printStackTrace();
			}
		}
		return val;
	}
}
