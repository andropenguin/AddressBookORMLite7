package com.sarltokyo.addressbookormlite7.asynctask;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;
import com.sarltokyo.addressbookormlite7.entity.Address;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by osabe on 15/07/24.
 */
public class PersonLoaderTest extends InstrumentationTestCase {

    private OpenHelper mOpenHelper;
    private Context mContext;
    private PersonLoader mPersonLoader;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test_" );
        mOpenHelper = new OpenHelper(mContext);
        mPersonLoader = new PersonLoader(mContext);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mOpenHelper.close();
        mOpenHelper = null;
        mPersonLoader = null;
    }

    private void createRegularData() throws Exception {
        // insert
        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            Address address = new Address();
            address.setZipcode(String.valueOf(i));
            address.setPrefecture("Tokyo");
            address.setCity("Shinjyuku-ku");
            address.setOther("Higashi-shinjyuku " + (i+1));
            person.setName("Abe" + i);
            person.setAddress(address);
            mOpenHelper.registerPerson(person);
        }
    }

    private void clearData() throws Exception {
        List<Person> persons = mOpenHelper.findPerson();
        for (Person person: persons) {
            mOpenHelper.deletePerson(person.getName());
        }
    }


    public void testSucces() throws Exception {
        createRegularData();

        List<Person> actual = mPersonLoader.getPersons();

        for (int i = 0; i< actual.size(); i++) {
            Person actualPerson = actual.get(i);
            String actuallName = actualPerson.getName();
            Address actualAddress = actualPerson.getAddress();
            String actualZipcode = actualAddress.getZipcode();
            String actualPrefecture = actualAddress.getPrefecture();
            String acutalCity = actualAddress.getCity();
            String actualOther = actualAddress.getOther();

            assertThat(actualZipcode, is(String.valueOf(i)));
            assertThat(actualPrefecture, is(("Tokyo")));
            assertThat(acutalCity, is(("Shinjyuku-ku")));
            assertThat(actualOther, is("Higashi-shinjyuku " + (i+1)));
            assertThat(actuallName, is("Abe" + i));
        }
    }

    public void testEmptyData() throws Exception {
        clearData();

        List<Person> actual = mPersonLoader.getPersons();
        assertTrue(actual.isEmpty());


    }
}
