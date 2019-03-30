package qsos.library.base.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import qsos.library.base.entity.StaticKey
import qsos.library.base.entity.app.UserEntity

/**
 * @author : 华清松
 * @date : 2018/10/15
 * @description : 用户数据库
 */
@Database(entities = [UserEntity::class],
        version = 1,
        exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {

        @Volatile
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }

        private fun create(context: Context): UserDatabase {
            return Room.databaseBuilder(context, UserDatabase::class.java, StaticKey.USER_DB_NAME).build()
        }
    }

}