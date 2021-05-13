package com.xc.tomcat.bio.http;

import java.io.OutputStream;

/**
 * description
 *
 * @author lichao chao.li07@hand-china.com 5/13/21 4:06 PM
 */
public class XCResponse {

    private OutputStream outputStream;

    public XCResponse(OutputStream os) {
        this.outputStream = os;
    }

    /*
     * Response输出-通常是返回 html或者json字符串
     */
    public void write(String s) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 ok\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        outputStream.write(sb.toString().getBytes());
    }
}
