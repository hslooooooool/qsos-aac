package qsos.module.common.api

/**
 * @author : 华清松
 * @date : 2019/1/24 16:52
 * @description : 推荐 表单参数
 */
class TCRecommendForm : ToMap {

    /*操作系统 api。可为空*/
    var os_api: String? = null
    /*设备类型。可为空*/
    var device_type: String? = null
    /*设备平台。可为空*/
    var device_platform: String? = null
    /*固定值 a。可为空*/
    var ssmix: String? = null
    /*版本号去除小数点，例如 232。可为空*/
    var manifest_version_code: String? = null
    /*设备 dpi。可为空*/
    var dpi: String? = null
    /*固定值0。可为空*/
    var abflag: Int? = null
    /*一个长度为15的纯数字字符串。可为空*/
    var uuid: String? = null
    /*版本号去除小数点，例如 232。可为空*/
    var version_code: String? = null
    /*固定值 tuchong。可为空*/
    var app_name: String? = null
    /*版本号。可为空*/
    var version_name: String? = null
    /*一个长度为16的小写字母和数字混合字符串。可为空*/
    var openudid: String? = null
    /*设备宽高像素。可为空*/
    var resolution: String? = null
    /*操作系统版本号。可为空*/
    var os_version: String? = null
    /*设备环境，例如 wifi。可为空*/
    var ac: String? = null
    /*固定值0。可为空*/
    var aid: Int? = null
    /*翻页值。从 1 开始。需要搭配 json 中的 pose_id 字段使用。可为空*/
    var page: Int? = null
    /*如果是第一页则不需要添加该字段。否则，需要加上该字段，该字段的值为上一页最后一个 json 中的 post_id 值*/
    var post_id: Int? = null
    /*如果是第一页则是 refresh，如果是加载更多，则是 loadmore。可为空*/
    var type: String? = null

    /* https://api.tuchong.com/feed-app?
    os_api=22
    &device_type=MI
    &device_platform=android
    &ssmix=a
    &manifest_version_code=232
    &dpi=400
    &abflag=0
    &uuid=651384659521356
    &version_code=232
    &app_name=tuchong
    &version_name=2.3.2
    &openudid=65143269dafd1f3a5
    &resolution=1280*1000
    &os_version=5.8.1
    &ac=wifi
    &aid=0
    &page=1
    &type=refresh
    */
    override fun getMap(): Map<String, Any?> {
        val map = HashMap<String, Any?>(19)
        map["os_api"] = os_api ?: ""
        map["device_type"] = device_type ?: ""
        map["device_platform"] = device_platform ?: "android"
        map["ssmix"] = ssmix ?: "a"
        map["manifest_version_code"] = manifest_version_code ?: ""
        map["dpi"] = dpi ?: ""
        map["uuid"] = uuid ?: ""
        map["version_code"] = version_code ?: ""
        map["app_name"] = app_name ?: "qsos-acc"
        map["version_name"] = version_name ?: ""
        map["openudid"] = openudid ?: ""
        map["resolution"] = resolution ?: ""
        map["os_version"] = os_version ?: ""
        map["ac"] = ac ?: "4g"
        map["type"] = type ?: "refresh"

        map["abflag"] = abflag ?: 0
        map["aid"] = aid ?: 0
        map["page"] = page ?: 1

        if (null != post_id) map["post_id"] = post_id!!
        return map
    }
}