package com.sarltokyo.addressbookormlite7.asynctask;

import android.content.Context;
import android.util.Log;
import com.sarltokyo.addressbookormlite7.core.ContextLogic;
import com.sarltokyo.addressbookormlite7.core.ContextLogicFactory;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osabe on 15/08/06.
 */
public class PersonLoader extends AbstractAsyncTaskLoader<List<Person>> {

    private final static String TAG = PersonLoader.class.getSimpleName();

    private ContextLogic mContextLogic;
    private OpenHelper mOpenHelper;

    public PersonLoader(Context context) {
        super(context);
        mContextLogic = ContextLogicFactory.createContextLogic(context);
        mOpenHelper = mContextLogic.createOpenHelper();
    }

    @Override
    public List<Person> loadInBackground() {
        return getPersons();
    }

    public List<Person> getPersons() {
        List<Person> persons = null;
        try {
            persons = mOpenHelper.findPerson();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        if (persons == null) {
            persons = new ArrayList<Person>();
        }

        if (persons.size() == 0) {
            Log.d(TAG, "person is empty");
        }
        for (Person person: persons) {
            Log.d(TAG, "person = " + person.getName());
        }
        return persons;
    }
}
