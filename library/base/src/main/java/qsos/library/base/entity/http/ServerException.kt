package qsos.library.base.entity.http

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：请求服务异常
 */
class ServerException(var code: Int, var msg: String?) : RuntimeException()