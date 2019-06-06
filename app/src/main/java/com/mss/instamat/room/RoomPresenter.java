package com.mss.instamat.room;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RoomPresenter extends MvpPresenter<RoomView> {

    Random random = new Random();

    public void onAddUserClick() {
        User user = createUser();
        Single.create((SingleOnSubscribe<Long>) emitter -> emitter.onSuccess(App.getDatabase().productDao().insert(user)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> showUsers());
    }

    public void onAddUsersClick() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(createUser());
        }
        Single.create((SingleOnSubscribe<List<Long>>) emitter -> emitter.onSuccess(App.getDatabase().productDao().insertAll(users)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> showUsers());
    }

    public void onDeleteUserClick() {
        App.getDatabase().productDao().getAll()
                .subscribeOn(Schedulers.io())
                .map(users -> {

                    if (users.size() > 0) {
                        return App.getDatabase().productDao().delete(users.get(0));
                    } else {
                        return 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> showUsers());
    }

    public void onUpdateUserClick() {
        App.getDatabase().productDao().getAll()
                .subscribeOn(Schedulers.io())
                .map(users -> {

                    if (users.size() > 0) {
                        User user = users.get(0);
                        user.setName(user.getName() + "_");
                        return App.getDatabase().productDao().update(user);
                    } else {
                        return 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> showUsers());
    }

    private void showUsers() {
        App.getDatabase().productDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> getViewState().showUsers(usersToString(users)));
    }

    private String usersToString(List<User> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append("Id = " + user.getId());
            stringBuilder.append(", Name = " + user.getName());
            stringBuilder.append(", Surname = " + user.getSurname());
            stringBuilder.append(", Age = " + user.getAge());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private User createUser() {
        User user = new User();
        user.setName("Name" + random.nextInt(100));
        user.setSurname("Surname" + random.nextInt(100));
        user.setAge(random.nextInt(80));
        return user;
    }
}
