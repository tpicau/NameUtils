/*
 * MIT License
 *
 * Copyright (c) 2020 Tim Coates
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.com.tpic.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameUtilsTest {

    @Test
    void normalise() {
        Map<String, String> names = new HashMap<>();
        names.put("john o'farrell", "John O'Farrell");
        names.put(" arron  james-smith  ", "Arron James-Smith");
        names.put(" AlISTaiR MCBRIDE", "Alistair McBride");
        names.put("MARTIN MACBRIDE", "Martin MacBride");
        names.put("LANA DEL MAY", "Lana del May");
        names.put("MITCHEL EL-HOWIE", "Mitchel el-Howie");
        names.put("  anne   Maclaren ", "Anne MacLaren");
        names.put("  Anne   Maclaren ", "Anne Maclaren");
        names.put("mAria della rosa", "Maria Della Rosa");
        names.put(" niccolò lo savio", "Niccolò Lo Savio");
        names.put("sergio de la peña", "Sergio de la Peña");
        names.put(" Cedric De beer", "Cedric de Beer");
        names.put("HENDRIK VAN DER  POST", "Hendrik van der Post");
        names.put(" s. du toit", "S. du Toit");
        names.put("n. el-madji-amor", "N. el-Madji-Amor");
        names.put(" Ernst aus'm weerth ", "Ernst aus'm Weerth");
        names.put(" Barbey d'Aurevilly ", "Barbey d'Aurevilly");
        names.put("    ", "");
        names.put(null, "");
        names.put("-$+ @$)(*&^^@*", "");
        for (Map.Entry<String, String> name : names.entrySet()) {
            String normalised = NameUtils.normalise(name.getKey());
            System.out.printf("Name='%s', Expected='%s', Actual='%s'%n", name.getKey(), name.getValue(), normalised);
            assertEquals(name.getValue(), normalised);
        }
    }

    @Test
    void isNormalised() {
        Map<String, Boolean> names = new HashMap<>();
        names.put("john o'farrell", false);
        names.put("John O'Farrell", true);
        names.put(" arron  james-smith  ", false);
        names.put("Arron James-Smith", true);
        names.put(" AlISTaiR MCBRIDE", false);
        names.put("Alistair McBride", true);
        names.put("MARTIN MACBRIDE", false);
        names.put("Martin MacBride", true);
        names.put("LANA DEL NAY", false);
        names.put("Lana del Shae", true);
        names.put("MITCHEL EL-HOWIE", false);
        names.put("Mitchel el-Howie", true);
        names.put("  anne   Maclaren ", false);
        names.put("Anne MacLaren", true);
        names.put("  Anne   Maclaren ", false);
        names.put("Anne Maclaren", true);
        names.put("mAria della rosa", false);
        names.put("Maria Della Rosa", true);
        names.put(" niccolò lo savio", false);
        names.put("Niccolò Lo Savio", true);
        names.put("sergio de la peña", false);
        names.put("Sergio de la Peña", true);
        names.put(" Cedric De beer", false);
        names.put("Cedric de Beer", true);
        names.put("HENDRIK VAN DER  POST", false);
        names.put("Hendrik van der Post", true);
        names.put(" s. du toit", false);
        names.put("S. du Toit", true);
        names.put("n. el-madji-amor", false);
        names.put("N. el-Madji-Amor", true);
        names.put(" Ernst aus'm weerth ", false);
        names.put("Ernst aus'm Weerth", true);
        names.put(" Barbey d'Aurevilly ", false);
        names.put("Barbey d'Aurevilly", true);
        names.put("    ", false);
        names.put("", false);
        names.put(null, false);
        names.put("-$+ @$)(*&^^@*", false);
        for (Map.Entry<String, Boolean> name : names.entrySet()) {
            boolean isCorrectCase = NameUtils.isNormalised(name.getKey());
            System.out.printf("Name='%s', Expected='%s', Actual='%s'%n", name.getKey(), name.getValue(), isCorrectCase);
            assertEquals(name.getValue(), isCorrectCase);
        }
    }

    @Test
    void normaliseWhitespaceToEmpty() {
        Map<String, String> names = new HashMap<>();
        names.put("Martin MacBride", "Martin MacBride");
        names.put("MITCHEL EL-HOWIE", "MITCHEL EL-HOWIE");
        names.put("Mitchel el-Howie", "Mitchel el-Howie");
        names.put("  anne   Maclaren ", "anne Maclaren");
        names.put("Anne MacLaren", "Anne MacLaren");
        names.put("  Anne   Maclaren ", "Anne Maclaren");
        names.put("Anne Maclaren", "Anne Maclaren");
        names.put("    ", "");
        names.put("", "");
        names.put("a ", "a");
        names.put(" a", "a");
        names.put(null, "");
        names.put("-$+ @$)(*&^^@*", "-$+ @$)(*&^^@*");
        for (Map.Entry<String, String> name : names.entrySet()) {
            String normalised = NameUtils.normaliseWhitespaceToEmpty(name.getKey());
            System.out.printf("String='%s', Expected='%s', Actual='%s'%n", name.getKey(), name.getValue(), normalised);
            assertEquals(name.getValue(), normalised);
        }
    }
}