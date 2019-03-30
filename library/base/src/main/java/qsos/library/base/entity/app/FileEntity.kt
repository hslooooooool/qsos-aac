package qsos.library.base.entity.app

import qsos.library.base.entity.BaseEntity

/**
 * @author : 华清松
 * @date : 2018/10/1 15:40
 * @description : 文件实体类
 */
class FileEntity : BaseEntity {
    companion object {
        fun isImage(extension: String?): Boolean {
            return when (extension) {
                "png", "jpg", "jpeg", "gif" -> true
                else -> false
            }
        }

        fun isVideo(extension: String?): Boolean {
            return when (extension) {
                "mp4", "flv", "avi", "3gp", "webm", "ts", "ogv", "m3u8", "asf", "wmv", "rm", "rmvb", "mov", "mkv" -> true
                else -> false
            }
        }

        fun isWord(extension: String?): Boolean {
            return when (extension) {
                "doc", "docx", "pdf", "txt", "ppt", "pptx", "xls", "xlsx" -> true
                else -> false
            }
        }
    }

    // 文件id
    var id: String? = null
    // 文件名
    var fileName: String = ""
    // 文件别名
    var name: String = ""
    // 文件地址
    var fileUrl: String? = null
    // 文件格式、文件扩展名、后缀
    var filenameExtension: String? = "jpg"

}
