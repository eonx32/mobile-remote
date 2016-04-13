import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

class Read extends Thread
{
    private InputStream br;
    private Socket socket;
    
    Read(Socket socket,String name) throws IOException
    {
        super(name);
        this.br = socket.getInputStream();
        this.socket = socket;
    }
    
    public void run()
    {
        byte[] buffer = new byte[1024];
        int count;
        
        try{
            while(socket.isConnected()){
                while ((count = br.read(buffer)) > 0)
                {
                    String s = new String(buffer, "UTF-8");
                    String[] inp = s.split(" ");
                    if(inp[0].equals("GET")){
                        int a=inp[1].charAt(1)-'0';
                        try{
                            pressKey(a);
                        }catch(AWTException e){
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
            
            if(socket!=null)
                socket.close();
            
            //System.out.println("Socket Closed...");
        }catch(IOException e){
            //System.out.println("Unable to read and write from buffer...");
        }
    }
    
    void pressKey(int button) throws AWTException
    {
        Robot robot = new Robot();
        robot.setAutoDelay(40);
        robot.setAutoWaitForIdle(true);
        
        robot.delay(40);
        if(button==0){
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
        else if(button==1){
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
        }
        else if(button==2){
            robot.keyPress(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_SPACE);
        }
        else if(button==3){
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        else if(button==4){
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
        }
        else if(button==5){
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        else if(button==6){
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        }
    }
}

public class autoKey{
    
   
    public static void main(String[] args)
    {
        
        try {
            
            while(true){
                
                ServerSocket serverSocket = new ServerSocket(8585);
                Socket socket = serverSocket.accept();
                serverSocket.close();
                Read read = new Read(socket,"read");
                read.start();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}