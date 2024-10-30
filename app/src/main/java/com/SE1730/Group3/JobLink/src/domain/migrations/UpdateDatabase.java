package com.SE1730.Group3.JobLink.src.domain.migrations;

import android.support.annotation.NonNull;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class UpdateDatabase {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Thực hiện câu lệnh SQL để thêm cột mới vào bảng User
            database.execSQL("CREATE TABLE IF NOT EXISTS `Message` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`senderId` BLOB, " +
                    "`receiverId` BLOB, " +
                    "`text` TEXT)");
        }
    };
}
