package dev.aidaco.parked.Utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Database.Daos.UserDao;
import dev.aidaco.parked.Database.ParkedDatabase;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;

// TODO: 5/14/19 javadoc
public class UserRepository {

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

    public void resetData() {
        this.currentUser = null;
        this.accessPrivilege = null;
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

    public void attemptCreateUser(String firstName, String lastName, String username, String password, Enums.UserType userType, CreateUserResultListener listener) {
        new CreateUserAsyncTask(userDao, firstName, lastName, username, password, userType, listener).execute();
    }

    public void ensureDefaultUser() {
        new DefaultUserAsyncTask(userDao).execute();
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

            if (result.size() > 0) {
                listener.onResult(result.get(0));
            } else {
                listener.onResult(null);
            }
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

    private static class CreateUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private Enums.UserType userType;
        private CreateUserResultListener listener;
        private int resultCode;
        private int userId = 0;

        public CreateUserAsyncTask(UserDao userDao, String firstName, String lastName, String username, String password, Enums.UserType userType, CreateUserResultListener listener) {
            this.userDao = userDao;
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.userType = userType;
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onAttemptReturn(resultCode, userId);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (userDao.getUserByUsername(username).size() > 0) {
                resultCode = CreateUserResultListener.INC_USERNAME;
                return null;
            }

            User newUser = new User(0, username, password, firstName, lastName, userType, true);

            userDao.addUser(newUser);

            List<User> _newUser = userDao.getUserByUsername(username);

            if (_newUser.size() > 0) {
                userId = _newUser.get(0).getId();
                resultCode = CreateUserResultListener.SUCCESS;
                return null;
            } else {
                resultCode = CreateUserResultListener.FAIL;
                return null;
            }
        }
    }

    private static class DefaultUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        public DefaultUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (userDao.getAllUsers().size() == 0) {
                userDao.addUser(User.DEF_USER);
            }
            return null;
        }
    }
}
