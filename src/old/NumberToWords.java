package old;
/*
Integer to English Words
Total Accepted: 8079 Total Submissions: 48999 Difficulty: Medium

Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.

For example,
123 -> "One Hundred Twenty Three"
12345 -> "Twelve Thousand Three Hundred Forty Five"
1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"

*/
public class NumberToWords {
    private static final String[] lessThan20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] tens = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] thousands = {"", "Thousand", "Million", "Billion"};
    
    public static String numberToWords(int num) {
        if (num == 0) return "Zero";
        int i = 0 ;
        String words = "";
        
        while ( num > 0) {
            if ( num % 1000 != 0) {
                words = helper(num % 1000) + thousands[i] + " " + words;
            }
            num /= 1000;
            i++;
        }
       return words.trim(); 
    }

    public static String helper( int num) {
        if (num == 0)  return "";
        
        else if (num < 20) 
            return lessThan20[num] + " ";
        else if (num < 100)
            return tens[num/10] + " " + helper(num % 10);
        else 
            return lessThan20[num / 100]  + " Hundred " + helper(num % 100);
    }
        
        
    
    public static void main(String[] args) {
        int num = 1234567;
        String str = numberToWords(num);
        System.out.println("str = " + str);
        
    }
    
}