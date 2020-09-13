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

import java.util.regex.Pattern;

public class NameUtils {

    protected NameUtils() {
    }

    protected static final Pattern WORDS_INCLUDING_DIACRITICS = Pattern.compile("[\\p{L}\\p{N}_]+");
    protected static final Pattern TITLE_CASE = Pattern.compile("\\b[a-z]");
    protected static final Pattern TITLE_CASE_WORDS = Pattern.compile("\\b[A-Z]");
    protected static final Pattern ALWAYS_LOWER_WORD = Pattern.compile("(?<=\\s|^)(van|von|de\\sla|den|op\\sde|ter|ten|van't|del|" +
            "der|du|de|dit|gelar|e|am|aus'm|vom|zum|zur|und zu|bin|b\\.|anak|a\\.|ak|ak\\.|binte|bte\\.|binti|" +
            "bt\\.|ibni)(?=\\s|$)", Pattern.CASE_INSENSITIVE);
    protected static final Pattern ALWAYS_LOWER_PREFIX = Pattern.compile("(?<=\\s)(al|el)(?=-[A-z])", Pattern.CASE_INSENSITIVE);
    protected static final Pattern CAPITALISE_AFTER = Pattern.compile("(?!\\s)(Mac|Mc)[A-z]", Pattern.CASE_INSENSITIVE);
    protected static final Pattern SEQUENTIAL_CAPS = Pattern.compile("[A-Z]{2,}");
    protected static final Pattern RULE_EXCEPTIONS = Pattern.compile("((?<=\\s)(van|von|de\\sla|den|op\\sde|ter|ten|van't|del|" +
            "der|du|de|dit|gelar|e|am|aus'm|vom|zum|zur|und zu|bin|b\\.|anak|a\\.|ak|ak\\.|binte|bte\\.|binti" +
            "|bt\\.|ibni)(?=\\s))|((?<=\\s)(al-|el-|d')(?=[A-z]))");
    protected static final Pattern PUNCTUATION = Pattern.compile("\\p{Punct}|\\s");
    protected static final Pattern WHITESPACE = Pattern.compile("\\s+");
    protected static final Pattern UNNECESSARY_WHITESPACE = Pattern.compile("((?<=\\s)(\\s))|(^\\s)|(\\s$)");

    /**
     * Attempt to fix name spacing and normalise capitalisation.
     * <ul>
     * <li>Always removes duplicate spaces and trims the string.</li>
     * <li>If there are sequential capital letters or there are no words in the string that are in 'Title' case, then it
     * will apply formatting rules.</li>
     * </ul>
     * Follows name rules from the HURIDOCS <a href="https://www.huridocs.org/resource/how-to-record-names-of-persons/">
     * human rights manual</a>.
     *
     * @param name The name with mixed case and additional white space.
     * @return The name with case and capitalisation normalised, or null if blank.
     */
    public static String normalise(String name) {
        if (isBlank(name) || isAllPunctuation(name)) {
            return "";
        }

        name = normaliseWhitespaceToEmpty(name);

        if (!isNormalised(name)) {
            // Apply rules
            name = TITLE_CASE.matcher(name.toLowerCase()).replaceAll(x -> x.group().toUpperCase());
            name = ALWAYS_LOWER_WORD.matcher(name).replaceAll(x -> x.group().toLowerCase());
            name = ALWAYS_LOWER_PREFIX.matcher(name).replaceAll(x -> x.group().toLowerCase());
            name = CAPITALISE_AFTER.matcher(name).replaceAll(x -> x.group().substring(0, x.group().length() - 1) + x.group().substring(x.group().length() - 1).toUpperCase());
        }

        return name;
    }

    /**
     * Remove unnecessary spacing from a string.
     *
     * @param str Input string.
     * @return String with additional unnecessary white space removed. Blank string if input string is null.
     */
    public static String normaliseWhitespaceToEmpty(String str) {
        if(str == null){
            return "";
        }
        return WHITESPACE.matcher(str).replaceAll(" ").trim();
    }

    /**
     * Determine if a proper noun is already in the correct case.
     *
     * @param name The input proper noun.
     * @return true if proper noun is not null and matches common name capitalisation rules.
     */
    public static boolean isNormalised(String name) {
        if (isBlank(name) || isAllPunctuation(name) || SEQUENTIAL_CAPS.matcher(name).find()) {
            return false;
        }
        long wordCount = WORDS_INCLUDING_DIACRITICS.matcher(name).results().count();
        long unnecessaryWhitespace = UNNECESSARY_WHITESPACE.matcher(name).results().count();
        long titleCaseWords = TITLE_CASE_WORDS.matcher(name).results().count();
        long exceptions = RULE_EXCEPTIONS.matcher(name).results()
                .map(x -> WORDS_INCLUDING_DIACRITICS.matcher(x.group()).results().count())
                .mapToLong(Long::longValue).sum();
        return wordCount + unnecessaryWhitespace - titleCaseWords - exceptions == 0L;
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    private static boolean isAllPunctuation(String str) {
        return str == null || PUNCTUATION.matcher(str).results().count() == str.length();
    }

}