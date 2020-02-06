package Data;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class CourseTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Course.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    @Test
    public void getAsJson() throws JSONException {
        Course testobj = new Course("data och så","d0012e",Credits.SEVEN,"xaxaxaxa","json",new CourseDate(1203,LP.FOUR));
        String test = testobj.getAsJson();
        System.out.println(test);
        assert test.equals(new JSONObject(test));
        testobj.setDevelopedKC(new KC("1","a thing", 2, "wow"));
        testobj.setRequiredKC((new KC("1","not a thing", 1, "no")));
        test = testobj.getAsJson();
        System.out.println(test);
        testobj.setDevelopedKC(new KC("4","boi thing", 3, "jesus"));
        testobj.setRequiredKC(new KC("11","wtf thing", 4, "Holy cow"));
        test = testobj.getAsJson();
        System.out.println(test);


    }
}
