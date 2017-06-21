package tarea1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tarea1 {

    private static ServerSocket server;

	public static void main(String args[] ) throws IOException {

    	int port = 8081;
        server = new ServerSocket(port);
        System.out.println("Listening for connection on port "+port+" ....");
        
        
        while (true) {
            try (Socket socket = server.accept()) {
            	
            	Map<String, Object> parameters = new HashMap<String, Object>();
                InputStreamReader isr =  new InputStreamReader(socket.getInputStream());
                
                BufferedReader in = new BufferedReader(isr);
                String line; int postDataI = 0;
                line = in.readLine();
                //Separo primera linea, para diferenciar POST y GET
                String[] parts = line.split(" ");
                System.out.println(line);
                while ((line = in.readLine()) != null && (line.length() != 0)) {
                    //System.out.println("HTTP-HEADER: " + line);
                    if (line.indexOf("Content-Length:") > -1) {
                    postDataI = new Integer(
                        line.substring(
                            line.indexOf("Content-Length:") + 16,
                            line.length())).intValue();
                    }
                }
                             
                String asd = parts[1];
                String postData = "";
                
                if(parts[0].equals("POST")){
                	// read the post data
                	if (postDataI > 0) {
                    	char[] charArray = new char[postDataI];
                    	in.read(charArray, 0, postDataI);
                    	postData = new String(charArray);
                    	System.out.println(postData);
                	}
                }
                
                
                if(asd.equals("/home_old")){
                	String httpResponse = "HTTP/1.1 301 Moved Permanently\r\nLocation: /\r\n\r\n";
                	System.out.println("301");
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else if(asd.equals("/secret")){
                	if(postData.equals("uname=root&psw=rdc2017")){
                		String aux = "";
                		String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "Autentificación Completada";
                		System.out.println("200");
                		socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                	}
                	else{
                		String aux = "403";
                		String httpResponse = "HTTP/1.1 403 Forbidden\r\n\r\n" + renderHtml(aux);
                		System.out.println("403");
                		socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                	}
                }
                else if(asd.equals("/")){
                	String aux = "index";
                	String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + renderHtml(aux);
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else if(asd.equals("/login")){
                	String aux = "login";
                	String httpResponse = "HTTP/1.1 200 OK\r\nVary: Upgrade-Insecure-Requests\r\n\r\n" + renderHtml(aux);
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else{
                	String aux = asd.split("/")[1];
                	String httpResponse = "HTTP/1.1 200 OK\r\nVary: Upgrade-Insecure-Requests\r\n\r\n" + renderHtml(aux);
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
            	socket.close();   

            }
        }
    }
    

    
    public static String renderHtml(String route){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String s = ""; 
        try {
            archivo = new File (route+".html");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            while((linea=br.readLine())!=null)
            		s += linea;
         } 
         catch(Exception e){
             e.printStackTrace();
          }finally{
             try{                    
                if( null != fr ){   
                   fr.close();     
                }                  
             }catch (Exception e2){ 
                e2.printStackTrace();
             }
          }
    	
    	return s;
    	
    }
    
    public static void parseQuery(String query, Map<String, 
    		  Object> parameters) throws UnsupportedEncodingException {

    		         if (query != null) {
    		                 String pairs[] = query.split("[&]");
    		                 for (String pair : pairs) {
    		                          String param[] = pair.split("[=]");
    		                          String key = null;
    		                          String value = null;
    		                          if (param.length > 0) {
    		                          key = URLDecoder.decode(param[0], 
    		                            System.getProperty("file.encoding"));
    		                          }

    		                          if (param.length > 1) {
    		                                   value = URLDecoder.decode(param[1], 
    		                                   System.getProperty("file.encoding"));
    		                          }

    		                          if (parameters.containsKey(key)) {
    		                                   Object obj = parameters.get(key);
    		                                   if (obj instanceof List<?>) {
    		                                            List<String> values = (List<String>) obj;
    		                                            values.add(value);

    		                                   } else if (obj instanceof String) {
    		                                            List<String> values = new ArrayList<String>();
    		                                            values.add((String) obj);
    		                                            values.add(value);
    		                                            parameters.put(key, values);
    		                                   }
    		                          } else {
    		                                   parameters.put(key, value);
    		                          }
    		                 }
    		         }
    		}

}









