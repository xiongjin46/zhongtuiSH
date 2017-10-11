package com.push.PushMerchant.network.resp;

/**
 * @ClassName: ResultCode
 * @Description:
 * @author: YYL
 * <p/>
 * create at 2016/9/6
 */
public interface ResultCode {
    int Code_OK = 0; // 正常结束，httpCode 200

    int Code_Network_Unavaiable = -800; // 当前没有网络
    int Code_Canceled = -801; // 请求被cancel
    int Code_Request_ParamErr = -802; // 请求参数错误
    int Code_Received_Html = -803; // 被劫持
    int Code_Response_Empty = -804; // 回报里的list或者item为null
    // --------Json异常
    int Code_Json_SyntaxErr = -805; // json语法错误
    int Code_Json_IOErr = -805; // json io 错误
    int Code_Json_ParseErr = -806; // json 解析错误
    // ---- Http异常
    int Code_Http_MalformedURLErr = -820; // url错误
    int Code_Http_Client_ProtocolErr = -821; // http协议错误
    int Code_Http_Connect_TimeOut = -822; // 连接超时
    int Code_Http_Socket_Timeout = -823; // socket超时
    int Code_Http_ConnectErr = -824; // 连接异常
    int Code_Http_SocketErr = -825; // socket异常
    int Code_Http_IOErr = -826; // IO异常
    int Code_Http_Err = -827; // 其他Http异常

    int Code_Http_EntityNull = -840; // 回包包体为空
    int Code_Http_ResponseNull = -841; // 没有得到Response


    int CODE_EXPIRED_ACCESS_TOKEN = 106;
    /**
     * access_token过期
     */
    int CODE_ACCESS_TOKEN_ERROR = 2;
}
