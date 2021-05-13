package com.xc.tomcat.bio.servlet;

import com.xc.tomcat.bio.http.XCRequest;
import com.xc.tomcat.bio.http.XCResponse;
import com.xc.tomcat.bio.http.XCServlet;

/**
 * Servlet
 *
 * @author lichao chao.li07@hand-china.com 5/13/21 4:11 PM
 */
public class SecondServlet extends XCServlet {

    @Override
    public void doGet(XCRequest request, XCResponse response) throws Exception {
        doPost(request,response);
    }

    @Override
    public void doPost(XCRequest request, XCResponse response) throws Exception {
        System.out.println("This is second servlet from BIO.");
        response.write("This is second servlet from BIO.");
    }

}
