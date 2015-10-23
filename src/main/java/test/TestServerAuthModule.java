package test;

import static javax.security.auth.message.AuthStatus.SEND_SUCCESS;
import static javax.security.auth.message.AuthStatus.SUCCESS;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServerAuthModule implements ServerAuthModule {

    private CallbackHandler handler;
    private Class<?>[] supportedMessageTypes = new Class[] { HttpServletRequest.class, HttpServletResponse.class };

    @Override
    public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, @SuppressWarnings("rawtypes") Map options) throws AuthException {
        this.handler = handler;
    }

    @Override
    public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {

    	try {
    		// java:global/glassfish-sam-ee-namespaces/EJBBean does work
			EJBBean ejbBean = (EJBBean) new InitialContext().lookup("java:module/EJBBean");
			System.out.println("SAM: " + ejbBean.hello());
		} catch (NamingException e) {
			System.out.println("SAM: Exception");
			e.printStackTrace();
		}
    	
        try {
        	 // The JASPIC protocol for "do nothing"
            handler.handle(new Callback[] { new CallerPrincipalCallback(clientSubject, (Principal) null) });
            
            return SUCCESS;
            
        } catch (IOException | UnsupportedCallbackException e) {
            throw (AuthException) new AuthException().initCause(e);
        }

        
    }

    @Override
    public Class<?>[] getSupportedMessageTypes() {
        return supportedMessageTypes;
    }

    @Override
    public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
    	try {
    		// java:global/glassfish-sam-ee-namespaces/EJBBean does work
			EJBBean ejbBean = (EJBBean) new InitialContext().lookup("java:module/EJBBean");
			System.out.println("SAM SR: " + ejbBean.hello());
		} catch (NamingException e) {
			System.out.println("SAM SR: Exception");
			e.printStackTrace();
		}
        return SEND_SUCCESS;
    }

    @Override
    public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {

    }
}