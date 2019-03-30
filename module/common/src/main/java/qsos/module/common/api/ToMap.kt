package qsos.module.common.api

/**
 * @author : 华清松
 * @date : 2019/1/24
 * @description : 实体对象 转 QueryMap
 */
interface ToMap {

    fun getMap(): Map<String, Any?>
}