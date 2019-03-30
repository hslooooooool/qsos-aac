package qsos.library.base.entity.http

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：接口异常实体
 */
class ApiException(throwable: Throwable, var code: ErrorCode) : Exception(throwable) {

    constructor(displayMessage: String?) : this(ServerException(ErrorCode.INTERNAL_SERVER_ERROR.value, "请求错误"), ErrorCode.INTERNAL_SERVER_ERROR) {
        this.displayMessage = displayMessage
    }

    var displayMessage: String? = null
}