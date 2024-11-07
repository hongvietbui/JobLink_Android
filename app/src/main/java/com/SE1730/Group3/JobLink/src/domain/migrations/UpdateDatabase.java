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
                    "`message` TEXT)");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 1. Tạo bảng Users mới với cấu trúc mới
            database.execSQL("CREATE TABLE IF NOT EXISTS `Users_new` (" +
                    "`id` BLOB NOT NULL, " +
                    "`username` TEXT, " +
                    "`password` TEXT, " +
                    "`email` TEXT, " +
                    "`firstName` TEXT, " +
                    "`lastName` TEXT, " +
                    "`phoneNumber` TEXT, " +
                    "`dateOfBirth` TEXT, " +
                    "`address` TEXT, " +
                    "`lat` INTEGER, " +
                    "`lon` INTEGER, " +
                    "`avatar` TEXT, " +
                    "`roleId` BLOB, " +
                    "`refreshToken` TEXT, " +
                    "`refreshTokenExpiryTime` TEXT, " +
                    "`status` TEXT, " +
                    "`isDeleted` INTEGER, " +
                    "`deletedAt` TEXT, " +
                    "`deletedBy` TEXT, " +
                    "`createdAt` TEXT, " +
                    "`createdBy` TEXT, " +
                    "`updatedAt` TEXT, " +
                    "`updatedBy` TEXT, " +
                    "PRIMARY KEY(`id`))");

            // 2. Copy dữ liệu từ bảng Users cũ sang bảng Users_new
            database.execSQL("INSERT INTO `Users_new` (`id`, `username`, `password`, `email`, `firstName`, " +
                    "`lastName`, `phoneNumber`, `dateOfBirth`, `address`, `lat`, `lon`, `avatar`, `roleId`, " +
                    "`refreshToken`, `refreshTokenExpiryTime`, `status`, `isDeleted`, `deletedAt`, `deletedBy`, " +
                    "`createdAt`, `createdBy`, `updatedAt`, `updatedBy`) " +
                    "SELECT `id`, `username`, `password`, `email`, `firstName`, `lastName`, `phoneNumber`, " +
                    "`dateOfBirth`, `address`, `lat`, `lon`, `avatar`, `roleId`, `refreshToken`, " +
                    "`refreshTokenExpiryTime`, `status`, `isDeleted`, `deletedAt`, `deletedBy`, `createdAt`, " +
                    "`createdBy`, `updatedAt`, `updatedBy` FROM `Users`");

            // 3. Xóa bảng Users cũ
            database.execSQL("DROP TABLE `Users`");

            // 4. Đổi tên bảng Users_new thành Users
            database.execSQL("ALTER TABLE `Users_new` RENAME TO `Users`");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Thực hiện câu lệnh SQL để thêm cột mới vào bảng User
            database.execSQL("ALTER TABLE `Message` ADD COLUMN `jobId` BLOB");
            database.execSQL("ALTER TABLE `Message` ADD COLUMN `isWorker` INTEGER DEFAULT 0 NOT NULL");
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //Added new Notification from notification
            database.execSQL("CREATE TABLE IF NOT EXISTS `Notification` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`title` TEXT, " +
                    "`message` TEXT, " +
                    "`timestamp` TEXT)");

        }
    };
}
