package mx.edu.utez.adoptame.util;

import javax.servlet.http.HttpServletRequest;

public class URLUtil {
    private URLUtil() {
        // Constructor vacío
    }

    public static String getBaseURl(HttpServletRequest request) {
	    String scheme = request.getScheme();
	    String serverName = request.getServerName();
	    int serverPort = request.getServerPort();
	    String contextPath = request.getContextPath();
	    StringBuilder url =  new StringBuilder();
	    url.append(scheme).append("://").append(serverName);
	    
        if ((serverPort != 80) && (serverPort != 443)) {
	        url.append(":").append(serverPort);
	    }
	    
        url.append(contextPath);
	    
        if(url.toString().endsWith("/")){
	    	url.append("/");
	    }
	    
        return url.toString();
	}
}
