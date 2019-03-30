package qsos.library.base.entity.http

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：错误类型字典
 */
enum class ErrorCode(code: Int) {
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    REQUEST_TIMEOUT(408),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    
    /**未知错误*/
    UNKNOWN(1000),
    /**解析错误*/
    PARSE_ERROR(1001),
    /**网络错误*/
    NETWORK_ERROR(1002),
    /**协议出错*/
    HTTP_ERROR(1003),
    /**访问超时*/
    BACK_ERROR(1004),
    /**返回数据为 null，暂无数据*/
    RESULT_NULL(1005);

    val value = code
}