package dev.aidaco.parked;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Interfaces.SingleResultListener;
import dev.aidaco.parked.Model.Daos.UserDao;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.ParkedDatabase;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private ParkedDatabase parkedDb;
    private UserDao userDao;

    public UserRepository(Context context) {
        parkedDb = ParkedDatabase.getInstance(context);
        userDao = parkedDb.userDao();
    }

    public void addUser(User user) {
        new AddUserAsyncTask(userDao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void getUserById(int id, SingleResultListener<User> listener) {
        new GetUserByIdAsyncTask(userDao, listener, id).execute();
    }

    public void getUserByUsername(String username, SingleResultListener<User> listener) {
        new GetUserByUsernameAsyncTask(userDao, listener, username).execute();
    }

    private static class AddUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        AddUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

    private static class GetUserByIdAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private SingleResultListener<User> listener;
        private int userId;
        private List<User> result;


        GetUserByIdAsyncTask(UserDao userDao, SingleResultListener<User> listener, int userId) {
            this.userDao = userDao;
            this.listener = listener;
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(Void... aVoids) {
            result = userDao.getUserById(userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(result.get(0));
        }
    }

    private static class GetUserByUsernameAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private SingleResultListener<User> listener;
        private String username;
        private List<User> result;


        GetUserByUsernameAsyncTask(UserDao userDao, SingleResultListener<User> listener, String username) {
            this.userDao = userDao;
            this.listener = listener;
            this.username = username;
        }

        @Override
        protected Void doInBackground(Void... aVoids) {
            this.result = userDao.getUserByUsername(username);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(result.get(0));
        }
    }

}
