package test;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/servlet")
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
			EJBBean ejbBean = (EJBBean) new InitialContext().lookup("java:module/EJBBean");
			System.out.println("Servlet: " + ejbBean.hello());
		} catch (NamingException e) {
			System.out.println("Servlet: Exception");
			e.printStackTrace();
		}
    	
    	
    	response.getWriter().write("This is a servlet \n");
    }

}
