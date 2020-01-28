package interceptors;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Created by reda on 03/12/2016.
 */
public class LoginInterceptor extends AbstractInterceptor{


    @Override
        public String intercept(ActionInvocation invocation) throws Exception {
            
            Map<String, Object> sessionMap = invocation.getInvocationContext().getSession();

            String loginId = (String) sessionMap.get("pseudo");

            if (loginId == null)
            {
                return Action.LOGIN;
            }

            else
            {
                return invocation.invoke();

            }
        }

}

