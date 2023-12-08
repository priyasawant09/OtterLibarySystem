package com.example.otterlibaraysystem;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {userPassTable.class , bookTable.class ,TransactionTable.class}, version=3, exportSchema = false)
public abstract class bookDatabase extends RoomDatabase {

    private static bookDatabase sInstance;

    public abstract UserDao user();
    public abstract bookDao book();
    public abstract TransactionDao transaction_Dao();

    public static synchronized bookDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            bookDatabase.class, "trivia.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public void populateInitialData() {
        runInTransaction(() -> {

            if (user().count() == 0) {
                user().insertUser(new userPassTable("anton", "t3nn1s23"));
                user().insertUser(new userPassTable("bernie", "ind1em3d$"));
                user().insertUser(new userPassTable("shirleybee", "carmel2chicago"));
                user().insertUser(new userPassTable("!admin2" , "!admin2"));
            }
            if (book().count() == 0) {
                book().insertBook(new bookTable("Angela’s Ashes", "Frank McCourt","Memoir"));
                book().insertBook(new bookTable("Head First Java", "Kathy Sierra","Computer Science"));
                book().insertBook(new bookTable("A Little Life", "Hanya Yanagihara","Fiction"));
            }


        });
    }


}






