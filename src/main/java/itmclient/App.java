package itmclient;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.mondeca.itm.schema.*;

/**
 * Hello ITM
 */
public class App {

  public static final String MDC_CLIENT = "lds";
  public static final String MDC_PASSWORD = "api"; ////"mondeca";
  public static final String MDC_LOGIN = "api";  //// "lds"
  public static final int MDC_WORKSPACE =  108942;   //// 100009;  // Terminology Workspace
   //// "http://lds.org/terminology/concept/term/recordtype-105-official-en";
  public static final String PSI_TERM_1 = "itm:n#_108948"; // Husband
  public static final String PSI_TERM_2 = "itm:n#_108944"; // Wife
  public static final String PSI_LIST = "itm:n#_108958"; // Family


  public static void main(String[] args) throws Exception {
    URL url = new URL("http://ec2-23-21-61-25.compute-1.amazonaws.com:8080/itmWebservices/itmws.wsdl");
//    URL url = new URL("http://ec2-54-242-139-150.compute-1.amazonaws.com:8080/itmWebservices/itmws.wsdl");

    ITMService service = new ITMService(url,QName.valueOf("{http://itm.mondeca.com/schema}ITMService"));
    ITM itm = service.getITMPort();
    // LOGIN
    ConnectionRequestType connectionRequest = new ConnectionRequestType();
    connectionRequest.setLogin(MDC_LOGIN);
    connectionRequest.setPassword(MDC_PASSWORD);
    connectionRequest.setClient(MDC_CLIENT);
    connectionRequest.setWorkspace(MDC_WORKSPACE);
    connectionRequest.setAnonymous(false);
    ConnectionResponseType connectionResponse = itm.connection(connectionRequest);
    if(!connectionResponse.isSuccessfull()) {
      System.err.println("FAILED to connect: " + connectionResponse.getMessage());
      return;
    }
    // Save connection ID
    String connectionId = connectionResponse.getIdentifier();

    // DO STUFF
    GetTopicRequestType topicRequest = new GetTopicRequestType();
    topicRequest.setConnectionID(connectionId);
        // Get a Term
//    topicRequest.setPsi(PSI_TERM_1); // classId=100030&id=102758
//    TopicType topic = itm.getTopic(topicRequest);
//    System.out.println("Term: " + topic.toString());

      // Get a List
    topicRequest.setPsi(PSI_LIST);
    topicRequest.setGetMetaData(true);
    TopicType topic = itm.getTopic(topicRequest);
    System.out.println("List: " + topic.toString());

    // LOGOUT
    ConnectedRequestType logoutRequest = new ConnectedRequestType();
    logoutRequest.setConnectionID(connectionId);
    GenericResponseType response = itm.logout(logoutRequest);
    if (response.isSuccessfull()) {
      System.out.println("Logged out: " + response.getMessage());
    }
    else {
      System.out.println("Logout FAILED: " + response.getMessage());
    }

  }
}
