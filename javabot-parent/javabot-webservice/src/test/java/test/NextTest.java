package test;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NextTest  {

//	@Test
//	public void test1(){
//		System.out.println("hi");
//	}
//    @BeforeClass
//    public static void setUp() throws Exception {
//        Properties properties = new Properties();
//        properties.setProperty("openejb.embedded.remotable", "true");
//        //properties.setProperty("httpejbd.print", "true");
//        //properties.setProperty("httpejbd.indent.xml", "true");
//        EJBContainer.createEJBContainer(properties);
//    }
//
//    @Test
//    public void test() throws Exception {
//        Service calculatorService = Service.create(
//                new URL("http://127.0.0.1:4204/Hello?wsdl"),
//                new QName("http://10.56.231.52:8076", "HelloService"));
//
//        assertNotNull("first test",calculatorService);
//
//        IHelloService calculator = calculatorService.getPort(IHelloService.class);
//        assertEquals("hello artur", calculator.hello("artur"));
//       
//    }
}
