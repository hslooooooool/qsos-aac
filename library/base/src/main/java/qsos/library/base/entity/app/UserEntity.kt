package qsos.library.base.entity.app

import android.arch.persistence.room.*
import qsos.library.base.entity.BaseEntity

/**
 * @author : 华清松
 * @date : 2018/10/8
 * @description : 用户信息
 */
@Entity(tableName = "app_user",
        indices = [Index(value = ["id"], unique = true)]
)
class UserEntity : BaseEntity {
    constructor()

    @Ignore
    constructor(realName: String) {
        this.realName = realName
    }

    @Ignore
    constructor(realName: String?, mobile: String?, avatar: String?) {
        this.realName = realName
        this.mobile = mobile
        this.avatar = avatar
    }

    @Ignore
    constructor(userId: String, realName: String, mobile: String, avatar: String, role: String) {
        this.userId = userId
        this.realName = realName
        this.mobile = mobile
        this.avatar = avatar
        this.role = role
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "user_id")
    var userId: String = ""
    /*家庭住址*/
    var address: String? = null
    /*邮编*/
    @ColumnInfo(name = "area_id")
    var areaId: String? = null
    /*头像*/
    var avatar: String? = null
    /*所在公司*/
    var company: String? = null
    /*角色*/
    var role: String? = ROLE.TOURIST.role
    /*创建人姓名*/
    @ColumnInfo(name = "create_user")
    var createUser: String? = null
    /*邮件*/
    var email: String? = null
    /*创建时间*/
    @ColumnInfo(name = "gmt_create")
    var gmtCreate: String? = null
    /*更新时间*/
    @ColumnInfo(name = "gmt_modified")
    var gmtModified: String? = null
    /*工号*/
    @ColumnInfo(name = "job_num")
    var jobNum: String? = null
    /*最近登录地址*/
    @ColumnInfo(name = "last_login_area")
    var lastLoginArea: String? = null
    /*最近登录时间*/
    @ColumnInfo(name = "last_login_time")
    var lastLoginTime: String? = null
    /*手机号*/
    var mobile: String? = null
    /*密码*/
    var password: String? = null
    /*职位*/
    var position: String? = null
    /*QQ号*/
    @ColumnInfo(name = "qq_number")
    var qqNumber: String? = null
    /*加盐*/
    var salt: String? = null
    /*性别*/
    var sex: Int? = null
    /*账号状态*/
    var status: String? = null
    /*真实姓名*/
    @ColumnInfo(name = "real_name")
    var realName: String? = null

    enum class ROLE(key: String) {
        TOURIST("游客"),
        ADMIN("管理员");

        val role: String = key

    }
}