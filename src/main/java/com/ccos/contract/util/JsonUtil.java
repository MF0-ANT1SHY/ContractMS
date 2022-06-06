package com.ccos.contract.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//Object -> Json
public class JsonUtil {
    public static void toJson(HttpServletResponse response,Object resultInfo){

        try{
            //set Response type and encoding format
            response.setContentType("application/json;charset=UTF-8");
            //get strstreaming
            PrintWriter out = response.getWriter();
            //->json
            String json = JSON.toJSONString(resultInfo);
            //output JSON
            out.write(json);
            //close resource
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
