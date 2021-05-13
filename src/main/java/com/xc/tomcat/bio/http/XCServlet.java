package com.xc.tomcat.bio.http;

/**
 * Servlet抽象基类
 *
 * @author lichao chao.li07@hand-china.com 5/13/21 4:07 PM
 */
public abstract class XCServlet {

    /*
     * 请求分发方法
     */
    public void service(XCRequest request, XCResponse response) throws Exception{
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }


    public abstract void doGet(XCRequest request, XCResponse response) throws Exception;

    public abstract void doPost(XCRequest request, XCResponse response) throws Exception;

}
