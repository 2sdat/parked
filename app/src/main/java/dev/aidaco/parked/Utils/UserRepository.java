package dev.aidaco.parked.Utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Database.Daos.UserDao;
import dev.aidaco.parked.Database.ParkedDatabase;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private ParkedDatabase parkedDb;
    private UserDao userDao;

    private static UserRepository INSTANCE;
    private User currentUser = null;
    private Enums.UserType accessPrivilege = null;

    private UserRepository(Context context) {
        parkedDb = ParkedDatabase.getInstance(context);
        userDao = parkedDb.userDao();
    }

    public static synchronized UserRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(context);
        }

        return INSTANCE;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.accessPrivilege = user.getUserType();
    }

    public Enums.UserType getAccessPrivilege() {
        return accessPrivilege;
    }

    public void attemptToggleActive(int userId, AttemptListener listener) {
        new AttemptToggleActiveAsynctask(userDao, getAccessPrivilege(), userId, listener).execute();
    }

    public void addUser(User user) {
        new AddUserAsyncTask(userDao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }


    public LiveData<List<User>> getAllUsers_LiveData() {
        return userDao.getAllUsers_LiveData();
    }

    public void getUserById(int userId, SingleResultListener<User> listener) {
        new GetUserByIdAsyncTask(userDao, listener, userId).execute();
    }

    public LiveData<List<User>> getUserById_LiveData(int userId) {
        return userDao.getUserById_LiveData(userId);
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

    private static class AttemptToggleActiveAsynctask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private AttemptListener listener;
        private int userId;
        private Enums.UserType accessPrivelige;
        private int resultCode;

        public AttemptToggleActiveAsynctask(UserDao userDao, Enums.UserType accessPrivelige, int userId, AttemptListener listener) {
            this.userDao = userDao;
            this.accessPrivelige = accessPrivelige;
            this.userId = userId;
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onReturnCode(resultCode);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<User> _user = userDao.getUserById(userId);

            if (_user.size() == 0) {
                resultCode = AttemptListener.FAIL;
                return null;
            }

            User user = _user.get(0);

            if (user.getUserType().getTypeCode() >= accessPrivelige.getTypeCode()) {
                resultCode = AttemptListener.FAIL;
                return null;
            } else {
                user.setActive(!user.getIsActive());
                if (user.getIsActive()) {
                    resultCode = AttemptListener.POS_SUCCESS;
                } else {
                    resultCode = AttemptListener.NEG_SUCCESS;
                }

                userDao.updateUser(user);
            }

            return null;
        }
    }
}
