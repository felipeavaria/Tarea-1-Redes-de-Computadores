package tarea1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.util.Date;



public class Tarea1 {

    private static ServerSocket server;

	public static void main(String args[] ) throws IOException {

    	int port = 8080;
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
                //System.out.println(line);
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
					historialLog("localhost:"+port+"/home_old" );
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
                	historialLog("localhost:"+port+"/secret" );
                }
                else if(asd.equals("/")){
                	String aux = "home";
                	String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + renderHtml(aux);
                	historialLog("localhost:"+port+"/home" );
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else if(asd.equals("/login")){
                	String aux = "login";
                	String httpResponse = "HTTP/1.1 200 OK\r\nVary: Upgrade-Insecure-Requests\r\n\r\n" + renderHtml(aux);
                	historialLog("localhost:"+port+"/login" );
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }

            	socket.close();   

            }
        }
    }
    

    
    public static String renderHtml(String page){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String s = ""; 
        System.out.println("route : "+page);
        try {
            archivo = new File (page +".html");
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
    public static void historialLog(String Url ){
        FileWriter fichero = null;
        PrintWriter pw = null;
        
        Date date = new Date();
        
        try
        {
            fichero = new FileWriter("log.txt",true);
            pw = new PrintWriter(fichero);
            pw.println("<127.0.0.1> <"+ Url +"> <" + DateFormat.getInstance().format(date) + ">");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
        	   if (null != fichero)
        		   fichero.close();
           	   } catch (Exception e2) {
           		   e2.printStackTrace();
           	   }
        }
    	
    }
    
    
    
    
 

}









