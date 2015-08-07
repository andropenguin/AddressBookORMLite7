package com.sarltokyo.addressbookormlite7.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sarltokyo.addressbookormlite7.entity.Address;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by osabe on 15/07/17.
 */
public class OpenHelper extends OrmLiteSqliteOpenHelper {
    private final static String TAG = OpenHelper.class.getSimpleName();

    private final static String DATABASE_NAME = "addressbook.db";
    private final static int DATABASE_VERSION = 1;

    private Dao<Person, Integer> mPersonDao;
    private Dao<Address, Integer> mAddressDao;

    public OpenHelper(Context context) {
        this(context, DATABASE_NAME);
    }


    public OpenHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // エンティティを指定してcreate tableする
            TableUtils.createTable(connectionSource, Person.class);
            TableUtils.createTable(connectionSource, Address.class);
        } catch (SQLException e) {
            Log.e(TAG, "cannot create database");
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Address.class, true);
            TableUtils.dropTable(connectionSource, Person.class, true);
            onCreate(database);
        } catch (SQLException e) {
            Log.e(TAG, "cannot upgrade database");
            Log.e(TAG, e.getMessage());
        }
    }

    public Dao<Person, Integer> getPersonDao() throws SQLException {
        if (mPersonDao == null) {
            mPersonDao = getDao(Person.class);
        }
        return mPersonDao;
    }

    public Dao<Address, Integer> getAddressDao() throws SQLException {
        if (mAddressDao == null) {
            mAddressDao = getDao(Address.class);
        }
        return mAddressDao;
    }

    public Person findPerson(String name) throws SQLException {
        List<Person> persons = getPersonDao().queryForEq("name", name);
        if (persons.isEmpty()) {
            Log.d(TAG, "persons isEmpty");
            return null;
        } else {
            Log.d(TAG, "person exists");
            return persons.get(0);
        }
    }

    public List<Person> findPerson() throws SQLException {
        return getPersonDao().queryForAll();
    }

    public void registerPerson(Person person) throws SQLException {
        getPersonDao().create(person);
    }

    public void updatePerson(Person person) throws SQLException {
        Address address = person.getAddress();
        getAddressDao().update(address); // todo
        getPersonDao().update(person);
    }

    public boolean deletePerson(String name) throws SQLException {
        Person person = findPerson(name);
        if (person == null) {
            return false;
        } else {
            Address address = person.getAddress(); // todo
            getAddressDao().delete(address);
            getPersonDao().delete(person);
            return true;
        }
    }
}
