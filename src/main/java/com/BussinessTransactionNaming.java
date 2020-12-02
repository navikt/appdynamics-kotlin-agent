package com;

import com.appdynamics.agent.api.AppdynamicsAgent;
import com.appdynamics.agent.api.EntryTypes;
import com.appdynamics.agent.api.Transaction;
import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKStringMatchType;
import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;
import java.util.ArrayList;
import java.util.List;

public class BussinessTransactionNaming
  extends AGenericInterceptor {
	
	public BussinessTransactionNaming() {
        super();
    }
	
  public List<Rule> initializeRules()
  {
    List<Rule> rules = new ArrayList<Rule>();
    rules.add(new Rule.Builder("io.ktor.server.netty.http1.NettyHttp1Handler")
      .methodMatchString("handleRequest").methodStringMatchType(SDKStringMatchType.EQUALS).build());
    
    return rules;
  }

@Override
public Object onMethodBegin(Object invokedObject, String className, String methodName, Object[] paramValues) {
	System.out.print("In begin");
	StringBuilder sb = new StringBuilder();
    sb.append("KTOR.");
	String getterChain1 = null;
	String bt = null;
	try {
		getterChain1 = paramValues[1].getClass().getField("uri").toString().split("/")[1];
	} catch (NoSuchFieldException e) {
		e.printStackTrace();
	} catch (SecurityException e) {
		e.printStackTrace();
	}
	if (!getterChain1.isEmpty()) {
	      sb.append(getterChain1);
	    }
	bt = sb.toString();
    AppdynamicsAgent.startTransaction(bt, null, EntryTypes.POJO, false);
	return null;
}

@Override
public void onMethodEnd(Object state, Object invokedObject, String className, String methodName,
        Object[] paramValues, Throwable thrownException, Object returnValue) {
	System.out.print("In end");
	Transaction currentTransaction = AppdynamicsAgent.getTransaction();
	currentTransaction.end();
	
}
}
