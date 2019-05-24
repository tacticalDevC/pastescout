package at.tacticalDevC;

import java.util.regex.Pattern;

public class ParseUtils {

    private static String[] regexs = new String[]
    {
            "[A-Za-z0-9._%+-]*@[A-Za-z0-9]*\\.[A-Za-z]{2,}\\s*:.*",
            "Username:\\s[a-zA-Z0-9]*\\sPassword:\\s[a-zA-Z0-9]*",
            "Email:\\s[a-zA-Z0-9]*\\sPassword:\\s[a-zA-Z0-9]*",
            "USERNAME[\\t|\\s]*PASSWORD",
            "EMAIL[\\t|\\s]*PASSWORD",
            "Username:\\s[A-Za-z0-9._%+-]*@[A-Za-z0-9]*\\.[A-Za-z]{2,}\\nPassword:\\s.*",
            "Email:\\s[A-Za-z0-9._%+-]*@[A-Za-z0-9]*\\.[A-Za-z]{2,}\\nPassword:\\s.*"
    };
    public static boolean parse(String paste)
    {
        for (String regex : regexs)
        {
            if(Pattern.matches(regex, paste))
                return true;
        }
        return false;
    }
}
