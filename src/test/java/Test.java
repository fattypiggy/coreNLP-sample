import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main (String args[]) {
        String text = "鲁迅（1881年9月25日－1936年10月19日），原名周樟寿，后改名周树人，字豫山，后改豫才，“鲁迅”是他1918年发表《狂人日记》时所用的笔名，也是他影响最为广泛的笔名，浙江绍兴人。\" +\n" +
                "                \"著名文学家、思想家，五四新文化运动的重要参与者，中国现代文学的奠基人。毛泽东曾评价：“鲁迅的方向，就是中华民族新文化的方向。” [1-6] ";
        // remove some useless character
        text = text.replaceAll("\t"," ").replaceAll("<[^>]*>", "");
        System.out.println(text);
        Pattern p = Pattern.compile("(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号]))");
        Matcher m = p.matcher(text);
        boolean b = m.find();
        System.out.println(b);
        System.out.println(m.group());
    }
}
