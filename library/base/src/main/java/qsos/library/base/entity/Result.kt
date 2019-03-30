package qsos.library.base.entity

/**
 * @author : 华清松(姓名) 821034742@qq.com(邮箱) hslooooooool(微信)
 * @date : 2018/5/23 0023
 * @desc : 所有实体需继承此接口
 */
interface Result<T> {
    fun isError(): Boolean
    fun getResult(): T?
    fun getErrorCode(): Int
    fun getMessage(): String?
}