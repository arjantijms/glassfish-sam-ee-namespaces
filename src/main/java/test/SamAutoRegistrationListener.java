package test;

import static javax.security.authenticationmechanism.JaspicUtils.registerSAM;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SamAutoRegistrationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	
    	try {
			EJBBean ejbBean = (EJBBean) new InitialContext().lookup("java:module/EJBBean");
			System.out.println("Servlet: " + ejbBean.hello());
		} catch (NamingException e) {
			System.out.println("Servlet: Exception");
			e.printStackTrace();
		}
    	
        registerSAM(sce.getServletContext(), new TestServerAuthModule());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}