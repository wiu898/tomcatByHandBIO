package com.xc.tomcat.bio.http;

import java.io.InputStream;

/**
 * Request
 *
 * @author lichao chao.li07@hand-china.com 5/13/21 4:05 PM
 */
public class XCRequest {

    private String url;

    private String method;

    public XCRequest(InputStream is) {
        try{
            //拿到http协议的具体内容
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if((len = is.read(buff)) > 0){
                content = new String(buff,0,len);
            }
            System.out.println(content);
            System.out.println("-----------------------------------------------");
            //逐行解析请求内容 \n
            String contentLine = content.split("\\n")[0];
            //按空格取出参数
            String[] arr = contentLine.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];

        }catch (Exception e){

        }
    }


    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
