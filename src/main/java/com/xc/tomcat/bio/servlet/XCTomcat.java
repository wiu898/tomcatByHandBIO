package com.xc.tomcat.bio.servlet;

import com.xc.tomcat.bio.http.XCRequest;
import com.xc.tomcat.bio.http.XCResponse;
import com.xc.tomcat.bio.http.XCServlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Tomcat启动测试类
 *
 * @author lichao chao.li07@hand-china.com 5/13/21 4:05 PM
 */
public class XCTomcat {

    private int port = 8080;  //默认端口

    private ServerSocket server;   //服务端Socket

    private Properties webxml = new Properties();

    //存储url和servlet的映射关系
    private Map<String, XCServlet> servletMapping = new HashMap<String, XCServlet>();

    public static void main(String[] args) {
        new XCTomcat().start();
    }

    /*
     * 启动入口
     */
    private void start() {
        //1.加载web.properties文件解析配置进行缓存 实际项目中为web.xml此处用properties替代
        init();

        //2.启动服务端socket，等待用户请求
        try{
            server = new ServerSocket(port);
            System.out.println("XC Tomcat 已启动,监听端口是:" + this.port);
            //通过一个死循环将server hold住，一直等待请求
            while (true){
                //获取客户端连接
                Socket client = server.accept();
                //3.获得请求，解析http协议内容
                process(client);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void process(Socket client) throws Exception{
        //通过客户端连接可以拿到一个输入流和输出流
        InputStream is = client.getInputStream();    //request
        OutputStream os = client.getOutputStream();  //response

        //初始化request和response
        XCRequest request = new XCRequest(is);
        XCResponse response = new XCResponse(os);

        //对request中的url进行分发
        if(servletMapping.containsKey(request.getUrl())){
            servletMapping.get(request.getUrl()).service(request,response);
        }else {
            response.write("404 - Not Found!");
        }
        os.flush();
        os.close();
        is.close();
        client.close();
    }

    /*
     * 初始化方法
     */
    private void init() {
        String WEB_INF = this.getClass().getResource("/").getPath();
        try{
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");
            webxml.load(fis);
            for(Object k : webxml.keySet()){
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$","");
                    String url = webxml.getProperty(key);
                    //拿到servlet类的全路径名
                    String className = webxml.getProperty(servletName+ ".className");
                    //通过反射创建servlet类
                    XCServlet servlet = (XCServlet) Class.forName(className).newInstance();
                    //将url和servlet映射关系存入map缓存建立映射关系
                    servletMapping.put(url,servlet);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
