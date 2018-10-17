package com.huowolf.util;

/**
 * Created by huowolf on 2018/10/10.
 */
public class ResultUtil {

    /**成功且带数据**/
    public static Result success(String msg,Object object){
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
    /**成功但不带数据**/
    public static Result success(){

        return success("success",null);
    }
    /**失败**/
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}

