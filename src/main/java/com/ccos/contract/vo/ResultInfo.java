package com.ccos.contract.vo;

import lombok.Getter;
import lombok.Setter;

//封装返回结果的类：状态码：成功1，失败0；提示信息；返回对象（字符串，javabean，集合，map等）
@Getter
@Setter
public class ResultInfo<T> {
    private Integer code;
    private String msg;
    private T result;

}
