package qsos.library.netservice.http

import io.reactivex.functions.Function
import qsos.library.base.entity.BaseResult
import qsos.library.base.entity.http.ErrorCode
import qsos.library.base.entity.http.ServerException

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：拦截固定格式的公共数据类型 Response<T>,判断里面状态码 <T>
 */
class ApiResponseFunc<T> : Function<BaseResult<T>, T> {

    @Throws(Exception::class)
    override fun apply(result: BaseResult<T>): T {
        if (!result.state) {
            throw ServerException(result.code, result.msg)
        }
        if (result.results == null && result.code == 200) {
            throw ServerException(ErrorCode.PARSE_ERROR.value, "解析错误")
        }
        when (result.code) {
            401 -> throw ServerException(401, result.msg)
            404 -> throw ServerException(404, result.msg)
            500 -> throw ServerException(500, result.msg)
        }
        return result.results!!
    }
}