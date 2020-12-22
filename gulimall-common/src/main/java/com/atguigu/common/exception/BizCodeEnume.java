package com.atguigu.common.exception;


/**
 * @ControllerAdvice+@ExceptionHandler —系统错误码
 * ·错误码和错误信息定义类
 * .1.错误码定义规则为5为救字
 * .2.前两位表示业务场景，最后三位表示错误码，例如， 100001. 10通用001：系统未知异常
 * .3，维护错误码后需要维护错误描述，将他们定义为枚举形式，错误码列表，
 * 10：通用
 *      001，参数格式校路
 * 11：商品
 * 12：订单
 * 13：购物车
 * 14：物流
 */
public enum BizCodeEnume {
    //2.系统未知异常
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    //1.参数校验
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常");
    private int code;
    private String message;
    BizCodeEnume(int code,String message){
        this.code=code;
        this.message =message;
    }
    public int getCode() { return code; }
    public String getMsg() { return message; }
}
