package qsos.library.base.callback

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：泛型回调
 */
interface OnTListener<T> {
    /**获取回调对象*/
    fun getItem(item: T)
}
