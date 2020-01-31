package com.tvschedule.domain;
import com.tvschedule.domain.TVProgram;
import com.tvschedule.resource.TVProgramsResource;
import io.dropwizard.setup.Environment;
import org.junit.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TVProgramTest {
    private static Validator validator;

    @BeforeClass
    public static  void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void isTVProgramValidated(){
        TVProgram tvProgram = new TVProgram("id","1",
                "2","2020-01-22","09:20","","6");
        Set<ConstraintViolation<TVProgram>> constraintViolations =
                validator.validate( tvProgram );
        assertEquals(0, constraintViolations.size());
     }
    @Test
    public void isDateValidated(){//aired
        TVProgram tvProgram = new TVProgram("id","1",
                            "2","2020-01-22","09:20","","6");
        assertEquals(10,tvProgram.getAired().length());
        assertEquals("2020-01-22",tvProgram.getAired());
        assertNotEquals(11,tvProgram.getAired().length());
        Assert.assertTrue(tvProgram.getAired().matches("\\d{4}-\\d{2}-\\d{2}"));
    }
    @Test
    public void isStartTimeValidated(){
        TVProgram tvProgram = new TVProgram("id","1",
                "2","2020-01-22","09:20","","6");
        assertEquals("09:20",tvProgram.getStartTime());
        assertNotEquals("9:22",tvProgram.getStartTime());
        Assert.assertTrue(tvProgram.getStartTime().matches("\\d{2}:\\d{2}"));
    }
    @Test
    public void isAirTimeValidated(){
        TVProgram tvProgram = new TVProgram("id","1",
                "2","2020-01-22","09:20","","6");
        assertEquals("6",tvProgram.getAirTime());
        assertNotEquals("9:22",tvProgram.getAirTime());
        Assert.assertTrue(tvProgram.getAirTime().matches("^[0-9]+$"));
    }

}
