package test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListener implements ServletRequestListener {
	
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		try {
			EJBBean ejbBean = (EJBBean) new InitialContext().lookup("java:module/EJBBean");
			
			System.out.println("RequestListener: " + ejbBean.hello());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		
	}

}
