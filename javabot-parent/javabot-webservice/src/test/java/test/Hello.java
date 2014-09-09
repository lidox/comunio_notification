package test;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService(
        portName = "HelloPort",
        serviceName = "HelloService",
        targetNamespace = "http://10.56.231.52:8076",
        endpointInterface = "test.IHelloService")
public class Hello implements IHelloService{

	@Override
	public String hello(String whom) {
		return "Hello "+whom;
	}

}
