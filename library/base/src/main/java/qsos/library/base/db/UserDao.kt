package qsos.library.base.db

import android.arch.persistence.room.*
import io.reactivex.Single
import qsos.library.base.entity.app.UserEntity

/**
 * @author : 华清松
 * @date : 2018/10/15
 * @description : 用户 Dao 层
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM app_user")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM app_user where user_id=:userId")
    fun getUserById(userId: String): Single<UserEntity?>

    @Query("SELECT * FROM app_user where user_id=:userId")
    fun getUser(userId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

}