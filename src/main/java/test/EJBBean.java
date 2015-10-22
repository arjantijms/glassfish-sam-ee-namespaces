package test;

import javax.ejb.Stateless;

@Stateless
public class EJBBean {
	
	public String hello() {
		return "Hello from EJB";
	}

}
