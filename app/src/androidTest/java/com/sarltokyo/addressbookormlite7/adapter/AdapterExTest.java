package com.sarltokyo.addressbookormlite7.adapter;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sarltokyo.addressbookormlite7.app.R;
import com.sarltokyo.addressbookormlite7.entity.Address;
import com.sarltokyo.addressbookormlite7.entity.Person;
import org.hamcrest.Matchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AdapterExTest extends InstrumentationTestCase {
    public void testGetView() {
        List<Person> dummys = new ArrayList<Person>();
        Person person1 = new Person();
        Address address1 = new Address();
        address1.setZipcode("123-4567");
        address1.setPrefecture("Tokyo");
        address1.setCity("Shinjuku");
        address1.setOther("hoge 1-2-3");
        person1.setName("Foo");
        person1.setAddress(address1);

        Person person2 = new Person();
        Address address2 = new Address();
        address2.setZipcode("111-2222");
        address2.setPrefecture("Kyoto");
        address2.setCity("Kyoto");
        address2.setOther("boo 4-5-6");
        person2.setName("Bar");
        person2.setAddress(address2);

        dummys.add(person1);
        dummys.add(person2);

        Context context = getInstrumentation().getTargetContext();
        AdapterEx sut = new AdapterEx(context, dummys);

        View view1 = sut.getView(0, null, null);
        assertThat(view1, is(Matchers.instanceOf(LinearLayout.class)));
        assertThat(view1.findViewById(R.id.nameTv), is(Matchers.instanceOf(TextView.class)));
        assertThat(((TextView)view1.findViewById(R.id.nameTv)).getText().toString(), is("Foo"));

        View view2 = sut.getView(1, null, null);
        assertThat(view2, is(Matchers.instanceOf(LinearLayout.class)));
        assertThat(view2.findViewById(R.id.nameTv), is(Matchers.instanceOf(TextView.class)));
        assertThat(((TextView)view2.findViewById(R.id.nameTv)).getText().toString(), is("Bar"));
    }
}
