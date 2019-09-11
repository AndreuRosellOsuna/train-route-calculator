package ro.andreu.recipes.techs.graph.importer.csv;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleCsvNamedNodeGraphImporterBeanTest {

    @Test
    public void regexTest() {
        String regex = SimpleCsvNamedNodeGraphImporterBean.REGEX_SPLITTER;
        String line = "abc def,ghi, jkl  mno,  pqr , stu  ,  vwxyz";

        String[] parts = line.split(regex);

        assertEquals("abc", parts[0]);
        assertEquals("def", parts[1]);
        assertEquals("ghi", parts[2]);
        assertEquals("jkl", parts[3]);
        assertEquals("mno", parts[4]);
        assertEquals("pqr", parts[5]);
        assertEquals("stu", parts[6]);
        assertEquals("vwxyz", parts[7]);
    }
}
