package dev.aidaco.parked;

import android.content.Context;
import android.os.AsyncTask;

import dev.aidaco.parked.Interfaces.ResultListener;
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

    public void getUserById(int id, ResultListener<User> listener) {
        new GetUserByIdAsyncTask(userDao, listener).execute(id);
    }

    public void getUserByUsername(String username, ResultListener<User> listener) {
        new GetUserByUsernameAsyncTask(userDao, listener).execute(username);
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

    private static class GetUserByIdAsyncTask extends AsyncTask<Integer, Void, User> {
        private UserDao userDao;
        private ResultListener<User> listener;


        GetUserByIdAsyncTask(UserDao userDao, ResultListener<User> listener) {
            this.userDao = userDao;
            this.listener = listener;
        }

        @Override
        protected User doInBackground(Integer... integers) {
            return userDao.getUserById(integers[0]);
        }

        @Override
        protected void onPostExecute(User result) {
            listener.onResult(result);
        }
    }

    private static class GetUserByUsernameAsyncTask extends AsyncTask<String, Void, User> {
        private UserDao userDao;
        private ResultListener<User> listener;


        GetUserByUsernameAsyncTask(UserDao userDao, ResultListener<User> listener) {
            this.userDao = userDao;
            this.listener = listener;
        }

        @Override
        protected User doInBackground(String... strings) {
            return userDao.getUserByUsername(strings[0]);
        }

        @Override
        protected void onPostExecute(User result) {
            listener.onResult(result);
        }
    }

}
